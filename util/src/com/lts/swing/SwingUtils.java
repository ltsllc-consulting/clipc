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
//  Copyright 2006, Clark N. Hobbie
//
//  This file is part of the util library.
//
//  The util library is free software; you can redistribute it and/or modify it
//  under the terms of the Lesser GNU General Public License as published by
//  the Free Software Foundation; either version 2.1 of the License, or (at
//  your option) any later version.
//
//  The util library is distributed in the hope that it will be useful, but
//  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
//  or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
//  License for more details.
//
//  You should have received a copy of the Lesser GNU General Public License
//  along with the util library; if not, write to the Free Software Foundation,
//  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
//
package com.lts.swing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.UIManager;

import com.lts.application.Application;
import com.lts.application.ApplicationException;
import com.lts.io.IOUtilities;
import com.lts.swing.keyboard.InputKey;
import com.lts.util.StringUtils;

public class SwingUtils
{
	public enum CommonKeys
	{
		Enter,
		ControlG,
		ControlQ,
		ControlX,
		ControlP,
		ControlC,
		ControlV,
		ControlW;
	}
	public static List toModelData (JList list)
	{
		List dataList = new ArrayList();
		
		ListModel model = list.getModel();
		for (int i = 0; i < model.getSize(); i++)
		{
			Object o = model.getElementAt(i);
			if (null != o)
				dataList.add(o);
		}
		
		return dataList;
	}
	
	
	public static List toStringList (JList list)
	{
		List strings = new ArrayList();
		
		ListModel model = list.getModel();
		int size = model.getSize();
		
		for (int i = 0; i < size; i++)
		{
			Object o = model.getElementAt(i);
			if (null != o)
				strings.add(o.toString());
		}
		
		return strings;
	}
	
	public static void mapKey(int condition, InputKey key, Action action, JComponent comp)
	{
		InputMap imap = comp.getInputMap(condition);
		imap.put(key.getKeyStroke(), key);
		ActionMap amap = comp.getActionMap();
		amap.put(key, action);
	}
	
	
	public static void mapKey(InputKey key, Action action, JComponent comp)
	{
		mapKey(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, key, action, comp);
	}
	
	
	public static void mapKeyAsDefault(InputKey key, Action action, JComponent comp)
	{
		int condition = JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
		mapKey(condition, key, action, comp);
	}
	
	public static void mapKeyAsDefault(KeyStroke key, Action action, JComponent comp)
	{
		int condition = JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
		InputMap imap = comp.getInputMap(condition);
		imap.put(key, key);
		ActionMap amap = comp.getActionMap();
		amap.put(key, action);
	}

	public static void setPreferredWidth(JComponent comp, int width)
	{
		Dimension dim = comp.getPreferredSize();
		dim.width = width;
		comp.setPreferredSize(dim);
	}
	
	public static void setWidth(JComponent comp, int width)
	{
		Dimension dim = comp.getSize();
		dim.width = width;
		comp.setSize(dim);
	}

	public static void setBold(JComponent field)
	{
		Font font = field.getFont();
		font = new Font(font.getName(), font.getStyle() | Font.BOLD, font.getSize());
		field.setFont(font);
	}
	
	
	public static Font scaleFont(Font font, int pointSizeDelta)
	{
		Font result = new Font(font.getName(), font.getStyle(), font.getSize() + pointSizeDelta);
		return result;
	}
	
	public static void modifyFont(JComponent comp, int style, int sizeDelta)
	{
		Font font = comp.getFont();
		Font result = new Font(font.getName(), style, font.getSize() + sizeDelta);
		comp.setFont(result);
	}
	
	
	public static int getLayoutWidth(JComponent comp)
	{
		LTSPanel panel = new LTSPanel();
		panel.addLabel(comp);
		panel.doLayout();
		return comp.getWidth();
	}
	
	
	public static int getButtonWidth(String label)
	{
		JButton button = new JButton(label);
		return getLayoutWidth(button);
	}


	public static int getLargerButtonWidth(int width, String label)
	{
		int temp = getButtonWidth(label);
		if (temp > width)
			width = temp;
		
		return width;
	}


	public static Dimension getLargerButton(Dimension dim, String string)
	{
		if (null == dim)
			dim = new Dimension();
		
		LTSPanel panel = new LTSPanel();
		JButton button = new JButton(string);
		panel.addLabel(button);
		panel.doLayout();
		
		Dimension result = new Dimension();
		Dimension temp = button.getSize();
		if (temp.width > dim.width)
			result.width = temp.width;
		else
			result.width = dim.width;
		
		if (temp.height > dim.height)
			result.height = temp.height;
		else
			result.height = dim.height;
		
		return result;
	}

