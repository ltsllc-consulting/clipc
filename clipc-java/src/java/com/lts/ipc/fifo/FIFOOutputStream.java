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
import java.io.OutputStream;

import com.lts.ipc.IPCException;

public class FIFOOutputStream extends OutputStream
{
	private byte[] myBuffer;
	private int myNumberOfBytes;
	private FIFO myNamedPipe;
	public FIFOOutputStream(FIFO pipe)
	{
		initialize(pipe);
	}
	
	
	public void initialize(FIFO pipe)
	{
		myNamedPipe = pipe;
		myBuffer = new byte[8192];
		myNumberOfBytes = 0;
	}
	
	
	@Override
	public void write(int b) throws IOException
	{
		if (myNumberOfBytes >= myBuffer.length)
		{
			flush();
		}
		
		myBuffer[myNumberOfBytes] = (byte) b;
		myNumberOfBytes++;
	}

	@Override
	public void flush() throws IOException
	{
		try
		{
			myNamedPipe.write(myBuffer, 0, myNumberOfBytes);
			myNumberOfBytes = 0;
		}
		catch (IPCException e)
		{
			throw new IOException("Error writing out data", e);
		}
	}

	
	@Override
	public void close() throws IOException
	{
		flush();
	}
}
