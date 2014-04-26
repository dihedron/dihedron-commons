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

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.Set;

import org.dihedron.commons.filters.compound.Not;
import org.dihedron.commons.reflection.filters.IsField;
import org.dihedron.commons.reflection.filters.IsMethod;
import org.dihedron.commons.reflection.filters.IsStatic;
import org.dihedron.commons.reflection.filters.NameIs;
import org.dihedron.commons.reflection.filters.NameLike;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class ReflectionsTest {
	
	private class A {
		
		protected static final int counterA = 0;
		
		private String string = "A";
		
		protected int i = 0;
		
		public String getString() {
			return string;
		}
		
		public int getInt() {
			return i;
		}
	}
	
	private class B extends A {
		
		protected static final int counterB = 0;
		
		private long l = 1;
		
		public long getLong() {
			return l;
		}
		
		public int getInt() {
			return i + 1;
		}
		
		public long getSum() {
			return getInt() + getLong();
		}
	}
	
	private static class C {
		public static final void myStaticMethodinC() {
			
		}
	}
	
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(ReflectionsTest.class);

	@Test 
	public void testGetFieldsOfAExcludeThis0() {
		Set<Field> fields = Reflections.getInstanceFields(A.class, new Not<Field>(new NameIs<Field>("this$0")));
		assertTrue(fields.size() == 2);
		assertTrue(containsByName(fields, "i"));
		assertTrue(containsByName(fields, "string"));
	}
	
	@Test 
	public void testGetFieldsOfAIncludeThis0() {
		Set<Field> fields = Reflections.getInstanceFields(A.class, null);
//
//		Set<Member> fields = Reflections.getMembers(A.class, new ReflectorFilter[] {
//			new IsField(),
//			new IsNotStatic()
//		});
		assertTrue(fields.size() == 3);
		assertTrue(containsByName(fields, "i"));
		assertTrue(containsByName(fields, "string"));
		assertTrue(containsByName(fields, "this$0"));
	}
	
	@Test 
	public void testGetFieldsOfBExcludeThis0() {
	
		// class B
		Set<Field> fields = Reflections.getInstanceFields(B.class, new Not<Field>(new NameIs<Field>("this$0")));
//		Set<Member> fields = Reflections.getMembers(B.class, new ReflectorFilter[] {
//			new IsField(),
//			new IsNotStatic(),
//			new NameIsNot("this$0")			
//		});
		for(Member field : fields) {
			logger.trace("field: '{}'", field.getName());
		}
		assertTrue(fields.size() == 3);
		assertTrue(containsByName(fields, "i"));
		assertTrue(containsByName(fields, "string"));
		assertTrue(containsByName(fields, "l"));
	}
	
	@Test 
	public void testGetFieldsOfBIncludeThis0() {
	
		Set<Field> fields = Reflections.getInstanceFields(B.class, null);
//		Set<Member> fields = Reflections.getMembers(B.class, new ReflectorFilter[] {
//			new IsField(),
//			new IsNotStatic()
//		});
		for(Member field : fields) {
			logger.trace("field: '{}'", field.getName());
		}
		assertTrue(fields.size() == 4);
		assertTrue(containsByName(fields, "i"));
		assertTrue(containsByName(fields, "string"));
		assertTrue(containsByName(fields, "l"));
		assertTrue(containsByName(fields, "this$0"));
	}
	
//	@Test 
//	public void testGetFieldsOfBIncludeThis0MaskOverridden() {
//	
//		Set<Member> fields = Reflections.getMembers(B.class, new ReflectorFilter[] {
//			new IsField(),
//			new IsNotStatic(),
//			new IsNotOverridden()
//		});
//		for(Member field : fields) {
//			logger.trace("field: '{}'", field.getName());
//		}
//		assertTrue(fields.size() == 4);
//		assertTrue(containsByName(fields, "i"));
//		assertTrue(containsByName(fields, "string"));
//		assertTrue(containsByName(fields, "l"));		
//		assertTrue(containsByName(fields, "this$0")); // only once
//	}
//	
//	@Test 
//	public void testGetMethodsOfA() {
//		Set<Member> methods = Reflections.getMembers(A.class,  new ReflectorFilter[] {
//			new IsMethod(),
//			new IsNotStatic()
//		});
//		for(Member method : methods) {
//			logger.trace("method: '{}'", method.getName());
//		}
//		assertTrue(methods.size() == 2);
//		assertTrue(containsByName(methods, "getInt"));
//		assertTrue(containsByName(methods, "getString"));
//	}
//	
//	@Test 
//	public void testGetMethodsOfB() {
//		Set<Member> methods = Reflections.getMembers(B.class,  new ReflectorFilter[] {
//			new IsMethod(),
//			new IsNotStatic()
//		});
//		for(Member method : methods) {
//			logger.trace("method: '{}'", method.getName());
//		}
//		assertTrue(methods.size() == 5);
//		assertTrue(containsByName(methods, "getInt")); // this is there twice
//		assertTrue(containsByName(methods, "getString"));
//		assertTrue(containsByName(methods, "getLong"));
//		assertTrue(containsByName(methods, "getSum"));
//	}
//	
//	@Test 
//	public void testGetMethodsOfBMaskOverridden() {
//		Set<Member> methods = Reflections.getMembers(B.class,  new ReflectorFilter[] {
//			new IsMethod(),
//			new IsNotStatic(),
//			new IsNotOverridden()
//		});
//		for(Member method : methods) {
//			logger.trace("method: '{}'", method.getName());
//		}
//		assertTrue(methods.size() == 4);
//		assertTrue(containsByName(methods, "getInt")); // this is there once only
//		assertTrue(containsByName(methods, "getString"));
//		assertTrue(containsByName(methods, "getLong"));
//		assertTrue(containsByName(methods, "getSum"));
//	}
//
//	@Test 
//	public void testGetMethodsOfBWithPatternMaskOverridden() {
//		Set<Member> methods = Reflections.getMembers(B.class,  new ReflectorFilter[] {
//			new IsMethod(),
//			new IsNotStatic(),
//			new NameLike("getS.*"),
//			new IsNotOverridden()
//		});
//		for(Member method : methods) {
//			logger.trace("method: '{}'", method.getName());
//		}
//		assertTrue(methods.size() == 2);
//		assertTrue(containsByName(methods, "getString"));
//		assertTrue(containsByName(methods, "getSum"));
//	}
//	
//	@Test 
//	public void testGetStaticFieldsOfB() {
//		Set<Member> fields = Reflections.getMembers(B.class,  new ReflectorFilter[] {
//			new IsField(),
//			new IsStatic()
//		});
//		for(Member field : fields) {
//			logger.trace("field: '{}'", field.getName());
//		}
//		assertTrue(fields.size() == 2);
//		assertTrue(containsByName(fields, "counterA"));
//		assertTrue(containsByName(fields, "counterB"));
//	}
//
//	
//	@Test 
//	public void testGetStaticMethodsOfC() {
//		Set<Member> methods = Reflections.getMembers(C.class,  new ReflectorFilter[] {
//			new IsMethod(),
//			new IsStatic()
//		});
//		for(Member method : methods) {
//			logger.trace("method: '{}'", method.getName());
//		}
//		assertTrue(methods.size() == 1);
//		assertTrue(containsByName(methods, "myStaticMethodinC"));
//	}
//	
//	@Test 
//	public void testGetMethodsOfBWithORedConditions() {
//		Set<Member> methods = Reflections.getMembers(B.class,  
//				new ReflectorFilter[] {
//					new IsMethod(),
//					new IsNotStatic(),
//					new NameIs("getSum")
//				},
//				new ReflectorFilter[] {
//						new IsMethod(),
//						new IsNotStatic(),
//						new NameIs("getLong")
//				}				
//		);
//		for(Member method : methods) {
//			logger.trace("method: '{}'", method.getName());
//		}
//		assertTrue(methods.size() == 2);
//		assertTrue(containsByName(methods, "getSum"));
//		assertTrue(containsByName(methods, "getLong"));
//		
//	}
	
	
	/**
	 * Checks if the given collection of {@code Method}s or {@code Field}s has 
	 * (at least) one element with the given name.
	 * 
	 * @param members
	 *   the collection of {@code Method}s or {@code Field}s.
	 * @param name
	 *   the name of the element to look for.
	 * @return
	 *   {@code true} if an element with the given name was found, {@code false}
	 *   otherwise.
	 */
	private static boolean containsByName(Set<?> members, String name) {		
		for(Object element : members) {
			Member member = (Member)element;
			logger.trace("{}", member.getName());;
			if(member.getName().equals(name)) {
				return true;
			}
		}
		return false;		
	}
	
}
