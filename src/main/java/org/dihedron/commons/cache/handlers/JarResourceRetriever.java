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

package org.dihedron.commons.cache.handlers;

import java.io.InputStream;

import org.dihedron.commons.cache.CacheException;
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
	 * @see org.dihedron.commons.cache.handlers.CacheMissHandler#getAsStream()
	 */
	public InputStream getAsStream() throws CacheException {		
		return clazz.getResourceAsStream(resource);
	}
}
