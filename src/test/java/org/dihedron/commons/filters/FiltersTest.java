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
package org.dihedron.commons.filters;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.dihedron.commons.filters.compound.And;
import org.dihedron.commons.filters.compound.Not;
import org.dihedron.commons.filters.objects.Null;
import org.dihedron.commons.filters.strings.Equals;
import org.dihedron.commons.filters.strings.Matches;
import org.junit.Test;

/**
 * @author Andrea Funto'
 */
public class FiltersTest {
	
	/**
	 * Test method for {@link org.dihedron.commons.filters.Filters#accept(java.util.Collection, org.dihedron.commons.filters.Filter<T>[][])}.
	 */
	@Test
	public void testAccept() {
		@SuppressWarnings("unchecked")
		Collection<String> result = Filter.apply(new And<String>(
						new Not<String>(
							new Null<String>()
						),
						new Equals("string")
					), "string", "and", "then", "another", "string");

		assertTrue(result.size() == 2);
	}
	
	/**
	 * Test method for {@link org.dihedron.commons.filters.Filters#accept(java.util.Collection, org.dihedron.commons.filters.Filter<T>[][])}.
	 */
	@Test
	public void testReject() {
		@SuppressWarnings("unchecked")
		Collection<String> result = Filter.apply(
					new Not<String>(
						new And<String>(
							new Not<String>(
								new Null<String>()
							),
							new Equals("string")
						)
					), 
					"string", "and", "then", "another", "string");

		assertTrue(result.size() == 3);
	}

	/**
	 * Test method for {@link org.dihedron.commons.filters.Filters#accept(java.util.Collection, org.dihedron.commons.filters.Filter<T>[][])}.
	 */
	@Test
	public void testPattern() {
		@SuppressWarnings("unchecked")
		Collection<String> result = Filter.apply(
					new And<String>(
						new Not<String>(
							new Null<String>()
						),
						new Matches(".*th.*")
					), 
					"string", "and", "then", "another", "string");

		assertTrue(result.size() == 2);
	}
	
}
