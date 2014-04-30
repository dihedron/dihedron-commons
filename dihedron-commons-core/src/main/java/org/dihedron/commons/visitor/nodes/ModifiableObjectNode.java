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
	 * @see org.dihedron.commons.visitor.nodes.UnmodifiableObjectNode#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) throws VisitorException {
		setFieldValue(object, field, value);
	}
}
