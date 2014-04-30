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
package org.dihedron.core.library;


/**
 * @author Andrea Funto'
 */
public enum Traits {
	
	/**
	 * The name of the library.
	 */
	NAME("${library}.name"),
	
	/**
	 * The version of the library.
	 */
	VERSION("${library}.version"),

	/**
	 * The version of the library.
	 */
	DESCRIPTION("${library}.description"),
	
	/**
	 * The web site of the library.
	 */
	WEBSITE("${library}.website");

	/**
	 * Returns the key of the property for the given library.
	 * 
	 * @param library
	 *   the library for which the property should be retrieved.
	 * @return
	 *   the key of the property for the given library.
	 */
	String toString(String library) {
		return trait.replaceFirst("\\$\\{library\\}", library);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param trait
	 *   the key of the property.
	 */
	private Traits(String trait) {
		this.trait = trait;
	}
	
	/**
	 * The key of the property.
	 */
	private String trait;
}
