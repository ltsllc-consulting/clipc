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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.lts.util.notifyinglist.NotifyingCollection;

public class CollectionHolder<E> implements NotifyingCollection<E>
{
	protected Collection<E> myCollection;
	protected CollectionListenerHelper myHelper;
	
	protected CollectionHolder()
	{
		initialize();
	}
	
	protected void initialize()
	{
		myHelper = new CollectionListenerHelper();
		myCollection = new ArrayList();
	}
	public CollectionHolder(Collection coll)
	{
		initialize();
		myHelper = new CollectionListenerHelper();
	}
	
	
	public boolean add(E o)
	{
		boolean changed = myCollection.add(o);
		myHelper.fireElementAdded(o);
		return changed;
	}

	public boolean addAll(Collection<? extends E> c)
	{
		boolean changed = false;
		
		for (E element : c)
		{
			boolean localChange = add(element);
			if (localChange && !changed)
				changed = true;
		}
		
		return changed;
	}

	public void clear()
	{
		myCollection.clear();
		myHelper.fireAllChanged();
	}

	public boolean contains(Object o)
	{
		return myCollection.contains(o);
	}

	public boolean containsAll(Collection<?> c)
	{
		return myCollection.containsAll(c);
	}

	public boolean isEmpty()
	{
		return myCollection.isEmpty();
	}

	public Iterator<E> iterator()
	{
		return myCollection.iterator();
	}

	public boolean remove(Object o)
	{
		boolean changed = myCollection.remove(o);
		if (changed)
		{
			myHelper.fireElementRemoved(o);
		}
		
		return changed;
	}

	public boolean removeAll(Collection<?> c)
	{
		boolean changed = false;
		
		for (Object o : c)
		{
			boolean localChange = remove(o);
			if (localChange && !changed)
				changed = true;
		}
		
		return changed;
	}

	public boolean retainAll(Collection<?> c)
	{
		boolean changed = false;
		
		for (Object o : c)
		{
			boolean localChange = remove(o);
			if (localChange && !changed)
				changed = true;
		}
		
		return changed;
	}

	public int size()
	{
		return myCollection.size();
	}

	public Object[] toArray()
	{
		return myCollection.toArray();
	}

	public <T> T[] toArray(T[] a)
	{
		return myCollection.toArray(a);
	}


	public void addListener(CollectionListener listener)
	{
		myHelper.addListener(listener);
	}


	public boolean removeListener(CollectionListener listener)
	{
		return myHelper.removeListener(listener);
	}
	
	
}
