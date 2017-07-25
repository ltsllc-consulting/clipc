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
package com.lts.chatterbox.monitor;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.lts.event.ListenerHelper;

public class ListDataHelper extends ListenerHelper
{
	public void notifyListener(Object olistener, Object oevent)
	{
		ListDataListener listener = (ListDataListener) olistener;
		ListDataEvent event = (ListDataEvent) oevent;
		listener.contentsChanged(event);
	}
	
	
	public void fireRemoved(Object source, int index)
	{
		ListDataEvent event = new ListDataEvent(source, ListDataEvent.INTERVAL_REMOVED, index, index);
		fire(event);
	}
	
	public void fireAllChanged(Object source, int size)
	{
		ListDataEvent event = new ListDataEvent(source, ListDataEvent.CONTENTS_CHANGED, 0, size - 1);
		fire(event);
	}
	
	public void fireChanged (Object source, int index)
	{
		ListDataEvent event = new ListDataEvent(source, ListDataEvent.CONTENTS_CHANGED, index, index);
		fire(event);
	}

	public void fireChanged(Object source, int start, int end)
	{
		ListDataEvent event = new ListDataEvent(source, ListDataEvent.CONTENTS_CHANGED, start, end);
		fire(event);
	}
	
}
