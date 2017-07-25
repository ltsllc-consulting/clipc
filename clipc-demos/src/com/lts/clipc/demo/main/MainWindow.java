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
package com.lts.clipc.demo.main;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.lts.LTSException;
import com.lts.application.Application;
import com.lts.clipc.demo.DemoProperties;
import com.lts.clipc.demo.DemoWindow;
import com.lts.clipc.demo.lostupdate.Update;
import com.lts.event.SimpleAction;
import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleListModel;
import com.lts.swing.SwingUtils;
import com.lts.swing.combobox.SimpleComboBoxModel;
import com.lts.swing.filefield.FileField;
import com.lts.test.Spawn;
import com.lts.test.SpawnException;

@SuppressWarnings("serial")
public class MainWindow extends DemoWindow
{
	private static MainWindow ourInstance;
	
	public static MainWindow getInstance()
	{
		return ourInstance;
	}
	
	public static synchronized MainWindow launch()
	{
		MainWindow win = null;
		
		try
		{
			JFrame frame = new JFrame();
			win = new MainWindow(frame);
			ourInstance = win;
			frame.setVisible(true);
		}
		catch (LTSException e)
		{
			showException(e);
		}
		
		return win;
	}


	private JComboBox myComboBox;
	private Spawn mySpawn;
	private FileField myFileField;
	private FileField mySemaphoreField;
	private SimpleListModel myListModel;
	
	public MainWindow (JFrame frame) throws LTSException
	{
		initialize(frame);
	}
	
	
	public void initialize(Container container) throws LTSException
	{
		setupShowSize(container);
		setupFontSizing(container);
		setExitOnClose(true);
		setBottomPanelMode(BOTTOM_PANEL_GO_QUIT);
		super.initialize(container);
		loadSettingsFromProperties();
	}
	
	private enum AllDemos 
	{
		LostUpdate("Lost Update - no Semaphores"),
		LostUpdateSemaphores("Lost Update - with Semaphores");
		
		public String title;
		
		AllDemos (String s)
		{
			title = s;
		}
		
		public static Object[][] SPEC = {
			{ LostUpdate.title, LostUpdate },
			{ LostUpdateSemaphores.title, LostUpdateSemaphores },
		};
		
		public static AllDemos titleToElement(String s)
		{
			AllDemos element = null;
			
			for (AllDemos temp : AllDemos.values())
			{
				if (temp.title.equalsIgnoreCase(s))
				{
					element = temp;
					break;
				}
			}
			
			return element;
		}
	}
	
	
	protected JPanel createCenterBottom()
	{
		LTSPanel panel = new LTSPanel();
		
		JLabel label = new JLabel("Output");
		SwingUtils.modifyFont(label, Font.BOLD, 4);
		panel.addCenteredLabel(label,5);
		
		panel.nextRow();
		
		JButton button = new JButton("Clear");
		SimpleAction action = new SimpleAction() {
			public void action() {
				myListModel.clear();
			}
		};
		button.addActionListener(action);
		panel.addCenteredLabel(button,5);
		
		panel.nextRow();
		
		myListModel = new SimpleListModel();
		JList list = new JList(myListModel);
		JScrollPane jsp = new JScrollPane(list);
		panel.addFill(jsp,5);
		
		return panel;
	}
	
	public JPanel createCenterTop() throws LTSException
	{
		LTSPanel panel = new LTSPanel();
		JLabel label;
		
		label = new JLabel("Semaphore File");
		SwingUtils.setBold(label);
		panel.addLabel(label,5);
		
		mySemaphoreField = new FileField();
		panel.addHorizontal(mySemaphoreField,5);
		
		panel.nextRow();
		
		label = new JLabel("Account File");
		SwingUtils.setBold(label);
		panel.addLabel(label, 5);
		
		myFileField = new FileField();
		panel.addHorizontal(myFileField,5);
		
		panel.nextRow();
		
		label = new JLabel("Demo");
		SwingUtils.setBold(label);
		panel.addLabel(label,5);
		
		SimpleComboBoxModel model = new SimpleComboBoxModel(AllDemos.SPEC);
		myComboBox = new JComboBox(model);
		
		panel.addLabel(myComboBox,5);
		
		return panel;
	}
	
	public JPanel createCenterPanel() throws LTSException
	{
		LTSPanel panel = new LTSPanel();
		
		JPanel temp = createCenterTop();
		panel.addHorizontal(temp);
		panel.nextRow();
		temp = createCenterBottom();
		panel.addFill(temp);
		
		
		return panel;
	}
	
	
	protected void browseAccountFile()
	{
		
	}

	@Override
	protected String getViewName()
	{
		return "CLIPC Demos";
	}
	
	@Override
	public Dimension getWindowSize()
	{
		return new Dimension(515, 407);
	}
	
	
	public void goButtonPressed()
	{
		String s = (String) myComboBox.getSelectedItem();
		if (null == s)
			return;
		
		AllDemos demo = AllDemos.titleToElement(s);
		if (null == demo)
			return;
		
		saveLastFileSettings();
		File accountFile = myFileField.getFile();
		File semFile = mySemaphoreField.getFile();
		
		mySpawn = new Spawn();
		StreamToListModel out = new StreamToListModel(myListModel);
		mySpawn.setOutputWriter(out);
		mySpawn.setErrorWriter(out);
		
		switch (demo)
		{
			case LostUpdate :
				spawn(2,Update.class, accountFile);
				break;
				
			case LostUpdateSemaphores :
				spawn(2, Update.class, semFile, accountFile);
				break;
		}
	}
	
	
	private void saveLastFileSettings()
	{
		Application app = Application.getInstance();
		DemoProperties p = (DemoProperties) Application.getInstance().getProperties();
		p.setAccountFileName(myFileField.getTextField().getText());
		p.setSemaphoreFileName(mySemaphoreField.getTextField().getText());
		app.storeApplicationProperties();
	}
	
	private void loadSettingsFromProperties()
	{
		Application app = Application.getInstance();
		DemoProperties p = (DemoProperties) app.getProperties();
		myFileField.getTextField().setText(p.getAccountFileName());
		mySemaphoreField.getTextField().setText(p.getSemaphoreFileName());
	}

	private void spawn(int i, Class clazz, File semFile, File accountFile)
	{
		try
		{
			String[] desc = { clazz.getName(), accountFile.toString(), semFile.toString() };
			mySpawn.launch(2,desc);
		}
		catch (SpawnException e)
		{
			showException(e);
		}
	}

	private void spawn(int count, Class clazz, File file)
	{
		try
		{
			mySpawn.launch(2, clazz, file.toString());
		}
		catch (Exception e)
		{
			showException(e);
		}
	}
	
	

}
