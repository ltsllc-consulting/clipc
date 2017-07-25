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

import com.lts.application.data.coll.ApplicationDataList;
import com.lts.swing.table.rowmodel.tablemodel.SimpleRowModelTableModel;

public class ApplicationDataListRowModel extends SimpleRowModelTableModel
{
	protected class ADLListener 
	{
		
	}
	

	protected ApplicationDataList myList;
	
	public ApplicationDataListRowModel(ApplicationDataList list, RowModel rowModel)
	{
		initialize(list, rowModel);
	}
	
	public void initialize(ApplicationDataList list, RowModel rowModel)
	{
		myList = list;
		setRowModel(rowModel);
		super.initialize();
	}
	
	
	@Override
	public void deleteRow(int rowIndex)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRowCount()
	{
		return myList.getCount();
	}

	@Override
	public Object getRowData(int rowIndex)
	{
		return myList.get(rowIndex);
	}

	@Override
	public void insertRow(int rowIndex, Object rowData)
	{
		throw new UnsupportedOperationException();
	}

}
