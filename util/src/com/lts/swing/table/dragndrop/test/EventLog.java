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
package com.lts.swing.table.dragndrop.test;

import java.util.ArrayList;
import java.util.List;

public class EventLog
{
	public static enum LogTypes {
		Default,
		Callgraph
	};
	
	protected List<RecordingEvent> myLog;
	static private EventLog ourInstance;
	static private CallGraphLogFactory ourFactory;
	
	protected LogTranscriber myTranscriber;
	
	private int mySequence;
	
	static public EventLog getInstance()
	{
		if (null == ourInstance)
		{
			initializeLog();
		}
		
		return ourInstance;
	}
	
	synchronized static private void initializeLog()
	{
		ourFactory = new CallGraphLogFactory();
		ourInstance = ourFactory.createEventLog();
	}

	public EventLog()
	{
		myLog = new ArrayList<RecordingEvent>();
		mySequence = 0;
	}
	
	static public void add(RecordingEvent event)
	{
		getInstance().addEvent(event);
	}
	
	synchronized public void addEvent(RecordingEvent event)
	{
		myLog.add(event);
		event.setSequence(mySequence);
		mySequence++;
	}
	
	static public RecordingEvent[] events()
	{
		return (RecordingEvent[]) getInstance().getEvents();
	}
	
	synchronized public Object[] getEvents()
	{
		return myLog.toArray();
	}
	
	
	static public String transcript()
	{
		return getInstance().getTranscript();
	}
	
	
	synchronized public String getTranscript()
	{
		return myTranscriber.transcribe(this);
	}
	
	static public void resetInstance()
	{
		getInstance().reset();
	}
	
	
	synchronized public void reset()
	{
		myLog = new ArrayList<RecordingEvent>();
	}

	public void enterMethod()
	{
		RecordingEvent event = new StackTraceEvent();
		addEvent(event);
	}
	
	
	public void leaveMethod()
	{
		RecordingEvent event = new StackTraceEvent();
		addEvent(event);
	}

	public void setTranscriber(LogTranscriber trans)
	{
		myTranscriber = trans;
	}
}
