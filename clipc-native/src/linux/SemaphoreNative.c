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
#include <sys/sem.h>
#include <stdio.h>
#include <fcntl.h>
#include <errno.h>

#include "SemaphoreNative.h"
#include "SemaphoreResult.h"

//
// The neighbor of the beast
//
#define PROJID 668

static int performSemop(JNIEnv *env, jobject resultObject, int semid, int value);
static void setResults(int outcome, JNIEnv *env, jobject result, int semid);

/**
 * Convert a Java supplied string to a key_t that is suitable for use in identifying
 * a semaphore.
 *
 * The function uses the constant PROJID in the ftok function to come up with the
 * identifying value.
 */
static key_t
jstringToKey(JNIEnv *env, jstring s)
{
	char name[512];
	int length;
	key_t key;


	length = (*env)->GetStringLength(env, s);
	(*env)->GetStringUTFRegion(env, s, 0, length, name);
	key = ftok(name, PROJID);

	return key;
}


/**
 * Perform an increment/decrement/wait operation.
 *
 * This method performs one of three operations, depending on the value of the
 * value parameter.
 *
 * If value is zero, then the process will block until the semaphore becomes zero
 * as well.  If the semaphore is already zero, then the function returns immediately.
 *
 * If value is one or more, then the process will increase the semaphore by value.
 * This may cause processes that are waiting for the semaphore to be woken.
 *
 * If the value is less than zero, the process will attempt to decrease the semaphore.
 * If the result of this would cause the semaphore to become less than 0, the caller
 * will block.
 *
 * PARAMETERS
 * env
 * 		A pointer to the JNI environment.  This is required to set the values of the
 * 		resultObject parameter.
 *
 * resultObject
 * 		An instance of SemaphoreResult whose fields will be set based on the results
 * 		of this function.
 *
 * semid
 * 		An identifier for the semaphore being operated on.
 *
 * value
 * 		The value being used in the operation as described above.
 *
 * RETURN VALUE
 *
 * The function returns 0 for success, -1 for failure.
 */
static int performSemop(JNIEnv *env, jobject resultObject, int semid, int value)
{
	struct sembuf buffer;
	int result;

	buffer.sem_num = 0;
	buffer.sem_op = value;
	buffer.sem_flg = 0;

	result = semop(semid, &buffer, 1);
	setResults(result, env, resultObject, semid);
	return result;
}

/**
 * Connect to an existing semaphore.
 *
 * This function simply tries to connect to the semaphore specified by the key parameter.
 * It recodes the outcome in the supplied result object.
 */
static int connect(JNIEnv *env, jobject result, key_t key)
{
	int flags;
	int semid;

	flags = 0660;
	semid = semget(key, 1, flags);
	setResults(semid, env, result, semid);
	return semid;
}


/**
 * Set the contents of a SemaphoreResult object depending on the values passed into
 * this function.
 *
 * If the result code signifies failure (-1), then set the result code in the result
 * object to the value of errno, and set the value of the handle field to -1.
 *
 * Otherwise, set the result field in the result object to 0 and set the handle field
 * to the value of the semaphore ID.
 *
 * PARAMS
 *
 * outcome
 * 		Whether the caller wants to signify success or failure.  A value of -1 signifies
 * 		failure, while any other code signifies success.
 *
 * env
 * 		The JNI environment.  Required to be able to set the fields of the result object.
 *
 * result
 * 		The result object.  This is the thing that the function sets.
 *
 * semid
 * 		The identifier for a semaphore.  In success cases, this should be a non-
 * 		negative integer value.
 */
static void setResults(int outcome, JNIEnv *env, jobject result, int semid)
{
	if (-1 == outcome)
	{
		SemaphoreResult_setResult(env, result, errno);
		SemaphoreResult_setHandle(env, result, -1);
	}
	else
	{
		SemaphoreResult_setResult(env, result, 0);
		SemaphoreResult_setHandle(env, result, semid);
	}
}

/**
 * Attempt to create and initialize a semaphore.
 *
 * This function will try to create a new semaphore.  On successfully creating
 * a semaphore, the function will set the initial value of the semaphore to the
 * value passed to the function.
 *
 * The function will fail if the semaphore already exists.  It does not try to
 * do anything else in such a failure scenario, though it will set the contents
 * of the result object in such a situation.
 *
 * PARAMETERS
 * env
 * 		A pointer to the JNI environment.  This is required when setting the
 * 		contents of the result object.
 *
 * result
 * 		A SemaphoreResult object that holds failure codes and the like if the
 * 		attempt to create a new semaphore fails.
 *
 * key
 * 		The key used to identify which semaphore to create or initialize.  This
 * 		is usually obtained via ftok.
 *
 * initialValue
 * 		The initial value for the semaphore.
 *
 * RETURN VALUE
 *
 * The function returns -1 to signal that it could not create the semaphore, or
 * that it could not initialize the semaphore once created.  On success, the
 * return value is the semaphore ID for the newly created semaphore.
 */
static int create(JNIEnv *env, jobject result, key_t key, int initialValue)
{
	int flags;
	int semid;
	int returnCode;

	//
	// Create the semaphore
	//
	flags = 0660; // r/w by user/group, nothing for others
	flags = flags | IPC_CREAT | IPC_EXCL;
	semid = semget(key, 1, flags);

	//
	// If we failed, stop here
	//
	if (-1 == semid)
	{
		setResults(semid, env, result, semid);
		returnCode = -1;
	}
	else
	{
		//
		// we succeeded in creating the semaphore.  Initialize it.
		//
		returnCode = performSemop(env, result, key, initialValue);
		setResults(returnCode, env, result, semid);
		if (returnCode != -1)
			returnCode = semid;
	}

	return returnCode;
}


/**
 * Connect to the semaphore, creating it if it does not already exist.
 */
JNIEXPORT void JNICALL
Java_com_lts_ipc_semaphore_SemaphoreNative_connect (
		JNIEnv *env,
		jclass clazz,
		jobject resultObject,
		jstring semaphoreName,
		jint maxValue,
		jint initialValue)
{
	key_t key;
	int semid;

	key = jstringToKey(env, semaphoreName);

	//
	// Try to create the semaphore.  If that succeeds then we are done.
	//
	semid = create(env, resultObject, key, initialValue);

	//
	// If the attempt fails because the semaphore already exists, then try to connect
	// to it.  Accept the outcode of the connect as the outcome to this method.
	//
	if (-1 == semid && EEXIST == errno)
	{
		semid = connect(env, resultObject, key);
	}
}

/**
 * Increment the semaphore, possibly causing processes that are waiting for it to
 * wake up.
 */
JNIEXPORT void JNICALL
Java_com_lts_ipc_semaphore_SemaphoreNative_increment (
		JNIEnv *env,
		jclass clazz,
		jobject result,
		jlong handle)
{
	int semid;

	semid = handle;
	performSemop(env, result, semid, 1);
}

/**
 * Decrement the semaphore, possibly causing the calling process to block waiting
 * for it to become available.
 */
JNIEXPORT void JNICALL
Java_com_lts_ipc_semaphore_SemaphoreNative_decrement (
		JNIEnv *env,
		jclass clazz,
		jobject result,
		jlong handle,
		jlong msecTimeout)
{
	int semid;

	performSemop(env, result, semid, -1);
}
