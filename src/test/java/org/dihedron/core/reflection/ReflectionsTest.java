/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.core.reflection;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Set;

import org.dihedron.core.filters.compound.Not;
import org.dihedron.core.reflection.Reflections;
import org.dihedron.core.reflection.filters.HasAnnotation;
import org.dihedron.core.reflection.filters.IsOverloaded;
import org.dihedron.core.reflection.filters.NameIs;
import org.dihedron.core.reflection.filters.NameLike;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class ReflectionsTest {
	
	private class A {
		
		@SuppressWarnings("unused")
		protected static final int counterA = 0;
		
		private String string = "A";
		
		protected int i = 0;
		
		@SuppressWarnings("unused")
		public String getString() {
			return string;
		}
		
		@SuppressWarnings("unused")
		public int getInt() {
			return i;
		}
	}
	
	private class B extends A {
		
		@SuppressWarnings("unused")
		protected static final int counterB = 0;
		
		private long l = 1;
		
		public long getLong() {
			return l;
		}
		
		public int getInt() {
			return i + 1;
		}
		
		@SuppressWarnings("unused")
		public long getSum() {
			return getInt() + getLong();
		}
	}
	
	private static class C {
		@SuppressWarnings("unused")
		public static final void myStaticMethodInC() {		
		}
		
		@SuppressWarnings("unused")
		public void overloaded(int i) {
			
		}
		
		@SuppressWarnings("unused")		
		public void overloaded(String s) {			
		}
		
		@SuppressWarnings("unused")
		@SafeVarargs
		public final void annotated(Object... objects) {			
		}
	}
	
	
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(ReflectionsTest.class);

	@Test 
	public void testGetFieldsOfAExcludeThis0() {
		Set<Field> fields = Reflections.getInstanceFields(A.class, new Not<Field>(new NameIs<Field>("this$0")));
		for(Member field : fields) {
			logger.trace("field: '{}'", field.getName());
		}
		assertTrue(fields.size() == 2);
		assertTrue(containsByName(fields, "i"));
		assertTrue(containsByName(fields, "string"));
	}
	
	@Test 
	public void testGetFieldsOfAIncludeThis0() {
		Set<Field> fields = Reflections.getInstanceFields(A.class, null);
		for(Member field : fields) {
			logger.trace("field: '{}'", field.getName());
		}
		assertTrue(fields.size() == 3);
		assertTrue(containsByName(fields, "i"));
		assertTrue(containsByName(fields, "string"));
		assertTrue(containsByName(fields, "this$0"));
	}
	
	@Test 
	public void testGetFieldsOfBExcludeThis0() {
	
		// class B
		Set<Field> fields = Reflections.getInstanceFields(B.class, new Not<Field>(new NameIs<Field>("this$0")));
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
		for(Member field : fields) {
			logger.trace("field: '{}'", field.getName());
		}
		assertTrue(fields.size() == 4);
		assertTrue(containsByName(fields, "i"));
		assertTrue(containsByName(fields, "string"));
		assertTrue(containsByName(fields, "l"));
		assertTrue(containsByName(fields, "this$0"));
	}
	
	
	@Test 
	public void testGetMethodsOfA() {
		Set<Method> methods = Reflections.getInstanceMethods(A.class, null);
		for(Member method : methods) {
			logger.trace("method: '{}'", method.getName());
		}
		assertTrue(methods.size() == 2);
		assertTrue(containsByName(methods, "getInt"));
		assertTrue(containsByName(methods, "getString"));
	}
	
	@Test 
	public void testGetMethodsOfB() {
		Set<Method> methods = Reflections.getInstanceMethods(B.class, null);
		for(Member method : methods) {
			logger.trace("method: '{}'", method.getName());
		}
		assertTrue(methods.size() == 4);
		assertTrue(containsByName(methods, "getInt"));
		assertTrue(containsByName(methods, "getString"));
		assertTrue(containsByName(methods, "getLong"));
		assertTrue(containsByName(methods, "getSum"));
	}
	

	@Test 
	public void testGetMethodsOfBWithPatternInclude() {
		Set<Method> methods = Reflections.getInstanceMethods(B.class, new NameLike<Method>("getS.*"));
		for(Member method : methods) {
			logger.trace("method: '{}'", method.getName());
		}
		assertTrue(methods.size() == 2);
		assertTrue(containsByName(methods, "getString"));
		assertTrue(containsByName(methods, "getSum"));
	}

	@Test 
	public void testGetMethodsOfBWithPatternExclude() {
		Set<Method> methods = Reflections.getInstanceMethods(B.class, new Not<Method>(new NameLike<Method>("getS.*")));
		for(Member method : methods) {
			logger.trace("method: '{}'", method.getName());
		}
		assertTrue(methods.size() == 2);
		assertTrue(containsByName(methods, "getInt"));
		assertTrue(containsByName(methods, "getLong"));
	}
		
	
	@Test 
	public void testGetStaticFieldsOfB() {
		Set<Field> fields = Reflections.getClassFields(B.class, null);
		for(Member field : fields) {
			logger.trace("field: '{}'", field.getName());
		}
		assertTrue(fields.size() == 2);
		assertTrue(containsByName(fields, "counterA"));
		assertTrue(containsByName(fields, "counterB"));
	}

	
	@Test 
	public void testGetStaticMethodsOfC() {
		Set<Method> methods = Reflections.getClassMethods(C.class, null);
		for(Member method : methods) {
			logger.trace("method: '{}'", method.getName());
		}
		assertTrue(methods.size() == 1);
		assertTrue(containsByName(methods, "myStaticMethodInC"));
	}
	
	@Test 
	public void testOverloaded() {
		Set<Method> methods = Reflections.getInstanceMethods(C.class, new IsOverloaded<Method>());
		for(Member method : methods) {
			logger.trace("method: '{}'", method.getName());
		}
		assertTrue(methods.size() == 1);
		assertTrue(containsByName(methods, "overloaded"));
	}
	
	@Test 
	public void testAnnotations() {
		Set<Method> methods = Reflections.getInstanceMethods(C.class, new HasAnnotation<Method>(SafeVarargs.class));
		for(Member method : methods) {
			logger.trace("method: '{}'", method.getName());
		}
		assertTrue(methods.size() == 1);
		assertTrue(containsByName(methods, "annotated"));
		
		methods = Reflections.getInstanceMethods(C.class, new HasAnnotation<Method>(SuppressWarnings.class));
		assertTrue(methods.size() == 0); // not retained at runtime!		
	}
	
	
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
