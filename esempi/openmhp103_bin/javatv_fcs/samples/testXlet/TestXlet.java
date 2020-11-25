/*
 * @(#)TestXlet.java	1.2 99/08/26
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

import com.sun.tv.si.*;
import com.sun.tv.receiver.*;

import java.awt.*;
import javax.tv.xlet.*;
import javax.tv.graphics.*;
import javax.tv.service.*;
import javax.tv.service.navigation.*;

public class TestXlet implements Xlet {
	public static void main(String args[]) {
		SIManager manager = SIManager.createInstance();
		SIEmulator emulator = SIEmulator.getInstance();
		emulator.putXlet(0, "TestXlet", "TestXlet", null);
	}


	Label label = null;

    /**      
     * Signals the Xlet to initialize itself and enter the 
     * <i>Paused</i> state.
     * The Xlet shall initialize itself in preparation for providing service.
     * It should not hold shared resources but should be prepared to provide 
     * service in a reasonable amount of time. <p>
     * An <code>XletContext</code> is used by the Xlet to access
     * properties associated with its runtime environment.
     * After this method returns successfully, the Xlet
     * is in the <i>Paused</i> state and should be quiescent. <p>
     * <b>Note:</b> This method shall only be called once.<p>
     *
     * @parameter ctx XletContext This Xlet's XletContext
     * @exception XletStateChangeException If the Xlet cannot be
     * initialized.
     * @see javax.tv.xlet.XletContext
     */

    public void initXlet(XletContext ctx) throws XletStateChangeException {
	if (ctx == null) {
		System.out.println("Error: ctx == null...");
	}

	System.out.println("TestXlet: initXlet...");

	Container rootContainer = TVContainer.getRootContainer(ctx);
	rootContainer.setLayout(new BorderLayout());
	rootContainer.setSize(new Dimension(250, 100));

	System.out.println("initXlet: " + rootContainer);

	label = new Label("TestXlet has succedded...");
	rootContainer.add("Center", label);

	rootContainer.validate();
    }

    /**   
     * Signals the Xlet to start providing service and
     * enter the <i>Active</i> state.
     * In the <i>Active</I> state the Xlet may hold shared resources.
     * The method will only be called when
     * the Xlet is in the <i>paused</i> state.
     * <p>
     *      
     * @exception XletStateChangeException  is thrown if the Xlet
     *		cannot start providing service. 
     *
     * Two kinds of failures can prevent the service from starting,
     * transient and non-transient.  For transient failures the
     * <code>XletStateChangeException</code> exception should be thrown.
     * For non-transient failures the <code>XletContext.done</code>
     * method should be called with an error indication (TBD).
     *
     */
    public void startXlet() throws XletStateChangeException {
	System.out.println("TestXlet: startXlet...");
    }
    
    /**
     *
     * Signals the Xlet to stop providing service and
     * enter the <i>Paused</i> state.
     * In the <i>Paused</i> state the Xlet must stop providing
     * service, and might release all shared resources
     * and become quiescent. This method will only be called
     * called when the Xlet is in the <i>Active</i> state. <p>
     *
     */
    public void pauseXlet() {
	System.out.println("TestXlet: pauseXlet...");
    }

    /** 
     * Signals the Xlet to terminate and enter the <i>Destroyed</i> state.
     * In the destroyed state the Xlet must release
     * all resources and save any persistent state. This method may
     * be called from the <i>Loaded</i>, <i>Paused</i> or 
     * <i>Active</i> states. <p>
     * Xlets should
     * perform any operations required before being terminated, such as
     * releasing resources or saving preferences or
     * state. <p>
     *
     * <b>NOTE:</b> The Xlet can request that it not enter the <i>Destroyed</i>
     * state by throwing an <code>XletStateChangeException</code>. This 
     * is only a valid response if the <code>unconditional</code>
     * flag is set to <code>false</code>. If it is <code>true</code>
     * the Xlet is assumed to be in the <i>Destroyed</i> state
     * regardless of how this method terminates. If it is not an 
     * unconditional request, the Xlet can signify that it wishes
     * to stay in its current state by throwing the Exception.
     * This request may be honored and the <code>destroy()</code>
     * method called again at a later time. 
     *
     *
     * @param boolean unconditional If <code>done</code> is true when this
     * method is called, requests by the Xlet to not enter the
     * destroyed state will be ignored.
     *   
     * @exception XletStateChangeException is thrown if the Xlet
     *		wishes to continue to execute (Not enter the <i>Destroyed</i>
     *          state). 
     *          This exception is ignored if <code>unconditional</code> 
     *          is equal to <code>true</code>.
     * 
     * 
     */
    public void destroyXlet(boolean unconditional) 
                      throws XletStateChangeException {
	System.out.println("TestXlet: destroyXlet...");
    }
}
