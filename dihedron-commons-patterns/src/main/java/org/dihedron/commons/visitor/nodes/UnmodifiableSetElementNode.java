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

import java.util.Set;

import org.dihedron.commons.visitor.VisitorException;

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
	 * @see org.dihedron.commons.visitor.nodes.AbstractNode#getValue()
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
