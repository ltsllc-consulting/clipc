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
package com.lts.application.data;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A read-only set.
 * 
 * @author cnh
 *
 * @param <E>
 */
public class ImmutableSet<E> extends AbstractSet<E>
{
	protected Map<E,E> myMap;
	
	public ImmutableSet(Map<E,E> map)
	{
		myMap = map;
	}
	
	public ImmutableSet(Collection<E> col)
	{
		myMap = new HashMap<E, E>(col.size());
		for (E e : col)
		{
			myMap.put(e, e);
		}
	}
	
	
	@Override
	public Iterator<E> iterator()
	{
		Iterator<E> iter = myMap.keySet().iterator();
		return new ImmutableIterator<E>(iter);
	}

	@Override
	public int size()
	{
		return myMap.size();
	}
}
