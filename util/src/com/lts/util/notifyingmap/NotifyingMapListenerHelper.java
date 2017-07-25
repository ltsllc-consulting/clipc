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

import com.lts.event.ListenerHelper;
import com.lts.util.notifyingmap.MapEvent.MapEventType;

public class NotifyingMapListenerHelper<K,V> extends ListenerHelper
{
	protected NotifyingMap<K,V> myMap;

	public NotifyingMap<K,V> getMap()
	{
		return myMap;
	}
	
	public void setMap(NotifyingMap<K,V> map)
	{
		myMap = map;
	}
	
	@Override
	public void notifyListener(Object olistener, int type, Object data)
	{
		MapEvent event = (MapEvent) data;
		NotifyingMapListener listener = (NotifyingMapListener) olistener;
		
		listener.mapEvent(event);
	}
	
	
	public void fireAdd(K key, V value)
	{
		MapEventType type = MapEventType.Add;
		MapEvent event = new MapEvent(type, myMap, key, value);
		fire(event);
	}
	
	public void fireDelete(K key, V value)
	{
		MapEventType type = MapEventType.Remove;
		MapEvent event = new MapEvent(type, myMap, key, value);
		fire(event);
	}
	
	public void fireUpdate(K key, V value)
	{
		MapEventType type = MapEventType.Update;
		MapEvent event = new MapEvent(type, myMap, key, value);
		fire(event);
	}

	public void fireAllChanged()
	{
		MapEvent event = new MapEvent();
		event.event = MapEventType.AllChanged;
		fire(event);
	}
}
