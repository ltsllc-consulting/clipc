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
 * Provide native methods required by the FIFONative class.
 */

#include <windows.h>
#include <stdio.h>
#include "FIFOImpl.h"
#include "FIFOResult.h"
#include "FIFOResult.h"


#define MAX_TIMEOUT 0x7FFFFFFF
#define HANDLE_TYPE int

static int methodsInitialized = 0;

static jfieldID fieldHandle;
static jfieldID fieldResultCode;
static jfieldID fieldErrorCode;
static jfieldID fieldByteCount;
static jfieldID fieldServer;
static jfieldID fieldSyncObject;

#define NAMED_PIPE_RESULT "com/lts/ipc/fifo/FIFOResult"

void
FIFOResult_initialize(JNIEnv *env)
{
	jclass nprClass;

	if (methodsInitialized)
	{
		return;
	}
	else
	{
		methodsInitialized = 1;
	}

	nprClass = (*env)->FindClass(env, NAMED_PIPE_RESULT);

	fieldHandle = (*env)->GetFieldID(env, nprClass, "handle", "J");
	fieldResultCode = (*env)->GetFieldID(env, nprClass, "resultCode", "I");
	fieldErrorCode = (*env)->GetFieldID(env, nprClass, "errorCode", "I");
	fieldByteCount = (*env)->GetFieldID(env, nprClass, "byteCount", "I");
	fieldServer = (*env)->GetFieldID(env, nprClass, "server", "Z");
	fieldSyncObject = (*env)->GetFieldID(env, nprClass, "syncObject", "J");
}


void
FIFOResult_SetHandle(JNIEnv *env, jobject result, HANDLE handle)
{
	jlong value;
	DWORD dword;

	dword = (DWORD) handle;
	value = dword;
	(*env)->SetLongField(env, result, fieldHandle, value);
}


void
FIFOResult_SetResultCode(JNIEnv *env, jobject result, jint value)
{
	(*env)->SetIntField(env, result, fieldResultCode, value);
}


void
FIFOResult_SetErrorCode(JNIEnv *env, jobject result, DWORD errorCode)
{
	jint value;

	value = (jint) errorCode;
	(*env)->SetIntField(env, result, fieldErrorCode, value);
}


void
FIFOResult_SetByteCount(JNIEnv *env, jobject result, DWORD byteCount)
{
	jint value = (jint) byteCount;
	(*env)->SetIntField(env, result, fieldByteCount, value);
}

int
FIFOResult_isServer(JNIEnv *env, jobject result)
{
	jboolean value = (*env)->GetBooleanField(env, result, fieldServer);
	return (int) value;
}

jlong
FIFOResult_GetSyncObject(JNIEnv *env, jobject result)
{
	jlong value = (*env)->GetLongField(env, result, fieldSyncObject);
	return value;
}


void
FIFOResult_SetSyncObject(JNIEnv *env, jobject result, jlong value)
{
	(*env)->SetLongField(env, result, fieldSyncObject, value);
}

