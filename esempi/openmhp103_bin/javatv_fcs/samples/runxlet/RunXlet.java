/*
 * @(#)RunXlet.java	1.6 00/07/28
 *
 * Copyright (c) 2000 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 */

import com.sun.tv.*;
import com.sun.tv.si.*;
import com.sun.tv.receiver.*;

import javax.tv.service.navigation.*;

import java.io.*;

public class RunXlet {
    SIEmulator emulator = null;
    public boolean load_si = true;

    // set up everything
    private void setUp(){
	emulator = new SIEmulator();
	
	    emulator.isCaughtUp();
    }

    private void startXlet(String xlet_name){
	emulator.putXlet(0,xlet_name, xlet_name, null);
    }

    public static void main(String args[]) {
	String xlet1 = null;
	boolean load_si = true;

	if(args.length < 1){
	    System.out.println("ERROR: you must specify at least 1 Xlet");
	    System.out.println("usage: RunXlet [-n] <classname>");
	    System.out.println("  -n     Does not load SI DB");
	    System.exit(1);
	}

	if(args[0].equals("-n")){
	    xlet1 = args[1];
	    load_si = false;
	} else {
	    xlet1 = args[0];
	}

	System.out.println("Running Xlet named: " + xlet1);
	RunXlet rxl = new RunXlet();
	rxl.load_si = load_si;
	rxl.setUp();

	rxl.startXlet(xlet1);
    }
}
