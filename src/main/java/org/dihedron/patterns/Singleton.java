/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns;

import org.dihedron.core.License;


/**
 * Provides minimal abstraction over the Singleton pattern. It's not possible to 
 * wrap the real Singleton pattern in a Java Generic due to the way templates are
 * realised in Java (type erasure). Java is not C++, Generics are not templates.
 *  
 * @author Andrea Funto'
 */
@License
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
