/**
 * Copyright (c) 2013, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Activities library ("Activities").
 *
 * Activities is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * Activities is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with Activities. If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.activities;

import static org.junit.Assert.fail;

import org.dihedron.activities.base.ParallelActivity;
import org.dihedron.activities.base.SequentialActivity;
import org.dihedron.activities.exceptions.ActivityException;
import org.dihedron.activities.impl.strings.PrintToStdErr;
import org.dihedron.activities.impl.strings.PrintToStdOut;
import org.dihedron.activities.types.Scalar;
import org.dihedron.activities.types.Vector;
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
