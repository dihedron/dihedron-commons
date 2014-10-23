/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 


package org.dihedron.core.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A library of utility methods for <code>Type</code> manipulation.
 * 
 * @author Andrea Funto'
 */
public final class Types {

	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Types.class);
	
	/**
	 * The textual description of a null object's type.
	 */
	public static final String NULL_TYPE_DESCRIPTION = "<null>";
	
	/**
	 * Returns the string representation of a type; all class names are fully 
	 * qualified, and generics are properly resolved, including their parameter
	 * types (e.g. Map&lt;String, String&gt;).
	 *  
	 * @param type
	 *   the type to describe.
	 * @return
	 *   a textual description of the type (e.g. Map&lt;String, String&gt;).
	 */
	public static String getAsString(Type type) {
		String result = null;
		if(isSimple(type)) {
			result = ((Class<?>)type).getCanonicalName();						
		} else if(isGeneric(type)) {
			StringBuilder buffer = new StringBuilder();
			
			// grab the name of the container class (e.g. List, Map...)
			ParameterizedType container = (ParameterizedType)type ;
			String containerType = ((Class<?>)container.getRawType()).getCanonicalName();
			buffer.append(containerType).append("<");

			// now grab the names of all generic types (those within <...>)
			Type[] generics = container.getActualTypeArguments();
			boolean first = true;
			for(Type generic : generics) {
				String genericType = getAsString(generic);
				buffer.append(first ? "" : ", ").append(genericType);
				first = false;
			}
			buffer.append(">");
			result = buffer.toString();
		}
		return result;		
	}
	
	/**
	 * Returns the string representation of a type; all class names are fully 
	 * qualified, and generic containers have a question mark for each parametric
	 * type ((e.g. Map&lt;?, ?&gt;).
	 *  
	 * @param type
	 *   the type to describe.
	 * @return
	 *   a textual description of the type ((e.g. Map&lt;?, ?&gt;)..
	 */
	public static String getAsParametricType(Type type) {
		String result = null;
		if(isSimple(type)) {
			result = ((Class<?>)type).getCanonicalName();
		} else if(isGeneric(type)) {
			StringBuilder buffer = new StringBuilder();
			ParameterizedType container = (ParameterizedType)type ;
			String containerType = ((Class<?>)container.getRawType()).getCanonicalName();
			buffer.append(containerType).append("<");
			// now grab the names of all generic types (those within <...>)
			Type[] generics = container.getActualTypeArguments();
			for(int i = 0; i < generics.length; ++i) {
				buffer.append(i == 0 ? "" : ", ").append("?");
			}
			buffer.append(">");
			result = buffer.toString();			
		}
		return result;
	}
	
	/**
	 * Returns the raw name of the type; for generic containers that is the name
	 * of the container class (e.g. List).
	 * 
	 * @param type
	 *   the type of which a string description is requested.
	 * @return
	 *   a string representing the fully qualified name of the type, with no
	 *   generic parameters indication (e.g. java.util.List).
	 */
	public static String getAsRawType(Type type) {
		String result = null;
		if(isSimple(type)) {
			result = ((Class<?>)type).getCanonicalName();
		} else if(isGeneric(type)) {
			ParameterizedType container = (ParameterizedType)type ;
			result = ((Class<?>)container.getRawType()).getCanonicalName();
		}
		return result;
	}
		
	/**
	 * Returns the parameter types for a generic container, e.g. it would return 
	 * <code>{String, int}</code> for a <code>Map&lt;String, int&gt;</code>.
	 * 
	 * @param generic
	 *   the generic class.
	 * @return
	 *   the parameter types of a generic container, null otherwise.
	 *   
	 */
	public static Type[] getParameterTypes(Type generic) {
		Type[] types = null;
		if(isGeneric(generic)) {
			types = ((ParameterizedType)generic).getActualTypeArguments();
		}
		return types;
	}
	
	/**
	 * Checks whether the given type represents a generics.
	 *  
	 * @param type
	 *   the type to check for genericity.
	 * @return
	 *   whether the type represents a generics class.
	 */
	public static boolean isGeneric(Type type) {
		return (type instanceof ParameterizedType);
	}
	
	/**
	 * Returns whether the type represents a simple type (e.g. a class).
	 *  
	 * @param type
	 *   the type to check.
	 * @return
	 *   whether the type represents a simple type (e.g. a class).
	 */
	public static boolean isSimple(Type type) {
		return (type instanceof Class<?>);
	}
	
	/**
	 * Returns whether the given object is a Java array.
	 * 
	 * @param object
	 *   the object (instance) being checked.
	 * @return
	 *   whether the given object is a Java array.
	 */
	public static boolean isArray(Object object) {
		return object!= null && object.getClass().isArray();
	}
	
	/**
	 * Returns whether the given object is a list, or a subclass thereof.
	 * 
	 * @param object
	 *   the object that is being checked as a list. 
	 * @return
	 *   whether the given object is a list.
	 */
	public static boolean isList(Object object) {
		return isOfSubClassOf(object, List.class);
	}
	
	/**
	 * Checks if the given type and the give class are the same; this check is 
	 * trivial for simple types (such as <code>java.lang.String</code>), less so 
	 * for generic types (say, <code>List&lt;String&gt;</code>), whereby the 
	 * generic (<code>List</code> in the example) is extracted before testing
	 * against the other class.
	 * 
	 * @param type
	 *   the type to be tested.
	 * @param clazz
	 *   the other class.
	 */
	public static boolean isOfClass(Type type, Class<?> clazz) {
		if(isSimple(type)) {
			logger.trace("simple: {}", ((Class<?>)type));
			return ((Class<?>)type) == clazz;
		} else if(isGeneric(type)) {
			logger.trace("generic: {}", (((ParameterizedType)type).getRawType()));
			return (((ParameterizedType)type).getRawType()) == clazz;
		}
		return false;
	}
	
	/**
	 * Returns whether the given object is an instance of a subclass of the given 
	 * class, that is if it can be cast to the given class.
	 * 
	 * @param object
	 *   the object being tested.
	 * @param clazz
	 *   the class to check.
	 * @return
	 *   whether the object under inspection can be cast to the given class.
	 */
    public static boolean isOfSubClassOf(Object object, Class<?> clazz) {
        if(object == null || clazz == null) {
        	return false;
        }
        try {
            object.getClass().asSubclass(clazz);
            return true;
        } catch(ClassCastException e) {
            return false;
        }
    }
    
	/**
	 * Returns whether the given object is an instance of a superclass of the 
	 * given class, that is if it can be cast to the given class.
	 * 
	 * @param object
	 *   the object being tested.
	 * @param clazz
	 *   the class to check.
	 * @return
	 *   whether the object under inspection can be cast to the given class.
	 */
    public static boolean isOfSuperClassOf(Object object, Class<?> clazz) {
        if(object == null || clazz == null) {
        	return false;
        }
        return object.getClass().isAssignableFrom(clazz);
    }
   	
	/**
	 * Private conctructor, to prevent improper instantiation.
	 */
	private Types() {
	}
}
