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

import java.util.Stack;

public class CallGraphLog extends EventLog
{
	protected boolean myDirty = true;
	protected Stack<CallGraphNode> myCallstack;
	protected CallGraphNode myRoot;
	
	public CallGraphLog ()
	{
		initalize();
	}
	
	
	private void initalize()
	{
		myRoot = new CallGraphNode();
		myCallstack = new Stack<CallGraphNode>();
	}


	public boolean isDirty()
	{
		return myDirty;
	}
	
	public void organize()
	{
		myDirty = false;
		
		
	}

	@Override
	public void enterMethod()
	{
		CallEnterEvent event = new CallEnterEvent(2);
		addEvent(event);
		
		CallGraphNode node = new CallGraphNode(event);
		if (myCallstack.empty())
		{
			myRoot.addChild(node);
		}
		else
		{
			myCallstack.peek().addChild(node);
		}
		
		myCallstack.push(node);
	}

	@Override
	public void leaveMethod()
	{
		CallLeaveEvent event = new CallLeaveEvent(2);
		addEvent(event);
		
		if (!myCallstack.empty())
		{
			myCallstack.pop();
		}
		
		CallGraphNode child = new CallGraphNode(event);
		if (myCallstack.empty())
		{
			myRoot.addChild(child);
		}
		else
		{
			myCallstack.peek().addChild(child);
		}
	}


	public CallGraphNode getRoot()
	{
		return myRoot;
	}
}
