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





abstract public class TestClass
{
	abstract protected void performTest() throws TestException;
	
	public static final int DEFAULT_TEST_GOAL = 1;
	public static final int DEFAULT_ITERATION_GOAL = 10;
	public static final long DEFAULT_DURATION = 10000;
	private static final int DEFAULT_BUFFER_SIZE = 1024;
	public static final int DEFAULT_REPORT_INTERVAL = 10000;
	
	private long myNextReport;
	
	public long getNextReport()
	{
		return myNextReport;
	}
	
	public void setNextReport(long nextReport)
	{
		myNextReport = nextReport;
	}
	
	public void updateNextReport()
	{
		long temp = System.currentTimeMillis();
		temp = temp + getReportInterval();
		setNextReport(temp);
	}
	
	private int myIndex;

	public int getIndex()
	{
		return myIndex;
	}

	public void setIndex(int index)
	{
		myIndex = index;
	}

	public void advance(int amount)
	{
		setIndex(getIndex() + amount);
	}

	private String[] myArgv;
	private long myIterationGoal = DEFAULT_ITERATION_GOAL;
	private int myBufferSize = DEFAULT_BUFFER_SIZE;
	private long myIterations;
	protected long myStartTime;
	protected long myStopTime;
	protected long myDurationGoal = DEFAULT_DURATION;
	protected long myTargetStopTime;
	private String myName;
	protected byte[] myBuffer;
	private boolean myContinuousTest;
	private int myReportInterval = DEFAULT_REPORT_INTERVAL;

	public String getArg(int index)
	{
		return myArgv[index];
	}

	public String getArg()
	{
		return getArg(getIndex());
	}

	public String getArgOffset(int offset)
	{
		return getArg(offset + getIndex());
	}
	
	public String[] getArgv()
	{
		return myArgv;
	}
	
	public int getNumberOfArguments()
	{
		return myArgv.length;
	}
	
	public int getArgCount()
	{
		return getNumberOfArguments();
	}
	
	public boolean hasNextArg()
	{
		int index = 1 + getIndex();
		return index < getArgv().length;
	}

	public void setArgv(String[] argv)
	{
		myArgv = argv;
	}

	public void execute(String[] argv)
	{
		try
		{
			processArguments(argv);
			performTest();
		}
		catch (TestException e)
		{
			e.printStackTrace(System.err);
		}
	}

