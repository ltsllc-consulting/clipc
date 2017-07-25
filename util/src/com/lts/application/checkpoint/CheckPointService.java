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
package com.lts.application.checkpoint;

import com.lts.application.Application;
import com.lts.application.ApplicationException;
import com.lts.util.scheduler.ControllableThreadException;
import com.lts.util.scheduler.WorkPauseThread;

/**
 * A Thread that periodically creates a checkpoint for the system.
 * 
 * @author cnh
 */
public class CheckPointService extends WorkPauseThread
{
	protected static CheckPointService ourInstance;
	
	protected Application myApp;
	
	protected CheckPointService()
	{
		initialize();
	}
	
	public String getName()
	{
		return "Checkpoint";
	}
	
	
	public static CheckPointService getInstance()
	{
		if (null == ourInstance)
			createInstance();
		
		return ourInstance;
	}
	
	
	synchronized protected static void createInstance()
	{
		if (null != ourInstance)
			return;
		
		ourInstance = new CheckPointService();
	}
	
	
	protected void initialize()
	{
		myPauseTime = 2 * 60 * 1000;
	}
	
	@Override
	protected void process() throws ControllableThreadException
	{
		try
		{
			Application.getInstance().checkPoint();
		}
		catch (ApplicationException e)
		{
			throw new ControllableThreadException(true, e);
		}
	}

	public static void startService(Application app)
	{
		getInstance().setApplication(app);
		getInstance().start();
	}
	
	private void setApplication(Application app)
	{
		myApp = app;
	}

	public static void stopService()
	{
		getInstance().stopThread();
	}

	@Override
	protected long getMaxPauseTime()
	{
		return 300000;
	}
}
