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
package com.lts.util.notifyinglist;

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import com.lts.exception.NotImplementedException;

/**
 * Iterator for the NotifyingListProxy class.
 * 
 * <P>
 * This class throws {@link NotImplementedException} for the set and remove optional
 * methods.
 * </P>
 * 
 * @author cnh
 *
 * @param <E>
 */
public class ProxyIterator<E> implements ListIterator<E>
{
	protected int myIndex;
	protected List<Integer> myVirtual;
	protected List<E> myActual;
	
	public ProxyIterator(List<Integer> virtual, List<E> actual, int start)
	{
		myIndex = start - 1;
		myVirtual = virtual;
		myActual = actual;
	}
	
	
	@Override
	public void add(Object o)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNext()
	{
		int nextIndex = 1 + myIndex;		
		return nextIndex < myVirtual.size(); 
	}

	@Override
	public boolean hasPrevious()
	{
		int size = myVirtual.size();
		return (size > 0) && (myIndex > 0);
	}

	protected E virtualToActual(int virtual)
	{
		int actual = myVirtual.get(virtual);
		return myActual.get(actual);
	}
	
	@Override
	public E next()
	{
		myIndex++;
		if (myIndex >= myVirtual.size())
		{
			throw new NoSuchElementException();
		}
		
		return virtualToActual(myIndex);
	}

	@Override
	public int nextIndex()
	{
		return 1 + myIndex;
	}

	@Override
	public E previous()
	{
		if (myIndex < 0)
			throw new NoSuchElementException();
		else
		{
			E o = virtualToActual(myIndex);
			myIndex--;
			return o;
		}
	}

	@Override
	public int previousIndex()
	{
		if (myIndex < 0)
			return -1;
		else
			return myIndex;
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void set(E e)
	{
		throw new UnsupportedOperationException();
	}

}
