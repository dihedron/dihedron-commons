/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.regex;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.dihedron.core.regex.Regex;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexTest {
	
//	private static final Logger logger = LoggerFactory.getLogger(RegexTest.class);

	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
	}


	@Test
	public void test() {
		Regex regex1 = new Regex("^pipp\\d*\\.pdf");
		assertFalse(regex1.matches("pippo1.pdf"));
		assertTrue(regex1.matches("pipp1.pdf"));
		assertFalse(regex1.matches("pipp1.pdff"));
		assertFalse(regex1.matches("pippo.pdf"));
		assertTrue(regex1.matches("pipp.pdf"));
		assertFalse(regex1.matches("pluto.pdf"));
		
		Regex regex2 = new Regex("^pipp\\d*\\.pdf");
		assertFalse(regex2.matches("pippo1.pdf"));
		assertTrue(regex2.matches("pipp1.pdf"));
		assertFalse(regex2.matches("pipp1.pdff"));
		assertFalse(regex2.matches("pippo.pdf"));
		assertTrue(regex2.matches("pipp.pdf"));
		assertFalse(regex2.matches("pluto.pdf"));
		
		Regex regex3 = new Regex("([a-z]*)\\:=([a-zA-Z0-9]*)");
		List<String[]> matches = regex3.getAllMatches("var:=valueNumber0,val:=valueNumber1");
		assertTrue(matches.get(0)[0].equals("var"));
		assertTrue(matches.get(0)[1].equals("valueNumber0"));
		assertTrue(matches.get(1)[0].equals("val"));
		assertTrue(matches.get(1)[1].equals("valueNumber1"));
//		int i = 0;
//		for(String[] match : matches) {
//			logger.trace("match number {}", i++);
//			for(String string : match) {
//				logger.trace("... match: '{}'", string);
//			}
//		}
	}
}
