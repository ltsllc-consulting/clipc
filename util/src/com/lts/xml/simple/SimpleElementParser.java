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
package com.lts.xml.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.lts.util.StringUtils;

public class SimpleElementParser
{
	private SimpleElement myRoot;
	
	public void parse(Document doc)
	{
		Element root = doc.getDocumentElement();
		myRoot = new SimpleElement("<root>");
		addChildren(myRoot, root.getChildNodes());
	}

	public SimpleElement getRoot()
	{
		return myRoot;
	}
	
	
	protected void addChildren(SimpleElement parent, NodeList children)
	{
		processValue(parent, children);
		processChildElements(parent, children);
	}

	protected void processChildElements(SimpleElement parent, NodeList children)
	{
		List<SimpleElement> childElements = new ArrayList<SimpleElement>();
		int count = children.getLength();
		for (int index = 0; index < count; index++)
		{
			Node child = children.item(index);
			if (child.getNodeType() == Node.ELEMENT_NODE)
			{
				Element el = (Element) child;
				childElements.add(buildElement(el));
			}
		}
		parent.setChildren(childElements);
	}

	protected SimpleElement buildElement(Element el)
	{
		SimpleElement obj = new SimpleElement();
		
		obj.setName(el.getNodeName());
		obj.setAttributes(buildAttributes(el.getAttributes()));
		processChildElements(obj, el.getChildNodes());
		processValue(obj, el.getChildNodes());
		
		return obj;
	}

	protected Map<String, String> buildAttributes(NamedNodeMap attributes)
	{
		Map<String, String> attrs = new HashMap<String, String>();
		
		int count = attributes.getLength();
		for (int index = 0; index < count; index++)
		{
			Node node = attributes.item(index);
			attrs.put(node.getNodeName(), node.getNodeValue());
		}
		
		return attrs;
	}

	protected void processValue(SimpleElement parent, NodeList children)
	{
		int count = children.getLength();
		
		StringBuffer value = new StringBuffer();
		for (int index = 0; index < count; index++)
		{
			Node childNode = children.item(index);
			if (childNode instanceof Text)
			{
				Text text = (Text) childNode;
				String s = StringUtils.trim(text.getNodeValue());
				if (null != s && s != "")
				{
					value.append(s);
				}
			}
		}
		
		String s = value.toString().trim();
		if (s.equals(""))
		{
			parent.setValue(null);
		}
		else
		{
			parent.setValue(s);
		}
	}
}
