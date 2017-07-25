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
 * Specify common collection properties.
 * 
 * <P>
 * This annotation should only be applied to a getter method for a bean property.
 * </P>
 * 
 * <P>
 * The annotation has three properties, all of which are optional.  It does not make
 * a whole lot of sense to use this annotation without specifying any properties, but
 * it is allowed.
 * </P>
 * 
 * <P>
 * The properties for this annotation are:
 * <UL>
 * <LI>collectionClass - which subclass of Collection to use.</LI>
 * <LI>elementName - the name to use for child elements.</LI>
 * <LI>elementClass - the class to use for child elements.</LI>
 * </UL>
 * </P>
 * 
 * @author cnh
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CollectionProperty {
	public String elementClass();
	public String collectionClass() default "java.util.ArrayList";
	public String elementName() default "element";
	public String collectionReader() 
		default "com.lts.bean.xml.serializer.reader.CollectionReader";
}
