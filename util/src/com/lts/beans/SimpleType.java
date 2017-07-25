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
package com.lts.beans;

import java.util.Map;

import com.lts.util.MapUtil;

public enum SimpleType 
{
	SimpleBoolean,
	SimpleByte,
	SimpleShort,
	SimpleInteger,
	SimpleLong,
	SimpleFloat,
	SimpleDouble,
	SimpleCharacter,
	SimpleString;

	private static Map<Class, SimpleType> ourClassToSimpleType;
	
	private static Object[][] SPEC_CLASS_TO_SIMPLE_TYPE = 
	{
		{ Boolean.TYPE, SimpleBoolean },
		{ Boolean.class, SimpleBoolean },
		{ Byte.TYPE, SimpleByte },
		{ Byte.class, SimpleByte },
		{ Short.TYPE, SimpleShort },
		{ Short.class, SimpleShort },
		{ Integer.TYPE, SimpleInteger },
		{ Integer.class, SimpleInteger },
		{ Long.TYPE, SimpleLong },
		{ Long.class, SimpleLong },
		{ Float.TYPE, SimpleFloat },
		{ Float.class, SimpleFloat },
		{ Double.TYPE, SimpleDouble },
		{ Double.class, SimpleDouble },
		{ Character.TYPE, SimpleCharacter },
		{ Character.class, SimpleCharacter },
		{ String.class, SimpleString }
	};
	
	
	static {
		ourClassToSimpleType = MapUtil.buildMap(SPEC_CLASS_TO_SIMPLE_TYPE);
	}
	
	
	public static boolean isSimpleType(Class clazz)
	{
		return null != ourClassToSimpleType.get(clazz);
	}
	
	public static SimpleType toSimpleType(Class clazz)
	{
		return ourClassToSimpleType.get(clazz);
	}
}
