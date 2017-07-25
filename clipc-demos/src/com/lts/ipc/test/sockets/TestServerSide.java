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
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.lts.ipc.test.Test;

public class TestServerSide extends Test
{
	private ServerSocket myServerSocket;
	
	public void performTest() 
	{
		try
		{
			initializeBuffer();
			ServerSocket ss = new ServerSocket(getPort());
			setServerSocket(ss);
			
			System.out.println("Waiting on port " + getPort() + " for connection");
			Socket socket = ss.accept();
			System.out.println("Got connection to " + socket.getRemoteSocketAddress());
			setSocket(socket);
			performIterations();
		}
		catch (IOException e)
		{
			System.err.println("Error trying to create server socket at " + getPort());
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeAll();
		}
	}
	
	private void performIterations() throws Exception
	{
		int count = 0;
		long duration = 0;
		
		
		try
		{
			OutputStream ostream = getSocket().getOutputStream();
			
			long start = System.currentTimeMillis();
			while (continueIterating())
			{
				ostream.write(getBuffer());
				setTotal(getTotal() + getBuffer().length);
				duration = System.currentTimeMillis() - start;
				setDuration(duration);
				setIterationGoal(count);
			}
		}
		catch (IOException e)
		{
			String msg = "Error sending data";
			throw new Exception(msg, e);
		}
		finally
		{
			String msg = bandwidthReport("Wrote "); 
			System.out.println(msg);
		}
	}

	private void closeAll()
	{
		if (null != getSocket())
		{
			try
			{
				getSocket().close();
			}
			catch (IOException e)
			{
				System.err.println("Error closing session");
				e.printStackTrace();
			}
		}
		
		if (null != getServerSocket())
		{
			try
			{
				getServerSocket().close();
			}
			catch (IOException e)
			{
				System.err.println("Error closing server socket");
				e.printStackTrace();
			}
		}
		
		
	}
	public ServerSocket getServerSocket()
	{
		return myServerSocket;
	}
	public void setServerSocket(ServerSocket serverSocket)
	{
		myServerSocket = serverSocket;
	}	
}
