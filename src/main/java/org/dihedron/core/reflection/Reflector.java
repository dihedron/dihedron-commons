/*
 * Copyright (c) 2012-2015, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.core.reflection;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.dihedron.core.License;
import org.dihedron.core.strings.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A helper class to access object properties and method through reflection.
 * 
 * @author Andrea Funto'
 */
@License
public class Reflector {

	/**
	 * Default value for whether the field access should be through getter methods
	 * or by reading the object field directly (value: <code>false</code>, meaning 
	 * that the fields are read directly through reflection, bypassing their getter 
	 * methods).
	 */
	public static final boolean DEFAULT_USE_GETTERS = false;
	
	/**
	 * Default value for whether the non-public fields and methods should be
	 * made available through the inspector (value: <code>true</code>, meaning
	 * that fields and methods will be accessed regardless of their being protected
	 * or private).
	 */
	public static final boolean DEFAULT_ACCESS_PRIVATE_MEMBERS = true;
	
	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(Reflector.class);
	
	/**
	 * The object under inspection.
	 */
	private Object object;
	
	/**
	 * Whether fields should be accessed using their getter method.
	 */
	private boolean useGetters = DEFAULT_USE_GETTERS;
	
	/**
	 * Whether private and protected fields and methods should be directly 
	 * accessed (if {@code true}) or the getter and setter methods should 
	 * be used instead (if {@code false}).
	 */
	private boolean accessPrivateMembers = DEFAULT_ACCESS_PRIVATE_MEMBERS;
		
	/**
	 * Constructor.
	 */
	public Reflector() {
		this.object = null;
		this.useGetters = DEFAULT_USE_GETTERS;
		this.accessPrivateMembers = DEFAULT_ACCESS_PRIVATE_MEMBERS;
	}
	
	/**
	 * Specifies the object to inspect.
	 * 
	 * @param object
	 *   the object under inspection.
	 * @return
	 *   the object itself, for method chaining.
	 */
	public Reflector inspect(Object object) {
		this.object = object;
		return this;
	}

	/**
	 * Sets the Reflector in a mode such that it will use getter methods to 
	 * access values; this will result in a method invocation and may lead to
	 * a value different from that stored in the field due to getter manipulation.
	 * 
	 * @return
	 *   the object itself, for method chaining.
	 */
	public Reflector usingGetters() {
		useGetters = true;
		return this;
	}
	
	/**
	 * Sets the Reflector in a mode such that it will use Java Reflection to 
	 * access values directly, actually bypassing their getter methods.
	 * 
	 * @return
	 *   the object itself, for method chaining.
	 */
	public Reflector usingReflection() {
		useGetters = false;
		return this;
	}


	/**
	 * Sets the Reflector in a mode that allows private fields and methods access.
	 *
	 * @return
	 *   the object itself, for method chaining.
	 */
	public void accessingPrivateMembers() {
		this.accessPrivateMembers = true;
	}

	/**
	 * Sets the Reflector in a mode that disallows private fields and methods access.
	 *
	 * @return
	 *   the object itself, for method chaining.
	 */
	public void avoidingPrivateMembers() {
		this.accessPrivateMembers = false;
	}
	
