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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.Proxy;

import org.dihedron.core.streams.Streams;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
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
