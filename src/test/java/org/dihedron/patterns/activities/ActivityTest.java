/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.activities;

import static org.junit.Assert.fail;

import org.dihedron.patterns.activities.base.ParallelActivity;
import org.dihedron.patterns.activities.base.SequentialActivity;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.impl.strings.PrintToStdErr;
import org.dihedron.patterns.activities.impl.strings.PrintToStdOut;
import org.dihedron.patterns.activities.types.Scalar;
import org.dihedron.patterns.activities.types.Vector;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class ActivityTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(ActivityTest.class);
	
	private static final int MAX_PARALLEL_TESTS = 25;
	
	/**
	 * Test method for {@link org.dihedron.activities.Activity#execute(org.dihedron.activities.ActivityContext)}.
	 */
	@Test
	public void testExecuteSequentialActivity() {
		
		try {
			SequentialActivity plan = new SequentialActivity();
			plan.addActivity(new PrintToStdOut());
			
			Vector vector = new Vector();
			vector.add("1");
			vector.add("2");
			vector.add("3");
			vector.add("4");
			vector.add("5");
			plan.perform(null, vector);
		} catch (ActivityException e) {
			logger.error("exception executing plan", e);
			fail("exception thrown!");
		}
	}
	
	
	/**
	 * Test method for {@link org.dihedron.activities.Activity#execute(org.dihedron.activities.ActivityContext)}.
	 */
	@Test
	public void testExecuteParallelActivityOnScalar() {
		
		try {
			ParallelActivity plan = new ParallelActivity();
			for(int i = 0; i < MAX_PARALLEL_TESTS; ++i) {
				plan.addActivity(new PrintToStdOut());
			}			
			plan.perform(null, new Scalar("0"));
			
		} catch (ActivityException e) {
			logger.error("exception executing plan", e);
			fail("exception thrown!");
		}
	}

	/**
	 * Test method for {@link org.dihedron.activities.Activity#execute(org.dihedron.activities.ActivityContext)}.
	 */
	@Test
	public void testExecuteParallelActivityOnVector() {
		
		try {
			ParallelActivity plan = new ParallelActivity();
			for(int i = 0; i < MAX_PARALLEL_TESTS; ++i) {
				plan
					.addActivity(new PrintToStdOut())
					.addActivity(new PrintToStdErr())
					;
			}
			
			Vector vector = new Vector();
			vector.add("1");
			vector.add("2");
			vector.add("3");
			vector.add("4");
			vector.add("5");
			plan.perform(null, vector);			
		} catch (ActivityException e) {
			logger.error("exception executing plan", e);
			fail("exception thrown!");
		}
	}
	
}
