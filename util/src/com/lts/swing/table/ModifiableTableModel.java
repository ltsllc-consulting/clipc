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

import javax.swing.table.TableModel;

/**
 * A table model that can do adds, deletes, etc.
 * 
 * <H2>Description</H2>
 * This interface extends the notion of TableModels to include adding and removing
 * rows.
 * 
 * <P>
 * This class is designed to work with LTSTable, though it can be used with regular 
 * JTables.
 * </P>
 * 
 * @author cnh
 *
 */
public interface ModifiableTableModel extends TableModel
{
	public void insert (int index, Object data);
	public void remove (int index);
	public void replace(int index, Object data);
	public Object getRowData(int index);
}
