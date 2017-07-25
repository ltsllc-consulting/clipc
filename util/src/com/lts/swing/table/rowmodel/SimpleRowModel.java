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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

abstract public class SimpleRowModel implements RowModel
{
	public class SimpleRowData
	{
		public String name;
		public Class columnClass;
		public boolean editable;
	}
	
	protected SimpleRowData[] myColumns;
	
	public SimpleRowData[] buildSimpleRowData(Object[][] spec)
	{
		SimpleRowData[] columns = new SimpleRowData[spec.length];
		
		int index = 0;
		for (Object[] row : spec)
		{
			SimpleRowData rowData = new SimpleRowData();
			rowData.name = (String) row[0];
			rowData.columnClass = (Class) row[1];
			rowData.editable = (Boolean) row[2];
			columns[index] = rowData;
			index++;
		}
		
		return columns;
	}
	
	
	public SimpleRowModel()
	{
		initialize(null);
	}
	
	public SimpleRowModel(Object[][] spec)
	{
		initialize(spec);
	}
	
	
	protected void initialize(Object[][] spec)
	{
		if (null != spec)
		{
			myColumns = buildSimpleRowData(spec);
		}
	}

	@Override
	public Class getColumnClass(int column)
	{
		return myColumns[column].columnClass;
	}

	@Override
	public int getColumnCount()
	{
		return myColumns.length;
	}

	@Override
	public String getColumnName(int column)
	{
		return myColumns[column].name;
	}

	@Override
	public String[] getColumnNames()
	{
		List<String> list = new ArrayList<String>();
		for(SimpleRowData row : myColumns)
		{
			list.add(row.name);
		}
		
		return (String[]) list.toArray();
	}

	@Override
	public boolean isColumnEditable(int col)
	{
		return myColumns[col].editable;
	}


	@Override
	public int compareRows(Object o1, Object o2)
	{
		return 0;
	}


	@Override
	public Comparator getComparator()
	{
		return null;
	}
}
