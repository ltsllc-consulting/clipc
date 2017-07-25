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
/**
 * 
 */
package com.lts.event;

/**
 * A class that executes a Runnable and checks for exceptions.  
 * 
 * <P>
 * If an exception is encountered, {@link #processException(Exception)} is called
 * to handle the exception.
 * </P>
 * 
 * @author cnh
 *
 */
public class ActionRunner implements Runnable
{
	public SimpleThreadedAction myAction;
	
	public ActionRunner(SimpleThreadedAction action)
	{
		myAction = action;
	}
	
	public void run()
	{
		try
		{
			myAction.action();
		}
		catch (Exception e)
		{
			processException(e);
		}
	}
	
	public void processException(Exception e)
	{
		e.printStackTrace();
	}
}