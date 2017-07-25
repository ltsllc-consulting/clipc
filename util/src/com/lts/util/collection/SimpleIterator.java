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
package com.lts.util.collection;

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A class that is intended to simplify creating new ListIterators.
 * 
 * <P>
 * This class provides a simple-minded, index based approach.  It keeps a reference
 * to the "containing" list, and index for the Next index.  Operations such as next
 * and previous update the index and call-through to the underlying list.
 * </P>
 * 
 * @author cnh
 *
 */
public class SimpleIterator<E> implements ListIterator
{
	protected boolean myModificationsSupported = true;
	
	/**
	 * This is the index of the last element returned by next or previous.
	 */
	protected int myCursor = -1;
	
	/**
	 * This is, quite simply, the index of the last element returned by either next 
	 * or previous.
	 */
	protected int myIndexOfLastReturned = -1;
	
	protected List<E> myList;
	protected boolean myInModifiableState = false;
	
	@Override
	public void add(Object o)
	{
		if (!myModificationsSupported)
		{
			throw new UnsupportedOperationException();
		}
		
		if (myCursor < 0 || myCursor >= myList.size())
		{
			throw new NoSuchElementException();
		}
		
		if (myInModifiableState)
			myInModifiableState = false;
		else
		{
			throw new IllegalStateException();
		}
		
		E e = (E) o;
		
		if (-1 == myCursor)
			myList.add(e);
		else
			myList.add(1 + myCursor, e);
	}

	@Override
	public boolean hasNext()
	{
		return ((1 + myCursor) < myList.size());
	}

	@Override
	public boolean hasPrevious()
	{
		return (myCursor >= 0);
	}

	@Override
	public Object next()
	{
		myCursor++;
		
		if (myCursor >= myList.size())
		{
			throw new NoSuchElementException();
		}
		
		myInModifiableState = true; 
		return myList.get(myCursor);
	}

	@Override
	public int nextIndex()
	{
		if ((1 + myCursor) < myList.size())
			return 1 + myCursor;
		else
			return myList.size();
	}

	@Override
	public Object previous()
	{
		if (myCursor >= 0 && myCursor < myList.size())
		{
			myInModifiableState = true;
			myCursor--;
			return myList.get(myCursor);
		}
		else 
		{
			throw new NoSuchElementException();
		}
		
	}

	@Override
	public int previousIndex()
	{
		if ((myCursor - 1) < 0)
			return -1;
		else
			return (myCursor - 1);
	}

	@Override
	public void remove()
	{
		if (myInModifiableState)
			myInModifiableState = false;
		else
		{
			throw new IllegalStateException();
		}
		
		
	}

	@Override
	public void set(Object e)
	{
		// TODO Auto-generated method stub
		
	}
	
}
