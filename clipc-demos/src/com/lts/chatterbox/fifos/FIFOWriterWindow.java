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
package com.lts.chatterbox.fifos;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.application.swing.ApplicationContentPanel;
import com.lts.event.SimpleAction;
import com.lts.ipc.fifo.FIFO;
import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleListModel;
import com.lts.swing.SwingUtils;
import com.lts.swing.keyboard.InputKey;

@SuppressWarnings("serial")
public class FIFOWriterWindow extends ApplicationContentPanel
{
	private SimpleListModel myListModel;
	private JTextArea myTextArea;
	private PrintWriter myOut;
	private JList myList;
	private FIFO myFIFO;
	private String myName;
	private int myTimeout;
	
	@Override
	public String buildWindowTitle()
	{
		return getViewName();
	}


	public PrintWriter getOut()
	{
		return myOut;
	}


	public void setOut(PrintWriter out)
	{
		myOut = out;
	}


	public FIFOWriterWindow(JFrame frame, String name, int timeoutMsec) throws LTSException
	{
		initialize(frame, name, timeoutMsec);
	}


	private void initialize(JFrame frame, String name, int timeoutMsec) throws LTSException
	{
		try
		{
			File file = new File(name);
			if (!file.isAbsolute())
			{
				File dir = new File(System.getProperty("user.home"));
				file = new File(dir, name);
				name = file.toString();
			}
			
			
			myName = name;
			myTimeout = timeoutMsec;
			
			super.initialize(frame);
		}
		catch (LTSException e)
		{
			throw e;
		}
	}

	
	public void intializeFifo()
	{
		try 
		{
			myTextArea.setText("Connecting to " + myName);
			myFIFO = new FIFO(myName, myTimeout);
			OutputStream ostream = myFIFO.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(ostream);
			myOut = new PrintWriter(osw);
			myTextArea.setText("Got connection!");
		} 
		catch (IOException e) 
		{
			showException(e);
		}
	}


	public void setFIFO(FIFO pipe)
	{
		myFIFO = pipe;
	}
	
	public FIFO getSocket()
	{
		return myFIFO;
	}


	@Override
	protected String getViewName()
	{
		return "FIFO Writer";
	}

	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		JPanel top = createCenterTop();
		JPanel bottom = createCenterBottom();
		JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, top, bottom);
		
		panel.addFill(jsp);
		panel.nextRow();
		
		bottom = createButtonPanel();
		panel.addHorizontal(bottom,5);
		
		return panel;
	}


	private JPanel createButtonPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		SimpleAction action = new SimpleAction() {
			public void action () {
				submitMessage();
			}
		};
		JButton button = new JButton("Send");
		button.addActionListener(action);
		panel.addButton(button,4);
		
		action = new SimpleAction() {
			public void action() {
				performQuit();
			}
		};
		button = new JButton("Quit");
		button.addActionListener(action);
		panel.addButton(button,5);
		
		return panel;
	}


	protected void performQuit()
	{
		Application.getInstance().shutDown();
	}

	private JPanel createCenterTop()
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel label = new JLabel("Log");
		SwingUtils.modifyFont(label, Font.BOLD, 10);
		panel.addCenteredLabel(label,5);
		
		panel.nextRow();
		
		myListModel = new SimpleListModel();
		myList = new JList(myListModel);
		JScrollPane jsp = new JScrollPane(myList);
		panel.addFill(jsp);
		
		return panel;
	}


	private JPanel createCenterBottom()
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel label = new JLabel("Message");
		SwingUtils.modifyFont(label, Font.BOLD, 10);
		panel.addCenteredLabel(label,5);
		
		panel.nextRow();
		
		myTextArea = new JTextArea();
		JScrollPane jsp = new JScrollPane(myTextArea);
		panel.addFill(jsp,5);
		
		AbstractAction action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				submitMessage();
			}
		};
		SwingUtils.mapKeyAsDefault(InputKey.Enter, action, myTextArea);
		
		return panel;
	}


	protected void submitMessage()
	{
		String s = myTextArea.getText();
		getOut().println(s);
		getOut().flush();
		myListModel.add(s);
	}


	public static FIFOWriterWindow launch(String name) throws LTSException
	{
		return launch(name, -1);
	}
	
	
	public void addDemoMessage(String s)
	{
		myListModel.add(s);
	}
	
	
	public void setUnsentMessage(String s)
	{
		myTextArea.setText(s);
	}
	
	public void setDemoFonts()
	{
		SwingUtils.modifyFont(myList, Font.BOLD, 5);
		SwingUtils.modifyFont(myTextArea, Font.BOLD, 5);
	}
	
	
	public static FIFOWriterWindow launchDemo() throws LTSException
	{
		JFrame frame = new JFrame();
		File dir = new File(System.getProperty("java.home"));
		File fifoFile = new File(dir, FIFOChatterbox.FIFO_NAME);
		FIFOWriterWindow win = new FIFOWriterWindow(frame, fifoFile.toString(), -1);
		
		win.setDemoFonts();
		for (String s : DEMO_MESSAGES)
		{
			win.addDemoMessage(s);
		}
		
		win.setUnsentMessage(DEMO_UNSENT);
		
		frame.setVisible(true);
		return win;
	}
	
	
	public static final String[] DEMO_MESSAGES = {
		"message #1",
		"message #2",
		"message #3",
	};
	
	public static final String DEMO_UNSENT = "Message #4";

	public static FIFOWriterWindow launch(String name, int timeoutMsec) throws LTSException 
	{
		JFrame frame = new JFrame();
		FIFOWriterWindow win = new FIFOWriterWindow(frame, name, timeoutMsec);
		frame.setVisible(true);
		return win;		
	}
}
