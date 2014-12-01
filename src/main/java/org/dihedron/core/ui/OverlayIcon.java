/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * @author Andrea Funto'
 */
public class OverlayIcon extends ImageIcon {
	
	/**
	 * Serial version id.
	 */
	private static final long serialVersionUID = -3169418871602305037L;
	
	/**
	 * A list of overlays.
	 */
	private List<ImageIcon> overlays = new ArrayList<>();
	
	/**
	 * Default constructor.
	 */
	public OverlayIcon() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param filename
	 *   the name of the file containing the icon.
	 */
	public OverlayIcon (String filename) {
		super(filename);
	}

	/**
	 * Constructor.
	 *
	 * @param filename
	 *   the name of the file containing the icon.
	 * @param description
	 *   a description for the icon.
	 */
	public OverlayIcon(String filename, String description) {
		super(filename, description);
	}
	
	/**
	 * Constructor.
	 *
	 * @param location
	 *   the URL at which the icon is available.
	 */
	public OverlayIcon (URL location) {
		super(location);
	}
	
	/**
	 * Constructor.
	 *
	 * @param location
	 *   the URL at which the icon is available.
	 * @param description
	 *   a description for the icon.
	 */
	public OverlayIcon(URL location, String description) {
		super(location, description);
	}
	
	/**
	 * Constructor.
	 *
	 * @param image
	 *   an image object to base this overlay icon upon.
	 */
	public OverlayIcon(Image image) {
		super(image);
	}

	/** 
	 * Constructor.
	 *
	 * @param image
	 *   an image object to base this overlay icon upon.
	 * @param description
	 *   a description for the icon.
	 */
	public OverlayIcon(Image image, String description) {
		super(image, description);
	}

	/**
	 * Constructor.
	 *
	 * @param imageData
	 *   a byte array containing the image data.
	 */
	public OverlayIcon(byte[] imageData) {
		super(imageData);
	}
	
	/**
	 * Constructor.
	 *
	 * @param imageData
	 *   a byte array containing the image data.
	 * @param description
	 *   a description for the icon.
	 */
	public OverlayIcon(byte[] imageData, String description) {
		super(imageData, description);
	}
	
	/**
	 * Constructor.
	 *
	 * @param base
	 *   the base icon.
	 */
	public OverlayIcon(ImageIcon base) {
		super(base.getImage());
	}

	/**
	 * Adds an overlay icon to the base icon.
	 * 
	 * @param overlays
	 *   the list of overlay icons to add.
	 * @return
	 *   the object itself, for method chaining.
	 */
	public OverlayIcon withOverlay(ImageIcon ... overlays) {
		if(overlays != null) {
			for(ImageIcon overlay : overlays) {
				this.overlays.add(overlay);
			}
		}
		return this;
	}
	
	/**
	 * Clears all the overlays.
	 */
	public void clearOverlays() {
		this.overlays.clear();
	}

	/**
	 * @see java.swing.ImageIcon#paintIcon(Component, Graphics, int, int)
	 */
	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {		
		super.paintIcon(c, g, x, y);
		for(ImageIcon icon: overlays) {
			icon.paintIcon(c, g, x, y);
		}
	}
}
