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
package org.dihedron.patterns.cache.storage;

import java.io.OutputStream;

import org.dihedron.core.regex.Regex;
import org.dihedron.patterns.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A storage class that provides a way to access read-only resources such as web 
 * URLs or class-path resources as if they were in an actual physical storage.
 * 
 * @author Andrea Funto'
 */
public abstract class ReadOnlyStorage extends AbstractStorage {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(ReadOnlyStorage.class);
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#store(java.lang.String)
	 */
	@Override
	final public OutputStream store(String resource) throws CacheException {
		logger.error("trying to write resource '{}' into read only cache", resource);
		throw new CacheException("cache is read only");
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#delete(org.dihedron.core.regex.Regex)
	 */
	@Override
	public void delete(Regex regex) {
		logger.error("trying to delete resources matching /{}/ from read-only cache", regex);
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#delete(java.lang.String, boolean)
	 */
	@Override
	public void delete(String resource, boolean caseInsensitive) {
		 logger.error("trying to delete resource '{}' from read-only cache", resource);
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#clear()
	 */
	@Override
	public void clear() {
		logger.error("trying to clear read-only cache");
	}
}
