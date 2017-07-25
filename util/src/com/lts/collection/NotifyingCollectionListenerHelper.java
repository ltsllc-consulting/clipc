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

import com.lts.collection.NotifyingCollectionEvent.CollectionEventType;
import com.lts.event.ListenerHelper;

/**
 * A class that manages and handles updates for {@link NotifyingCollection}.
 * @author cnh
 *
 */
public class NotifyingCollectionListenerHelper extends ListenerHelper
{
	protected NotifyingCollection myCollection;
	
	public NotifyingCollectionListenerHelper(NotifyingCollection collection)
	{
		myCollection = collection;
	}
	
	
	@Override
	public void notifyListener(Object olistener, int type, Object data)
	{
		NotifyingCollectionListener listener;
		listener = (NotifyingCollectionListener) olistener;
		NotifyingCollectionEvent event = (NotifyingCollectionEvent) data;
		listener.collectionEvent(event);
	}

	public void fireAdd(Object element)
	{
		NotifyingCollectionEvent event;
		event = new NotifyingCollectionEvent(CollectionEventType.Add, element, myCollection);
		fire(event);
	}
	
	public void fireDelete(Object element)
	{
		NotifyingCollectionEvent event;
		event = new NotifyingCollectionEvent(CollectionEventType.Delete, element, myCollection);
		fire(event);
	}
	
	public void fireUpdate(Object element)
	{
		NotifyingCollectionEvent event;
		event = new NotifyingCollectionEvent(CollectionEventType.Update, element, myCollection);
		fire(event);
	}
	
	public void fireAllChanged()
	{
		NotifyingCollectionEvent event;
		event = new NotifyingCollectionEvent(CollectionEventType.AllChanged, null, myCollection);
		fire(event);
	}
}
