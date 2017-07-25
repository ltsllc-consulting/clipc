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
package com.lts.highlander;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lts.LTSException;
import com.lts.application.swing.ApplicationContentPanel;
import com.lts.event.SimpleAction;
import com.lts.ipc.IPCException;
import com.lts.ipc.semaphore.Semaphore;
import com.lts.swing.LTSPanel;
import com.lts.swing.SwingUtils;
import com.lts.test.Spawn;

@SuppressWarnings("serial")
public class ControlWindow extends ApplicationContentPanel
{
	private JTextField myLimitField;
	private Semaphore mySemaphore;

	@Override
	public String buildWindowTitle()
	{
		return getViewName();
	}

	@Override
	protected String getViewName()
	{
		return "Highlander";
	}

	public ControlWindow(JFrame frame) throws LTSException
	{
		initialize(frame);
	}
	
	public void initialize(Container container) throws LTSException
	{
		setupShowSize(container);
		setExitOnClose(true);
		super.initialize(container);
	}
	

	@Override
	public Dimension getWindowSize()
	{
		return new Dimension(382, 168);
	}
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		panel.addFill(createCenterCenter());
		panel.nextRow();
		panel.addHorizontal(createBottomCenter());
		
		return panel;
	}
	
	
	private JPanel createBottomCenter()
	{
		LTSPanel panel = new LTSPanel();
		SimpleAction action = null;
		JButton button = null;
		
		action = new SimpleAction() {
			public void action() {
				startProcess();
			}
		};
		button = new JButton("Start");
		SwingUtils.modifyFont(button, Font.BOLD, 10);
		button.addActionListener(action);
		panel.addButton(button,5);
		
//		action = new SimpleAction() {
//			public void action() {
//				setMaxValue();
//			}
//		};
//		
//		button = new JButton("Make it so!");
//		SwingUtils.modifyFont(button, Font.BOLD, 10);
//		button.addActionListener(action);
//		panel.addButton(button,5);
		
		action = new SimpleAction() {
			public void action() {
				quit();
			}
		};
		
		button = new JButton("Quit");
		button.addActionListener(action);
		SwingUtils.modifyFont(button, Font.BOLD, 10);
		panel.addButton(button, 5);
		
		return panel;
	}

	public static final String[][] DEBUG_CLIENT_COMMAND = {
		{ 
			"-Xdebug",
			"-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=1044",
			SemaphoreApplication.class.getName(), 
			"client",
		},
	};
	
	
	public static final String[][] CLIENT_COMMAND = {
		{ 
			SemaphoreApplication.class.getName(), 
			"client",
		},
	};
	
	
	private void startProcess()
	{
		try
		{
			setupSemaphore();
			Spawn spawn = new Spawn();
			spawn.addToLibraryPath("C:\\files\\opensrc\\clipc-native\\Debug");
			spawn.updateLibraryPath();
			spawn.launch(CLIENT_COMMAND);
		}
		catch (Exception e)
		{
			showException(e);
		}
	}

	private void setupSemaphore() throws IPCException
	{
		if (null == mySemaphore)
		{
			String s = myLimitField.getText().trim();
			if (s.equals(""))
			{
				return;
			}
			
			int limit = Integer.parseInt(s);
			mySemaphore = new Semaphore(SemaphoreConstants.NAME, limit);
			mySemaphore.decrement();
			mySemaphore.increment();
		}
	}

	private JPanel createCenterCenter()
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel label = new JLabel("There can be only");
		SwingUtils.modifyFont(label, Font.BOLD, 10);
		panel.addLabel(label,5);
		
		myLimitField = new JTextField("           ");
		SwingUtils.modifyFont(myLimitField, Font.BOLD, 10);
		panel.addLabel(myLimitField, 5);

		return panel;
	}
	
	
	public static ControlWindow launch() throws LTSException
	{
		JFrame frame = new JFrame();
		ControlWindow win = new ControlWindow(frame);
		frame.setVisible(true);
		return win;
	}
}
