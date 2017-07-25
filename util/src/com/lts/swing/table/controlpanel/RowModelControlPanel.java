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
package com.lts.swing.table.controlpanel;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JTable;

import com.lts.event.LTSMouseAdapter;
import com.lts.event.SimpleThreadedAction;
import com.lts.swing.table.ControlPanel;

abstract public class RowModelControlPanel extends ControlPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	abstract protected void select(int[] selections);
	abstract protected Object create();
	
	protected JTable myTable;
	
	private PanelModes myMode;
	
	public void initialize(PanelModes mode, JTable table)
	{
		JButton button;
		ActionListener action;
		
		myMode = mode;
		
		action = new SimpleThreadedAction() {
			public void action() {
				create();
			}
		};
		button = new JButton("Create");
		button.addActionListener(action);
		addButton(button,5);
		
		action = new SimpleThreadedAction() {
			public void action() {
				checkedEdit();
			}
		};
		button = new JButton("Edit");
		button.addActionListener(action);
		addButton(button,5);
		
		action = new SimpleThreadedAction() {
			public void action() {
				checkedDelete();
			}
		};
		button = new JButton("Delete");
		button.addActionListener(action);
		addButton(button,5);
		
		if (mode == PanelModes.SelectOnly)
		{
			action = new SimpleThreadedAction() {
				public void action() {
					checkedSelect();
				}
			};
			button = new JButton("Select");
			button.addActionListener(action);
			addButton(button,5);
		}
	}
	
	
	public PanelModes getMode()
	{
		return myMode;
	}
	
	
	public void initializeTable(JTable table)
	{
		myTable = table;
		
		MouseListener listener = new LTSMouseAdapter() {
			public void doubleClick(Object source) {
				performDoubleClick();
			}
		};
		
		myTable.addMouseListener(listener);
	}
}
