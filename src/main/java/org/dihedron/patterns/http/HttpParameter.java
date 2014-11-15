/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;

import org.dihedron.core.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Andrea Funto'
 */
@License
public abstract class HttpParameter {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(HttpParameter.class);

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
	 * Concatenates a list of HTTP parameters (which must NOT be of type "FILE")
	 * and URL-encodes them.
	 * 
	 * @param parameters
	 *   a list of {@link HttpParameter}s.
	 * @return
	 *   a string representation of the key/value pairs, joined via the ampersand.
	 * @throws HttpClientException
	 *   if a "FILE" parameter was provided.
	 */
	public static String concatenate(Collection<HttpParameter> parameters) throws HttpClientException {
		StringBuilder buffer = new StringBuilder();
		for(HttpParameter parameter : parameters) {
			if(parameter.getType() == Type.FILE) {
				logger.error("parameters '{}' has type '{}' and cannot be URL-encoded", parameter.getName(), parameter.getType().name());
				throw new HttpClientException("Error URL-encoding a parameter of type 'FILE'");
			}
			buffer.append(buffer.length() > 0 ? "&" : "");
			try {
				buffer
					.append(URLEncoder.encode(((HttpTextParameter)parameter).getName(), "UTF-8"))
					.append("=")
					.append(URLEncoder.encode(((HttpTextParameter)parameter).getValue().toString(), "UTF-8"));
			} catch(UnsupportedEncodingException e) {
				// NOTE: this should NEVER NEVER happen!
				logger.error("unsupported encoding", e);
			}
		}
		return buffer.toString();
	}
	
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
	
	/**
	 * Returns the hash code of this parameter, calculated on the name of the 
	 * parameter; we ignore type and class because we want different parameters
	 * sharing the same name to be considered instances of the same parameter,
	 * so one may replace the other in the set of request parameters.
	 *  
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return name.hashCode(); 
	}
	
	/**
	 * Checks whether two parameters have the same name.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return other != null && other instanceof HttpParameter && other.hashCode() == this.hashCode();
	}
}
