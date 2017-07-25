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
package com.lts.application.data.coll;

import com.lts.event.ListenerHelper;

public class ADLListenerHelper extends ListenerHelper
{
	@Override
	public void notifyListener(Object olistener, int type, Object data)
	{
		ApplicationDataListListener listener = (ApplicationDataListListener) olistener;
		ApplicationDataListEvent event = (ApplicationDataListEvent) data;
		listener.eventOccurred(event);
	}
	
	
	public void fireAllChanged (ApplicationDataList list)
	{
		ApplicationDataListEvent.EventType type = ApplicationDataListEvent.EventType.AllChanged;
		ApplicationDataListEvent event = new ApplicationDataListEvent(type, -1, list);
		fire(event);
	}
	
	
	public void fireCreate (int index, ApplicationDataList list)
	{
		ApplicationDataListEvent.EventType type = ApplicationDataListEvent.EventType.Create;
		ApplicationDataListEvent event = new ApplicationDataListEvent(type, index, list);
		fire(event);
	}
	
	
	public void fireDelete (int index, ApplicationDataList list)
	{
		ApplicationDataListEvent.EventType type = ApplicationDataListEvent.EventType.Delete;
		ApplicationDataListEvent event = new ApplicationDataListEvent(type, index, list);
		fire(event);
	}
	
	
	public void fireUpdate (int index, ApplicationDataList list)
	{
		ApplicationDataListEvent.EventType type = ApplicationDataListEvent.EventType.Update;
		ApplicationDataListEvent event = new ApplicationDataListEvent(type, index, list);
		fire(event);
	}
}
