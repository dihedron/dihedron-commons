/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.http;


/**
 * @author Andrea Funto'
 */
public abstract class HttpParameter {

	/**
	 * The type of parameter.
	 * 
	 * @author Andrea Funto'
	 */
	public enum Type {
		/**
		 * A form parameter that can be mapped to text: these include simple
		 * inputs, password fields, check boxes and radio buttons, etc.
		 */
		TEXT,
				
		/**
		 * A file (either provided as a File object or as a stream).
		 */
		FILE
	}
	
	/**
	 * The parameter type.
	 */
	private Type type;
	
	/**
	 * The name of the parameter.
	 */
	private String name;
	
	/**
	 * Constructor.
	 *
	 * @param type
	 *   the parameter type.
	 * @param name
	 *   the name of the parameter.
	 */
	protected HttpParameter(Type type, String name) {
		this.type = type;
		this.name = name;
	}
		
	/**
	 * Returns the type of HTTP parameter.
	 * 
	 * @return
	 *   the type of HTTP parameter.
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Returns the name of the HTTP parameter.
	 * 
	 * @return
	 *   the name of the HTTP parameter.
	 */
	public String getName() {
		return name;
	}
}
