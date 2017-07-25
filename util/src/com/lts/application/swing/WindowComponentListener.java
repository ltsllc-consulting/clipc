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
package com.lts.application.swing;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * A class that simplifies listening for window and component events.
 * <P>
 * The class implements both WindowListener and ComponentListener. In addition to
 * supplying dummy methods for all events, the class also defines
 * {@link #windowClosingOrHidden(ComponentEvent)} which is called when
 * {@link #windowClosing(WindowEvent)}, {@link #componentHidden(ComponentEvent)} or
 * {@link #windowClosed(WindowEvent)} is called.  This is useful if you want to 
 * know any time that a window is "closed."
 * </P>
 * 
 * <P>
 * The class defines the {@link #windowComponentEvent(ComponentEvent)} method.  That 
 * method is called when any of the event listener methods are called.
 * </P>
 * 
 * @author cnh
 */
public class WindowComponentListener implements WindowListener, ComponentListener
{

	@Override
	public void windowActivated(WindowEvent e)
	{
		windowComponentEvent(e);
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		windowClosingOrHidden(e);
		windowComponentEvent(e);
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		windowClosingOrHidden(e);
		windowComponentEvent(e);
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		windowComponentEvent(e);
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		windowOpenedOrDisplayed(e);
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		windowClosingOrHidden(e);
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		windowComponentEvent(e);
		windowOpenedOrDisplayed(e);
	}

	@Override
	public void componentHidden(ComponentEvent e)
	{
		windowClosingOrHidden(e);
		windowComponentEvent(e);
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
		windowComponentEvent(e);		
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		windowComponentEvent(e);
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
		windowComponentEvent(e);
		windowOpenedOrDisplayed(e);
	}

	/**
	 * This method is called when any window or component method is called.
	 * 
	 * @param e Triggering event.
	 */
	public void windowComponentEvent(ComponentEvent e)
	{
	}

	/**
	 * Called when windowClosing, windowClosed or componentHidden is 
	 * called.
	 * 
	 * @param e Triggering event.
	 */
	public void windowClosingOrHidden(ComponentEvent e)
	{
		windowComponentEvent(e);
	}
	
	/**
	 * Called when the window goes from invisible, closed or hidden to being
	 * visible.
	 * 
	 * <P>
	 * Triggering calls:
	 * </P>
	 * <UL>
	 * <LI>componentShown</LI>
	 * <LI>windowOpened</LI>
	 * </UL>
	 * 
	 * @param e
	 */
	public void windowOpenedOrDisplayed(ComponentEvent e)
	{}
}
