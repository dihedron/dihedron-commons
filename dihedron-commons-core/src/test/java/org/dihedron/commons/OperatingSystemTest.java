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
package org.dihedron.commons;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class OperatingSystemTest {
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(OperatingSystemTest.class);

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// in a future version, I might want to install the binary library
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		// in a future version, I might want to unload the binary library
	}

	@Test
	public void test() {

		System.out.println(">" + OperatingSystem.getArchitecture() + "<");

		Map<String, String> environment = System.getenv();
		for (Entry<String, String> entry : environment.entrySet()) {
			logger.trace("ENVIRONMENT: '{}' => '{}'", entry.getKey(), entry.getValue());
		}

		Properties properties = System.getProperties();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			logger.trace("PROPERTIES: '{}' => '{}'", entry.getKey(), entry.getValue());
		}

		logger.trace("VARIABLE: '{}'", OperatingSystem.resolveKnownPathVariables("${USERHOME}/test"));
		logger.trace("VARIABLE: '{}'", OperatingSystem.resolveKnownPathVariables("${WINDIR}/test"));
		logger.trace("VARIABLE: '{}'", OperatingSystem.resolveKnownPathVariables("${SYSTEMROOT}/test"));
		logger.trace("VARIABLE: '{}'", OperatingSystem.resolveKnownPathVariables("${COMMONPROGRAMFILES}/test"));
		logger.trace("VARIABLE: '{}'", OperatingSystem.resolveKnownPathVariables("${PROGRAMFILES}/test"));
		logger.trace("VARIABLE: '{}'", OperatingSystem.resolveKnownPathVariables("${TEMP}/test"));
		logger.trace("VARIABLE: '{}'", OperatingSystem.resolveKnownPathVariables("${SYSTEMDRIVE}/test"));
		logger.trace("VARIABLE: '{}'", OperatingSystem.resolveKnownPathVariables("${USERPROFILE}/test"));
	}
}
