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
//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the com.lts.pest library.
//
//  The com.lts.pest library is free software; you can redistribute it and/or
//  modify it under the terms of the Lesser GNU General Public License as
//  published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  The com.lts.pest library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU
//  General Public License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the com.lts.pest library; if not, write to the Free Software
//  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.pest;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.lts.application.Application;
import com.lts.application.ApplicationException;
import com.lts.application.ApplicationProperties;
import com.lts.application.ApplicationRepository;
import com.lts.application.checkpoint.CheckPointManager;
import com.lts.io.ImprovedFile;
import com.lts.scheduler.ScheduledEventListener;
import com.lts.scheduler.Scheduler;
import com.lts.util.StringUtils;

/**
 * 
 * @author cnh
 */
abstract public class Pest extends Application
{
	protected static boolean debugDialogs = true;
	
	transient protected Scheduler myScheduler;
	transient protected ScheduledEventListener schedulerListener;
	transient protected boolean isQuestioning;
	transient protected ApplicationRepository myCheckpointRepository;

	private ImprovedFile myCheckPointDirectory;
	
	public void setCheckpointRepository(ApplicationRepository repos)
	{
		myCheckpointRepository = repos;
	}
	
	
	public static boolean getAlwaysOnTop ()
	{
		return !debugDialogs;
	}
	
	
	public boolean isQuestioning()
	{
		return this.isQuestioning;
	}
	
	public boolean getQuestioning()
	{
		return isQuestioning();
	}
	
	protected void addResourcePathElements(List list)
	{
		list.add("resources.messages.pest");
	}
	
	
	@Override
	public String getApplicationName()
	{
		return "Pest";
	}

	
	public Scheduler getScheduler()
	{
		return Scheduler.getInstance();
	}

	static public Application getApp()
	{
		return (Application) getInstance();
	}
	
	
	public synchronized void checkPoint() throws ApplicationException
	{
		if (null == myCheckpointRepository)
		{
			myCheckpointRepository = createCheckPointRepository();
		}
		
		myCheckpointRepository.storeApplicationData(getApplicationData());
		myCheckpointRepository.commit();
		myCheckpointRepository.close();
	}
	
	
	protected ApplicationRepository getCheckpointRepository() throws ApplicationException
	{
		if (null == myCheckpointRepository)
			myCheckpointRepository = createRepository();
		
		return myCheckpointRepository;
	}
	protected ApplicationRepository createCheckPointRepository() throws ApplicationException
	{
		try
		{
			if (!getCheckPointDirectory().exists() && !getCheckPointDirectory().mkdirs())
			{
				String msg = "Could not create check point directory: "
						+ getCheckPointDirectory();
	
				throw new ApplicationException(msg);
			}
			
			File reposFile = File.createTempFile("checkpoint", ".zip", getCheckPointDirectory());
			ApplicationRepository repos = createRepository(reposFile, getCheckPointDirectory(), false);
			deleteOnShutdown(reposFile);
			return repos;
		}
		catch (IOException e)
		{
			String msg = "Error creating repository for checkpoint";
			throw new ApplicationException(msg);
		}
	}
	public ImprovedFile getCheckPointDirectory()
	{
		if (null == myCheckPointDirectory)
		{
			myCheckPointDirectory = new ImprovedFile(getMasterTempDir(), "checkpoints");
		}
		
		return myCheckPointDirectory;
	}
	
	
	protected void processCheckPoints()
	{
		try
		{
			File dir = getCheckPointDirectory();
			String[] files = null;
			if (dir.isDirectory())
				files = dir.list();
			
			if (null != files && files.length > 0)
			{
				CheckPointManager manager = CheckPointManager.getInstance();
				manager.setCheckPointDirectory(getCheckPointDirectory());
				manager.cleanupCheckPointDirectory();
				manager.reloadData();
				
				manager.displayAndWait();
				if (manager.quitApplication())
					quit();
				
				if (null != manager.getRepository())
					setRepository(manager.getRepository());
			}
		}
		catch (ApplicationException e)
		{
			String msg = "Error processing checkpoints";
			Application.showException(msg, e);
		}
	}
	protected boolean checkPointPresent()
	{
		ImprovedFile dir = getCheckPointDirectory();
		if (!dir.exists())
			return false;
		
		String[] contents = dir.list();
		return (null != contents && contents.length > 0);
	}


