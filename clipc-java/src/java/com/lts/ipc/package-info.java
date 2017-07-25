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
 * <P>
 * A package that supports interprocess communications.
 * </P>
 *
 * <H2>Table of Contents</H2>
 * <UL>
 * <LI><A href="#intro">Introduction</A></LI>
 * <LI><A href="#named_pipe">Named Pipes</A></LI>
 * <LI><A href="#semaphore">Semaphores</A></LI>
 * <LI><A href="#shared_memory">Shared Memory</A></LI>
 * <LI><A href="#socket">Sockets</A></LI>
 * </UL>
 * 
 * <A name="intro"><H2>Introduction</H2></A>
 * This package documents the CLIP library (com.lts.ipc).  CLIP provides support
 * for communication primitives that do not exist in the JRE, such as semaphores
 * and named pipes, or improve support for primitives that exist but may not be 
 * as clear, such as shared memory.
 * 
 * <A name="named_pipe"><H2>Named Pipes</H2></A>
 * Named Pipes also refer to FIFOs or Message Queues.  In the context of CLIP, a 
 * named pipe is an one-way IPC mechanism that appears on the underlying file system.
 * 
 * <P>
 * In terms of speed and bandwith, named pipes appear to fall somewhere between 
 * sockets and shared memory.
 * </P>
 * 
 * <P>
 * The underlying mechanism may or may not directly use the file created by this 
 * class.  On Linux, for example, the file is the actual named pipe --- a FIFO.
 * On Windows, the file is the logical name for the named pipe.  The actual name 
 * exists in a Windows specific namespace.
 * </P>
 * 
 * <A name="semaphore"><H2>Semaphores</H2></A>
 * A CLIP {@link com.lts.ipc.semaphore.Semaphore}
 * is the traditional synchronization primitive that allow processes to 
 * signal each other.  The CLIP implementation is an "n-ary" semaphore that also 
 * a file for use in allowing processes to identify the semaphore.
 * 
 * <P>
 * CLIP semaphores appear to be the faster than the other primitives in terms of 
 * synchronization, but of course do not provide any data exchange beyond that.
 * </P>
 * 
 * <A name="shared_memory"><H2>Shared Memory</H2></A>
 * The {@link com.lts.ipc.sharedmemory.SharedMemory} 
 * class is a more user-friendly implementation of the JRE 
 * mechanism for accessing shared memory: {@link java.io.RandomAccessFile},
 * {@link java.nio.channels.FileChannel} and {@link java.nio.MappedByteBuffer}.
 * 
 * <P>
 * Should the JRE classes end up using some other mechanism that is unstuitable
 * for for IPC, the intention is to change the CLIP version so that it keeps 
 * supporting shared memory.
 * </P>
 * 
 * <P>
 * CLIP shared memory appears to have the greatest bandwidth of all the CLIP 
 * IPC mechanisms.
 * </P>
 * 
 * <P>
 * CLIP shared memory exploits the underlying JRE support for synchronization 
 * via file locking to provide synchronization.  This implementation appears
 * to be roughly equal to the speed of named pipes and sockets, but significantly
 * slower than that provided by semaphores.
 * </P>
 * 
 * <A name="socket"><H2>Sockets</H2></A>
 * The CLIP {@link com.lts.ipc.socket} package is a collection of utilities for 
 * use with regular JRE sockets.  CLIP does not try to provide a replacement for 
 * JRE sockets since they are good enough as is.
 * 
 * <A name="file_naming"><H2>File Naming</H2></A>
 * CLIP uses files and file names to allow processes to identify instances of 
 * various IPC mechanisms that do not have a standard means for such identification
 * across supported platforms.  When creating an instance of Semaphore or 
 * NamedPipe, for example, the developer supplies a file name to identify it.
 * 
 * <P>
 * The idea is that all processes that use the same file name will be connected to 
 * the same mechanism.  Two processes that both use "/foo/bar" as the name with
 * the Semaphore class, for example, will be connected to the same semaphore.
 * </P>
 * 
 * <P>
 * The use of file names is functional as well as symbolic.  On Linux and Windows,
 * the name supplied is created and populated with the underlying name or handle 
 * that is used to access the primitive.
 * </P>
 * 
 * <P>
 * For example, on Windows the file name provided for a shared queue will contain 
 * the actual name that windows uses to identify the queue.  For semaphores, the 
 * Windows file will contain an integer value that is passed to semaphore-related 
 * operations to identify the semaphore being operated on.
 * </P>
 * 
 * <P>
 * The manner in which files are used is similar to the way that shared memory 
 * operates.
 * </P>
 * 
 * @see com.lts.ipc.namedpipe.NamedPipe
 * @see com.lts.ipc.semaphore.Semaphore
 * @see com.lts.ipc.sharedmemory.SharedMemory
 */
package com.lts.ipc;

