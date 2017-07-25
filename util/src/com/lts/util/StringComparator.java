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
 * A Comparator that compares strings modified by ascending/descending and 
 * case sensitivity.
 * 
 * @author cnh
 */
public class StringComparator implements Comparator<String>
{
	private boolean ascending;
	private boolean caseSensitive;
	
	
	public StringComparator (boolean isAscending, boolean isCaseSensitive)
	{
		ascending = isAscending;
		caseSensitive = isCaseSensitive;
	}
	
	
	public int compare(String s1, String s2)
	{
		int result;
		if (caseSensitive)
			result = s1.compareTo(s2);
		else
			result = s1.compareToIgnoreCase(s2);
		
		if (!ascending)
			result = -1 * result;
		
		return result;
	}
	
	
	static public Comparator COMPARATOR_CASELESS = new StringComparator(true, false);
	static public Comparator COMPARATOR = new StringComparator(true, true);
	static public Comparator COMPARATOR_ASCENDING_CASELESS = 
		new StringComparator(false, false);
	static public Comparator COMPARATOR_ASCENDING = 
		new StringComparator(false, true);
}
