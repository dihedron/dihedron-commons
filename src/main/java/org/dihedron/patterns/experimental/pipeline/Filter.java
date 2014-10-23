/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.experimental.pipeline;


/**
 * A consumer that does not change the type of elements it processes, and simply 
 * acts of the elements, possibly filtering or transforming them on a per-element 
 * basis.
 * 
 * @author Andrea Funto'
 */
public interface Filter<T> extends Consumer<T, T> {

}
