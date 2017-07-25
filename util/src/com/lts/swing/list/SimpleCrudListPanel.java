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
package com.lts.swing.list;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import com.lts.event.ActionListenerHelper;
import com.lts.event.LTSMouseAdapter;
import com.lts.event.SimpleThreadedAction;
import com.lts.exception.NotImplementedException;
import com.lts.swing.LTSPanel;
import com.lts.swing.SimpleMouseAdapter;
import com.lts.swing.keyboard.InputKey;
import com.lts.swing.keyboard.KeyStrokeAction;
import com.lts.util.ArrayUtils;

/**
 * A panel that displays a JList along some buttons that are commonly used with
 * a list like create, delete, move up, etc.
 * 
 * <P>
 * The full list of buttons that the list can have, along with their default behaviors
 * are:
 * </P>
 * 
 * <UL>
 * <LI>delete - deletes the current element.</LI>
 * <LI>up - Move the currently selected element up one.</LI>
 * <LI>down - Move the currently selected element down one.</LI>
 * </UL>
 * 
 * <P>
 * The following buttons can be displayed, but must be overridden by a subclass.  
 * Calling the corresponding method that this class defines will result in a 
 * {@link NotImplementedException} being thrown.
 * </P>
 * 
 * <UL>
 * <LI>update</LI>
 * <LI>delete</LI>
 * </UL>
 * 
 * @author cnh
 */
@SuppressWarnings("serial")
public class SimpleCrudListPanel extends LTSPanel
{
	abstract public static class SclpThreadedAction extends SimpleThreadedAction
	{
		public SimpleCrudListPanel panel;
	}
	
	
	public enum PanelButton 
	{
		Create
		{
			public JButton createButton(SimpleCrudListPanel panel)
			{
				SclpThreadedAction action =  new SclpThreadedAction() {
					public void action() {
						panel.processCreate();
					}
				};
				
				action.panel = panel;
				JButton button = new JButton("Create");
				button.addActionListener(action);
				return button;
			}
		},
		
		Update
		{
			public JButton createButton(SimpleCrudListPanel panel)
			{
				SclpThreadedAction action =  new SclpThreadedAction() {
					public void action() {
						panel.processUpdate();
					}
				};
				
				action.panel = panel;
				JButton button = new JButton("Update");
				button.addActionListener(action);
				return button;
			}
		},

		Delete
		{
			public JButton createButton(SimpleCrudListPanel panel)
			{
				SclpThreadedAction action =  new SclpThreadedAction() {
					public void action() {
						panel.processDelete();
					}
				};
				
				action.panel = panel;
				JButton button = new JButton("Delete");
				button.addActionListener(action);
				return button;
			}
		},

		MoveUp
		{
			public JButton createButton(SimpleCrudListPanel panel)
			{
				SclpThreadedAction action =  new SclpThreadedAction() {
					public void action() {
						panel.processMoveUp();
					}
				};
				
				action.panel = panel;
				JButton button = new JButton("Up");
				button.addActionListener(action);
				return button;
			}
		},

		MoveDown
		{
			public JButton createButton(SimpleCrudListPanel panel)
			{
				SclpThreadedAction action =  new SclpThreadedAction() {
					public void action() {
						panel.processMoveDown();
					}
				};
				
				action.panel = panel;
				JButton button = new JButton("Down");
				button.addActionListener(action);
				return button;
			}
		};
		
		abstract public JButton createButton(SimpleCrudListPanel panel);
	}
	
	
	public enum PanelMode
	{
		AllButtons
		{
			public PanelButton[] getButtons()
			{
				return new PanelButton[] {
						PanelButton.Create,
						PanelButton.Update,
						PanelButton.Delete,
						PanelButton.MoveDown,
						PanelButton.MoveUp,
				};
			}
		},
		
		EditButtons
		{
			public PanelButton[] getButtons()
			{
				return new PanelButton[] {
						PanelButton.Create,
						PanelButton.Update,
						PanelButton.Delete,
				};
			}
		},

		MovementButtons
		{
			public PanelButton[] getButtons()
			{
				return new PanelButton[] {
						PanelButton.MoveDown,
						PanelButton.MoveUp,
				};
			}
		},
		
		MinusAdd
		{
			public PanelButton[] getButtons()
			{
				return new PanelButton[] {
						PanelButton.Update,
						PanelButton.Delete,
						PanelButton.MoveUp,
						PanelButton.MoveDown
				};
			}
		},
		
		JustDelete
		{
			public PanelButton[] getButtons()
			{
				return new PanelButton[] {
						PanelButton.Delete
				};
			}
		},
		
		None
		{
			public PanelButton[] getButtons()
			{
				PanelButton[] buttons = new PanelButton[0];
				return buttons;
			}
		}
		;
		
		
		abstract public PanelButton[] getButtons();
	}
	
	
	protected DefaultListModel myModel;
	protected JList myList;
	protected ActionListenerHelper myHelper;
	
