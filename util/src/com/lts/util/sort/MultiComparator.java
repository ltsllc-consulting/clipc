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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MultiComparator implements Comparator
{
	protected List<Comparator> myComparators;
	
	public MultiComparator()
	{
		initialize();
	}
	
	public MultiComparator (Comparator[] comps)
	{
		initialize(comps);
	}
	
	
	public MultiComparator(Comparator comp)
	{
		initialize(comp, null, null, null);
	}
	
	public MultiComparator(Comparator comp, Comparator comp2)
	{
		initialize(comp, comp2, null, null);
	}
	
	public MultiComparator(Comparator comp, Comparator comp2, Comparator comp3)
	{
		initialize(comp, comp2, comp3, null);
	}
	
	public MultiComparator(Comparator comp, Comparator comp2, Comparator comp3, Comparator comp4)
	{
		initialize(comp, comp2, comp3, comp4);
	}
	
	protected void initialize()
	{
		initialize(null, null, null, null);
	}
	
	protected void initialize (Comparator c1, Comparator c2, Comparator c3, Comparator c4)
	{
		myComparators = new ArrayList<Comparator>();
		
		if (null != c1)
			myComparators.add(c1);
		
		if (null != c2)
			myComparators.add(c2);
		
		if (null != c3)
			myComparators.add(c3);
		
		if (null != c4)
			myComparators.add(c4);
	}
	
	
	protected void initialize (Comparator[] comps)
	{
		myComparators = new ArrayList<Comparator>();
		
		for (Comparator comp : comps)
		{
			if (null != comp)
				myComparators.add(comp);
		}
	}
	
	
	public int compare(Object o1, Object o2)
	{
		int result = 0;
		
		for (Comparator comp : myComparators)
		{
			result = comp.compare(o1, o2);
			if (0 != result)
				break;
		}
		
		return result;
	}

}
