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
package com.lts.clipc.demo.lostupdate;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import com.lts.ipc.IPCException;
import com.lts.ipc.semaphore.Semaphore;
import com.lts.util.FileUtils;
import com.lts.util.ThreadUtils;


public class Update
{
	public static void main(String[] argv)
	{
		try
		{
			Update obj = new Update();
			
			if (argv.length < 1)
			{
				System.err.println("usage: update <account file> <semaphore file>");
				return;
			}

			Semaphore sem = null;
			if (argv.length > 1)
			{
				sem = new Semaphore(argv[1]);
			}
			
			File file = new File(argv[0]);
			
			try
			{
				if (null == sem)
					obj.run(file, new Random());
				else
					obj.run(sem, file, new Random());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		catch (IPCException e)
		{
			e.printStackTrace();
		}
	}
	
	private void sleep(int millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch (InterruptedException e)
		{
			;
		}
	}
	
	public void run(Semaphore sem, File file, Random r) throws Exception
	{
		ThreadUtils.sleep(r.nextInt(2000));
		
		sem.decrement();
		
		int balance = readBalance(file);
		
		sleep(r.nextInt(4000));
		
		int amount = generateTransactionAmount(r, balance);

		printMessage(balance, amount);
		
		balance = balance + amount;
		writeBalance(file, balance);
		
		sem.increment();
	}
	
	public void run(File file, Random r) throws Exception
	{
		ThreadUtils.sleep(r.nextInt(2000));
		int balance = readBalance(file);
		
		sleep(r.nextInt(4000));
		
		int amount = generateTransactionAmount(r, balance);

		printMessage(balance, amount);
		
		balance = balance + amount;
		writeBalance(file, balance);
	}
	
	
	

	private int generateTransactionAmount(Random r, int balance)
	{
		int amount = r.nextInt(100);
		
		if (r.nextBoolean())
		{
			//
			// do not allow withdraws that would take the balance below 0.  If 
			// that happens, just turn the withdraw into a deposit
			//
			if (amount <= balance)
			{
				amount = -1 * amount;
			}
		}
		return amount;
	}

	private void writeBalance(File file, int balance) throws IOException
	{
		String s;
		s = Integer.toString(balance);
		FileUtils.writeFile(file, s);
	}
	
	
	private void printMessage(int balance, int amount)
	{
		if (amount < 0)
		{
			System.out.println(balance + " - " + (-1 * amount) + " = " + (balance + amount));
		}
		else
		{
			System.out.println(balance + " + " + amount + " = " + (balance + amount));
		}
	}

	private int readBalance(File file) throws Exception
	{
		if (null == file)
		{
			throw new Exception("expected file name");
		}
		
		String s = FileUtils.readFile(file);
		if (null == s)
		{
			throw new Exception("empty or missing file " + file);
		}
		
		String[] fields = s.split(" |\t|\n|\r");
		if (fields.length < 1)
		{
			throw new Exception("missing or empty file " + file);
		}
		
		s = fields[0];
		int balance = Integer.parseInt(s);
		return balance;
	}
	
	
	public int run(Random r) throws IOException
	{
		int amount = r.nextInt(100);

		if (r.nextBoolean())
		{
			amount = -1 * amount;
		}
		
		return amount;
	}
	

	
}
