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

import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * A JTable that has several convenience methods.
 * 
 * <H2>Description</H2>
 * 
 * @author cnh
 *
 */
@SuppressWarnings("serial")
public class LTSTable extends JTable
{
	public LTSTable(TableModel tableModel)
	{
		super(tableModel);
	}

	public ModifiableTableModel getModifiableModel()
	{
		return (ModifiableTableModel) getModel();
	}
	
	public void insert (int index, Object data)
	{
		getModifiableModel().insert(index, data);
	}
	
	public void append (Object data)
	{
		int index = getRowCount();
		insert(index, data);
	}
	
	public void replace (int index, Object data)
	{
		getModifiableModel().replace(index, data);
	}
	
	public void delete (int index)
	{
		getModifiableModel().remove(index);
	}
}
