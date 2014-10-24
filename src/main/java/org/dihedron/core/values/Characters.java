/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.values;

import java.awt.event.KeyEvent;

import org.dihedron.core.License;

/**
 * @author Andrea Funto'
 */
@License
public final class Characters {
	
	/**
	 * Checks whether the given character is printable.
	 * 
	 * @param c
	 *   the character to test.
	 * @return
	 *   whether the character is printable.
	 */
	public static boolean isPrintable( char c ) {
	    Character.UnicodeBlock block = Character.UnicodeBlock.of( c );
	    return (!Character.isISOControl(c)) 	&& 
	    		c != KeyEvent.CHAR_UNDEFINED 	&&
	            block != null 					&&
	            block != Character.UnicodeBlock.SPECIALS;
	}	
	
	/**
	 * Private constructor to prevent construction.
	 */
	private Characters() {
		
	}
}
