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

import javax.swing.JMenuItem;

import com.lts.swing.keyboard.InputKey;

public class ActionMenuItem extends MenuItemSpec
{
	private ActionListener myAction;
	private InputKey myInputKey;
	
	public ActionListener getAction()
	{
		return myAction;
	}

	public void setAction(ActionListener action)
	{
		myAction = action;
	}

	public JMenuItem buildItem()
	{
		JMenuItem item = new JMenuItem(getName());
		item.addActionListener(getAction());
		
		if (null != getInputKey())
		{
			item.setAccelerator(getInputKey().getKeyStroke());
		}
		
		return item;
	}

	public InputKey getInputKey()
	{
		return myInputKey;
	}

	@Override
	public void parseSpecification(Object[] spec)
	{
		for (int i = 0; i < spec.length; i++)
		{
			Object o = spec[i];
			if (null == o)
				continue;
			
			Class clazz = o.getClass();
			processSpecificationItem(o, clazz);
		}
	}

	protected void processSpecificationItem(Object o, Class clazz)
	{
		if (clazz == InputKey.class)
		{
			InputKey ikey = (InputKey) o;
			setKey(ikey);
		}
		else if (clazz == ActionListener.class)
		{
			ActionListener action = (ActionListener) o;
			setAction(action);
		}
		else 
		{
			super.processSpecificationItem(o, clazz);
		}
	}

	public void setKey(InputKey ikey)
	{
		myInputKey = ikey;
	}
}
