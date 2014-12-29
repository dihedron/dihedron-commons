/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.cache.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dihedron.core.License;
import org.dihedron.core.regex.Regex;
import org.dihedron.core.streams.NullOutputStream;
import org.dihedron.core.strings.Strings;
import org.dihedron.core.url.URLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class implementing a pseudo-storage: it actually emulates a read/write 
 * storage by discarding any information that is written to it (it would not be
 * able to write data to a remote URL anyway) except the URL itself, so it keeps 
 * memory of what is assumed to be present in the cache. When the resource is
 * actually retrieved from the storage, the underlying engine will take care of 
 * fetching the bytes from the remote URL on the fly, directly returning the 
 * URL's output stream as the actual stream. This helps saving memory when 
 * large files need to be fetched: they are never stored locally.
 * Note that this mechanism if different from having a web retriever as a cache
 * miss handler, because in that case resource retrieval occurs as soon as the
 * resource is retrieved for the first time from the cache and the associated data 
 * must be stored locally (either on disk or in the local memory). By using this
 * kind of storage you don't need to get a resource twice, you can simply put its 
 * address into the cache, and then delegate the data retrieval to the storage 
 * engine at the time when the actual data is needed.
 *    
 * @author Andrea Funto'
 */
@License
public class URLStorage extends AbstractStorage {
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(URLStorage.class);

	/**
	 * A proxy address, if connections cannot be direct.
	 */
	private Proxy proxy = null;
	
	/**
	 * A hash set containing the URLs of the resources proxied by this virtual
	 * storage; these are the URLs of the actual resources on the remote server.
	 */
	private Set<String> resources = new HashSet<>();
	
	/**
	 * Constructor.
	 */
	public URLStorage() {
	}
	
	/**
	 * Constructor.
	 *
	 * @param proxy
	 *   a proxy address, if the connection should not be direct.
	 */
	public URLStorage(Proxy proxy) {
		this.proxy = proxy;
	}
	
	/**
	 * @see org.dihedron.patterns.cache.Storage#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return resources.isEmpty();
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#list(org.dihedron.core.regex.Regex)
	 */
	@Override
	public String[] list(Regex regex) {
		if(regex != null && !resources.isEmpty()) {
			List<String> matches = new ArrayList<>();
			for(String resource : resources) {
				if(regex.matches(resource)) {
					matches.add(resource);
				}
			}
			return matches.toArray(new String[matches.size()]);
		}
		return null;
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String resource) {
		if(Strings.isValid(resource)) {
			return resources.contains(resource);
		}
		return false;
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#retrieve(java.lang.String)
	 */
	@Override
	public InputStream retrieve(String resource) {
		InputStream stream = null;
		try {
			if(contains(resource)) {
				logger.trace("retrieving resource at URL '{}'", resource);
				URL url = URLFactory.makeURL(resource);
				stream = url.openConnection(proxy != null ? proxy : Proxy.NO_PROXY).getInputStream();
			}
		} catch (MalformedURLException e) {
			logger.error("error parsing URL", e);
		} catch (IOException e) {
			logger.error("error opening connection", e);
		}
		return stream;
	}
	
	/**
	 * This implementation would not be able to retrieve the actual web resource 
	 * size without fully downloading it, so it returns -1 as if there were no 
	 * such resource in cache.
	 * 
	 * @see org.dihedron.patterns.cache.Storage#retrieveSize(java.lang.String)
	 */
	@Override
	public long retrieveSize(String resource) {
		return -1;
	}

	/**
	 * This method actually does nothing to store the binary data, it returns a 
	 * void outputStream and there is no use in writing anything to it; as a side 
	 * effect, it records the name of the resource so that it <em>looks like</em>
	 * the resource is actually stored, but it is not: it will be retrieved from 
	 * the remote server when requested.
	 * 
	 * @see org.dihedron.patterns.cache.Storage#store(java.lang.String)
	 */
	@Override
	public OutputStream store(String resource) {
		if(Strings.isValid(resource)) {
			resources.add(resource);
			return new NullOutputStream();
		}
		return null;
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#delete(org.dihedron.core.regex.Regex)
	 */
	@Override
	public void delete(Regex regex) {
		String[] matches = list(regex);
		if(matches != null) {
			for(String match : matches) {
				resources.remove(match);
			}
		}
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#delete(java.lang.String, boolean)
	 */
	@Override
	public void delete(String resource, boolean caseInsensitive) {
		if(resource != null) {
			List<String> removables = new ArrayList<>();
			for(String element : resources) {
				
				if(caseInsensitive ? element.equalsIgnoreCase(resource) : element.equals(resource)) {
					// NOTE: do not remove from set while iterating: this has
					// unpredictable behaviour because it may invalidate the
					// iterator.
					removables.add(element);
				}
			}			
			resources.removeAll(removables);
		}		
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#clear()
	 */
	@Override
	public void clear() {
		resources.clear();
	}
}
