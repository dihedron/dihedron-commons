/**
 * 
 */
package org.dihedron.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.BoundedRangeModel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import org.dihedron.core.strings.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class JProgressArc extends JProgressBar implements ActionListener {

	/**
	 * An enumeration representing whether the progress arc should show a progress
	 * string, the current percentage, the number of steps over the total (e.g. 
	 * 2/10), or whatever was provided by the user (text or icon) while progressing.
	 * 
	 * @author Andrea Funto'
	 *
	 */
	public enum Mode {
		
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
		 * Draw whatever was provided by the user for rendering (text or icon).
		 */
		PROVIDED
	}
	
	/**
	 * An enumeration expressing the rotation speed of the progress arc.
	 *  
	 * @author Andrea Funto'
	 */
	public enum Speed {
		
		/**
		 * The progress arc will not rotate.
		 */
		NONE(0),
		
		/**
		 * The progress arc will rotate very slowly.
		 */
		SLOW(1),
		
		/**
		 * The progress arc will rotate at a medium speed.
		 */
		MEDIUM(5),
		
		/**
		 * The progress arc will rotate quite fast.
		 */
		FAST(13),
		
		/**
		 * The progress arc will rotate extremely fast.
		 */
		VERY_FAST(34);
		
		/**
		 * Constructor.
		 * 
		 * @param value
		 *   the value to be used for incrementing the base angle.
		 */
		private Speed(int value) {
			this.value = value;
		}
		
		/**
		 * The value used for incrementing the base angle.
		 */
		private int value;
		
		/**
		 * Returns the value used for incrementing the base angle.
		 * 
		 * @return
		 *   the value used for incrementing the base angle.
		 */
		public int getValue() {
			return value;
		}
	}

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = -4843184688409841197L;
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(JProgressArc.class);	
	
	/**
	 * A mirror graphic device the indicator will pain to, that help avoid image 
	 * flickering when painting. 
	 */
    private Image offscreen;
    
	/**
	 * The colour of the circle under the progress arc.
	 */
	private Color circle = Color.GRAY;    

	/**
	 * The padding between border and progress circle.
	 */
	private int padding = 0;
    
    /**
     * The thickness of the progress arc.
     */
	private int thickness = 4;
	
	/**
	 * The timer used to animate the progress arc.
	 */
	private Timer timer = null;	
	
	/**
	 * The user-provided text to show in {@code ProgressMode.PROVIDED} mode.
	 */
	private String text = null;
	
	/**
	 * The rendering mode when showing progress (that is percent is between 0% 
	 * and 100%).
	 */
	private Mode mode = Mode.PERCENT;
	
	/**
	 * The speed at which the progress arc will rotate.
	 */
	private Speed speed = Speed.MEDIUM;

	/**
	 * The start angle of the progress arc.
	 */
	private int start = 0;	
	
	/**
	 * Constructor.
	 */
	public JProgressArc() {
	}

	/**
	 * Constructor.
	 * 
	 * @param model
	 *   the data model for the progress arc.
	 */
	public JProgressArc(BoundedRangeModel model) {
		super(model);
	}

	/**
	 * Constructor.
	 * 
	 * @param min
	 *   the minimum value of the progress arc.
	 * @param max
	 *   the maximum value of the progress arc.
	 */
	public JProgressArc(int min, int max) {
		super(min, max);
	}
	
	/**
	 * Sets the current progress arc value.
	 * 
	 * @param value
	 *   the current value of the progress arc.
	 */
	@Override
	public void setValue(int value) {
		int min = getMinimum();
		int max = getMaximum();
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
		super.setValue(value);
	}
	
	/**
	 * Sets the current progress arc value and associated text.
	 * 
	 * @param value
	 *   the current value of the progress arc.
	 * @param text
	 *   the text to show if in {@code ProgressMode.PROVIDED} mode.
	 */
	public void setValue(int value, String text) {
		this.text = text;
		setValue(value);
	}	
	
	/**
	 * Sets the text to show.
	 * 
	 * @param text
	 *   the text to show.
	 */
	public void setText(String text) {
		this.text = text;
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
	 * Returns the mode for showing the current value.
	 * 
	 * @return
	 *   the current mode for showing the current value.
	 */
	public Mode getMode() {
		return mode;
	}
	
	/**
	 * Sets the new mode for showing the current value.
	 * 
	 * @param mode
	 *   the new mode for showing the current value.
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public Speed getSpeed() {
		return speed;
	}
	
	public void setSpeed(Speed speed) {
		this.speed = speed;
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
		int percent = getValue() * 100 / (getMaximum() - getMinimum());
		graphics.setColor(getForeground());		
		if(isIndeterminate() && getValue() != getMaximum()) {
			// indeterminate mode, not complete or starting
			graphics.drawArc(padding + thickness, padding + thickness, width - (2 * (padding + thickness)), height - (2 * (padding + thickness)), start, -120);
		} else {
			// determinate mode			
			logger.trace("percent is now '{}'", percent);
			int angle = (int)(percent * 360 / 100);
			graphics.drawArc(padding + thickness, padding + thickness, width - (2 * (padding + thickness)), height - (2 * (padding + thickness)), start, -angle);
		}
		
		// now, if there are subcomponents, draw them, otherwise draw text
		synchronized(getTreeLock()) {
			String string = null;
			
			if(getComponentCount() > 0) {
				for(Component component : this.getComponents()) {
					component.paint(graphics);
				}
			} else {
				switch(mode) {
				case VALUE:
					string = Integer.toString(getValue());
					break;
				case PERCENT:
					string = Integer.toString(percent) + "%";
					break;
				case STEPS:
					string = Integer.toString(getValue() - getMinimum()) + "/" + Integer.toString(getMaximum() - getMinimum());
					break;
				case PROVIDED:
					string = text;
					break;
				}
			}
			
			if(Strings.isValid(string)) {
				logger.trace("drawing text: '{}'", string);
				Rectangle2D boundary = graphics.getFontMetrics().getStringBounds(string, graphics);   
				graphics.drawString(string, (int)(width - boundary.getWidth()) / 2, (int)(height + boundary.getHeight())/ 2);				
			}
		}
				
		// copy the mirror device onto the main device
		g.drawImage(offscreen, 0, 0, this); 

		start = start - speed.getValue() % 360;
	}	
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		repaint();
	}		
}
