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
package com.lts.swing.table.rowmodel.tablemodel;

import com.lts.event.ListenerHelper;

public class RowModelTableModelListenerHelper extends ListenerHelper
{

	@Override
	public void notifyListener(Object listener, int type, Object data)
	{
		RowModelTableModelEvent event = (RowModelTableModelEvent) data;
		RowModelTableModelListener l = (RowModelTableModelListener) listener;
		l.eventOccurred(event);
	}
	
	
	public void rowAdded (int row)
	{
		RowModelTableModelEvent.EventType etype;
		etype = RowModelTableModelEvent.EventType.rowAdded;
		RowModelTableModelEvent event;
		event = new RowModelTableModelEvent(etype, row);
		fire(event);
	}
	
	
	public void rowChanged (int row)
	{
		RowModelTableModelEvent.EventType etype;
		etype = RowModelTableModelEvent.EventType.rowChanged;
		RowModelTableModelEvent event;
		event = new RowModelTableModelEvent(etype, row);
		fire(event);
	}

	public void rowDelete (int row)
	{
		RowModelTableModelEvent.EventType etype;
		etype = RowModelTableModelEvent.EventType.rowRemoved;
		RowModelTableModelEvent event;
		event = new RowModelTableModelEvent(etype, row);
		fire(event);
	}

	public void allChanged (int row)
	{
		RowModelTableModelEvent.EventType etype;
		etype = RowModelTableModelEvent.EventType.allChanged;
		RowModelTableModelEvent event;
		event = new RowModelTableModelEvent(etype, row);
		fire(event);
	}
}
