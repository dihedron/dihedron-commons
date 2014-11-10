/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map.Entry;

import org.dihedron.core.streams.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class HttpClient {
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(HttpClient.class);
	
	private static final byte [] NEWLINE = {0x0D, 0x0A};
		
	public HttpClient() {
		
	}
	
//	========================
//	POST /path/to/script.php HTTP/1.0
//	Host: example.com
//	Content-type: multipart/form-data, boundary=AaB03x
//	Content-Length: $requestlen
//	
//	--AaB03x
//	content-disposition: form-data; name="field1"
//	
//	$field1
//	--AaB03x
//	content-disposition: form-data; name="field2"
//	
//	$field2
//	--AaB03x
//	content-disposition: form-data; name="userfile"; filename="$filename"
//	Content-Type: $mimetype
//	Content-Transfer-Encoding: binary
//	
//	$binarydata
//	--AaB03x--
//	==========================	
	
	public HttpResponse perform(HttpRequest request) throws IOException {
		
		URL url = request.getURL();
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// optional default is GET
		connection.setRequestMethod(request.getMethod().name());
		for(Entry<String, String> header : request.getHeaders().entrySet()) {
			connection.setRequestProperty(header.getKey(), header.getValue());
		}
		
		if(request.getMethod() == HttpMethod.POST) {
			if(request.isMultiPartFormData()) {
				request.setHeader("Content-type", "multipart/form-data, boundary=" + request.getBoundary());
			}
			connection.setDoOutput(true);
			DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
			if(request.isMultiPartFormData()) {
				for(Entry<String, HttpParameter> parameter : request.getParameters().entrySet()) {
					stream.writeBytes(request.getBoundary());
					stream.write(NEWLINE);
					switch(parameter.getValue().getType()) {
					case TEXT:
						stream.writeBytes("content-disposition: form-data; name=\"" + parameter.getKey() + "\"");
						stream.write(NEWLINE);
						stream.write(NEWLINE);
						stream.writeBytes(parameter.getValue().toString());
						break;
					case FILE:
						stream.writeBytes("content-disposition: form-data; name=\"" + parameter.getKey() + "\"; filename=\"" + ((HttpFileParameter)parameter.getValue()).getFileName() + "\"");
						stream.write(NEWLINE);
						stream.write(NEWLINE);
						Streams.copy(((HttpFileParameter)parameter.getValue()).getInputStream(), stream);
						stream.write(NEWLINE);
						break;						
					}
				}
			} else {
				// plain form
			}
			
//			stream.writeBytes(urlParameters);
			stream.flush();
			stream.close();			
		} else if(request.getMethod() == HttpMethod.GET) {
			// TODO: implement!
		}
		
		return null;
	}
}
