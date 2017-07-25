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
package com.lts.util.notifyingmap;

import java.util.Map;
import java.util.Set;

/**
 * A class that supports get,remove,update and keySet.
 * 
 * <P>
 * The class has similarities to the java {@link Map} interface, but differs in 
 * the following ways:
 * </P>
 * 
 * <UL>
 * <LI>It is much simpler to implement</LI>
 * <LI>Clients can register to be notified when the map changes.</LI>
 * <LI>The {@link #keySet()} is immutable.
 * </UL>
 *  
 * @author cnh
 *
 */
public interface NotifyingMap<K,V>
{
	public boolean addListener(NotifyingMapListener listener);
	public boolean removeListener(NotifyingMapListener listener);
	
	public V get (K key);
	public void update(K key, V value);
	public V remove (K key);
	public Set<K> keySet();
}
