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

import com.lts.util.StringUtils;

/**
 * A {@link FilteredPropertyListener} that fires only if the property name matches
 * a certain value.
 * 
 * <P>
 * The property name is not case sensitive --- "foo" is the same as "FoO" as far as
 * this class is concerned.
 * </P>
 * 
 * <P>
 * This class can be used to watch for changes to groups of properties such as 
 * "foo.display.directoryView".
 * </P>
 * 
 * @author cnh
 *
 */
public class PrefixPropertyListener extends FilteredPropertyListener
{
	private String myPrefix;
	
	
	public PrefixPropertyListener()
	{}
	
	public PrefixPropertyListener(String prefix, ApplicationPropertyListener listener)
	{
		initialize(prefix, listener);
	}
	
	protected void initialize(String prefix, ApplicationPropertyListener listener)
	{
		super.initialize(listener);
		myPrefix = prefix;
	}
	
	
	public String getName()
	{
		return myPrefix;
	}


	public void setName(String name)
	{
		myPrefix = StringUtils.toLowerCase(name);
	}


	@Override
	public boolean passesFilter(ApplicationPropertyEvent event)
	{
		String s = event.getName().toLowerCase();
		return (s.startsWith(myPrefix));
	}

}
