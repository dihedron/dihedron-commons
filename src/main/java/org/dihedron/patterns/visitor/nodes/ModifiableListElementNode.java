/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.visitor.nodes;

import java.util.List;

import org.dihedron.core.License;
import org.dihedron.patterns.visitor.VisitorException;

/**
 * @author Andrea Funto'
 */
@License
public class ModifiableListElementNode extends UnmodifiableListElementNode {
	
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
	public ModifiableListElementNode(String name, List<?> list, int index) {
		super(name, list, index);
	}

	/**
	 * @see org.dihedron.patterns.visitor.nodes.AbstractNode#getValue()
	 */
	@SuppressWarnings("unchecked")
	public void setValue(Object value) throws VisitorException {
		((List<Object>)list).set(index, value);
	}
}
