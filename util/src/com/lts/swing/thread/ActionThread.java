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
package com.lts.swing.thread;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A BlockingThread that starts when an action occurs.
 * 
 * <P>
 * This class was created to be used by the UI in situations where the current (UI)
 * thread should not block.
 * </P>
 * 
 * @author cnh
 *
 */
public abstract class ActionThread extends BlockingThread implements ActionListener
{
	abstract public void run();
	
	public void actionPerformed (ActionEvent event)
	{
		myThread = new Thread(this);
		BlockingThread.addBlockingThread(this);
		startThread();
	}	
}
