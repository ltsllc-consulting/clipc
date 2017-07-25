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

import com.lts.util.tree.TreeNodeEvent.EventType;


/**
 * A TreeListener that watches one particular node.
 * 
 * <P>
 * This class listens to a {@link Tree} and filters out all the changes except
 * for those pertaining to a particular node.  Specifically, a {@link TreeNodeEvent} 
 * is sent on to the listeners of this class under the following circumstances:
 * </P>
 * 
 * <TABLE border="1">
 * <TR>
 * 		<TD><B>TreeListener</B></TD>
 * 		<TD><B>{@link TreeNodeEvent#eventType}</B></TD>
 * 		<TD><B>Conditions</B></TD>
 * 		<TD><B>{@link TreeNodeEvent#node}</B></TD>
 * </TR>
 * 
 * <TR>
 * 		<TD>allChanged</TD>
 * 		<TD>{@link EventType#TreeChanged}</TD>
 * 		<TD>none</TD>
 * 		<TD>null</TD>
 * </TR>
 * 
 * <TR>
 * 		<TD>nodeAdded</TD>
 * 		<TD>{@link EventType#NodeAdded}</TD>
 * 		<TD>child is watched node</TD>
 * 		<TD>new parent</TD>
 * </TR>
 * 
 * <TR>
 * 		<TD>nodeAdded</TD>
 * 		<TD>{@link EventType#ChildAdded}</TD>
 * 		<TD>parent is watched node</TD>
 * 		<TD>new child</TD>
 * </TR>
 * 
 * <TR>
 * 		<TD>nodeChanged</TD>
 * 		<TD>{@link EventType#NodeChanged}</TD>
 * 		<TD>node is watched node</TD>
 * 		<TD>null</TD>
 * </TR>
 * 
 * <TR>
 * 		<TD>nodeRemoved</TD>
 * 		<TD>{@link EventType#NodeRemoved}</TD>
 * 		<TD>child is watched node</TD>
 * 		<TD>former child</TD>
 * </TR>
 * 
 * <TR>
 * 		<TD>nodeRemoved</TD>
 * 		<TD>{@link EventType#NodeRemoved}</TD>
 * 		<TD>parent is watched node</TD>
 * 		<TD>former parent</TD>
 * </TR>
 * 
 * </TABLE>
 * 
 * <P>
 * It is the responsibility of the listeners to keep this class informed of which node
 * needs to be watched.  If allChanged occurs, for example, the listeners need to take
 * whatever action is needed so that watchedNode is set correctly.
 * </P>
 * 
 * @author cnh
 *
 * @param <N>
 */
public class TreeNodeListenerImpl<N extends TreeNode> extends TreeAdaptor<N>
{
	private Tree<N> myTree;
	private N myWatchedNode;
	private TreeNodeHelper<N> myHelper = new TreeNodeHelper<N>();
	
	public TreeNodeListenerImpl()
	{}

	public N getWatchedNode()
	{
		return myWatchedNode;
	}

	public void setWatchedNode(N watchedNode)
	{
		myWatchedNode = watchedNode;
	}

	public Tree<N> getTree()
	{
		return myTree;
	}

	public void setTree(Tree<N> tree)
	{
		if (null != myTree)
			myTree.removeListener(this);
		
		myTree = tree;
		
		if (null!= myTree)
			myTree.addListener(this);
	}

	@Override
	public void allChanged()
	{
		myHelper.fireAllChanged();
	}

	@Override
	public void nodeAdded(N parent, N child)
	{
		if (getWatchedNode() == parent)
			myHelper.fireChildAdded(child);
		else if (getWatchedNode() == child)
			myHelper.fireNodeAdded(parent);
	}

	@Override
	public void nodeChanged(N node)
	{
		if (getWatchedNode() == node)
			myHelper.fireNodeChanged();
	}

	@Override
	public void nodeRemoved(N parent, N child)
	{
		if (getWatchedNode() == parent)
			myHelper.fireChildRemoved(child);
		else if (getWatchedNode() == child)
			myHelper.fireNodeRemoved(parent);
	}
	
	
	public void addListener(TreeNodeListener<N> listener)
	{
		myHelper.addListener(listener);
	}
	
	public void removeListener(TreeNodeListener<N> listener)
	{
		myHelper.removeListener(listener);
	}
}
