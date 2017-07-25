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

import java.io.File;

import com.lts.application.Application;
import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationData;

public class FIFOApplication extends Application
{
	public static final String FIFO_NAME = "chatterboxFifo";
	
	@Override
	public ApplicationData createApplicationData() throws ApplicationException
	{
		return null;
	}

	@Override
	public String getApplicationName()
	{
		return "FCA";
	}

	private enum Mode
	{
		Reader,
		NonBlockingReader,
		Writer,
		NonBlockingWriter;
		
		static public Mode valueOfIgnoreCase(String s)
		{
			for (Mode m : values())
			{
				if (s.equalsIgnoreCase(m.name()))
					return m;
			}
			
			return null;
		}
	}
	
	private Mode myMode = Mode.Reader;
	private String myFIFO = "clipcFIFO";
	
	protected void processCommandLine(String[] argv)
	{
		if (argv.length > 0)
		{
			myMode = Mode.valueOfIgnoreCase(argv[0]);
			if (null == myMode)
			{
				System.err.println("Unrecognized mode: " + argv[0]);
				System.exit(1);
			}
		}
		
		if (argv.length > 1)
		{
			myFIFO = argv[1];
		}
		
		File file = new File(myFIFO);
		if (!file.isAbsolute())
		{
			File dir = new File(System.getProperty("user.home"));
			file = new File(dir, myFIFO);
			myFIFO = file.toString();
		}
		
	}
	
	@Override
	public void startApplication() throws ApplicationException
	{
		try
		{
			switch(myMode)
			{
				case Reader :
				{
					FIFOReaderWindow.launch(myFIFO);
					// win.initializeFIFO();
					break;
				}
				
				case NonBlockingReader :
				{
					FIFOReaderWindow win = FIFOReaderWindow.launch(myFIFO, 200);
					win.initializeFIFO();
					break;
				}
				
				case Writer :
				{
					FIFOWriterWindow win = FIFOWriterWindow.launch(myFIFO);
					win.intializeFifo();
					break;
				}
				
				case NonBlockingWriter :
				{
					FIFOWriterWindow win = FIFOWriterWindow.launch(myFIFO, 200);
					win.intializeFifo();
					break;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main (String[] argv)
	{
		FIFOApplication app = new FIFOApplication();
		app.startApplication(argv);
	}
}
