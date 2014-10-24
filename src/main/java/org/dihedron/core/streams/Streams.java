/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.core.streams;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.InvalidParameterException;

import org.dihedron.core.License;
import org.dihedron.core.strings.Strings;
import org.dihedron.core.url.URLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class providing useful utility methods for manipulating streams.
 * 
 * @author Andrea Funto'
 */
@License
public class Streams {
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Streams.class);
	
	/**
	 * Default size of the internal memory buffer (1 megabyte).
	 */
	public static final int DEFAULT_BUFFER_SIZE = 1024 * 1024;
	
	/**
	 * Copies all the bytes it can read from the input stream into the output
	 * stream; input and output streams management (opening, flushing, closing)
	 * are all up to the caller.
	 * 
	 * @param input
	 *   an open and ready-to-be-read input stream.
	 * @param output
	 *   an open output stream.
	 * @return
	 *   the total number of bytes copied.
	 * @throws IOException
	 */
	public static long copy(InputStream input, OutputStream output) throws IOException {
		return copy(input, output, false);
	}
	
	/**
	 * Copies all the bytes it can read from the input stream into the output
	 * stream; input and output streams management (opening, flushing, closing)
	 * are all up to the caller.
	 * 
	 * @param input
	 *   an open and ready-to-be-read input stream.
	 * @param output
	 *   an open output stream.
	 * @param autoclose
	 *   whether input and output stream should be closed once this method has
	 *   finished using them.
	 * @return
	 *   the total number of bytes copied.
	 * @throws IOException
	 */
	public static long copy(InputStream input, OutputStream output, boolean autoclose) throws IOException {
		// check input parameters
		if(input == null) {
			logger.error("input stream must not be null");
			throw new InvalidParameterException("input stream must not be null");
		}		
		if(output == null) {
			logger.error("output stream must not be null");
			throw new InvalidParameterException("output stream must not be null");
		}
		
		// read a "bufferful" at a time and copy into the output stream 
		try {
			long total = 0;
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int read = 0;
			while((read = input.read(buffer)) > 0) {
				logger.trace("read {} bytes from input stream", read);
				total += read;
				output.write(buffer, 0, read);
			}
			logger.trace("total of {} bytes copied", total);
			return total;
		} finally {
			if(autoclose) {
				try {
					input.close();
				} catch(IOException e) {
					logger.warn("error closing input stream", e);
				}
				try {
					output.close();
				} catch(IOException e) {
					logger.warn("error closing output stream", e);
				}
			}
		}
	}	
	
	/**
	 * Reads a resource from an URL, specified as a string; since this class makes
	 * use of the URL factory, URLs may also represent resources in the class
	 * path, re (in the format "classpath:org/dihedron/resources/MyResource.png").
	 * 
	 * @param url
	 *   a string presenting an URL.
	 * @return
	 *   an input stream to access the URL.
	 * @throws IOException
	 *   if the URL is malformed or an error occurs opening the stream.
	 */
	public static InputStream fromURL(String url) throws IOException {
		if(Strings.isValid(url)) {
			return fromURL(URLFactory.makeURL(url));
		}
		return null;
	}
	
	/**
	 * Opens an input stream to the given URL.
	 * 
	 * @param url
	 *   the URL to open the stream to.
	 * @return
	 *   an input stream to access the URL.
	 * @throws IOException
	 *   if an error occurs opening the stream.
	 */
	public static InputStream fromURL(URL url) throws IOException {
		if(url != null) {
			return url.openStream();
		}
		return null;
	}
	
	/**
	 * Returns an input stream from a {@code File} object.
	 * 
	 * @param file
	 *   a {@code File} object representing the input file; the file must be
	 *   existing and readable.
	 * @return
	 *   a {@code FileInputStream} object.
	 * @throws FileNotFoundException
	 */
	public static InputStream fromFile(File file) throws FileNotFoundException {
		if(file != null && file.isFile() && file.canRead()) {
			return new FileInputStream(file);
		}
		return null;
	}
	
	/**
	 * Returns an input stream from a string representing its path.
	 * 
	 * @param filepath
	 *   a string representing the file path.
	 * @return
	 *   a {@code FileInputStream} object.
	 * @throws FileNotFoundException
	 */
	public static InputStream fromFile(String filepath) throws FileNotFoundException {
		if(Strings.isValid(filepath)) {
			return fromFile(new File(filepath));
		}
		return null;
	}
	
	/**
	 * Returns an output stream corresponding to the given file.
	 * 
	 * @param file
	 *   a non-null {@code File} object. 
	 * @return
	 *   a {@code FileOutputStrea} to the given file, or {@code null} if the file
	 *   is not valid.
	 * @throws FileNotFoundException
	 */
	public static OutputStream toFile(File file) throws FileNotFoundException {
		if(file != null) {
			return new FileOutputStream(file);
		}
		return null;
	}
	
	/**
	 * Returns an output stream corresponding to the given file.
	 * 
	 * @param file
	 *   a string representing the output file path. 
	 * @return
	 *   a {@code FileOutputStrea} to the given file, or {@code null} if the file
	 *   is not valid.
	 * @throws FileNotFoundException
	 */
	public static OutputStream toFile(String filepath) throws FileNotFoundException {
		if(Strings.isValid(filepath)) {
			return new FileOutputStream(new File(filepath));
		}
		return null;
	}
	
	/**
	 * Returns an input stream from an array of bytes in memory.
	 * 
	 * @param data
	 *   a byte array in memory.
	 * @return
	 *   an {@code InputStream} object.
	 */
	public static InputStream fromMemory(byte [] data) {
		if(data != null) {
			return new ByteArrayInputStream(data);
		}
		return null;
	}
	
	/**
	 * Returns an output stream backed up by an array of bytes in memory.
	 * 
	 * @return
	 *   an {@code OutputStream} object.
	 */
	public static OutputStream toMemory() {
		return new ByteArrayOutputStream();
	}
	
	/**
	 * Returns an input stream for the given resource path; to read data from the
	 * class path resource paths must be absolute and start with no slash, e.g.
	 * "org/dihedron/commons/resources/myresource.png".
	 * 
	 * @param path
	 *   the path to the resource.
	 * @return
	 *   an input stream if the resource was found on the classpath, {@code null}
	 *   otherwise.
	 */
	public static InputStream fromClassPath(String path) {
		return Streams.class.getClassLoader().getResourceAsStream(path);		
	}
	
	/**
	 * Tries to close the given stream; if any exception is thrown in the process, 
	 * it suppresses it.
	 * 
	 * @param stream
	 *   the input stream to be closed. 
	 */
	public static void safelyClose(InputStream stream) {
		if(stream != null) {
			try {
				stream.close();
			} catch(IOException e) {
				logger.warn("error closing internal input stream, this may lead to a stream leak", e);
			}
		}
	}
	
	/**
	 * Tries to close the given stream; if any exception is thrown in the process, 
	 * it suppresses it.
	 * 
	 * @param stream
	 *   the output stream to be closed. 
	 */
	public static void safelyClose(OutputStream stream) {
		if(stream != null) {
			try {
				stream.close();
			} catch(IOException e) {
				logger.warn("error closing internal output stream, this may lead to a stream leak", e);
			}
		}
	}
}
