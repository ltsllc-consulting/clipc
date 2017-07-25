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
package com.lts.util.scheduler;

import com.lts.util.DefaultSharedQueue;
import com.lts.util.SharedQueue;

/**
 * A thread that calls a method when it receives data from a shared queue.
 * 
 * <H2>Abstract Class</H2>
 * Subclasses must define the following methods to be instantiatable:
 * <UL>
 * <LI>process
 * </UL>
 * 
 * <H2>Description</H2>
 * Objects of this class have a number of well defined states:
 * <UL>
 * <LI>STATUS_INITIALIZED --- after the constructor has returned.
 * <UL>
 * 		<LI>stop will result in the object immediately going to the 
 * stopped state without it trying to start the thread.
 * 		<LI>start will result in the object going into the starting 
 * state.  The thread associated with the object will have it's start 
 * method called.  Once the state changes to STATUS_RUNNING, the object is 
 * executing.
 * </UL>
 * 
 * <LI>STATUS_STARTING --- after the start method has returned, but before the 
 * first call to loop.
 * <UL>
 * 		<LI>stop will cause the thread to stop after the end of the next 
 * loop iteration.  The thread will be interrupted if it was waiting on the 
 * shared queue.
 * 
 * 		<LI>Calling start has no effect at this point.
 * </UL>
 * 
 * <LI>STATUS_RUNNING --- after the first call to loop.
 * <UL>
 * 		<LI>Calling stop will cause the object to go into the STATUS_STOPPING
 * state.  The thread will also be interrupted if it was waiting on the shared 
 * queue.  The state goes to STATE_STOPPED just before the run method completes
 * 
 * 		<LI>Calling start at this point will be ignored.
 * </UL>
 * 
 * <LI>STATUS_STOPPING --- the thread is in the process of stopping.
 * <UL>
 * 		<LI>Calls to start and stop are ignored at this point.
 * </UL>
 * 
 * <LI>STATUS_STOPPED --- The reader is dead for all intents and purposes.
 * Calls to start and stop will be ignored.
 * 
 * </UL>
 * 
 * @author cnh
 *
 */
abstract public class QueueReader extends ControllableThread
{
	abstract protected void process(Object o) throws Exception;
	
	protected SharedQueue myQueue;
	protected boolean myCheckThenProcess;
	
	public boolean checkThenProcess()
	{
		return myCheckThenProcess;
	}
	
	public QueueReader()
	{
		initialize(null);
	}
	
	
	public QueueReader(String description)
	{
		initialize(description);
	}
	
	
	public void initialize(String description)
	{
		myQueue = new DefaultSharedQueue();
		super.initialize(description);
	}
	
	
	public SharedQueue getQueue()
	{
		return myQueue;
	}


	public void setQueue(SharedQueue queue)
	{
		this.myQueue = queue;
	}
	
	protected long getWaitTime()
	{
		return 0;
	}


	protected boolean shouldProcess (Object o)
	{
		return null != o; 
	}
	
	
	protected boolean shouldProcessMessage(Object o)
	{
		return true;
	}

	protected boolean processThenCheck()
	{
		return !checkThenProcess();
	}

	protected void implementOneCycle() throws Exception
	{
		try
		{
			Object o = myQueue.next(getWaitTime());
			
			if (shouldProcess(o))
				process(o);
		}
		catch (InterruptedException e)
		{
			threadInterrupt(e);
		}
	}
}
