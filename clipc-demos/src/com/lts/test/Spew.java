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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * Stream the output of a program to designated places.
 * 
 * @author cnh
 *
 */
public class Spew
{
	private Process myProcess;
	private SpewThread myStandardOutThread;
	private SpewThread myStandardErrorThread;
	
	private class SpewThread implements Runnable
	{
		public Reader in;
		public Writer out;
		public Writer err;
		public Thread thread;
		
		public SpewThread(Reader input, Writer stdout, Writer stderr)
		{
			in = input;
			out = stdout;
			err = stderr;
			thread = new Thread("spew");
		}
		
		public void run()
		{
			try
			{
				basicRun();
			}
			catch (IOException e)
			{
				PrintWriter temp = new PrintWriter(err);
				e.printStackTrace(temp);
			}
		}

		private void basicRun() throws IOException
		{
			for (int c = in.read(); -1 != c; c = in.read())
			{
				char cval = (char) c;
				out.write(cval);
			}
		}
		
		public void launch()
		{
			thread.start();
		}
	}
	
	public Spew(Process process, PrintWriter out, PrintWriter error)
	{
		initialize(process, out, error);
	}
	
	public Spew (Process process)
	{
		initialize(process, null, null);
	}

	
	public Spew (Process process, Writer out, Writer err)
	{
		initialize(process, out, err);
	}
	
	
	public Spew (Process process, PrintWriter out)
	{
		initialize(process, out, out);
	}

	
	protected void initialize(Process p)
	{
		PrintWriter out = new PrintWriter(System.out);
		PrintWriter error = new PrintWriter(System.err);
		initialize(p, out, error);
	}

	protected void initialize(Process process, Writer out, Writer error)
	{
		InputStreamReader stdout = new InputStreamReader(process.getInputStream());
		InputStreamReader stderr = new InputStreamReader(process.getErrorStream());
		
		myProcess = process;
		myStandardErrorThread = new SpewThread(stderr, out, error);
		myStandardOutThread = new SpewThread(stdout, out, error);
	}
	
	public Process getProcess()
	{
		return myProcess;
	}
	
	public void launch ()
	{
		myStandardOutThread.launch();
		myStandardErrorThread.launch();
	}
}
