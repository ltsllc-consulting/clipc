//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the util library.
//
//  The util library is free software; you can redistribute it and/or modify it
//  under the terms of the Lesser GNU General Public License as published by
//  the Free Software Foundation; either version 2.1 of the License, or (at
//  your option) any later version.
//
//  The util library is distributed in the hope that it will be useful, but
//  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
//  or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
//  License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the util library; if not, write to the Free Software Foundation,
//  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
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

import java.util.HashMap;
import java.util.Map;

/**
 * A map that "goes" in both directions: from key to value and value to key.
 */
public class DoubleMap<K, V>
{
	protected Map<K,V> keyToValueMap = new HashMap<K,V>();
	protected Map<V,K> valueToKeyMap = new HashMap<V,K>();
	
	public V keyToValue (K key)
	{
		return keyToValueMap.get(key);
	}
	
	public K valueToKey (V value)
	{
		return valueToKeyMap.get(value);
	}
	
	public DoubleMap()
	{}
	
	public DoubleMap (Object[] spec)
	{
		for (int i = 0; i < spec.length; i += 2)
		{
			K key = (K) spec[i];
			V value = (V) spec[1+i];
			put (key, value);
		}
	}
	
	public DoubleMap (Object[][] spec)
	{
		for (int i = 0; i < spec.length; i++)
		{
			Object[] row = spec[i];
			put((K)row[0], (V)row[1]);
		}
	}
	
	
	public void put (K key, V value)
	{
		if (null == key)
			throw new IllegalArgumentException();
		
		if (null == value)
			remove(key);
		else
		{
			keyToValueMap.put(key, value);
			valueToKeyMap.put(value, key);
		}
	}
	
	public void addChecked (K key, V value)
	{
		if (null == key || null == value)
			throw new IllegalArgumentException();

		if (null != keyToValueMap.get(key))
			throw new IllegalArgumentException();
		
		if (null != valueToKeyMap.get(key))
			throw new IllegalArgumentException();
		
		keyToValueMap.put(key, value);
		valueToKeyMap.put(value, key);
	}
	
	
	public V get (K key)
	{
		return getKeyToValueMap().get(key);
	}
	
	public void remove (K key)
	{
		removeKey(key);
	}
	
	public void removeKey (K key)
	{
		V value = keyToValueMap.get(key);
		keyToValueMap.remove(key);
		
		if (null != value)
			valueToKeyMap.remove(value);
	}
	
	public void removeValue (V value)
	{
		K key = valueToKeyMap.get(value);
		valueToKeyMap.remove(value);
		
		if (null != key)
			keyToValueMap.remove(key);
	}
	
	
	public Map<K,V> getKeyToValueMap()
	{
		return keyToValueMap;
	}
	
	public Map<V,K> getValueToKeyMap ()
	{
		return valueToKeyMap;
	}
	
	public void clear ()
	{
		keyToValueMap = new HashMap();
		valueToKeyMap = new HashMap();
	}
}
