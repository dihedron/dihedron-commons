/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.filters;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.dihedron.core.filters.Filter;
import org.dihedron.core.filters.compound.And;
import org.dihedron.core.filters.compound.Not;
import org.dihedron.core.filters.objects.Null;
import org.dihedron.core.filters.strings.Equals;
import org.dihedron.core.filters.strings.Matches;
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
