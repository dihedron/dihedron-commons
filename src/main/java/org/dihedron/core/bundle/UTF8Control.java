/*
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */
package org.dihedron.core.bundle;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

/**
 * Implementation of the {@code Control} logic to load from a properties file in
 * UTF-8 encoding, where the ordinary {@code ResourceBundle} class wouold use
 * the ISO-8859-1 encoding. It can be used as <pre>
 * ResourceBundle bundle = ResourceBundle.getBundle("myBundle", new UTF8Control());
 * </pre>
 * 
 * @author Andrea Funto'
 */
public class UTF8Control extends Control {

	/**
	 * @see java.util.ResourceBundle.Control#newBundle(java.lang.String, java.util.Locale, java.lang.String, java.lang.ClassLoader, boolean)
	 */
	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
			throws IllegalAccessException, InstantiationException, IOException {
		
		// this is the default implementation
		String bundleName = toBundleName(baseName, locale);
		String resourceName = toResourceName(bundleName, "properties");
		ResourceBundle bundle = null;
		InputStream stream = null;
		if (reload) {
			URL url = loader.getResource(resourceName);
			if (url != null) {
				URLConnection connection = url.openConnection();
				if (connection != null) {
					connection.setUseCaches(false);
					stream = connection.getInputStream();
				}
			}
		} else {
			stream = loader.getResourceAsStream(resourceName);
		}
		if (stream != null) {
			try {
				// only this line is changed to make it to read properties files as UTF-8.
				bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
			} finally {
				stream.close();
			}
		}
		return bundle;
	}
}
