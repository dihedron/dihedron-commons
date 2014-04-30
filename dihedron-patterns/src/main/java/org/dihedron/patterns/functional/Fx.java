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
package org.dihedron.patterns.functional;


/**
 * The interface that must be implemented to iterate over collection elements in 
 * a pseudo-functional way.
 * 
 * @author Andrea Funto'
 */
public interface Fx<S, E> {
	
	/**
	 * This method will be executed on each element in the collection or entry in
	 * the map, and may have a state object available to store information as the
	 * iteration proceeds.
	 *  
	 * @param state
	 *   a state object, used for keeping track of progression; this object will
	 *   be the final result of the processing if returned by the last invocation 
	 *   of this method on a collection element.
	 * @param element
	 *   the current element, be it a list/set element or a map emtry.
	 * @return
	 *   an object that will be passed on to the next invocation of this method 
	 *   as the {@code state} parameter. At the end of the processing, this object
	 *   will be the result of the overall iteration. 
	 */
	S apply(S state, E element);
}
