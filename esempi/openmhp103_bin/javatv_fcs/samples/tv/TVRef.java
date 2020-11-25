/**
 * @(#)TVRef.java	1.1 00/03/26
 *
 * Copyright 2000 by Sun Microsystems, Inc.,
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */

import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;
import javax.media.*;
import javax.tv.*;
import javax.tv.media.*;
import javax.tv.locator.*;
import javax.tv.service.*;
import javax.tv.service.transport.*;
import javax.tv.service.guide.*;
import javax.tv.service.selection.*;
import javax.tv.service.navigation.*;

public class TVRef extends Applet implements ActionListener, ItemListener {

	private final double FRAME_RATE_CHANGE = 0.2;

	private SIManager manager = null;
	private LocatorFactory factory = null;
	private ServiceContextFactory scFactory = null;
	private ServiceContext context = null;
	private ProgramGuide programGuide = new ProgramGuide("Program Schedule");

	private Hashtable counterCache = new Hashtable();
	private Hashtable displayCache = new Hashtable();

	public boolean isApplet = true;
	public String serverURL = "file:///";

// -----------------------------------------------------
static { 
	String contentPathProp = "java.content.handler.pkgs";
	Properties props = System.getProperties();
	props.put(contentPathProp, "com.sun.media.content");
	System.setProperties(props);
}

// -----------------------------------------------------
private Image LoadIcon(String filename) {
	URL serverBase = null;
	try {
		if (isApplet) {
			serverBase = getCodeBase();
		} else {
			serverBase = new URL(serverURL);
		}
// TBD		return new ImageIcon(new URL(serverBase, filename)).getImage();

	} catch (Exception e) {
		System.out.println("Load icon error " + filename);
		e.printStackTrace();
	}
	return null;
}

// -----------------------------------------------------
public Button newImageButton(String filename, String action) {
//	ImageButton button = new ImageButton(LoadIcon(filename), null);
	Button button = new Button(action);
	button.setActionCommand(action);
	button.addActionListener(this);
	return button;
}

// -----------------------------------------------------
public Button newIconButton(String action, Point pt,
	String normalIcon, String selectedIcon,
	String pressedIcon, String rolloverIcon) {

	Button button = new Button(action);
/**
 **	Button button = new Button();
 **	button.setIcon(LoadIcon(normalIcon));
 **	button.setSelectedIcon(LoadIcon(selectedIcon));
 **	button.setPressedIcon(LoadIcon(pressedIcon));
 **	button.setRolloverIcon(LoadIcon(rolloverIcon));
 **/

	button.setForeground(Color.black);
	button.setActionCommand(action);
	button.addActionListener(this);
	return button;
}

// -----------------------------------------------------
public Choice newChannelsButton(String action) {
	Choice cbox = new Choice();
// TDN	cbox.setEditable(true);

	ServiceList sl = manager.filterServices(null);
	if (sl == null) {
		System.out.println("newChannelsButton: " + action);
		return cbox;
	}

	for (int i = 0; i < sl.size(); i++) {
		try {
			Service service = sl.getService(i);
			if (service != null) {
				cbox.addItem(service.getName());
			}
		} catch (Exception e) {
			;
		}
	}
	cbox.addItem(" ");
	cbox.addItemListener(this);
	return cbox;
}

// -----------------------------------------------------
public void init() {
	try {
		this.manager = SIManager.createInstance();
		if (manager == null) {
			throw new Exception("SIManager.createInstance() == null");
		}

		this.factory = LocatorFactory.getInstance();
		if (factory == null) {
			throw new Exception("LocatorFactory.getInstance() == null");
		}

		this.scFactory = ServiceContextFactory.getInstance();
		if (scFactory == null) {
			throw new Exception("ServiceContextFactory == null");
		}

		this.context = scFactory.createServiceContext();
		if (context == null) {
			throw new Exception("createServiceContext == null");
		}
 	} catch (Exception e) {
		System.out.println("FAILURE: " + e);
		e.printStackTrace();
	}

	Panel panel = new Panel(new GridLayout(0, 2));
	this.add(panel, "Center");

	panel.add(newChannelsButton("select"));

	panel.add(newIconButton("mute", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("pause", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("play", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("rewind", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("ff", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("stop", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("rec", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("ch+", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("ch-", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("counter", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("display", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("audio", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("power", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("vol+", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("vol-", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("rate-", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("rate+", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("frame-", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("frame+", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("zoom-", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("zoom+", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("focus-", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("focus+", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("guide", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("xlets", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("0", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("1", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("2", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("3", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("4", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("5", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("6", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("7", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("8", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("9", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("enter", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("pip", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("phone", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("left", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("right", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("down", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

	panel.add(newIconButton("up", new Point(0, 0),
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif",
		"images/mute.gif"));

}

public void start() {
}

public void stop() {
}

public void destroy() {
}

// -----------------------------------------------------
public void itemStateChanged(ItemEvent event) {
	if (event.getStateChange() != event.SELECTED)
		return;

	try {
		actionSelect(event);
	} catch (Exception e) {
		e.printStackTrace();
	}
}

// -----------------------------------------------------
public void actionPerformed (ActionEvent event) {
	try {
		if (event == null)
			return;

		String cmd = event.getActionCommand();
		if (cmd == null) 
			return;

System.out.println("actionPerformed: " + cmd);

		if ("select".equals(cmd)) {
			actionSelect(event);

		} else if ("mute".equals(cmd)) {
			actionMute(event);

		} else if ("rewind".equals(cmd)) {
			actionRewind(event);

		} else if ("pause".equals(cmd)) {
			actionPause(event);

		} else if ("play".equals(cmd)) {
			actionPlay(event);

		} else if ("ff".equals(cmd)) {
			actionFF(event);

		} else if ("stop".equals(cmd)) {
			actionStop(event);

		} else if ("rec".equals(cmd)) {
			actionRec(event);

		} else if ("ch+".equals(cmd)) {
			actionCHplus(event);

		} else if ("ch-".equals(cmd)) {
			actionCHminus(event);

		} else if ("counter".equals(cmd)) {
			actionCounter(event);

		} else if ("display".equals(cmd)) {
			actionDisplay(event);

		} else if ("audio".equals(cmd)) {
			actionAudio(event);

		} else if ("power".equals(cmd)) {
			actionPower(event);

		} else if ("vol-".equals(cmd)) {
			actionVol(event, -0.02F);

		} else if ("vol+".equals(cmd)) {
			actionVol(event,  0.02F);

		} else if ("rate+".equals(cmd)) {
			actionRate(event, (float)FRAME_RATE_CHANGE);

		} else if ("rate-".equals(cmd)) {
			actionRate(event, -(float)FRAME_RATE_CHANGE);

		} else if ("frame+".equals(cmd)) {
			actionFrame(event, 1);

		} else if ("frame-".equals(cmd)) {
			actionFrame(event, -1);

		} else if ("zoom+".equals(cmd)) {
			actionZoom(event, 1);

		} else if ("zoom-".equals(cmd)) {
			actionZoom(event, -1);

		} else if ("guide".equals(cmd)) {
			actionGuide(event);
		}

 	} catch (Exception e) {
		System.out.println("FAILURE: " + e);
		e.printStackTrace();
	}
}

private synchronized void ensureStopped(Player player) {
	player.stop();
	while (player.getState() == player.Started) {
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			;
		}
	}
}

// -----------------------------------------------------
private void actionSelect(EventObject event) throws Exception {
	if (context == null) {
		throw new NullPointerException("context == null");
	}

	String locatorStr = null;
	if (event.getSource() instanceof Choice) {
		Choice cbox = (Choice)event.getSource();
		String serviceName = (String)cbox.getSelectedItem();
		locatorStr = "service:/" + serviceName;
	}

	if (locatorStr == null) {
		throw new NullPointerException("locator == null");
	}
		
	Locator locator = factory.createLocator(locatorStr);
	if (locator == null) {
		throw new Exception("factory.createLocator() == null");
	}

	Service service = manager.getService(locator);

	context.select(service);
}

// -----------------------------------------------------
private void actionMute(ActionEvent event) throws Exception {
	if (context == null) {
		throw new NullPointerException("context == null");
	}

	ServiceContentHandler handlers[] = context.getServiceContentHandlers();
	if (handlers == null) {
		throw new NullPointerException("handlers == null");
	}

	for (int i = 0; i < handlers.length; i++) {
		if (!(handlers[i] instanceof Player))
			continue;

		Player player = (Player)handlers[i];
		GainControl gain = player.getGainControl();
		if (gain == null)
			continue;

		boolean mute = gain.getMute();
		gain.setMute(!mute);
	}
}

// -----------------------------------------------------
private void actionVol(ActionEvent event, float deltaDB) throws Exception {
	if (context == null) {
		throw new NullPointerException("context == null");
	}

	ServiceContentHandler handlers[] = context.getServiceContentHandlers();
	if (handlers == null) {
		throw new NullPointerException("handlers == null");
	}

	for (int i = 0; i < handlers.length; i++) {
		if (!(handlers[i] instanceof Player))
			continue;

		Player player = (Player)handlers[i];

		GainControl gain = player.getGainControl();
		if (gain == null)
			continue;

		float level = gain.getLevel() + deltaDB;
		if (level > 1.0F) {
			level = 1.0F;
		} else if (level < 0.0F) {
			level = 0.0F;
		}
		gain.setLevel(level);
	}
}

// -----------------------------------------------------
private void actionRewind(ActionEvent event) throws Exception {
	if (context == null) {
		throw new NullPointerException("context == null");
	}

	ServiceContentHandler handlers[] = context.getServiceContentHandlers();
	if (handlers == null) {
		throw new NullPointerException("handlers == null");
	}

	for (int i = 0; i < handlers.length; i++) {
		if (!(handlers[i] instanceof Player))
			continue;

		Player player = (Player)handlers[i];

		ensureStopped(player);
		player.setRate(1.0F);
		player.setStopTime(javax.media.Clock.RESET);
//		player.syncStart(player.getTimeBase().getTime());
	}
}

// -----------------------------------------------------
private void actionPause(ActionEvent event) throws Exception {
	if (context == null) {
		throw new NullPointerException("context == null");
	}

	ServiceContentHandler handlers[] = context.getServiceContentHandlers();
	if (handlers == null) {
		throw new NullPointerException("handlers == null");
	}

	for (int i = 0; i < handlers.length; i++) {
		if (!(handlers[i] instanceof Player))
			continue;

		Player player = (Player)handlers[i];

		ensureStopped(player);
	}
}

// -----------------------------------------------------
private void actionFF(ActionEvent event) throws Exception {
	if (context == null) {
		throw new NullPointerException("context == null");
	}

	ServiceContentHandler handlers[] = context.getServiceContentHandlers();
	if (handlers == null) {
		throw new NullPointerException("handlers == null");
	}

	for (int i = 0; i < handlers.length; i++) {
		if (!(handlers[i] instanceof Player))
			continue;

		Player player = (Player)handlers[i];

		ensureStopped(player);
		player.setRate(3.0F);
		player.syncStart(player.getTimeBase().getTime());
	}
}

// -----------------------------------------------------
private void actionPlay(ActionEvent event) throws Exception {
	if (context == null) {
		throw new NullPointerException("context == null");
	}

	ServiceContentHandler handlers[] = context.getServiceContentHandlers();
	if (handlers == null) {
		throw new NullPointerException("handlers == null");
	}

	for (int i = 0; i < handlers.length; i++) {
		if (!(handlers[i] instanceof Player))
			continue;

		Player player = (Player)handlers[i];

		ensureStopped(player);
		player.setRate(1.0F);
		player.syncStart(player.getTimeBase().getTime());
	}
}

// -----------------------------------------------------
private void actionRate(ActionEvent event, float rateChange)
	throws Exception {

	if (context == null) {
		throw new NullPointerException("context == null");
	}

	ServiceContentHandler handlers[] = context.getServiceContentHandlers();
	if (handlers == null) {
		throw new NullPointerException("handlers == null");
	}

	for (int i = 0; i < handlers.length; i++) {
		if (!(handlers[i] instanceof Player))
			continue;

		Player player = (Player)handlers[i];

		ensureStopped(player);

		float rate = player.getRate() * (1.0F + rateChange);
		player.setRate(rate);
		player.syncStart(player.getTimeBase().getTime());
	}
}


// -----------------------------------------------------
private void actionFrame(ActionEvent event, float direction) throws Exception {
	if (context == null) {
		throw new NullPointerException("context == null");
	}

	ServiceContentHandler handlers[] = context.getServiceContentHandlers();
	if (handlers == null) {
		throw new NullPointerException("handlers == null");
	}

	for (int i = 0; i < handlers.length; i++) {
		if (!(handlers[i] instanceof Player))
			continue;

		Player player = (Player)handlers[i];

		ensureStopped(player);

		long oneFrame = player.getStartLatency().getNanoseconds();
		long curTime = player.getTimeBase().getTime().getNanoseconds();
		Time nextFrame = new Time(curTime + direction * oneFrame);

		player.setRate(direction);
		player.setStopTime(nextFrame);
		player.syncStart(player.getTimeBase().getTime());
	}
}

// -----------------------------------------------------
private void actionStop(ActionEvent event) throws Exception {
	if (context == null) {
		throw new NullPointerException("context == null");
	}

	context.stop();
}

// -----------------------------------------------------
private void actionRec(ActionEvent event) throws Exception {
	if (context == null) {
		throw new NullPointerException("context == null");
	}

	// TBD
}

// -----------------------------------------------------
private void actionCHplus(ActionEvent event) throws Exception {
	if (context == null) {
		throw new NullPointerException("context == null");
	}

	if (manager == null) {
		throw new NullPointerException("manager == null");
	}

	ServiceList sl = manager.filterServices(null);
	if (sl == null) {
		throw new NullPointerException("ServiceList == null");
	}

	Service curr = context.getService();
	if (curr == null) {
		throw new NullPointerException("current service == null");
	}
	
	int index = sl.indexOf(curr);
	index++;
	if (index >= sl.size()) {
		index = 0;
	}

	Service next = sl.getService(index);
	if (next == null) {
		throw new NullPointerException("no next service");
	}

	context.select(next);
}

// -----------------------------------------------------
private void actionCHminus(ActionEvent event) throws Exception {
	if (context == null) {
		throw new NullPointerException("context == null");
	}

	if (manager == null) {
		throw new NullPointerException("manager == null");
	}

	ServiceList sl = manager.filterServices(null);
	if (sl == null) {
		throw new NullPointerException("ServiceList == null");
	}

	Service curr  = context.getService();
	if (curr == null) {
		throw new NullPointerException("current service == null");
	}

	int index = sl.indexOf(curr);
	index--;
	if (index <0) {
		index = sl.size() - 1;;
	}

	Service prev = sl.getService(index);
	if (prev == null) {
		throw new NullPointerException("no previous service");
	}

	context.select(prev);
}

// -----------------------------------------------------
private void actionCounter(ActionEvent event) throws Exception {
	if (context == null) {
		throw new NullPointerException("context == null");
	}

	ServiceContentHandler handlers[] = context.getServiceContentHandlers();
	if (handlers == null) {
		throw new NullPointerException("handlers == null");
	}

	for (int i = 0; i < handlers.length; i++) {
		if (!(handlers[i] instanceof Player))
			continue;

		Player player = (Player)handlers[i];
		Component comp = player.getVisualComponent();
		if (comp == null)
			continue;

		if (counterCache.containsKey(player)) {
			counterCache.remove(player);
		} else {
			counterCache.put(player, comp);
		}
	}
}

// -----------------------------------------------------
private void actionDisplay(ActionEvent event) throws Exception {
	if (context == null) {
		throw new NullPointerException("context == null");
	}

	ServiceContentHandler handlers[] = context.getServiceContentHandlers();
	if (handlers == null) {
		throw new NullPointerException("handlers == null");
	}

	for (int i = 0; i < handlers.length; i++) {
		if (!(handlers[i] instanceof Player))
			continue;

		Player player = (Player)handlers[i];
		Component comp = player.getVisualComponent();
		if (comp == null)
			continue;

		if (displayCache.containsKey(player)) {
			displayCache.remove(player);
		} else {
			displayCache.put(player, comp);
		}
	}
}

// -----------------------------------------------------
private void actionAudio(ActionEvent event) throws Exception {
	if (context == null) {
		throw new NullPointerException("context == null");
	}

	ServiceContentHandler handlers[] = context.getServiceContentHandlers();
	if (handlers == null) {
		throw new NullPointerException("handlers == null");
	}

	for (int i = 0; i < handlers.length; i++) {
		if (!(handlers[i] instanceof Player))
			continue;

		Player player = (Player)handlers[i];
		// TBD
	}
}

// -----------------------------------------------------
private void actionPower(ActionEvent event) throws Exception {
	if (context == null) {
		throw new NullPointerException("context == null");
	}

	context.stop();
	context.destroy();
	System.exit(0);
}

// -----------------------------------------------------
private void actionZoom(ActionEvent event, int direction)
	throws Exception {

	if (context == null) {
		throw new NullPointerException("context == null");
	}

	ServiceContentHandler handlers[] = context.getServiceContentHandlers();
	if (handlers == null) {
		throw new NullPointerException("handlers == null");
	}

	for (int i = 0; i < handlers.length; i++) {
		if (!(handlers[i] instanceof Player))
			continue;

		Player player = (Player)handlers[i];

		ensureStopped(player);
// TBD
		Control control = player.getControl("javax.tv.media.AWTVideoSizeControl");
		Control controls[] = player.getControls();
			if (controls == null || controls.length == 0) {
				throw new NullPointerException();
			}
			
			for (int j = 0; j < controls.length; j++) {
				if (controls[j] instanceof AWTVideoSizeControl) {
				}
			}

		player.syncStart(player.getTimeBase().getTime());
	}
}

// -----------------------------------------------------
private void actionGuide(ActionEvent event) throws Exception {

	if (programGuide == null) {
		programGuide = new ProgramGuide("Program Schedule");

	} else if (programGuide.isShowing() == true) {
		programGuide.hide();

	} else {
		programGuide.show();
	} 
}

// -----------------------------------------------------
public static void main(String args[]) {
	TVRef ref = new TVRef();
	ref.setSize(150, 500);
	ref.serverURL = "file:///" + System.getProperty("user.dir") + "/../";
	ref.isApplet = false;

	ref.init();
	ref.start();

	Frame frame = new Frame("Sample Reference Implementation");
	frame.add("Center", ref);
	frame.setSize(150, 500);
	frame.validate();
	frame.show();
}
}

// -----------------------------------------------------
class ProgramGuide extends Frame implements
		ProgramScheduleListener, SIRequestor, ServiceDetailsChangeListener {

	ProgramGuidePanel panel = null;
	Hashtable guide = new Hashtable();

// -----------------------------------------------------
public ProgramGuide(String title) {
	super(title);

	panel = new ProgramGuidePanel(guide);

	ScrollPane scroller = new ScrollPane();
/** TBD
	scroller.setHorizontalScrollBarPolicy(
		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	scroller.setVerticalScrollBarPolicy(
		ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
 **/

	scroller.add(panel);

	this.add("Center", scroller);
	this.setSize(500, 250);
	this.validate();
	this.show();

	try {
		SIManager manager = SIManager.createInstance();
		Transport transports[] = manager.getTransports();
		for (int j = 0; j < transports.length; j++) {
  			transports[j].addServiceDetailsChangeListener(this);
		}

		ServiceList col = manager.filterServices(null);
		for (int i = 0; i < col.size(); i++) {
			Service service = col.getService(i);
			SIRequest req = service.retrieveDetails(this);
		}
	} catch (Exception e) {
		System.out.println("Failure in retrieveDetails: " + e);
		e.printStackTrace();
	}
}

// -----------------------------------------------------
public void notifySuccess(SIRetrievable results[]) {
	try {
		Date beginDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis() + 7*24*60*60*1000);

		for (int i = 0; i < results.length; i++) {
			if (results[i] == null)
				continue;

			if (results[i] instanceof ServiceDetails) {
				ServiceDetails details = (ServiceDetails)results[i];
				ProgramSchedule schedule = details.getProgramSchedule();
				if (schedule == null) 
					continue;

				schedule.retrieveFutureProgramEvents(beginDate, endDate, this);
				schedule.addListener(this);

			} else if (results[i] instanceof ProgramEvent) {
				ProgramEvent program = (ProgramEvent)results[i];
System.out.println("Retrieve program event: " + program.getName());

				String serviceName = program.getService().getName();
				String programName = program.getName();
				String key = serviceName + "." + programName;

				guide.put(key, program);
			}
		}
	} catch (Exception e) {
		System.out.println("notifySuccess failure: " + e);
		e.printStackTrace();
	}
}

// -----------------------------------------------------
public void notifyFailure(SIRequestFailureType reason) {
	System.out.println("notifyFailure: " + reason);
}

// -----------------------------------------------------
public void notifyChange(ProgramScheduleEvent event) {
	if (event == null)
		return;

	SIChangeType ct = event.getChangeType();
	ProgramSchedule schedule = event.getProgramSchedule();
	ProgramEvent program = event.getProgramEvent();

	String serviceName = program.getService().getName();
	String programName = program.getName();
	String key = serviceName + "." + programName;

System.out.println("notifyChange: " + key);

	if (ct == ct.REMOVE) {
		guide.remove(key);
	} else {
		guide.put(key, program);
	}

	panel.repaint();
}
// -----------------------------------------------------
public void notifyChange(ServiceDetailsChangeEvent event) {
System.out.println("notifyChange: ServiceDetailsChangeEvent...");
	try {
		Date beginDate = new Date(System.currentTimeMillis());
		Date endDate = new Date(System.currentTimeMillis() + 7*24*60*60*1000);

		ServiceDetails details = event.getServiceDetails();
		ProgramSchedule schedule = details.getProgramSchedule();
		if (schedule == null) 
			return;

		schedule.retrieveFutureProgramEvents(beginDate, endDate, this);
		schedule.addListener(this);
	} catch (Exception e) {
		System.out.println("notify ServiceDetailsChange: " + e);
	}
}
}

// -----------------------------------------------------------
class ProgramGuidePanel extends Canvas {

	int PGWidth = 1024, PGHeight = 1024;
	int rowHeight = 15, colWidth = 50, pixelsPerMinute = 2;
	int offset = 3, maxchars = 25;

	Hashtable guide = null;
	Font smallFont = new Font("SansSerif",Font.PLAIN,10);

	long startTime = System.currentTimeMillis();

public ProgramGuidePanel(Hashtable guide) {
	super();

	setSize(PGWidth, PGHeight);
	this.guide = guide;
}

// -----------------------------------------------------------
public void paint(Graphics g) {
	super.paint(g);

	ServiceList col = SIManager.createInstance().filterServices(null).sortByName();
	g.setFont(smallFont);

	int y = 0;
	for (int i = 0; i < col.size(); i++) {
		Service service = col.getService(i);
		String serviceName = service.getName();

		if ((y + rowHeight) > PGHeight) {
			PGHeight = (y + rowHeight);
		}

		g.setColor(Color.green);
		g.fillRect(0, y, colWidth, rowHeight);

		g.setColor(Color.black);
		g.drawRect(0, y, colWidth, rowHeight);

		int len2 = (serviceName.length() > maxchars) ? maxchars : serviceName.length();

		g.setColor(Color.black);
		g.drawString(serviceName.substring(0, len2), offset, y+rowHeight-offset);
		y = y + rowHeight;
	}

	Enumeration list = guide.elements();
	while (list.hasMoreElements()) {
		ProgramEvent program = (ProgramEvent)list.nextElement();

		String serviceName = program.getService().getName();
		String programName = program.getName();

		int row = col.indexOf(program.getService());
		if (row == -1) {
			System.out.println("Service not found: " + serviceName);
			continue;
		}

		java.util.Date startDate = program.getStartTime();
		int duration = (int)(program.getDuration()/60);
		int minutes = (int)((startDate.getTime()-startTime)/(60*1000));
		if (minutes < 0) 
			continue;

		int x = pixelsPerMinute * minutes + colWidth;
		y = row * rowHeight;
		int xlen = pixelsPerMinute * duration;


		if ((x + xlen) > PGWidth) {
			PGWidth = x + xlen;
		}

		if ((y + rowHeight) > PGHeight) {
			PGHeight = (y + rowHeight);
		}

		g.setColor(Color.yellow);
		g.fillRect(x, y, xlen, rowHeight);

		g.setColor(Color.black);
		g.drawRect(x, y, xlen, rowHeight);

		int len1 = (programName.length() > maxchars) ? maxchars : programName.length();

		g.setColor(Color.black);
		g.drawString(programName.substring(0, len1), x+offset, y+rowHeight-offset);
	}

	setSize(PGWidth, PGHeight);
}
}
