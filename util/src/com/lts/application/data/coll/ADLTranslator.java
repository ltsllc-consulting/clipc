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
package com.lts.application.data.coll;

import com.lts.application.data.ApplicationDataElement;

/**
 * A class that translates the protocol defined by {@link ApplicationDataList}
 * to something that another object can understand.
 * 
 * <H2>Description</H2>
 * The class basically forwards method calls onto another object.
 * 
 * <P>
 * Subclasses must define the following methods:
 * </P>
 * 
 * <UL>
 * <LI>basicGet</LI>
 * <LI>basicInsert</LI>
 * <LI>basicDelete</LI>
 * <LI>basicUpdate</LI>
 * </UL>
 * 
 * @author cnh
 */
abstract public class ADLTranslator implements ApplicationDataList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	abstract protected Object basicGet(int index);
	abstract protected void basicInsert(int index, Object data);
	abstract protected void basicDelete(int index);
	abstract protected void basicUpdate(int index, Object newData);
	
	protected ADLListenerHelper myHelper;
	protected boolean myDirty;
	
	public ADLTranslator ()
	{
		myHelper = new ADLListenerHelper();
	}
	
	
	public void addADLListener(ApplicationDataListListener listener)
	{
		myHelper.addListener(listener);
	}

	public void append(ApplicationDataElement data)
	{
		int index = getCount();
		insert(index, data);
	}

	public void delete(int index)
	{
		basicDelete(index);
		myDirty = false;
		myHelper.fireDelete(index, this);
	}
	
	
	public boolean getDirty()
	{
		return myDirty;
	}

	public void insert(int index, ApplicationDataElement data)
	{
		basicInsert(index, data);
		myDirty = true;
		myHelper.fireCreate(index, this);
	}

	public boolean isDirty()
	{
		return myDirty;
	}

	public void removeADLListener(ApplicationDataListListener listener)
	{
		myHelper.removeListener(listener);
	}

	public void setDirty(boolean dirty)
	{
		myDirty = dirty;
	}

	public void update(int index, ApplicationDataElement newValue)
	{
		basicUpdate(index, newValue);
		myDirty = true;
		myHelper.fireUpdate(index, this);
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
