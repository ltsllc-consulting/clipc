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

import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lts.LTSException;
import com.lts.application.swing.ApplicationContentPanel;
import com.lts.event.SimpleThreadedAction;
import com.lts.swing.LTSPanel;

@SuppressWarnings("serial")
public class ConnectWindow extends ApplicationContentPanel
{

	private JTextField myPortField;

	public String buildWindowTitle()
	{
		return getViewName();
	}
	
	
	public void initialize(Container container) throws LTSException
	{
		super.initialize(container);
		setupShowSize(container);
	}

	@Override
	protected String getViewName()
	{
		return "Connect";
	}
	
	@Override
	public Dimension getWindowSize()
	{
		return new Dimension(316, 196);
	}
	
	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		JPanel sub = createCenterTop();
		panel.addHorizontal(sub);
		panel.nextRow();
		
		sub = createCenterBottom();
		panel.addHorizontal(sub);

		return panel;
	}
	
	
	
	public JPanel createCenterTop()
	{
		LTSPanel panel = new LTSPanel();
		JLabel label = new JLabel("Port");
		panel.addLabel(label,5);
		myPortField = new JTextField();
		panel.addHorizontal(myPortField,5);

		return panel;
	}
	
	
	public JPanel createCenterBottom()
	{
		LTSPanel panel = new LTSPanel();
		
		SimpleThreadedAction action = new SimpleThreadedAction() {
			public void action() {
				performListen();
			}
		};
		JButton button = new JButton("Listen");
		button.addActionListener(action);
		panel.addButton(button,5);
		
		action = new SimpleThreadedAction() {
			public void action() throws Exception {
				performConnect();
			}
		};
		button = new JButton("Connect");
		button.addActionListener(action);
		panel.addButton(button,5);
		
		action = new SimpleThreadedAction() {
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


	protected void performConnect() throws UnknownHostException, IOException, LTSException
	{
		String s = myPortField.getText();
		int port = Integer.parseInt(s);
		Socket socket = new Socket("localhost", port);
		SocketAddress addr = new InetSocketAddress(port);
		socket.connect(addr);
		SocketWindow.launch(socket);
		getWindow().setVisible(false);
	}


	protected void performListen()
	{
		
	}

	public static ConnectWindow launch ()
	{
		try
		{
			JFrame frame = new JFrame();
			ConnectWindow win = new ConnectWindow(frame);
			frame.setVisible(true);
			return win;
		}
		catch (LTSException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	public ConnectWindow(JFrame frame) throws LTSException
	{
		initialize(frame);
	}
}
