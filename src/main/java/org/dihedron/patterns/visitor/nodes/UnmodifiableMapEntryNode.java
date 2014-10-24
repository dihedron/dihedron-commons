/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.visitor.nodes;

import java.util.Map;

import org.dihedron.patterns.visitor.VisitorException;

/**
 * @author Andrea Funto'
 */
public class UnmodifiableMapEntryNode extends AbstractNode {
	
	/**
	 * The map to which this element belongs.
	 */
	protected Map<?, ?> map;
	
	/**
	 * The key of this object in the map.
	 */
	protected Object key;
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 *   the pseudo-OGNL path of the node.
	 * @param map
	 *   the list to which the node belongs.
	 * @param int index
	 *   the index at which the element is located in the list.
	 */
	public UnmodifiableMapEntryNode(String name, Map<?, ?> map, Object key) {
		super(name);
		this.map = map;
		this.key = key;
	}

	/**
	 * @see org.dihedron.patterns.visitor.nodes.AbstractNode#getValue()
	 */
	public Object getValue() throws VisitorException {
		// TODO: I might want to improve performances by caching the result; this
		// is OK under two assumptions: the iterator will reuse the nodes list 
		// it creates the first time for further iterations, and the visit is read 
		// only otherwise I would end up caching uselessly, or providing possibly
		// stale values to the user.
		return map.get(key);
	}
}
