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

import com.lts.application.data.IdListEvent.EventType;
import com.lts.event.ListenerHelper;

public class IdListListenerHelper extends ListenerHelper
{

	@Override
	public void notifyListener(Object olistener, int type, Object data)
	{
		IdListEvent event = (IdListEvent) data;
		IdListListener listener = (IdListListener) olistener;
		listener.idListEvent(event);
	}
	
	
	public void fireAdd(IdApplicationDataElement element)
	{
		IdListEvent event = new IdListEvent(EventType.Add, element);
		fire(event);
	}
	
	public void fireDelete(IdApplicationDataElement element)
	{
		IdListEvent event = new IdListEvent(EventType.Delete, element);
		fire(event);
	}
	
	
	public void fireUpdate(IdApplicationDataElement element)
	{
		IdListEvent event;
		event = new IdListEvent(EventType.Update, element);
		fire(event);
	}

	public void fireAllChantged()
	{
		IdListEvent event;
		event = new IdListEvent();
		event.event = EventType.AllChanged;
		fire(event);
	}

	
}
