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

import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * A very simple Transfer handler that allows clients to move rows around in 
 * the same table.
 * 
 * <P>
 * On an implementation note, one aspect of a move transfer handler that makes it
 * different from other transfer handlers is that it defers importing data until 
 * the {@link TransferHandler#exportDone(JComponent, Transferable, int)}
 * </P>
 * 
 * @author cnh
 *
 */
abstract public class MoveTransferHandler // extends LTSTransferHandler
{
//	abstract protected void move(JTable table, int[] rows, int selection);
//
//	@Override
//	protected Object copyRow(TableModel model, int index)
//	{
//		throw new InvalidOperationException();
//	}
//
//	@Override
//	protected void deleteRow(TableModel model, int index)
//	{
//		throw new InvalidOperationException();
//	}
//
//	@Override
//	protected void insertRow(TableModel model, Object data, int index)
//	{
//		throw new InvalidOperationException();
//	}
//
//	protected Transferable myTransferable;
//	
//	@Override
//	public boolean importData(JComponent c, Transferable t)
//	{
//		return false;
//	}
//
//	@Override
//	protected void exportDone(JComponent comp, Transferable data, int action)
//	{
//		if (MOVE != action)
//			return;
//		
//		JTable table = (JTable) comp;
//		int selection = table.getSelectedRow();
//		if (-1 == selection)
//			return;
//		
//		SimpleTransferData std = (SimpleTransferData) data;
//		move(table, std.getRows(), selection); 
//	}

}
