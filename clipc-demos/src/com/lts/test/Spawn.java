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
package com.lts.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * A JRE Launcher.
 * 
 * <P>
 * This class allows clients to easily control a group of Java processes.
 * </P>
 * 
 * 
 * @author cnh
 *
 */
public class Spawn
{
	private List<String> myClasspath = new ArrayList<String>();
	private String myCommand;
	private Throwable myException;
	private List<Process> myProcessList;
	private boolean myDebug;
	private List<String> myLibraryPath = new ArrayList<String>();
	private Writer myOutputWriter;
	private Writer myErrorWriter;
	
	public Writer getOutputWriter()
	{
		return myOutputWriter;
	}
	
	public void setOutputWriter(Writer writer)
	{
		myOutputWriter = writer;
	}
	
	public Writer getErrorWriter()
	{
		return myErrorWriter;
	}
	
	public void setErrorWriter(Writer writer)
	{
		myErrorWriter = writer;
	}
	
	
	public void setErrorWriter(PrintWriter writer)
	{
		myErrorWriter = writer;
	}
	
	public void setLibraryPath(List<String> list)
	{
		myLibraryPath = list;
	}
	
	public List<String> getLibraryPath()
	{
		return myLibraryPath;
	}
	
	
	public boolean isDebug()
	{
		return myDebug;
	}


	public void setDebug(boolean debug)
	{
		myDebug = debug;
	}


	public Spawn()
	{
		initialize();
	}
	
	
	public String buildPath(List<String> list)
	{
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String s : list)
		{
			if (first)
			{
				first = false;
			}
			else
			{
				sb.append(File.pathSeparator);
			}
			
			sb.append(s);
		}
		
		return sb.toString();
	}
	
	public String getClasspath()
	{
		return buildPath(myClasspath);
	}
	
	
	public String getLibraryPathString()
	{
		return buildPath(myLibraryPath);
	}
	
	
	public void addToClasspath(String s)
	{
		myClasspath.add(s);
	}
	
	public void setClasspath(List<String> list)
	{
		if (null == list)
			myClasspath = new ArrayList<String>();
		else
			myClasspath = list;
	}
	
	public String getCommand()
	{
		return myCommand;
	}
	
	public void setCommand(String command)
	{
		myCommand = command;
	}
	
	protected void initialize()
	{
		initializeClassPath();
		initializeLibraryPath();
		initializeCommand();
	}


	private void initializeCommand()
	{
		String s = System.getProperty("java.home");
		s = s + File.separator + "bin" + File.separator + "java";
		myCommand = s;
	}


	private List<String> stringToPath(String s)
	{
		String[] fields = s.split(File.pathSeparator);
		List<String> list = new ArrayList<String>(fields.length);
		for (String entry : fields)
		{
			entry = entry.trim();
			if (!entry.equals(""))
				list.add(entry);
		}
		
		return list;
	}
	
	private void initializeClassPath()
	{
		String s = System.getProperty("java.class.path");
		List<String> list = stringToPath(s);
		setClasspath(list);
	}
	
	
	private void initializeLibraryPath()
	{
		String s = System.getProperty("java.library.path");
		List<String> list = stringToPath(s);
		myLibraryPath = list;
	}
	
	public void updateLibraryPath()
	{
		String pathString = buildPath(myLibraryPath);
		System.setProperty("java.library.path", pathString);
	}
	
	public void launch(String[][] classes) throws SpawnException
	{
		List<Process> processList = new ArrayList<Process>();
		
		ProcessBuilder builder = new ProcessBuilder();
		for (String[] row : classes)
		{
			try
			{
				processList.add(createProcess(builder, row));
			}
			catch (IOException e)
			{
				setException(e);
				if (processList.size() < 1)
				{
					throw new SpawnException("Error spawning process", e);
				}
				else
				{
					break;
				}
			}
		}
		
		setProcesses(processList);
	}
	
	
	public void launch (int count, Class clazz, String arg1) throws SpawnException
	{
		String[] desc = buildDescription(clazz, arg1);
		launch(count, desc);
	}
	
	
	public String[] buildDescription (Class clazz)
	{
		String[] desc = { clazz.getName() };
		return desc;
	}
	
	public String[] buildDescription (Class clazz, String a1)
	{
		String[] desc = { clazz.getName(), a1};
		return desc;
	}
	
	public String[] buildDescription (Class clazz, String a1, String a2)
	{
		String[] desc = { clazz.getName(), a1, a2 };
		return desc;
	}
	
	
	public void launch (int count, Class clazz) throws SpawnException
	{
		List<Process> list = new ArrayList<Process>();
		ProcessBuilder builder = new ProcessBuilder();
		
		for (int i = 0; i < count; i++)
		{
			try
			{
				list.add(createProcess(builder, clazz));
			}
			catch (IOException e)
			{
				throw new SpawnException(e);
			}
		}
	}
	
	
	private Process createProcess (ProcessBuilder builder, String[] desc) throws IOException
	{
		buildCommandLine(builder, desc);
		setupLibraryPath(builder);
		
		Process p = builder.start();
		
		
		Spew spew = new Spew(p, getOutputWriter(), getErrorWriter());
		spew.launch();
		
		return p;
	}
	
	
	private Process createProcess(ProcessBuilder builder, Class clazz) throws IOException
	{
		String[] desc = {
				 clazz.getName()
		};
		return createProcess(builder, desc);
	}

	private void setupLibraryPath(ProcessBuilder builder)
	{
		builder.environment().put("PATH", getLibraryPathString());
		builder.environment().put("LD_LIBRARY_PATH", getLibraryPathString());
	}

	private void buildCommandLine(ProcessBuilder builder, String[] desc)
	{
		List<String> list = new ArrayList<String>();
		list.add(getCommand());
		list.add("-cp");
		list.add(getClasspath());
		
		for (String s : desc)
		{
			list.add(s);
		}
		
		builder.command(list);
		
		if (isDebug())
		{
			printDebugInfo(builder);
		}
	}

	private void printDebugInfo(ProcessBuilder builder)
	{
		System.out.print("Launching");
		for (String s : builder.command())
		{
			System.out.print(" ");
			System.out.print(s);
		}
		
		System.out.println();
	}


	public void setProcesses(List<Process> processList)
	{
		myProcessList = processList;
	}

	public List<Process> getProcesses()
	{
		return myProcessList;
	}

	private void setException(Throwable e)
	{
		myException = e;
	}
	
	public Throwable getException()
	{
		return myException;
	}


	public void terminate()
	{
		if (null == getProcesses())
			return;
		
		for (Process proc : getProcesses())
		{
			proc.destroy();
		}
	}
	
	
	public void join ()
	{
		for (Process proc : getProcesses())
		{
			joinProcess(proc);
		}
	}


	private void joinProcess(Process proc) 
	{
		try
		{
			InputStream istream = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(istream);
			BufferedReader in = new BufferedReader(isr);

			for (String s = in.readLine(); null != s; s = in.readLine())
			{
				System.out.println(s);
			}
			
			proc.waitFor();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void addToLibraryPath(String string)
	{
		myLibraryPath.add(string);
		
	}

	public void launch (int count, String[] desc) throws SpawnException
	{
		try
		{
			List<Process> list = new ArrayList<Process>();
			ProcessBuilder builder = new ProcessBuilder();
			
			for (int i = 0; i < count; i++)
			{
				list.add(createProcess(builder, desc));
			}
			
			setProcesses(list);
		}
		catch (IOException e)
		{
			throw new SpawnException(e);
		}
	}
}
