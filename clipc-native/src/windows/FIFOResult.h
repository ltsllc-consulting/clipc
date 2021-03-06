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
 * Utility routines for setting the fields in FIFOResult
 */
#ifndef _Included_com_lts_ipc_fifo_FIFOResult
#define _Included_com_lts_ipc_fifo_FIFOResult
#ifdef __cplusplus
extern "C" {
#endif


#undef com_lts_ipc_fifo_FIFOResult_ERROR_UNKNOWN
#define com_lts_ipc_fifo_FIFOResult_ERROR_UNKNOWN -1L
#undef com_lts_ipc_fifo_FIFOResult_SUCCESS
#define com_lts_ipc_fifo_FIFOResult_SUCCESS 0L
#undef com_lts_ipc_fifo_FIFOResult_ERROR_ACCESS_DENIED
#define com_lts_ipc_fifo_FIFOResult_ERROR_ACCESS_DENIED 1L
#undef com_lts_ipc_fifo_FIFOResult_ERROR_INVALID_HANDLE
#define com_lts_ipc_fifo_FIFOResult_ERROR_INVALID_HANDLE 2L
#undef com_lts_ipc_fifo_FIFOResult_ERROR_PIPE_BUSY
#define com_lts_ipc_fifo_FIFOResult_ERROR_PIPE_BUSY 3L
#undef com_lts_ipc_fifo_FIFOResult_ERROR_NOT_FOUND
#define com_lts_ipc_fifo_FIFOResult_ERROR_NOT_FOUND 4L
#undef com_lts_ipc_fifo_FIFOResult_ERROR_PIPE_CLOSED
#define com_lts_ipc_fifo_FIFOResult_ERROR_PIPE_CLOSED 5L
#undef com_lts_ipc_fifo_FIFOResult_ERROR_CONNECT
#define com_lts_ipc_fifo_FIFOResult_ERROR_CONNECT 6L
#undef com_lts_ipc_fifo_FIFOResult_ERROR_READ
#define com_lts_ipc_fifo_FIFOResult_ERROR_READ 7L
#undef com_lts_ipc_fifo_FIFOResult_TIMEOUT
#define com_lts_ipc_fifo_FIFOResult_TIMEOUT 8L



extern void
FIFOResult_SetHandle(JNIEnv *env, jobject result, HANDLE handle);

extern void
FIFOResult_SetResultCode(JNIEnv *env, jobject result, jint value);

extern void
FIFOResult_SetErrorCode(JNIEnv *env, jobject result, DWORD errorCode);

extern void
FIFOResult_SetByteCount(JNIEnv *env, jobject result, DWORD byteCount);

extern int
FIFOResult_isServer(JNIEnv * env, jobject result);

extern void
FIFOResult_initialize(JNIEnv *env);

extern jlong
FIFOResult_GetSyncObject(JNIEnv *env, jobject result);

extern void
FIFOResult_SetSyncObject(JNIEnv *env, jobject result, jlong value);

#ifdef __cplusplus
}
#endif
#endif
