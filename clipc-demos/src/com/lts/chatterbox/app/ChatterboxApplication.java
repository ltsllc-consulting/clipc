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
package com.lts.chatterbox.app;

import com.lts.application.Application;
import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationData;
import com.lts.ipc.IPCPackage;
import com.lts.test.Spawn;

public class ChatterboxApplication extends Application
{
	private static ChatterboxApplication ourInstance;
	
	private Spawn mySpawn;
	
	public Spawn getSpawn()
	{
		if (null == mySpawn)
			mySpawn = new Spawn();
		
		return mySpawn;
	}

	public void setSpawn(Spawn spawn)
	{
		mySpawn = spawn;
	}

	@Override
	public ApplicationData createApplicationData() throws ApplicationException
	{
		return null;
	}

	@Override
	public String getApplicationName()
	{
		return "MultiMon";
	}

	@Override
	public void startApplication() throws ApplicationException
	{
		IPCPackage.initializePackage();
		ControlWindow.launch();
	}

	public static void launch(String[] argv)
	{
		ChatterboxApplication app = new ChatterboxApplication();
		ourInstance = app;
		app.startApplication(argv);
	}
	
	
	public static ChatterboxApplication getApp()
	{
		return ourInstance;
	}
}

