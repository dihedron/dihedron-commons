/*
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */
package org.dihedron.ui;

import java.awt.Color;

import org.dihedron.core.regex.Regex;
import org.dihedron.core.strings.Strings;

/**
 * A class to convert RGB string to colors an vice-versa.
 * 
 * @author Andrea Funto'
 */
public final class Colors {
	
	/**
	 * The regular expression that color parameters must match (e.g. RGB(12,34,56), #ABC or #AABBCC).
	 */	
	private static final String COLOR_REGEX = "^(?:(\\#)(([0-9a-f]{2})([0-9a-f]{2})([0-9a-f]{2})|([0-9a-f]{1})([0-9a-f]{1})([0-9a-f]{1}))|(rgb)\\s*\\(\\s*(?:(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3}))\\s*\\)\\s*)$";
	
	/**
	 * Parses an input string and returns the corresponding colour object.
	 * 
	 * @param value
	 *   the string, in "#(AB,CD,EF)" or "rgb(AB,CD,EF)" format.
	 * @return
	 *   the {@code Color} object, or null.
	 */
	public static final Color fromString(String value) {		
		if(Strings.isValid(value)) {
			Regex regex = new Regex(COLOR_REGEX, false);
			if(regex.matches(value.trim())) {
				int r = -1, g = -1, b = -1;
				String[] matches = regex.getAllMatches(value).get(0);
				if(matches[0] != null && matches[0].equals("#")) {
					// this is an hex number, it can be #ABC or #AABBCC
					if(matches[1].length() == 3) {
						// it's #ABC, which must be parsed as AA BB CC
						r = Integer.decode("0X" + matches[5] + matches[5]);
						g = Integer.decode("0X" + matches[6] + matches[6]);
						b = Integer.decode("0X" + matches[7] + matches[7]);						
					} else if(matches[1].length() == 6) {
						// it's #1A2B3C, parsed as 1A 2B 3C
						r = Integer.decode("0X" + matches[2]);
						g = Integer.decode("0X" + matches[3]);
						b = Integer.decode("0X" + matches[4]);						
					}
				} else if(matches[8] != null && matches[8].equalsIgnoreCase("rgb")) {
					// it's rgb(111,222,333)	
					r = Integer.parseInt(matches[9]);
					g = Integer.parseInt(matches[10]);
					b = Integer.parseInt(matches[11]);						
				}
				if(r <= 255 && g <= 255 && b <= 255) {
					return new Color(r, g, b);
				}
			}
		}
		return null;
	}

	/**
	 * Returns the input color as an RGB tuple (e.g. "rgb(AB,CD,EF)").
	 * 
	 * @param color
	 *   the color to represent as an RGB tuple.
	 * @return
	 *   a string representing the RGB tuple of integer values. 
	 */
	public static String toRGB(Color color) {
		if(color != null) {
			return "rgb(" + Integer.toString(color.getRed()) + "," + Integer.toString(color.getGreen()) + "," + Integer.toString(color.getBlue()) + ")"; 
		}
		return null;
	}
	
	/**
	 * Returns the input color as an hexadecimal value (e.g. "#ABCDEF)").
	 * 
	 * @param color
	 *   the color to represent as an hexadecimal tuple.
	 * @return
	 *   a string representing the RGB tuple of values. 
	 */
	public static String toHex(Color color) {
		if(color != null) {
			return "#" + Integer.toHexString(color.getRed()) + Integer.toHexString(color.getGreen()) + Integer.toHexString(color.getBlue()); 
		}
		return null;
	}	
	
	/**
	 * Private constructor, to prevent instantiation. 
	 */
	private Colors() {
	}
}
