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

import java.util.Map;

import org.dihedron.commons.visitor.VisitorException;

/**
 * @author Andrea Funto'
 */
public class ModifiableMapEntryNode extends UnmodifiableMapEntryNode {
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 *   the pseudo-OGNL path of the node.
	 * @param map
	 *   the map owning this node.
	 * @param key
	 *   the key of this entry in the map.
	 */
	public ModifiableMapEntryNode(String name, Map<?, ?> map, Object key) {
		super(name, map, key);
	}

	/**
	 * @see org.dihedron.commons.visitor.nodes.AbstractNode#getValue()
	 */
	@SuppressWarnings("unchecked")
	public void setValue(Object value) throws VisitorException {
		((Map<Object, Object>)map).put(key, value);
	}
}
