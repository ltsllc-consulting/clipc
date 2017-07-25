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
package com.lts.chatterbox.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.lts.swing.SimpleListModel;

public class SocketReceiverThread implements Runnable
{
	private Thread myThread;
	private Socket mySocket;
	private BufferedReader myIn;
	private SimpleListModel myModel;
	
	public Thread getThread()
	{
		return myThread;
	}
	
	public Socket getSocket()
	{
		return mySocket;
	}
	
	public void setSocket(Socket socket)
	{
		mySocket = socket;
	}
	
	public SocketReceiverThread(Socket socket, SimpleListModel model) throws IOException
	{
		initialize(socket, model);
	}
	
	
	protected void initialize(Socket socket, SimpleListModel model) throws IOException
	{
		mySocket = socket;
		InputStreamReader isr = new InputStreamReader(socket.getInputStream());
		myIn = new BufferedReader(isr);
		myThread = new Thread(this, "Socket Reader");
		myModel = model;
	}
	
	
	public void launch()
	{
		myThread.start();
	}
	
	@Override
	public void run()
	{
		try
		{
			mainLoop();
		}
		catch (Exception e)
		{
			processMainLoopException(e);
		}
		finally {}
	}
	
	
	protected void processMainLoopException(Exception e)
	{
		e.printStackTrace();
	}

	public void mainLoop() throws IOException
	{
		String s = myIn.readLine();
		while (null != s)
		{
			processLine(s);
			s = myIn.readLine();
		}
	}

	protected void processLine(String s)
	{
		myModel.add(s);
	}

}
