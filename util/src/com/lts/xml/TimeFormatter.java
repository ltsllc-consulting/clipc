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
package com.lts.xml;

import java.util.Date;

/**
 * A SimpleDateFormatter that uses the number of milliseconds as its format
 * 
 * <P>
 * That is, this class inputs/outputs {@link Date#getTime()} as its value.
 * </P>
 * 
 * @author cnh
 *
 */
public class TimeFormatter
{
	private static final long serialVersionUID = 1L;

	public TimeFormatter()
	{}
	
	public Date parse(String s)
	{
		long time;
		
		try
		{
			time = new Long(s);
		}
		catch (NumberFormatException e)
		{
			return null;
		}
		
		return new Date(time);
	}
	
	public String format(Date d)
	{
		return format(d.getTime());
	}
	
	public String format(long time)
	{
		return Long.toString(time);
	}
}
