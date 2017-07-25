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

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class DynamicTableModel implements TableModel
{
	protected TableModelHelper myHelper;
	protected Class[] myColumnClasses;
	protected String[] myColumnNames;
	protected List<Object[]> myData;
	protected boolean[] myEditable;
	
	public DynamicTableModel()
	{
		initialize();
	}
	
	
	protected void initialize()
	{
		myHelper = new TableModelHelper(this);
		myColumnClasses = new Class[0];
		myColumnNames = new String[0];
		myData = new ArrayList();
		myEditable = new boolean[0];
	}
	
	
	public void addRow (int index, Object[] row)
	{
		if (-1 == index)
			myData.add(row);
		else
			myData.add(index, row);
		
		myHelper.fireRowAdded(index);
	}
	
	
	public void removeRow(int index)
	{
		myData.remove(index);
		myHelper.fireRowRemoved(index);
	}
	
	
	public void update (int index, Object[] row)
	{
		myData.set(index, row);
		myHelper.fireRowUpdated(index);
	}
	
	
	public void addTableModelListener(TableModelListener l)
	{
		myHelper.addListener(l);
	}

	public Class getColumnClass(int columnIndex)
	{
		return String.class;
	}

	public int getColumnCount()
	{
		return myColumnNames.length;
	}

	public String getColumnName(int columnIndex)
	{
		return myColumnNames[columnIndex];
	}

	public int getRowCount()
	{
		return myData.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Object[] row = myData.get(rowIndex);
		return row[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return myEditable[columnIndex];
	}
	
	public void setCellEditable(int rowIndex, int columnIndex, boolean editable)
	{
		myEditable[columnIndex] = editable;
	}

	public void removeTableModelListener(TableModelListener l)
	{
		myHelper.removeListener(l);
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		Object[] row = myData.get(rowIndex);
		row[columnIndex] = aValue;
	}


	public void setColumnNames(String[] columnNames)
	{
		myColumnNames = columnNames;
		
		myColumnClasses = new Class[columnNames.length];
		for (int i = 0; i < myColumnClasses.length; i++)
		{
			myColumnClasses[i] = String.class;
		}
		
		myEditable = new boolean[columnNames.length];
		for (int i = 0; i < myEditable.length; i++)
		{
			myEditable[i] = false;
		}
	}
	
	
	public void setColumnClasses(Class[] columnClasses)
	{
		myColumnClasses = columnClasses;
	}
	
	public void setColumnsEditable(boolean[] editable)
	{
		myEditable = editable;
	}
	
	public void setData (Object[][] data)
	{
		myData = new ArrayList<Object[]>(data.length);
		
		for (int i = 0; i < data.length; i++)
		{
			myData.add(data[i]);
		}
		
		myHelper.fireTableChanged();
	}
	
	public void clear()
	{
		myData = new ArrayList<Object[]>();
		myHelper.fireTableChanged();
	}


	public void setData(List<Object[]> list)
	{
		myData = list;
		myHelper.fireTableChanged();
	}
	
	
}
