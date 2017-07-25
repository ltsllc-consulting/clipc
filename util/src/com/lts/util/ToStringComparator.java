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
package com.lts.util;

import java.util.Comparator;

/**
 * A comparator that calls toString on its objects and then uses StringComparator
 * to compare them.
 * 
 * @author cnh
 *
 */
public class ToStringComparator implements Comparator
{
	private StringComparator myComparator;
	
	public ToStringComparator (boolean ascending, boolean caseSensitive)
	{
		myComparator = new StringComparator(ascending, caseSensitive);
	}
	
	public int compare(Object o1, Object o2)
	{
		String s1 = o1.toString();
		String s2 = o2.toString();
		
		return myComparator.compare(s1, s2);
	}

	static public Comparator CASE_SENSITIVE_ASCENDING = new ToStringComparator(true, true);
	static public Comparator CASELESS_ASCENDING = new ToStringComparator(true, false);
	static public Comparator CASE_SENSITIVE_DESCENDING = new ToStringComparator(false, true);
	static public Comparator CASELESS_DESCENDING = new ToStringComparator(false, false);
}
