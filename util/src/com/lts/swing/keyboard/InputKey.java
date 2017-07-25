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
package com.lts.swing.keyboard;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * Convenience enum to handle key codes and the like.
 * 
 * @author cnh
 */
public enum InputKey
{
	Enter(KeyEvent.VK_ENTER, 0),
	CtrlC(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK),
	CtrlD(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK), 
	CtrlG(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK),
	CtrlL(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK),
	CtrlR(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK),
	CtrlS(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK),
	CtrlV(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK),
	CtrlW(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK),
	CtrlX(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK),
	UpArrow(KeyEvent.VK_UP, 0),
	DownArrow(KeyEvent.VK_DOWN, 0),
	LeftArrow(KeyEvent.VK_LEFT, 0),
	RightArrow(KeyEvent.VK_RIGHT, 0),
	Insert(KeyEvent.VK_INSERT, 0),
	Delete(KeyEvent.VK_DELETE, 0), 	
	;
	
	private int myKeyCode;
	private int myModifier;
	
	InputKey(int keyCode)
	{
		myKeyCode = keyCode;
	}
	
	InputKey(int keyEvent, int modifier)
	{
		myKeyCode = keyEvent;
		myModifier = modifier;
	}
	
	public KeyStroke getKeyStroke()
	{
		return KeyStroke.getKeyStroke(myKeyCode, myModifier);
	}
}