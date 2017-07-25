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

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import com.lts.swing.SimpleListModel;

/**
 * Make a SimpleListModel look like a BufferedOutputStream.
 * <P>
 * The class operates by buffering output until it sees a newline.  When it encounters one, 
 * it creates a new item for the list and adds it.
 * </P>
 * 
 * @author cnh
 *
 */
public class StreamToListModel extends Writer
{
	private StringWriter myBuffer;
	private SimpleListModel myModel;
	
	public StreamToListModel(SimpleListModel model)
	{
		initialize(model);
	}

	protected void initialize(SimpleListModel model)
	{
		myBuffer = new StringWriter();
		myModel = model;
	}

	protected void basicWrite(int c) throws IOException
	{
		if ('\n' == c)
		{
			flush();
		}
		else
		{
			myBuffer.write(c);
		}
	}

	@Override
	public void close() throws IOException
	{
		flush();
	}

	@Override
	public void flush() throws IOException
	{
		String line = myBuffer.toString();
		myBuffer = new StringWriter();
		myModel.add(line);
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException
	{
		for (int i = 0; i < len; i++)
		{
			basicWrite(cbuf[off+i]);
		}
	}
}
