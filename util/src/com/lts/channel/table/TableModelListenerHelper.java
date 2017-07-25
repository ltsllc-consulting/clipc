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
package com.lts.channel.table;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.lts.event.ListenerHelper;

public class TableModelListenerHelper extends ListenerHelper
{

	@Override
	public void notifyListener(Object o, int type, Object data)
	{
		TableModelListener listener = (TableModelListener) o;
		TableModelEvent event = (TableModelEvent) data;
		listener.tableChanged(event);
	}

	public void fireRowInserted(TableModel source, int row)
	{
		int col = TableModelEvent.ALL_COLUMNS;
		int eventType = TableModelEvent.INSERT;
		
		TableModelEvent event = new TableModelEvent(source, row, row, col, eventType);
		
		fire(event);
	}

	public void fireRowChanged(TableModel source, int row)
	{
		TableModelEvent event = new TableModelEvent(source,row);
		fire(event);
	}
	
	
	public void fireRowDeleted(TableModel source, int row)
	{
		int col = TableModelEvent.ALL_COLUMNS;
		int type = TableModelEvent.DELETE;
		TableModelEvent event = new TableModelEvent(source, row, row, col, type);
		
		fire(event);
	}
	
	public void fireAllChanged(TableModel source)
	{
		TableModelEvent event = new TableModelEvent(source);
		fire(event);
	}

	public void fireValueChanged(TableModel source, int rowIndex, int columnIndex)
	{
		TableModelEvent event;
		int type = TableModelEvent.UPDATE;
		event = new TableModelEvent(source, rowIndex, rowIndex, columnIndex, type);
		
		fire(event);
	}
}
