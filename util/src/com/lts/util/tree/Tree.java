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
//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.pest library.
//
//  The com.lts.pest library is free software; you can redistribute it and/or
//  modify it under the terms of the Lesser GNU General Public License as
//  published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.pest library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.pest library; if not, write to the Free Software
//  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.util.tree;

import java.io.Serializable;
import java.util.Map;

import com.lts.util.deepcopy.DeepCopier;
import com.lts.util.deepcopy.DeepCopyException;
import com.lts.util.deepcopy.DeepCopyUtil;

public class Tree<N extends TreeNode> implements Serializable, DeepCopier
{
	private static final long serialVersionUID = 1L;

	protected N myRoot;
	transient protected TreeListenerHelper myHelper = new TreeListenerHelper<TreeNode>();
	
	public N getRoot()
	{
		return myRoot;
	}
	
	public void setRoot(N root)
	{
		setRoot(true, root);
	}
	
	protected void setRoot(boolean notify, N root)
	{
		myRoot = root;
		if (notify)
			myHelper.fireAllChanged();
	}
	
	protected TreeListenerHelper<TreeNode> getHelper()
	{
		return myHelper;
	}
	
	public Tree(N theRoot)
	{
		initialize(theRoot);
	}
	
	protected void basicAddChild(boolean update, N parent, N child)
	{
		parent.addChild(child);
		if (update)
			myHelper.fireNodeAdded(parent, child);
	}
	
	public void addChild(boolean update, N parent, N child)
	{
		basicAddChild(update, parent, child);
	}
	
	public void initialize (N theRoot)
	{
		this.myRoot = theRoot;
	}
	
	
	public TreeListenerHelper getTreeListener()
	{
		if (null == myHelper)
			myHelper = new TreeListenerHelper();
		
		return myHelper;
	}
	
	
	public void addNodeTo (N parent, N child)
	{
		parent.addChild(child);
		getTreeListener().fireNodeAdded(parent, child);
	}
	
	
	public void removeNodeFrom (N parent, N child)
	{
		parent.removeChild(child);
		myHelper.fireNodeRemoved(parent, child);
	}
	
	
	/**
	 * Notify listeners that the node has changed.
	 * 
	 * @param node
	 */
	public void changeNode (N node)
	{
		myHelper.fireNodeChanged(node);
	}
	
	
	public void addListener (TreeListener listener)
	{
		myHelper.addListener(listener);
	}
	
	public void removeListener (TreeListener listener)
	{
		myHelper.removeListener(listener);
	}
	
	
	public void deepCopyData (Object ocopy, Map map, boolean copyTransients) throws DeepCopyException
	{
		Tree copy = (Tree) ocopy;
		copy.myRoot = (N) DeepCopyUtil.continueDeepCopy(this.myRoot, map, copyTransients);
		
		if (copyTransients)
			copy.myHelper = (TreeListenerHelper) DeepCopyUtil.continueDeepCopy(this.myHelper, map, copyTransients);
	}

	public DeepCopier continueDeepCopy(Map map, boolean copyTransients) throws DeepCopyException
	{
		return (DeepCopier) DeepCopyUtil.continueDeepCopy(this, map, copyTransients);
	}

	public Object deepCopy() throws DeepCopyException
	{
		return DeepCopyUtil.deepCopy(this, false);
	}

	public Object deepCopy(boolean copyTransients) throws DeepCopyException
	{
		return DeepCopyUtil.deepCopy(this, copyTransients);
	}
}
