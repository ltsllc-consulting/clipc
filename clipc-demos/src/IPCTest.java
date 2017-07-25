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
import com.lts.ipc.Utils;
import com.lts.ipc.test.BusyTest;
import com.lts.ipc.test.TestClass;
import com.lts.ipc.test.fifo.NamedPipeTest;
import com.lts.ipc.test.semaphore.ClientServerTest;
import com.lts.ipc.test.semaphore.SemaphoreTest;
import com.lts.ipc.test.sharedmemory.SharedMemoryTest;
import com.lts.ipc.test.sockets.SocketTest;

/**
 * The entry point for tests for the CLIP library.
 * 
 * <P>
 * JUnit in its present form cannot be used to test CLIP because it is oriented
 * around single processes.  Therefore, this class is used as an entry point into 
 * test programs.
 * </P>
 * 
 * @author cnh
 *
 */
public class IPCTest
{
	private enum SubTests
	{
		Socket,
		NamedPipe,
		Semaphore,
		SharedMemory,
		SMSEM,
		BusyTest;
		
		public static SubTests valueOfIgnoreCase(String s)
		{
			return (SubTests) Utils.toValueIgnoreCase(SubTests.values(), s); 
		}
	}
	
	
	private static final String USAGE = "usage: IPCTest <subtest>";

	static String[] removeFirst (String[] argv)
	{
		String[] result = new String[argv.length - 1];
		for (int i = 1; i < argv.length; i++)
		{
			result[i-1] = argv[i];
		}
		
		return result;
	}
	
	
	public static void sometest()
	{
	}
	
	public static void main (String[] argv)
	{
		if (argv.length < 1)
		{
			System.err.println(USAGE);
			return;
		}
		
		String[] newArgv = removeFirst(argv);
		
		SubTests subTest = SubTests.valueOfIgnoreCase(argv[0]);
		switch(subTest)
		{
			case NamedPipe :
			{
				ClientServerTest test = new NamedPipeTest();
				test.execute(newArgv);
				break;
			}
				
			case Semaphore :
			{
				TestClass test = new SemaphoreTest();
				test.execute(newArgv);
				break;
			}
				
			case SharedMemory :
			{
				TestClass test = new SharedMemoryTest();
				test.execute(newArgv);
				break;
			}
			
			case SMSEM :
			{
				System.err.println("not implemented");
				break;
			}
			
			case Socket :
			{
				SocketTest test = new SocketTest();
				test.execute(newArgv);
				break;
			}
			
			case BusyTest :
			{
				TestClass test = new BusyTest();
				test.execute(newArgv);
				break;
			}
				
			default :
			{
				System.err.println("Unrecognized sub-test: " + subTest);
				break;
			}
		}
		
		System.exit(0);
	}
}
