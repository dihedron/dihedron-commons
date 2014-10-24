/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.functional;

import org.dihedron.core.License;


/**
 * The interface that must be implemented to iterate over collection elements in 
 * a pseudo-functional way.
 * 
 * @author Andrea Funto'
 */
@License
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
