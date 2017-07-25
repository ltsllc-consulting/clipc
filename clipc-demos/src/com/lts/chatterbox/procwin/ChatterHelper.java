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

import com.lts.event.ListenerHelper;

public class ChatterHelper extends ListenerHelper
{
	public void fireChatter (int count, String message)
	{
		ChatterMessage msg = new ChatterMessage(count, message);
		fire(msg);
	}
	
	public void notifyListener (Object olistener, Object omessage)
	{
		ChatterListener listener = (ChatterListener) olistener;
		ChatterMessage message = (ChatterMessage) omessage;
		listener.chatter(message);
	}
	
}
