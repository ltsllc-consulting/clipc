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
package com.lts.chatterbox.controlpanel;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.lts.LTSException;
import com.lts.application.swing.ApplicationContentPanel;
import com.lts.chatterbox.app.ChatterboxApplication;
import com.lts.chatterbox.monitor.MonitorWindow;
import com.lts.chatterbox.smem.ProcessWindow;
import com.lts.event.SimpleThreadedAction;
import com.lts.swing.LTSPanel;
import com.lts.test.Spawn;
import com.lts.test.SpawnException;

@SuppressWarnings("serial")
public class ControlWindow extends ApplicationContentPanel
{
	private static ControlWindow ourInstance;

	private JButton myStartButton;
	private JButton myStopButton;

	public ControlWindow (JFrame frame) throws LTSException
	{
		super(frame);
	}

	
	public void initialize(Container container) throws LTSException
	{
		super.initialize(container);
		setExitOnClose(true);
	}
	
	public static void launch ()
	{
		try
		{
			if (null == ourInstance)
			{
				JFrame frame = new JFrame();
				ourInstance = new ControlWindow(frame);
				frame.setVisible(true);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	
	public JPanel createCenterPanel() 
	{
		SimpleThreadedAction action;
		LTSPanel panel = new LTSPanel();

		action = new SimpleThreadedAction() {
			public void action() {
				spawnTest();
			}
		};
		
		myStartButton = new JButton("Start");
		myStartButton.addActionListener(action);
		
		panel.addButton(myStartButton);
		
		myStopButton = new JButton("Quit");
		panel.addButton(myStopButton);

		action = new SimpleThreadedAction() {
			public void action() {
				terminateTest();
			}
		};
		
		myStopButton.addActionListener(action);
		
		return panel;
	}

	protected void terminateTest()
	{
		Spawn spawn = ChatterboxApplication.getApp().getSpawn();
		spawn.terminate();
		super.quit();
	}

	public static final String SEGMENT_NAME = "/temp/clipc";
	
	public static final String[][] tests = {
		{ ProcessWindow.class.getName(), SEGMENT_NAME, "abe", 	"3000" },
		{ ProcessWindow.class.getName(), SEGMENT_NAME, "betty",	"3000" },
		{ ProcessWindow.class.getName(), SEGMENT_NAME, "charlie","3000"},
		{ ProcessWindow.class.getName(), SEGMENT_NAME, "dave",	"3000" },
		{ MonitorWindow.class.getName(), SEGMENT_NAME },
	};
	
	protected void spawnTest()
	{
		try
		{
			Spawn spawn = ChatterboxApplication.getApp().getSpawn();
			spawn.setDebug(true);
			spawn.launch(tests);
		}
		catch (SpawnException e)
		{
			e.printStackTrace();
		}
	}


	@Override
	protected String getViewName()
	{
		return "Control Panel";
	}
}
