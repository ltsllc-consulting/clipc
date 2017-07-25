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
/**
 * 
 */
package com.lts.swing.table.dragndrop.test;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.TransferHandler;

class RecordingTransferable implements Transferable, Serializable
{
	private static final long serialVersionUID = 1L;
	
	transient protected EventLog myLog;
	static protected DataFlavor ourJVMLocalObjectFlavor;
	static protected DataFlavor[] ourFlavors;

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
	
	
	public RecordingTransferable(EventLog log)
	{
		initializeConstants();
		myLog = log;
	}
	
	public RecordingTransferable()
	{}
	
	public boolean isCompatible(DataFlavor[] flavors)
	{
		for (DataFlavor flavor : flavors)
		{
			if (isCompatible(flavor))
				return true;
		}
		
		return false;
	}
	
	private boolean isCompatible(DataFlavor flavor)
	{
		for (DataFlavor candidate : ourFlavors)
		{
			if (candidate.equals(flavor))
				return true;
		}
		
		return false;
	}


	public boolean canImport(TransferHandler.TransferSupport support)
	{
		RecordingEvent event = new CanImportCalled(support.getDataFlavors());
		myLog.addEvent(event);

		return true;
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException
	{
		GetTransferDataCalled event = new GetTransferDataCalled(flavor);
		myLog.addEvent(event);
		return toInputStream();
	}

	protected InputStream toInputStream()
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(this);
			out.close();
			byte[] buf = baos.toByteArray();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(buf);
			return bais;
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public DataFlavor[] getTransferDataFlavors()
	{
		GetTransferDataFlavorsCalled event;
		event = new GetTransferDataFlavorsCalled(ourFlavors);
		myLog.addEvent(event);
		return ourFlavors;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		RecordingEvent event = new IsDataFlavorSupportedCalled();
		myLog.addEvent(event);
		
		return true;
	}
}