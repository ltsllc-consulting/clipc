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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;

public class LTSTransferable implements Transferable
{
	static protected String MIME_TYPE =
		"java.awt.datatransfer.DataFlavor[mimetype=application/x-java-jvm-local-objectref;representationclass=java.lang.Object]";
	
	static protected DataFlavor ourFlavor;
	static protected DataFlavor[] ourFlavors;
	
	protected Object myData;
	
	static synchronized protected void initializeStatics()
	{
		try
		{
			if (null != ourFlavor)
				return;
			
			ourFlavor = new DataFlavor(MIME_TYPE);
			ourFlavors = new DataFlavor[] { ourFlavor };
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	
	public Object getData()
	{
		return myData;
	}
	
	public LTSTransferable(Object data)
	{
		myData = data;
	}
	
	
	@Override
	public Object getTransferData(DataFlavor flavor) 
			throws UnsupportedFlavorException, IOException
	{
		return myData;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors()
	{
		return ourFlavors;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		return canImport(null, flavor);
	}
	
	
	static public boolean canImport(JComponent component, DataFlavor flavor)
	{
		for (DataFlavor temp : ourFlavors)
		{
			if (temp.equals(flavor))
				return true;
		}
		
		return false;
	}
	
	
	static public boolean canImport(JComponent component, DataFlavor[] flavors)
	{
		for (DataFlavor temp : flavors)
		{
			if (canImport(component, temp))
				return true;
		}
		
		return false;
	}
}
