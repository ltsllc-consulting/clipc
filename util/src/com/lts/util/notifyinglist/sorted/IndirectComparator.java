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
package com.lts.util.notifyinglist.sorted;

import java.util.Comparator;
import java.util.List;

public class IndirectComparator implements Comparator
{
	public List myList;
	public Comparator myBasicComparator;
	
	public IndirectComparator()
	{}
	
	
	public IndirectComparator(Comparator comp, List list)
	{
		myBasicComparator = comp;
		myList = list;
	}
	
	public Object getValue(int index)
	{
		return myList.get(index);
	}
	
	
	@Override
	public int compare(Object o1, Object o2)
	{
		if (!(o1 instanceof Integer) || !(o2 instanceof Integer))
		{
			throw new RuntimeException("About to get a class cast exception");
		}
		Integer i1 = (Integer) o1;
		Integer i2 = (Integer) o2;
		Object v1 = getValue(i1);
		Object v2 = getValue(i2);
		return myBasicComparator.compare(v1, v2);
	}
}
