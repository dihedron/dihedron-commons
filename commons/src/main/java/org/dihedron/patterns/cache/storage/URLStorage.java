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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import org.dihedron.core.regex.Regex;
import org.dihedron.core.url.URLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class URLStorage extends ReadOnlyStorage {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(URLStorage.class);

	/**
	 * A proxy address, if connections cannot be direct.
	 */
	private Proxy proxy = null;
	
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
		return false;
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#list(org.dihedron.core.regex.Regex)
	 */
	@Override
	public String[] list(Regex regex) {
		return null;
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String resource) {
		return false;
	}

	/**
	 * @see org.dihedron.patterns.cache.Storage#retrieve(java.lang.String)
	 */
	@Override
	public InputStream retrieve(String resource) {
		InputStream stream = null;
		try {
			logger.trace("retrieving resource at URL '{}'", resource);
			URL url = URLFactory.makeURL(resource);
			stream = url.openConnection(proxy != null ? proxy : Proxy.NO_PROXY).getInputStream();
		} catch (MalformedURLException e) {
			logger.error("error parsing URL", e);
		} catch (IOException e) {
			logger.error("error opening connection", e);
		}
		return stream;
	}
}
