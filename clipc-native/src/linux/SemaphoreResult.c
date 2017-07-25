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
 * Accessor methods for the SemaphoreResult class.
 *
 *  Created on: Mar 5, 2009
 *      Author: cnh
 */

#include <jni.h>
#include <errno.h>

#include "SemaphoreResult.h"

static jfieldID resultField;
static jfieldID handleField;
static jfieldID errorCodeField;
static jfieldID resultCodeField;

#define CLASS_NAME "com/lts/ipc/semaphore/SemaphoreResult"
#define RESULT_SIGNATURE "Lcom/lts/ipc/semaphore/SemaphoreResult$Results;"

void SemaphoreResult_initialize(JNIEnv *env)
{
	jclass clazz;

	clazz = (*env)->FindClass(env, CLASS_NAME);

	handleField = (*env)->GetFieldID(env, clazz, "handle", "J");
	resultCodeField = (*env)->GetFieldID(env, clazz, "resultCode", "I");
	errorCodeField = (*env)->GetFieldID(env, clazz, "errorCode", "I");
	resultField = (*env)->GetFieldID(env, clazz, "result", RESULT_SIGNATURE);
}


void SemaphoreResult_setResult(JNIEnv *env, jobject this, int rawCode)
{
	jobject tag;

	(*env)->SetObjectField(env, this, resultField, tag);
	(*env)->SetIntField(env, this, errorCodeField, rawCode);
}


void SemaphoreResult_setHandle(JNIEnv *env, jobject this, sem_t *handle)
{
	jlong value;

	value = (jlong) ((int) handle);
	(*env)->SetLongField(env, this, handleField, value);
}

