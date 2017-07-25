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
package com.lts.util.tree;

public class TreeNodeAdaptor<N extends TreeNode> implements TreeNodeListener<N>
{
	private TreeNodeEvent myEvent;
	
	@Override
	public void treeNodeEvent(TreeNodeEvent event)
	{
		switch (event.eventType)
		{
			case ChildAdded :
				childAdded(event.node);
				break;
				
			case ChildChanged :
				childChanged(event.node);
				break;
				
			case ChildRemoved :
				childRemoved(event.node);
				break;
				
			case NodeAdded :
				nodeAdded(event.node);
				break;
				
			case NodeChanged :
				nodeChanged();
				break;
				
			case NodeRemoved :
				nodeRemoved(event.node);
				break;
				
			case TreeChanged :
				treeChanged();
				break;
		}
	}


	public void treeChanged()
	{}


	public void nodeRemoved(TreeNode parent)
	{}


	public void nodeChanged()
	{}


	public void nodeAdded(TreeNode parent)
	{}


	public void childRemoved(TreeNode child)
	{}


	public void childChanged(TreeNode child)
	{}


	public void childAdded(TreeNode child)
	{}


	public TreeNodeEvent getEvent()
	{
		return myEvent;
	}


	public void setEvent(TreeNodeEvent event)
	{
		myEvent = event;
	}
}
