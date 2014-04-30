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
package org.dihedron.commons.properties;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

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
	 * Test method for {@link org.dihedron.commons.properties.Properties#load(java.io.File)}.
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
