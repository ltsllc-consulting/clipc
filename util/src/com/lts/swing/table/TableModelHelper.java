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
package com.lts.swing.table;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.lts.event.ListenerHelper;

public class TableModelHelper extends ListenerHelper
{
	protected TableModel mySource;
	
	public TableModelHelper(TableModel source)
	{
		mySource = source;
	}
	
	public void setSource(TableModel source)
	{
		mySource = source;
	}
	
	
	@Override
	public void notifyListener(Object olistener, int type, Object data)
	{
		TableModelListener listener = (TableModelListener) olistener;
		TableModelEvent event = (TableModelEvent) data;
		listener.tableChanged(event);
	}

	public void fireRowAdded(int index)
	{
		TableModelEvent event = new TableModelEvent(mySource, index, index,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		fire(event);
	}
	
	public void fireRowRemoved(int index)
	{
		TableModelEvent event = new TableModelEvent(mySource, index, index,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
		fire(event);
	}
	
	
	public void fireRowUpdated(int index)
	{
		TableModelEvent event = new TableModelEvent(mySource, index, index,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE);
		fire(event);
	}


	public void fireTableChanged()
	{
		TableModelEvent event = new TableModelEvent(mySource);
		fire(event);
	}

	public void fireCellChanged(int rowIndex, int column)
	{
		TableModelEvent event = new TableModelEvent(mySource, rowIndex, rowIndex, column);
		fire(event);
	}

}
