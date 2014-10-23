/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.cache;

import java.io.InputStream;

/**
 * @author Andrea Funto'
 */
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
