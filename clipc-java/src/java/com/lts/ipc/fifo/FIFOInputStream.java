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
package com.lts.ipc.fifo;

import java.io.IOException;
import java.io.InputStream;

import com.lts.ipc.IPCException;

public class FIFOInputStream extends InputStream
{
	private FIFO myPipe;
	private byte[] myBuffer;
	private int myIndex;
	private boolean myEndOfFile;
	private int myDataSize;
	private int myTimeout;
	
	public boolean isEndOfFile()
	{
		return myEndOfFile;
	}

	public void setEndOfFile(boolean endOfFile)
	{
		myEndOfFile = endOfFile;
	}

	public FIFOInputStream(FIFO pipe)
	{
		initialize(pipe, -1);
	}
	
	public FIFOInputStream(FIFO fifo, int timeoutMsec) 
	{
		initialize(fifo, timeoutMsec);
	}

	protected void initialize(FIFO pipe, int timeoutMsec)
	{
		myPipe = pipe;
		myBuffer = new byte[8192];
		myIndex = -1;
		setEndOfFile(false);
		myTimeout = timeoutMsec;
	}
	
	
	public int read() throws IOException
	{
		if (isEndOfFile())
			return -1;
		
		if (-1 == myIndex || myIndex >= myDataSize)
		{
			loadBuffer();
		}
		
		if (isEndOfFile())
			return -1;
		
		int bvalue = myBuffer[myIndex];
		myIndex++;
		return bvalue;
	}
	
	protected int bytesAvailable()
	{
		if (myIndex < 0)
			return 0;
		
		return myDataSize - myIndex;
	}
	
	public int read(byte[] buffer, int offset, int length) throws IOException
	{
		if (isEndOfFile())
			return -1;
		
		//
		// if we don't currently have any data, load some
		//
		if (bytesAvailable() < 1)
			loadBuffer();
		
		if (isEndOfFile())
			return -1;
		
		int count = bytesAvailable();
		int index = 0;
		while (index < count && index < length && index < buffer.length)
		{
			buffer[index + offset] = (byte) read();
			index++;
		}

		return count;
	}

	protected void loadBuffer() throws IOException
	{
		try
		{
			if (-1 == myTimeout)
			{
				myDataSize = myPipe.read(myBuffer);
			}
			else
			{
			}
			myIndex = 0;
			
			if (-1 == myDataSize)
			{
				setEndOfFile(true);
			}
		}
		catch (IPCException e)
		{
			throw new IOException("Error reading data from pipe", e);
		}
	}
}
