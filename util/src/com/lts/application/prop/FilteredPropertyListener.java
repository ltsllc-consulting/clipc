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

/**
 * An {@link ApplicationPropertyListener} that fires only for an event such that
 * meets certain criteria.
 * 
 * <P>
 * "certain criteria" are defined by the {@link #passesFilter(ApplicationPropertyEvent)} 
 * method, which concrete subclasses must define.  Clients of the class must also 
 * supply the listener that will receive matching events.
 * </P>
 * 
 * @author cnh
 *
 */
abstract public class FilteredPropertyListener implements ApplicationPropertyListener
{
	abstract public boolean passesFilter (ApplicationPropertyEvent event);
	
	private ApplicationPropertyListener myListener;
	
	public ApplicationPropertyListener getListener()
	{
		return myListener;
	}

	public void setListener(ApplicationPropertyListener listener)
	{
		myListener = listener;
	}

	@Override
	public void propertyEvent(ApplicationPropertyEvent event)
	{
		if (passesFilter(event))
			myListener.propertyEvent(event);
	}

	public void initialize(ApplicationPropertyListener listener)
	{
		myListener = listener;
	}
}
