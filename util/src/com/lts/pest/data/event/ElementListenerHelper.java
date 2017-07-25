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
package com.lts.pest.data.event;

import com.lts.event.ListenerHelper;

/**
 * A class that simplifies ElementHolder classes by defining methods that take care
 * of tracking and notifying ElementHolderListeners.
 * 
 * <P>
 * Classes that implement {@link ElementHolder} can essentially delegate all their
 * listener responsibilities to this class.  Add and remove listener methods are
 * defined, as well as various "fire" methods that the holder can call to signal
 * the different changes defined by {@link ElementHolderEvent}.  For example, to
 * signal that an element has been added to the holder, clients can call
 * {@link #fireElementAdded(Object)}.
 * </P>
 * 
 * @author cnh
 *
 */
public class ElementListenerHelper extends ListenerHelper
{
	
	@Override
	public void notifyListener(Object listener, int type, Object data)
	{
		ElementHolderEvent event = (ElementHolderEvent) data;
		ElementHolderListener el = (ElementHolderListener) listener;
		el.elementEvent(event);
	}

	
	public void fireElementAdded (Object element)
	{
		ElementHolderEvent event = new ElementHolderEvent(ElementHolderEvent.EVENT_ELEMENT_ADDED, element, null);
		fire(event.getEventType(), event);
	}
	
	public void fireElementRemoved (Object element)
	{
		ElementHolderEvent event = new ElementHolderEvent(ElementHolderEvent.EVENT_ELEMENT_REMOVED, element, null);
		fire(event.getEventType(), event);
	}
	
	public void fireElementChanged (Object oldVersion, Object newVersion)
	{
		ElementHolderEvent event = new ElementHolderEvent(ElementHolderEvent.EVENT_ELEMENT_CHANGED, newVersion, oldVersion);
		fire(event.getEventType(), event);
	}
	
	public void fireAllChanged ()
	{
		ElementHolderEvent event = new ElementHolderEvent(ElementHolderEvent.EVENT_ALL_CHANGED);
		fire(event.getEventType(), event);
	}
	
	public void fireDeserialized ()
	{
		ElementHolderEvent event = new ElementHolderEvent(ElementHolderEvent.EVENT_POST_DESERIALIZATION);
		fire(event.getEventType(), event);
	}
}
