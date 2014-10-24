/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.patterns.cache;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.dihedron.core.License;
import org.dihedron.core.regex.Regex;

/**
 * @author Andrea Funto'
 */
@License
public interface Storage extends Iterable<String> {

	/**
	 * Returns an iterator over all resources in the storage.
	 * 
	 * @return
	 *   an iterator over all the resources in the storage; deletion is not 
	 *   supported, and the returned iterator contains information about the 
	 *   resources in the cache at the moment of the creation: do not assume the
	 *   behaviour to be 100% correct in a multi-threaded environment.  
	 */
	public Iterator<String> iterator();
	
	/**
	 * Returns an iterator over all resources matching the given regular expression.
	 * 
	 * @param regex
	 *   a regular expression; if {@code null}  is provided , a n iterator over 
	 *   all the resources in the storage is returned.
	 * @return
	 *   an iterator over all the resources whose name matches the given regular 
	 *   expression.
	 */
	public Iterator<String> iterator(Regex regex);
	
	/**
	 * Checks whether the Storage is empty.
	 * 
	 * @return
	 *   <code>true</code> if it is empty, <code>false</code> otherwise.
	 */
	boolean isEmpty();
	
	/**
	 * Returns the number of elements in the cache.
	 * 
	 * @return
	 *   the number of elements in the cache.
	 */
	long size();

	/**
	 * Returns a list of all resources in the storage.
	 * 
	 * @return
	 *   a list of all resources in the storage.
	 */
	String [] list();
	
	/**
	 * Returns a list of all resources matching the given regular expression.
	 * 
	 * @param regex
	 *   a regular expression; if {@code null}provided, a list of all resources 
	 *   in the storage is returned.
	 * @return
	 *   a list of resources matching the given regular expression.
	 */
	String [] list(Regex regex);
	
	/**
	 * Checks whether the storage contains the given resource.
	 * 
	 * @param resource
	 *   the key of the resource.
	 * @return
	 *   {@code true} if the resource is in the storage, {@code false} otherwise.
	 */
	boolean contains(String resource);
	
	/**
	 * Handles the persistence of the given resource into the storage, by opening
	 * a stream to the resource and returning it to the caller; it is up to the
	 * caller to flush and close the output stream once it's done with it.
	 * 
	 * @param resource
	 *   the name of the resource.
	 * @return
	 *   an output stream directly pointing to the new resource; the stream must
	 *   be flushed and closed by the caller to avoid leaks.
	 * @throws CacheException
	 */
	OutputStream store(String resource) throws CacheException;
		
	/**
	 * Retrieves the given resource (if present), and returns an open input stream 
	 * to its data; it is up to the caller to prorperly flush and close the stream 
	 * once it's finished with it, to avoid leaks. 
	 * array.
	 *  
	 * @param resource
	 *   the name of the resource.
	 * @return
	 *   an input stream giving access to the resource data, or {@code null}
	 *   if not present.
	 */
	InputStream retrieve(String resource);
	
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
