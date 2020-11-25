/*
 * 00/08/01 @(#)SvcDispXlet.java	1.3
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

import javax.tv.xlet.*;
import javax.tv.graphics.*;
import javax.tv.service.*;
import javax.tv.service.navigation.*;

import java.awt.*;
import java.awt.event.*;

public class SvcDispXlet implements Xlet, ActionListener {
    private Container root_container = null;
    private Panel panel = null;
    private List list = null;
    private Button button = null;
    private SIManager si_manager = null;

    // init method
    public void initXlet(XletContext ctx){
	
	root_container = TVContainer.getRootContainer(ctx);
	
	panel = new Panel();
	panel.setBackground(Color.black);

	root_container.setLayout(new BorderLayout());
	
	root_container.add(panel);

	panel.setLayout(new BorderLayout());

	list = new List();
	list.setBackground(Color.lightGray);
	panel.add(list, "Center");

	button = new Button("Refresh");
	button.setBackground(Color.darkGray);
	button.setForeground(Color.white);
	button.addActionListener(this);
	panel.add(button, "South");
        
        root_container.setVisible(true);

	si_manager = SIManager.createInstance();
    }

    // start method
    // when we start, do an initial update of the list
    public void startXlet(){
	panel.validate();
	updateList();
    }

    // pause
    public void pauseXlet(){}

    // destroy
    public void destroyXlet(boolean unconditional){}
    
    // called when the refresh button is pressed
    public void actionPerformed(ActionEvent evt){
	if(evt.getSource() == button){
	    this.updateList();
	} 
    }

    private void updateList(){
	// clear out the old list
	list.removeAll();
	
	ServiceList collection = 
	    si_manager.filterServices(null);
	/**
	if(collection.size() < 1){
	    system.out.println("No Services Available...");
	    return;
	}
	//	collection.toBeginning();

	for(int i = 0; i < collection.size(); i++){
	    // collection.setIndex(i);
	    list.addItem(collection.getService(i).getName() , 0);
	}

	**/
	ServiceIterator si = collection.createServiceIterator();
	
	si.toEnd();
	while(si.hasPrevious()){
	    list.addItem(si.previousService().getName() , 0);
	}
    }

}
