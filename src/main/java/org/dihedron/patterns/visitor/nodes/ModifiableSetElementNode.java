/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.visitor.nodes;

import java.util.Set;

import org.dihedron.core.License;
import org.dihedron.patterns.visitor.VisitorException;

/**
 * @author Andrea Funto'
 */
@License
public class ModifiableSetElementNode extends UnmodifiableSetElementNode {
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 *   the pseudo-OGNL path of the node.
	 * @param object
	 *   the object owning the field represented by this node object
	 * @param field
	 *   the field containing the object represented by this node object.
	 */
	public ModifiableSetElementNode(String name, Set<?> set, Object element) {
		super(name, set, element);
	}

	/**
	 * @see org.dihedron.patterns.visitor.nodes.AbstractNode#setValue(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void setValue(Object value) throws VisitorException {
		((Set<Object>)set).remove(element);
		((Set<Object>)set).add(value);
	}
}
