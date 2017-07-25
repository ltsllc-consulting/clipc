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
//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.application library.
//
//  The com.lts.application library is free software; you can redistribute it
//  and/or modify it under the terms of the Lesser GNU General Public License
//  as published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.application library is distributed in the hope that it will be
//  useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.application library; if not, write to the Free
//  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
//  02110-1301 USA
//
package com.lts.application.data;

import java.io.Serializable;

import com.lts.application.ApplicationException;
import com.lts.util.deepcopy.DeepCopier;
import com.lts.xml.simple.SimpleSerialization;

/**
 * A ApplicationData object that supports the notion of an entry name.
 * 
 * <P>
 * The entry name is used primarily for storage --- where should the data 
 * be obtained from a repository?
 * 
 * @author cnh
 *
 */
public interface ApplicationDataElement 
	extends Serializable, DeepCopier, Cloneable, SimpleSerialization
{
	/**
	 * Does this object need to be serialized (saved)?
	 * 
	 * @return true if the data is dirty, false if it is clean.
	 * @throws ApplicationException
	 *         If an error is encountered while trying to determine the status of the
	 *         data.
	 */
	public boolean isDirty();
	
	/**
	 * Mark the object as clean or dirty.
	 * 
	 * @param dirty false if the data is now clean, true if the data is now dirty.
	 */
	public void setDirty (boolean dirty);

	/**
	 * Perform any housekeeping operations required before clients start using the 
	 * data.
	 * 
	 * <P>
	 * For example, if a class exists that should never have a null value for some 
	 * particular field, then this method could be used to ensure that this is the 
	 * case.
	 * </P>
	 */
	public void postDeserialize() throws ApplicationException;
	
	/**
	 * Copy the data from the supplied element into the receiver.
	 * 
	 * <P>
	 * This method is like using clone, except the state (fields) of the object are 
	 * copied instead of making a copy of the object itself.  The method should call
	 * {@link #postDeserialize()} on itself after copying the data.
	 * </P>
	 * 
	 * <P>
	 * The fields copied should not include transients.  Those fields should be 
	 * initialized by calling postDeserialize anyways.
	 * </P>
	 * 
	 * @param element The object to copy.  This should be "assignment compatible" with 
	 * the receiver.
	 * @throws ApplicationException If a problem is encountered during the copy.
	 */
	public void copyFrom (ApplicationDataElement element) throws ApplicationException;

	/**
	 * Make an independent copy of the object.
	 * 
	 * <P>
	 * In this context, a clone is a "shallow copy."  That is, suppose the class 
	 * has a list field and that A is an instance.  If B is the result of calling
	 * A.clone(), then changes to B's list will show up in A's list.
	 * </P>
	 * 
	 * @return A copy of the object.
	 */
	public Object clone() throws CloneNotSupportedException;
}
