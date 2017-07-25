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
package com.lts.application.international;

import java.util.ListResourceBundle;

public class CriticalMessages extends ListResourceBundle
{
	static public String[][] MESSAGES = {
			{
					"critcal.error.unknown",
					"The application has encountered an unknown error condition.  "
							+ "This probably means that you have found a bug in the program."
			},

			{
					"critial.question.quit", "Do you want to quit the application?"
			},
			{
					"critical.error.internal", "An internal error has occurred."
			},
			{
					"critical.error.resourceNotFound",
					"An internal error has occurred that has prevented the application from getting some "
							+ "data that it needs."
			},
			{
					"critical.error.unknownException",
					"The application has encountered an unknown error.  It is likely that whatever "
							+ "operation you had requested has failed."
			},
			{
					"critical.error.unrecognizedKey",
					"An internal error has occurred that has prevented the application from displaying "
							+ "messages.  This probably means that you have founda bug in the program."
			},
			{
					"critical.question.quit.title", "Quit Application"
			},
			{
					"critical.question.saveData",
					"You have unsaved changes.  Save them now?"
			},
			{
					"critical.question.saveDataTitle", "Unsaved Changes"
			},
			{
					"critical.question.saveFailed",
					"The application could not save your changes because {0}"
			},
			{
					"critical.question.saveFailedUnknown",
					"The application was unable to save your changes.  No additional information "
							+ "regarding the problem is available.  Do you want to try saving again or do"
							+ "you want to discard the changes?"
			},
			{
					"criticlal.com.lts.application.errors.properties.saving",
					"The application could not settings and preferences to the file {0}."
			},
	};

	@Override
	protected Object[][] getContents()
	{
		return MESSAGES;
	}

}
