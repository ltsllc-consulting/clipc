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
package com.lts.chatterbox.channels.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketRegistrar implements Runnable
{
	public static final int PORT_NUMBER = 6767;
	private boolean myKeepGoing;
	private ServerSocket myServerSocket;
	private Map<String, Integer> myClientToPort = new HashMap<String, Integer>();
	private Thread myThread;
	
	
	public synchronized Map<String, Integer> copyClients()
	{
		Map<String, Integer> copy = new HashMap<String, Integer>(myClientToPort);
		return copy;
	}
	
	
	public ServerSocket getServerSocket()
	{
		return myServerSocket;
	}

	public boolean keepGoing()
	{
		return myKeepGoing;
	}
	
	public void setKeepGoing(boolean keepGoing)
	{
		myKeepGoing = keepGoing;
	}
	
	public SocketRegistrar() throws IOException
	{
		initialize();
	}
	
	protected void initialize() throws IOException
	{
		myServerSocket = new ServerSocket(PORT_NUMBER);
	}

	@Override
	public void run()
	{
		try
		{
			execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void execute() throws IOException
	{
		myServerSocket = new ServerSocket(PORT_NUMBER);
		while (true)
		{
			Socket socket = myServerSocket.accept();
			RegistrarThread thread = new RegistrarThread(socket, this);
			thread.launch();
		}
	}


	synchronized public void register(String name, String strPort)
	{
		int port = Integer.parseInt(strPort);
		myClientToPort.put(name, port);
	}
	
	
	synchronized public void launch()
	{
		myThread = new Thread(this, "socket registrar");
		myThread.start();
	}
}
