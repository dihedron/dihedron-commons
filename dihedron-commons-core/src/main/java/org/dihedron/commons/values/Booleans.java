/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Dihedron Common Utilities library ("Commons").
 *
 * "Commons" is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * "Commons" is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with "Commons". If not, see <http://www.gnu.org/licenses/>.
 */


package org.dihedron.commons.values;

import org.dihedron.commons.strings.Strings;


/**
 * Utility package for boolean operations.
 * 
 * @author Andrea Funto'
 */
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
	 * Private constructor to prevent utility class instantiation. 
	 */
	private Booleans() {
	}	
}
