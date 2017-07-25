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

/**
 * A class to simplify the implementation of creating an 
 * ApplicationDataCollectionListener.
 * 
 * <P>
 * This class defines methods for each of the different events defined by 
 * {@link ADCEvent.EventType}.  The default methods simply return without taking 
 * any action.
 * </P>
 * 
 * @author cnh
 */
public class ADCListenerAdaptor implements ADCListener
{
	public void eventOccurred(ADCEvent event)
	{
		switch (event.event)
		{
			case add :
				elementAdded(event.element);
				break;
				
			case delete :
				elementDeleted(event.element);
				break;
				
			case update :
				elementUpdated(event.element);
				break;
				
			case all :
				elementAllChanged();
				break;
				
				
			default :
				throw new IllegalArgumentException(event.event.name());
		}
	}

	protected void elementAllChanged()
	{
		// TODO Auto-generated method stub
		
	}

	protected void elementUpdated(ApplicationData element)
	{
	}

	protected void elementDeleted(ApplicationData element)
	{
	}

	protected void elementAdded(ApplicationData element)
	{
	}
}
