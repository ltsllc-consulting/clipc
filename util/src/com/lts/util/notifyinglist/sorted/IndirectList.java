/*******************************************************************************
 * Copyright 2009, Clark N. Hobbie
 * 
 * This file is part of the CLIPC library.
 * 
 * The CLIPC library is free software; you can redistribute it and/or modify it
 * under the terms of the Lesser GNU General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 * 
 * The CLIPC library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
 * License for more details.
 * 
 * You should have received a copy of the Lesser GNU General Public License
 * along with the CLIP library; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * 
 *******************************************************************************/
package com.lts.util.notifyinglist.sorted;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lts.util.notifyinglist.ListEvent;
import com.lts.util.notifyinglist.NotifyingList;
import com.lts.util.notifyinglist.NotifyingListHelper;
import com.lts.util.notifyinglist.NotifyingListListener;
import com.lts.util.notifyinglist.sublist.SublistProxy;

/**
 * A list that maps entries from its list to entries in another list.
 * <P>
 * This class maps from a virtual ordering of the data to another ordering used by the
 * list it wraps.
 * </P>
 * <P>
 * This class was originally created to hold common state, behavior and functionality that
 * the {@link SublistProxy} and {@link SortedListProxy} had in common.
 * </P>
 * <P>
 * This uses a "forward and listen" pattern: it forwards requests to the actual list and
 * only makes changes changes based on updates from the actual list.
 * </P>
 * 
 * @author cnh
 * @param <E>
 */
