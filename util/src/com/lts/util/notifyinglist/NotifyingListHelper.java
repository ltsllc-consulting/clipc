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

import com.lts.event.ListenerHelper;
import com.lts.util.notifyinglist.ListEvent.EventType;

public class NotifyingListHelper extends ListenerHelper
{

	@Override
	public void notifyListener(Object olistener, int type, Object data)
	{
		NotifyingListListener listener = (NotifyingListListener) olistener;
		ListEvent event = (ListEvent) data;
		listener.listEvent(event);
	}

	public void fireInsert (int index, Object element)
	{
		ListEvent event = new ListEvent(EventType.Insert, index, element);
		fire(event);
	}
	
	public void fireUpdate (int index, Object element)
	{
		ListEvent event = new ListEvent(EventType.Update, index, element);
		fire(event);
	}
	
	public void fireDelete (int index, Object element)
	{
		ListEvent event = new ListEvent(EventType.Delete, index, element);
		fire(event);
	}
	
	public void fireAllChanged()
	{
		ListEvent event = new ListEvent(EventType.AllChanged);
		fire(event);
	}
}
