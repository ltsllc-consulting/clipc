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

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class TableModelListenerAdaptor implements TableModelListener
{
	@SuppressWarnings("unused")
	private TableModelEvent myEvent;
	
	public void tableChanged(TableModelEvent e)
	{
		myEvent = e;
		
		switch(e.getType())
		{
			case TableModelEvent.DELETE :
				delete(e.getFirstRow(), e.getLastRow());
				break;
				
			case TableModelEvent.INSERT :
				insert(e.getFirstRow(), e.getLastRow());
				break;
				
			case TableModelEvent.UPDATE :
			{
				//
				// A complete change is represented by an update with a first row
				// of Integer.MIN_VALUE and a last row of Integer.MAX_VALUE
				//
				if (e.getFirstRow() == Integer.MIN_VALUE
						&& e.getLastRow() == Integer.MAX_VALUE)
				{
					allChanged();
				}
				else
				{
					update(e.getFirstRow(), e.getLastRow());
				}
				break;
			}
			
			default :
				break;
		}
	}

	protected void update(int firstRow, int lastRow)
	{}

	protected void delete(int firstRow, int lastRow)
	{}

	protected void insert(int firstRow, int lastRow)
	{}
	
	protected void allChanged()
	{}

}
