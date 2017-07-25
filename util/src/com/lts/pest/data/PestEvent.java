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

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * This class represents a an event that occurred at a particular point in time.
 * 
 * @author cnh
 */
public class PestEvent implements Serializable, Comparable
{
	private static final long serialVersionUID = 1L;

	protected long myTime;
	
	
	public PestEvent()
	{}
	
	public PestEvent (long time)
	{
		initialize(time);
	}
	
	
	protected void initialize (long time)
	{
		myTime = time;
	}
	
	
	public long getTime()
	{
		return myTime;
	}
	
	public void setTime(long l)
	{
		myTime = l;
	}
	
	
	public int compareTo(Object o)
	{
		PestEvent event = (PestEvent) o;
		if (myTime > event.myTime)
			return 1;
		else if (myTime < event.myTime)
			return -1;
		else
			return 0;
	}
	
	
	protected static final SimpleDateFormat FORMAT =
		new SimpleDateFormat("HH:mm:ss");
	
	public String toString()
	{
		return FORMAT.format(myTime);
	}

}
