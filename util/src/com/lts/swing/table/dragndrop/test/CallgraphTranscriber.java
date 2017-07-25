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

import java.io.PrintWriter;
import java.io.StringWriter;

public class CallgraphTranscriber extends LogTranscriber
{

	@Override
	public String transcribe(EventLog log)
	{
		CallGraphLog cgl = (CallGraphLog) log;
		
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		
		CallGraphNode root = cgl.getRoot();
		printRoot(1, out, root);
		
		out.close();
		return writer.toString();
	}

	private void printRoot(int prefix, PrintWriter out, CallGraphNode root)
	{
		int count = root.getChildCount();
		for (int i = 0; i < count; i++)
		{
			CallGraphNode call = (CallGraphNode) root.getChildren().get(i);
			printNode(prefix, out, call);
			i++;
			CallGraphNode endCall = (CallGraphNode) root.getChildren().get(i);
			if (endCall.getEvent() instanceof CallLeaveEvent)
			{
				printNode(prefix, out, endCall);
			}
			
			out.println();
		}
	}

	static public String nanosToString(long nanos)
	{
		int length = 9;
		char[] str = new char[length];
		
		for (int i = 0; i < str.length; i++)
		{
			int rem = (int) (nanos % 10);
			str[length - i - 1] = Integer.toString(rem).charAt(0);
			nanos = nanos / 10;
		}
		
		return new String(str);
	}
	
	private String toString(int prefix)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < prefix; i++)
		{
			sb.append(' ');
		}
		
		return sb.toString();
	}
	
	private void printNode(int prefix, PrintWriter out, CallGraphNode node)
	{
		String s = "         ";
		
		if (!node.isRoot())
			s = nanosToString(node.getEvent().getNanoTime());
		
		else if (node.getEvent() instanceof CallLeaveEvent)
			prefix = prefix - 2;
		
		out.print (s);
		out.print (toString(prefix));
		out.print (node);
		out.println();
		
		for (Object o : node.getChildren())
		{
			CallGraphNode child = (CallGraphNode) o;			
			printNode(prefix + 2, out, child);
		}
	}
	
}
