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
package com.lts;

/**
 * A class that provides instance equivalents of static enum methods.
 * <P>
 * This class provides access to the static methods/constants that are normally defined by
 * Java when you create an enum type; specifically values and valueOf.
 * </P>
 * <P>
 * This class would be completely unnecessary if the people who had implemented enums had
 * supplied a similar facility. The need seems obvious enough that I feel like there is
 * some class, somewhere that already provides access to these methods. So far, I haven't
 * found it.
 * </P>
 * <P>
 * This means that this class could disappear at any time if and when I find said
 * mechanism. In the meantime, however, here it is...
 * </P>
 */
abstract public class EnumWrapper
{
	/**
	 * return the result of calling the underlying Enum's values method.
	 * 
	 * <P>
	 * Note that the return type of this method is Enum[], not E.  This because 
	 * of the weird manner in which enums are implemented under the current JVM.  What this 
	 * means is that yes, you have to cast to the actual enum type.
	 * </P>
	 * 
	 * @return All the elements of the enum.
	 */
	abstract public Object[] values();

	/**
	 * (From the Java language specification) Returns the enum constant of this type with the specified name. The string must
	 * match exactly an identifier used to declare an enum constant in this type.
	 * (Extraneous whitespace characters are not permitted.)
	 * 
	 * @return the enum constant with the specified name
	 * @throws IllegalArgumentException
	 *         if this enum type has no constant with the specified name
	 */
	abstract public Object valueOf(String s);
}
