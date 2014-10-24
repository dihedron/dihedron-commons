/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.visitor.nodes;

import java.lang.reflect.Field;

import org.dihedron.patterns.visitor.VisitorException;

/**
 * @author Andrea Funto'
 */
public class ModifiableObjectNode extends UnmodifiableObjectNode {

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
	public ModifiableObjectNode(String name, Object object, Field field) {
		super(name, object, field);
	}
	
	/**
	 * @see org.dihedron.patterns.visitor.nodes.UnmodifiableObjectNode#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) throws VisitorException {
		setFieldValue(object, field, value);
	}
}
