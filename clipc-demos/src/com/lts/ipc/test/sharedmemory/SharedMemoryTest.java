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
package com.lts.ipc.test.sharedmemory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

import com.lts.ipc.IPCException;
import com.lts.ipc.IPCPackage;
import com.lts.ipc.Utils;
import com.lts.ipc.sharedmemory.SharedMemory;
import com.lts.ipc.test.ArgumentException;
import com.lts.ipc.test.TestClass;
import com.lts.ipc.test.TestException;

public class SharedMemoryTest extends TestClass
{
	public enum SharedMemorySubtests
	{
		Locking,
		Hog,
		Continuous,
		Bandwidth;
		
		
		public static SharedMemorySubtests toValueIgnoreCase (String s)
		{
			return (SharedMemorySubtests) Utils.toValueIgnoreCase(values(), s);
		}
	}
	
	public SharedMemoryTest()
	{
		super();
		initialize();
	}
	
	protected void initialize()
	{
		setName("/temp/testSharedMemory");
	}
	
	
	private SharedMemory mySharedMemory;
	private SharedMemorySubtests mySubtest;
	
	@Override
	protected void performTest() throws TestException
	{
		setup();

		try
		{
			switch(getSubtest())
			{
				case Continuous :
				case Locking :
					testLockingContinuous();
					break;
					
				case Bandwidth :
					dataTest();
					break;
										
				case Hog :
					testHogMode();
					break;
					
				default :
					throw new TestException("Undefined test type");
			}
		}
		catch (IOException e)
		{
			throw new TestException("exception performing locking test", e);
		}
	}

	private void setupSharedMemory() throws TestException
	{
		try
		{
			SharedMemory smem = new SharedMemory(getName(), getBufferSize());
			setSharedMemory(smem);
		}
		catch (IPCException e)
		{
			throw new TestException("Error creating shared memory", e);
		}
	}

	private synchronized void testHogMode() throws IOException, TestException
	{
		getSharedMemory().lock();
		try
		{
			System.out.println("acquired lock, going to sleep forever");
			wait();
		}
		catch (InterruptedException e)
		{
			throw new TestException(e);
		}
	}

	private void testLockingContinuous() throws IOException
	{
		long now = System.currentTimeMillis();
		long nextReport = getReportInterval() + now;
		noteStartTime();
		
		while (true)
		{
			now = System.currentTimeMillis();
			if (now > nextReport)
			{
				noteStopTime();
				iterationReport();
				resetIterations();
				noteStartTime();
				
				nextReport = getReportInterval() + System.currentTimeMillis();
			}
			
			lockIteration();
			incrementIterations();
		}
	}

	protected void setup() throws TestException
	{
		setupSharedMemory();
		setupBuffer();
	}
	
	protected void setupBuffer()
	{
		int size = 1024;
		if (0 != getBufferSize())
			size = getBufferSize();
		
		myBuffer = new byte[size];
		Random rand = new Random();
		rand.nextBytes(myBuffer);
	}
	
	
	static ByteBuffer buffer;
	
	
	protected void dataTest() throws TestException 
	{
		String msg =
			"Shared memory data test.  Buffer size = " 
			+ getBuffer().length
			+ ", name = " + getName();
			
		System.out.println(msg);
		
		
		if (null == buffer)
		{
			buffer = ByteBuffer.allocateDirect(1024);
		}
		
		long startTime = System.currentTimeMillis();
		long nextReport = getReportInterval() + startTime;
		noteStartTime();
		
		while (!metDurationGoal())
		{
			long now = System.currentTimeMillis();
			if (now < nextReport)
			{
				dataIteration();
				incrementIterations();
			}
			else
			{
				noteStopTime();
				bandwidthReport();
				resetIterations();
				nextReport = getReportInterval() + System.currentTimeMillis();
				noteStartTime();
			}
			
			ByteBuffer bb;
			bb = IPCPackage.createBuffer(16 * 1024);
			bb.hasArray();
		}
	}
	
	protected void dataIteration() throws TestException
	{
		try
		{
			getSharedMemory().lock();
			getSharedMemory().write(getBuffer());
			getSharedMemory().unlock();
		}
		catch (IPCException e)
		{
			throw new TestException("Error writing shared memory",e);
		}
		catch (IOException e)
		{
			throw new TestException("Error on lock", e);
		}
	}
	
	
	private void lockIteration() throws IOException
	{
		getSharedMemory().lock();
		getSharedMemory().unlock();
	}

	public SharedMemory getSharedMemory()
	{
		return mySharedMemory;
	}

	public void setSharedMemory(SharedMemory sharedMemory)
	{
		mySharedMemory = sharedMemory;
	}

	@Override
	protected void processArgument(String argument) throws ArgumentException
	{
		SharedMemorySubtests subtest;
		subtest = SharedMemorySubtests.toValueIgnoreCase(argument);
		setSubtest(subtest);
	}

	public void setSubtest(SharedMemorySubtests subtest)
	{
		mySubtest = subtest;
	}
	
	public SharedMemorySubtests getSubtest()
	{
		return mySubtest;
	}
}
