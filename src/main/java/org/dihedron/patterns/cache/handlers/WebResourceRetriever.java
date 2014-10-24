/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.patterns.cache.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import org.dihedron.core.License;
import org.dihedron.patterns.cache.CacheException;
import org.dihedron.patterns.cache.CacheMissHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
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
			stream = url.openConnection(proxy != null ? proxy : Proxy.NO_PROXY).getInputStream();
			if(stream == null) {
				logger.error("error opening stream");
			} else {
				logger.debug("stream opened");
			}
		} catch(IOException e) {
			logger.error("error accessing url '{}'", url);
			throw new CacheException("error accessing url " + url, e);
		}
		return stream;
	}
}
