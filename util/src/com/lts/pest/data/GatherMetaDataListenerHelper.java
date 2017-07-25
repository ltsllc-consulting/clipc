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
package com.lts.pest.data;

import com.lts.event.ListenerHelper;
import com.lts.pest.data.GatherMetaDataEvent.EventTypes;

public class GatherMetaDataListenerHelper extends ListenerHelper
{
	@Override
	public void notifyListener(Object l, int type, Object data)
	{
		GatherMetaDataEvent event = (GatherMetaDataEvent) data;
		GatherMetaDataListener listener = (GatherMetaDataListener) l;
		listener.gatherMetaDataEvent(event);
	}

	public void firePeriodChanged (long period)
	{
		GatherMetaDataEvent event = new GatherMetaDataEvent();
		event.setEventType(EventTypes.PeriodChanged);
		event.setPeriod(period);
		fire(event);
	}
	
	public void fireStartGathering (long period)
	{
		GatherMetaDataEvent event = new GatherMetaDataEvent();
		event.setEventType(EventTypes.StartGathering);
		event.setPeriod(period);
		fire(event);
	}
	
	public void fireStopGathering ()
	{
		GatherMetaDataEvent event = new GatherMetaDataEvent();
		event.setEventType(EventTypes.StopGathering);
		fire(event);
	}
}