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
package com.lts.ipc.test.fifo;

import com.lts.ipc.IPCException;
import com.lts.ipc.fifo.FIFO;
import com.lts.ipc.test.TestException;
import com.lts.ipc.test.semaphore.ClientServerTest;

public class NonBlockingTest extends ClientServerTest
{
	private FIFO myNamedPipe;

	protected void setup()
	{
		if (null == getName())
		{
			setName("/temp/testNamedPipe");
		}
		
		FIFO pipe = new FIFO(getName());
		setNamedPipe(pipe);
		byte[] buffer = new byte[getBufferSize()];
		setBuffer(buffer);
	}
	
	protected void setNamedPipe(FIFO pipe)
	{
		myNamedPipe = pipe;
	}
	
	protected FIFO getNamedPipe()
	{
		return myNamedPipe;
	}

	protected void performTest() throws TestException
	{
		setup();
		
		switch(getTestMode())
		{
			case Client :
				client();
				break;
				
			case Server :
				server();
				break;
				
			default :
				throw new TestException("unrecognized test mode " + getTestMode());
		}
	}
	
	public void report()
	{
		bandwidthReport();
	}
	
	
	protected void server() throws TestException
	{
		try
		{
			getNamedPipe().create();

			String msg =
				"Created named pipe waiting for connection...";
			
			System.out.println(msg);
			
			getNamedPipe().openWriter();

			System.out.println("Got connection.");
			
			noteStartTime();
			updateNextReport();
			while(true)
			{
				checkReport();

				int count = getNamedPipe().write(getBuffer());
				if (count != getBuffer().length)
				{
					System.err.println(
							"Warning: expected to write " + getBuffer().length + " bytes.  "
							+ "Actually wrote " + count + " bytes"
					);	
				}
				incrementIterations();
			}
		}
		catch (IPCException e)
		{
			String msg = "Error trying to create or open a named pipe";
			throw new TestException(msg,e);
		}
	}
	
	protected void client() throws TestException 
	{
		try
		{
			String msg;
			
			msg = 
				"Connecting to pipe via virtual name "
				+ getNamedPipe().getVirtualName();
			
			System.out.println(msg);
			
			getNamedPipe().openReader();
			
			System.out.println("Got connection");
			
			noteStartTime();
			updateNextReport();
			while (true)
			{
				checkReport();
				int count = getNamedPipe().read(getBuffer());
				if (count != getBuffer().length)
				{
					System.err.println(
							"Warning: expected to read " + getBuffer().length
							+ " bytes actually got " + count + " bytes"
					);
				}
				incrementIterations();
			}
		}
		catch (IPCException e)
		{
			throw new TestException ("Error trying to open named pipe", e);
		}
	}
}
