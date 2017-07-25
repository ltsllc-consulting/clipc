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
package com.lts.util.notifyingmap;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lts.util.notifyinglist.NotifyingList;
import com.lts.util.notifyinglist.NotifyingListHelper;
import com.lts.util.notifyinglist.NotifyingListListener;

abstract public class NotifyingMapToListAdapter<K, E>
	extends AbstractList<E>
	implements NotifyingMapListener, NotifyingList<E>
{
	abstract protected Integer findIndex(Object key);
	
	protected NotifyingMap<K,E> myActual;
	protected NotifyingListHelper myHelper;
	protected Map<K, Integer> myActualToVirtual;
	protected List myVirtual;
	
	protected void initialize()
	{
		myHelper = new NotifyingListHelper();
		refresh();
	}
	
	public void refresh()
	{
		myActualToVirtual = new HashMap<K, Integer>();
		myVirtual = new ArrayList<K>();
		
		for (K key : myActual.keySet())
		{
			Integer virtual = findIndex(key);
			myVirtual.add(virtual, key);
		}
		
		myHelper.fireAllChanged();
	}

	@Override
	public void mapEvent(MapEvent event)
	{
		switch(event.event)
		{
			case Add :
				processAdd(event, event.key, event.value);
				break;
				
			case AllChanged :
				processAllChanged(event);
				break;
				
			case Remove :
				processRemove(event, event.key, event.value);
				break;
				
			case Update :
				processUpdate(event, event.key, event.value);
				break;
		}
	}

	protected void processUpdate(MapEvent event, Object key, Object value)
	{
		int index = findIndex(key);		
		myHelper.fireUpdate(index, value);
	}

	protected Integer keyToVirtual(Object key)
	{
		return myActualToVirtual.get(key);
	}
	
	protected void processRemove(MapEvent event, Object key, Object value)
	{
		Integer index = keyToVirtual(key);
		if (null == index)
			return;
		
		myVirtual.remove(index);
		myHelper.fireDelete(index, value);
	}

	protected void processAllChanged(MapEvent event)
	{
		refresh();
	}

	protected void processAdd(MapEvent event, Object key, Object value)
	{
		Integer index = findIndex(key);
		if (null != index)
		{
			myVirtual.add(index, value);
			myActualToVirtual.put((K)key, index);
			myHelper.fireInsert(index, value);
		}
	}

	@Override
	public E get(int virtual)
	{
		K key = (K) myVirtual.get(virtual);
		return myActual.get(key);
	}

	@Override
	public int size()
	{
		return myVirtual.size();
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

	public Object getDataObject()
	{
		return null;
	}

	public void nodeChanged(E node)
	{
	}

}
