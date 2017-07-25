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
package com.lts.ipc.test;

import java.net.Socket;
import java.util.Random;


abstract public class Test
{
	public static final int DEFAULT_BUFFER_SIZE = 1024;
	public static final long DEFAULT_DURATION_GOAL = 10000;
	public static final int DEFAULT_NUMBER_OF_ITERATIONS = 1;
	public static final int DEFAULT_PORT = 7777;
	
	private int myBufferSize = DEFAULT_BUFFER_SIZE;
	private Socket mySocket;
	private int myIterationGoal = DEFAULT_NUMBER_OF_ITERATIONS;
	private int myIterations;
	byte[] myBuffer;
	private long myTotal;
	private long myDuration;
	private long myDurationGoal = DEFAULT_DURATION_GOAL;
	private int myPort = DEFAULT_PORT;

	abstract public void performTest() throws ArgumentException;

	protected void initializeBuffer()
	{
		myBuffer = new byte[DEFAULT_BUFFER_SIZE];
		Random r = new Random();
		r.nextBytes(myBuffer);
	}

	public int getPort()
	{
		return myPort;
	}

	public void setPort(int port)
	{
		myPort = port;
	}

	public int getBufferSize()
	{
		return myBufferSize;
	}

	public void setBufferSize(int bufferSize)
	{
		myBufferSize = bufferSize;
	}

	public long getDurationGoal()
	{
		return myDurationGoal;
	}

	public long getDuration()
	{
		return myDuration;
	}

	public void setDuration(long duration)
	{
		myDuration = duration;
	}

	public Socket getSocket()
	{
		return mySocket;
	}

	public void setSocket(Socket socket)
	{
		mySocket = socket;
	}

	public int getIterationGoal()
	{
		return myIterationGoal;
	}

	public void setIterationGoal(int goal)
	{
		myIterationGoal = goal;
	}

	public byte[] getBuffer()
	{
		return myBuffer;
	}

	public void setBuffer(byte[] buffer)
	{
		myBuffer = buffer;
	}

	public long getTotal()
	{
		return myTotal;
	}

	public void setTotal(long total)
	{
		myTotal = total;
	}

	public void setDurationGoal(long durationGoal)
	{
		myDurationGoal = durationGoal;
	}

	public void processArguments(String[] argv) throws ArgumentException
	{
		int index = 0;
		while (index < argv.length)
		{
			String value = argv[index];
			int advanceBy = processOneArgument(value, index, argv);
			index = index + advanceBy;
		}
	}

	protected int processOneArgument(String value, int index, String[] argv)
			throws ArgumentException
	{
		value = value.toLowerCase();
		boolean isSwitch = value.startsWith("-");
		if (isSwitch)
			value = value.substring(1);
		
		return processOneArgument(value, isSwitch, index, argv);
	}

	protected int processOneArgument(String value, boolean isSwitch, int index,
			String[] argv) throws ArgumentException
	{
		int advanceBy;
		
		if (value.equals("p") || value.equals("port"))
		{
			advanceBy = 2;
			String temp = getSwitchArgument(value, index, argv);
			int port = Integer.parseInt(temp);
			setPort(port);
		}
		else if (value.equals("b") || value.equals("buffersize"))
		{
			advanceBy = 2;
			String temp = getSwitchArgument(value, index, argv);
			int size = Integer.parseInt(temp);
			setBufferSize(size);
		}
		else if (value.equals("t") || value.equals("time") || value.equals("d") || value.equals("duration"))
		{
			advanceBy = 2;
			String temp = getSwitchArgument(value, index, argv);
			int duration = Integer.parseInt(temp);
			setDuration(duration);
		}
		else if (value.equals("i") || value.equals("iteration") || value.equals("iterations"))
		{
			advanceBy = 2;
			String temp = getSwitchArgument(value, 1+index, argv);
			int icount = Integer.parseInt(temp);
			setIterationGoal(icount);
		}
		else 
		{
			String original = argv[index];
			throw new ArgumentException("unrecognized argument: " + original);
		}
		
		
		return advanceBy;
	}

	protected String getSwitchArgument(String value, int index, String[] argv)
			throws ArgumentException
	{
		if (index >= argv.length)
		{
			throw new ArgumentException("Missing value for " + value + " option");
		}
		
		return argv[index];
	}

	public void performTest(String[] argv) throws ArgumentException
	{
		processArguments(argv);
		while (getIterations() < getIterationGoal())
		{
			performTest();
			setIterations(1 + getIterations());
		}
	}

	public int getIterations()
	{
		return myIterations;
	}

	public void setIterations(int iterations)
	{
		myIterations = iterations;
	}

	protected String bandwidthReport(String prefix)
	{
		Bandwidth band = new Bandwidth();
		band.setByteCount(getTotal());
		band.setDuration(getDuration());
		String msg = band.toBandwidthReport(prefix);
		return msg;
	}

	public boolean continueIterating()
	{
		return getDuration() < getDurationGoal();
	}
	
}
