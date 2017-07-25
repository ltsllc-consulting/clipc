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

import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;

public class ImportDataCalled extends RecordingEvent
{
	protected TransferHandler.TransferSupport myTransferSupport;
	
	public ImportDataCalled(TransferHandler.TransferSupport transferSupport)
	{
		initialize(transferSupport);
	}
	
	
	private void initialize(TransferSupport transferSupport)
	{
		myTransferSupport = transferSupport;
	}


	
	public void appendSubclassToString(StringBuffer sb)
	{
		if (!myTransferSupport.isDrop())
		{
			sb.append("not a drop");
		}
		else
		{
			String action = actionToString(myTransferSupport.getSourceDropActions());
			sb.append("source drop action = ");
			sb.append(action);
			
			sb.append(", drop action = ");
			action = actionToString(myTransferSupport.getDropAction());
			sb.append(action);
			
			sb.append(", user drop action = ");
			action = actionToString(myTransferSupport.getUserDropAction());
			sb.append(action);
		}
	}
}
