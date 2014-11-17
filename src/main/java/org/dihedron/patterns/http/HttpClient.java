/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Map.Entry;

import org.dihedron.core.License;
import org.dihedron.core.streams.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class HttpClient {
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(HttpClient.class);

	/**
	 * The [0x0D 0x0A] byte sequence representing a new line in HTTP parlance.
	 */
//	private static final byte [] NEWLINE = { 0x0D, 0x0A };
	private static final String NEWLINE = "\r\n";
	
	/**
	 * The optional proxy address.
	 */
	private Proxy proxy;
	
	/**
	 * Constructor.
	 */
	public HttpClient() {
		this(null); 
	}
	
	/**
	 * Constructor.
	 *
	 * @param proxy
	 *   an optional HTTP proxy.
	 */
	public HttpClient(Proxy proxy) {
		this.proxy = proxy;
	}
	
	/**
	 * Performs the given request, returning an HttpResponse object that wraps 
	 * the actual web server response. 
	 * 
	 * @param request
	 *   a request object.
	 * @return
	 *   a response object.
	 * @throws IOException
	 * @throws HttpClientException
	 */
	public HttpResponse perform(HttpRequest request) throws IOException, HttpClientException {
		
		// first check if the request is a multipart/form-data and the method is
		// not POST: if so, there's something inconsistent and we just bail out
		if(request.isMultiPartFormData() && request.getMethod() != HttpMethod.POST) {
			logger.error("multipart/form-data can only be conveyed through a POST, using {} instead", request.getMethod().name());
			throw new HttpClientException("multipart/form-data cannot be conveyed through a " + request.getMethod().name() + " request");
		}
		
		URL url = request.getURL();		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy != null ? proxy : Proxy.NO_PROXY);
		// optional default is GET
		connection.setRequestMethod(request.getMethod().name());
		for(Entry<String, String> header : request.getHeaders().entrySet()) {
			connection.setRequestProperty(header.getKey(), header.getValue());
		}
		
		if(request.getMethod() == HttpMethod.POST) {
			// add headers directly to the connection (we've already looped over
			// headers in the request object, so it's too late to add headers now)
			if(request.isMultiPartFormData()) {
				connection.setRequestProperty("Content-type", "multipart/form-data, boundary=" + request.getBoundary());
			} else {
				connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			}
						
			connection.setDoOutput(true);
//			try(MirrorOutputStream mirror = new MirrorOutputStream(connection.getOutputStream(), new FileOutputStream("target/request.dat")); DataOutputStream stream = new DataOutputStream(mirror)) {
			try(DataOutputStream stream = new DataOutputStream(connection.getOutputStream())) {
				if(request.isMultiPartFormData()) {
					//
					// multipart/form-encoded format is:
					//
					// ========================
					// POST /path/to/script.php HTTP/1.0
					// Host: example.com
					// Content-type: multipart/form-data, boundary=<RANDOM BOUNDARY>
					// Content-Length: <REQUEST_LENGTH>
					//
					// --<RANDOM BOUNDARY>
					// Content-Disposition: form-data; name="field1"
					//
					// value1
					// --<RANDOM BOUNDARY>
					// Content-Disposition: form-data; name="field2"
					//
					// value2
					// --<RANDOM BOUNDARY>
					// Content-Disposition: form-data; name="file1"; filename="<NAME OF FILE 1>"
					// Content-Type: <FILE 1 MIME TYPE>
					// Content-Transfer-Encoding: binary
					// 
					// <FILE 1 BINARY DATA>
					// --<RANDOM BOUNDARY>
					// Content-Disposition: form-data; name="file2"; filename="<NAME OF FILE 2>"
					// Content-Type: <FILE 2 MIME TYPE>
					// Content-Transfer-Encoding: binary
					// 
					// <FILE 2 BINARY DATA>
					// --<RANDOM BOUNDARY>--
					// ==========================						
					for(HttpParameter parameter : request.getParameters()) {
						stream.writeBytes("--" + request.getBoundary());
						stream.writeBytes(NEWLINE);
						switch(parameter.getType()) {
						case TEXT:
							stream.writeBytes("Content-Disposition: form-data; name=\"" + parameter.getName() + "\"" + NEWLINE);
							stream.writeBytes(NEWLINE);
							stream.writeBytes(((HttpTextParameter)parameter).getValue().toString() + NEWLINE);
							break;
						case FILE:
							stream.writeBytes("Content-Disposition: form-data; name=\"" + parameter.getName() + "\"; filename=\"" + ((HttpFileParameter)parameter).getFileName() + "\"" + NEWLINE);
							stream.writeBytes("Content-Type: " + ((HttpFileParameter)parameter).getContentType() + NEWLINE);
							stream.writeBytes("Content-Transfer-Encoding: binary" + NEWLINE);
							stream.writeBytes(NEWLINE);
							Streams.copy(((HttpFileParameter)parameter).getInputStream(), stream);
							stream.writeBytes(NEWLINE);
							break;						
						}
					}					
					stream.writeBytes("--" + request.getBoundary() + "--");
				} else {
					//
					// plain old form format is:
					//
					// ========================
					// POST /path/to/script.php HTTP/1.0
					// Host: example.com
					// Content-type: application/x-www-form-urlencoded
					// Content-Length: <CONTENT LENGTH>
					//
					// logfile=blabla&configfile=more+blabla&usercomment=hello&useremail=
					// ==========================						
					//
					String values = HttpParameter.concatenate(request.getParameters());
					stream.writeBytes(values);
				}
			
				stream.flush();
				stream.close();
			}
		} else if(request.getMethod() == HttpMethod.GET) {
			// do nothing
		}
		
		return new HttpResponse(connection);
	}
}
