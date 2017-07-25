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
package com.lts.chatterbox.procwin;

import com.lts.chatterbox.events.ChatterEvent;
import com.lts.chatterbox.events.ChatterListener;
import com.lts.chatterbox.monitor.LimitedQueueListModel;
import com.lts.chatterbox.smem.Channel;


public class ChatterListModelAdaptor implements ChatterListener
{
	private LimitedQueueListModel myModel;

	@Override
	public void chatter(ChatterEvent msg)
	{
		String s = msg.source + " " + msg.message;
		myModel.add(s);
	}

	public void setListModel(LimitedQueueListModel model)
	{
		myModel = model;
	}
	
	public void setChatterSource (Channel receiver)
	{
		receiver.addListener(this);
	}
	
	public void initialize(LimitedQueueListModel model, Channel receiver)
	{
		setListModel(model);
		setChatterSource(receiver);
	}
	
	public ChatterListModelAdaptor(LimitedQueueListModel model, Channel receiver)
	{
		initialize(model, receiver);
	}
}
