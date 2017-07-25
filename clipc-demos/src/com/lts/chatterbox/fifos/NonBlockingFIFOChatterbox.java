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
package com.lts.chatterbox.fifos;

import com.lts.LTSException;
import com.lts.chatterbox.servers.Server;
import com.lts.swing.TextWindow;
import com.lts.test.Spawn;
import com.lts.test.SpawnException;

public class NonBlockingFIFOChatterbox implements Server
{
	public static final String FIFO_NAME = "chatterboxFIFO";
	
	private static NonBlockingFIFOChatterbox ourInstance;
	
	
	public NonBlockingFIFOChatterbox()
	{
		initialize();
	}
	
	protected void initialize()
	{
		mySpawn = new Spawn();
	}
	
	
	public static NonBlockingFIFOChatterbox getInstance()
	{
		if (null == ourInstance)
			ourInstance = new NonBlockingFIFOChatterbox();
		
		return ourInstance;
	}
	
	@Override
	public void launch() 
	{
		try
		{
			String[][] spawnClasses = {
				{ FIFOApplication.class.getName(), "NonBlockingReader" },
				{ FIFOApplication.class.getName(), "NonBlockingWriter" },
			};
			
			getSpawn().launch(spawnClasses);
		}
		catch (SpawnException e)
		{
			TextWindow.showException(e);
		}
	}
	
	
	public void terminate()
	{
		getSpawn().terminate();
	}
	
	
	public void launchDemo()
	{
		try
		{
			FIFOWriterWindow.launchDemo();
			FIFOReaderWindow.launchDemo();
		}
		catch (LTSException e)
		{
			e.printStackTrace();
		}
	}

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

}
