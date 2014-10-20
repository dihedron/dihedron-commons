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
package org.dihedron.patterns.activities;


/**
 * An interface that exposes the {@link #clone()} method for cloneable classes.
 * The original {@link java.lang.Cloneable} interface does not have a {@code clone()}
 * method, so it was necessary to wrap it.
 * 
 * @author Andrea Funto'
 */
public interface Copyable extends Cloneable {
	
	/**
	 * Returns a clone of the original object, by copying its fields one by one.
	 * 
	 * @return
	 *   a clone of the original object.
	 */
	public Object clone() throws CloneNotSupportedException;
}
