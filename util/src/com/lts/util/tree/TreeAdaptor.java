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


/**
 * A class that provides "do nothing" implementations for all the methods required 
 * by the {@link TreeListener} interface.
 * 
 * @author cnh
 */
public class TreeAdaptor<N extends TreeNode> implements TreeListener<N>
{
	@Override
	public void allChanged()
	{}

	@Override
	public void nodeAdded(N parent, N child)
	{}

	@Override
	public void nodeChanged(N node)
	{}

	@Override
	public void nodeRemoved(N parent, N child)
	{}
}
