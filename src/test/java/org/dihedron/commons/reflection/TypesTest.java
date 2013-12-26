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
package org.dihedron.commons.reflection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class TypesTest {
	
	private class SuperClass {
		
	}
	
	private class SubClass extends SuperClass {
		
	}
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(TypesTest.class);
	
	private Type[] autotest(List<String> list, Map<String, List<String>> map, char[] array, Set<String> set, String string) {
		Method[] methods = this.getClass().getDeclaredMethods();
		for(Method method : methods) {
			if(method.getName().equals("autotest")) {
				return method.getGenericParameterTypes();
			}
		}
		return null;
	}

	/**
	 * Test method for {@link org.dihedron.commons.reflection.Types#getAsString(java.lang.reflect.Type)}.
	 */
	@Test
	public void testGetAsString() {
		Type list = autotest(null, null, null, null, null)[0];
		Type map = autotest(null, null, null, null, null)[1];
		String string = Types.getAsString(list);
		assertTrue(string.equals("java.util.List<java.lang.String>"));
		logger.trace(string);
		string = Types.getAsString(map);
		logger.trace(string);
		assertTrue(string.equals("java.util.Map<java.lang.String, java.util.List<java.lang.String>>"));
	}

	/**
	 * Test method for {@link org.dihedron.commons.reflection.Types#getAsParametricType(java.lang.reflect.Type)}.
	 */
	@Test
	public void testGetAsParametricType() {
		Type list = autotest(null, null, null, null, null)[0];
		Type map = autotest(null, null, null, null, null)[1];
		String string = Types.getAsParametricType(list);
		logger.trace(string);
		assertTrue(string.equals("java.util.List<?>"));		
		string = Types.getAsParametricType(map);
		logger.trace(string);
		assertTrue(string.equals("java.util.Map<?, ?>"));
	}

	/**
	 * Test method for {@link org.dihedron.commons.reflection.Types#getAsRawType(java.lang.reflect.Type)}.
	 */
	@Test
	public void testGetAsRawType() {
		Type list = autotest(null, null, null, null, null)[0];
		Type map = autotest(null, null, null, null, null)[1];
		String string = Types.getAsRawType(list);
		logger.trace(string);
		assertTrue(string.equals("java.util.List"));		
		string = Types.getAsRawType(map);
		logger.trace(string);
		assertTrue(string.equals("java.util.Map"));
	}

	/**
	 * Test method for {@link org.dihedron.commons.reflection.Types#getParameterTypes(java.lang.reflect.Type)}.
	 */
	@Test
	public void testGetParameterTypes() {
		Type map = autotest(null, null, null, null, null)[1];
		Type[] types = Types.getParameterTypes(map);
		assertTrue(types[0].toString().equals("class java.lang.String"));
		assertTrue(types[1].toString().equals("java.util.List<java.lang.String>"));
	}

	/**
	 * Test method for {@link org.dihedron.commons.reflection.Types#isGeneric(java.lang.reflect.Type)}.
	 */
	@Test
	public void testIsGeneric() {
		Type map = autotest(null, null, null, null, null)[0];
		Type string = autotest(null, null, null, null, null)[4];
		assertTrue(Types.isGeneric(map));
		assertFalse(Types.isGeneric(string));
	}

	/**
	 * Test method for {@link org.dihedron.commons.reflection.Types#isSimple(java.lang.reflect.Type)}.
	 */
	@Test
	public void testIsSimple() {
		Type map = autotest(null, null, null, null, null)[0];
		Type string = autotest(null, null, null, null, null)[4];
		assertFalse(Types.isSimple(map));
		assertTrue(Types.isSimple(string));
	}

	/**
	 * Test method for {@link org.dihedron.commons.reflection.Types#isArray(java.lang.Object)}.
	 */
	@Test
	public void testIsArray() {
		assertTrue(Types.isArray(new String[] {}));
	}

	/**
	 * Test method for {@link org.dihedron.commons.reflection.Types#isList(java.lang.Object)}.
	 */
	@Test
	public void testIsList() {
		assertTrue(Types.isList(new ArrayList<String>()));
	}

	/**
	 * Test method for {@link org.dihedron.commons.reflection.Types#isOfClass(java.lang.reflect.Type, java.lang.Class)}.
	 */
	@Test
	public void testIsOfClass() {
//		fail("Not yet implemented");
		Type list = autotest(null, null, null, null, null)[0];
		Type map = autotest(null, null, null, null, null)[1];
		Type string = autotest(null, null, null, null, null)[4];
		assertTrue(Types.isOfClass(list, List.class));
		assertTrue(Types.isOfClass(map, Map.class));
		assertTrue(Types.isOfClass(string, String.class));
	}

	/**
	 * Test method for {@link org.dihedron.commons.reflection.Types#isOfSubClassOf(java.lang.Object, java.lang.Class)}.
	 */
	@Test
	public void testIsOfSubClassOf() {
		assertTrue(Types.isOfSubClassOf(new HashMap<String, String>(), Map.class));
		assertTrue(Types.isOfSubClassOf(new SubClass(), SuperClass.class));
	}

	/**
	 * Test method for {@link org.dihedron.commons.reflection.Types#isOfSuperClassOf(java.lang.Object, java.lang.Class)}.
	 */
	@Test
	public void testIsOfSuperClassOf() {
		assertTrue(Types.isOfSuperClassOf(new SuperClass(), SubClass.class));
	}
}
