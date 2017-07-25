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
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.application.swing.ApplicationContentPanel;
import com.lts.event.SimpleActionListener;
import com.lts.ipc.IPCException;
import com.lts.ipc.fifo.FIFO;
import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleListModel;
import com.lts.swing.SwingUtils;
import com.lts.swing.TextWindow;

@SuppressWarnings("serial")
public class FIFOReaderWindow extends ApplicationContentPanel
{
	private SimpleListModel myListModel;
	private BufferedReader myIn;
	private JList myList;
	private ReaderThread myReaderThread;
	private FIFO myFIFO;
	private String myFifoName;
		
	
	@Override
	public String buildWindowTitle()
	{
		return getViewName();
	}


	public BufferedReader getIn()
	{
		return myIn;
	}


	public void setIn(BufferedReader in)
	{
		myIn = in;
	}


	
	public FIFOReaderWindow(JFrame frame, String name, int timeoutMsec) throws LTSException
	{
		initialize(frame, name, timeoutMsec);
	}


	private void initialize(JFrame frame, String name, int timeoutMsec) throws LTSException
	{
		try
		{
			myFifoName = name;
			// initializeFIFO(name, timeoutMsec);
			super.initialize(frame);
			launchThread();
		}
		catch (LTSException e)
		{
			throw e;
		}
	}
	

	private static class ReaderThread implements Runnable
	{
		private Thread myThread;
		private Reader myReader;
		private SimpleListModel myModel;
		private FIFOReaderWindow myReaderWindow;
		
		public ReaderThread(FIFOReaderWindow win, Reader reader, SimpleListModel model)
		{
			myReaderWindow = win;
			myThread = new Thread(this, "Reader Thread");
			myReader = reader;
			myModel = model;
		}

		@Override
		public void run()
		{
			try
			{
				myReaderWindow.myListModel.add("Connecting to " + myReaderWindow.myFifoName);
				myReaderWindow.initializeFIFO();
				myReaderWindow.myListModel.add("Got connection");
				myReader = myReaderWindow.getIn();
				mainLoop();
			}
			catch (Exception e)
			{
				TextWindow.showException(e);
			}
		}
		
		private void mainLoop() throws IOException
		{
			BufferedReader in = new BufferedReader(myReader);
			String s = in.readLine();
			while (null != s)
			{
				myModel.add(s);
				s = in.readLine();
			}
		}
		
		
		public void launch()
		{
			myThread.start();
		}
	}
	
	
	
	private void launchThread()
	{
		myReaderThread = new ReaderThread(this, myIn, myListModel);
		myReaderThread.launch();
	}


	public void initializeFIFO() throws IPCException, IOException
	{
		myFIFO = new FIFO(myFifoName);
		myFIFO.create();
		InputStream istream = myFIFO.getInputStream();
		InputStreamReader isr = new InputStreamReader(istream);
		myIn = new BufferedReader(isr);
	}
	
	
	@Override
	protected String getViewName()
	{
		return "FIFO Reader";
	}

	public JPanel createCenterPanel()
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel label = new JLabel("Messages");
		SwingUtils.modifyFont(label, Font.BOLD, 10);
		panel.addCenteredLabel(label,5);
		
		panel.nextRow();
		
		myListModel = new SimpleListModel();
		myList = new JList(myListModel);
		JScrollPane jsp = new JScrollPane(myList);
		panel.addFill(jsp);
		
		panel.nextRow();
		
		JButton button = new JButton("Quit");
		SimpleActionListener listener = new SimpleActionListener() {
			public void action() {
				quit();
			}
		};
		button.addActionListener(listener);
		panel.addButton(button,5);
		
		return panel;
	}

	
	public void quit()
	{
		Application.getInstance().shutDown();
	}

	public static FIFOReaderWindow launch(String name) throws LTSException
	{
		return launch(name, -1);
	}
	
	
	public void addDemoMessage(String s)
	{
		myListModel.add(s);
	}
	
	
	public void setDemoFonts()
	{
		SwingUtils.modifyFont(myList, Font.BOLD, 5);
		// SwingUtils.modifyFont(myTextArea, Font.BOLD, 5);
	}
	
	
	public static FIFOReaderWindow launchDemo() throws LTSException
	{
		JFrame frame = new JFrame();
		FIFOReaderWindow win = new FIFOReaderWindow(frame, null, -1);
		
		win.setDemoFonts();
		for (String s : DEMO_MESSAGES)
		{
			win.addDemoMessage(s);
		}
		
		// win.setUnsentMessage(DEMO_UNSENT);
		
		frame.setVisible(true);
		return win;
	}
	
	
	public static final String[] DEMO_MESSAGES = {
		"message #1",
		"message #2",
		"message #3",
	};
	
	public static final String DEMO_UNSENT = "w0rd";
	
	
	public static void main(String[] argv)
	{
		try
		{
			File dir = new File(System.getProperty("user.home"));
			File fifoFile = new File(dir, FIFOApplication.FIFO_NAME);
			launch(fifoFile.toString());
		}
		catch (Exception e)
		{
			TextWindow.showException(e);
		}
	}


	public static FIFOReaderWindow launch(String name, int timeoutMsec) throws LTSException 
	{
		JFrame frame = new JFrame();
		File file = new File(name);
		if (!file.isAbsolute())
		{
			File dir = new File(System.getProperty("user.home"));
			file = new File(dir, name);
			name = file.toString();
		}
		FIFOReaderWindow win = new FIFOReaderWindow(frame, name, timeoutMsec);
		frame.setVisible(true);
		return win;
	}
}
