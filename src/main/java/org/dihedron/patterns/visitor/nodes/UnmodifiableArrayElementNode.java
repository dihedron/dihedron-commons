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
public class UnmodifiableArrayElementNode extends AbstractNode {
	
	/**
	 * The array to which this element belongs.
	 */
	protected Object[] array;
	
	/**
	 * The index of this object in the array.
	 */
	protected int index;
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 *   the pseudo-OGNL path of the node.
	 * @param array
	 *   the array to which the node belongs.
	 * @param int index
	 *   the index at which the element is located in the array.
	 */
	public UnmodifiableArrayElementNode(String name, Object[] array, int index) {
		super(name);
		this.array = array.clone();
		this.index = index;
	}

	/**
	 * @see org.dihedron.patterns.visitor.nodes.AbstractNode#getValue()
	 */
	public Object getValue() throws VisitorException {
		// TODO: I might want to improve performances by caching the result; this
		// is OK under two assumptions: the iterator will reuse the nodes list 
		// it creates the first time for further iterations, and the visit is read 
		// only otherwise I would end up caching uselessly, or providing possibly
		// stale values to the user.
		return array[index];
	}
}