	public SimpleCrudListPanel(PanelMode mode)
	{
		initialize(mode);
	}
	
	/**
	 * Create the panel using the provided list model and with the specified 
	 * buttons.
	 * 
	 * @param model The list model to use.
	 * @param buttons The buttons to display.
	 */
	protected void initialize(DefaultListModel model, PanelButton[] buttons)
	{
		myHelper = new ActionListenerHelper();
		myModel = model;
		initializePanel(buttons);
	}
	
	
	protected void initialize(DefaultListModel model, PanelMode mode)
	{
		initialize(model, mode.getButtons());
	}
	
	protected void initialize(PanelMode mode)
	{
		DefaultListModel model = new DefaultListModel();
		initialize(model, mode);
	}
	
	protected void initialize(PanelButton[] buttons)
	{
		DefaultListModel model = new DefaultListModel();
		initialize(model, buttons);
	}
	
	
	protected void initializePanel(PanelButton[] buttons)
	{
		
		addFill(createListPanel(myModel));
		nextRow();
		addHorizontal(createButtonPanel(buttons));
		
		mapKeys();
		initializeMouseListener();
	}
	
	
	protected void mapMouse()
	{
		SimpleMouseAdapter listener = new SimpleMouseAdapter()
		{
			public void doubleClick(MouseEvent event)
			{
				int selected = myList.getSelectedIndex();
				if (-1 != selected)
				{
					processDoubleClick(selected);
				}
			}
		};
		
		myList.addMouseListener(listener);
	}
	
	
	protected void processDoubleClick(int index)
	{}


	abstract protected class ListKeyStrokeAction extends KeyStrokeAction
	{
		public ListKeyStrokeAction(InputKey key)
		{
			super(key);
		}
	}
	
	
	public static InputKey[] DEFAULT_MAPPED_KEYS = {
		InputKey.Insert,
		InputKey.Delete
	};
	
	/**
	 * Define keyboard mappings for commonly used keys.
	 * 
	 * <P>
	 * The keys and their mappings:
	 * </P>
	 * 
	 * <UL>
	 * <LI>{@link InputKey#Delete} - {@link #processDelete()}</LI>
	 * <LI>{@link InputKey#Insert} - {@link #processCreate()}</LI>
	 * </UL>
	 */
	protected void mapKeys(InputKey[] keysToMap)
	{
		KeyStrokeAction action;
		
		action = new KeyStrokeAction() {
			public void keyAction(InputKey key) {
				processKey(key);
			}			
		};
		
		for (int i = 0; i < keysToMap.length; i++)
		{
			KeyStrokeAction.mapInputKey(keysToMap[i], action, myList);
		}
	}
	
	
	protected void mapKeys()
	{
		InputKey[] keysToMap = getDefaultKeysToMap();
		mapKeys(keysToMap);
	}
	
	protected InputKey[] getDefaultKeysToMap()
	{
		return DEFAULT_MAPPED_KEYS;
	}

	/**
	 * React to a special key being pressed while the JList has focus.
	 * 
	 * <P>
	 * See the class description for a list of which key strokes are mapped by
	 * default.  Subclasses that want to process additional keys should override 
	 * this method, check for the additional keys, and then call this version of
	 * the method to get the default processing.
	 * </P>
	 * 
	 * @param key The key to process.
	 */
	protected void processKey(InputKey key)
	{
		switch (key)
		{
			case Delete :
				processDelete();
				break;
				
			case Insert :
				processCreate();
				break;
				
			default :
				String message = "Unrecognized key code: " + key;
				throw new IllegalArgumentException(message);
		}
	}

	protected JPanel createListPanel(DefaultListModel model)
	{
		LTSPanel panel = new LTSPanel();
		
		myList = new JList(model);
		JScrollPane jsp = new JScrollPane(myList);
		panel.addFill(jsp);
		mapKeys();
		
		return panel;
	}

	
	
	protected void processUpdate()
	{
		throw new NotImplementedException();
	}
	
	protected void processMoveUp()
	{
		//
		// "The Block" refers to the sublist of elements in the list of repositories that
		// are currently selected.
		//
		// Shifting the block up one element is the same as
		// * deleting the first element before the block
		// * inserting the deleted element after the block
		// * updating the selected indicies to be one less than they were before
		//
		int[] selections = myList.getSelectedIndices();
		if (null == selections || selections.length < 1)
		{
			return;
		}
		
		int beforeIndex = ArrayUtils.smallest(selections) - 1;
		int lastIndex = ArrayUtils.largest(selections);
		if (beforeIndex < 0)
			return;
		
		//
		// remove the first element before the block
		//
		Object element = myModel.elementAt(beforeIndex);
		myModel.remove(beforeIndex);
		
		//
		// add the deleted element after the block
		//
		myModel.add(lastIndex, element);
		
		//
		// subtract 1 from the indicies of the selected elements
		//
		for (int i = 0; i < selections.length; i++)
		{
			selections[i]--;
		}
		myList.setSelectedIndices(selections);
	}
	
