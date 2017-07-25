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
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.lts.LTSException;
import com.lts.chatterbox.MultiMonException;
import com.lts.chatterbox.monitor.LimitedQueueListModel;
import com.lts.chatterbox.procwin.ChatterListModelAdaptor;
import com.lts.chatterbox.procwin.ChatterListener;
import com.lts.chatterbox.procwin.ChatterMessage;
import com.lts.chatterbox.procwin.ChatterThread;
import com.lts.event.SimpleThreadedAction;
import com.lts.swing.LTSPanel;
import com.lts.swing.SwingUtils;
import com.lts.swing.TextWindow;
import com.lts.swing.panel.RootPaneContentPanel;

@SuppressWarnings("serial")
public class ProcessWindow extends RootPaneContentPanel implements ChatterListener
{
	private ChatterThread myProcess;
	private JTextArea myTextArea;
	private JTextField myPauseField;
	private LimitedQueueListModel myListModel;
	private JList myList;

	@Override
	public JPanel createCenterPanel() throws LTSException
	{
		LTSPanel panel = new LTSPanel();
		
		panel.addHorizontal(createCenterTop());
		panel.nextRow();
		panel.addFill(createCenterCenter());
		
		return panel;
	}


	@SuppressWarnings("unused")
	private LTSPanel createCenterCenterBottom()
	{
		JLabel label;
		JScrollPane jsp;
		LTSPanel panel = new LTSPanel();
		
		label = new JLabel("Last Message I Sent");
		SwingUtils.modifyFont(label, Font.BOLD, 5);
		panel.addCenteredLabel(label,5);
		
		panel.nextRow();
		
		myTextArea = new JTextArea();
		myTextArea.setEditable(false);
		jsp = new JScrollPane(myTextArea);
		panel.addFill(jsp);
	
		return panel;
	}


	private LTSPanel createCenterCenter()
	{
		LTSPanel panel = new LTSPanel();
		JLabel label;
		
		label = new JLabel("All Messages");
		SwingUtils.modifyFont(label, Font.BOLD, 5);
		panel.addCenteredLabel(label,5);
		
		panel.nextRow();
		
		myListModel = new LimitedQueueListModel();
		myListModel.setLimit(100);
		myList = new JList(myListModel);
		SwingUtils.modifyFont(myList, Font.BOLD, 10);
		JScrollPane jsp = new JScrollPane(myList);
		panel.addFill(jsp);
		return panel;
	}
	
	
	public void initialize(Container container) throws LTSException
	{
		setHeadingString("Chatter");
		setBottomPanelMode(BOTTOM_PANEL_CLOSE);
		setExitOnClose(true);
		super.initialize(container);
	}

	public Dimension getWindowSize() 
	{
		return toPercentOfScreen(0.35);
	}

	public ProcessWindow(JFrame frame) throws LTSException
	{
		super(frame);
	}

	public ChatterThread getProcess()
	{
		return myProcess;
	}
	
	public void setProcess(ChatterThread process)
	{
		myProcess = process;
		updateFields();
	}

	public static ProcessWindow launch(String[] argv) throws LTSException, MultiMonException
	{
		logStart(argv);
		SwingUtils.setLookAndFeel(ProcessWindow.class);
		JFrame frame = new JFrame();
		
		if (null == argv)
		{
			JOptionPane.showMessageDialog(null, "Null ARGV");
			System.exit(1);
		}
		
		if (argv.length < 2)
		{
			JOptionPane.showMessageDialog(null, "Argv less than 2");
			System.exit(1);
		}
		
		ProcessWindow win = new ProcessWindow(frame);
		frame.setVisible(true);
		
		String segmentName = argv[0];
		String speakerName = argv[1];
		
		long pauseTime = ChatterThread.DEFAULT_PAUSE_TIME;
		if (argv.length > 2)
		{
			pauseTime = Long.parseLong(argv[2]);
		}
		
		
		ChatterThread proc;
		SharedMemoryChannel channel = new SharedMemoryChannel(segmentName);
		proc = ChatterThread.launch(channel, speakerName, pauseTime);
		if (null == proc)
		{
			JOptionPane.showMessageDialog(null, "Error launching process");
			System.exit(1);
		}
		
		new ChatterListModelAdaptor(win.myListModel, channel);
		
		win.setProcess(proc);
		proc.addListener(win);
		
		return win;
	}


	private static void logStart(String[] argv)
	{
		try
		{
			String name = "/foo";
			FileOutputStream fos = new FileOutputStream(name, true);
			PrintWriter out = new PrintWriter(fos);
			out.print(System.currentTimeMillis());
			out.println(" started " + ProcessWindow.class.getName());
			logArgv(out, argv);
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void logArgv(PrintWriter out, String[] argv)
	{
		for (String s : argv)
		{
			out.println ("\t" + s);
		}
	}


	public static void main(String[] argv)
	{
		try
		{
			launch(argv);
		}
		catch (LTSException e)
		{
			TextWindow.showException(e);
		}
		catch (MultiMonException e)
		{
			TextWindow.showException(e);
		}
		finally {}
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


	private JPanel createCenterTop()
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel label = new JLabel("Chatter pause");
		panel.addLabel(label,5);
		
		myPauseField = new JTextField();
		panel.addHorizontal(myPauseField,5);
		
		JButton button = new JButton("Update");
		SimpleThreadedAction action = new SimpleThreadedAction() {
			public void action() {
				updateChatterSleepTime();
			}
		};
		button.addActionListener(action);
		
		panel.addLabel(button,5);
		
		
		return panel;
	}
	
	
	private void updateChatterSleepTime()
	{
		try
		{
			int pauseTime = Integer.parseInt(myPauseField.getText());
			getProcess().setPauseTime(pauseTime);
		}
		catch (Exception e)
		{
			;
		}
	}


	@Override
	public void chatter(ChatterMessage message)
	{
		String s = myTextArea.getText();
		s = s + message.message;
		myTextArea.setText(s);
	}
	
	
	private void updateFields()
	{
		long pauseTime = getProcess().getPauseTime();
		myPauseField.setText(Long.toString(pauseTime));
	}
}