	/**
	 * Retrieves the value of a field.
	 * 
	 * @param fieldName
	 *   the name of the field.
	 * @return
	 *   the field value.
	 * @throws ReflectorException 
	 */
	public Object getFieldValue(String fieldName) throws ReflectorException {
		if(object == null) {
			logger.error("object is null: did you specify it using the 'inspect()' method?");
			throw new ReflectorException("object is null: did you specify it using the 'inspect()' method?");
		}
		if(!Strings.isValid(fieldName)) {
			logger.error("field name is null or not valid");
			throw new ReflectorException("field name is null or not valid");
		}
		
		Object result = null;
		String name = fieldName.trim();
		if(useGetters) {
			logger.trace("accessing value using getter");
			String methodName = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);			
			result = invoke(methodName);
		} else {
			logger.trace("accessing value through direct field access");
			Field field = null;			
			boolean needReprotect = false;
			try {
				field = object.getClass().getDeclaredField(name);				
				if(accessPrivateMembers) {
					needReprotect = unprotect(field);
				}
				result = field.get(object);
			} catch (Exception e) {
				String message = "error accessing field '" + fieldName + "'"; 
				logger.error(message, e);
				throw new ReflectorException(message, e);
			} finally {
				if(needReprotect) {				
					protect(field);
				}
			}
		}
		return result;		
	}
	
	/**
	 * Sets the value of the field.
	 * 
	 * @param fieldName
	 *   the name of the field.
	 * @param value
	 *   the new value of the field.
	 * @throws ReflectorException 
	 */
	public void setFieldValue(String fieldName, Object value) throws ReflectorException  {
		if(object == null) {
			logger.error("object is null: did you specify it using the 'inspect()' method?");
			throw new ReflectorException("object is null: did you specify it using the 'inspect()' method?");
		}
		if(!Strings.isValid(fieldName)) {
			logger.error("field name is null or not valid");
			throw new ReflectorException("field name is null or not valid");
		}

		String name = fieldName.trim();
		if(useGetters) {
			logger.trace("accessing value using setter");
			String methodName = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
			invoke(methodName);
		} else {
			logger.trace("accessing value through direct field access");
			Field field = null;
			boolean needReprotect = false;
			try {
				field = object.getClass().getDeclaredField(name);
				if(accessPrivateMembers) {
					needReprotect = unprotect(field);
				}
				field.set(object, value);
			} catch (Exception e) {
				String message = "error accessing field '" + fieldName + "'"; 
				logger.error(message, e);
				throw new ReflectorException(message, e);
			} finally {				
				if(needReprotect) {				
					protect(field);
				}		
			}
		}		
	}
	
	/**
	 * If the object is an array or a subclass of <code>List</code>, it retrieves
	 * the element at the given index.
	 * 
	 * @param index
	 *   the offset of the element to be retrieved; this value can be positive 
	 *   (and the offset is calculated from the start of the array or list), or
	 *   negative, in which case the offset is calculated according to the rule
	 *   that element -1 is the last, -2 is the one before the last, etc.
	 * @return
	 *   the element at the given index.
	 * @throws ReflectorException 
	 *   if the index is not valid for the given object.
	 */
	public Object getElementAtIndex(int index) throws ReflectorException  {
		
		if(object == null) {
			logger.error("object is null: did you specify it using the 'inspect()' method?");
			throw new ReflectorException("object is null: did you specify it using the 'inspect()' method?");
		}
		
		Object result = null;
		if(object.getClass().isArray()) {
			result = Array.get(object, translateArrayIndex(index, getArrayLength()));
		} else if (object instanceof List<?>){
			result = ((List<?>)object).get(translateArrayIndex(index, getArrayLength()));
		} else {
			throw new ReflectorException("object is not an array or a list");
		}
		return result;
	}
	
	/**
	 * Sets the value of the n-th element in an array or a list.
	 * 
	 * @param index
	 *   the offset of the element to be set; this value can be positive 
	 *   (and the offset is calculated from the start of the array or list), or
	 *   negative, in which case the offset is calculated according to the rule
	 *   that element -1 is the last, -2 is the one before the last, etc.
	 * @throws ReflectorException 
	 *   if the object is not a list or an array.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setElementAtIndex(int index, Object value) throws ReflectorException   {
		
		if(object == null) {
			logger.error("object is null: did you specify it using the 'inspect()' method?");
			throw new ReflectorException("object is null: did you specify it using the 'inspect()' method?");
		}
		
		if(object.getClass().isArray()) {
			 Array.set(object, translateArrayIndex(index, getArrayLength()), value);
		} else if (object instanceof List<?>){
			((List)object).set(index, value);
		} else {
			throw new ReflectorException("object is not an array or a list");
		}		
	}
	
	/**
	 * Returns the number of elements in the array or list object;
	 * 
	 * @return
	 *   the number of elements in the array or list object.
	 * @throws ReflectorException 
	 *   if the object is not a list or an array.
	 */
	public int getArrayLength() throws ReflectorException  {
		
		if(object == null) {
			logger.error("object is null: did you specify it using the 'inspect()' method?");
			throw new ReflectorException("object is null: did you specify it using the 'inspect()' method?");
		}
		
		int length = 0;
		if(object.getClass().isArray()) {
			length = Array.getLength(object);
		} else if (object instanceof List<?>){
			length = ((List<?>)object).size();
		} else {
			throw new ReflectorException("object is not an array or a list");
		}
		return length;
	}
	
	/**
	 * If the object is a <code>Map</code> or a subclass, it retrieves the 
	 * element corresponding to the given key.
	 * 
	 * @param key
	 *   the key corresponding to the element to be retrieved.
	 * @return
	 *   the element corresponding to the given key, or <code>null</code> if none 
	 *   found.
	 * @throws ReflectorException
	 *   if the object is not <code>Map</code>.
	 */
	public Object getValueForKey(Object key) throws ReflectorException  {

		if(object == null) {
			logger.error("object is null: did you specify it using the 'inspect()' method?");
			throw new ReflectorException("object is null: did you specify it using the 'inspect()' method?");
		}
		
		Object result = null;
		if(object instanceof Map) {
			result = ((Map<?, ?>)object).get(key);
		} else {
			throw new ReflectorException("object of class '" + object.getClass().getSimpleName() + "' is not a map");
		}
		return result;
	}

	/**
	 * If the object is a <code>Map</code> or a subclass, it sets the 
	 * element corresponding to the given key.
	 * 
	 * @param key
	 *   the key corresponding to the element to be set.
	 * @param value
	 *   the value to be set.
	 * @throws ReflectorException
	 *   if the object is not <code>Map</code>.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setValueForKey(Object key, Object value) throws ReflectorException  {
		
		if(object == null) {
			logger.error("object is null: did you specify it using the 'inspect()' method?");
			throw new ReflectorException("object is null: did you specify it using the 'inspect()' method?");
		}

		if(object instanceof Map) {
			((Map)object).put(key, value);
		} else {
			throw new ReflectorException("object is not a map");
		}
	}	
	
	/**
	 * Invokes a method on the object under inspection.
	 * 
	 * @param methodName
	 *   the name of the method.
	 * @param args
	 *   the optional method arguments.
	 * @return
	 *   the return value of the given method.
	 * @throws ReflectorException 
	 *   if any of the intermediate reflection methods raises a problem during
	 *   the object access.
	 */
	public Object invoke(String methodName, Object... args) throws ReflectorException  {
		
		if(object == null) {
			logger.error("object is null: did you specify it using the 'inspect()' method?");
			throw new ReflectorException("object is null: did you specify it using the 'inspect()' method?");
		}		
		if(!Strings.isValid(methodName)) {
			logger.error("method name is null or not valid");
			throw new ReflectorException("method name is null or not valid");
		}
		
		Method method = null;
		Object result = null;
		boolean needReprotect = false;
		try {
			method = object.getClass().getMethod(methodName);			
			if(accessPrivateMembers) {
				needReprotect = unprotect(method);
			}
			result = method.invoke(object, args);
		} catch (Exception e) {
			logger.error("error invoking method '" + methodName + "'", e);
			throw new ReflectorException("error invoking method '" + methodName + "'", e);
		} finally {	
			if(needReprotect) {
				protect(method);
			}
		}
		return result;
	}
	
	/**
	 * Checks if the given field or method is protected or private, and if so
	 * makes it publicly accessible.
	 * 
	 * @param accessible
	 *   the field or method to be made public.
	 * @return
	 *   <code>true</code> if the field or method had to be modified in order to
	 *   be made accessible, <code>false</code> if no change was needed.
	 */
	public boolean unprotect(AccessibleObject accessible) {
		if(!accessible.isAccessible()) {
			accessible.setAccessible(true);
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the given field or method is public, and if so makes it 
	 * unaccessible.
	 * 
	 * @param accessible
	 *   the field or method to be made private.
	 * @return
	 *   <code>true</code> if the field or method had to be modified in order to
	 *   be made private, <code>false</code> if no change was needed.
	 */
	public boolean protect(AccessibleObject accessible) {
		if(accessible.isAccessible()) {
			accessible.setAccessible(false);
			return true;
		}
		return false;
	}
	
	/**
	 * Utility method that translates an array index, either positive or negative, 
	 * into its positive representation. The method ensures that the index is within
	 * array bounds, then it translates negative indexes to their positive 
	 * counterparts according to the simple rule that element at index -1 is the 
	 * last element in the array, index -2 is the one before the last, and so on.
	 *  
	 * @param index
	 *   the element offset within the array or the list.
	 * @param length
	 *   the length of the array or the list.
	 * @return
	 *   the actual (positive) index of the element.
	 * @throws ReflectorException 
	 */
	private int translateArrayIndex(int index, int length) throws ReflectorException {
		if(!(index > 0 ? index < length : Math.abs(index) <= Math.abs(length))) {
			logger.error("index ({}) must be less than number of elements ({})", index, length);
			throw new ReflectorException("index must be less than number of elements");
		}
		int translated = index;
		
		if(!(translated > 0 ? translated < length : Math.abs(translated) <= Math.abs(length))){
			logger.error("index {} is out of bounds", translated);
			throw new ArrayIndexOutOfBoundsException();
		}
		if(translated < 0) {
			translated = length + translated;
		}
		return translated;
	}
}
