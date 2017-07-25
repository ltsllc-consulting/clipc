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
package com.lts.ipc.test.sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.lts.ipc.Utils;
import com.lts.ipc.test.ArgumentException;
import com.lts.ipc.test.TestClass;
import com.lts.ipc.test.TestException;

public class SocketTest extends TestClass
{
	public enum TestMode
	{
		Bandwidth,
		Synchronization;
		
		public static TestMode valueOfIgnoreCase (String s)
		{
			return (TestMode) Utils.toValueIgnoreCase(values(), s);
		}
		
		public static TestMode matchIgnoreCase(String s)
		{
			return (TestMode) Utils.matchIgnoreCase(values(), s);
		}
	}
	
	public enum TestRole
	{
		Client,
		Server;
		
		public static TestRole valueOfIgnoreCase (String s)
		{
			return (TestRole) Utils.toValueIgnoreCase(values(), s);
		}
		
		public static TestRole matchIgnoreCase(String s)
		{
			return (TestRole) Utils.matchIgnoreCase(values(), s);
		}
	}
	
	
	private TestMode myMode;
	private TestRole myRole;
	private int myPort = 7777;
	private long myNextReport;
	

	
	@Override
	protected void performTest() throws TestException
	{
		switch(getRole())
		{
			case Client :
				client();
				break;
				
			case Server :
				server();
				break;
				
			default :
				String msg = "Unrecognized role " + getRole();
				throw new TestException(msg);
		}
	}
	

	private void client() throws TestException
	{
		InputStream istream = null;
		OutputStream ostream = null;
		
		String msg = 
			"Client performinging " + getMode()
			+ " connecting to port " + getPort();
		
		System.out.println(msg);
		
		try
		{
			Socket socket = new Socket("127.0.0.1", getPort());
			istream = socket.getInputStream();
			ostream = socket.getOutputStream();
			
			while (true)
			{
				if (System.currentTimeMillis() > getNextReport())
				{
					report();
				}
				else
				{
					performIteration(istream, ostream);
				}
			}
		}
		catch (IOException e)
		{
			throw new TestException(e);
		}
		finally
		{}
	}
	
	private void server() throws TestException
	{
		InputStream istream = null;
		OutputStream ostream = null;
		
		try
		{
			ServerSocket ssocket = new ServerSocket(getPort());
			String msg = 
				"Server performing " + getMode()
				+ " test.  Listening on port " + getPort();
			System.out.println(msg);
			
			Socket socket = ssocket.accept();
			istream = socket.getInputStream();
			ostream = socket.getOutputStream();
			
			msg = "got connection, commencing test";
			System.out.println(msg);
			
			setNextReport(System.currentTimeMillis() + getReportInterval());
			noteStartTime();
			while (true)
			{
				if (getNextReport() > System.currentTimeMillis())
				{
					performIteration(istream, ostream);
				}
				else
				{
					report();
				}
			}
		}
		catch (IOException e)
		{
			throw new TestException(e);
		}
		finally
		{
			Utils.closeNoExceptions(istream);
			Utils.closeNoExceptions(ostream);
		}
	}

	
	private void report()
	{
		noteStopTime();
		
		switch(getMode())
		{
			case Bandwidth :
				bandwidthReport();
				break;
				
			case Synchronization :
				iterationReport();
				break;
		}
		
		resetIterations();
		long nextReport = getReportInterval() + System.currentTimeMillis();
		setNextReport(nextReport);
		noteStartTime();
	}

	private void performIteration(InputStream istream, OutputStream ostream) throws IOException
	{
		switch (getRole())
		{
			case Client :
			{
				istream.read(getBuffer());
				ostream.write(getBuffer());
				break;
			}
			
			case Server :
			{
				ostream.write(getBuffer());
				istream.read(getBuffer());
				break;
			}
		}
		
		incrementIterations();
	}
	
	
	
	@Override
	protected void processArgumentNormal(String argument) throws ArgumentException
	{
		TestMode mode = TestMode.matchIgnoreCase(argument);
		if (null != mode)
			setMode(mode);
		else
		{			
			TestRole role = TestRole.matchIgnoreCase(argument);
			if (null != role)
				setRole(role);
			else
				super.processArgument(argument);
		}
	}
	
	
	@Override
	protected void processSwitch(char c) throws ArgumentException
	{
		char c2 = Character.toLowerCase(c);
		switch (c2)
		{
			case 'p' :
				setPort(nextArgInt());
				advance(1);
				break;
				
			default :
				super.processSwitch(c);
				break;
		}
	}


	public TestMode getMode()
	{
		return myMode;
	}


	public void setMode(TestMode mode)
	{
		myMode = mode;
		
		switch (myMode)
		{
			case Bandwidth :
			{
				byte[] buffer = new byte[1024];
				setBuffer(buffer);
				break;
			}
			
			case Synchronization :
			{
				byte[] buffer = new byte[1];
				setBuffer(buffer);
				break;
			}
		}
	}


	public TestRole getRole()
	{
		return myRole;
	}


	public void setRole(TestRole role)
	{
		myRole = role;
	}


	protected int getPort()
	{
		return myPort;
	}


	protected void setPort(int port)
	{
		myPort = port;
	}


	public long getNextReport()
	{
		return myNextReport;
	}


	public void setNextReport(long nextReport)
	{
		myNextReport = nextReport;
	}

}
