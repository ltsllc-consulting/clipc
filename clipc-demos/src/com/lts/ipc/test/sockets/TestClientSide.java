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
import java.net.Socket;

import com.lts.ipc.test.ArgumentException;
import com.lts.ipc.test.Test;

public class TestClientSide extends Test
{
	public static final String DEFAULT_HOST = "127.0.0.1";
	
	private String myHost = DEFAULT_HOST;
	
	public void performTest()
	{
		InputStream istream = null;
		Socket socket = null;
		long start = 0;
		long stop = 0;
		
		try
		{
			String msg = 
				"Connecting to " + getHost() + ":" + getPort();
			System.out.println(msg);
			
			socket = new Socket(getHost(), getPort());
			setSocket(socket);
			byte[] buffer = new byte[getBufferSize()];
			istream = socket.getInputStream();
			long total = 0;
			int count = 0;
			
			start = System.currentTimeMillis();
			do {
				count = istream.read(buffer);
				total += count;
				setTotal(total);
			} while (count > 0);
			stop = System.currentTimeMillis();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeStream(istream);
			closeSocket(socket);

			long duration = stop - start;
			setDuration(duration);

			String msg = bandwidthReport("Read ");
			System.out.println(msg);
		}
	}


	private void closeSocket(Socket socket)
	{
		try
		{
			if (null != socket)
			{
				socket.close();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	private void closeStream(InputStream istream)
	{
		try
		{
			if (null != istream)
				istream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	public String getHost()
	{
		return myHost;
	}


	public void setHost(String host)
	{
		myHost = host;
	}


	@Override
	protected int processOneArgument(String value, boolean isSwitch, int index, String[] argv)
			throws ArgumentException
	{
		int advanceBy;
		
		if (value.equals("h") || value.equals("host"))
		{
			advanceBy = 2;
			String temp = getSwitchArgument(value, index+1, argv);
			setHost(temp);
		}
		else
		{
			advanceBy = super.processOneArgument(value, isSwitch, index, argv);
		}
		
		return advanceBy;
	}
	
	
	
}
