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
package com.lts.compare;

import java.util.Comparator;

public class IntComparator
{
	public int compare(Object o1, Object o2)
	{
		Integer iobj = (Integer) o1;
		int i1 = iobj;
		
		iobj = (Integer) o2;
		int i2 = iobj;
		
		if (i1 > i2)
			return 1;
		else if (i1 < i2)
			return -1;
		else
			return 0;
	}
	
	public static class NormalComparator implements Comparator
	{
		public int compare (Object o1, Object o2)
		{
			return compare(o1, o2);
		}
	}
	
	
	public static class InverseComparator implements Comparator
	{
		public int compare (Object o1, Object o2)
		{
			return -1 * compare(o1, o2);
		}
	}

	public static Comparator NORMAL = new NormalComparator();
	public static Comparator INVERSE = new InverseComparator();
}
