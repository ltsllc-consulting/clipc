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
package com.lts.pest.data;

/**
 * An event that describes a change to the GatherMetaData
 * 
 * <P>
 * This class does not know or care when gathers should or were supposed to occur:
 * it only deals with the data it carries. 
 * </P>
 * 
 * <P>
 * The various events and their meanings are:
 * </P>
 * 
 * <UL>
 * <LI>
 * {@link EventTypes#StartGathering} - the system is now gathering data.  The 
 * Period property specifies the gather period.  
 * </LI>
 * 
 * <LI>{@link EventTypes#StopGathering} - stop gathering data.</LI>
 * <LI>{@link EventTypes#PeriodChanged} - change the interval between gathers.
 * The Period property specifies the new period when gathers should occur.
 * </LI>
 * </UL>
 * @author cnh
 */
public class GatherMetaDataEvent
{
	public enum EventTypes
	{
		StartGathering,
		StopGathering,
		PeriodChanged
	}
	
	private long myPeriod;
	
	public long getPeriod()
	{
		return myPeriod;
	}
	
	public void setPeriod (long period)
	{
		myPeriod = period;
	}
	
	
	private EventTypes myEventType;
	
	public EventTypes getEventType()
	{
		return myEventType;
	}
	
	public void setEventType(EventTypes eventType)
	{
		myEventType = eventType;
	}
	
	
	public GatherMetaDataEvent()
	{}
	
	
	public GatherMetaDataEvent (EventTypes eventType)
	{
		myEventType = eventType;
	}
	
	public GatherMetaDataEvent (EventTypes eventType, long period)
	{
		myEventType = eventType;
		myPeriod = period;
	}
}
