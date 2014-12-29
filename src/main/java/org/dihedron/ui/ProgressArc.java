/*
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */
package org.dihedron.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A complex widget implementing a progress indicator (as an arc), and a
 * textual or iconic progress indication; the widget is capable of responding to 
 * user clicks, since it extends {@code JButton}.
 * 
 * @author Andrea Funto'
 */
public class ProgressArc extends JButton implements ActionListener {
	
	/**
	 * The default arc thickness.
	 */
	public static final int DEFAULT_THICKNESS = 4;
	
	/**
	 * The default padding between arc and widget border.
	 */
	public static final int DEFAULT_PADDING = 0;
	
	/**
	 * The default colour for the arc.
	 */
	public static final Color DEFAULT_ARC_FOREGROUND_COLOR = Color.BLACK;

	/**
	 * The default colour for the arc's background circle.
	 */
	public static final Color DEFAULT_ARC_BACKGROUND_COLOR = null;
	
	/**
	 * The default minimum allowed value for the arc.
	 */
	public static final int DEFAULT_ARC_MIN_VALUE = 0;
	
	/**
	 * The default maximum allowed value for the arc.
	 */
	public static final int DEFAULT_ARC_MAX_VALUE = 100;
	
	/**
	 * Whether by default the arc is in indeterminate mode.
	 */
	public static final boolean DEFAULT_ARC_INDETERMINATE = false;
	
	/**
	 * An enumeration representing whether the progress arc should show a progress
	 * string, the current percentage, the number of steps over the total (e.g. 
	 * 2/10), or whatever was provided by the user (text or icon) while progressing.
	 * 
	 * @author Andrea Funto'
	 *
	 */
	public enum ValueMode {
		
		/**
		 * The actual, current value.
		 */
		VALUE,
		
		/**
		 * The (automatically calculated) percentage.
		 */
		PERCENT,
		
		/**
		 * The number of steps (calculated as value - minimum) out of the total
		 * (calculated as maximum - minimum).
		 */
		STEPS, 
		
		/**
		 * Draw whatever was provided by the user for rendering (text or icon),
		 * according to the appropriate render mode.
		 */
		USER
	}
	
	/**
	 * Whether the component should draw the text or the icon.
	 * 
	 * @author Andrea Funto'
	 */
	public enum ViewMode {
		
		/**
		 * Only consider text; ignore icons even if text is lacking.
		 */
		TEXT_ONLY,
		
		/**
		 * Only consider icon; ignore text even if icon is lacking.
		 */
		ICON_ONLY,
		
		/**
		 * Prefer text; if text lacks, use the icon if available.
		 */
		TEXT_OR_ICON,
		
