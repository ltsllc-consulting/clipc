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
package com.lts.chatterbox.app;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.application.swing.ApplicationContentPanel;
import com.lts.chatterbox.fifos.FIFOChatterbox;
import com.lts.chatterbox.fifos.NonBlockingFIFOChatterbox;
import com.lts.chatterbox.servers.Server;
import com.lts.event.SimpleAction;
import com.lts.swing.LTSPanel;
import com.lts.swing.TextWindow;
import com.lts.swing.combobox.SimpleComboBoxModel;

@SuppressWarnings("serial")
public class ControlWindow extends ApplicationContentPanel
{
	public enum Modes
	{
		SharedMemory,
		Sockets,
		FIFOs,
		Semaphores, NonBlockingFIFOs;
		
		public static Modes toModeIgnoreCase(String s)
		{
			s = s.toLowerCase();
			Modes mode = null;
			
			for (Modes amode : Modes.values())
			{
				if (s.equalsIgnoreCase(amode.name()))
				{
					mode = amode;
					break;
				}
			}
			
			return mode;
		}
	}
	
	private Server myServer;
	private SimpleComboBoxModel myModel;

	public ControlWindow(JFrame frame) throws LTSException
	{
		super(frame);
	}

	public void initialize(Container container) throws LTSException
	{
		setupShowSize(container);
		super.initialize(container);
		setExitOnClose(true);
	}
	
	@Override
	public String buildWindowTitle()
	{
		return getViewName();
	}

	@Override
	protected String getViewName()
	{
		return "Chatterbox";
	}

	public static final Object[][] APPLICATIONS = {
		{ "Shared Memory",		Modes.SharedMemory, },
		{ "Sockets",			Modes.Sockets, },
		{ "FIFOS",				Modes.FIFOs, },
		{ "Non-Blocking FIFOs",	Modes.NonBlockingFIFOs, },
		{ "Semaphores",			Modes.Semaphores },
	};
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel label = new JLabel("Application");
		panel.addLabel(label,5);
		
		myModel = new SimpleComboBoxModel(APPLICATIONS);
		JComboBox jcb = new JComboBox(myModel);
		panel.addLabel(jcb,5);
		
		panel.nextRow();
		
		JButton button = new JButton("GO");
		SimpleAction action = new SimpleAction() {
			public void action() {
				runApplication();
			}
		};
		button.addActionListener(action);
		panel.addButton(button,5);
		
		button = new JButton("Quit");
		action = new SimpleAction() {
			public void action() {
				quit();
			}
		};
		button.addActionListener(action);
		panel.addButton(button,5);
		
		
		return panel;
	}
	
	
	
	public void runApplication()
	{
		if (null != myServer)
		{
			myServer.terminate();
			myServer = null;
		}
		
		
		String s = (String) myModel.getSelectedItem();
		Modes mode = toMode(s);
		
		if (null == mode)
		{
			JOptionPane.showMessageDialog(this, "Unrecognized mode: " + s);
			return;
		}
		
		
		myServer = null;
		
		switch (mode)
		{
			case FIFOs :
				myServer = new FIFOChatterbox();
				break;
			
			case NonBlockingFIFOs :
				myServer = new NonBlockingFIFOChatterbox();
				break;
				
			case Semaphores :
			case SharedMemory :
			case Sockets :
		}
		
		myServer.launch();
	}
	
	
	private Modes toMode(String s) 
	{
		for (Object[] row : APPLICATIONS)
		{
			String name = (String) row[0];
			if (name.equalsIgnoreCase(s))
			{
				return (Modes) row[1];
			}
		}
		
		return null;
	}

	public static ControlWindow launch()
	{
		try
		{
			JFrame frame = new JFrame();
			ControlWindow win = new ControlWindow(frame);
			frame.setVisible(true);
			return win;
		}
		catch (LTSException e)
		{
			TextWindow.showException(e);
			return null;
		}
	}
	
	@Override
	public Dimension getWindowSize()
	{
		return new Dimension(316, 190);
	}

	public void quit()
	{
		if (null != myServer)
		{
			myServer.terminate();
		}
		
		Application.getInstance().shutDown();
	}
}
