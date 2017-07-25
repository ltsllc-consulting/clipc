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
package com.lts.bean.xml.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Specify the class to use when reading the bean.
 * 
 * <P>
 * This annotation causes the bean serializer to instantiate the specified class when 
 * reading the associated property.  An instance of the specified class is created
 * via the default constructor and then populated with the values defined by the 
 * XML for this property.
 * </P>
 * 
 * <P>
 * This annotation should only be used with getter methods.
 * </P>
 * 
 * @author cnh
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyClass {
	public String value();
}