		/**
		 * Prefer icon; if icon lacks, use the text if available.
		 */
		ICON_OR_TEXT
	}

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = -7347433685724714873L;

	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ProgressArc.class);

	/**
	 * A mirror graphic device the indicator will pain to, that help avoid image 
	 * flickering when painting. 
	 */
    private Image offscreen;

	/**
	 * The padding between border and progress circle.
	 */
	private int padding = DEFAULT_PADDING;
    
    /**
     * The thickness of the progress arc.
     */
	private int thickness = DEFAULT_THICKNESS;
		
	/**
	 * The colour of the circle under the progress arc.
	 */
	private Color circle = DEFAULT_ARC_BACKGROUND_COLOR;
	
	/**
	 * The colour of the progress arc.
	 */
	private Color arc = DEFAULT_ARC_FOREGROUND_COLOR;	
		
	/**
	 * The minimum value of the progress arc.
	 */
	private int min = DEFAULT_ARC_MIN_VALUE;
	
	/**
	 * The maximum value of the progress arc.
	 */
	private int max = DEFAULT_ARC_MAX_VALUE;
		
	/**
	 * Whether the progress arc is in indeterminate mode.
	 */
	private boolean indeterminate = DEFAULT_ARC_INDETERMINATE;
	
	/**
	 * The current value of the progress arc.
	 */
	private int value = 0;
	
	/**
	 * The timer used to animate the progress arc.
	 */
	private Timer timer = null;	
	
	/**
	 * The rendering mode when showing progress (that is percent is between 0% 
	 * and 100%).
	 */
	private ValueMode valueMode = ValueMode.PERCENT;
	
	/** 
	 * How the progress arc should show its current status (whether via text or
	 * an icon).
	 */
	private ViewMode viewMode = ViewMode.TEXT_OR_ICON;
		
	/**
	 * The start angle of the progress arc.
	 */
	private int start = 0;
	
	/**
	 * Constructor.
	 */
	public ProgressArc() {
	}

	/**
	 * Constructor.
	 * 
	 * @param icon
	 *   the icon to be show in the button.
	 */
	public ProgressArc(Icon icon) {
		super(icon);
	}

	/**
	 * Constructor.
	 * 
	 * @param text
	 *   the text to be shown in the button.
	 */
	public ProgressArc(String text) {
		super(text);
	}

	/**
	 * Constructor.
	 * 
	 * @param action
	 *   the action used to specify the new button.
	 */
	public ProgressArc(Action action) {
		super(action);
	}

	/**
	 * Constructor.
	 * 
	 * @param text
	 *   the text to be shown in the button.
	 * @param icon
	 *   the icon to be shown on the button.
	 */
	public ProgressArc(String text, Icon icon) {
		super(text, icon);
	}
	
	/**
	 * Returns the current value of the padding.
	 * 
	 * @return
	 *  the current value of the padding.
	 */
	public int getPadding() {
		return padding;
	}
	
	/**
	 * Sets the size of the padding.
	 * 
	 * @param padding
	 *   the new size of the padding.
	 * @throws IllegalArgumentException
	 *   if the value of the padding is negative.
	 */
	public void setPadding(int padding) {
		if(padding < 0) {
			throw new IllegalArgumentException("The value for the padding must be non-negative.");
		}
		this.padding = padding;
	}
	
	/**
	 * Returns the thickness of the progress arc.
	 * 
	 * @return
	 *   the thickness of the progress arc.
	 */
	public int getThickness() {
		return thickness;
	}
	
	/**
	 * Sets the thickness of the progress arc.
	 * 
	 * @param thickness
	 *   the thickness of the progress arc.
	 * @throws IllegalArgumentException
	 *   if the value of the thickness is negative.
	 */
	public void setThickness(int thickness) {
		if(thickness < 0) {
			throw new IllegalArgumentException("The value for the arc thickness must be non-negative.");
		}
		this.thickness = thickness;
	}
	
	/**
	 * Returns the colour of the circle lying under the progress arc.
	 * 
	 * @return
	 *   the colour of the circle lying under the progress arc.
	 */
	public Color getArcBackgroundColor() {
		return circle;
	}
	
	/**
	 * Sets the colour of the circle lying under the progress arc.
	 * 
	 * @param color
	 *   the colour of the circle lying under the progress arc; pass {@code null}
	 *   to have a transparent circle.
	 */
	public void setArcBackgroundColor(Color color) {
		this.circle = color;
	}

	/**
	 * Returns the colour of the progress arc.
	 * 
	 * @return
	 *   the colour of the progress arc.
	 */
	public Color getArcForegroundColor() {
		return arc;
	}
	
	/**
	 * Sets the colour of the progress arc.
	 * 
	 * @param color
	 *   the colour of the the progress arc; if {@code null}, the default colour 
	 *   (black) is used.
	 */
	public void setArcForegroundColor(Color color) {
		this.arc = color != null ? color : DEFAULT_ARC_FOREGROUND_COLOR;
	}
	
	/**
	 * Returns the current minimum acceptable value.
	 * 
	 * @return
	 *   the current minimum acceptable value.
	 */
	public int getMinimum() {
		return min;
	}
	
	/**
	 * Sets the minimum value of scale the progress arc is capable of showing.
	 * 
	 * @param min
	 *   the minimum value of the scale of values the progress arc will show.
	 */
	public void setMinimum(int min) {
		this.min = min;
	}
	
	/**
	 * Returns the current maximum acceptable value.
	 * 
	 * @return
	 *   the current maximum acceptable value.
	 */
	public int getMaximum() {
		return max;
	}
	
	/**
	 * Sets the maximum value of scale the progress arc is capable of showing.
	 * 
	 * @param max
	 *   the maximum value of the scale of values the progress arc will show.
	 */
	public void setMaximum(int max) {
		this.max = max;
	}
	
	/**
	 * Returns the current progress arc value.
	 * 
	 * @return
	 *   the current value of the progress arc.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Sets the current progress arc value.
	 * 
	 * @param value
	 *   the current value of the progress arc.
	 */
	public void setValue(int value) { 
		if(value < min  || value > max) {
			throw new IllegalArgumentException("The value " + value + " is out of the admitted range [" + min + ", " + max + "].");
		}
		if(value != min && value != max) {
			if(timer == null) {
				logger.trace("creating timer");
				timer = new Timer(50, this);
			}
			if(!timer.isRunning()) {
				logger.trace("starting timer");
				timer.start();
			}
		} else {
			logger.trace("value is '{}' (in range [{}, {}]", value, min, max);
			if(timer != null && timer.isRunning()) {
				logger.trace("stopping timer");
				timer.stop();
				// unless we force a repaint, the component may stop at 99%
				repaint();
			}
		}
		this.value = value;
	}
	
	/**
	 * Sets the current progress arc value.
	 * 
	 * @param value
	 *   the current value of the progress arc.
	 * @param text
	 *   the text to be shown inside the progress arc.
	 */
	public void setValue(int value, String text) {
		setValue(value);
		setText(text);
	}
	
	/**
	 * Sets the current progress arc value.
	 * 
	 * @param value
	 *   the current value of the progress arc.
	 * @param icon
	 *   the icon to be shown inside the progress arc.
	 */
	public void setValue(int value, Icon icon) {
		setValue(value);
		setIcon(icon);
	}
	
	/**
	 * Returns whether the progress arc is in indeterminate mode.
	 * 
	 * @return
	 *   whether the progress arc is in indeterminate mode.
	 */
	public boolean isIndeterminate() {
		return indeterminate;
	}
	
	/**
	 * Sets whether the progress arc is in indeterminate mode.
	 *
	 * @param indeterminate
	 *   whether the progress arc is in indeterminate mode.
	 */
	public void setIndeterminate(boolean indeterminate) {
		this.indeterminate = indeterminate;
	}
	
	/**
	 * Returns the mode for showing the current value.
	 * 
	 * @return
	 *   the current mode for showing the current value.
	 */
	public ValueMode getValueMode() {
		return valueMode;
	}
	
	/**
	 * Sets the new mode for showing the current value.
	 * 
	 * @param mode
	 *   the new mode for showing the current value.
	 */
	public void setValueMode(ValueMode mode) {
		this.valueMode = mode;
	}

	/**
	 * Returns the mode for showing something inside the progress arc.
	 * 
	 * @return
	 *   the current mode for showing something inside the progress arc: whether
	 *   the widget shows text, an icon, or which one it should prefer if both
	 *   are available.
	 */
	public ViewMode getViewMode() {
		return viewMode;
	}
	
	/**
	 * Sets the new mode for showing something inside the progress arc.
	 * 
	 * @param mode
	 *   the new mode for showing something inside the progress arc.
	 */
	public void setViewMode(ViewMode mode) {
		this.viewMode = mode;
	}
	
	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		
		int width = getWidth();
		int height = getHeight();
		
		if(offscreen == null) {
			// prepare the graphic buffer for off-screen painting
			logger.trace("dimensions: {} x {}", width, height);
			offscreen = createImage(width, height);				
			logger.trace("offscreeen is null? {}", offscreen == null);			
		}
				
		Graphics2D graphics = (Graphics2D)offscreen.getGraphics();				
		
		// only if the component is opaque should we set the background colour
		if(isOpaque()) {
			graphics.setBackground(getBackground());
		}
		
		graphics.setFont(getFont());
		
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		
		// clear the background of the mirror device
		graphics.clearRect(0, 0, width, height); 
		
		// paint the background circle
		graphics.setColor(circle);
		graphics.drawOval(padding + thickness, padding + thickness, width - (2 * (padding + thickness)), height - (2 * (padding + thickness)));
		
		// draw the progress arc
		graphics.setColor(arc);
		String string = null;
		Icon icon = null;
		if(isIndeterminate() && value != max) {
			graphics.drawArc(padding + thickness, padding + thickness, width - (2 * (padding + thickness)), height - (2 * (padding + thickness)), start, -120);
			switch(viewMode) {
			case TEXT_ONLY:
				string = getText();
				break;
			case ICON_ONLY:
				icon = getIcon();
				break;
			case TEXT_OR_ICON:
				string = getText();
				if(string == null) {
					icon = getIcon();
				}
				break;
			case ICON_OR_TEXT:
				icon = getIcon();
				if(icon == null) {
					string = getText();
				}
				break;
			}			
		} else {
			int percent = value * 100 / (max - min);
			logger.trace("percent is now '{}'", percent);
			int angle = (int)(percent * 360 / 100);
			graphics.drawArc(padding + thickness, padding + thickness, width - (2 * (padding + thickness)), height - (2 * (padding + thickness)), start, -angle);
			
			switch(valueMode) {
			case VALUE:
				string = Integer.toString(value);
				break;
			case PERCENT:
				string = Integer.toString(percent) + "%";
				break;
			case STEPS:
				string = Integer.toString(value - min) + "/" + Integer.toString(max - min);
				break;
			case USER:
				switch(viewMode) {
				case TEXT_ONLY:
					string = getText();
					break;
				case ICON_ONLY:
					icon = getIcon();
					break;
				case TEXT_OR_ICON:
					string = getText();
					if(string == null) {
						icon = getIcon();
					}
					break;
				case ICON_OR_TEXT:
					icon = getIcon();
					if(icon == null) {
						string = getText();
					}
					break;
				}
				break;
			}
		}
		
		if(string != null) {
			logger.trace("drawing text: '{}'", string);
			Rectangle2D boundary = graphics.getFontMetrics().getStringBounds(string, graphics);   
			graphics.drawString(string, (int)(width - boundary.getWidth()) / 2, (int)(height + boundary.getHeight())/ 2);				
		} else if(icon != null) {
			// TODO: draw the icon
			icon.paintIcon(this, graphics, (width - icon.getIconWidth()) / 2, (height - icon.getIconHeight()) / 2);
		}
				
		// copy the mirror device onto the main device
		g.drawImage(offscreen, 0, 0, this); 

		start = start - 1 % 360;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		repaint();
	}	
}
