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
package com.lts.swing.table.dragndrop;

/**
 * Capture the state of a component when a drag and drop is initiated.
 * 
 * <P>
 * One issue with Java drag and drop is that the way it is set up, the 
 * TransferHandler may be presented in one state when DnD is initiated, and a 
 * different state when the drop happens.  
 * 
 * <P>
 * For example, suppose you try to drag some rows from a JTable to another row 
 * in the same JTable.  When the drag is initiated, the JTable will return one 
 * set of rows for getSelectedRows when the drag is initiated, but it will return 
 * the destination row when the drop occurs. 
 * </P>
 * 
 * <P>
 * This object encapsulates the component state, if any in between the time when
 * the drag is started, and the time the drop occurs.
 * </P>
 * 
 * @author cnh
 *
 */
public class TransferMark
{

}
