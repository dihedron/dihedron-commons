/*
 * 
 */
package org.dihedron.ui;

import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.List;

import org.dihedron.core.License;
import org.dihedron.core.regex.Regex;
import org.dihedron.ui.Colors;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class ColorsTest {

	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ColorsTest.class);
	
	/**
	 * Test method for {@link org.dihedron.ui.Colors#fromString(java.lang.String)}.
	 */
	@Test
	public void testFromString() {
		final String [] values = {
			"RGB(55,155,255)",
			"RGB (55,155,255)",
			"RGB ( 55,155,255)",
			"RGB ( 55 ,155,255)",
			"RGB ( 55 , 155,255)",
			"RGB ( 55 , 155 ,255)",
			"RGB ( 55, 155 , 255 )",
			"rgb(55,155,255)",
			"rgb (55,155,255)",
			"rgb ( 55,155,255)",
			"rgb ( 55 ,155,255)",
			"rgb ( 55 , 155,255)",
			"rgb ( 55 , 155 ,255)",
			"rgb ( 55, 155 , 255 )",
			"#ABC",
			"#AABBCC",
			"#aBc",
			"#aAbBcC",
			"#abc",
			"#aabbcc"
		};
		
		for(String value : values) {
			Color color = Colors.fromString(value);
			logger.trace("value '{}' has become color '{}'", value, Colors.toRGB(color));
			assertTrue(color != null);
		}		
	}
	
	/**
	 * Test method for {@link org.dihedron.ui.Colors#toString(java.awt.Color)}.
	 */
	@Test
	public void testToStringColor() {
		Color color = Colors.fromString("rgb(255,255,255)");
		String rgb = Colors.toRGB(color);
		logger.trace("RGB string: '{}'", rgb);
		assertTrue("rgb(255,255,255)".equalsIgnoreCase(rgb));
		String hex = Colors.toHex(color);
		logger.trace("Hex string: '{}'", hex);
		assertTrue("#FFFFFF".equalsIgnoreCase(hex));
	}

	/**
	 * This is not a real test: this is only to show the capturing groups in 
	 * action when stressed by different input patterns.
	 */
//	@Test
	public void showRegExMatches() {
		final String [] values = {
			"RGB(111,222,333)",
			"RGB (111,222,333)",
			"RGB ( 111,222,333)",
			"RGB ( 111 ,222,333)",
			"RGB ( 111 , 222,333)",
			"RGB ( 111 , 222 ,333)",
			"RGB ( 111, 222 , 333 )",
			"rgb(111,222,333)",
			"rgb (111,222,333)",
			"rgb ( 111,222,333)",
			"rgb ( 111 ,222,333)",
			"rgb ( 111 , 222,333)",
			"rgb ( 111 , 222 ,333)",
			"rgb ( 111, 222 , 333 )",
			"#ABC",
			"#AABBCC",
			"#aBc",
			"#aAbBcC",
			"#abc",
			"#aabbcc",
		};
		
		String COLOR_REGEX = "^(?:(\\#)(([0-9a-f]{2})([0-9a-f]{2})([0-9a-f]{2})|([0-9a-f]{1})([0-9a-f]{1})([0-9a-f]{1}))|(rgb)\\s*\\(\\s*(?:(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3}))\\s*\\)\\s*)$";
		
		Regex regex = new Regex(COLOR_REGEX, false);
		for(String value : values) {
			logger.trace("============================================ {} ============================================", value);
			if(regex.matches(value)) {
				List<String[]> list = regex.getAllMatches(value);
				for(String[] matches : list) {
					int i = 0;					
					for(String match : matches) {
						logger.trace("match[{}]: '{}'", i++, match);
					}
				}
				logger.trace("------------------------------------------------------------------------------------------");
			} else {
				logger.trace("                                        NOT MATCHING                                        ");
			}
		}
	}

}
