/*
 * @(#)FileAccess.c	1.5 98/03/28
 *
 * Copyright 1996-1998 by Sun Microsystems, Inc.,
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */


#include <stdio.h>
#include <stdlib.h>
#include "bsd-endian.h"
#include "jni-util.h"
#include "com_sun_tv_media_util_JMFProperties.h"


JNIEXPORT jint JNICALL
Java_com_sun_media_util_JMFProperties_nOpenFile(JNIEnv *env,
						jclass props,
						jstring filename,
						jint write)
{
    jchar *name;
    char *cName;
    int len, i;
    FILE *fp;
    len = (int)(*env)->GetStringLength(env, filename);
    cName = (char*) malloc((len+1) * sizeof(char));
    name = (jchar*) (*env)->GetStringChars(env, filename, NULL);

    for (i = 0; i < len && (char)name[i] > 31; i++)
	if ((char)name[i] > 31)
	    cName[i] = (char) name[i];
    cName[i] = 0;
    (*env)->ReleaseStringChars(env, filename, name);
    if (write == 0)
	fp = fopen(cName, "r");
    else
	fp = fopen(cName, "w");
    
    if (fp == NULL)
	return (jint)0;
    else
	return (jint)fp;
}

JNIEXPORT jstring JNICALL
Java_com_sun_media_util_JMFProperties_nReadLine(JNIEnv *env,
						jclass props,
						jint fid)
{
    char line[256];
    jstring string;
    if (fgets(line, 255, (FILE*)fid)) {
	/* create a java string */
	int k;
	/* Get rid of carriage return */
	if (k = strlen(line)) {
	    if (line[k - 1] < 31)
		line[k - 1] = 0;
	}

	string = (jstring) (*env)->NewStringUTF(env, (const char*) line);
	return string;
    } else
	return (jstring)NULL;
}

JNIEXPORT void JNICALL
Java_com_sun_media_util_JMFProperties_nWriteLine(JNIEnv *env,
				   jclass props,
				   jint fid,
				   jstring line)
{
    char *cLine = (char*) (*env)->GetStringUTFChars(env, line, NULL);
    fprintf((FILE*)fid, "%s\n", cLine);
    (*env)->ReleaseStringUTFChars(env, line, cLine);
}

JNIEXPORT jboolean JNICALL
Java_com_sun_media_util_JMFProperties_nFileExists(JNIEnv *env,
				   jclass props,
				   jstring filename)
{
    char *cFile = (char*) (*env)->GetStringUTFChars(env, filename, NULL);
    FILE *fp;
    jboolean retVal;
    if ((fp = fopen(cFile, "r")) != NULL) {
	fclose(fp);
	retVal = (jboolean)1;
    } else {
	retVal = (jboolean)0;
    }
    (*env)->ReleaseStringUTFChars(env, filename, cFile);
    return retVal;
}

JNIEXPORT void JNICALL
Java_com_sun_media_util_JMFProperties_nCloseFile(JNIEnv *env,
						 jclass props,
						 jint fid)
{
    fclose((FILE*)fid);
}
