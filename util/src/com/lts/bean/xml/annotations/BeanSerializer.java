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
 * Specify the class to use when reading or writing the bean.
 * 
 * <P>
 * This annotation specifies that the serializer should use a specific serializaiton
 * class instead of the default serializer.  This can be indicated by passing a 
 * class object for the serializer class, or by using the className property to 
 * pass the name of the class instead.
 * </P>
 * 
 * <P>
 * In either case, the class should implement BeanSerializer.
 * </P>
 * 
 * <P>
 * This class should be used only on class declarations.
 * </P>
 * @author cnh
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanSerializer {
	public Class value() default BeanSerializer.class;
	public String className() default "default";
}
