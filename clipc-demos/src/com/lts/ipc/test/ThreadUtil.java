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
package com.lts.ipc.test;

/**
 * Utility methods used with java.lang.Thread.
 */
public class ThreadUtil
{
	public static long toMilliSeconds (long millis, int nanos)
	{
		long time = millis;
		if (nanos > 0)
			time += (nanos/1000000);
		
		return millis;
	}
	
	
	/**
	 * Sleep for a specified period, throwing a RuntimeException if interrupted.
	 * 
	 * <P>
	 * Use if you are not planning on interrupting sleeping threads.
	 * 
	 * @param millis number of milliseconds to sleep.
	 * @param nanos number of nanoseconds to sleep.
	 */
	public static void sleep (long millis, int nanos)
	{
		try
		{
			Thread.sleep(millis, nanos);
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}
		
	/**
	 * Sleep for a specified period, throwing a RuntimeException if interrupted.
	 * 
	 * <P>
	 * Use if you are not planning on interrupting sleeping threads.
	 * 
	 * @param millis number of milliseconds to sleep.
	 * @param nanos number of nanoseconds to sleep.
	 */
	public static void sleep (long millis)
	{
		sleep (millis, 0);
	}
	
	/**
	 * Sleep for some period; simply retutrn if interrupted.
	 * 
	 * @param millis The time to sleep, in milliseconds.
	 */
	public static void sleepNoExceptions (long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch (InterruptedException e)
		{
			// ignore the exception
		}
	}
}
