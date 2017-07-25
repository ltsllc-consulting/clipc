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

public class TreeNodeEvent<N extends TreeNode>
{
	public enum EventType {
		/**
		 * The data of the watched node was changed.
		 * 
		 * <P>
		 * node should be null.
		 * </P>
		 */
		NodeChanged,
		
		/**
		 * The watched node was added as a child to another node.
		 * 
		 * <P>
		 * node should be the of the parent of the watched node.
		 * </P>
		 */
		NodeAdded,
		
		/**
		 * The watched node was removed from its parent node's children.
		 * 
		 * <P>
		 * node should be the former parent of the watched node.
		 * </P>
		 */
		NodeRemoved,
		
		/**
		 * An "all changed" style event occurred in the tree that the watched
		 * node resides in.
		 * 
		 * <P>
		 * node should be null
		 * </P>
		 */
		TreeChanged,
		
		/**
		 * A child of the watched node was added.
		 * 
		 * <P>
		 * node should be the added child.
		 * </P>
		 */
		ChildAdded,
		
		/**
		 * A child of the watched node was removed.
		 * 
		 * <P>
		 * node should be the former child.
		 * </P>
		 */
		ChildRemoved,
		
		/**
		 * A child of the watched node was changed.
		 * 
		 * <P>
		 * node should be the changed child.
		 * </P>
		 */
		ChildChanged
	}
	
	public EventType eventType;
	
	public N node;
	
	public TreeNodeEvent() {}
	
	public TreeNodeEvent(EventType event, N node)
	{
		this.eventType = event;
		this.node = node;
	}
}
