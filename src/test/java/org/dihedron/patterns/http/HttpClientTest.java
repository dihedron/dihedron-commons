/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.http;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class HttpClientTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(HttpClientTest.class);

	@Test
	public void test() throws MalformedURLException {
		URL url = new URL("http://www.google.com/search?q=dihedron");
		logger.trace("url: '{}'", url);
//		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//		
//		connection.setRequestMethod("GET");
//		connection.setRequestProperty("User-Agent", "MyUserAgent");
//		connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//		
//		int code = connection.getResponseCode();
//		String message = connection.getResponseMessage();
//		
//		connection.setDoOutput(true);
//		for(Entry<String, String> header : request.getHeaders().entrySet()) {
//			connection.setRequestProperty(header.getKey(), header.getValue());
//		}
//
	}
}
