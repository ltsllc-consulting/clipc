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
package com.lts.collection;

import java.util.Collection;

/**
 * Signifies that a change of some sort has occurred in the collection the listener is
 * watching.
 * 
 * @author cnh
 *
 */
public class NotifyingCollectionEvent
{
	public enum CollectionEventType
	{
		Add,
		Delete,
		Update,
		AllChanged
	}
	
	public Collection collection;
	public Object element;
	public CollectionEventType eventType;
	
	public NotifyingCollectionEvent ()
	{}
	
	public NotifyingCollectionEvent (CollectionEventType pEventType, Object pElement, Collection pCollection)
	{
		eventType = pEventType;
		collection = pCollection;
		element = pElement;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("CollectionEvent{ ");
		sb.append("collection: ");
		sb.append(collection.hashCode());
		sb.append(", event: ");
		sb.append(eventType);
		sb.append(", element: ");
		sb.append(element);
		sb.append("}");
		
		return sb.toString();
	}
}
