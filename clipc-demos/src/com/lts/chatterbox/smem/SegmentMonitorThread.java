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

import java.util.HashMap;
import java.util.Map;

import com.lts.chatterbox.MultiMonException;
import com.lts.chatterbox.Segment;
import com.lts.chatterbox.events.ChatterEvent;
import com.lts.chatterbox.events.ChatterHelper;
import com.lts.chatterbox.events.ChatterListener;
import com.lts.ipc.test.ThreadUtil;

public class SegmentMonitorThread implements Runnable
{
	private Map<String, Integer> myIdToSequence = new HashMap<String, Integer>();
	private Segment mySegment;
	private ChatterHelper myHelper = new ChatterHelper();
	private long mySleepTime;
	
	@SuppressWarnings("unused")
	private Thread myThread;
	
	public static SegmentMonitorThread launch (Segment segment, long sleepTime)
	{
		SegmentMonitorThread receiver = new SegmentMonitorThread(segment, sleepTime);
		Thread thread = new Thread(receiver, "SharedMemoryReceiver");
		receiver.myThread = thread;
		thread.start();
		return receiver;
	}
	
	public SegmentMonitorThread(Segment segment)
	{
		initialize(segment);
	}
	
	public SegmentMonitorThread(Segment segment, long sleepTime)
	{
		mySegment = segment;
		mySleepTime = sleepTime;
	}

	protected void initialize(Segment segment)
	{
		mySegment = segment;
	}
	
	public void addListener(ChatterListener listener)
	{
		myHelper.addListener(listener);
	}

	public void removeListener(ChatterListener listener)
	{
		myHelper.removeListener(listener);
	}

	private ChatterEvent toMessage(String s) throws ChannelException
	{
		if (null == s)
			return null;
		
		s = s.trim();
		String[] fields = s.split("\\|");
		
		//
		// it is possible for a slot to just have the id name or the word "reserved"
		// In that situation, ignore the slot.
		//
		if (fields.length < 3)
		{
			return null;
		}
		
		ChatterEvent event = new ChatterEvent(fields[0], fields[1], fields[2]);
		return event;
	}
	
	
	private void scan () throws MultiMonException, ChannelException
	{
		for (int slot = 1; slot < mySegment.getNumberOfEntries(); slot++)
		{
			//
			// get the message from this slot
			//
			String s = mySegment.getEntry(slot);
			ChatterEvent event = toMessage(s);
			if (null == event)
			{
				continue;
			}
			
			//
			// ensure that we have not already reported this message.
			//
			Integer msgSequence = event.sequence;
			Integer last = myIdToSequence.get(event.source);
			if (null != last && last >= msgSequence)
			{
				continue;
			}
			
			//
			// New message, notify our listeners
			//
			myIdToSequence.put(event.source, msgSequence);
			myHelper.fire(event);
		}
	}

	@Override
	public void run()
	{
		try
		{
			//
			// no flag to stop since we can crash and burn without causing 
			// problems.
			//
			while (true)
			{
				scan();
				ThreadUtil.sleep(mySleepTime);
			}
		}
		catch (Exception e)
		{
			//
			// not a whole lot we can do, so print a stack trace and terminate
			//
			e.printStackTrace();
		}
	}
}
