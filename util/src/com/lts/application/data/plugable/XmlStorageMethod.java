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
import com.lts.xmlser.XmlObjectInputStream;
import com.lts.xmlser.XmlObjectOutputStream;

public class XmlStorageMethod implements RepositoryStorageMethod
{

	public Object readData(ApplicationRepository repository, String entry) throws ApplicationException
	{
		return loadXml(repository, entry);
	}

	public void writeData(ApplicationRepository repository, Object data, String entry)
			throws ApplicationException
	{
		storeAsXml(repository, entry, data);
	}

	public XmlObjectOutputStream getXmlOutputStream(ApplicationRepository repository,
			String entry, boolean append) throws ApplicationException
	{
		OutputStream ostream = repository.getOutputStream(entry, append);
		XmlObjectOutputStream xstream = new XmlObjectOutputStream(ostream);
		return xstream;
	}


	public void storeAsXml(ApplicationRepository repository, String entry, Object o)
			throws ApplicationException
	{
		XmlObjectOutputStream xoos = null;
		
		try
		{
			xoos = getXmlOutputStream(repository, entry, false);
			xoos.write(o);
		}
		catch (ApplicationException e)
		{
			throw e;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String msg = "Error trying to store entry " + entry;
			throw new ApplicationException(msg,e);
		}
		finally
		{
			if (null != xoos)
				xoos.close();
		}
	}
	
	public Object loadXml (ApplicationRepository repository, String entry) throws ApplicationException
	{
		XmlObjectInputStream xois = null;
		try
		{
			InputStream istream = repository.getInputStream(entry);
			if (null == istream)
				return null;
			
			xois = new XmlObjectInputStream(istream);
			if (null == xois)
				return null;
			else
				return xois.readObject();
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (ApplicationException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String msg = "Error trying to load entry " + entry;
			throw new ApplicationException(e,msg);
		}
		finally
		{
			if (null != xois)
				xois.close();
		}
	}

	@Override
	public Object read(InputStream istream) throws ApplicationException
	{
		XmlObjectInputStream xois = null;
		
		try
		{
			xois = new XmlObjectInputStream(istream);
			return xois.readObject();
		}
		catch (ApplicationException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String msg = "Error trying to read data";
			throw new ApplicationException(e, msg);
		}
		finally
		{
			if (null != xois)
				xois.close();
		}
	}

	@Override
	public void write(OutputStream ostream, Object obj)
			throws ApplicationException
	{
		XmlObjectOutputStream xoos = null;
		
		try
		{
			xoos = new XmlObjectOutputStream(ostream);
			xoos.writeObject(obj);
		}
		catch (ApplicationException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			String msg = "Error writing object.";
			throw new ApplicationException(msg,e);
		}
		finally
		{
			if (null != xoos)
				xoos.close();
		}
	}
}
