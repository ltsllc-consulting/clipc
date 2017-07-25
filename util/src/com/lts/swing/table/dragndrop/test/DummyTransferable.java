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
package com.lts.swing.table.dragndrop.test;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class DummyTransferable implements Transferable
{
	static protected DataFlavor ourJVMLocalObjectFlavor;
	static protected DataFlavor[] ourFlavors;
	
	static protected void checkConstants()
	{
		if (null == ourJVMLocalObjectFlavor || null == ourFlavors)
			initializeConstants();
	}
	
	static synchronized protected void initializeConstants()
	{
		if (null != ourJVMLocalObjectFlavor || null != ourFlavors)
			return;
		
		try
		{
			ourJVMLocalObjectFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
			ourFlavors = new DataFlavor[] { ourJVMLocalObjectFlavor };
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException,
			IOException
	{
		return this;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors()
	{
		return ourFlavors;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		return true;
	}

}
