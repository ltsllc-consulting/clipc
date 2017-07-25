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
package com.lts.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.lts.util.system.SystemUtils.StandardProperties;

/**
 * Utility methods useful in dealing with files.
 */
public class FileUtils
{
    public static void copyFromToFile(File infile, File outfile)
			throws FileNotFoundException, IOException
	{
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try
		{
			fis = new FileInputStream(infile);
			fos = new FileOutputStream(outfile);
			int buffSize = 8192;
			byte[] buf = new byte[buffSize];
			int byteCount = fis.read(buf);
			while (byteCount > 0)
			{
				fos.write(buf, 0, byteCount);
				byteCount = fis.read(buf);
			}
		}
		finally
		{
			if (null != fos)
				fos.close();

			if (null != fis)
				fis.close();
		}
	}



	public static List<String> findDirectoriesContaining (String name, String[] path, boolean stopAtFirstMatch)
	{
		List<String> list = new ArrayList<String>();
		boolean found = false;
		for (String dir : path)
		{
			File component = new File(dir);
			if (component.isDirectory())
			{
				found = appendDirectoriesToSearch(list, name, component, stopAtFirstMatch);
				if (found && stopAtFirstMatch)
					break;
			}
		}
		
		return list;
	}
	
	public static List<String> findDirectoriesContaining (String name, String path)
	{
		String sep = StandardProperties.PathSeparator.getValue();
		String[] dirs = path.split(sep);
		return findDirectoriesContaining (name, dirs, true);
	}
	
	public static String findFirstDirectoryContaining (String name, String path)
	{
		String sep = StandardProperties.PathSeparator.getValue();
		String[] dirs = path.split(sep);

		List<String> list = findDirectoriesContaining(name, dirs, true);
		if (list.size() < 1)
			return null;
		else
			return list.get(0);
	}

	/**
	 * Search the directory passed for the specified file.
	 * <H3>NOTE</H3>
	 * This method assumes that the directory parameter returns true to
	 * {@link File#isDirectory()}. If the file parameter is not a directory, this method
	 * will probably throw an exception.
	 * <H3>Description</H3>
	 * This method will append the directory passed to the list parameter if the name
	 * specified in the name parameter exists in the directory. In addition, it will
	 * search any sub-directories for matches as well if the searchAll parameter is set to
	 * true.
	 * 
	 * @param list
	 *        The list of directory matches.
	 * @param name
	 *        The name of the file or directory to look for.
	 * @param dir
	 *        The directory to search. See notes above.
	 * @param stopAtFirstMatch
	 *        If set to true, then the search will stop when it finds the first matching
	 *        file or directory. If false, the search will find all matches in the
	 *        specified directory or sub-directories.
	 */
	protected static boolean appendDirectoriesToSearch(List<String> list, String name, File dir, boolean stopAtFirstMatch)
	{
		boolean found = false;
		
		File[] contents = dir.listFiles();
		for (File entry : contents)
		{
			if (entry.getName().equals(name))
			{
				list.add(dir.toString());
				found = true;
				if (stopAtFirstMatch)
					return true;
			}
			
			if (entry.isDirectory())
			{
				found = appendDirectoriesToSearch(list, name, entry, stopAtFirstMatch);
				if (found && stopAtFirstMatch)
					return true;
			}
		}
		
		return found;
	}

	public static String findFirstDirectoryContaining(String name, String[] path)
	{
		List<String> list = findDirectoriesContaining(name, path, true);
		if (list.size() < 1)
			return null;
		else
			return list.get(0);
	}
	
	
	public static String[] splitFileName(String name)
	{
		//
		// unfortunately, the windows separator character has significance in the 
		// String.split method.  Therefore, if we are on that platform, make sure to 
		// escape the meaning of the separator character
		//
		String pathRegex = File.separator;
		if (File.separatorChar == '\\')
		{
			pathRegex = "\\\\";
		}
		
		String[] fields = name.split(pathRegex);
		return fields;
	}



