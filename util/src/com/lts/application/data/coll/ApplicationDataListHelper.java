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
package com.lts.application.data.coll;

import com.lts.application.data.ApplicationData;
import com.lts.event.ListenerHelper;

public class ApplicationDataListHelper extends ListenerHelper
{

	@Override
	public void notifyListener(Object listener, int type, Object data)
	{
		ADCListener adcl = (ADCListener) listener;
		ADCEvent event = (ADCEvent) data;
		adcl.eventOccurred(event);
	}
	
	
	public void fireElementAdded (ApplicationData element)
	{
		ADCEvent event = new ADCEvent();
		event.element = element;
		event.event = ADCEvent.EventType.add;
		fire(event);
	}


	public void fireAllChanged()
	{
		ADCEvent event = new ADCEvent();
		event.event = ADCEvent.EventType.all;
		fire(event);
	}
	
	public void fireDelete (ApplicationData element)
	{
		ADCEvent event = new ADCEvent();
		event.element = element;
		event.event = ADCEvent.EventType.delete;
		fire(event);
	}
	
	public void fireUpdate (ApplicationData data)
	{
		ADCEvent event = new ADCEvent();
		event.event = ADCEvent.EventType.update;
		event.element = data;
		fire(event);
	}

}
