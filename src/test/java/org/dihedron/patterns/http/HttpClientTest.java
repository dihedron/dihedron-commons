/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.http;

import java.io.File;
import java.io.IOException;

import org.dihedron.core.License;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class HttpClientTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(HttpClientTest.class);

	private final static String GET_URL = "http://localhost/websign/index.html"; 
	private final static String POST_URL = "http://localhost/upload/upload.php";

	@Test
	@Ignore
	public void testGET() throws IOException, HttpClientException {
		
		
		HttpClient client = new HttpClient();
		
		HttpRequest get = new HttpRequest(HttpMethod.GET, GET_URL)
			.withHeader("User-Agent", "MyUserAgent")
			.withHeader("Accept-Language", "en-US,en;q=0.5")
			.withParameter(new HttpTextParameter("param1", "value1"))
			.withParameter(new HttpTextParameter("param2", "value 2"))
			.withParameter(new HttpTextParameter("param3", "value3"));		
		HttpResponse response = client.perform(get);
		logger.trace("content-type: {}", response.getContentType());
		logger.trace("response data: \n{}", response);
	}
	
	
	@Test
	@Ignore
	public void testPOST() throws IOException, HttpClientException {
		
		
		HttpClient client = new HttpClient();
		
		HttpRequest post = new HttpRequest(HttpMethod.POST, POST_URL)
			.withHeader("User-Agent", "MyUserAgent")
			.withHeader("Accept-Language", "en-US,en;q=0.5")
			.withParameter(new HttpTextParameter("param1", "value1"))
			.withParameter(new HttpTextParameter("param3", "value  2"))
			.withParameter(new HttpFileParameter("file01", "icon01.png", "image/png", new File("src/test/resources/org/dihedron/commons/images/icon01.png")))
			.withParameter(new HttpFileParameter("file02", "icon02.png", "image/png", new File("src/test/resources/org/dihedron/commons/images/icon02.png")))
			.withParameter(new HttpFileParameter("file03", "icon03.png", "image/png", new File("src/test/resources/org/dihedron/commons/images/icon03.png")))
			.withParameter(new HttpFileParameter("file04", "icon04.png", "image/png", new File("src/test/resources/org/dihedron/commons/images/icon04.png")));
		HttpResponse response = client.perform(post);
		logger.trace("content-type: {}", response.getContentType());
		logger.trace("response data: \n{}", response);
	}
}
