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
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.application.swing.ApplicationContentPanel;
import com.lts.event.SimpleAction;
import com.lts.ipc.semaphore.Semaphore;
import com.lts.ipc.test.ThreadUtil;
import com.lts.swing.LTSPanel;
import com.lts.swing.SwingUtils;

@SuppressWarnings("serial")
public class ClientWindow extends ApplicationContentPanel
{
	private JTextField myStatusField;
	private Semaphore mySemaphore;
	
	
	public class StatusThread implements Runnable
	{
		public void run()
		{
			try
			{
				myStatusField.setText("Waiting");
				mySemaphore.decrement();
				myStatusField.setText("Running");
				Random r = new Random();
				long sleepTime = r.nextLong() % 20000;
				if (0 > sleepTime)
				{
					sleepTime = -1 * sleepTime;
				}
				
				ThreadUtil.sleep(sleepTime);
				mySemaphore.increment();
				myStatusField.setText("Done");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public ClientWindow(JFrame frame) throws LTSException
	{
		initialize(frame);
	}


	protected String buildWindowName()
	{
		return getViewName();
	}
	
	
	public String buildWindowTitle()
	{
		return getViewName();
	}
	
	@Override
	protected String getViewName()
	{
		return "Client";
	}

	public static ClientWindow launch()
	{
		ClientWindow win = null;
		
		try
		{
			JFrame frame = new JFrame();
			win = new ClientWindow(frame);
			frame.setVisible(true);
		}
		catch (Exception e)
		{
			showException(e);
		}
		
		return win;
	}
	
	
	public void startThread()
	{
		StatusThread st = new StatusThread();
		Thread thread = new Thread(st, "Status Thread");
		thread.start();
	}
	
	
	public void initialize(Container container) throws LTSException
	{
		try
		{
			setupShowSize(container);
			mySemaphore = new Semaphore(SemaphoreConstants.NAME, 1);
			super.initialize(container);
		}
		catch (Exception e)
		{
			throw new LTSException("Error connecting to semaphore", e);
		}
	}
	
	@Override
	public Dimension getWindowSize()
	{
		return new Dimension(313, 200);
	}


	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		panel.addFill(createCenterCenter(),25);
		panel.nextRow();
		panel.addHorizontal(createBottomCenter());
		
		return panel;
	}
	
	
	public JPanel createCenterCenter() 
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel label = new JLabel("Status");
		SwingUtils.modifyFont(label, Font.BOLD, 10);
		panel.addLabel(label,5);
		
		myStatusField = new JTextField("Ready");
		SwingUtils.modifyFont(myStatusField, Font.BOLD, 10);
		panel.addHorizontal(myStatusField,5);
		myStatusField.setEditable(false);
		panel.addHorizontal(myStatusField,5);

		return panel;
	}
	
	
	public JPanel createBottomCenter()
	{
		LTSPanel panel = new LTSPanel();
		ActionListener action;
		JButton button = null;
		
		button = new JButton("GO");
		SwingUtils.modifyFont(button, Font.BOLD, 10);
		action = new SimpleAction() {
			public void action() {
				startThread();
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
		SwingUtils.modifyFont(button, Font.BOLD, 10);
		button.addActionListener(action);
		
		panel.addButton(button,5);
		
		return panel;
	}
	
	
	public void quit()
	{
		Application.getInstance().shutDown();
	}
}
