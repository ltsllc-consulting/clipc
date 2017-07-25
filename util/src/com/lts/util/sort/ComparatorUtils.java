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
package com.lts.util.sort;

import java.util.Comparator;


public class ComparatorUtils
{
	public static Comparator INT_COMPARATOR = new Comparator() {
		@Override
		public int compare(Object o1, Object o2)
		{
			Integer i1 = (Integer) o1;
			Integer i2 = (Integer) o2;
			return i1.compareTo(i2);
		}
	};
	
	public static Comparator LONG_COMPARATOR = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			Long l1 = (Long) o1;
			Long l2 = (Long) o2;
			return l1.compareTo(l2);
		}
	};
	
	
	public static Comparator STRING_COMPARATOR = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 = (String) o1;
			String s2 = (String) o2;
			return s1.compareTo(s2);
		}
	};
	
	public static int compare (long l1, long l2)
	{
		if (l1 > l2)
			return 1;
		else if (l1 < l2)
			return -1;
		else
			return 0;
	}
	
	public static int inverse (long l1, long l2)
	{
		return -1 * compare(l1, l2);
	}
	
	
	public static int compare (int i1, int i2)
	{
		if (i1 > i2)
			return 1;
		else if (i1 < i2)
			return -1;
		else
			return 0;
	}
	
	
	public static int inverse (int i1, int i2)
	{
		return -1 * compare(i1, i2);
	}
}
