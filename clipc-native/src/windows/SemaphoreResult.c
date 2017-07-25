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
/*
 * SemaphoreResult.c
 *
 * Accessor methods for the SemaphoreResult class.
 *
 *  Created on: Mar 5, 2009
 *      Author: cnh
 */

#include <windows.h>
#include "SemaphoreResult.h"

static jfieldID resultCodeField;
static jfieldID handleField;
static jfieldID errorCodeField;

#define CLASS_NAME "com/lts/ipc/semaphore/SemaphoreResult"

void SemaphoreResult_initialize(JNIEnv *env)
{
	jclass clazz;

	clazz = (*env)->FindClass(env, CLASS_NAME);

	handleField = (*env)->GetFieldID(env, clazz, "handle", "J");
	errorCodeField = (*env)->GetFieldID(env, clazz, "errorCode", "I");
	resultCodeField = (*env)->GetFieldID(env, clazz, "resultCode", "I");
}



void SemaphoreResult_setResultCode(JNIEnv *env, jobject this, int code)
{
	(*env)->SetIntField(env, this, resultCodeField, (jint) code);
}


void SemaphoreResult_setErrorCode(JNIEnv *env, jobject this, int code)
{
	(*env)->SetIntField(env, this, errorCodeField, (int) code);
}


void SemaphoreResult_setHandle(JNIEnv *env, jobject this, int handle)
{
	jlong value;

	value = (jlong) ((int) handle);
	(*env)->SetLongField(env, this, handleField, value);
}
