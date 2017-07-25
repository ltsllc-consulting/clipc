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

import com.lts.util.tree.TreeNode;

public class CallGraphNode extends TreeNode
{
	private StackTraceEvent myEvent;

	public CallGraphNode(StackTraceEvent event)
	{
		initialize(event);
	}

	public CallGraphNode()
	{
		initialize(null);
	}
	
	public boolean isRoot()
	{
		return null == myEvent;
	}
	
	protected void initialize(StackTraceEvent event)
	{
		myEvent = event;
	}
	
	
	public StackTraceEvent getEvent()
	{
		return myEvent;
	}
	
	public boolean isAncestorOf(StackTraceEvent event)
	{
		return myEvent.isAncestorOf(event);
	}
	
	public boolean isParentOf(StackTraceEvent event)
	{
		return myEvent.isParentOf(event);
	}

	public RecordingEvent getTrace()
	{
		return myEvent;
	}
	
	public String toString()
	{
		if (null == myEvent)
			return "root";
		else
			return myEvent.toString();
	}
	
	/**
	 * Add a child event to this node.
	 * 
	 * <H3>Note</H3>
	 * This method assumes that receiver.isAncestorOf(event) would return true
	 * with the parameter.  If this is note the case, then calling this method 
	 * may throw an exception, explode, etc.
	 * 
	 * <H3>Description</H3>
	 * If this node is the immediate parent of the event, then the argument will
	 * become one of the node's immediate children.  If the receiver is not, then 
	 * this method will try to add the event to a child that is either the parent 
	 * or one of the ancestors of the parameter.  
	 * 
	 * <P>
	 * If the receiver already has child nodes, and the receiver is the parent of the 
	 * parameter, then the child will be added at a location such that a) it occurs
	 * after a point in time of one of the other children or b) it occurs at a line
	 * number after the other child.
	 * </P>
	 * 
	 * @param event The new child event.
	 */
//	public void addChildEvent(StackTraceEvent event)
//	{
//		CallGraphNode node = new CallGraphNode(event);
//		int count = getChildCount();
//		List list = getChildren();
//		
//		for (int i = 0; i < count; i++)
//		{
//			CallGraphNode child = (CallGraphNode) list.get(i);
//		}
//	}
}
