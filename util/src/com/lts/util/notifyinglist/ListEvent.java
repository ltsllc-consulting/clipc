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
package com.lts.util.notifyinglist;

/**
 * Instances of this class are sent to NotifyingListListener objects when changes occur 
 * to a NotifyingList.
 * 
 * @author cnh
 *
 */
public class ListEvent
{
	public enum EventType
	{
		Insert,
		Update,
		Delete,
		AllChanged
	}
	
	public int index;
	public EventType eventType;
	public Object element;
	
	protected void initialize(EventType eventType, int eventIndex, Object element)
	{
		this.index = eventIndex;
		this.eventType = eventType;
		this.element = element;
	}
	
	
	public ListEvent (EventType etype)
	{
		initialize(etype, -1, null);
	}
	
	
	public ListEvent (EventType eventType, int index, Object element)
	{
		initialize(eventType, index, element);
	}
}
