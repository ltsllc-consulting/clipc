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
/**
 * 
 */
package com.lts.ipc.test;

import java.text.DecimalFormat;

public class IterationData
{
	public static final long KBYTE = 1024;
	public static final long MBYTE = KBYTE * 1024;
	public static final long GBYTE = MBYTE * 1024;
	public static final long TBYTE = GBYTE * 1024;
	
	private double myUnitsPerSecond;
	private long myDuration;
	private String myUnitSuffix;
	private long myCount;
	
	private boolean myNeedsUpdate = true;
	private DecimalFormat myDecimalFormat;
	private long myCountPerUnit;
	private double myCountPerSecond;
	
	public long getCount()
	{
		return myCount;
	}
	
	public void setCount(long count)
	{
		myCount = count;
		setNeedsUpdate(true);
	}
	
	public long getDurationMsec()
	{
		return myDuration;
	}
	
	public void setDuration(long duration)
	{
		myDuration = duration;
		setNeedsUpdate(true);
	}
	
	private boolean needsUpdate()
	{
		return getNeedsUpdate();
	}
	
	private boolean getNeedsUpdate()
	{
		return myNeedsUpdate;
	}
	
	private void setNeedsUpdate(boolean value)
	{
		myNeedsUpdate = value;
	}
	
	
	public String toLongString()
	{
		update();
		
		StringBuilder sb = new StringBuilder();
		DecimalFormat format = new DecimalFormat("0.##");
		
		sb.append(format.format(getUnitsPerSecond()));
		sb.append(getUnitSuffix());
		sb.append("/sec");
		
		return sb.toString();
	}
	
	
	private void update()
	{
		if (!needsUpdate())
			return;
		
		updateCountPerSecond(getCount(), getDurationMsec());
		updateCountPerUnit(getCountPerSecond());
		updateUnitsPerSecond(getCount(), getCountPerUnit(), getDurationMsec());
		updateDecimalFormat(getUnitsPerSecond());
		
		setNeedsUpdate(false);
	}

	private void updateCountPerSecond(long iterationCount, long msec)
	{
		double count = iterationCount;
		double sec = msec;
		sec = sec / 1000; // msec to sec
		
		myCountPerSecond = count / sec;
	}
	
	private void updateCountPerUnit (double countPerSecond)
	{
		if (countPerSecond < KBYTE)
		{
			myUnitSuffix = "";
			myCountPerUnit = 1;
		}
		else if (countPerSecond < MBYTE)
		{
			myUnitSuffix = "k";
			myCountPerUnit = KBYTE;
		}
		else if (countPerSecond < GBYTE)
		{
			myUnitSuffix = "M";
			myCountPerUnit = MBYTE;
		}
		else if (countPerSecond < TBYTE)
		{
			myUnitSuffix = "G";
			myCountPerUnit = GBYTE;
		}
		else 
		{
			myUnitSuffix = "T";
			myCountPerUnit = TBYTE;
		}
	}

	private static final long TRILLION = (long) 1E12;
	private static final long BILLION = (long) 1E9;
	private static final long MILLION = (long) 1E6;
	private static final long THOUSAND = (long) 1E3;
	
	public static String formatInverse (long count, long msec)
	{
		double sec = msec / 1000;
		double secPerIter = sec / count;
		long normalizedCount = (long) (count / sec);
		String suffix;
		
		double value;
		
		
		if (normalizedCount > BILLION)
		{
			value = secPerIter * TRILLION;
			suffix = "psec";
		}
		else if (normalizedCount > MILLION)
		{
			value = secPerIter * BILLION;
			suffix = "nsec";
		}
		else if (normalizedCount > THOUSAND)
		{
			value = secPerIter * MILLION;
			suffix = "usec";
		}
		else
		{
			value = secPerIter * THOUSAND;
			suffix = "msec";
		}
		
		
		DecimalFormat format = valueToFormat(value);
		
		String s = format.format(value) + " " + suffix;
		return s;
	}
	
	
	public static DecimalFormat valueToFormat(double value)
	{
		DecimalFormat format;
		
		if (value < 1)
		{
			format = new DecimalFormat("0.###");
		}
		else if (value < 10)
		{
			format = new DecimalFormat("0.##");
		}
		else 
		{
			format = new DecimalFormat("0");
		}
		
		return format;
	}
	
	
	private void updateUnitsPerSecond(long count, long countPerUnit, long msec)
	{
		double sec = msec;
		sec = sec / 1000;
		
		double ups = count;
		ups = ups/countPerUnit;
		ups = ups/sec;
		
		setUnitsPerSecond(ups);
	}

	private void updateDecimalFormat(double ups)
	{
		DecimalFormat format;
		
		if (ups < 10)
			format = new DecimalFormat("0.##");
		else if (ups < 100)
			format = new DecimalFormat("0.#");
		else 
			format = new DecimalFormat("0");
		
		setDecimalFormat(format);
	}

	public void setUnitsPerSecond(double ups)
	{
		myUnitsPerSecond = ups;
	}
	
	public double getUnitsPerSecond()
	{
		return myUnitsPerSecond;
	}

	public void setDecimalFormat(DecimalFormat format)
	{
		myDecimalFormat = format;
	}
	
	public DecimalFormat getDecimalFormat()
	{
		return myDecimalFormat;
	}

	public String toByteUnits(long count)
	{
		update();
		
		StringBuilder sb = new StringBuilder();
		sb.append(getDecimalFormat().format(getUnitsPerSecond()));
		sb.append(getUnitSuffix());
		return sb.toString();
	}
	
	
	public String bandwidthReport(long totalBytes, long totalMsec)
	{
		double sec = totalMsec;
		sec = sec / 1000;
		double dbytes = totalBytes;
		double total = dbytes / sec;
		
		DecimalFormat format = new DecimalFormat("0.##");
		return format.format(total);
	}


	public String toString()
	{
		update();
		DecimalFormat format = getFormatFor(getUnitsPerSecond());
		
		StringBuilder sb = new StringBuilder();
		sb.append(format.format(getUnitsPerSecond()));
		sb.append(getUnitSuffix());
		sb.append("/sec");
		
		return sb.toString();
	}


	public static DecimalFormat getFormatFor(double value)
	{
		DecimalFormat format;
		
		if (value < 10)
			format = new DecimalFormat("0.##");
		else if (value < 100)
			format = new DecimalFormat("0.#");
		else 
			format = new DecimalFormat("0");
		
		return format;
	}


	public String formatUnitsPerSecond()
	{
		StringBuffer sb = new StringBuffer();
		
		double value = getUnitsPerSecond();
		sb.append(getDecimalFormat().format(value));
		sb.append(getUnitSuffix());
		sb.append("/sec");
		
		return sb.toString();
	}
	
	
	public String toBandwidthReport()
	{
		update();
		
		String msg = 
			formatUnitsPerSecond() + " (" + getCount() + " in " + getDurationMsec() + "msec)"; 
		
		return msg;
	}
	
	
	public String toLatencyReport()
	{
		return formatInverse(getCount(), getDurationMsec());
	}

	public String getUnitSuffix()
	{
		return myUnitSuffix;
	}

	public void setUnitSuffix(String unitSuffix)
	{
		myUnitSuffix = unitSuffix;
	}

	public long getCountPerUnit()
	{
		return myCountPerUnit;
	}

	public void setCountPerUnit(long countPerUnit)
	{
		myCountPerUnit = countPerUnit;
	}

	public double getCountPerSecond()
	{
		return myCountPerSecond;
	}

	public void setCountPerSecond(double countPerSecond)
	{
		myCountPerSecond = countPerSecond;
	}
}