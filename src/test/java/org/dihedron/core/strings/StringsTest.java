/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.strings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Andrea Funto'
 */
public class StringsTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link org.dihedron.utils.Strings#isValid(java.lang.String)}.
	 */
	@Test
	public void testIsValid() {
		assertTrue(Strings.isValid(" valid "));
		assertTrue(Strings.isValid(" _ "));
		assertFalse(Strings.isValid("    "));
		assertFalse(Strings.isValid("\t"));
		assertFalse(Strings.isValid(null));
	}

	/**
	 * Test method for {@link org.dihedron.utils.Strings#trim(java.lang.String)}.
	 */
	@Test
	public void testTrim() {
		assertTrue(Strings.trim(" string ").equals("string"));
		assertTrue(Strings.trim(null) == null);
	}
	
	/**
	 * Test method for {@link org.dihedron.utils.Strings#trimLeft(java.lang.String)}.
	 */
	@Test
	public void testTrimLeft() {
		assertTrue(Strings.trimLeft(" string ").equals("string "));
		assertTrue(Strings.trimLeft("\t \tstring ").equals("string "));
		assertTrue(Strings.trimLeft(null) == null);
	}
	
	/**
	 * Test method for {@link org.dihedron.utils.Strings#trimLeft(java.lang.String)}.
	 */
	@Test
	public void testTrimRight() {
		assertTrue(Strings.trimRight(" string \t").equals(" string"));
		assertTrue(Strings.trimRight("\t \tstring\t\t  ").equals("\t \tstring"));
		assertTrue(Strings.trimRight(null) == null);
	}
	
	/**
	 * Test method for {@link org.dihedron.utils.Strings#concatenate(java.lang.String[])}.
	 */
	@Test
	public void testConcatenate() {
		assertTrue(Strings.concatenate("hello", "world", null, "!").equals("helloworld!"));
	}
	
	/**
	 * Test method for {@link org.dihedron.utils.Strings#join(java.lang.String, java.lang.String[])}.
	 */
	@Test
	public void testJoin() {
		assertTrue(Strings.join(",", "hello", "world", null, "!").equals("hello,world,!"));
	}	

	/**
	 * Test method for {@link org.dihedron.utils.Strings#join(java.lang.String, java.lang.String[])}.
	 */
	@Test
	public void testSplit() {
		String[] strings = Strings.split("a,   happy,jolly    ,good, day,", ",");
		
		assertTrue(strings.length == 5);
		assertTrue(strings[0].equals("a"));
		assertTrue(strings[1].equals("happy"));
		assertTrue(strings[2].equals("jolly"));
		assertTrue(strings[3].equals("good"));
		assertTrue(strings[4].equals("day"));
		
	}
	
	/**
	 * Test method for {@link org.dihedron.utils.Strings#firstValidOf(java.lang.String[])}.
	 */
	@Test
	public void testFirstValidOf() {
		assertTrue(Strings.firstValidOf(" ", "\t", null, "first", "second").equals("first"));
	}

}
