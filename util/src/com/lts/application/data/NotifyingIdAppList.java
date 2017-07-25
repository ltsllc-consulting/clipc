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
package com.lts.application.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import com.lts.util.notifyinglist.ListEvent;
import com.lts.util.notifyinglist.NotifyingList;
import com.lts.util.notifyinglist.NotifyingListListener;
import com.lts.util.notifyinglist.sorted.IndirectList;
import com.lts.util.notifyingmap.NotifyingMap;
import com.lts.util.notifyingmap.NotifyingMapListener;

/**
 * This class makes a NotifyingMap appear to be a NotifyingList for classes that 
 * extend IdApplicationDataList.
 * 
 * <P>
 * The class uses the ID of the elements as the order in the virtual list.
 * </P>
 * 
 * @author cnh
 *
 */
public class NotifyingIdAppList<E extends IdApplicationDataElement>
	extends IndirectList<E>
	implements NotifyingMap<Integer, IdApplicationDataElement>, NotifyingListListener, 
		NotifyingList<E>, IdListListener 
{
	protected IdApplicationDataList<IdApplicationDataElement> myList;
	
	public NotifyingIdAppList(IdApplicationDataList<E> list)
	{
		initialize(list);
	}
	
	protected void initialize(IdApplicationDataList<E> list)
	{
		super.initialize(list);
		myList = (IdApplicationDataList<IdApplicationDataElement>) list;
		myList.addIdListListener(this);
	}
	
	protected void initialize()
	{
	}
	
	@Override
	public boolean addListener(NotifyingMapListener listener)
	{
		return myHelper.addListener(listener);
	}
	
	@Override
	public boolean removeListener(NotifyingMapListener listener)
	{
		return myHelper.removeListener(listener);
	}

	@Override
	public IdApplicationDataElement get(Integer key)
	{
		return myList.idToElement(key);
	}

	
	@Override
	public synchronized E get(int virtual)
	{
		int actual = myVirtualList.get(virtual);
		return (E) myList.idToElement(actual);
	}

	@Override
	public Set<Integer> keySet()
	{
		ArrayList<Integer> idlist = new ArrayList<Integer>(myList.size());
		
		for (IdApplicationDataElement e : myList)
		{
			idlist.add(e.getId());
		}
		
		ImmutableSet<Integer> idset = new ImmutableSet<Integer>(idlist);
		return idset;
	}

	@Override
	public IdApplicationDataElement remove(Integer key)
	{
		return myList.remove(key.intValue());
	}

	@Override
	public void update(Integer key, IdApplicationDataElement value)
	{
		myList.update(value);
	}

	@Override
	public void listEvent(ListEvent event)
	{
	}

	protected void processUpdate(ListEvent event, int index, Object element)
	{
	}

	protected void processInsert(ListEvent event, int index, Object element)
	{
		super.processInsert(index, (E) element);
	}

	protected void processDelete(ListEvent event, int index, Object element)
	{
		super.processDelete(index, (E) element);
	}

	protected void processAllChanged(ListEvent event)
	{
	}

	@Override
	protected int findIndexForInsert(IdApplicationDataElement e, int actualIndex)
	{
		int location = Collections.binarySearch(myVirtualList, e.getId());
		
		//
		// returnValue = -1 - insertionPoint 
		// returnValue + insertionPoint = -1
		// insertionPoint = -1 - returnValue
		// insertionPoint = (-1) * returnValue - 1
		//
		if (location < 0)
		{
			location = (-1) * location - 1;
		}
		return location;
	}

	@Override
	synchronized public void refresh()
	{
		myVirtualList = new ArrayList<Integer>();
		myIdToVirtual = new HashMap<Integer, Integer>();
		
		for (int virtual = 0; virtual < myActual.size(); virtual++)
		{
			IdApplicationDataElement e = myActual.get(virtual);
			myVirtualList.add(e.getId());
			myIdToVirtual.put(virtual, e.getId());
		}
		
		myHelper.fireAllChanged();
	}

	@Override
	public void idListEvent(IdListEvent event)
	{
		switch (event.event)
		{
			case Add :
				processIdAdd(event, event.element);
				break;
				
			case AllChanged :
				processIdAllChanged(event);
				break;
				
			case Delete :
				processIdDelete(event, event.element);
				break;
				
			case Update :
				processIdUpdate(event, event.element);
				break;
		}
	}

	protected void processIdUpdate(IdListEvent event, IdApplicationDataElement element)
	{
		int virtual = myIdToVirtual.get(element.getId());
		myHelper.fireUpdate(virtual, element);
	}

	protected void processIdDelete(IdListEvent event, IdApplicationDataElement element)
	{
		int virtual = myIdToVirtual.get(element.getId());
		myVirtualList.remove(virtual);
		myHelper.fireDelete(virtual, element);
	}

	protected void processIdAllChanged(IdListEvent event)
	{
		refresh();
	}

	protected void processIdAdd(IdListEvent event, IdApplicationDataElement element)
	{
		int virtual = findIndexForInsert(element, element.getId());
		myVirtualList.add(virtual, element.getId());
		myHelper.fireInsert(virtual, element);
	}

	public Object getDataObject()
	{
		return myActual.getDataObject();
	}

	public void nodeChanged(E node)
	{
	}
}
