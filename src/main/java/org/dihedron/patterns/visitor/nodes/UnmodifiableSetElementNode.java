/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.visitor.nodes;

import java.util.Set;

import org.dihedron.patterns.visitor.VisitorException;

/**
 * @author Andrea Funto'
 */
public class UnmodifiableSetElementNode extends AbstractNode {
	
	/**
	 * The set to which this element belongs.
	 */
	protected Set<?> set;
	
	/**
	 * The very object in the set.
	 */
	protected Object element;
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 *   the pseudo-OGNL path of the node.
	 * @param list
	 *   the list to which the node belongs.
	 * @param int index
	 *   the index at which the element is located in the list.
	 */
	public UnmodifiableSetElementNode(String name, Set<?> set, Object element) {
		super(name);
		this.set = set;
		this.element = element;
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
		return element;
	}
}
