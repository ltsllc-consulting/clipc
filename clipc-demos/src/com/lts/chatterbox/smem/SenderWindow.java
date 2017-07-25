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
package com.lts.chatterbox.smem;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.lts.LTSException;
import com.lts.application.swing.ApplicationContentPanel;
import com.lts.chatterbox.procwin.ChatterThread;
import com.lts.event.SimpleAction;
import com.lts.swing.LTSPanel;
import com.lts.swing.SwingUtils;

@SuppressWarnings("serial")
public class SenderWindow extends ApplicationContentPanel
{
	private ChatterThread myProcess;
	private JTextArea myTextArea;
	private int myFontSizeMod = 0;

	
	public String getViewName()
	{
		return "Sender";
	}
	
	
	@Override
	public JPanel createCenterPanel() throws LTSException
	{
		LTSPanel panel = new LTSPanel();
		
		panel.addFill(createTopCenter());
		panel.nextRow();
		panel.addHorizontal(createBottomCenter());
		
		return panel;
	}


	private JPanel createTopCenter()
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel label = new JLabel("Sender");
		SwingUtils.modifyFont(label, Font.BOLD, myFontSizeMod);
		panel.addCenteredLabel(label,5);
		
		panel.nextRow();
		
		myTextArea = new JTextArea();
		SwingUtils.modifyFont(myTextArea, Font.BOLD, myFontSizeMod);
		JScrollPane jsp = new JScrollPane(myTextArea);
		panel.addFill(jsp,5);
		
		
		return panel;
	}
	
	
	private JPanel createBottomCenter() 
	{
		LTSPanel panel = new LTSPanel();
		
		
		JButton button = new JButton("Send");
		SimpleAction action = new SimpleAction() {
			public void action() {
				sendMessage();
			}
		};
		button.addActionListener(action);
		SwingUtils.modifyFont(button, Font.BOLD, myFontSizeMod);
		panel.addButton(button,5);
		
		button = new JButton("Quit");
		action = new SimpleAction() {
			public void action() {
				quit();
			}
		};
		button.addActionListener(action);
		SwingUtils.modifyFont(button, Font.BOLD, myFontSizeMod);
		panel.addButton(button,5);
		
		return panel;
	}


	protected void sendMessage()
	{
		// TODO Auto-generated method stub
		
	}


	public void initialize(Container container) throws LTSException
	{
		setExitOnClose(true);
		super.initialize(container);
	}

	public Dimension getWindowSize() 
	{
		return toPercentOfScreen(0.35);
	}

	public SenderWindow(JFrame frame) throws LTSException
	{
		initialize(frame);
	}

	public SenderWindow(JFrame frame, int fontSizeMod) throws LTSException
	{
		initialize(frame, fontSizeMod);
	}


	protected void initialize(JFrame frame, int fontSizeMod) throws LTSException
	{
		myFontSizeMod = fontSizeMod;
		initialize(frame);
	}
	
	
	public ChatterThread getProcess()
	{
		return myProcess;
	}
	
	public static SenderWindow launch(String[] argv) 
	{
		SenderWindow win = null;
		
		try
		{
			JFrame frame = new JFrame();
			win = new SenderWindow(frame, 10);
			frame.setVisible(true);
		}
		catch (LTSException e)
		{
			showException(e);
		}
		
		return win;
	}


	public void closeWindow()
	{
		try
		{
			if (null != getProcess())
			{
				getProcess().shutDownThread();
				getProcess().getThread().join();
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		super.closeWindow();
	}
}
