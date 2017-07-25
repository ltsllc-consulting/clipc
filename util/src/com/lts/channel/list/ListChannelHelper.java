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
package com.lts.channel.list;

import com.lts.event.ListenerHelper;

public class ListChannelHelper extends ListenerHelper
{

	@Override
	public void notifyListener(Object client, int type, Object data)
	{
		ListChannelListener listener = (ListChannelListener) client;
		ListChannelEvent event = (ListChannelEvent) data;
		ListChannel list = event.getList();
		int oldIndex = event.getOldIndex();
		int newIndex = event.getNewIndex();
		
		switch (event.getEventType())
		{
			case ListChannelEvent.EVENT_ADD :
				listener.addElement(event, list, oldIndex);
				break;
				
			case ListChannelEvent.EVENT_ALL_CHANGED :
				listener.allChanged(event, list);
				break;
				
			case ListChannelEvent.EVENT_MOVE :
				listener.moveElement(event, list, oldIndex, newIndex);
				break;
				
			case ListChannelEvent.EVENT_REMOVE :
				listener.removeElement(event, list, oldIndex);
				break;
			
			default :
				throw new IllegalArgumentException();
		}
	}

}
