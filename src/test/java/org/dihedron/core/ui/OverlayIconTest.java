/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.ui;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.dihedron.ui.OverlayIcon;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class OverlayIconTest {
	
	/**
	 * The logger.
	 */
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(OverlayIconTest.class);

	private class TestUI extends JLabel {
		
		/**
		 * Serial version id.
		 */
		private static final long serialVersionUID = 3966501435275802967L;
		
		private JLabel label;
		
		public TestUI() {
			ImageIcon icon = new OverlayIcon("java-swing-tutorial.JPG", "My Website").withOverlay(new ImageIcon());
			setLayout(new GridLayout(1, 1));
			label = new JLabel("Overlay Icon", icon, JLabel.CENTER);
			label.setVerticalTextPosition(JLabel.BOTTOM);
			label.setHorizontalTextPosition(JLabel.CENTER);
			add(label);
		}		
	}
	
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//	}

	/**
	 * Test method for {@link org.dihedron.ui.OverlayIcon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)}.
	 */
	@Test
	public void testPaintIconComponentGraphicsIntInt() {
		JFrame frame = new JFrame("Overlay Icon Usage Demo");
		frame.addWindowListener(new WindowAdapter() {			
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setContentPane(new TestUI());
		frame.pack();
		frame.setVisible(true);		
	}
}
