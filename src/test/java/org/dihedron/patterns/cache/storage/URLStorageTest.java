/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.cache.storage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.Proxy;

import org.dihedron.core.License;
import org.dihedron.core.streams.Streams;
import org.dihedron.patterns.cache.Cache;
import org.dihedron.patterns.cache.CacheException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class URLStorageTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(URLStorageTest.class);

	// test values, replace with valid ones
	private static final String PROXYHOST = "proxy.acme.org";
	private static final String PROXYPORT = "8080";
	private static final String USERNAME = "jdoe@acme.org";
	private static final String PASSWORD = "trustn00n3";
	
	private static final String RESOURCE_URL = "http://www.google.com/";
//	private static final String RESOURCE_URL = "http://localhost/websign/index.html";
	
	@Before
	public void setUp() {
		
		// this is needed to test against proxies
		System.setProperty("http.proxyHost", PROXYHOST);
		System.setProperty("http.proxyPort", PROXYPORT);
		System.setProperty("http.proxyUser", USERNAME);
		System.setProperty("http.proxyPassword", PASSWORD);

		// now register the default authenticator; this will only send the 
		// user name and password to the system registered proxy
		Authenticator.setDefault(new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        if (getRequestorType() == RequestorType.PROXY) {
		            String protocol = getRequestingProtocol().toLowerCase();
		            String host = System.getProperty(protocol + ".proxyHost", "");
		            String port = System.getProperty(protocol + ".proxyPort", "80");
		            String user = System.getProperty(protocol + ".proxyUser", "");
		            String password = System.getProperty(protocol + ".proxyPassword", "");

		            if (getRequestingHost().equalsIgnoreCase(host)) {
		                if (Integer.parseInt(port) == getRequestingPort()) {
		                    // the request comes from the system proxy
		                    return new PasswordAuthentication(user, password.toCharArray());  
		                }
		            }
		        }
		        return null;
		    }  
		});				
	}
	
	/**
	 * Test method for {@link org.dihedron.patterns.cache.storage.URLStorage#retrieve(java.lang.String)}.
	 * @throws IOException 
	 */
	@Test
	@Ignore
	public void testRetrieve() throws IOException {

//		// if using proxies, enable and modify URLConstructor contructor
//		String host = System.getProperty("http.proxyHost", "");
//		String port = System.getProperty("http.proxyPort", "80");
//		SocketAddress address = new InetSocketAddress(host, Integer.parseInt(port));
//		Proxy proxy = new Proxy(Type.HTTP, address);
		
		URLStorage storage = new URLStorage(Proxy.NO_PROXY);
	
		try(OutputStream os = storage.store(RESOURCE_URL)) {}
		
		try(InputStream input = storage.retrieve(RESOURCE_URL); ByteArrayOutputStream output = new ByteArrayOutputStream()) { 
			Streams.copy(input, output);
			String s = new String(output.toByteArray());
			logger.trace("result:\n{}", s);
		}
	}
	
	@Test
	@Ignore
	public void testWithCache() throws CacheException, IOException {
		Cache cache = new Cache(new URLStorage(Proxy.NO_PROXY));
		
		// NOTE: we have to do it like this because we do not want to store anything 
		// into the cache, we are simply recoriding the URL of the remte resource
		// so that the cache believes it has it in store already; when we'll
		// try to retrieve the resource from the cache, the storage will actually
		// go fetch it from the remote server: this is different from using the
		// handler to grab the resource, because that mechanism will actually
		// perform the web request only once, up-front, and will then store the
		// (potentially huge amount of) data into the local cache, assuming there
		// is a memory storage or a disk storage. By doing like this, we are
		// tricking the cache into believing it has already a local copy, but it 
		// has not, so the load on the local memory is kept to 0 at the expense 
		// of a little delay in acquiring the resource when needed. 
		try(OutputStream os = cache.put(RESOURCE_URL)) {}
		
		try(InputStream input = cache.get(RESOURCE_URL); ByteArrayOutputStream output = new ByteArrayOutputStream()) { 
			Streams.copy(input, output);
			String s = new String(output.toByteArray());
			logger.trace("result:\n{}", s);
		}		
	}
}
