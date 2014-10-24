/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.visitor.nodes;

import java.lang.reflect.Field;

import org.dihedron.patterns.visitor.VisitorException;

/**
 * @author Andrea Funto'
 */
public class UnmodifiableObjectNode extends AbstractNode {
	
	/**
	 * The object on which this field node resides.
	 */
	protected Object object;
	
	/**
	 * The field representing the node.
	 */
	protected Field field;
	
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
	public UnmodifiableObjectNode(String name, Object object, Field field) {
		super(name);
		this.object = object;
		this.field = field;
	}

	/**
	 * 
	 * @see org.dihedron.patterns.visitor.nodes.AbstractNode#getValue()
	 */
	public Object getValue() throws VisitorException {
		// TODO: I might want to improve performances by caching the result; this
		// is OK under two assumptions: the iterator will reuse the nodes list 
		// it creates the first time for further iterations, and the visit is read 
		// only otherwise I would end up caching uselessly, or providing possibly
		// stale values to the user.
		return getFieldValue(object, field, Object.class);
	}

	/**
	 * @throws VisitorException
	 *   this method is not supported on this kind of node. 
	 * @see org.dihedron.patterns.visitor.Node#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) throws VisitorException {
		throw new VisitorException("Unsupported operation");
	}
}
