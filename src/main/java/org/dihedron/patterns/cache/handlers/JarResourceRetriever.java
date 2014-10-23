/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.patterns.cache.handlers;

import java.io.InputStream;

import org.dihedron.patterns.cache.CacheException;
import org.dihedron.patterns.cache.CacheMissHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class retrieves a resource from the JAR, using the class loader methods, 
 * and returns it as a stream.
 * 
 * @author Andrea Funto'
 */
public class JarResourceRetriever implements CacheMissHandler {
	
	/** 
	 * The logger. 
	 */
	private static Logger logger = LoggerFactory.getLogger(JarResourceRetriever.class);
	
	/** 
	 * A class in the same package as the resource. 
	 */
	private Class<?> clazz;
	
	/** 
	 * The name of the resource. 
	 */
	private String resource;
	
	/**
	 * Constructor.
	 * 
	 * @param clazz
	 *   a class in the same package as the resource to be retrieved.
	 * @param resource
	 *   the name of the resource.
	 */
	public JarResourceRetriever(Class<?> clazz, String resource) {
		this.clazz = clazz;
		this.resource = resource;
		logger.debug("retrieving resource '{}' from path of class '{}'", resource, clazz.getName());
	}

	/**
	 * @see org.dihedron.patterns.cache.CacheMissHandler#getAsStream()
	 */
	public InputStream getAsStream() throws CacheException {		
		return clazz.getResourceAsStream(resource);
	}
}
