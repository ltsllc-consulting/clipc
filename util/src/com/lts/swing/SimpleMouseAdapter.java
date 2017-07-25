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
//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the util library.
//
//  The util library is free software; you can redistribute it and/or modify it
//  under the terms of the Lesser GNU General Public License as published by
//  the Free Software Foundation; either version 2.1 of the License, or (at
//  your option) any later version.
//
//  The util library is distributed in the hope that it will be useful, but
//  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
//  or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
//  License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the util library; if not, write to the Free Software Foundation,
//  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.swing;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JPopupMenu;


/**
 * This class simplifies mouse interaction by providing default implementations
 * for the various required methods.
 * 
 * <P>
 * Clients can use this class directly to display popup menus by simply passing 
 * the menu to the constructor.  To define doubleClick behavior, the doubleClick
 * method should be overridden.
 * 
 * @author cnh
 */
public class SimpleMouseAdapter extends SimpleMouseListener
{
	protected JPopupMenu menu;
	protected ActionListener myDoubleClickListener;
	
	public SimpleMouseAdapter ()
	{}
	
	public SimpleMouseAdapter (JPopupMenu menu)
	{
		this.menu = menu;
	}
	
	
	@Override
	public void showPopup(Component comp, int x, int y)
	{
		if (null != this.menu)
			this.menu.show(comp, x, y);
	}

	@Override
	public void doubleClick()
	{
		if (null != myDoubleClickListener)
			myDoubleClickListener.actionPerformed(null);
	}
	
	
	public static SimpleMouseAdapter createDoubleClick(ActionListener listener)
	{
		SimpleMouseAdapter adapt = new SimpleMouseAdapter();
		adapt.myDoubleClickListener = listener;
		return adapt;
	}
}
