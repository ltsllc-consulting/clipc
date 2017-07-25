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
import java.text.SimpleDateFormat;

import javax.swing.TransferHandler;


class RecordingEvent
{
	static protected DataFlavor ourJVMLocalObjectFlavor;
	static protected DataFlavor[] ourFlavors;
	static protected SimpleDateFormat ourSimpleFormat = new SimpleDateFormat("HH:mm:ss");
	
	protected long myTimestamp;
	protected long myPreciseTime;
	protected int mySequence;
	
	public int getSequence()
	{
		return mySequence;
	}
	
	public void setSequence(int sequence)
	{
		mySequence = sequence;
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

	public RecordingEvent()
	{
		initialize();
	}

	
	protected void initialize()
	{
		initializeConstants();
		myPreciseTime = System.nanoTime();
		myTimestamp = System.currentTimeMillis();
		mySequence = -1;
	}
	
	
	protected String actionToString(int action)
	{
		switch (action)
		{
			case TransferHandler.MOVE :
				return "move";
			
			case TransferHandler.COPY :
				return "copy";
				
			case TransferHandler.COPY_OR_MOVE :
				return "copy-or-move";
		}
		
		return "unknown: " + action;
	}

	protected String buildString(String name, int action)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		sb.append("{");
		sb.append(actionToString(action));
		sb.append("}");
		
		return sb.toString();
	}

	protected String buildNameFlavorString(DataFlavor[] flavors)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getClass().getSimpleName());
		sb.append("{");
		
		flavorsToString(flavors, sb);
		
		sb.append("}");
		return sb.toString();
	}

	protected void flavorsToString(DataFlavor[] flavors, StringBuffer sb)
	{
		boolean first = true;
		for (DataFlavor flavor : flavors)
		{
			if (first)
			{
				first = false;
			}
			else
			{
				sb.append(", ");
			}
			
			sb.append(flavorToString(flavor));
		}
	}

	protected String flavorToString(DataFlavor flavor)
	{
		if (flavor.equals(ourJVMLocalObjectFlavor))
			return "JVM-object";
		else
			return flavor.toString();
	}

	protected String buildNameAction(int action)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getClass().getSimpleName());
		sb.append("{");
		sb.append(actionToString(action));
		sb.append("}");
		return sb.toString();
	}
	
	
	protected String buildNameFlavor(DataFlavor flavor)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getClass().getSimpleName());
		sb.append("{");
		sb.append(flavorToString(flavor));
		sb.append("}");
		return sb.toString();
	}

	protected String buildNameActionTimestamp(int action, long time)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(getClass().getSimpleName());
		sb.append("{");
		sb.append(actionToString(action));
		sb.append(", ");
		sb.append(ourSimpleFormat.format(time));
		sb.append("}");
		
		return sb.toString();
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(getClass().getSimpleName());
		sb.append("{");
		
		sb.append(ourSimpleFormat.format(myTimestamp));
		
		appendSubclassToString(sb);
		
		sb.append("}");
		
		return sb.toString();
	}

	protected void appendSubclassToString(StringBuffer sb)
	{}

	public long getNanoTime()
	{
		return myPreciseTime;
	}
}