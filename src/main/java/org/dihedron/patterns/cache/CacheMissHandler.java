/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.cache;

import java.io.InputStream;

import org.dihedron.core.License;

/**
 * @author Andrea Funto'
 */
@License
public interface CacheMissHandler {
	
	/**
	 * Retrieves an input stream from which the missing 
	 * resource can be read.
	 * 
	 * @return
	 *   an input stream from which the missing resource
	 *   can be read.
	 */
	public InputStream getAsStream() throws CacheException;
}
