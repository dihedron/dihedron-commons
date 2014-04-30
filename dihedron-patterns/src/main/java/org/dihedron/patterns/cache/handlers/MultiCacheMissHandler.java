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

package org.dihedron.patterns.cache.handlers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dihedron.patterns.cache.CacheException;
import org.dihedron.patterns.cache.CacheMissHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles multiple cache miss handlers at one.
 * 
 * @author Andrea Funto'
 */
public class MultiCacheMissHandler implements CacheMissHandler {

	/** 
	 * The logger. 
	 */
	private static Logger logger = LoggerFactory.getLogger(MultiCacheMissHandler.class);
	
	/** 
	 * The list of handlers. 
	 */
	private List<CacheMissHandler> handlers;
	
	/**
	 * Constructor.
	 */
	public MultiCacheMissHandler() {
		logger.debug("instantiating empty multi-provider CacheMissHandler");
		this.handlers = new ArrayList<CacheMissHandler>();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param handlers
	 *   a list of <code>CacheMissHandler</code>s.
	 */
	public MultiCacheMissHandler(List<CacheMissHandler> handlers) {
		if(handlers != null) {
			logger.debug("instantiating multi-provider CacheMissHandler, initial list has {} elements", handlers.size());
			this.handlers = new ArrayList<CacheMissHandler>(handlers);
		} else {
			logger.debug("instantiating empty multi-provider CacheMissHandler");
			this.handlers = new ArrayList<CacheMissHandler>();
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param array
	 *   an array of <code>CacheMissHandler</code>s.
	 */
	public MultiCacheMissHandler(CacheMissHandler [] array) {
		logger.debug("instantiating multi-provider CacheMissHandler");
		this.handlers = new ArrayList<CacheMissHandler>();
		// copy handlers into array
		if(array != null) {
			logger.debug("adding {} elements to handlers", array.length);
			for(CacheMissHandler handler : array) {
				logger.debug("adding handler of class '{}'", handler.getClass());
				this.handlers.add(handler);
			}
		}
	}
	
	/**
	 * Appends a <code>CacheMissHandler</code> to the internal list of handlers.
	 * 
	 * @param handler
	 *   the <code>CacheMissHandler</code> to be added.
	 */
	public void addHandler(CacheMissHandler handler) {
		if(handler != null) {
			logger.debug("adding handler of class '{}'", handler.getClass());
			handlers.add(handler);
		}
	}
	
	/**
	 * Attempts a resource retrieval from each of the provided 
	 * <code>CacheMissHandler</code>s; as soon as one succeeds, it returns it as
	 * an <code>InputStream</code>.
	 * 
	 * @return
	 *   the resource as an <code>InputStream</code>, as soon as any of the 
	 *   <code>CacheMissHandler</code>s succeeds, null otherwise.
	 */
	public InputStream getAsStream() throws CacheException {
		List<Exception> exceptions = new ArrayList<Exception>(); 
		for (CacheMissHandler handler : handlers) {
			try {
				logger.debug("trying handler of class '{}'", handler.getClass());
				InputStream stream = handler.getAsStream();
				if(stream != null) {
					logger.debug("success, returning stream");
					return stream;
				}
			} catch(Exception e) {
				logger.error("error during resource retrieval", e);
				exceptions.add(e);
			}
		}
		
		if(exceptions.size() > 0) {
			logger.warn("failure, resource not found, and {} exceptions occurred", exceptions.size());
			throw new CacheException("Resource not found", exceptions);
		}
		logger.warn("failure, resource not found");
		return null;
	}	
}
