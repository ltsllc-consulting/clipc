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
package com.lts.util.collection;

import com.lts.event.ListenerHelper;

public class CollectionListenerHelper extends ListenerHelper
{

	@Override
	public void notifyListener(Object listener, int type, Object data)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	public void fireElementAdded (Object element)
	{
		CollectionEvent event = new CollectionEvent();
		event.myElement = element;
		event.myEvent = CollectionEvent.EventType.add;
		fire(event);
	}
	
	public void fireElementRemoved (Object element)
	{
		CollectionEvent event = new CollectionEvent();
		event.myElement = element;
		event.myEvent = CollectionEvent.EventType.remove;
		fire(event);
	}
	
	public void fireElementChanged (Object element)
	{
		CollectionEvent event = new CollectionEvent();
		event.myElement = element;
		event.myEvent = CollectionEvent.EventType.update;
		fire(event);
	}

	public void fireAllChanged ()
	{
		CollectionEvent event = new CollectionEvent();
		event.myEvent = CollectionEvent.EventType.allChanged;
		fire(event);
	}
}
