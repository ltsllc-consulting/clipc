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
package com.lts.application.data.plugable;

import java.io.InputStream;
import java.io.OutputStream;

import com.lts.application.ApplicationException;
import com.lts.application.ApplicationRepository;

/**
 * This interface represents the approach taken to store myData in an 
 * ApplicationRepository.
 * 
 * <P>
 * Example methods: XML, binary, etc.
 * </P>
 * 
 * <P>
 * The purpose of the interface is to separate how the myData is stored in terms 
 * of the form of the myData file from the nature that the repository takes.
 * For example, a directory that contains the files as opposed to a zip file. 
 * </P>
 * 
 * @author cnh
 *
 */
public interface RepositoryStorageMethod
{
	public void writeData (ApplicationRepository repository, Object data, String entry) 
		throws ApplicationException;
	
	public Object readData (ApplicationRepository repository, String entry)
		throws ApplicationException;
	
	public Object read(InputStream istream) throws ApplicationException;
	public void write (OutputStream ostream, Object data) throws ApplicationException;
}	
