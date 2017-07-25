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
import javax.swing.JPopupMenu;

import com.lts.swing.keyboard.InputKey;

public class ActionMenuBuilder
{
	final static public String STR_RADIO_BUTTON = "radio";
	
	private Object[] myCurrentRow;
	private int myCurrentRowColumn;


	public Object[] getCurrentRow()
	{
		return myCurrentRow;
	}


	public void setCurrentRow(Object[] currentRow)
	{
		myCurrentRow = currentRow;
	}


	public int getCurrentRowColumn()
	{
		return myCurrentRowColumn;
	}


	public void setCurrentRowColumn(int currentRowColumn)
	{
		myCurrentRowColumn = currentRowColumn;
	}


	protected void buildMenu(JMenu menu, Object[][] spec)
	{
		for (int i = 0; i < spec.length; i++)
		{
			if (isSeparator(spec[i]))
				menu.addSeparator();
			else if (isTitle(spec[i]))
			{
				String name = (String) spec[i][0];
				menu.setName(name);
				menu.setText(name);
			}
			else
			{
				JMenuItem item = buildMenuItem(spec[i]);
				menu.add(item);
			}
		}
	}
	
	
	protected void processTextOption(String s)
	{
		if (s.equalsIgnoreCase(STR_RADIO_BUTTON))
			processRadioButton(s);
	}
	
	
	private void processRadioButton(String s)
	{
		
	}


	protected boolean isTitle (Object[] spec)
	{
		return spec.length == 1 && spec[0] instanceof String;
	}
	
	
	protected boolean isSeparator(Object[] objects)
	{
		return objects.length < 1;
	}


	/**
	 * Build a single "leaf" entry in the hierarchy of menu items.
	 * 
	 * @param spec
	 * @return
	 */
	protected JMenuItem buildMenuItem(Object[] spec)
	{
		myCurrentRow = spec;
		
		String title = (String) spec[0];
		ActionListener action = (ActionListener) spec[1];
		JMenuItem item = new JMenuItem();
		item.addActionListener(action);
		item.setName(title);
		item.setText(title);
		
		for (int i = 2; i < spec.length; i++)
		{
			myCurrentRowColumn = i;
			processExtraParameter(item, spec[i]);
		}
		
		return item;
	}


	private void processExtraParameter(JMenuItem item, Object object)
	{
		if (null == object)
		{
			return;
		}
		else if (object.getClass() == InputKey.class)
		{
			InputKey ikey = (InputKey) object;
			processInputKey(ikey, item);
		}
	}


	private void processInputKey(InputKey keyStroke, JMenuItem item)
	{
		item.setAccelerator(keyStroke.getKeyStroke());
	}


	public JMenuBar buildMenuBar(Object[][][] spec)
	{
		JMenuBar menuBar = new JMenuBar();
		
		for (int i = 0; i < spec.length; i++)
		{
			Object[][] subSpec = spec[i];
			JMenu menu = new JMenu();
			buildMenu(menu, subSpec);
			menuBar.add(menu);
		}
		
		return menuBar;
	}


	protected void buildMenu (Object menu, Object[][] spec)
	{
		for (int i = 0; i < spec.length; i++)
		{
			if (isSeparator(spec[i]))
				addSeparator(menu);
			else if (isTitle(spec[i]))
			{
				String name = (String) spec[i][0];
				setMenuTitle(menu, name);
			}
			else
			{
				JMenuItem item = buildMenuItem(spec[i]);
				addMenuItem(menu, item);
			}
		}
	}
	
	
	protected void addMenuItem (Object menuObject, JMenuItem item)
	{
		if (menuObject instanceof JMenu)
		{
			JMenu menu = (JMenu) menuObject;
			menu.add(item);
		}
		else if (menuObject instanceof JPopupMenu)
		{
			JPopupMenu menu = (JPopupMenu) menuObject;
			menu.add(item);
		}
		else
		{
			String msg = "Unrecognized menu class: " + menuObject.getClass().getName();
			throw new RuntimeException(msg);
		}
	}
	
	protected void addSeparator (Object menuObject)
	{
		if (menuObject instanceof JMenu)
		{
			JMenu menu = (JMenu) menuObject;
			menu.addSeparator();
		}
		else if (menuObject instanceof JPopupMenu)
		{
			JPopupMenu menu = (JPopupMenu) menuObject;
			menu.addSeparator();
		}
		else
		{
			String msg = "Unrecognized menu class: " + menuObject.getClass().getName();
			throw new RuntimeException(msg);
		}
	}
	
	protected void setMenuTitle (Object menuObject, String title)
	{
		if (menuObject instanceof JMenu)
		{
			JMenu menu = (JMenu) menuObject;
			menu.setText(title);
			menu.setName(title);
		}
		else if (menuObject instanceof JPopupMenu)
		{
			; // silently ignore
		}
		else
		{
			String msg = "Unrecognized menu class: " + menuObject.getClass().getName();
			throw new RuntimeException(msg);
		}
	}
	
	
	public JPopupMenu buildPopup(Object[][] spec)
	{
		JPopupMenu popup = new JPopupMenu();
		buildMenu(popup, spec);
		return popup;
	}

}
