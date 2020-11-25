/*
 * @(#)bsd-endian.h	1.5 98/11/18
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

#ifdef sun
#pragma ident "@(#)bsd-endian.h	1.5 98/11/18" 
#endif

#ifdef WIN32
#include <winsock.h>
#else
#include <sys/param.h>
#endif
#ifndef IPPROTO_IP
#include <netinet/in.h>
#endif

#ifdef _X86_
#define SHIFT(n) (24 - (n))
#else
#define SHIFT(n) (n)
#endif
#define EXTRACT(v, n) (((v) >> SHIFT(n)) & 0xff)
#define SPLICE(v, p, n) (v) |= (p) << SHIFT(n)
