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

import javax.swing.JTable;

/**
 * A transferable that contains a JTable and a set of row indices.
 * 
 * <P>
 * This class allows data transfer in the same instance of JTable.  It assumes 
 * that some other class "knows" how to access the actual data pointed to by 
 * this instance.  Therefore, it contains the source JTable and the rows that 
 * are to be transferred, but does not represent that information in, for example
 * a MIME form that can be sent to the clipboard.
 * </P>
 *  
 * @author cnh
 *
 */
public class SimpleTransferData implements Transferable
{
	private JTable myTable;
	private int[] myRows;
	private int myDestinationRow;
	
	public int getDestinationRow()
	{
		return myDestinationRow;
	}
	
	public void setDestinationRow(int destnationRow)
	{
		myDestinationRow = destnationRow;
	}
	
	public SimpleTransferData (JTable table, int[] rows)
	{
		myTable = table;
		myRows = rows;
	}
	

	public JTable getTable()
	{
		return myTable;
	}
	
	public int[] getRows()
	{
		return myRows;
	}
	
	
	public static final String SPEC_MIME_TYPE =
		DataFlavor.javaJVMLocalObjectMimeType
		+ ";class=" + SimpleTransferData.class.getName();

	static protected DataFlavor ourFlavor;
	static protected DataFlavor[] ourFlavors;
	
	static protected DataFlavor getFlavor() 
	{
		if (null == ourFlavor)
		{
			try
			{
				ourFlavor = new DataFlavor(SPEC_MIME_TYPE);
			}
			catch (ClassNotFoundException e)
			{
				throw new RuntimeException(e);
			}
		}
		
		return ourFlavor;
	}
	
	static protected DataFlavor[] getFlavors()
	{
		if (null == ourFlavors)
		{
			ourFlavors = new DataFlavor[] { getFlavor() };
		}
		
		return ourFlavors;
	}
	
	
	@Override
	public Object getTransferData(DataFlavor flavor) 
		throws UnsupportedFlavorException, IOException
	{
		 if (!flavor.equals(getFlavor()))
		 {
			 throw new UnsupportedOperationException();
		 }
		 
		 return this;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors()
	{
		return getFlavors();
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		for (DataFlavor theFlavor : getFlavors())
		{
			if (theFlavor.equals(flavor))
				return true;
		}
		
		return false;
	}

}
