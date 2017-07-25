//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the util library.
//
//  The util library is free software; you can redistribute it and/or modify it
//  under the terms of the Lesser GNU General Public License as published by
//  the Free Software Foundation; either version 2.1 of the License, or (at
//  your option) any later version.
//
//  The util library is distributed in the hope that it will be useful, but
//  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
//  or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
//  License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the util library; if not, write to the Free Software Foundation,
//  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
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
package com.lts.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author cnh
 */
public class IOUtilities 
{
	public static void closeNoExceptions (OutputStream ostream)
	{
		if (null == ostream)
			return;
			
		try
		{
			ostream.close();
		}
		catch (IOException e)
		{}
	}
	
	
	public static void closeNoExceptions (InputStream istream)
	{
		if (null == istream)
			return;
		
		try
		{
			istream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void close (InputStream istream)
	{
		closeNoExceptions(istream);
	}
	
	public static void close (Reader reader)
	{
		closeNoExceptions(reader);
	}
	
	public static void close (OutputStream ostream)
	{
		closeNoExceptions(ostream);
	}
	
	public static void close (Writer writer)
	{
		closeNoExceptions(writer);
	}
	
	public static void closeNoExceptions (Reader in)
	{
		if (null == in)
			return;
		
		try
		{
			in.close();
		}
		catch (IOException e)
		{}
	}
	
	
	public static void closeNoExceptions (Writer out)
	{
		if (null == out)
			return;

		try
		{
			out.close();
		}
		catch (IOException e)
		{}
	}
	
	
	
	public static void closeNoExceptions (BufferedReader in)
	{
		if (null == in)
			return;
		
		try
		{
			in.close();
		}
		catch (IOException e)
		{}
	}
	
	
	public static File createTempDirectory (File parentDir, String prefix, String suffix)
		throws IOException
	{
		File f;
		if (null == parentDir)
			f = File.createTempFile(prefix, suffix);
		else
			f = File.createTempFile(prefix, suffix, parentDir);
		
		if (f.exists() && !f.delete())
		{
			throw new IOException (
				"Error trying to delete file " + f 
				+ " so as to make room for a temp directory."
			);
		}
		
		if (!f.mkdirs())
		{
			throw new IOException (
				"Error creating temp directory " + f
			);
		}
		
		return f;
	}
	
	
	public static File createTempDirectory (String prefix) throws IOException
	{
		return createTempDirectory(null, prefix, "");
	}
	
	public static File createTempDirectory (File parentDir, String prefix)
		throws IOException 
	{
		return createTempDirectory(parentDir, prefix, "");
	}
	
	
	public static long copyFile (File input, File output) throws IOException
	{
		long bytesCopied = 0;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		byte[] buffer = new byte[8192];
		
		try
		{
			fis = new FileInputStream(input);
			fos = new FileOutputStream(output);
			
			int bytesRead = fis.read(buffer);
			while (bytesRead > 0)
			{
				fos.write(buffer, 0, bytesRead);
				bytesCopied += bytesRead;
				bytesRead = fis.read(buffer);
			}
		}
		finally
		{
			close(fis);
			close(fos);
		}
		
		return bytesCopied;
	}
	
	
	public static ImprovedFile toImprovedFile (File f)
	{
		if (f instanceof ImprovedFile)
			return (ImprovedFile) f;
		else
			return new ImprovedFile(f);
	}
	
	
	public static File backup (File file) throws IOException
	{
		if (null == file || !file.exists())
			return null;
		
		String fname = file.toString();
		int index = fname.lastIndexOf('.');
		if (-1 != index)
			fname = fname.substring(0,index);
		
		fname = fname + ".bak";
		File backupFile = new File(fname);
		copyFile(file, backupFile);
		
		return backupFile;
	}
	
	
	public static void copyStream (InputStream istream, PrintStream out) throws IOException
	{
		InputStreamReader isr = new InputStreamReader(istream);
		BufferedReader in = new BufferedReader(isr);
		
		for (String s = in.readLine(); null != s; s = in.readLine())
		{
			out.println(s);
		}		
	}


	/**
	 * Attempt to reset a reader assuming that mark was previously called; throw 
	 * a RuntimeException if you fail.
	 * 
	 * @param breader The reader to call reset against.
	 * @throws RuntimeException If an exception occurs when reset is called.
	 */
	public static void reset(Reader reader)
	{
		try
		{
			reader.reset();
		}
		catch (IOException e)
		{
			String msg = 
				"Exception while trying to reset stream back to marked position.";
			throw new RuntimeException(msg,e);
		}
	}
	
	
	public static String readContents(Reader reader) throws IOException
	{
		char[] buffer = new char[1024];
		StringWriter writer = new StringWriter();
		
		int count = 0;
		do {
			count = reader.read(buffer);
			writer.write(buffer, 0, count);
		} while (count > 0);
		
		reader.close();
		writer.close();
		
		return writer.toString();
	}
	
	
	public static String readContents(String name) throws IOException
	{
		FileReader reader = null;
		
		try
		{
			reader = new FileReader(name);
			return readContents(reader);
		}
		finally
		{
			close(reader);
		}
	}


	public static void writeContents(String name, String contents) throws IOException
	{
		FileWriter writer = null;
		
		try
		{
			writer = new FileWriter(name);
			writeContents(writer, contents);
		}
		finally
		{
			close(writer);
		}
	}


	public static void writeContents(FileWriter writer, String contents) throws IOException
	{
		writer.write(contents);
	}
}
