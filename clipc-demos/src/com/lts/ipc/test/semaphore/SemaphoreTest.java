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
package com.lts.ipc.test.semaphore;

import com.lts.ipc.IPCException;
import com.lts.ipc.semaphore.Semaphore;
import com.lts.ipc.test.TestException;

public class SemaphoreTest extends ClientServerTest
{
	private Semaphore mySemaphore;

	
	public SemaphoreTest()
	{
	}

	@Override
	protected void performTest() throws TestException
	{
		setup();
		
		noteStartTime();
		switch(getTestMode())
		{
			case Client :
			case Server :
				multiProcessTest();
				break;
				
			case Singleton :
				singleProcessTest();
				break;
				
			case Continuous :
				performContinuousTest();
				break;
				
			default :
				throw new TestException("unrecognized test mode " + getTestMode());
		}
		noteStopTime();
		
		report();
	}

	private void singleProcessTest() throws TestException
	{
		noteStartTime();
		updateNextReport();
		while (true)
		{
			try
			{
				checkReport();
				getSemaphore().decrement();
				getSemaphore().increment();
				incrementIterations();
			}
			catch (IPCException e)
			{
				throw new TestException("Error accessing semaphore", e);
			}
		}
	}

	
	private void multiProcessTest() throws TestException
	{
		try
		{
			while (!metDurationGoal())
			{
				getSemaphore().decrement();
				getSemaphore().increment();
				incrementIterations();
			}
		}
		catch (IPCException e)
		{
			throw new TestException(e);
		}
	}
	
	
	private void performContinuousTest () throws TestException
	{
		try
		{
			long now = System.currentTimeMillis();
			long nextReport = now + getReportInterval();
			
			while (true)
			{
				now = System.currentTimeMillis();
				if (now > nextReport)
				{
					noteStopTime();
					iterationReport();
					resetIterations();
					nextReport = getReportInterval() + System.currentTimeMillis();
					noteStartTime();
				}
				getSemaphore().decrement();
				getSemaphore().increment();
				incrementIterations();
			}
		}
		catch (IPCException e)
		{
			throw new TestException(e);
		}
	}
	
	protected void report()
	{
		iterationReport();
	}

	private void setup() throws TestException
	{
		try
		{
			String name = getName();
			if (null == name)
			{
				name = "/temp/semtest";
			}
			
			mySemaphore = new Semaphore(name, 1);
		}
		catch (IPCException e)
		{
			throw new TestException("Error trying to create semaphore", e);
		}
	}

	public Semaphore getSemaphore()
	{
		return mySemaphore;
	}

	public void setSemaphore(Semaphore semaphore)
	{
		mySemaphore = semaphore;
	}
}
