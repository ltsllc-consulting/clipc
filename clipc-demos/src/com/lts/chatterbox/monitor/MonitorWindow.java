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
package com.lts.chatterbox.monitor;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.lts.LTSException;
import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleListModel;
import com.lts.swing.panel.RootPaneContentPanel;

@SuppressWarnings("serial")
public class MonitorWindow extends RootPaneContentPanel
{
	private SimpleListModel myListModel;

	public MonitorWindow (JFrame frame) throws LTSException
	{
		super(frame);
	}
	
	
	public void initialize(Container container) throws LTSException
	{
		setExitOnClose(true);
		setBottomPanelMode(BUTTON_CLOSE);
		super.initialize(container);
	}
	
	public static MonitorWindow launch()
	{
		try
		{
			JFrame frame = new JFrame();
			MonitorWindow win = new MonitorWindow(frame);
			frame.setVisible(true);
			return win;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
	
		myListModel = new SimpleListModel();
		JList list = new JList();
		list.setModel(myListModel);
		
		JScrollPane jsp = new JScrollPane(list);
		panel.addFill(jsp);
		
		return panel;
	}
	
	
	public void appendMessage(String msg)
	{
		myListModel.add(msg);
	}
}
