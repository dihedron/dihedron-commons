/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.http;


/**
 * @author Andrea Funto'
 */
public class HttpTextParameter extends HttpParameter {
	
	/**
	 * The value of the HTTP parameter.
	 */
	private Object value;
	
	/**
	 * Constructor.
	 *
	 * @param type
	 *   the type of the HTTP parameter.
	 * @param name
	 *   the name of the HTTP parameter.
	 * @param value
	 *   the value of the HTTP parameter.
	 */
	public HttpTextParameter(Type type, String name, Object value) {
		super(type, name);
		this.value = value;
	}	
	
	/**
	 * Returns the value of the HTTP parameter.
	 * 
	 * @return
	 *   the value of the HTTP parameter.
	 */
	public Object getValue() {
		return value;
	}
}
