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

/**
 * A row of data in a JTable that uses an underlying object instead of a matrix
 * of object values or the like.
 * 
 * <P>
 * Many of the methods in this class translate directly into methods that are 
 * defined for the TableModel interface.
 * </P>
 * 
 * @author cnh
 */
public interface RowModel
{
	/**
	 * Get the names of the columns in the table.
	 * 
	 * @return Column names.
	 */
	public String[] getColumnNames();
	
	/**
	 * Get the class for a particular table column.
	 * 
	 * @param column 
	 * @return
	 */
	public Class getColumnClass(int column);
	public Object getValueAt (int column, Object row);
	public void setValueAt (int rowIndex, Object data, int column, Object value);
	public Comparator getComparator();
	public boolean isColumnEditable(int col);
	public int getColumnCount();
	public String getColumnName(int column);
	public int compareRows(Object o1, Object o2);
}
