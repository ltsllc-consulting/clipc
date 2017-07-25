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

import com.lts.application.ApplicationRepository;

abstract public class PluggableApplicationRepository implements ApplicationRepository
{
//	abstract protected MultiElementAppData createApplicationData();
//
//	private ApplicationRepository myRepository;
//	private RepositoryStorageMethod myStorageMethod;
//	private MultiElementAppData myApplicationData;
//	
//	
//	public RepositoryStorageMethod getStorageMethod()
//	{
//		return myStorageMethod;
//	}
//	
//	public void setStorageMethod(RepositoryStorageMethod method)
//	{
//		myStorageMethod = method;
//	}
//	
//	public ApplicationRepository getRepository()
//	{
//		return myRepository;
//	}
//	
//	public void setRepository(ApplicationRepository repository)
//	{
//		myRepository = repository;
//	}
//	
//	
//	public void setApplicationData(MultiElementAppData data)
//	{
//		myApplicationData = data;
//	}
//	
//	
//	public void close() throws ApplicationException
//	{
//		myRepository.close();
//	}
//
//	public void commit() throws ApplicationException
//	{
//		myRepository.commit();
//	}
//
//	public void commitAs(File outfile) throws ApplicationException
//	{
//		myRepository.commitAs(outfile);
//
//	}
//
//	public void delete() throws ApplicationException
//	{
//		myRepository.delete();
//	}
//
//	public ApplicationData getApplicationData() throws ApplicationException
//	{
//		if (null == myApplicationData)
//		{
//			myApplicationData = createApplicationData();
//			loadApplicationData(myApplicationData);
//		}
//		
//		return myApplicationData;
//	}
//
//	public InputStream getInputStream(String entry) throws ApplicationException
//	{
//		return myRepository.getInputStream(entry);
//	}
//
//	public OutputStream getOutputStream(String entry, boolean append)
//			throws ApplicationException
//	{
//		return myRepository.getOutputStream(entry, append);
//	}
//
//	public File getRepositoryFile() throws ApplicationException
//	{
//		return myRepository.getRepositoryFile();
//	}
//
//	public boolean removeEntry(String entry) throws ApplicationException
//	{
//		return myRepository.removeEntry(entry);
//	}
//
//	public void rollback() throws ApplicationException
//	{
//		myRepository.rollback();
//	}
//
//	public void storeApplicationData(MultiElementAppData data) throws ApplicationException
//	{
//		for (Enum e : )
//		{
//			ApplicationDataElement pade = data.get(e);
//			myStorageMethod.writeData(myRepository, pade, e.toString());
//		}
//	}
//
//	/**
//	 * Populate the entries in an instance of MultiElementAppData with data obtained 
//	 * from the repository.
//	 * 
//	 * TODO: This method should really just populate the instance of appdata instead
//	 * of using this weird scheme.  In the interest of getting something working, 
//	 * I have ignored the issue.
//	 * 
//	 * @param appdata
//	 * @throws ApplicationException
//	 */
//	public void loadApplicationData (MultiElementAppData appdata) throws ApplicationException
//	{
//		for (Enum e : appdata.getDataElements())
//		{
//			ApplicationDataElement element;
//			element = (ApplicationDataElement) myStorageMethod.readData(myRepository, e.toString());
//			if (null != element)
//				appdata.setEntry(e, element);
//		}
//	}
//	
//	
//	public Object read(String entry) throws ApplicationException
//	{
//		InputStream istream = null;
//	
//		try
//		{
//			istream = myRepository.getInputStream(entry);
//			return myStorageMethod.read(istream);
//		}
//		finally
//		{
//			IOUtilities.close(istream);
//		}
//	}
//	
//	public void write (String entry, Object data) throws ApplicationException
//	{
//		OutputStream ostream = null;
//		
//		try
//		{
//			ostream = myRepository.getOutputStream(entry, false);
//			myStorageMethod.write(ostream, data);
//		}
//		finally
//		{
//			IOUtilities.close(ostream);
//		}
//	}
//	
//
//	public void storeApplicationData (ApplicationData data) throws ApplicationException
//	{
//		MultiElementAppData mead = (MultiElementAppData) data;
//		storeApplicationData(mead);
//	}
//
//	@Override
//	public List<String> listEntries(String name) throws ApplicationException
//	{
//		return myRepository.listEntries(name);
//	}
}
