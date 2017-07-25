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

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;

import javax.swing.JComponent;
import javax.swing.TransferHandler;


@SuppressWarnings("serial")
public class RecordingTransferHandler extends TransferHandler
{
	protected EventLog myLog;
	
	public RecordingTransferHandler()
	{
		myLog = new EventLog();
	}
	
	
	public boolean canImport(TransferHandler.TransferSupport support)
	{
		RecordingEvent event = new CanImportCalled(support.getDataFlavors());
		myLog.addEvent(event);
		return true;
	}

	public Transferable createTransferable(JComponent comp)
	{
		if (null == comp)
			return null;
		
		RecordingEvent event = new CreateTransferableCalled();
		myLog.addEvent(event);
		RecordingTransferable trans = new RecordingTransferable(myLog);
		return trans;
	}
	
	
	public void exportAsDrag(JComponent comp, InputEvent event, int action)
	{
//		RecordingEvent rec;
//		rec = new ExportAsDragCalled(event, action);
//		myLog.addEvent(rec);
//		RecordingEvent call = new CallStart(rec);
//		myLog.addEvent(call);
//		super.exportAsDrag(comp, event, action);
//		call = new CallEnd(rec);
//		myLog.addEvent(call);
	}
	
	protected void exportDone(JComponent source, Transferable data, int action)
	{
		RecordingEvent event = new ExportDoneCalled(data, action);
		myLog.addEvent(event);
		super.exportDone(source, data, action);
	}

	public void exportToClipboard(JComponent comp, Clipboard clip, int action)
	{
//		RecordingEvent event = new ExportToClipboardCalled(clip, action);
//		myLog.addEvent(event);
//		RecordingEvent call = new CallStart(event);
//		myLog.addEvent(call);
//		
//		super.exportToClipboard(comp, clip, action);
//		
//		call = new CallEnd(event);
//		myLog.addEvent(call);
	}
	
	
	public int getSourceActions(JComponent c)
	{
		RecordingEvent event = new GetSourceActionsCalled();
		myLog.addEvent(event);
		return COPY_OR_MOVE;
	}
	
	
	public boolean importData(TransferHandler.TransferSupport support)
	{
		RecordingEvent event = new ImportDataCalled(support);
		myLog.addEvent(event);
		return true;
	}
	
	
	public Object[] getEvents()
	{
		return myLog.getEvents();
	}

	
	public String getTranscript()
	{
		return myLog.getTranscript();
	}
}
