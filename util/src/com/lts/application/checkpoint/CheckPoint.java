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
package com.lts.application.checkpoint;

import java.io.File;
import java.text.SimpleDateFormat;

public class CheckPoint
{
	private File myFile;
	
	public File getFile()
	{
		return myFile;
	}
	
	
	public CheckPoint(File file)
	{
		initialize(file);
	}
	
	
	protected void initialize(File file)
	{
		if (!file.exists())
		{
			String msg = "The file, " + file + ", does not exist";
			throw new IllegalArgumentException(msg);
		}
		
		myFile = file;
	}
	
	
	final static private SimpleDateFormat ourFormat =
		new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss");
	
	
	public String toString()
	{
		return ourFormat.format(myFile.lastModified());
	}
}