abstract public class IndirectList<E> 
	extends AbstractList<E> 
	implements NotifyingList<E>, NotifyingListListener
{
	protected NotifyingListHelper myHelper = new NotifyingListHelper();
	protected NotifyingList<E> myActual;
	protected List<Integer> myVirtualList = new ArrayList<Integer>();
	protected Map<Integer, Integer> myIdToVirtual = new HashMap<Integer, Integer>();
	
	protected IndirectList()
	{}
	
	protected void initialize(NotifyingList<E> list)
	{
		myActual = list;
		myActual.addListener(this);
		refresh();
	}
	
	
	public NotifyingListHelper getHelper()
	{
		return myHelper;
	}

	public void setHelper(NotifyingListHelper helper)
	{
		myHelper = helper;
	}

	public NotifyingList<E> getActual()
	{
		return myActual;
	}

	public List<Integer> getVirtualList()
	{
		return myVirtualList;
	}

	public Map<Integer, Integer> getActualToVirtual()
	{
		return myIdToVirtual;
	}

	@Override
	public E get(int index)
	{
		//
		// lock the underlying data so that the virtual/actual mapping does not change 
		// between the time when we get the actual row index and the time when we try
		// to get the actual data.
		//
		// We use the underlying data object because the only way the virtual/actual 
		// mapping can change in between these calls is the underlying data being changed.
		// as long as everyone locks against that underlying data, we should be OK.
		//
		synchronized(getDataObject())
		{
			int id = myVirtualList.get(index);
			return myActual.get(id);
		}
	}

	@Override
	public int size()
	{
		return myVirtualList.size();
	}
	
	@Override
	public void addListener(NotifyingListListener listener)
	{
		myHelper.addListener(listener);
	}

	protected List<Integer> translate(int[] rows)
	{
		List<Integer> list = new ArrayList<Integer>(rows.length);
		
		for (int index : rows)
		{
			list.add(myVirtualList.get(index));
		}
		
		return list;
	}
	
	
	@Override
	public boolean removeListener(NotifyingListListener listener)
	{
		return myHelper.removeListener(listener);
	}

	@Override
	public boolean add(E e)
	{
		return myActual.add(e);
	}

	@Override
	public void add(int index, E element)
	{
		myActual.add(element);
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		return myActual.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c)
	{
		return myActual.addAll(c);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		for (Object o : c)
		{
			if (!myActual.contains(o))
			{
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		boolean changed = false;
		
		for (Object o : c)
		{
			if (remove(o))
			{
				changed = true;
			}
		}
		
		return changed;
	}
	
	
	@Override
	public E remove(int index)
	{
		Integer actual = myVirtualList.get(index);
		if (null == actual)
			return null;
		
		return myActual.remove(actual.intValue());
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		boolean changed = false;
		
		for (E e : this)
		{
			if (!c.contains(e))
			{
				if (remove(e))
					changed = true;
			}
		}
		
		return changed;
	}

	@Override
	public E set(int index, E element)
	{
		int actual = myVirtualList.get(index);
		return myActual.set(actual, element);
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		 int size = this.size();
		 if (a.length < size)
			 a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
		 
		 for (int index = 0; index < size; index++)
		 {
			 a[index] = (T) get(index);
		 }
			 
		 return a;
	}

	@Override
	public void listEvent(ListEvent event)
	{
		E e = (E) event.element;
		
		synchronized(getDataObject())
		{
			switch (event.eventType)
			{
				case AllChanged :
					processAllChanged();
					break;
					
				case Delete :
					processDelete(event.index, e);
					break;
					
				case Insert :
					processInsert(event.index, e);
					break;
					
				case Update :
					processUpdate(event.index, e);
					break;
			}
		}
	}

	protected void processUpdate(int actualIndex, E element)
	{
		int virtualIndex = myIdToVirtual.get(actualIndex);
		myHelper.fireUpdate(virtualIndex, element);
	}

	
	protected void processInsert(int actualIndex, E element)
	{
//		int virtualIndex = findIndexForInsert(element, actualIndex);
//		myVirtualList.add(virtualIndex, actualIndex);
//		myIdToVirtual.put(actualIndex, virtualIndex);
//		myHelper.fireInsert(virtualIndex, element);
		refresh();
	}

	/**
	 * Find the index for a newly inserted element in the actual list.
	 * 
	 * <P>
	 * This method finds the location in the virtual list that a new element 
	 * should be inserted.  
	 * </P>
	 * 
	 * <P>
	 * This method is essentially a hook for subclasses to determine list behavior.
	 * A sorting list, for example, should use {@link Collections#binarySearch(List, Object)}
	 * or the like to determine the sorted location, and then return that.  A list 
	 * that only shows part of the actual list should figure out if the element should
	 * be included at all.
	 * </P>
	 * 
	 * <P>
	 * If the element should be excluded from the list, then a value of -1 should be 
	 * returned.
	 * </P>
	 * 
	 * <P>
	 * The default implementation simply returns the actualIndex passed to it.
	 * </P>
	 * 
	 * @param e The element to the added.
	 * @param index The index in the actual list where the new element lives. 
	 * @return The index where the new element should be inserted or -1 if it should be
	 * excluded from the list.
	 */
	protected int findIndexForInsert(E e, int actualIndex)
	{
		return actualIndex;
	}

	
	protected void processDelete(int actualIndex, E element)
	{
		Integer virtualIndex = myIdToVirtual.get(actualIndex);
		if (null == virtualIndex)
		{
			return;
		}
		
//		myVirtualList.remove(virtualIndex);
//		myIdToVirtual.remove(actualIndex);
//		myHelper.fireDelete(virtualIndex, element);
		refresh();
	}

	protected void processAllChanged()
	{
		refresh();
	}

	public void refresh()
	{
		myVirtualList = new ArrayList<Integer>();
		myIdToVirtual = new HashMap<Integer, Integer>();
		
		for (int index = 0; index < myActual.size(); index++)
		{
			int virtualIndex = findIndexForInsert(myActual.get(index), index);
			if (virtualIndex >= 0)
			{
				myVirtualList.add(index);
				myIdToVirtual.put(index, virtualIndex);
			}
		}
		
		myHelper.fireAllChanged();
	}

	public void nodeChanged(E node)
	{
	}
}