	/**
	 * Create a file if it does not already exist.
	 * <P>
	 * The IPC classes in this package all expect a file to exist that corresponds to the
	 * resource that they are manipulating. This method checks to see if the corresponding
	 * file does indeed exist on the underlying system. If it does not, then the method
	 * will try to create it.
	 * </P>
	 * 
	 * @param name
	 *        The absolute path to the file.
	 * @return true if the method creates the file, false otherwise.
	 * @throws IPCException
	 *         If the method tries to create the file but an exception is thrown. The
	 *         message will be set to {@link Errors#ExceptionCreatingFile} and the
	 *         cause will be set to the offending exception.
	 */
	public static boolean checkFile(File f) throws IOException
	{
		boolean createdFile = false;
	
		if (!f.exists())
		{
			//
			// According to the javadoc, if java.lang.File.createNewFile returns
			// normally, then the specified file now exists --- it was created by
			// the method or someone else created it just before we made the call.
			// The only other possibility is an exception.
			//
			f.createNewFile();
			createdFile = true;
		}
	
		return createdFile;
	}



	public static void checkFile(String name) throws IOException
	{
		File file = new File(name);
		checkFile(file);
	}



	public static String readFile(File file) throws IOException
	{
		FileReader reader = null;
	
		try
		{
			reader = new FileReader(file);
			StringWriter sw = new StringWriter(1024);
			for (int c = reader.read(); c != -1; c = reader.read())
			{
				sw.write(c);
			}
	
			return sw.toString();
		}
		catch (FileNotFoundException e)
		{
			return null;
		}
		finally
		{
			closeNoExceptions(reader);
		}
	}



	public static String readFile(String name) throws IOException
	{
		File file = new File(name);
		return readFile(file);
	}



	/**
	 * Read or create a file.
	 * <P>
	 * This method opens the named text file and returns its contents, assuming the file
	 * exists.
	 * </P>
	 * <P>
	 * If the file does not exist, then it creates it and writes the supplied string into
	 * it. This operation happens in an "atomic" fashion, so that another thread or
	 * process that if another process or thread attempts to write the same file at the
	 * same time, only one of them will succeed.
	 * </P>
	 * 
	 * @param file
	 *        The file to read or create.
	 * @param contents
	 *        If the file needs to be created, the contents of the file.
	 * @throws IOException
	 *         If a problem exists while trying to read or write the file.
	 * @return The contents of the file. If the file does not exist, this will simply
	 *         return the supplied string.
	 */
	public static String readOrCreate(File file, String contents) throws IOException
	{
		//
		// if the file exists, return its contents
		//
		if (file.exists())
		{
			return readFile(file);
		}
		//
		// otherwise, write the supplied string to the file using an "atomic"
		// operation
		//
		else
		{
			//
			// create a temp file and write the contents to it.
			//
			File parent = file.getParentFile();
			File temp = File.createTempFile("tempfile", null, parent);
			writeFile(temp, contents);
	
			//
			// rename the temp file to the desired name. If the destination does
			// not exist, this should succeed.
			//
			if (temp.renameTo(file))
			{
				return contents;
			}
			//
			// otherwise, someone created the file before we did. Return its
			// contents
			//
			else
			{
				return readFile(file);
			}
		}
	}



	public static String readOrCreate(String name, String contents) throws IOException
	{
		File file = new File(name);
		return readOrCreate(file, contents);
	}



	public static void writeFile(File file, String contents) throws IOException
	{
		FileWriter writer = null;
	
		try
		{
			writer = new FileWriter(file);
			writer.write(contents);
		}
		finally
		{
			closeNoExceptions(writer);
		}
	}



	public static void writeFile(String name, String contents) throws IOException
	{
		File file = new File(name);
		writeFile(file, contents);
	}



	public static void closeNoExceptions(BufferedReader reader)
	{
		if (null == reader)
			return;
	
		try
		{
			reader.close();
		}
		catch (IOException e)
		{
			; // this method is supposed to ignore exceptions
		}
	}



	public static void closeNoExceptions(FileWriter writer)
	{
		if (null == writer)
			return;
	
		try
		{
			writer.close();
		}
		catch (IOException e)
		{
			; // the point of this method is to avoid throwing exceptions
		}
	}



	public static void closeNoExceptions(InputStream istream)
	{
		if (null == istream)
			return;
	
		try
		{
			istream.close();
		}
		catch (IOException e)
		{
			; // this method is supposed to ignore exceptions
		}
	}



	public static void closeNoExceptions(OutputStream ostream)
	{
		try
		{
			ostream.close();
		}
		catch (IOException e)
		{
			;
		}
	}



	public static void closeNoExceptions(Reader reader)
	{
		if (null == reader)
			return;
	
		try
		{
			reader.close();
		}
		catch (IOException e)
		{
			; // ignore exceptions
		}
	}
	

}
