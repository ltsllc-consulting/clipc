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
package com.lts.util.notifyinglist;

import java.util.AbstractList;
import java.util.HashMap;
import java.util.Map;

/**
 * A NotifyingList that uses a NotifyingList as its actual list.
 * <P>
 * This class implements a "forward and listen" approach to changes. When asked to
 * add/remove/update an element, it forwards the request onto the actual list.
 * <P>
 * <P>
 * When the actual list changes, it sends out a {@link ListEvent} which this class will
 * listen for and update its own list to its clients. This is the "listen" part of
 * "forward and listen"
 * </P>
 * 
 * @author cnh
 * @param <E>
 */
abstract public class AbstractNotifyingProxyList<E> 
	extends AbstractList<E> implements NotifyingList<E>, NotifyingListListener
{
	abstract public Integer getIdFor(E e);

	protected NotifyingList<E> myActual;
	protected NotifyingListHelper myHelper;
	protected Map<Integer,Integer> myActualToVirtual;
	
	protected void initialize(NotifyingList<E> actual)
	{
		myActual = actual;
		myActual.addListener(this);
		myHelper = new NotifyingListHelper();
		myActualToVirtual = new HashMap<Integer, Integer>();
	}
	
	@Override
	public E get(int index)
	{
		return myActual.get(index);
	}

	@Override
	public int size()
	{
		return myActual.size();
	}

	@Override
	public void addListener(NotifyingListListener listener)
	{
		myHelper.addListener(listener);
	}

	@Override
	public boolean removeListener(NotifyingListListener listener)
	{
		return myHelper.removeListener(listener);
	}

	@Override
	public void listEvent(ListEvent event)
	{
		switch (event.eventType)
		{
			case AllChanged :
				processAllChanged();
				break;
				
			case Delete :
				processDelete(event, event.index, event.element);
				break;
				
			case Insert :
				processInsert(event, event.index, event.element);
				break;
				
			case Update :
				processUpdate(event, event.index, event.element);
		}
	}

	
	protected void processUpdate(ListEvent event, int actual, Object element)
	{
		Integer virtual = actualToVirtual(actual);
		if (null == virtual)
			return;
		
		
	}

	public Integer actualToVirtual(int actual)
	{
		return myActualToVirtual.get(actual);
	}

	protected void processInsert(ListEvent event, int actualIndex, Object element)
	{
		
	}

	protected void processDelete(ListEvent event, int index, Object element)
	{
		// TODO Auto-generated method stub
		
	}

	protected void processAllChanged()
	{
		// TODO Auto-generated method stub
		
	}

	
	public void refresh()
	{
	}

	public Object getDataObject()
	{
		return myActual.getDataObject();
	}

	public void nodeChanged(E node)
	{
	}
}
