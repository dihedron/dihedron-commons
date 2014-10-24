/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.variables;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class VariablesTest {
	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(VariablesTest.class);

	/**
	 * Test method for {@link org.dihedron.variables.Variables#replaceVariables(java.lang.String, org.dihedron.variables.ValueProvider[])}.
	 */
	@Test
	public void testReplaceVariablesStringValueProviderArray() {
		String text = "this ${myVariable1} ${my_Variable_2} '${my-Variable-3} ${${_PARTIAL_}4_}'! (${no_match})";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("myVariable1", "is");
		values.put("my_Variable_2", "my");
		values.put("my-Variable-3", "hallo");
		values.put("_PARTIAL_", "_MY_VARIABLE_");
		values.put("_MY_VARIABLE_4_", "world");
		text = Variables.replaceVariables(text, new MapBasedValueProvider(values));
		logger.info("text after binding variables: '{}'", text);
		assertTrue(text.equals("this is my 'hallo world'! (${no_match})"));
	}
}
