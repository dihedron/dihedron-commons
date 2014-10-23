/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.core.url;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class to create custom URLs, associating them with their respective stream 
 * handlers; this allows to create URLs that refer to custom protocols such as 
 * <code>classpath:</code>.
 * 
 * @author Andrea Funto'
 */
public final class URLFactory {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(URLFactory.class);

	/**
	 * Returns an URL object for the given URL specification.
	 * 
	 * @param specification
	 *   the URL specification.
	 * @return
	 *   an URL object; if the URL is of "classpath://" type, it will return an URL
	 *   whose connection will be opened by a specialised stream handler. 
	 * @throws MalformedURLException
	 */
	public static URL makeURL(String specification) throws MalformedURLException {
		logger.trace("retrieving URL for specification: '{}'", specification);
		if(specification.startsWith("classpath:")) {
			logger.trace("URL is of type 'classpath'");
			return new URL(null, specification, new ClassPathURLStreamHandler());
		} 
		logger.trace("URL is of normal type");
		return new URL(specification);
	}
	
	/**
	 * Private constructor, to prevent instantiation.
	 */
	private URLFactory() {
	}
}
