/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 
package org.dihedron.patterns.visitor.factories;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dihedron.core.License;
import org.dihedron.patterns.visitor.Node;
import org.dihedron.patterns.visitor.NodeFactory;
import org.dihedron.patterns.visitor.nodes.ModifiableArrayElementNode;
import org.dihedron.patterns.visitor.nodes.ModifiableListElementNode;
import org.dihedron.patterns.visitor.nodes.ModifiableMapEntryNode;
import org.dihedron.patterns.visitor.nodes.ModifiableObjectNode;
import org.dihedron.patterns.visitor.nodes.ModifiableSetElementNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
@License
public class ModifiableNodeFactory implements NodeFactory {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(ModifiableNodeFactory.class);

	/**
	 * @see org.dihedron.patterns.visitor.NodeFactory#makeObjectNode(java.lang.String, java.lang.Object, java.lang.reflect.Field)
	 */
	@Override
	public Node makeObjectNode(String name, Object object, Field field) {
		logger.trace("returning instance of unmodifiable object node for '{}'", name);
		return new ModifiableObjectNode(name, object, field);
	}

	/**
	 * @see org.dihedron.patterns.visitor.NodeFactory#makeListElementNode(java.lang.String, java.util.List, int)
	 */
	@Override
	public Node makeListElementNode(String name, List<?> list, int index) {
		logger.trace("returning instance of unmodifiable list element node for '{}'", name);
		return new ModifiableListElementNode(name, list, index);
	}

	/**
	 * @see org.dihedron.patterns.visitor.NodeFactory#makeArrayElementNode(java.lang.String, java.lang.Object[], int)
	 */
	@Override
	public Node makeArrayElementNode(String name, Object[] array, int index) {
		logger.trace("returning instance of unmodifiable array element node for '{}'", name);
		return new ModifiableArrayElementNode(name, array, index);
	}

	/**
	 * @see org.dihedron.patterns.visitor.NodeFactory#makeSetElementNode(java.lang.String, java.util.Set, int)
	 */
	@Override
	public Node makeSetElementNode(String name, Set<?> set, Object element) {
		logger.trace("returning instance of unmodifiable set element node for '{}'", name);
		return new ModifiableSetElementNode(name, set, element);
	}

	/**
	 * @see org.dihedron.patterns.visitor.NodeFactory#makeMapEntryNode(java.lang.String, java.util.Map, java.lang.Object)
	 */
	@Override
	public Node makeMapEntryNode(String name, Map<?, ?> map, Object key) {
		logger.trace("returning instance of unmodifiable map entry node for '{}'", name);
		return new ModifiableMapEntryNode(name, map, key);
	}
}
