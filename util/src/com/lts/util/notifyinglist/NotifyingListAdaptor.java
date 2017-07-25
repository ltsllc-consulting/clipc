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

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Make a regular list look like a {@link NotifyingList}.
 * 
 * <P>
 * Given a list, this class will generate events for a {@link NotifyingListListener} 
 * based on calls to {@link List} methods.  The underlying list does not have to 
 * worry about notifications and the like, it just continues on oblivously.
 * </P>
 * 
 * @author cnh
 *
 * @param <E> The class of the elements of the list.
 */
public class NotifyingListAdaptor<E>
	extends AbstractList<E>
	implements NotifyingList<E>
{
	protected List<E> myList;
	protected NotifyingListHelper myHelper;
	
	public NotifyingListAdaptor(Collection<E> col)
	{
		initialize();
		myList.addAll(col);
	}
	
	public NotifyingListAdaptor()
	{
		initialize();
	}

	public void initialize()
	{
		myList = new ArrayList();
		myHelper = new NotifyingListHelper();
	}
	
	protected void initialize(List<E> list)
	{
		initialize();
		myList = list;
	}
	
	@Override
	synchronized public E get(int index)
	{
		return myList.get(index);
	}

	
	@Override
	synchronized public void add(int index, E element)
	{
		myList.add(index, element);
		myHelper.fireInsert(index, element);
	}


	@Override
	synchronized public E remove(int index)
	{
		E element = myList.get(index);
		myList.remove(index);
		myHelper.fireDelete(index, element);
		return element;
	}


	@Override
	synchronized public E set(int index, E element)
	{
		E oldValue = myList.get(index);
		myList.set(index, element);
		myHelper.fireUpdate(index, element);
		return oldValue;
	}

	synchronized public void update (E e)
	{
		int index = myList.indexOf(e);
		myHelper.fireUpdate(index, e);
	}

	@Override
	synchronized public int size()
	{
		return myList.size();
	}

	@Override
	synchronized public void addListener(NotifyingListListener listener)
	{
		myHelper.addListener(listener);
	}

	@Override
	synchronized public boolean removeListener(NotifyingListListener listener)
	{
		return myHelper.removeListener(listener);
	}

	synchronized public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object getDataObject()
	{
		return myList;
	}

	public void replaceWith(List<E> list)
	{
		myList.clear();
		myList.addAll(list);
		myHelper.fireAllChanged();
	}

	public void nodeChanged(E e)
	{
		int index = myList.indexOf(e);
		if (-1 != index)
		{
			myHelper.fireUpdate(index, e);
		}
	}
	
	
}
