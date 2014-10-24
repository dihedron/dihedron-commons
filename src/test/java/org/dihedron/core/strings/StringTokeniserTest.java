/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.strings;

import static org.junit.Assert.assertTrue;

import org.dihedron.core.License;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Andrea Funto'
 */
@License
public class StringTokeniserTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link org.dihedron.utils.StringTokeniser#tokenise(java.lang.String)}.
	 */
	@Test
	public void testTokenise() {
		StringTokeniser tokeniser = new StringTokeniser(";");
		tokeniser.setTrimSpaces(true);
		String[] strings = tokeniser.tokenise("test01;test02; test03;test04\t;   test05  ; test06  ");
		assertTrue(strings.length==6);
		assertTrue(strings[0].equals("test01"));
		assertTrue(strings[1].equals("test02"));
		assertTrue(strings[2].equals("test03"));
		assertTrue(strings[3].equals("test04"));
		assertTrue(strings[4].equals("test05"));
		assertTrue(strings[5].equals("test06"));

		tokeniser.setTrimSpaces(false);
		strings = tokeniser.tokenise("test01;test02; test03;test04\t;   test05  ; test06  ");
		assertTrue(strings.length==6);
		assertTrue(strings[0].equals("test01"));
		assertTrue(strings[1].equals("test02"));
		assertTrue(strings[2].equals(" test03"));
		assertTrue(strings[3].equals("test04\t"));
		assertTrue(strings[4].equals("   test05  "));
		assertTrue(strings[5].equals(" test06  "));

		tokeniser = new StringTokeniser("--");
		tokeniser.setTrimSpaces(false);
		strings = tokeniser.tokenise("test01--test02-- test03--test04\t --   test05  -- test06  --");
		assertTrue(strings.length==6);
		assertTrue(strings[0].equals("test01"));
		assertTrue(strings[1].equals("test02"));
		assertTrue(strings[2].equals(" test03"));
		assertTrue(strings[3].equals("test04\t "));
		assertTrue(strings[4].equals("   test05  "));
		assertTrue(strings[5].equals(" test06  "));
	}
}
