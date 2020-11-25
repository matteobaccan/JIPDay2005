/*
 * @(#)ImageButton.java	1.1 00/03/26
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

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;

public class ImageButton extends Button implements ImageObserver {

private Image image = null;
private String pathname = null;
private String label = null;

private int desiredWidth  = -1;
private int desiredHeight = -1;

private final int OFFSET = 4;
private final int SPACING = 2;

private final int STATE_NORMAL = 0;
private final int STATE_PRESSED = 1;
private final int STATE_FOCUS = 2;
private int state = STATE_NORMAL;

public static final int VERTICAL = 0;
public static final int HORIZONTAL = 1;
private int alignment = VERTICAL;

/**
 *
 */
public ImageButton(Image image, String label) {
	this.label = label;
	this.setBackground(Color.white);
	this.setFont(new Font ("Courier", Font.BOLD, 12));

	this.image = image;
}

/**
 *
 */
public ImageButton(String pathname, String label) {
	this.label = label;
	this.pathname = pathname;
	this.setBackground(Color.white);
	this.setFont(new Font ("Courier", Font.BOLD, 12));

	if (new File(pathname).exists() == false) {
		System.out.println("Image file not found: " + pathname);

	} else {
		this.image = Toolkit.getDefaultToolkit().getImage(pathname);
		if (this.image == null) {
			System.out.println("Image failed to load: " + pathname);
		} else {
			getParent().prepareImage(this.image, this);
		}
	} 
}

/**
 *
 */
public boolean imageUpdate(Image img, int flags,
	       int x, int y, int w, int h) {

	if (img != null && img == this.image) {
		if ((flags & ALLBITS) != 0) {
			paint(getGraphics());
		}
		return (flags & (ALLBITS|ERROR)) == 0;
	}
	return false;
}


/**
 * This method is called to repaint this canvas. Most applications 
 * that subclass <code>Canvas</code> should override this method in 
 * order to perform some useful operation. 
 * <p>
 * The <code>paint</code> method provided by <code>Canvas</code> 
 * redraws this canvas's rectangle in the background color. 
 * <p>
 * The graphics context's origin (0,&nbsp;0) is the top-left corner 
 * of this canvas. Its clipping region is the area of the context. 
 * @param      g   the graphics context.
 * @see        java.awt.Graphics
 * @since      JDK1.0
 */
public void paint(Graphics g) {
	int i;
	Rectangle r = bounds();
	Color brighter = getBackground().brighter();
	Color darker = getBackground().darker();

	g.setColor(getBackground());

	switch (state) {
	case STATE_NORMAL:
	default:
		g.fillRect(0, 0, r.width, r.height);
		g.setColor(Color.black);
		break;

		/*case STATE_PRESSED:
	case STATE_FOCUS:
		g.fillRect(0, 0, r.width, r.height);
		g.setColor((state == STATE_PRESSED) ? darker : brighter);
		for (i = 1; i < OFFSET; i++) {
			g.drawLine(i, i, i, r.height-i);
			g.drawLine(i, i, r.width-i, i);
		}
		g.setColor((state == STATE_PRESSED) ? brighter : darker);
		for (i = 1; i < OFFSET; i++) {
			g.drawLine(r.width-i, i, r.width-i, r.height-i);
			g.drawLine(i, r.height-i, r.width-i, r.height-i);
		}
		g.setColor(Color.black);
		g.drawRect(0, 0, r.width, r.height);
		g.drawRect(1, 1, r.width-2, r.height-2);
		g.setColor(Color.blue);
		break;
		*/
	}

	if (label != null) {
	    FontMetrics fm = g.getFontMetrics();
	    int xt;
	    int yt = r.height - OFFSET - fm.getDescent();
	    if (alignment == VERTICAL) {
	    	xt = (r.width - fm.stringWidth(label))/2;
 	    } else {
	    	xt = r.width - fm.stringWidth(label) - OFFSET;
	    }
	    g.drawString(label, xt, yt);
	}

	if (prepareImage(image, this)) {
	    int yi = OFFSET;
	    int xi;
	    if (alignment == VERTICAL) {
	        xi = (r.width - image.getWidth(null))/2;
	    } else {
	        xi = OFFSET;
	    }
	    g.drawImage(image, xi, yi, this);
	}
}

/**
 *
 */
public boolean mouseDown(Event evt, int x, int y) {
	state = STATE_PRESSED;
	paint(getGraphics());
	return super.mouseDown(evt, x, y);
}

/**
 *
 */
public boolean mouseUp(Event evt, int x, int y) {
	state = STATE_NORMAL;
	paint(getGraphics());
	deliverEvent(new Event(evt.target, Event.ACTION_EVENT, evt.arg));
	return super.mouseUp(evt, x, y);
}

/** 
 * @deprecated As of JDK version 1.1,
 * replaced by processFocusEvent(FocusEvent).
 */
public boolean mouseEnter(Event evt, int x, int y) {
	state = STATE_FOCUS;
	paint(getGraphics());
	return super.mouseEnter(evt, x, y);
}

/** 
 * @deprecated As of JDK version 1.1,
 * replaced by processFocusEvent(FocusEvent).
 */
public boolean mouseExit(Event evt, int x, int y) {
	state = STATE_NORMAL;
	paint(getGraphics());
	return super.mouseExit(evt, x, y);
}

/**
 *
 */
public boolean handleEvent(Event evt) {
	return super.handleEvent(evt);
}

/**
 * @deprecated As of JDK version 1.1,
 * replaced by <code>getPreferredSize()</code>.
 */
public Dimension preferredSize() {
	Dimension size = new Dimension(0, 0);

	if (desiredWidth != -1 || desiredHeight != -1) {
		size.width = desiredWidth;
		size.height = desiredHeight;
		return size;
	}

	if (label != null) {
		Graphics g = getGraphics();
		FontMetrics fm = g.getFontMetrics();
		size.width  = fm.stringWidth(label);
		size.height = fm.getHeight();
	}

	if (image != null && prepareImage(image, this)) {
	    if (alignment == VERTICAL) {
		if (size.width < image.getWidth(this)) {
		    size.width = image.getWidth(this);
		}
		size.height = size.height + image.getHeight(this) + SPACING;
 	    } else {
		if (size.height < image.getHeight(this)) {
		    size.height = image.getHeight(this);
		}
		size.width = size.width + image.getWidth(this)+SPACING;
	    }
	}

	if (size.width > size.height) {
	    size.width  = size.width  + OFFSET * 2;
	    size.height = size.width;
	} else {
	    size.height = size.height + OFFSET * 2;
	    size.width  = size.height;
	}

	return size;
}

/**
 * @deprecated As of JDK version 1.1,
 * replaced by <code>getMinimumSize()</code>.
 */
public Dimension minimumSize() {
	return preferredSize();
}

/** 
 * Gets the maximum size of this component.
 * @return A dimension object indicating this component's maximum size.
 * @see #getMinimumSize
 * @see #getPreferredSize
 * @see LayoutManager
 */
public Dimension getMaximumSize() {
	return preferredSize();
}
}
