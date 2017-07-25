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

public class ApplicationPropertyEvent
{
	public enum EventTypes {
		create,
		change,
		delete,
		allChanged
	}

	private EventTypes myType;
	private String myName;
	private String myValue;;
		
	public EventTypes getType()
	{
		return myType;
	}


	public void setType(EventTypes type)
	{
		myType = type;
	}


	public String getName()
	{
		return myName;
	}


	public void setName(String name)
	{
		myName = name;
	}


	public String getValue()
	{
		return myValue;
	}


	public void setValue(String value)
	{
		myValue = value;
	}

	public ApplicationPropertyEvent(EventTypes type, String name)
	{
		initialize(type, name, null);
	}
	
	
	public ApplicationPropertyEvent(EventTypes type, String name, String value)
	{
		initialize(type, name, value);
	}
	
	
	protected void initialize(EventTypes type, String name, String value)
	{
		myType = type;
		myName = name;
		myValue = value;
	}
	
	public String toString()
	{
		String msg = 
			"Property event {"
			+ myType
			+ ", " + myName
			+ ", " + myValue + "}";
		
		return msg;
			
	}
}
