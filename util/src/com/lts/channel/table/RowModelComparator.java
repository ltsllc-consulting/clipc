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

import java.util.Comparator;

import com.lts.swing.table.rowmodel.RowModel;

public class RowModelComparator implements Comparator
{
	private RowModel myRowModel;
	
	public RowModelComparator (RowModel rowModel)
	{
		myRowModel = rowModel;
	}
	
	
	public int compare(Object o1, Object o2)
	{
		return myRowModel.getComparator().compare(o1, o2);
	}
}
