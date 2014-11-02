/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.dihedron.core.License;
import org.dihedron.core.regex.Regex;
import org.dihedron.core.streams.Streams;
import org.dihedron.core.strings.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class Cache implements Iterable<String>{
	
	/** 
	 * The logger. 
	 */
	private static final Logger logger = LoggerFactory.getLogger(Cache.class);

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
	@Deprecated
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
	 * Returns the number of elements in the cache.
	 * 
	 * @return
	 *   the number of elements in the cache.
	 */
	public long size() {
		return storage.size();
	}
	
	/**
	 * Empties the cache.
	 * 
	 * @return
	 *   the cache itself, for method chaining.
	 */
	public Cache clear(){
		logger.debug("clearing the cache");
		storage.clear();
		return this;
	}
	
	/**
	 * Deletes all resources that match the given resource name criteria.
	 * 
	 * @param regex
	 *   the resource name pattern.
	 * @return
	 *   the cache itself, for method chaining.
	 */
	public Cache delete(Regex regex) {
		logger.debug("deleting all files named according to /{}/", regex);
		storage.delete(regex);
		return this;
	}
	
	/**
	 * Deletes all resource that match the given resource name criteria.
	 * 
	 * @param resource
	 *   the resource name.
	 * @param caseInsensitive
	 *   whether the resource name comparison should be
	 *   case insensitive.
	 * @return
	 *   the cache itself, for method chaining.
	 */
	public Cache delete(String resource, boolean caseInsensitive) {
		logger.debug("deleting all files named according to '{}' (case insensitive)", resource);
		storage.delete(resource, caseInsensitive);
		return this;
	}
	
	/**
	 * Copies data from one resource to another, possibly replacing the destination 
	 * resource if one exists.
	 * 
	 * @param source
	 *   the name of the source resource.
	 * @param destination
	 *   the name of the destination resource
	 * @return
	 *   the cache itself, for method chaining.
	 */
	public Cache copyAs(String source, String destination) throws CacheException {
		if(!Strings.areValid(source, destination)) {
			logger.error("invalid input parameters for copy from '{}' to '{}'", source, destination);
			throw new CacheException("invalid input parameters (source: '" + source + "', destination: '" + destination + "')");
		}
		try (InputStream input = storage.retrieve(source); OutputStream output = storage.store(destination)) {
			long copied = Streams.copy(input, output);
			logger.trace("copied {} bytes from '{}' to '{}'", copied, source, destination);
		} catch (IOException e) {
			logger.error("error copying from '" + source + "' to '" + destination + "'", e);
			throw new CacheException("error copying from '" + source + "' to '" + destination + "'", e);
		}
		return this;
	}
	
	/**
	 * Returns the iterator on the cache items.
	 * 
	 * @see Iterable#iterator()
	 */
	@Override
	public Iterator<String> iterator() {		
		return storage.iterator();
	}
	
	/**
	 * Returns the iterator on the cache items; only items whose name matches 
	 * the given regular expression will be returned.
	 * 
	 * @param regex
	 *   an optional regular expression; if {@code null} all items will be 
	 *   returned.
	 * @return
	 *   an iterator that allows looping over the resource names.
	 */	
	public Iterator<String> iterator(Regex regex) {		
		return storage.iterator(regex);
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
	 * Retrieves a resource from the cache if it is in there; if
	 * the <code>autoRefresh</code> flag is on, the cache will check
	 * if the resource has not been refreshed during this session yet
	 * and (if so) will retrieve it again.
	 * 
	 * @param resource
	 *   the name of the resource.
	 * @param handlers
	 *   an optional set of cache miss handler, which will be requested to 
	 *   retrieve the resource, if missing; the handlers will be called in the 
	 *   order specified here, and as soon as one returns a valid stream the 
	 *   lookup stops.
	 * @return
	 *   the resource as an input stream if it can be retrieved, {@code null}
	 *   otherwise.
	 * @throws CacheException 
	 */
	public InputStream get(String resource, CacheMissHandler ... handlers) throws CacheException {
		InputStream stream = storage.retrieve(resource);		
		if(stream == null) {
			logger.trace("cache miss for resource '{}'...", resource);
			if(handlers != null) {				
				lookup:
				for(CacheMissHandler handler : handlers) {
					logger.trace("... attempting retrieval of '{}' using handler of class '{}'", resource, handler.getClass().getSimpleName());
					try (InputStream input = handler.getAsStream(); OutputStream output = storage.store(resource)) {
						if(input != null) {
							long copied = Streams.copy(input,  output);
							logger.trace("... stored {} bytes for resource '{}'", copied, resource);
							break lookup;
						} else {
							logger.trace("... resource '{}' not found", resource);
							continue lookup;
						}
					} catch (IOException e) {
						logger.warn("I/O error trying to retrieve resource '" + resource + "' with handler of class '" + handler.getClass().getSimpleName() +"'", e);
					}					
				}
			}
			logger.trace("retrieving resource from storage");
			stream = storage.retrieve(resource);			
		}
		return stream;
	}
	
	/**
	 * Tells the cache to store under the given resource name the contents 
	 * that will be written to the output stream; the method creates a new 
	 * resource entry and opens an output stream to it, then returns the
	 * stream to the caller so this can copy its data into it. It is up to 
	 * the caller to close the steam once all data have been written to it.
	 * This mechanism actually by-passes the cache and the miss handlers and
	 * provides direct access to the underlying storage engine, thus providing
	 * a highly efficient way of storing data into the cache. 
	 * 
	 * @param resource
	 *   the name of the new resource, to which the returned output stream 
	 *   will point.
	 * @return
	 *   an output stream ; the caller will write its data into it, and then 
	 *   will flush and close it one it's done writing data.
	 * @throws CacheException
	 */
	public OutputStream put(String resource) throws CacheException {
		return storage.store(resource);
	}
}