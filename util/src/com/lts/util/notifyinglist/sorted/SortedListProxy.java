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

import java.util.Collections;
import java.util.Comparator;

import com.lts.util.notifyinglist.NotifyingList;

/**
 * A list that obtains its data from a NotifyingList but presents that data to clients in
 * a different order.
 * <P>
 * This class contains a {@link NotifyingList} that it presents in an order determined by
 * a Comparator that is passed to it when the instance is created.
 * </P>
 * 
 * @author cnh
 * @param <E>
 */
public class SortedListProxy<E> extends IndirectList<E> 
{
	protected Comparator<E> myComparator;
	
	protected void initialize (NotifyingList actual, Comparator<E> comparator)
	{
		myComparator = comparator;
		super.initialize(actual);
	}
	
	public SortedListProxy(NotifyingList actual, Comparator<E> comp)
	{
		initialize(actual, comp);
	}
	
	
	@Override
	protected int findIndexForInsert(E e, int actualIndex)
	{
		int insertPoint = Collections.binarySearch(this, e, myComparator);
		// (-(insertion point) - 1) = returnValue
		// returnValue = -1 * (insertPoint + 1)
		// (-1)*returnValue = (insertPoint + 1)
		// -1*returnValue - 1 = insertPoint
		// insertPoint = ((-1) * returnValue) - 1
		
		if (insertPoint < 0)
			insertPoint = (-1 * insertPoint) - 1;
		
		return insertPoint;
	}

	@Override
	public Object getDataObject()
	{
		return myActual.getDataObject();
	}

	
}
