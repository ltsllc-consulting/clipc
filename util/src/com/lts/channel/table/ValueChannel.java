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
package com.lts.channel.table;

import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * An object that acts as a go-between between a display model and the actual
 * data.
 * 
 * <P>
 * This interface is similar to {@link Serializable} in that it identifies 
 * classes rather than defining specific methods.
 * </P>
 * 
 * <P>
 * The value channel pattern (VCP) is used in the model-view-controller (MVC) approach
 * to user interface creation.  The idea behind MVC it allows the underlying 
 * data, the model, to have many ways of display.  This works for simple data,
 * but for things like JTable and JTree, the view forces a particular form onto 
 * the data.  In JTree, for example, it requires a hierarchy, whereas JTable requires
 * tables and columns.
 * </P>
 * 
 * <P>
 * Furthermore, the data is more often cached in some sort of object such as 
 * {@link DefaultMutableTreeNode} or a 2-dimensional array in the case of JTable.
 * These necessities reduce the benefits of MVC considerably.  One alternative
 * is to create custom subclasses of the models that know how to forward requests
 * for data and modifications onto the underlying data.  This approach is the 
 * basis of the value channel pattern.
 * </P>
 * 
 * <P>
 * The value channel pattern was created for use in Smalltalk.
 * </P>
 * 
 * @author cnh
 */
public interface ValueChannel
{}
