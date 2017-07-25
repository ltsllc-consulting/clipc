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
package com.lts.swing.table.dragndrop;

import java.awt.Component;

import javax.swing.JComponent;

public class SimplifiedTransferHandlerAdaptor implements SimplifiedTransferHandler
{
	protected boolean mySupportsCopy;
	protected boolean mySupportsCut;
	protected boolean mySupportsMove;
	protected boolean mySupportsPaste;
	
	protected void initialize()
	{
		mySupportsCopy = false;
		mySupportsCut = false;
		mySupportsMove = false;
		mySupportsPaste = false;
	}
	
	
	@Override
	public Object copy(JComponent comp)
	{
		return null;
	}

	@Override
	public boolean paste(Component comp, Object data)
	{
		return false;
	}

	@Override
	public void remove(JComponent comp, Object data)
	{}

	@Override
	public boolean supportsCopy()
	{
		return mySupportsCopy;
	}

	@Override
	public boolean supportsCut()
	{
		return mySupportsCut;
	}

	@Override
	public boolean supportsMove()
	{
		return mySupportsMove;
	}

	@Override
	public boolean supportsPaste()
	{
		return mySupportsPaste;
	}

}