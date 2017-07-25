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
 * SemaphoreResult.h
 *
 *  Created on: Mar 5, 2009
 *      Author: cnh
 */
#include <jni.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <semaphore.h>

#ifndef SEMAPHORERESULT_H_
#define SEMAPHORERESULT_H_

extern void SemaphoreResult_setResult(JNIEnv *env, jobject this, int code);
extern void SemaphoreResult_setHandle(JNIEnv *env, jobject this, sem_t *handle);
extern void SemaphoreResult_initialize(JNIEnv *env);

#endif /* SEMAPHORERESULT_H_ */
