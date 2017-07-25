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
package com.lts.ipc.fifo;

/**
 * <P>
 * An internal class to hold the results of a call to a JNI method.
 * </P>
 * <H2>NOTE</H2>
 * <P>
 * This is an internal class and not intended for general use.
 * </P>
 * <H2>Description</H2>
 * <P>
 * This class is used to pass multiple results between the JNI layer and the Java layer of
 * named pipes. JNI calls can only return a single result, so to overcome that limitation,
 * an instance of this class is passed back instead.
 * </P>
 * <H2>Properties</H2>
 * <UL>
 * <LI>handle - an integer value the operating system uses to identify the named pipe</LI>
 * <LI>errorCode - a value the native method uses to communicate a problem with the
 * operation. The codes are defined by the ERROR_ constants that this class defines.</LI>
 * <LI>resultCode - The Java level result code. Generally a value of 0 means that the call
 * succeeded whereas a non-zero code means that there was a problem.</LI>
 * <LI>byteCount - For operations like read and write, this property specifies the number
 * of bytes actually read or written by the underlying operation.</LI>
 * </UL>
 * 
 * @author cnh
 */
public class FIFOResult
{
	public static final int ERROR_UNKNOWN = -1;

	public static final int SUCCESS = 0;

	public static final int ERROR_ACCESS_DENIED = 1;

	/**
	 * An attempt was made to perform some operation such as listening or writing to a
	 * named pipe when the client did not have a valid pipe handle.
	 */
	public static final int ERROR_INVALID_HANDLE = 2;

	/**
	 * An attempt was made to connect to a named pipe, but failed because all the
	 * instances were already connected to other clients.
	 */
	public static final int ERROR_PIPE_BUSY = 3;

	/**
	 * An attempt was made to open a file, but either a component of the path to the file
	 * or the file itself does not exist.
	 */
	public static final int ERROR_NOT_FOUND = 4;

	public static final int ERROR_PIPE_CLOSED = 5;
	
	/**
	 * An attempt to connect to a FIFO failed.
	 */
	public static final int ERROR_CONNECT = 6;
	
	/**
	 * A generic problem occurred while trying to read from the FIFO.
	 */
	public static final int ERROR_READ = 7;
	
	/**
	 * Timeout while waiting for something.
	 */
	public static final int TIMEOUT = 8;
	
	/**
	 * An error was encountered while making a call to wait for an IPC 
	 * mechanism to become ready (e.g., select or WaitForSingleObject).
	 */
	public static final int ERROR_SELECT = 9;

	public int resultCode;

	public long handle;

	public int errorCode;

	public int byteCount;
	
	/**
	 * Is the FIFO a server?
	 * 
	 * <P>
	 * A value of true indicates that whoever is using the FIFO is the server.
	 * A value of false means that the user is not the server.
	 * </P>
	 * 
	 * <P>
	 * This is only relevant for the Windows platform, where a different system 
	 * call must be used depending on whether this is the client or the server.
	 * </P>
	 */
	public boolean server;
	
	
	/**
	 * A windows-specific object required when using non-blocking FIFOs.
	 */
	public long syncObject;
}
