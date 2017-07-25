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

public class Bandwidth
{
	public static final long KBYTE = 1024;
	public static final long MBYTE = KBYTE * 1024;
	public static final long GBYTE = MBYTE * 1024;
	public static final long TBYTE = GBYTE * 1024;
	
	private double myUnitsPerSecond;
	private long myBytesPerUnit;
	private long myByteCount;
	private long myDuration;
	private String myUnitSuffix;
	
	private boolean myNeedsUpdate = true;
	private DecimalFormat myDecimalFormat;
	private double myBytesPerSecond;
	
	public long getByteCount()
	{
		return myByteCount;
	}
	
	public void setByteCount(long byteCount)
	{
		myByteCount = byteCount;
		setNeedsUpdate(true);
	}
	
	public long getDuration()
	{
		return myDuration;
	}
	
	public void setDuration(long duration)
	{
		myDuration = duration;
		setNeedsUpdate(true);
	}
	
	public boolean needsUpdate()
	{
		return getNeedsUpdate();
	}
	
	public boolean getNeedsUpdate()
	{
		return myNeedsUpdate;
	}
	
	public void setNeedsUpdate(boolean value)
	{
		myNeedsUpdate = value;
	}
	
	
	public String toBytesPerSec(long count, long msec)
	{
		setByteCount(count);
		setDuration(msec);
		
		update();
		
		StringBuilder sb = new StringBuilder();
		
		Bandwidth band = toBandwidth(count, msec);
		DecimalFormat format = new DecimalFormat("0.##");
		sb.append(format.format(band.myUnitsPerSecond));
		sb.append(band.myUnitSuffix);
		sb.append("/sec");
		
		return sb.toString();
	}
	
	
	public void update()
	{
		if (!needsUpdate())
			return;
		
		long count = getByteCount();
		long duration = getDuration();
		
		updateBytesPerSecond(count, duration);
		updateBpuAndSuffix(getBytesPerSecond());
		updateUnitsPerSecond(getBytesPerSecond(), getBytesPerUnit());
		updateDecimalFormat(getUnitsPerSecond());
		
		setNeedsUpdate(false);
	}

	public double getBytesPerSecond()
	{
		return myBytesPerSecond;
	}

	private void updateBytesPerSecond(long count, long duration)
	{
		double dcount = count;
		double msec = duration;
		double bytesPerSecond = dcount / msec;
		bytesPerSecond = bytesPerSecond * (double) 1000;
		
		setBytesPerSecond(bytesPerSecond);
	}

	private void setBytesPerSecond(double bytesPerSecond)
	{
		myBytesPerSecond = bytesPerSecond;
	}

	private void updateUnitsPerSecond(double bytesPerSecond, long bytesPerUnit)
	{
		double ups = bytesPerSecond / bytesPerUnit;
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

	private void updateBpuAndSuffix(double bytesPerSecond)
	{
		long bytesPerUnit;
		String suffix;
		
		if (bytesPerSecond < KBYTE)
		{
			bytesPerUnit = 1;
			suffix = "bytes";
		}
		else if (bytesPerSecond < MBYTE)
		{
			bytesPerUnit = KBYTE;
			suffix = "kB";
		}
		else if (bytesPerSecond < GBYTE)
		{
			bytesPerUnit = MBYTE;
			suffix = "MB";
		}
		else if (bytesPerSecond < TBYTE)
		{
			bytesPerUnit = GBYTE;
			suffix = "GB";
		}
		else
		{
			bytesPerUnit = TBYTE;
			suffix = "TB";
		}
		
		setBytesPerUnit(bytesPerUnit);
		setUnitSuffix(suffix);
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
	
	
	/**
	 * Calculate the bytes per second in a format like megabytes or kilobytes per
	 * second.
	 * 
	 * <P>
	 * The object returned has 
	 * @param byteCount
	 * @param msec
	 * @return 
	 */
	public Bandwidth toBandwidth(long count, long msec)
	{
		Bandwidth band = new Bandwidth();
		
		
		return band;
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


	public String toByteUnitsPerSecond(long byteCount, long duration)
	{
		Bandwidth band = toBandwidth(byteCount, duration);
		DecimalFormat format = getFormatFor(band.myUnitsPerSecond);
		
		StringBuilder sb = new StringBuilder();
		sb.append(format.format(band.myUnitsPerSecond));
		sb.append(band.myUnitSuffix);
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
	
	
	public String toBandwidthReport(String prefix)
	{
		update();
		
		String msg = 
			prefix + formatUnitsPerSecond() + " (" + getByteCount() + " in " + getDuration() + "msec)"; 
		
		return msg;
	}
	
	
	public String getUnitsPerSecondString()
	{
		return formatUnitsPerSecond();
	}

	public long getBytesPerUnit()
	{
		return myBytesPerUnit;
	}

	public void setBytesPerUnit(long bytesPerUnit)
	{
		myBytesPerUnit = bytesPerUnit;
	}

	public String getUnitSuffix()
	{
		return myUnitSuffix;
	}

	public void setUnitSuffix(String unitSuffix)
	{
		myUnitSuffix = unitSuffix;
	}
}