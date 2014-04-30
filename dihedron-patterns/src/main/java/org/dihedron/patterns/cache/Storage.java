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

package org.dihedron.patterns.cache;

import java.io.InputStream;

import org.dihedron.core.regex.Regex;

/**
 * @author Andrea Funto'
 */
public interface Storage {
	
	/**
	 * Checks whether the Storage is empty.
	 * 
	 * @return
	 *   <code>true</code> if it is empty, <code>false</code> otherwise.
	 */
	boolean isEmpty();
		
	/**
	 * Checks whether the storage contains the given resource.
	 * 
	 * @param resource
	 *   the key of the resource.
	 * @return
	 *   <code>true</code> if the resource is in the storage, <code>false</code> 
	 *   otherwise.
	 */
	boolean contains(String resource);
	
	/**
	 * Returns a list of all resources matching the given regular expression.
	 * 
	 * @param regex
	 *   a regular expression; if null provided, a list of all resources in the 
	 *   storage is returned.
	 * @return
	 *   a list of resources matching the given regular expression.
	 */
	String [] list(Regex regex);
	
	/**
	 * Handles the persistence of the resource in the storage.
	 * 
	 * @param resource
	 *   the name of the resource.
	 * @param stream
	 *   the resource, as a stream.
	 * @throws CacheException
	 */
	void store(String resource, InputStream stream) throws CacheException;

	/**
	 * Handles the persistence of the resource in the storage.
	 * 
	 * @param resource
	 *   the name of the resource.
	 * @param data
	 *   the resource, as a byte array.
	 * @throws CacheException
	 */
	void store(String resource, byte [] data) throws CacheException;
		
	/**
	 * Retrieve the given resource(if present), and returns its data as a byte 
	 * array.
	 *  
	 * @param resource
	 *   the name of the resource.
	 * @return
	 *   the resource data, as a stream, or null if not present.
	 */
	InputStream retrieveAsStream(String resource);
	
	/**
	 * Retrieve the given resource(if present), and returns its data as a byte 
	 * array.
	 *  
	 * @param resource
	 *   the name of the resource.
	 * @return
	 *   the resource data, as a byte array, or null if not present.
	 */
	byte [] retrieveAsByteArray(String resource);
	
	/**
	 * Removes all resources matching the given regular expression from the storage.
	 * 
	 * @param regex
	 *   a regular expression that will be used to match the name of the resources 
	 *   to be deleted.
	 */
	void delete(Regex regex);

	/**
	 * Removes a single resource from the storage.
	 * 
	 * @param resource
	 *   the name of the resource to be removed.
	 * @param caseInsensitive
	 *   whether the resource names' matching should be case insensitive.
	 */
	void delete(String resource, boolean caseInsensitive);
	
	/**
	 * Deletes all items from the storage.
	 */
	void clear();
}
