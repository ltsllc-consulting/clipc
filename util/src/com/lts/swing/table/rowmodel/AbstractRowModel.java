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

import java.util.Comparator;
import java.util.List;

abstract public class AbstractRowModel implements RowModel
{
	private Comparator myComparator;
	private Class[] myColumnClasses;
	private String[] myNames;
	
	private List myData;
	
	
	
	protected int findIndexOfRow(Object row)
	{
		if (null == myData)
		{
			String msg = "This class of RowModel, " 
				+ getClass().getName()
				+ ", does not support findIndexOfRow";
			
			throw new RuntimeException(msg);
		}
		
		for (int i = 0; i < myData.size(); i++)
		{
			if (myData.get(i).equals(row))
				return i;
		}
		
		return -1;
	}

	protected void initialize(Comparator comp, Class[] columns, String[] names, List data)
	{
		myComparator = comp;
		myColumnClasses = columns;
		myNames = names;
		myData = data;
	}
	
	protected void initialize(Comparator comp, Class[] columns, String[] names)
	{
		initialize(comp, columns, names, null);
	}

	public int compareRows(Object o1, Object o2)
	{
		return myComparator.compare(o1, o2);
	}

	public Class getColumnClass(int column)
	{
		return myColumnClasses[column];
	}

	public String[] getColumnNames()
	{
		return myNames;
	}

//	public static void resizeColumns(JTable table)
//	{
//		TableModel model = table.getModel();
//		if (null == model)
//			return;
//		
//		if (!(model instanceof RowModelTableModelAdaptor))
//			return;
//		
//		RowModelTableModelAdaptor rowTableModel = (RowModelTableModelAdaptor) model;
//		RowModel rowModel = rowTableModel.getRowModel();
//		int count = rowModel.getColumnNames().length;
//		for (int i = 0; i < count; i++)
//		{
//			
//		}
//	}
}
