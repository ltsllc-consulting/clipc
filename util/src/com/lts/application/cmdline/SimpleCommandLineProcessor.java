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
 * A command line processor that assumes arguments take a key,value form.
 * 
 * <P>
 * "Key,value" means that the class assumes that the first argument is a key for
 * a property and the value after it is a value.
 * </P>
 * 
 * <P>
 * For example:
 * <CODE>
 * <PRE>
 * 		&lt;program&gt; foo bar
 * </PRE>
 * </CODE>
 * 
 * Would result in a properties object where Properties.setProperty("foo", "bar")
 * had been called.
 * </P>
 * 
 * <P>
 * Arguments are processed via the processArgument method.  That method should 
 * change the state of the instance to reflect the argument processed.
 * </P>
 * 
 * <P>
 * The class defines the following fields:
 * <UL>
 * <LI>myOriginalArguments - the original arguments to the class.</LI>
 * <LI>
 * 		myIndex - the index within myOriginalArguments of the argument currently
 * 		being processed.
 * </LI>
 * 
 * <LI>myCurrentArgument - the argument currently being processed</LI>
 * <LI>myProperties - the properties to be returned.</LI>
 * </UL>
 * 
 * @author cnh
 *
 */
public class SimpleCommandLineProcessor implements CommandLineProcessor
{
	protected int myIndex;
	protected String myCurrentArgument;
	protected String[] myOriginalArguments;
	protected Properties myProperties;
	
	@Override
	public Properties processArguments(String[] argv) throws ApplicationException
	{
		myProperties = new Properties();
		myOriginalArguments = argv;
		
		myIndex = 0;
		while (myIndex < argv.length)
		{
			int old = myIndex;
			myCurrentArgument = argv[myIndex];
			processArgument();
			//
			// forces processing onto the next parameter
			//
			if (old >= myIndex)
				myIndex++;
		}
		
		return myProperties;
	}

	/**
	 * Process the current argument.
	 * 
	 * <P>
	 * This implementation tries to create a property with the name of the current
	 * argument, and the value of the argument after the current one.  myIndex is 
	 * updated to consume the value.
	 * </P>
	 * 
	 * <P>
	 * If the command line does not contain a value, then the argument is discarded,
	 * that is, a command line of the form:
	 * <BR/>
	 * <CODE>
	 * <PRE>
	 * 		&lt;prog&gt; foo
	 * </PRE>
	 * </CODE>
	 * </P>
	 * 
	 * <P>
	 * Would not result in anything being set.
	 * </P>
	 */
	protected void processArgument()
	{
		String key = myCurrentArgument;
		myIndex++;
		if (myIndex < myOriginalArguments.length)
		{
			String value = myOriginalArguments[myIndex];
			myProperties.setProperty(key, value);
		}
	}

}
