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
package com.lts.util.notifyinglist.sublist;

import java.util.ArrayList;
import java.util.List;

import com.lts.swing.list.ListListener;
import com.lts.util.notifyinglist.ListEvent;
import com.lts.util.notifyinglist.NotifyingList;
import com.lts.util.notifyinglist.NotifyingListListener;
import com.lts.util.notifyinglist.sorted.IndirectList;

/**
 * A list that filters elements from another list.
 * <P>
 * An instance of this class has a view of its elements that it presents to the 
 * rest of the world and then an underlying list called the actual list.  The actual
 * list is an instance of {@link NotifyingList}.
 * </P>
 * <P>
 * The class uses an instance of {@link SublistInclusionTest} to decide if a particular
 * element in the actual list should be displayed to its clients.
 * </P>
 * <P>
 * The class forwards events unto the actual list for implementation.  That is, 
 * requests to create, update and delete elements are not actually implemented in 
 * this class.  Instead it simply calls the corresponding method(s) on the actual list.
 * </P>
 * <P>
 * Changes are communicated to this object via the {@link NotifyingListListener} 
 * interface.  When such changes occur, the SublistInclusionTest is used to determine
 * if the event is of interest to this object's clients.  "Uninteresting" events are 
 * ignored, while the rest are forwarded onto clients.
 * </P>
 * 
 * <P>
 * The list preserves the order of the elements in the underlying list.  Therefore,
 * you will not see elements [1,2,3,4,5] in the actual list, and then [2,5,1] in 
 * this list.
 * </P>
 * 
 * <P>
 * A reasonably sane implementation of List should provide the following:
 * </P>
 * <UL>
 * <LI>size</LI>
 * <LI>get(int)</LI>
 * <LI>set(int, value)</LI>
 * <LI>add(int, value)</LI>
 * <LI>remove(int)</LI>
 * </UL>
 * 
 * @author cnh
 * @param <E>
 */
public class SublistProxy<E> extends IndirectList<E>
{
	static public SublistInclusionTest ALWAYS_INCLUDE = new SublistInclusionTest() {
		public boolean include(Object o)
		{
			return true;
		}
	};
	
	protected SublistInclusionTest<E> myTest;
	
	public SublistProxy(SublistInclusionTest test, NotifyingList<E> actual)
	{
		initialize(test, actual);
	}
	
	protected void initialize(SublistInclusionTest<E> test, NotifyingList<E> actual)
	{
		myTest = test;
		initialize(actual);
	}
	
	
	
	
	@Override
	protected void processUpdate(int actualIndex, E element)
	{
		Integer index = myIdToVirtual.get(actualIndex);
		boolean include = myTest.include(element);
		
		//
		// if the element was not visible, and it still isn't ignore the event
		//
		if (!include && null == index)
		{}
		
		//
		// if the element was not visible, but now it is, process like an insert
		//
		else if (include && null == index)
		{
			processInsert(index, element);
		}
		
		//
		// if the element was visible, but now it is not, process like an delete
		//
		else if (!include && null != index)
		{
			processDelete(actualIndex, element);
		}
		
		//
		// otherwise, it was and still is visible, but the data may have changed, 
		// process as a true update
		//
		else
		{
			myHelper.fireUpdate(index, element);
		}		
	}

	public void processDelete(ListEvent event)
	{
		E element = (E) event.element;
		if (myTest.include(element))
		{
			int index = myIdToVirtual.get(event.index);
			myIdToVirtual.remove(event.index);
			myVirtualList.remove(index);
			myHelper.fireDelete(index, event.element);
		}
	}
	
	public void addListListener(ListListener listener)
	{
		myHelper.addListener(listener);
	}
	
	public void removeListListener (ListListener listener)
	{
		myHelper.removeListener(listener);
	}
	
	
	@Override
	public void addListener(NotifyingListListener listener)
	{
		myHelper.addListener(listener);
	}


	protected List<Integer> translate(int[] rows)
	{
		List<Integer> list = new ArrayList<Integer>();
		for (int index = 0; index < rows.length; index++)
		{
			list.add(myVirtualList.get(rows[index]));
		}
		
		return list;
	}

	@Override
	public boolean removeListener(NotifyingListListener listener)
	{
		return myHelper.removeListener(listener);
	}
	
	
	public void setInclusionTest(SublistInclusionTest<E> test)
	{
		myTest = test;
		refresh();
	}

	@Override
	protected int findIndexForInsert(E e, int actualIndex)
	{
		int index;
		
		if (myTest.include(e))
		{
			index = size();
		}
		else
		{
			index = -1;
		}
		
		return index;
	}

	@Override
	public Object getDataObject()
	{
		return myActual.getDataObject();
	}
}
