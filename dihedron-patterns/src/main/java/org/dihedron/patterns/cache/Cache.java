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

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.dihedron.core.regex.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class Cache implements Iterable<String>{
	
	/** 
	 * The logger. 
	 */
	private static Logger logger = LoggerFactory.getLogger(Cache.class);

	/** 
	 * The underlying storage engine. 
	 */
	private Storage storage;
	
	/**
	 * Constructor.
	 * 
	 * @param storage
	 *   the storage engine to be used for persistence.
	 */
	public Cache(Storage storage) {
		logger.debug("created cache");
		this.storage = storage;
	}
	
	/**
	 * Retrieves the underlying storage engine.
	 * 
	 * @return
	 *   the storage engine.
	 */
	public Storage getStorageEngine() {
		return storage;
	}
	
	/**
	 * Checks whether the cache is empty.
	 * 
	 * @return
	 *   whether the cache is empty.
	 */
	public boolean isEmpty() {
		logger.trace("checking if cache is empty");
		return storage.isEmpty();
	}
	
	/**
	 * Empties the cache.
	 */
	public void clear(){
		logger.debug("clearing the cache");
		storage.clear();
	}
	
	/**
	 * Deletes all resources that match the given resource name 
	 * criteria.
	 * 
	 * @param regex
	 *   the resource name pattern.
	 */
	public void delete(Regex regex) {
		logger.debug("deleting all files named according to /{}/", regex);
		storage.delete(regex);
	}
	
	/**
	 * Deletes all resource that match the given resource name 
	 * criteria.
	 * 
	 * @param resource
	 *   the resource name.
	 * @param caseInsensitive
	 *   whether the resource name comparison should be
	 *   case insensitive.
	 */
	public void delete(String resource, boolean caseInsensitive) {
		logger.debug("deleting all files named according to '{}' (case insensitive)", resource);
		storage.delete(resource, caseInsensitive);
	}
	
	/**
	 * Copies data from one resource to another, possibly replacing
	 * the destination resource if one exists.
	 * 
	 * @param source
	 *   the name of the source resource.
	 * @param destination
	 *   the name of the destination resource
	 * @return
	 *   the name of the destination resource, or null if something fails.
	 */
	public String copyAs(String source, String destination) throws CacheException {
		if(source == null || source.length() == 0 || destination == null || destination.length() == 0) {
			logger.error("invalid input parameters: from '{}' to '{}'", source, destination);
		}
		byte [] data = storage.retrieveAsByteArray(source);
		if(data != null) {
			logger.trace("storing {} bytes as {}", data.length, destination);
			storage.store(destination, data);
			logger.trace("data stored");
			return destination;
		}
		return null;
	}
	
	/**
	 * Returns the iterator on the cache items.
	 */	
	public Iterator<String> iterator() {		
		return new CacheIterator(storage.list(new Regex()));
	}
	
	/**
	 * Checks whether the cache contains the given resource.
	 * 
	 * @param resource
	 *   the resource whose existence in the cache is to be checked.
	 * @return
	 *   <code>true</code> if the resource exists in the cache, <code>false
	 *   </code> otherwise.
	 */
	public boolean contains(String resource) {
		boolean result = storage.contains(resource);
		logger.debug("resource '{}' {} in cache", resource, (result ? "is" : "is not"));
		return result; 
	}
	
	/**
	 * Retrieve a resource from the cache, if it exists;
	 * returns null otherwise.
	 * 
	 * @param resource
	 *   the name of the resource.
	 * @return
	 *   the resource (as a Stream) if it's in the cache
	 *   already; null otherwise.
	 * @throws CacheException 
	 */
	public final InputStream getAsStream(String resource) throws CacheException {
		logger.trace("retrieving resource '{}' from cache", resource);
		return storage.retrieveAsStream(resource);
	}
	
	/**
	 * Retrieve a resource from the cache, if it exists;
	 * returns null otherwise.
	 * 
	 * @param resource
	 *   the name of the resource.
	 * @return
	 *   the resource (as an array of bytes) if it's in the cache
	 *   already; null otherwise.
	 * @throws CacheException 
	 */
	public final byte[] getAsByteArray(String resource) throws CacheException {
		logger.trace("retrieving resource '{}' from cache", resource);
		return storage.retrieveAsByteArray(resource);
	}
	
	/**
	 * Retrieves a resource from the cache if it is in there; if
	 * the <code>autoRefresh</code> flag is on, the cache will check
	 * if the resource has not been refreshed during this session yet
	 * and (if so) will retrieve it again.
	 * 
	 * @param resource
	 *   the name of the resource.
	 * @param handler
	 *   the object which will retrieve the resource, if missing.
	 * @return
	 *   the resource as an input stream if it can be retrieved, 
	 *   null otherwise.
	 * @throws Exception 
	 * @throws Exception
	 */
	public InputStream getAsStream(String resource, CacheMissHandler handler) throws CacheException {
		InputStream stream = storage.retrieveAsStream(resource);
		
		if(stream == null) {			
				logger.trace("retrieving '{}' using {}", resource, handler.getClass().getSimpleName());
				stream = handler.getAsStream();
				storage.store(resource, stream);
				return getAsStream(resource);
					
		} else {
			return stream;
		}
	}
	
	/**
	 * Retrieves a resource from the cache if it is in there; if
	 * the <code>autoRefresh</code> flag is on, the cache will check
	 * if the resource has not been refreshed during this session yet
	 * and (if so) will retrieve it again.
	 * 
	 * @param resource
	 *   the name of the resource.
	 * @param handler
	 *   the object which will retrieve the resource, if missing.
	 * @return
	 *   the resource as a byte array if it can be retrieved, 
	 *   null otherwise.
	 * @throws CacheException
	 */
	public byte[] getAsByteArray(String resource, CacheMissHandler handler) throws CacheException {
		try {
			getAsStream(resource, handler).close();
			return storage.retrieveAsByteArray(resource);
		} catch(IOException e) {
			logger.error("I/O exception trying to access resource '{}' as a stream", resource);
			throw new CacheException("I/O exception trying to access resource '" + resource + "' as a stream", e);
		}
	}
	
	/**
	 * Iterates over the cache resources, one at a time.
	 */
	private final class CacheIterator implements Iterator<String> {

		/** 
		 * The cache resources. 
		 */
		private String [] resources;
		
		/**
		 * The current item.
		 */
		private int current = 0;
		
		/**
		 * Constructor.
		 * 
		 * @param resources
		 *   the resources in the cache.
		 */
		private CacheIterator(String [] resources) {	
			if(resources != null && resources.length > 0) {
				this.resources = Arrays.copyOf(resources, resources.length);
			}			
		}
		
		/**
		 * Whether there are other resources in the cache.
		 */
		public boolean hasNext() {
			if(resources != null && current < resources.length) {
				return true;
			}
			return false;			
		}

		/**
		 * Returns the next resource in the cache, if any.
		 */
		public String next() {
			if(resources != null && current < resources.length) {
				return resources[current++];
			}
			throw new NoSuchElementException("No more items in the cache");
		}

		/**
		 * Unsupported operation: removes the next resource from the cache. 
		 */
		public void remove() {			
			throw new UnsupportedOperationException("Removal from cache is not supported");			
		}		
	}
}