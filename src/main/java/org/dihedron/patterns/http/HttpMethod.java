/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.http;

import org.dihedron.core.License;


/**
 * An enumeration of HTTP methods.
 * 
 * @author Andrea Funto'
 */
@License
public enum HttpMethod {
	
	/**
	 * The OPTIONS HTTP method.
	 */
	OPTIONS,
	
	/**
	 * The GET HTTP method.
	 */
	GET,
	
	/**
	 * The HEAD HTTP method.
	 */
	HEAD,
	
	/**
	 * The POST HTTP method.
	 */
	POST,
	
	/**
	 * The PUT HTTP method.
	 */
	PUT,
	
	/**
	 * The DELETE HTTP method.
	 */
	DELETE,
	
	/**
	 * The TRACE HTTP method.
	 */
	TRACE,
	
	/**
	 * The CONNECT HTTP method.
	 */
	CONNECT
}
