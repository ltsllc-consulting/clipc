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
package com.lts.chatterbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lts.ipc.IPCException;
import com.lts.ipc.sharedmemory.SharedMemory;

public class Segment
{

	private SharedMemory mySegment;
	protected int myEntrySize;
	protected int myNumberOfEntries;
	protected static byte[] BLANK_ENCODED = " ".getBytes();
	private static byte[] NEWLINE = "\n".getBytes();

	/**
	 * Initialize the communications segment.
	 * <P>
	 * This method is usually called by the constructor.
	 * </P>
	 * <P>
	 * The method will create the shared memory segment if it does not already exist and
	 * connect to it.
	 * </P>
	 * 
	 * @param fileName
	 *        The name of the file that is used for the memory segment.
	 * @param entrySize
	 *        The size, in bytes, of each entry in the segment.
	 * @param numberOfEntries
	 *        The number of entries in the segment.
	 * @throws MultiMonException
	 *         If a problem is encountered during initialization.
	 */
	protected void initialize(String fileName, int entrySize, int numberOfEntries)
			throws MultiMonException
	{
		try
		{
			myEntrySize = entrySize;
			myNumberOfEntries = numberOfEntries;
			int segmentSize = entrySize * numberOfEntries * BLANK_ENCODED.length;
			mySegment = new SharedMemory(fileName, segmentSize);
			if (mySegment.isFileCreator())
			{
				formatSegment();
			}
		}
		catch (Exception e)
		{
			throw new MultiMonException("error during initialization", e);
		}
	}

	protected String myBlankEntry;
	private byte[] myTemplate;
	public static final int DEFAULT_ENTRY_LENGTH = 256;
	public static final int DEFAULT_NUMBER_OF_SLOTS = 8;

	public Segment(String fileName) throws MultiMonException
	{
		initialize(fileName, DEFAULT_ENTRY_LENGTH, DEFAULT_NUMBER_OF_SLOTS);
	}

	public void putEntry(int slot, String entry)
	{
		int offset = getEntrySize() * slot;
		getSegment().putLine(offset, entry);
	}
	
	
	public void putEntry (int slot, byte[] data) throws MultiMonException
	{
		int offset = getEntrySize() * slot;
		try
		{
			getSegment().put(offset, data);
		}
		catch (IPCException e)
		{
			int length = (null == data) ? -1 : data.length;
			String msg =
				"Exception while trying to put " + length 
				+ " bytes of data to slot " + slot
				+ ", offset " + offset;
			throw new MultiMonException(msg,e);
		}
	}

	public String getEntry(int slotNumber) throws MultiMonException
	{
		try
		{
			int offset = getEntrySize() * slotNumber;
			return getSegment().getLine(offset, getEntrySize());
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new MultiMonException("Error trying to get entry number " + slotNumber, e);
		}
	}

	/**
	 * Unlock the shared memory segment if we have it locked.
	 * <P>
	 * This is a convenience method to handle all the problems associated with unlocking
	 * the shared memory segment.
	 * </P>
	 * 
	 * @param locked
	 *        true if the we have the segment locked, false otherwise.
	 * @throws MultiMonException
	 *         If an error is encountered while trying to unlock the segment.
	 */
	public void unlockSegment(boolean locked) throws MultiMonException
	{
		if (!locked)
			return;
	
		try
		{
			getSegment().unlock();
		}
		catch (IOException e)
		{
			throw new MultiMonException("Error unlocking segment", e);
		}
	}
	
	
	public void unlockSegment() throws MultiMonException
	{
		try
		{
			getSegment().unlock();
		}
		catch (IOException e)
		{
			throw new MultiMonException("Error unlocking segment", e);
		}
	}

	public void lockSegment() throws MultiMonException
	{
		try
		{
			getSegment().lock();
		}
		catch (IOException e)
		{
			throw new MultiMonException("Error locking segment", e);
		}
	}

	public SharedMemory getSegment()
	{
		return mySegment;
	}

	public void setSegment(SharedMemory segment)
	{
		mySegment = segment;
	}

	public int getEntrySize()
	{
		return myEntrySize;
	}

	public void setEntrySize(int entrySize)
	{
		myEntrySize = entrySize;
	}

	public int getNumberOfEntries()
	{
		return myNumberOfEntries;
	}

	public void setNumberOfEntries(int numberOfEntries)
	{
		myNumberOfEntries = numberOfEntries;
	}

	protected void formatSegment() throws IPCException
	{
		int size = getEntrySize() * getNumberOfEntries();
		byte[] buf = new byte[size];
		int index = 0;
		while (index < buf.length)
		{
			for (int i = 0; i < NEWLINE.length; i++)
			{
				buf[index + i] = NEWLINE[i];
			}
			index += NEWLINE.length;
		}
		
		getSegment().put(0, buf);
	}
	
	
	protected void initializeTemplate()
	{
		byte[] newline = "/n".getBytes();
		
		myTemplate = new byte[getEntrySize()];
		int count = 0;
		while (count < myTemplate.length)
		{
			for (int i = 0; i < myTemplate.length; i++)
			{
				myTemplate[count] = newline[i];
				count++;
			}
		}
	}

	public static void createSegmentFile (String fileName) throws IPCException
	{
		int size = DEFAULT_ENTRY_LENGTH * DEFAULT_NUMBER_OF_SLOTS;
		byte[] buffer = new byte[size];
		int index = 0;
		while (index < buffer.length)
		{
			for (int i = 0; i < NEWLINE.length; i++)
			{
				buffer[index + i] = NEWLINE[i];
			}
			index += NEWLINE.length;
		}
		
		File file = new File(fileName);
		if (file.exists())
		{
			boolean success = file.delete();
			if (!success)
			{
				throw new IPCException("Could not remove segment file " + file);
			}
		}
		
		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(buffer);
			fos.close();
		}
		catch (FileNotFoundException e)
		{
			throw new IPCException("Could not create segment file " + file);
		}
		catch (IOException e)
		{
			throw new IPCException("Error trying to write segment file " + file);
		}
	}

}