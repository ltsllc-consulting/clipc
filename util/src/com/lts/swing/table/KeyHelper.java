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

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class KeyHelper
{
	public enum Mapping
	{
		Insert (KeyStroke.getKeyStroke(KeyEvent.VK_INSERT,0)),
		Delete (KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0)),
		Enter  (KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0));
		
		public final KeyStroke keyStroke;
		Mapping (KeyStroke stroke)
		{
			keyStroke = stroke;
		}
	};
	
	public static void mapKey (Mapping mapping, JComponent comp, Action action)
	{
		comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(mapping.keyStroke, mapping.toString());
		comp.getActionMap().put(mapping.toString(), action);
	}
}
