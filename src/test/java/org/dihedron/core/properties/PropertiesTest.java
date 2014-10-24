/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.properties;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.dihedron.core.properties.Properties;
import org.dihedron.core.properties.PropertiesException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class PropertiesTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(PropertiesTest.class);

	/**
	 * Test method for {@link org.dihedron.core.properties.Properties#load(java.io.File)}.
	 */
	@Test
	public void testLoadFile() {
		Properties properties = new Properties();
		try {
			File file = new File("src/test/resources/org/dihedron/commons/properties/test.properties");
			logger.trace("opening '{}'", file.getCanonicalPath());
			properties.load(file, ":=");
			assertTrue(properties.get("key1").equals("new_value1"));
			assertTrue(properties.get("key2").equals("new_value2"));
			assertTrue(properties.get("key3").equals("new_value3"));
			assertTrue(properties.get("key4").equals("new_value4"));
			assertTrue(properties.get("key5").equals("value5"));
			assertTrue(properties.get("key6").equals("value6"));
			assertTrue(properties.get("key7").equals("value with other text and other text still"));
		} catch (IOException e) {
			logger.error("error opening or reading properties file", e);
			fail("Error opening or reading properties file");
		} catch (PropertiesException e) {
			logger.error("error parsing properties file", e);
			fail("Error parsing properties file");
		}		
	}
}
