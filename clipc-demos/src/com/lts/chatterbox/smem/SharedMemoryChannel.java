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
package com.lts.chatterbox.smem;

import com.lts.chatterbox.MultiMonException;
import com.lts.chatterbox.Segment;
import com.lts.chatterbox.events.ChatterListener;
import com.lts.ipc.Utils;

/**
 * Defines the structure of a shared memory segment used to define the names of of the
 * entities that will be reporting status. The order in which the entries appear in this
 * segment also defines where in the status segment a particular entities status will be
 * found.
 * <P>
 * A segment is broken up into 128 character names. To communicate whether or not a
 * segment has been initialized, the first slot in the segment is taken up by a string
 * with the following contents:
 * </P>
 * <CODE>
 * <PRE>
 *     initialized &lt;number of entries&gt;&lt;blank&gt;
 * </PRE>
 * </CODE>
 * <P>
 * If the above string is not found as the first entry in the segment, it is assumed that
 * the segment has not been initialized. If this situation is detected, the class will
 * call the {@link #wipe()} method to clear the table.
 * </P>
 * <P>
 * If a particular entry is not being used, then the entry should contain all space
 * (blank) characters. For example:
 * </P>
 * <TABLE border="1">
 * <TR>
 * <TD><B>Offset</B></TD>
 * <TD><B>String</B></TD>
 * </TR>
 * <TR>
 * <TD>0</TD>
 * <TD>initialized 128 8</TD>
 * </TR>
 * <TR>
 * <TD>128</TD>
 * <TD>Power</TD>
 * </TR>
 * <TR>
 * <TD>256</TD>
 * <TD></TD>
 * </TR>
 * <TR>
 * <TD>384</TD>
 * <TD>Humidity</TD>
 * </TR>
 * <TR>
 * <TD>512</TD>
 * <TD></TD>
 * </TR>
 * <TR>
 * <TD>640</TD>
 * <TD>Fuel</TD>
 * </TR>
 * <TR>
 * <TD>768</TD>
 * <TD>Temperature</TD>
 * </TR>
 * <TR>
 * <TD>896</TD>
 * <TD></TD>
 * </TR>
 * <TR>
 * <TD>1024</TD>
 * <TD></TD>
 * </TR>
 * </TABLE>
 * <P>
 * The first entry in the table shows that it has been initialized, that the entry size is 
 * 128 bytes, and that there are 8 entries (including the 0th entry).
 * </P>
 * <P>
 * The table contains 4 entries: temperature, power, humidity and fuel. The rest of the
 * entries in the table are unused.
 * </P>
 */
public class SharedMemoryChannel implements Channel
{
	public static final byte[] NEWLINE = "\n".getBytes();
	
	private int mySlot = -1;
	
	private Segment mySegment;

	private int myCount;

	private String myId;
	
	private SegmentMonitorThread myMonitorThread;
	
	public int getSlot()
	{
		return mySlot;
	}


	public void setSlot(int slot)
	{
		mySlot = slot;
	}


	public SharedMemoryChannel (String segmentName) throws MultiMonException
	{
		initialize(segmentName);
	}
		
		
	public void initialize(String segmentName) throws MultiMonException
	{
		mySegment = new Segment(segmentName);
		myMonitorThread = SegmentMonitorThread.launch(mySegment, 250);
	}
	
	
	/**
	 * Reserve a slot in the table.
	 * <P>
	 * This method locks the segment and searches for a slot that is not in use. If one is
	 * is found, the method put's the entry into the table and returns the slot number
	 * that it now occupies. If the table is full, the method returns -1.
	 * </P>
	 * 
	 * @param entry
	 *        The name that the caller wants to put in the table.
	 * @return The slot number where the entry is stored, or -1 if the table is full.
	 * @throws MultiMonException
	 *         If a problem is encountered while reserving a slot. The message portion of
	 *         the exception should contain the exact problem.
	 */
	synchronized public int reserveSlot(String entry) throws MultiMonException
	{
		boolean locked = false;

		try
		{
			getSegment().lockSegment();
			locked = true;
			int slot = -1;
			
			for (int i = 1; i < getSegment().getNumberOfEntries(); i++)
			{
				String s = getSegment().getEntry(i).trim();
				if (s.equals(""))
				{
					slot = i;
					getSegment().putEntry(i, entry);
					break;
				}	
			}
			
			return slot;
		}
		finally
		{
			if (locked)
			{
				getSegment().unlockSegment();
			}
		}
	}
	
	
	synchronized public void releaseSlot(int slot) throws MultiMonException
	{
		boolean locked = false;
		try
		{
			int size = getSegment().getEntrySize();
			byte[] entry = Utils.createAndFillWith(size, "\n");
			getSegment().lockSegment();
			locked = true;
			getSegment().putEntry(getSlot(), entry);
		}
		finally
		{
			if (locked)
			{
				getSegment().unlockSegment();
			}
		}		
	}

	
	@Override
	public void addListener(ChatterListener listener)
	{
		getMonitorThread().addListener(listener);
	}


	@Override
	public void removeListener(ChatterListener listener)
	{
		getMonitorThread().removeListener(listener);
	}


	public void send(String message)
	{
		String msg = 
			myId 
			+ "|" + myCount
			+ "|" + message;
		
		getSegment().putEntry(getSlot(), msg);
		myCount++;
	}
	
	public void open(String id) throws ChannelException
	{
		try
		{
			mySlot = reserveSlot("reserved");
			myCount = 0;
			myId = id;
		}
		catch (MultiMonException e)
		{
			throw new ChannelException("Error opening channel", e);
		}
	}
	
	public void close()
	{
		try
		{
			releaseSlot(mySlot);
		}
		catch (MultiMonException e)
		{
			//
			// very little we can do at this point
			//
			e.printStackTrace();
		}
	}


	public int getCount()
	{
		return myCount;
	}


	public void setCount(int count)
	{
		myCount = count;
	}
	
	public void incrementCount()
	{
		myCount++;
	}


	public String getId()
	{
		return myId;
	}


	public void setId(String id)
	{
		myId = id;
	}


	public Segment getSegment()
	{
		return mySegment;
	}


	public void setSegment(Segment segment)
	{
		mySegment = segment;
	}


	public SegmentMonitorThread getMonitorThread()
	{
		return myMonitorThread;
	}


	public void setMonitorThread(SegmentMonitorThread monitorThread)
	{
		myMonitorThread = monitorThread;
	}
}
