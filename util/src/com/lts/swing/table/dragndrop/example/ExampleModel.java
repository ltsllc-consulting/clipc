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
package com.lts.swing.table.dragndrop.example;

import java.util.ArrayList;
import java.util.List;

import com.lts.swing.table.dragndrop.TableModelAdaptor;

public class ExampleModel extends TableModelAdaptor
{
	protected List<String> myList;
	
	public String[] getColumnNames()
	{
		String[] temp = new String[] { "col" };
		return temp;
	}
	
	public ExampleModel()
	{
		initialize();
	}

	protected void initialize()
	{
		super.initialize();
		myList = new ArrayList<String>();
	}
	
	public void addString(String s)
	{
		myList.add(s);
		int index = myList.size() - 1;
		myHelper.fireRowAdded(index);
	}
	
	
	public String removeRow(int index)
	{
		String s = myList.get(index);
		myList.remove(index);
		myHelper.fireRowRemoved(index);
		return s;
	}
	
	public String getString(int index)
	{
		return myList.get(index);
	}
	
	@Override
	public int getRowCount()
	{
		return myList.size();
	}

	@Override
	public Object getValueAt(int row, int col)
	{
		return myList.get(row);
	}

	@Override
	public void setValueAt(Object value, int row, int col)
	{
		myList.set(row, value.toString());
	}

	public void insertString(String s, int index)
	{
		myList.add(index, s);
		myHelper.fireRowAdded(index);
	}

	public void insertString(int index, String s)
	{
		myList.add(index, s);
		myHelper.fireRowAdded(index);
	}

}
