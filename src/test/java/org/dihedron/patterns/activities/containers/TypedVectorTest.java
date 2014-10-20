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
package org.dihedron.patterns.activities.containers;

import static org.junit.Assert.assertTrue;

import org.dihedron.patterns.activities.TypedVector;
import org.junit.Test;

/**
 * @author Andrea Funto'
 */
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
