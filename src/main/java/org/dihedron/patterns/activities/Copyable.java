/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities;

import org.dihedron.core.License;


/**
 * An interface that exposes the {@link #clone()} method for cloneable classes.
 * The original {@link java.lang.Cloneable} interface does not have a {@code clone()}
 * method, so it was necessary to wrap it.
 * 
 * @author Andrea Funto'
 */
@License
public interface Copyable extends Cloneable {
	
	/**
	 * Returns a clone of the original object, by copying its fields one by one.
	 * 
	 * @return
	 *   a clone of the original object.
	 */
	public Object clone() throws CloneNotSupportedException;
}
