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
#include <sys/types.h>
#include <sys/ipc.h>
#include <jni.h>

//
// The neighbor of the beast
//
#define PROJID 668

/**
 * Convert a Java supplied string to a key_t that is suitable for use in identifying
 * a semaphore.
 *
 * The function uses the constant PROJID in the ftok function to come up with the 
 * identifying value.
 */
key_t jstringToKey(JNIEnv *env, jstring s)
{
	char name[512];
	int length;
	key_t key;


	length = (*env)->GetStringLength(env, s);
	(*env)->GetStringUTFRegion(env, s, 0, length, name);
	key = ftok(name, PROJID);

	return key;
}



