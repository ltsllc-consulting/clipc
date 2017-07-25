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
package com.lts.chatterbox.procwin;

import java.util.Random;

import com.lts.chatterbox.MultiMonException;
import com.lts.chatterbox.smem.Channel;
import com.lts.chatterbox.smem.ChannelException;

public class ChatterThread implements Runnable
{
	
	public static byte[] NEWLINE = "\n".getBytes();
	public static long DEFAULT_PAUSE_TIME = 3000;
	
	private String myName;
	private boolean myKeepGoing;
	private Thread myThread;
	private ChatterHelper myHelper = new ChatterHelper();
	private long myPauseTime = 2000;
	private Channel myTransmitter;
	
	public Channel getTransmitter()
	{
		return myTransmitter;
	}

	public void setTransmitter(Channel transmitter)
	{
		myTransmitter = transmitter;
	}

	public void addListener(ChatterListener listener)
	{
		myHelper.addListener(listener);
	}
	
	public boolean removeListener(ChatterListener listener)
	{
		return myHelper.removeListener(listener);
	}
	
	
	public Thread getThread()
	{
		return myThread;
	}
	
	public boolean keepGoing()
	{
		return myKeepGoing;
	}
	
	public void setKeepGoing(boolean keepGoing)
	{
		myKeepGoing = keepGoing;
	}
	
	public String getName()
	{
		return myName;
	}

	public void setName(String name)
	{
		myName = name;
	}

	public ChatterThread(Channel trans, String name, long pauseTime) throws MultiMonException
	{
		initialize(trans, name, pauseTime);
	}
	
	
	
	public void initialize(Channel trans, String name, long pauseTime) throws MultiMonException
	{
		myKeepGoing = true;
		setName(name);
		setPauseTime(pauseTime);
		setTransmitter(trans);
	}

	public static ChatterThread launch (Channel trans, String speakerName, long pauseTime)
	{
		try
		{
			ChatterThread instance = new ChatterThread(trans, speakerName, pauseTime);
			Thread thread = new Thread(instance, speakerName);
			instance.myThread = thread;
			thread.start();
			return instance;
		}
		catch (MultiMonException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static ChatterThread launch (Channel trans, String speakerName)
	{
		return launch(trans, speakerName, DEFAULT_PAUSE_TIME);
	}

	
	private void sleep(long time)
	{
		try
		{
			Thread.sleep(time);
		}
		catch (InterruptedException e)
		{
			;
		}
	}
	
	
	private Random myRandom = new Random();
	
	
	private Random getRandom()
	{
		return myRandom;
	}

	public void run()
	{
		try
		{
			mainLoop();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void mainLoop() throws MultiMonException, ChannelException
	{
		int count = 0;

		getTransmitter().open(getName());
		
		while (keepGoing())
		{
			sleep(nextPauseTime());			
			getTransmitter().send(nextMessage());
			count++;
		}
		
		getTransmitter().close();
	}
	
	private String nextMessage()
	{
		int index = getRandom().nextInt(MESSAGES.length);
		return MESSAGES[index];
	}

	public void shutDownThread()
	{
		setKeepGoing(false);
	}

	public void setPauseTime(long pauseTime)
	{
		myPauseTime = pauseTime;
	}
	
	public long getPauseTime()
	{
		return myPauseTime;
	}
	
	public long nextPauseTime()
	{
		long next = getRandom().nextLong();
		if (0 > next)
			next = -1 * next;
		
		next = next % getPauseTime();
		next++;
		
		return next;
	}
	
	public static final String[] MESSAGES = {
		"How goes?",
		"Anyone know of a good web browser?",
		"Wazzup?",
		"Wazzzzzzzup?",
		"Wasabi!",
		"Have you ever thought about the benefits of owning a really good encylopedia?",
		"What's the weather like out there?",
		"Can you loan me $10?",
		"My mom let's me driver her Jetta!",
		"(pssst) Wanna cyber?",
		"ASL?",
		"So, like, I was at the mall, like, ya know?  And so, like, ...",
		"A duck walks into a bar, orders a drink and says: 'put it on my bill!'",
		"Huh?",
		"Are we there yet?",
		"Ya ever wonder what those long distance savings come out to per foot?",
		"You ever notice that dog spelled forward is still 'dog?'",
		"Have I ever told you about gyroscopes?",
		"Biodiesel: it's the fuel of the future!",
		"Algae farms are where it's at!",
		"You know, a mitochondrion functions a lot like a fuel cell...",
		"You *can* have a triangle with 3 90 degree angles, it just has to be on a curved surface!",
		"Where would you put a klien's bottle?",
		"I work where there are tiny, twisty cubicles, all alike.",
		"Did you know that DSL uses ATM?",
		"Did you know that the original Diesel engine ran on peanut oil?",
		"Between JNI and GCC, it's a wonder that I'm SIG.",
		"Fool me once, shame on you.  Fool me twice, you're a politician",
		"A typical atomic radius is something like 1E-11m",
		"I'm hungry!",
		"Stop looking at me like that!",
		"What did you just say?",
		"There can be only 1,000!",
		"Is it time to go home yet?",
		"Bah!  I liked Microsoft IM better than this!",
		"Is it too late to go back to AIM?",
		"Who wrote this thing anyways?",
		"Oh shut up.",
		
		
	};
	

}