	/**
	 * Return the value of a string field or null.
	 * <P>
	 * This method will return null if the text field passed to it is null or if the value
	 * of that field, when passed to {@link StringUtils#trim(String)}, returns null.
	 * </P>
	 * 
	 * @param fileName
	 *        The field to process.
	 * @return The value of the field, or null if the field is null, or the value is null,
	 *         or the value consists of only whitespace.
	 */
	public static String trimField(JTextField fileName)
	{
		String s = null;
		
		if (null != fileName)
			s = StringUtils.trim(fileName.getText());
		
		return s;
	}
	
	
	public static void setupDoubleClickAction(JComponent comp, ActionListener listener)
	{
		SimpleMouseAdapter adapt = SimpleMouseAdapter.createDoubleClick(listener);
		comp.addMouseListener(adapt);
	}


	/**
	 * Return the resource locations to search for the look and feel.
	 * 
	 * <P>
	 * This method returns an array of strings that are the locations that will
	 * be used as arguments to {@link Class#getResourceAsStream(String)} in order
	 * to try and find a matching properties file.
	 * 
	 * <P>
	 * See {@link #setLookAndFeel()} for details.
	 * 
	 * @see #setLookAndFeel()
	 */
	public static String[] lafGetResourceNames()
	{
		return LAF_DEFAULT_LOCATIONS;
	}


	/**
	 * Try to set the UIManager look and feel.
	 * 
	 * <P>
	 * The method calls {@link UIManager#setLookAndFeel(String)}.  If this fails,
	 * the method displays the resulting exception and returns false.  Otherwise
	 * it returns true.
	 * 
	 * @param className Name of the class to use in the call to setLookAndFeel
	 * @return true if the call succeeds, false otherwise.
	 */
	protected static boolean lafSetLookAndFeel(String className)
	{
		try
		{
			UIManager.setLookAndFeel(className);
			return true;
		}
		catch (Exception e)
		{
			Application.showException(e);
			return false;
		}
	}
	
	private static final String PLAF_WINDOWS = 
		"com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	public static final String[] LAF_DEFAULT_LOCATIONS = {
			"/resources/swing/swing.properties",
			"/resources/swing.properties",
			"/swing.properties"
		};
	
	public static void setWindowsLookAndFeel() throws ApplicationException
	{	
		try
		{
			UIManager.setLookAndFeel(PLAF_WINDOWS);
		}
		catch (RuntimeException e)
		{
			throw (RuntimeException) e;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	
	/**
	 * Set the look and feel for the application by checking for a properties file.
	 * 
	 * <P>
	 * This method tries to set the UI look and feel by looking up a properties file
	 * located in one of several "well defined" locations.
	 * 
	 * <P>
	 * The method uses {@link Class#getResourceAsStream(String)} to try and find the
	 * resource (properties file) "swing.properties" in the following locations:
	 * <UL>
	 * <LI>/resources/swing/swing.properties
	 * <LI>/resources/swing.properties
	 * <LI>/swing.properties
	 * </UL>
	 * 
	 * <P>
	 * For each of the resource names specified, the method does the following:
	 * <UL>
	 * <LI>try to get the name of the class to use in a call to 
	 * {@link UIManager#setLookAndFeel(String)} by calling 
	 * {@link #lafCheckResourceName(String)}.
	 * 
	 * <LI>If lafCheckResourceName returns a value, then that string returned is 
	 * deemed to be the name of a class that should be used as the new nook and 
	 * to be used in a call to {@link UIManager#setLookAndFeel(String)}.
	 * 
	 * If the call to the UIManager succeeds, then the method stops.  If the call
	 * fails, then the process tries again with the next location.
	 */
	public static void setLookAndFeel(Class clazz)
	{
		String[] locations = lafGetResourceNames();
		if (null == locations)
			return;
		
		for (int i = 0; i < locations.length; i++)
		{
			String className = lafCheckResourceName(locations[i], clazz);
			if (null != className)
				if (lafSetLookAndFeel(className))
					break;
		}
	}
	
	
	public static void setLookAndFeel()
	{
		Class clazz = SwingUtils.class;
		setLookAndFeel(clazz);
	}
	
	
	/**
	 * Try to find the "swing.defaultlaf" property in a particular resource.
	 * 
	 * <P>
	 * This method performs the following actions, returning null if any step
	 * fails:
	 * <UL>
	 * <LI>Find a resource via {@link Class#getResourceAsStream(String)}.
	 * <LI>Create a properties object from the resource.
	 * <LI>Find and return the value of the "swing.defaultlaf" property
	 * </UL>
	 * 
	 * @param name Name of the resource to search.
	 * @return Value of the property, or null if the property was not found.
	 */
	protected static String lafCheckResourceName(String name, Class clazz)
	{
		InputStream istream = null;
		Properties props = new Properties();
		
		try
		{
			istream = clazz.getResourceAsStream(name);
			if (null == istream)
				return null;
			
			props.load(istream);
			return props.getProperty("swing.defaultlaf");
		}
		catch (Exception e)
		{
			Application.showException(e);
			return null;
		}
		finally
		{
			IOUtilities.close(istream);
		}
	}



}
