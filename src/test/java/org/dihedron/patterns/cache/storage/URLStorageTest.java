/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.cache.storage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.Proxy;

import org.dihedron.core.License;
import org.dihedron.core.streams.Streams;
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
	
		InputStream input = storage.retrieve("http://www.google.com/");
		ByteArrayOutputStream output = new ByteArrayOutputStream(); 
		Streams.copy(input, output);
		String s = new String(output.toByteArray());
		logger.trace("result:\n{}", s);
	}
}
