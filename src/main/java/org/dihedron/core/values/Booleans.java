/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.values;

import org.dihedron.core.License;
import org.dihedron.core.strings.Strings;


/**
 * Utility package for boolean operations.
 * 
 * @author Andrea Funto'
 */
@License
public final class Booleans {
	
	/**
	 * Returns whether the input string is a valid representation of the boolean
	 * {@code true} value.
	 * 
	 * @param string
	 *   an input string.
	 * @return
	 *   whether the string represents the boolean {@code true} value.
	 */
	public static final boolean isTrue(String string) {
		return Strings.isValid(string) && string.equalsIgnoreCase("true"); 
	}
	
	/**
	 * Returns whether the input string is a valid representation of the boolean
	 * {@code false} value.
	 * 
	 * @param string
	 *   an input string.
	 * @return
	 *   whether the string represents the boolean {@code false} value.
	 */
	public static final boolean isFalse(String string) {
		return Strings.isValid(string) && string.equalsIgnoreCase("false"); 
	}
	
	/**
	 * Converts the input object to a boolean value, either {@code true} or 
	 * {@code false}, according to the following rules:<ol>
	 * <li>if the input object is a {@code Boolean} it is returned as is</li>
	 * <li>if the input item is a {@code String}, it is tested for case-insensitive 
	 * equality to the "true" string; in any other case it is considered false.</li>
	 * <li>if is an integral number (an integer, a short, a long, a byte), it is
	 * considered true if positive, false if negative or zero</li>
	 * <li>if it is an object, it is considered true if not null</li>
	 * </ol>.
	 *  
	 * @param object
	 *   the object being evaluated as a boolean.
	 * @return
	 *   a Boolean value.
	 */
	public static Boolean toBoolean(Object object) {
		if(object == null) {
			return false;
		}
		if(object instanceof Boolean) {
			return (Boolean)object;
		}
		if(object instanceof String) {
			return object.toString().equalsIgnoreCase("true");
		}
		if(object instanceof Byte) {
			return (Byte)object > 0;
		}
		if(object instanceof Short) {
			return (Short)object > 0;
		}
		if(object instanceof Integer) {
			return (Integer)object > 0;
		}
		if(object instanceof Long) {
			return (Long)object > 0;
		}
		return false;
	}
	
	/**
	 * Private constructor, to prevent improper instantiation.
	 */
	private Booleans() {
		
	}
}
