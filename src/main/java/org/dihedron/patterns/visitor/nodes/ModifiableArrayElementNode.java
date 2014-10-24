/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.visitor.nodes;

import org.dihedron.core.License;
import org.dihedron.patterns.visitor.VisitorException;

/**
 * @author Andrea Funto'
 */
@License
public class ModifiableArrayElementNode extends UnmodifiableArrayElementNode {
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 *   the pseudo-OGNL path of the node.
	 * @param array
	 *   the array to which this element mode belongs.
	 * @param index
	 *   the index of this node in the array.
	 */
	public ModifiableArrayElementNode(String name, Object[] array, int index) {
		super(name, array, index);
	}

	/**
	 * @see org.dihedron.patterns.visitor.nodes.AbstractNode#setValue(java.lang.Object)
	 */
	public void setValue(Object value) throws VisitorException {
		array[index] = value;
	}
}
