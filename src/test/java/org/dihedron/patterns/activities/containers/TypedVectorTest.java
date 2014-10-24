/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.activities.containers;

import static org.junit.Assert.assertTrue;

import org.dihedron.core.License;
import org.dihedron.patterns.activities.TypedVector;
import org.junit.Test;

/**
 * @author Andrea Funto'
 */
@License
public class TypedVectorTest {

	/**
	 * Test method for {@link org.dihedron.activities.types.Vector#get(int)}.
	 */
	@Test
	public void testGetInt() {
		TypedVector<String> vector = new TypedVector<String>();
		vector.add("0");
		vector.add("1");
		vector.add("2");
		vector.add("3");
		vector.add("4");
		assertTrue(vector.get(0).equals(vector.get(-5)));
		assertTrue(vector.get(1).equals(vector.get(-4)));
		assertTrue(vector.get(2).equals(vector.get(-3)));
		assertTrue(vector.get(3).equals(vector.get(-2)));
		assertTrue(vector.get(4).equals(vector.get(-1)));
	}

	/**
	 * Test method for {@link org.dihedron.activities.types.Vector#remove(int)}.
	 */
	@Test
	public void testRemoveInt() {
		TypedVector<String> vector = new TypedVector<String>();
		vector.add("0");
		vector.add("1");
		vector.add("2");
		vector.add("3");
		vector.add("4");
		vector.remove(-1);
		assertTrue(vector.size() == 4);
		assertTrue(vector.get(-1).equals("3"));
	}

	/**
	 * Test method for {@link org.dihedron.activities.types.Vector#set(int, java.lang.Object)}.
	 */
	@Test
	public void testSetIntE() {
		TypedVector<String> vector = new TypedVector<String>();
		vector.add("0");
		vector.add("1");
		vector.add("2");
		vector.add("3");
		vector.add("4");
		vector.set(-2, "5");
		assertTrue(vector.size() == 5);
		assertTrue(vector.elementAt(3).equals("5"));
	}
}
