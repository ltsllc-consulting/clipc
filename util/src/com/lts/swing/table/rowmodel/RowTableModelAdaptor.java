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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.lts.swing.table.TableModelHelper;

/**
 * A class that creates a table model that uses a RowModel to render the row
 * data and that depends on another class to hold the data.
 * 
 * <H2>Abstract Class</H2>
 * Concrete subclasses must implement the following methods:
 * <UL>
 * <LI>{@link #getRowData(int)}</LI>
 * <LI>{@link #basicGetRowCount()}</LI>
 * </UL>
 * 
 * <H2>Description</H2>
 * The primary difference between this class and {@link AbstractTableModel} is
 * that the other class assumes that the data is held inside the instance.  This 
 * class, on the other hand, uses the {@link #getRowData(int row)} to get the 
 * data, then uses the {@link RowModel} to render it.
 * 
 * @author cnh
 *
 */
abstract public class RowTableModelAdaptor implements TableModel
{
	abstract protected Object getRowData(int index);
	abstract protected int basicGetRowCount();
	
	protected TableModelHelper myHelper;
	protected RowModel myRowModel;
	
	protected void initialize(RowModel rowModel)
	{
		myHelper = new TableModelHelper(this);
		myRowModel = rowModel;
	}
	
	
	public void addTableModelListener(TableModelListener l)
	{
		myHelper.addListener(l);
	}

	public Class<?> getColumnClass(int columnIndex)
	{
		return myRowModel.getColumnClass(columnIndex);
	}

	public int getColumnCount()
	{
		return myRowModel.getColumnCount();
	}

	public String getColumnName(int columnIndex)
	{
		return myRowModel.getColumnName(columnIndex);
	}

	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Object data = getRowData(rowIndex);
		return myRowModel.getValueAt(columnIndex, data);
	}

	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return myRowModel.isColumnEditable(columnIndex);
	}

	public void removeTableModelListener(TableModelListener l)
	{
		myHelper.removeListener(l);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex)
	{
		Object row = getRowData(rowIndex);
		myRowModel.setValueAt(rowIndex, row, columnIndex, value);
	}

	public int getRowCount()
	{
		return basicGetRowCount();
	}
	
	
	public TableModelHelper getTableModelListenerHelper()
	{
		return myHelper;
	}
}
