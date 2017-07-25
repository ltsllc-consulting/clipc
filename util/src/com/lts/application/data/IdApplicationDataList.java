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
package com.lts.application.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lts.application.ApplicationException;
import com.lts.datalist.DataList;
import com.lts.util.notifyinglist.NotifyingListHelper;
import com.lts.xml.simple.SimpleElement;


abstract public class IdApplicationDataList<E extends IdApplicationDataElement> 
	extends DataList<E>
	implements SimpleSerialization
{
	private static final long serialVersionUID = 1L;
	
	abstract protected String getSerializationElementName();
	abstract protected E createListElement(String name);
	
	transient protected int myNextId;
	
	/**
	 * A map from meal ID to the value that can be used with methods like {@link #get(int)}.
	 * That is, the ID to the position in the list.
	 */
	transient protected Map<Integer, Integer> myIdToListPosition;
	transient protected Map<Integer, E> myIdToElement;
	transient protected IdListListenerHelper myIdHelper;
	
	public E idToElement(int id)
	{
		return myIdToElement.get(id);
	}
	
	

	
	@Override
	public void postDeserialize() throws ApplicationException
	{
		super.postDeserialize();
		renumber();
		rebuildIdToListPosition();
		rebuildIdToElement();
	}
	
	public IdApplicationDataList()
	{
		initialize();
	}
	
	
	public void initialize()
	{
		super.initialize();
		myIdToListPosition = new HashMap<Integer, Integer>();
		myIdToElement = new HashMap<Integer, E>();
		myHelper = new NotifyingListHelper();
		myIdHelper = new IdListListenerHelper();
		myNextId = 0;
	}
	
	protected void initialize(List<E> list)
	{
		super.initialize(list);
		rebuildIdToElement();
		rebuildIdToListPosition();
	}
	
	
	/**
	 * Rebuild transient data members.
	 * 
	 * <P>
	 * This method should be called prior to using an instance of the class after
	 * deserializing.  The only reason it is not in the {@link #postDeserialize()}
	 * method is that it avoids introducing a direct dependency between this class
	 * and {@link CalorieCountData}. 
	 * </P>
	 * 
	 * @param list The list of foods to use when rebuilding.
	 */
	protected void rebuildIdToListPosition()
	{
		myIdToListPosition = new HashMap<Integer, Integer>();
		
		for (int i = 0; i < myList.size(); i++)
		{
			IdApplicationDataElement element = myList.get(i);
			myIdToListPosition.put(element.getId(), i);
		}
	}
	
	
	protected void rebuildIdToElement()
	{
		myIdToElement = new HashMap<Integer, E>();
		for (E element : this)
		{
			myIdToElement.put(element.getId(), element);
		}
	}
	

	protected void renumber() 
	{
		myNextId = 0;
		for (IdApplicationDataElement element : myList)
		{
			if (null != element)
			{
				element.setId(myNextId);
				myNextId++;
			}
		}
	}


	@Override
	public void deserializeFrom(SimpleElement elem)
	{
		myIdToListPosition = new HashMap<Integer, Integer>();
		List<E> list = new ArrayList<E>();
		
		for (SimpleElement child : elem.getChildren())
		{
			E childElement = deserializeChildElement(child);
			if (null != childElement)
				list.add(childElement);
		}
		
		replaceWith(list);
	}

	protected E deserializeChildElement(SimpleElement elem)
	{
		String name = elem.getName();
		E child = createListElement(name);
		
		if (null != child)
			child.deserializeFrom(elem);
		
		return child;
	}


	@Override
	public void serializeTo(SimpleElement elem)
	{
		for (IdApplicationDataElement element : this)
		{
			SimpleElement child = element.createSerializationElement();
			elem.addChild(child);
		}
	}
	
	public void add(int index, E element)
	{
		element.setId(myNextId);
		myNextId++;
		
		int theSize = size();
		myIdToElement.put(element.getId(), element);

		if (index == theSize)
		{
			myIdToListPosition.put(element.getId(), index);
		}
		else
		{
			rebuildIdToListPosition();
		}
		
		super.add(index, element);
		
		myIdHelper.fireAdd(element);
	}
	
	public boolean add(E element)
	{
		add(size(), element);
		return true;
	}

	public E remove (int index)
	{
		E element = super.remove(index);
		
		if (null != element)
		{
			myIdToListPosition.remove(element.getId());
			myIdToElement.remove(element.getId());
			myIdHelper.fireDelete(element);
		}
		
		return element;
	}

	public Integer idToIndex(int id)
	{
		return myIdToListPosition.get(id);
	}
	
	public boolean remove (Object o)
	{
		E element = (E) o;
		Integer index = idToIndex(element.getId());
		if (null == index)
		{
			return false;
		}
		else
		{
			return null != remove(index.intValue());
		}		
	}
	
	
	public boolean removeById (int id)
	{
		int index = idToIndex(id);
		return (remove(index) != null);
	}

	@Override
	public E set(int index, E element)
	{
		E old = super.set(index, element);
		
		myIdToListPosition.put(element.getId(), index);
		myIdToElement.put(element.getId(), element);
		
		myIdHelper.fireUpdate(element);
		return old;
	}

	@Override
	public SimpleElement createSerializationElement()
	{
		SimpleElement root = new SimpleElement(getSerializationElementName());
		serializeTo(root);
		return root;
	}


	public void replaceWith(List<E> list)
	{
		myList = Collections.synchronizedList(list);
		rebuildTransients();
	}
	
	synchronized protected void rebuildTransients()
	{
		rebuildIdToElement();
		rebuildIdToListPosition();
		rebuildNextId();
	}
	
	
	protected void rebuildNextId()
	{
		int max = 0;
		for (IdApplicationDataElement element : this)
		{
			if (element.getId() > max)
				max = element.getId();
		}
		
		myNextId = max;
	}
	
	
	public void update(E e)
	{
		super.update(e);
		myIdHelper.fireUpdate(e);
	}
	
	
	public boolean addIdListListener(IdListListener listener)
	{
		return myIdHelper.addListener(listener);
	}
	
	public boolean removeIdListListener(IdListListener listener)
	{
		return myIdHelper.removeListener(listener);
	}
}
