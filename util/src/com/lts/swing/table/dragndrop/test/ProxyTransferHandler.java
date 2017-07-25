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

@SuppressWarnings("serial")
public class ProxyTransferHandler
    extends javax.swing.TransferHandler

{
    public boolean canImport(
        javax.swing.JComponent arg0,
        java.awt.datatransfer.DataFlavor[] arg1
    )
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.canImport(arg0, arg1);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public boolean canImport(javax.swing.TransferHandler.TransferSupport arg0)
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.canImport(arg0);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    protected java.awt.datatransfer.Transferable createTransferable(javax.swing.JComponent arg0)
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.createTransferable(arg0);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public void exportAsDrag(
        javax.swing.JComponent arg0,
        java.awt.event.InputEvent arg1,
        int arg2
    )
    {
        try
        {
            EventLog.getInstance().enterMethod();
            super.exportAsDrag(arg0, arg1, arg2);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    protected void exportDone(
        javax.swing.JComponent arg0,
        java.awt.datatransfer.Transferable arg1,
        int arg2
    )
    {
        try
        {
            EventLog.getInstance().enterMethod();
            super.exportDone(arg0, arg1, arg2);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public void exportToClipboard(
        javax.swing.JComponent arg0,
        java.awt.datatransfer.Clipboard arg1,
        int arg2
    )
    {
        try
        {
            EventLog.getInstance().enterMethod();
            super.exportToClipboard(arg0, arg1, arg2);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public int getSourceActions(javax.swing.JComponent arg0)
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.getSourceActions(arg0);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public javax.swing.Icon getVisualRepresentation(java.awt.datatransfer.Transferable arg0)
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.getVisualRepresentation(arg0);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public boolean importData(
        javax.swing.JComponent arg0,
        java.awt.datatransfer.Transferable arg1
    )
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.importData(arg0, arg1);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public boolean importData(javax.swing.TransferHandler.TransferSupport arg0)
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.importData(arg0);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public boolean equals(java.lang.Object arg0)
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.equals(arg0);
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
    public java.lang.String toString()
    {
        try
        {
            EventLog.getInstance().enterMethod();
            return super.toString();
        }
        finally
        {
            EventLog.getInstance().leaveMethod();
        }
    }
    
}
