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

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.dihedron.commons.filters.Filter;
import org.dihedron.commons.filters.compound.And;
import org.dihedron.commons.filters.compound.Not;
import org.dihedron.commons.filters.logic.True;
import org.dihedron.commons.reflection.filters.IsOverridden;
import org.dihedron.commons.reflection.filters.IsStatic;

/**
 * @author Andrea Funto'
 */
public class Reflections {

	/**
	 * Returns the set of instance fields of the given class, including those 
	 * inherited from the super-classes.
	 * 
	 * @param clazz
	 *   the class whose fields are being retrieved.
	 * @param filters
	 *   an optional set of names for the fields to be looked up; only fields 
	 *   with those names will be returned.
	 * @return
	 *   the set of fields from the given class and its super-classes.
	 */
	@SuppressWarnings("unchecked")
	public static Set<Field> getInstanceFields(Class<?> clazz, Filter<Field> filter) {
		return getFields(clazz, 
				new And<Field>(
					filter != null ? filter : new True<Field>(), 
					new Not<Field>(
						new IsStatic<Field>()
					), 
					new Not<Field>(
						new IsOverridden<Field>()
					)
				)
			);
	}
		
	/**
	 * Returns the set of class (static) fields of the given class, including those 
	 * inherited from the super-classes.
	 * 
	 * @param clazz
	 *   the class whose fields are being retrieved.
	 * @param filters
	 *   an optional set of names for the fields to be looked up; only fields 
	 *   with those names will be returned.
	 * @return
	 *   the set of static fields from the given class and its super-classes.
	 */
	@SuppressWarnings("unchecked")
	public static Set<Field> getClassFields(Class<?> clazz, Filter<Field> filter) {
		return getFields(clazz, 
				new And<Field>(
					filter != null ? filter : new True<Field>(), 
					new IsStatic<Field>(),
					new Not<Field>(
						new IsOverridden<Field>()
					)
				)
			);
	}	
	
	/**
	 * Returns the set of instance methods of the given class, including those 
	 * inherited from the super-classes.
	 * 
	 * @param clazz
	 *   the class whose methods are being retrieved.
	 * @param filters
	 *   an optional set of names for the methods to be looked up; only methods 
	 *   with those names will be returned.
	 * @return
	 *   the set of methods from the given class and its super-classes.
	 */
	@SuppressWarnings("unchecked")
	public static Set<Method> getInstanceMethods(Class<?> clazz, Filter<Method> filter) {
		return getMethods(clazz, 
				new And<Method>(
					filter != null ? filter : new True<Method>(), 
					new Not<Method>(
						new IsStatic<Method>()
					), 
					new Not<Method>(
						new IsOverridden<Method>()
					)
				)
			);
	}
	
	/**
	 * Returns the set of class methods of the given class, including those 
	 * inherited from the super-classes.
	 * 
	 * @param clazz
	 *   the class whose methods are being retrieved.
	 * @param filters
	 *   an optional set of names for the methods to be looked up; only methods 
	 *   with those names will be returned.
	 * @return
	 *   the set of class methods from the given class and its super-classes.
	 */
	@SuppressWarnings("unchecked")
	public static Set<Method> getClassMethods(Class<?> clazz, Filter<Method> filter) {
		return getMethods(clazz, 
				new And<Method>(
					filter != null ? filter : new True<Method>(), 
					new IsStatic<Method>(), 
					new Not<Method>(
						new IsOverridden<Method>()
					)
				)
			);
	}

	/**
	 * Returns the set of fields of the given class, including those inherited 
	 * from the super-classes; if provided, complex filter criteria can be applied.
	 * 
	 * @param clazz
	 *   the class whose fields are being retrieved.
	 * @param filters
	 *   an optional set of filter arrays; all filters in each array must be 
	 *   matched for an element to be accepted (ANDed); each array is applied 
	 *   and as soon as one matches the element is accepted (ORed).
	 * @return
	 *   the set of fields from the given class and its super-classes.
	 */
	public static Set<Field> getFields(Class<?> clazz, Filter<Field> filter) {
		Set<Field> fields = new HashSet<Field>();
		
		Class<?> cursor = clazz;
		while(cursor != null && cursor != Object.class) {
			// get all fields and apply filters
			fields.addAll(Filter.apply(filter , cursor.getDeclaredFields()));
			// up one step on the hierarchy
			cursor = cursor.getSuperclass();
		}		
		return fields;
	}
	
	/**
	 * Returns the set of methods of the given class, including those inherited 
	 * from the super-classes; if provided, complex filter criteria can be applied.
	 * 
	 * @param clazz
	 *   the class whose methods are being retrieved.
	 * @param filters
	 *   an optional set of filter arrays; all filters in each array must be 
	 *   matched for an element to be accepted (ANDed); each array is applied 
	 *   and as soon as one matches the element is accepted (ORed).
	 * @return
	 *   the set of methods from the given class and its super-classes.
	 */
	public static Set<Method> getMethods(Class<?> clazz, Filter<Method>filter) {
		Set<Method> methods = new HashSet<Method>();
		
		Class<?> cursor = clazz;
		while(cursor != null && cursor != Object.class) {
			// get all methods and apply filters
			methods.addAll(Filter.apply(filter, cursor.getDeclaredMethods()));
			// up one step on the hierarchy
			cursor = cursor.getSuperclass();
		}		
		return methods;
	}	
	
	/**
	 * Returns the set of fields and/or methods of the given class, including 
	 * those inherited from the super-classes; if provided, complex filter
	 * criteria can be applied.
	 * 
	 * @param clazz
	 *   the class whose fields and methods are being retrieved.
	 * @param filters
	 *   an optional set of filter arrays; all filters in each array must be 
	 *   matched for an element to be accepted (ANDed); each array is applied 
	 *   and as soon as one matches the element is accepted (ORed).
	 * @return
	 *   the set of fields and/or methods from the given class and its 
	 *   super-classes.
	 */
	public static Set<Member> getMembers(Class<?> clazz, Filter<Member> filter) {
		Set<Member> members = new HashSet<Member>();
		
		Class<?> cursor = clazz;
		while(cursor != null && cursor != Object.class) {
			// get all fields and apply filters			
			members.addAll(Filter.apply(filter, cursor.getDeclaredFields()));
			// get all methods and apply filters
			members.addAll(Filter.apply(filter, cursor.getDeclaredMethods()));
			// up one step on the hierarchy
			cursor = cursor.getSuperclass();
		}		
		return members;
	}
		
	/**
	 * Private constructor: do not allow instantiation of library class.
	 */
	private Reflections() {
	}
}