	/**
	 * Create a filter given an identifier for the file extension.
	 * <P>
	 * This method allows an application to create a {@link FileNameExtensionFilter} for
	 * files that are loaded and stored by the application.
	 * </P>
	 * <P>
	 * The default implementation for this method returns null, signaling that no file 
	 * name extension filter should be used.
	 * </P>
	 * <P>
	 * For example, if the files that the app normally stores its data in have the
	 * extension "ccd", then One possible version of this method is:
	 * </P>
	 * <P>
	 * <CODE>
	 * <PRE>
	 * protected FileNameExtensionFilter createFileNameExtensionFilter(String s)
	 * {
	 *     FileNameExtensionFilter filter = null;
	 *     filter = super.createFileNameExtensionFilter(s);
	 *     if (null != filter)
	 *         return filter;
	 * 
	 *     if ("ccd" == s)
	 *     {
	 * 	       String desc = "CalorieCount Data Files (.ccd)";
	 *         String ext = "ccd";
	 *         filter = new FileNameExtensionFilter (desc, ext);
	 *     }
	 * 
	 *     return filter;
	 * }
	 * </PRE>
	 * </CODE>
	 * 
	 * @param s The file name extension.
	 * @return The file the user selected.
	 */
	public FileNameExtensionFilter createFileNameExtensionFilter(String s)
	{
		return null;
	}


	/***
	 * Open a file browser and obtain a file that represents where the user wants to load
	 * or store something.
	 * <P>
	 * The reason for using this method is that the application will "remember" where the
	 * last directory was by setting the {@link ApplicationProperties#PROP_LAST_DIRECTORY}
	 * , and {@link ApplicationProperties#PROP_LAST_FILE} properties. The next time this
	 * method is called it will start in that directory and with that file unless
	 * instructed otherwise.
	 * </P>
	 * <P>
	 * 
	 * </P>
	 * 
	 * @see {@link JFileChooser}, {@link JFileChooser#setFileSelectionMode(int)}.
	 * 
	 * @param comp
	 *        The "owning" component.
	 * @param openFile
	 *        true if the dialog should use the
	 *        {@link JFileChooser#showOpenDialog(Component)}, otherwise
	 *        {@link JFileChooser#showSaveDialog(Component)} will be used.
	 * @param mode
	 *        This is passed on to the JFileChooser via
	 *        {@link JFileChooser#setFileSelectionMode(int)}. See the JavaDoc on that
	 *        method for details.
	 * @param startingDirectory
	 *        The directory that the dialog should start in. This is only used if the
	 *        startingFile parameter is not set.
	 * @param startingFile
	 *        The file that will be selected when the dialog comes up. If this parameter
	 *        is null, then no file is selected.
	 * @param extension
	 *        The file extension that the resulting file will have, if it has no
	 *        extension.
	 * @return The file the user selected or null if the user canceled.
	 */
	public File browseFiles(Component comp, boolean openFile, int mode, File startingDirectory,
			File startingFile, String extension)
	{
		File f = null;
		int result;
		
		do {
			JFileChooser jfc = getFileChooser();
			jfc.setFileSelectionMode(mode);
			
			if (null != startingDirectory)
				jfc.setCurrentDirectory(startingDirectory);
			
			if (null != startingFile)
				jfc.setSelectedFile(startingFile);
			
			extension = StringUtils.trim(extension);
			if (null != extension)
			{
				FileNameExtensionFilter filter;
				filter = createFileNameExtensionFilter(extension);
				jfc.setFileFilter(filter);
			}
			
			if (!openFile)
			{
				FileNameExtensionFilter filter;
				filter = new FileNameExtensionFilter("CalorieCount data files", "ccd");
				jfc.setFileFilter(filter);
			}
			result = JFileChooser.CANCEL_OPTION;
			
			if (openFile)
				result = jfc.showOpenDialog(comp);
			else 
				result = jfc.showSaveDialog(comp);
			
			if (JFileChooser.CANCEL_OPTION == result)
				break;
			
			if (JFileChooser.APPROVE_OPTION == result)
			{
				f = jfc.getSelectedFile();
				if (f.exists() && !openFile)
				{
					String msg =
						"The file already exists, overwrite it?";
					
					int ans = JOptionPane.showConfirmDialog(null, msg);
					if (JOptionPane.YES_OPTION != ans)
					{
						result = JFileChooser.CANCEL_OPTION;
						continue;
					}
				}
				setLastDirectory(f.getParentFile());
				setLastFile(f);
			}
		} while (result != JFileChooser.APPROVE_OPTION);
		
		if (null != f && null != extension)
		{
			if (!f.toString().endsWith(extension))
			{
				String s = f.toString();
				s = s + "." + extension;
				f = new File(s);
			}			
		}
		
		return f;
	}
	
	
	public File browseFiles(Component comp, boolean openFile, int mode, File startingDirectory,
			File startingFile)
	{
		return browseFiles(comp, openFile, mode, startingDirectory, startingFile, null);
	}
}
