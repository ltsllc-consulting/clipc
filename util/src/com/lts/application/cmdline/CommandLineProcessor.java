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
package com.lts.application.cmdline;

import java.util.Properties;

import com.lts.application.ApplicationException;

/**
 * A class that processes the command line to an application.
 * 
 * @author cnh
 *
 */
public interface CommandLineProcessor
{
	/**
	 * Process the command line and return the interpreted values as properties.
	 * 
	 * <P>
	 * The application framework is designed with the idea that arguments to the 
	 * program can be represented as a properties object.  The properties set and 
	 * the values used should have more meaning than simply parroting the contents
	 * of the argv array.
	 * </P>
	 * 
	 * <P>
	 * For example, the program might take an argument like "-s" to indicate non-
	 * verbose mode.  In that situation, the resulting properties object might 
	 * contain something like key="verbosity", value="terse" or "silent=true".
	 * </P>
	 * 
	 * @param argv The array of string passed to the program on the command line.
	 * @return The settings that the input translates to.
	 */
	public Properties processArguments(String[] argv) throws ApplicationException;
}
