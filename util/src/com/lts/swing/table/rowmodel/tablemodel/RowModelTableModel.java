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
package com.lts.swing.table.rowmodel.tablemodel;

import javax.swing.table.TableModel;

/**
 * A TableModel that uses the RowModel framework.  
 * 
 * <P>
 * Instances of this interface provide basic table-like operations.  The data 
 * is a sequence of data that can be accessed by row and column indexes.  The actual
 * organization can be quite different. Regardless of what it was trying to do, 
 * </P>
 * 
 * @author cnh
 *
 */
public interface RowModelTableModel extends TableModel
{
	/**
	 * Get the underlying object at the specified row instead of the values used 
	 * to display the row.
	 * 
	 * @param index the row to get.
	 * @return The object for that row.
	 */
	public Object getRow(int index);
	
	/**
	 * Get the number of rows in the table.
	 * 
	 * @return The number of rows in the table.
	 */
	public int getRowCount();
}
