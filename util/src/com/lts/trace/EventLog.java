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
package com.lts.trace;

import java.util.List;

public class EventLog
{
	static public int ME = 2;
	static public int MY_CALLER = 3;
	static public int MY_CALLERS_CALLER = 4;
	
	private static EventLog ourInstance;
	protected List<MethodCall> myCalls;
	
	static public EventLog getInstance()
	{
		return ourInstance;
	}
	
	static synchronized public void resetInstance()
	{
		ourInstance = new EventLog();
	}
	
	public void add()
	{
		add(1 + ME);
	}
	
	public void add(int framesBeforeThis)
	{
		MethodCall call = new MethodCall(1 + framesBeforeThis);
		myCalls.add(call);
	}

	public void enterMethod()
	{
		add(2);
	}
	
	public void leaveMethod()
	{
		add(2);
	}

	public String getTranscript()
	{
		
		// TODO Auto-generated method stub
		return null;
	}
}
