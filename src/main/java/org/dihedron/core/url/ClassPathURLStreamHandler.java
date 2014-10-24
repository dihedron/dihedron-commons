/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.core.url;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import org.dihedron.core.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * A {@link URLStreamHandler} that handles resources on the classpath.
 * For details, see http://stackoverflow.com/questions/861500/url-to-load-resources-from-the-classpath-in-java
 */
@License
public class ClassPathURLStreamHandler extends URLStreamHandler {
	
    /** 
     * The class loader to find resources from. 
     */
    private final ClassLoader classloader;
    
    /**
     * The logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(ClassPathURLStreamHandler.class);

    /**
     * Constructor.
     */
    public ClassPathURLStreamHandler() {
        this.classloader = getClass().getClassLoader();
    }

    /**
     * Constructor.
     *
     * @param classloader
     *   a user-defined class loader.
     */
    public ClassPathURLStreamHandler(ClassLoader classloader) {
        this.classloader = classloader;
    }

    /**
     * Opens a connection to the given resource, if found in the classpath.
     * 
     * @param url
     *   the URL representing the path to the resource, in the following 
     *   format: <code>classpath:path/to/resource.ext</code>.
     * @return
     *   the <code>URLConnection/code> to the requested resource.
     */
    @Override
    protected URLConnection openConnection(URL url) throws IOException {
    	logger.trace("opening connection to resource '{}' (protocol: '{}')", url.getPath(), url.getProtocol());
        URL resource = classloader.getResource(url.getPath());
        return resource.openConnection();
    }
}
