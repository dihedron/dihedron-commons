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
package org.dihedron.patterns.visitor.nodes;

import org.dihedron.patterns.visitor.VisitorException;

/**
 * @author Andrea Funto'
 */
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
