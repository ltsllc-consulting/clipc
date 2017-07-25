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

import com.lts.event.ListenerHelper;
import com.lts.util.tree.TreeNodeEvent.EventType;

public class TreeNodeHelper<N extends TreeNode> extends ListenerHelper
{
	@Override
	public void notifyListener(Object olistener, Object oevent)
	{
		TreeNodeListener<N> listener = (TreeNodeListener<N>) olistener;
		TreeNodeEvent<N> event = (TreeNodeEvent<N>) oevent;
		
		listener.treeNodeEvent(event);
	}
	
	public void fireNodeAdded(N node)
	{
		TreeNodeEvent<N> event = new TreeNodeEvent<N>(EventType.NodeAdded, node);
		fire(event);
	}
	
	public void fireNodeChanged()
	{
		TreeNodeEvent<N> event = new TreeNodeEvent<N>(EventType.NodeChanged, null);
		fire(event);
	}

	public void fireNodeRemoved(N node)
	{
		TreeNodeEvent<N> event = new TreeNodeEvent<N>(EventType.NodeRemoved, node);
		fire(event);
	}
	
	public void fireChildAdded(N node)
	{
		TreeNodeEvent<N> event = new TreeNodeEvent<N>(EventType.ChildAdded, node);
		fire(event);
	}
	
	public void fireChildChanged(N node)
	{
		TreeNodeEvent<N> event = new TreeNodeEvent<N>(EventType.ChildChanged, node);
		fire(event);
	}
	
	public void fireChildRemoved(N node)
	{
		TreeNodeEvent<N> event = new TreeNodeEvent<N>(EventType.ChildRemoved, node);
		fire(event);
	}
	
	public void fireAllChanged()
	{
		TreeNodeEvent<N> event = new TreeNodeEvent<N>(EventType.TreeChanged, null);
		fire(event);
	}	
}
