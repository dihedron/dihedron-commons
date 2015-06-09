/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.dihedron.core.License;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@License
public class RegexTest {
	
	private interface RegexFactory {
		public Regex makeRegex(String pattern);
	}
	
//	private static final Logger logger = LoggerFactory.getLogger(RegexTest.class);

	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
	}
	
	
	@Test
	public void testRegex() throws InterruptedException {
		System.out.println("---------------------------------------------------------");
		long start = System.currentTimeMillis();
		runTest(new RegexFactory() {
			@Override
			public Regex makeRegex(String pattern) {
				return new Regex(pattern);
			}			
		});
		System.out.println("test took " + (System.currentTimeMillis() - start) + " ms");
	}	
	
	private void runTest(final RegexFactory factory) throws InterruptedException {		
		
		// prepare tasks
		List<Callable<Void>> tasks = new ArrayList<>();
		int cores = Runtime.getRuntime().availableProcessors();
		System.out.println("running test on " + cores + " available (logical) cores");
		for(int i = 0; i < cores; ++i) {
			tasks.add(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					
					System.out.println("thread: " + Thread.currentThread().getId());
					
					for(int i = 0; i < 1000; ++i) {
						Regex regex1 = factory.makeRegex("^pipp\\d*\\.pdf");
						assertFalse(regex1.matches("pippo1.pdf"));
						assertTrue(regex1.matches("pipp1.pdf"));
						assertFalse(regex1.matches("pipp1.pdff"));
						assertFalse(regex1.matches("pippo.pdf"));
						assertTrue(regex1.matches("pipp.pdf"));
						assertFalse(regex1.matches("pluto.pdf"));
						
						Regex regex2 = factory.makeRegex("^pipp\\d*\\.pdf");
						assertFalse(regex2.matches("pippo1.pdf"));
						assertTrue(regex2.matches("pipp1.pdf"));
						assertFalse(regex2.matches("pipp1.pdff"));
						assertFalse(regex2.matches("pippo.pdf"));
						assertTrue(regex2.matches("pipp.pdf"));
						assertFalse(regex2.matches("pluto.pdf"));
						
						Regex regex3 = factory.makeRegex("([a-z]*)\\:=([a-zA-Z0-9]*)");
						List<String[]> matches = regex3.getAllMatches("var:=valueNumber0,val:=valueNumber1");
						assertTrue(matches.get(0)[0].equals("var"));
						assertTrue(matches.get(0)[1].equals("valueNumber0"));
						assertTrue(matches.get(1)[0].equals("val"));
						assertTrue(matches.get(1)[1].equals("valueNumber1"));						
					}
					return null;
				}
			});
		}
		
		final ExecutorService pool = Executors.newFixedThreadPool(cores);
		pool.invokeAll(tasks);
		pool.shutdown();
		pool.awaitTermination(120, TimeUnit.SECONDS);
	}	
}
