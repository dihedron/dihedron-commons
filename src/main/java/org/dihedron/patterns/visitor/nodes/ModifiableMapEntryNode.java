/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.visitor.nodes;

import java.util.Map;

import org.dihedron.patterns.visitor.VisitorException;

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
	 * @see org.dihedron.patterns.visitor.nodes.AbstractNode#getValue()
	 */
	@SuppressWarnings("unchecked")
	public void setValue(Object value) throws VisitorException {
		((Map<Object, Object>)map).put(key, value);
	}
}
