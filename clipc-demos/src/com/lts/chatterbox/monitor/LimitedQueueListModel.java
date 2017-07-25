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
package com.lts.chatterbox.monitor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 * A list model that limits the number of elements it can contain.
 * 
 * <P>
 * The model maintains a queue like data structure.  The oldest entries will
 * get dropped from the list as new ones are added, once the list fills up.
 * </P>
 * 
 * @author cnh
 */
public class LimitedQueueListModel implements ListModel
{
	private List myElements = new ArrayList<Object>();
	private ListDataHelper myHelper = new ListDataHelper();
	private int myLimit = -1;

	public int getLimit()
	{
		return myLimit;
	}

	synchronized public void setLimit(int limit)
	{
		if (limit < 0)
		{
			myLimit = -1;
			return;
		}
		else if (limit == 0)
		{
			return;
		}
		
		
		myLimit = limit;
		
		trim();
	}
	
	
	synchronized public void trim()
	{
		if (0 > getLimit())
			return;
		
		int listSize = myElements.size();
		while (listSize > getLimit())
		{
			remove(listSize - 1);
		}
	}

	public void remove(int index)
	{
		int last = myElements.size() - 1;
		myElements.remove(index);
		
		myHelper.fireRemoved(this, last);
		
		if (index < last)
		{
			myHelper.fireChanged(this, index, myElements.size() - 1);
		}
	}
	
	
	public void add(Object newElement)
	{
		myElements.add(0, newElement);
		if (myElements.size() > getLimit())
		{
			myElements.remove(myElements.size() - 1);
		}
		
		myHelper.fireAllChanged(this, myElements.size());
	}

	@Override
	public void addListDataListener(ListDataListener l)
	{
		myHelper.addListener(l);
	}

	@Override
	synchronized public Object getElementAt(int index)
	{
		return myElements.get(index);
	}

	@Override
	public int getSize()
	{
		return myElements.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l)
	{
		myHelper.removeListener(l);
	}
}
