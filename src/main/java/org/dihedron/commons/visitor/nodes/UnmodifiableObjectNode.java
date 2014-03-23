/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Dihedron Common Utilities library ("Commons").
 *
 * "Commons" is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * "Commons" is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with "Commons". If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.commons.visitor.nodes;

import java.lang.reflect.Field;

import org.dihedron.commons.visitor.VisitorException;

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
	 * @see org.dihedron.commons.visitor.nodes.AbstractNode#getValue()
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
	 * @see org.dihedron.commons.visitor.Node#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) throws VisitorException {
		throw new VisitorException("Unsupported operation");
	}
}
