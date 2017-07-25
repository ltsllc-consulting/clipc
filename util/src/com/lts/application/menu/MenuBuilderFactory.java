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
package com.lts.application.menu;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBuilderFactory
{
	public enum ItemTypes {
		action,
		radio,
		title
	}
	
	private JMenuBar myMenuBar;
	private int myCurrentRow;

	public JMenuBar getMenuBar()
	{
		return myMenuBar;
	}

	public void setMenuBar(JMenuBar menuBar)
	{
		myMenuBar = menuBar;
	}

	public int getCurrentRow()
	{
		return myCurrentRow;
	}

	public void setCurrentRow(int currentRow)
	{
		myCurrentRow = currentRow;
	}

	public JMenuBar basicMenuBar(Object[][][] spec)
	{
		myMenuBar = new JMenuBar();
		
		for (int i = 0; i < spec.length; i++)
		{
			JMenu menu = buildMenu(spec[i]);
			myMenuBar.add(menu);
		}
		
		return myMenuBar;
	}

	protected JMenu buildMenu(Object[][] row)
	{
		String title = (String) row[0][0];
		JMenu menu = new JMenu(title);
		
		for (int i = 1; i < row.length; i++)
		{
			myCurrentRow = i;
			JMenuItem item = buildMenuItem(row[i]);
			menu.add(item);
		}
		
		return menu;
	}

	protected JMenuItem buildMenuItem(Object[] row)
	{
		ItemTypes type = determineType(row);
		MenuItemSpec ispec = null;
		
		switch (type)
		{
			case action :
				ispec = new ActionMenuItem();
				break;
				
			case radio :
				ispec = new RadioButtonMenuItem();
				break;

			default :
				throw new RuntimeException("unrecognized menu item specification");
		}
		
		ispec.parseSpecification(row);
		JMenuItem item = ispec.buildItem();
		return item;
	}

	protected ItemTypes determineType(Object[] row)
	{
		ItemTypes type = null;

		if (isActionItem(row))
			type = ItemTypes.action;
		else if (isRadioItem(row))
			type = ItemTypes.radio;
		else
		{
			StringBuilder sb = new StringBuilder();
			
			boolean first = true;
			for (Object o : row)
			{
				if (first)
					first = false;
				else
					sb.append(", ");
				
				sb.append(o);
			}
			
			String msg = 
				"Unrecognized item specification in row " + getCurrentRow() 
				+ ", data: " + sb.toString();
			throw new RuntimeException(msg);
		}
		
		return type;
	}

	private boolean isRadioItem(Object[] row)
	{
		boolean hasRadio = false;
		boolean hasAction = false;
		
		for (Object o : row)
		{
			if (null == o)
				continue;
			
			Class clazz = o.getClass();
			
			if (clazz == String.class)
			{
				String s = (String) o;
				hasRadio = s.equalsIgnoreCase(RadioButtonMenuItem.STR_RADIO);
			}
			else if (clazz == ActionListener.class)
			{
				hasAction = true;
			}
		}
		
		return hasRadio && hasAction;
	}

	private boolean isActionItem(Object[] row)
	{
		return
				(row.length == 2 || row.length == 3)
				&& row[0] != null
				&& row[0].getClass() == String.class
				&& row[1] != null
				&& row[1].getClass() == ActionListener.class; 
	}

	private Object[][][] toSpecType(Object spec)
	{
		Class expectedClass = Object[][][].class;
		
		if (null == spec)
		{
			throw new RuntimeException("null menu bar specification");
		}
		
		if (spec.getClass() != expectedClass)
		{
			String msg =
				"menu bar specification is not of correct type.  "
				+ "Expected: " + expectedClass
				+ ", actual: " + spec.getClass();
			
			throw new RuntimeException(msg);
		}

		return (Object[][][]) spec;
	}
	
	public JMenuBar buildMenuBar(Object o)
	{
		Object[][][] spec = toSpecType(o);
		JMenuBar menuBar = basicMenuBar(spec);
		return menuBar;
	}
	
	static public boolean isValidSpecification(Object spec[])
	{	
		return null != spec && spec.getClass() == Object[][][].class;
	}


}
