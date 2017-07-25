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
package com.lts.swing.table.rowmodel;

import javax.swing.table.TableModel;

import com.lts.swing.table.TableModelHelper;


/**
 * A class that selectively passes events onto a real set of table model listeners.
 * @author cnh
 *
 */
abstract public class TableModelFilter 
	extends TableModelListenerAdaptor
	implements TableModel
{
	public TableModelFilter (TableModel model)
	{
		initialize(model);
	}
	
	protected void initialize(TableModel model)
	{
		myModel = model;
		myModel.addTableModelListener(this);
	}
	
	protected TableModel myModel;
	
	protected TableModelHelper myHelper;

	public void addListener(Object o)
	{
		myHelper.addListener(o);
	}

	public void fireRowAdded(int index)
	{
		myHelper.fireRowAdded(index);
	}

	public void fireRowRemoved(int index)
	{
		myHelper.fireRowRemoved(index);
	}

	public void fireRowUpdated(int index)
	{
		myHelper.fireRowUpdated(index);
	}

	public void fireTableChanged()
	{
		myHelper.fireTableChanged();
	}

	public boolean removeListener(Object o)
	{
		return myHelper.removeListener(o);
	}
	
}