	protected void processMoveDown()
	{
		//
		// moving the selected sub-list down by one element is exactly the same thing as
		// * removing the first element after the last element of the selected sub-list
		// * inserting the recently deleted element before the selected sub-list
		// * adding 1 to indicies of the selected sub-list
		//
		// Note that this works only if the selected sub-list is contiguous
		//
		int[] selections = myList.getSelectedIndices();
		if (null == selections || selections.length < 1)
			return;
		
		int firstIndex = selections[0];
		int afterIndex = 1 + ArrayUtils.largest(selections);
		
		if (afterIndex >= myModel.getSize())
			return;
		
		//
		// remove the first element after the selected sub-list
		//
		Object after = myModel.get(afterIndex);
		
		myModel.remove(afterIndex);
		
		//
		// insert the deleted element *before* the first element of the selected-sublist
		//
		myModel.add(firstIndex, after);
		
		//
		// update the selected sub-list so that it is shifted down one element
		//
		for (int i = 0; i < selections.length; i++)
		{
			selections[i]++;
		}
		
		myList.setSelectedIndices(selections);
	}
	
	protected void processDelete()
	{
		//
		// Deleting the selected sub-list is the same as deleting the first element of 
		// that sub-list a number of times equal to the number of elements in the 
		// sub-list.
		//
		int[] selected = myList.getSelectedIndices();
		if (null == selected || selected.length < 1)
			return;
		
		int first = ArrayUtils.smallest(selected);
		for (int i = 0; i < selected.length; i++)
		{
			myModel.remove(first);
		}
		
		if (first >= myModel.getSize())
		{
			first = myModel.getSize() - 1;
		}
		
		if (first < 0)
			return;
		
		myList.setSelectedIndex(first);
	}
	
	protected void processCreate()
	{
		throw new NotImplementedException();
	}
	
	protected JPanel createButtonPanel(PanelButton[] buttons)
	{
		LTSPanel panel = new LTSPanel();
		JButton button;
		
		for (int i = 0; i < buttons.length; i++)
		{
			button = buttons[i].createButton(this);
			panel.addButton(button,5);
		}
		
		return panel;
	}
	
	/**
	 * Insert an element before the currently selected element, or add it to the 
	 * end of list if there is no selected element.
	 * 
	 * @param element The element to add.
	 */
	public int insertOrAdd (Object element)
	{
		int index = myList.getSelectedIndex();
		if (-1 == index)
		{
			index = myModel.getSize() - 1;
			myModel.addElement(element);
		}
		else
		{
			myModel.add(index, element);
		}
		
		return index;
	}
	
	
	public void update (int index, Object newValue)
	{
		myModel.set(index, newValue);
	}
	
	
	public void setElements(List list)
	{
		myModel.removeAllElements();
		for (Object o : list)
		{
			myModel.addElement(o);
		}
	}
	
	public void addElement(Object element)
	{
		myModel.addElement(element);
	}
	
	public void addIfAbsent(Object element)
	{
		if (!myModel.contains(element))
		{
			myModel.addElement(element);
		}
	}
	
	public List getElements()
	{
		List list = new ArrayList();
		
		int count = myModel.getSize();
		for (int i = 0; i < count; i++)
		{
			list.add(myModel.get(i));
		}
		
		return list;
	}

	public void initializeMouseListener()
	{
		LTSMouseAdapter mouseListener = new LTSMouseAdapter()
		{
			public void doubleClick(Object source)
			{
				myHelper.fireAction(this);
			}
		};
		
		myList.addMouseListener(mouseListener);
	}
	
	
	public void addDoubleClickListener (ActionListener listener)
	{
		myHelper.addListener(listener);
	}

	public Object getSelectedItem()
	{
		return myList.getSelectedValue();
	}
	
	public int getSelectedIndex()
	{
		return myList.getSelectedIndex();
	}
	
	public void setSelectedIndex(int index)
	{
		myList.setSelectedIndex(index);
	}
	
	
	public Object getElementAt(int index)
	{
		ListModel model = myList.getModel();
		return model.getElementAt(index);
	}
	
	public void removeAt(int index)
	{
		myList.remove(index);
	}
	
	public void addElement(int index, Object element)
	{
		myModel.add(index, element);
	}
	
	
	public int getListSize()
	{
		return myModel.size();
	}

	public boolean containsElement(Object o)
	{
		return myModel.contains(o);
	}

	public void replaceWith(List list)
	{
		myModel.clear();
		for (Object o : list)
		{
			myModel.addElement(o);
		}
	}
	
}
