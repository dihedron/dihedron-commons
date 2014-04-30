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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import org.dihedron.patterns.cache.CacheException;
import org.dihedron.patterns.cache.CacheMissHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class WebResourceRetriever implements CacheMissHandler {
	
	/** 
	 * The logger. 
	 */
	private static Logger logger = LoggerFactory.getLogger(WebResourceRetriever.class);
	
	/** 
	 * The URL to connect to. 
	 */
	private URL url;
	
	/** 
	 * The proxy through which the connection will go. 
	 */
	private Proxy proxy;
	
	/**
	 * Constructor.
	 * 
	 * @param url
	 *   the remote resource URL.
	 */
	public WebResourceRetriever(URL url) {
		this(url, null);
	}

	/**
	 * Constructor.
	 * 
	 * @param url
	 *   the remote resource URL.
	 * @throws MalformedURLException
	 */
	public WebResourceRetriever(String url) throws MalformedURLException {		
		this(new URL(url), null);
	}

	/**
	 * Constructor.
	 * 
	 * @param url
	 *   the remote resource URL.
	 * @param proxy
	 *   the proxy server.
	 */
	public WebResourceRetriever(URL url, Proxy proxy) {
		this.url = url;
		this.proxy = proxy;
		logger.debug("creating web resource retriever for URL '{}' (proxy: '{}')", url,  (proxy != null ? proxy : "none"));
	}

	/**
	 * Constructor.
	 * 
	 * @param url
	 *   the remote resource URL.
	 * @param proxy
	 *   the proxy server.
	 * @throws MalformedURLException
	 */
	public WebResourceRetriever(String url, Proxy proxy) throws MalformedURLException {
		this(new URL(url), proxy);
	}
	
	/**
	 * Accesses the given URL and returns its contents as a stream.
	 */
	public InputStream getAsStream() throws CacheException {
		InputStream stream = null;
		try {
			logger.debug("opening connection...");
			if(proxy != null) {
				stream = url.openConnection(proxy).getInputStream();
			} else {
				stream = url.openConnection().getInputStream();
			}
			if(stream == null) {
				logger.error("error opening stream");
			} else {
				logger.debug("stream opened");
			}
		} catch(IOException e) {
			logger.error("error accessing url '{}'", url);
			throw new CacheException("Error accessing url " + url, e);
		}
		return stream;
	}
}
