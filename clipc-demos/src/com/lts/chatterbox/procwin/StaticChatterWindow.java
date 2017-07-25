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
package com.lts.chatterbox.procwin;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.lts.LTSException;
import com.lts.chatterbox.MultiMonException;
import com.lts.chatterbox.smem.ProcessWindow;
import com.lts.chatterbox.smem.SharedMemoryChannel;
import com.lts.swing.ActionAdapter;
import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleListModel;
import com.lts.swing.SwingUtils;
import com.lts.swing.keyboard.InputKey;
import com.lts.swing.panel.RootPaneContentPanel;

@SuppressWarnings("serial")
public class StaticChatterWindow extends RootPaneContentPanel 
{
	private ChatterThread myProcess;
	private JTextField myPauseField;
	private SimpleListModel myListModel;
	private JList myList;

	@Override
	public JPanel createCenterPanel() throws LTSException
	{
		LTSPanel panel = new LTSPanel();
		panel.addFill(createCenterCenter());
		return panel;
	}


	private JPanel createCenterCenter()
	{
		LTSPanel panel = new LTSPanel();
		JLabel label;
		
		label = new JLabel("All Messages");
		SwingUtils.modifyFont(label, Font.BOLD, 5);
		panel.addCenteredLabel(label,5);
		
		panel.nextRow();
		
		myListModel = new SimpleListModel();
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
		Action action = new ActionAdapter() {
			public void actionPerformed(ActionEvent event) {
				nextMessage();
			}
		};
		
		SwingUtils.mapKeyAsDefault(InputKey.Enter, action, this);
		SwingUtils.mapKeyAsDefault(InputKey.DownArrow, action, this);
	}

	public Dimension getWindowSize() 
	{
		return toPercentOfScreen(0.35);
	}

	public StaticChatterWindow(JFrame frame) throws LTSException
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

	public static StaticChatterWindow launch(String[] argv) throws LTSException, MultiMonException
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
		
		StaticChatterWindow win = new StaticChatterWindow(frame);
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
		
		// SharedMemoryReceiver.launch(channel, 250);
		
		win.setProcess(proc);
		
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


	private void updateFields()
	{
		long pauseTime = getProcess().getPauseTime();
		myPauseField.setText(Long.toString(pauseTime));
	}
	
	public static void main(String[] argv)
	{
		try
		{
			SwingUtils.setLookAndFeel();
			JFrame frame = new JFrame();
			new StaticChatterWindow(frame);
			frame.setVisible(true);
		}
		catch (HeadlessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (LTSException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	String[] msgs1 = {
			"Betty: FINALLY!",
			"Art: d00dz!  If we don't applaud he'll keep talking!",
			"Charlie: NOOOOO!!!!1111",
			"Dave: ZOMG!",
			"Betty: <CLAP> <CLAP> <CLAP>",
		};

	String[] msgs2 = {
			"Art: d0es bitt0rrent use sawckets?!",
			"Charlie: I hope knot tey s0und slow",
			"Dave: thatz just yer system dewd",
			"Betty: pwnd!",
		};
	
	String[] msgs = {
			"Charlie: whed y0u wanna use theze thingz?",
			"Dave: Yeah, u can only send 0n3 w4y",
			"Betty: I use em all the time with you guys",
			"Art: LOL!",
	};

	String[][] allmsgs = {
			msgs1,
			msgs2,
			msgs
	};
	
	private int myMessageIndex;
	private int mySetIndex;
	
	private void nextMessage()
	{
		if (myMessageIndex >= allmsgs[mySetIndex].length)
		{
			myMessageIndex = 0;
			myListModel.clear();
			mySetIndex++;
		}
		
		if (mySetIndex >= allmsgs.length)
		{
			mySetIndex = 0;
		}
		
		myListModel.add(allmsgs[mySetIndex][myMessageIndex]);
		myMessageIndex++;
	}
	
	public void closeButtonPressed()
	{
//		if (myMessageIndex >= msgs.length)
//		{
//			myMessageIndex = 0;
//			myListModel.clear();
//		}
//		
//		myListModel.add(msgs[myMessageIndex]);
//		myMessageIndex++;
		nextMessage();
	}
}
