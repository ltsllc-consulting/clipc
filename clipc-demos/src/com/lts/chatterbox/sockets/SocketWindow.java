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
package com.lts.chatterbox.sockets;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

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
import com.lts.application.swing.ApplicationContentPanel;
import com.lts.event.SimpleAction;
import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleListModel;
import com.lts.swing.SwingUtils;
import com.lts.swing.keyboard.InputKey;

@SuppressWarnings("serial")
public class SocketWindow extends ApplicationContentPanel
{
	private Socket mySocket;
	private SimpleListModel myListModel;
	private JTextArea myTextArea;
	private PrintWriter myOut;
	
	
	
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


	public BufferedReader getIn()
	{
		return myIn;
	}


	public void setIn(BufferedReader in)
	{
		myIn = in;
	}


	private BufferedReader myIn;
	private JList myList;
	
	
	public SocketWindow(JFrame frame, Socket socket) throws LTSException
	{
		initialize(frame, socket);
	}


	private void initialize(JFrame frame, Socket socket) throws LTSException
	{
		try
		{
			if (null != socket)
			{
				setSocket(socket);
				InputStreamReader isr = new InputStreamReader(socket.getInputStream());
				myIn = new BufferedReader(isr);
				OutputStreamWriter osr = new OutputStreamWriter(socket.getOutputStream());
				myOut = new PrintWriter(osr);
			}
			
			super.initialize(frame);
		}
		catch (IOException e)
		{
			throw new LTSException("Error setting up streams",e);
		}
		catch (LTSException e)
		{
			throw e;
		}
	}


	public void setSocket(Socket socket)
	{
		mySocket = socket;
	}
	
	public Socket getSocket()
	{
		return mySocket;
	}


	@Override
	protected String getViewName()
	{
		return "Socket Client";
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
		System.exit(0);
	}

	private JPanel createCenterTop()
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel label = new JLabel("Log");
		SwingUtils.modifyFont(label, Font.BOLD, 5);
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
		SwingUtils.modifyFont(label, Font.BOLD, 5);
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
		myListModel.add(s);
	}


	public static SocketWindow launch(Socket socket) throws LTSException
	{
		JFrame frame = new JFrame();
		SocketWindow win = new SocketWindow(frame, socket);
		win.setVisible(true);
		return win;
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
	
	
	public static SocketWindow launchDemo() throws LTSException
	{
		JFrame frame = new JFrame();
		SocketWindow win = new SocketWindow(frame, null);
		
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
		"Heya",
		"How's life?",
		"It beats the alternatives",
	};
	
	public static final String DEMO_UNSENT = "w0rd";
}
