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
package org.dihedron.patterns;


/**
 * Provides minimal abstraction over the Singleton pattern. It's not possible to 
 * wrap the real Singleton pattern in a Java Generic due to the way templates are
 * realised in Java (type erasure). Java is not C++, Generics are not templates.
 *  
 * @author Andrea Funto'
 */
public final class Singleton {

	/**
	 * The single instance.
	 */
	private static Object singleton;
	
	/**
	 * Sets a reference to the single instance.
	 * 
	 * @param reference
	 *   the single object instance.
	 */
	public static <T> void set(T reference) {
		singleton = reference;
	}
	
	/**
	 * Returns the reference to the single object instance.
	 * 
	 * @return
	 *   the reference to the single object instance.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get() {
		return (T)singleton;
	}
		
	/**
	 * Clears the reference ot the single object instance.
	 */
	public static <T> void clear() {
		singleton = null;
	}
	
	/**
	 * Private constructor, to prevent instantiation.
	 */
	private Singleton() {
	}
}
