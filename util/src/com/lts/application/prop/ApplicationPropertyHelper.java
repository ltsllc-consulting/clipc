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
package com.lts.application.prop;

import com.lts.application.prop.ApplicationPropertyEvent.EventTypes;
import com.lts.event.ListenerHelper;

public class ApplicationPropertyHelper extends ListenerHelper
{
	public void fireCreate(String name, String value)
	{
		ApplicationPropertyEvent event = 
			new ApplicationPropertyEvent(EventTypes.create, name, value);
		fire(event);
	}
	
	public void fireDelete(String name, String oldValue)
	{
		ApplicationPropertyEvent event =
			new ApplicationPropertyEvent(EventTypes.delete, name, oldValue);
		fire(event);
	}
	
	public void fireChange(String name, String oldValue)
	{
		ApplicationPropertyEvent event = 
			new ApplicationPropertyEvent(EventTypes.change, name, oldValue);
		fire(event);
	}
	
	public void fireAllChanged()
	{
		ApplicationPropertyEvent event = 
			new ApplicationPropertyEvent(EventTypes.allChanged, null, null);
		fire(event);
	}

	@Override
	public void notifyListener(Object olistener, Object oevent)
	{
		ApplicationPropertyListener listener = (ApplicationPropertyListener) olistener;
		ApplicationPropertyEvent event = (ApplicationPropertyEvent) oevent;
		listener.propertyEvent(event);
	}
	
	
	
}