	protected void processArguments(String[] argv)
	{
		try
		{
			setArgv(argv);
			setIndex(0);
			while (getIndex() < argv.length)
			{
				processArgument(getArg());
				advance(1);
			}
		}
		catch (ArgumentException e)
		{
			System.err.println("Error processing command line arguments");
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	protected void processArgument(String argument) throws ArgumentException
	{
		if (!argument.startsWith("-"))
		{
			processArgumentNormal(argument);
		}
		else
		{
			if (argument.length() < 1)
				throw new ArgumentException("Invalid argument: -");
				
			char c = argument.charAt(1);
			processSwitch(c);
		}
	}
	
	
	protected void processArgumentNormal(String argument) throws ArgumentException
	{}

	protected String getNextArg()
	{
		if (!hasNextArg())
		{
			return null; 
		}
		else
		{
			String[] argv = getArgv();
			String value = argv[1 + getIndex()];
			return value;
		}
	}
	
	
	protected int nextArgInt() throws ArgumentException
	{
		int value = -1;
		String s = getNextArg();
		if (null != s)
		{
			try
			{
				value = Integer.parseInt(s);
			}
			catch (NumberFormatException e)
			{
				throw new ArgumentException("Invalid argument, expected integer, got: " + s);
			}
		}
		
		return value;
	}

	protected void setIterationGoal(long value)
	{
		myIterationGoal = value;
	}

	public long getIterationGoal()
	{
		return myIterationGoal;
	}
	
	
	public long getIterations()
	{
		return myIterations;
	}
	
	public void setIterations(long iterations)
	{
		myIterations = iterations;
	}
	
	public void incrementIterations()
	{
		myIterations++;
	}

	public void setBufferSize(int bufferSize)
	{
		myBufferSize = bufferSize;
	}

	public int getBufferSize()
	{
		return myBufferSize;
	}

	protected long getDuration()
	{
		return getStopTime() - getStartTime();
	}

	protected long getDurationGoal()
	{
		return myDurationGoal;
	}

	protected long getTargetStopTime()
	{
		return myTargetStopTime;
	}

	public long getStartTime()
	{
		return myStartTime;
	}

	public long getStopTime()
	{
		return myStopTime;
	}

	public long getIterationCount()
	{
		return getIterations();
	}

	public void setIterationCount(long count)
	{
		myIterations = count;
	}
	
	public void resetIterations()
	{
		setIterationCount(0);
	}
	
	
	protected void setTargetStopTime(long targetTime)
	{
		myTargetStopTime = targetTime;
	}

	public void setStartTime(long startTime)
	{
		myStartTime = startTime;
	}

	public void setStopTime(long stopTime)
	{
		myStopTime = stopTime;
	}

	protected void setDurationGoal(long duration)
	{
		myDurationGoal = duration;
	}

	protected boolean metDurationGoal()
	{
		return getTargetStopTime() < System.currentTimeMillis();
	}

	protected void iterationReport()
	{
		long now = System.currentTimeMillis();
		now = now % 1000000;
		now = now /1000;
		
		IterationData idata = new IterationData();
		idata.setCount(getIterationCount());
		idata.setDuration(getDuration());
		
		String msg =
			now 
			+ " " + IterationData.formatInverse(getIterationCount(), getDuration())
			+ ", " + idata.toBandwidthReport();
		
		System.out.println(msg);
	}

	protected void processSwitch(char c) throws ArgumentException
	{
		c = Character.toLowerCase(c);
		switch (c)
		{
			case 'i' :
				setIterationGoal(nextArgInt());
				advance(1);
				break;
				
			case 'b' :
				setBufferSize(nextArgInt());
				advance(1);
				break;
				
			case 'd' :
				setDurationGoal(nextArgInt());
				advance(1);
				break;	
				
			case 'n' :
				setName(getNextArg());
				advance(1);
				break;
				
			case 'c' :
				setContinuousTest(true);
				break;
				
			case 'r' :
				setReportInterval(nextArgInt());
				advance(1);
				break;
		}
	}

	public void setReportInterval(int reportInterval)
	{
		myReportInterval = reportInterval;
	}
	
	public int getReportInterval()
	{
		return myReportInterval;
	}

	public void setContinuousTest(boolean continuousTest)
	{
		myContinuousTest = continuousTest;
	}
	
	public boolean continuousTest()
	{
		return myContinuousTest;
	}

	public String getName()
	{
		return myName;
	}

	public void setName(String s)
	{
		myName = s;
	}

	protected void noteStopTime()
	{
		setStopTime(System.currentTimeMillis());
	}

	protected void noteStartTime()
	{
		long time = System.currentTimeMillis();
		setStartTime(time);
		setTargetStopTime(time + getDurationGoal());
	}

	protected Bandwidth generateStatistics()
	{
		Bandwidth band = new Bandwidth();
		long total = getIterationCount() * getBuffer().length;
		band.setByteCount(total);
		band.setDuration(getDuration());
		
		return band;
	}
	
	
	protected void bandwidthReport()
	{
		Bandwidth band = generateStatistics();
		
		long now = System.currentTimeMillis();
		now = now % 1000000;
		now = now / 1000;
		
		String msg =
			now
			+ " " + band.toBandwidthReport("wrote ");
		System.out.println(msg);
	}

	public byte[] getBuffer()
	{
		return myBuffer;
	}
	
	public void setBuffer(byte[] buffer)
	{
		myBuffer = buffer;
	}
}
