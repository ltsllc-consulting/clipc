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

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.lts.swing.table.TableModelHelper;

public class BridgeAdaptor 
	extends TableModelListenerAdaptor 
	implements TableModel
{
	protected TableModelHelper myTableModelHelper;
	protected TableModel myTableModel;

	public BridgeAdaptor(TableModel model)
	{
		initialize(model);
	}
	
	protected void initialize(TableModel tableModel)
	{
		myTableModel = tableModel;
		myTableModel.addTableModelListener(this);
		myTableModelHelper = new TableModelHelper(this);
	}

	public void addTableModelListener(TableModelListener l)
	{
		myTableModelHelper.addListener(l);
	}

	public Class<?> getColumnClass(int columnIndex)
	{
		return myTableModel.getColumnClass(columnIndex);
	}

	public int getColumnCount()
	{
		return myTableModel.getColumnCount();
	}

	public String getColumnName(int columnIndex)
	{
		return myTableModel.getColumnName(columnIndex);
	}

	public int getRowCount()
	{
		return myTableModel.getRowCount();
	}

	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return myTableModel.getValueAt(rowIndex, columnIndex);
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return myTableModel.isCellEditable(rowIndex, columnIndex);
	}

	public void removeTableModelListener(TableModelListener l)
	{
		myTableModel.removeTableModelListener(l);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex)
	{
		myTableModel.setValueAt(value, rowIndex, columnIndex);
	}
}
