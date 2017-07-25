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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A thread that handles a client.
 * <P>
 * Clients can ask to register, unregister and to get a list of registered entities.
 * </P>
 * 
 * @author cnh
 */
public class RegistrarThread implements Runnable, RegistrarCommands
{
	private static final int END_OF_COMMAND = ';';
	private Socket mySocket;
	private Reader myReader;
	private Writer myWriter;
	private SocketRegistrar myRegistrar;
	private Thread myThread;
		
	public Writer getWriter()
	{
		return myWriter;
	}

	public void setWriter(Writer writer)
	{
		myWriter = writer;
	}

	public SocketRegistrar getRegistrar()
	{
		return myRegistrar;
	}

	public RegistrarThread (Socket socket, SocketRegistrar registrar) throws IOException
	{
		initialize(socket, registrar);
	}
	
	protected void initialize(Socket socket, SocketRegistrar registrar) throws IOException
	{
		mySocket = socket;
		myReader = new InputStreamReader(socket.getInputStream());
		myRegistrar = registrar;
		myWriter = new OutputStreamWriter(socket.getOutputStream());
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
		String strCommand;
		
		strCommand = readCommand();
		while (null != strCommand)
		{
			executeCommand(strCommand);
		}
	}

	private void executeCommand(String strCommand) throws IOException
	{
		String[] fields = strCommand.split(REGEX_FIELD_SEPARATOR);
		if (fields.length < 1)
			return;
		else if (fields[0].equalsIgnoreCase(CMD_LIST))
		{
			performList();
		}
		else if (fields[0].equalsIgnoreCase(CMD_REGISTER))
		{
			preformRegister(fields);
		}
	}

	private void preformRegister(String[] fields)
	{
		getRegistrar().register(fields[1], fields[2]);
	}

	private void performList() throws IOException
	{
		Map<String, Integer> clients = getRegistrar().copyClients();
		List<String> list = new ArrayList<String>(clients.keySet());
		Collections.sort(list);
		
		for (String clientName : list)
		{
			Integer port = clients.get(clientName);
			String msg = 
				clientName 
				+ STR_FIELD_SEPARATOR + port.toString();
			
			getWriter().write(msg);
		}
		
		getWriter().write(END_OF_COMMAND);
	}

	private String readCommand() throws IOException
	{
		StringBuffer sb = new StringBuffer();
		int c = getReader().read();
		while (-1 != c && c != END_OF_COMMAND)
		{
			sb.append((char) c);
		}
		
		String s = sb.toString().trim();
		if (s.equals(""))
			return null;
		else
			return s;
	}

	public Socket getSocket()
	{
		return mySocket;
	}

	public void setSocket(Socket socket)
	{
		mySocket = socket;
	}

	public Reader getReader()
	{
		return myReader;
	}

	public void setReader(Reader reader)
	{
		myReader = reader;
	}

	public void launch()
	{
		myThread = new Thread(this, "service thread");
		myThread.start();
	}
}
