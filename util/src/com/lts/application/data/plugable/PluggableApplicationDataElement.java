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
package com.lts.application.data.plugable;

import java.util.Map;

import com.lts.application.ApplicationException;
import com.lts.application.data.ApplicationDataElement;
import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;

abstract public class PluggableApplicationDataElement implements ApplicationDataElement
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	transient private boolean myDirty;
	
	public boolean isDirty()
	{
		return myDirty;
	}

	public void postDeserialize() throws ApplicationException
	{
		myDirty = false;
	}

	public void setDirty(boolean dirty)
	{
		myDirty = dirty;
	}

	public DeepCopier continueDeepCopy(Map map, boolean copyTransients) throws DeepCopyException
	{
		return (DeepCopier) DeepCopyUtil.continueDeepCopy(this, map, copyTransients);
	}

	public Object deepCopy() throws DeepCopyException
	{
		return deepCopy(false);
	}

	public Object deepCopy(boolean copyTransients) throws DeepCopyException
	{
		return DeepCopyUtil.deepCopy(this, copyTransients);
	}

	public void deepCopyData(Object copy, Map map, boolean copyTransients) throws DeepCopyException
	{
		PluggableApplicationDataElement other;
		other = (PluggableApplicationDataElement) copy;
		
		if (copyTransients)
			other.myDirty = myDirty;
	}
	
	public void copyFrom (ApplicationDataElement element) throws ApplicationException
	{
		postDeserialize();
	}

	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
		}
	}
}
