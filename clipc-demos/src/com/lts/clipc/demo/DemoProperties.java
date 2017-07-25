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
package com.lts.clipc.demo;

import java.util.ArrayList;
import java.util.List;

import com.lts.application.prop.ApplicationProperties;

public class DemoProperties extends ApplicationProperties
{
	private static final long serialVersionUID = 1L;

	public static final String PROP_PREFIX = "demos";
	
	public static final String PROP_ACCOUNT_FILE = 
		PROP_PREFIX + ".accountFile";
	
	public static final String PROP_SEMAPHORE_FILE =
		PROP_PREFIX + ".semaphoreFile";
	
	public static final String DEFAULT_ACCOUNT_FILE =
		"/temp/clipc/account.txt";
	
	public static final String DEFAULT_SEMAPHORE =
		"/temp/clipc/semaphore.txt";
		
	
	public enum AppProperties
	{
		LastAccountFile(PROP_ACCOUNT_FILE, DEFAULT_ACCOUNT_FILE),
		LastSemaphoreFile(PROP_SEMAPHORE_FILE, DEFAULT_SEMAPHORE);
		
		public String name;
		public String defaultValue;
		
		
		private AppProperties(String name, String defaultValue)
		{
			this.name = name;
			this.defaultValue = defaultValue;
		}
		
		static public String[] getNames()
		{
			AppProperties[] values = values();
			List<String> list = new ArrayList<String>(values.length);
			for (AppProperties prop : values)
			{
				list.add(prop.name);
			}
			
			return list.toArray(new String[list.size()]);
		}
	}

	public DemoProperties()
	{
		initialize();
	}
	
	protected void initialize()
	{
		for (AppProperties prop : AppProperties.values())
		{
			setProperty(prop.name, prop.defaultValue);
		}
	}

	public void setAccountFileName(String text)
	{
		setProperty(AppProperties.LastAccountFile.name, text);
	}
	
	public void setSemaphoreFileName(String value)
	{
		setProperty(AppProperties.LastSemaphoreFile.name, value);
	}

	public String getAccountFileName()
	{
		return getProperty(AppProperties.LastAccountFile.name);
	}
	
	public String getSemaphoreFileName()
	{
		return getProperty(AppProperties.LastSemaphoreFile.name);
	}
}
