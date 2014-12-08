/*
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.dihedron.core.strings.Strings;
import org.dihedron.core.url.URLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class to help manipulate fonts.
 * 
 * @author Andrea Funto'
 */
public class Fonts {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(Fonts.class);

	public static Font loadDefaultFont(int style, int size) {
		Font font = null;
		try(InputStream input = URLFactory.makeURL("classpath:org/dihedron/ui/verdana.ttf").openStream()) {
			font = Font.createFont(Font.TRUETYPE_FONT, input).deriveFont(style, size);				
		} catch (FontFormatException | IOException e) {
			logger.error("error loading font", e);		
		}			
		return font;
	}
	
	public static Font loadFont(String name, int style, int size) {
		Font font = null;
		if(Strings.isValid(name)) {
			font = new Font(name, style, size);
		} else {
			font = loadDefaultFont(style, size);
		}
		return font;
	}
	
	/**
	 * Private constructor, to prevent instantiation.
	 */
	private Fonts() {
	}
}
