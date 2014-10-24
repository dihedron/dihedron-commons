/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.visitor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dihedron.core.License;

/**
 * Annotation used to identify objects that will be inspected during an object
 * visit; unless annotated, nested objects are treated as opaque types and not
 * recursed upon during a visit. Embedded objects, on the contrary, are treated
 * as recursion points and a new sub-visit is started on them. This behaviour 
 * also occurs on containers such as arrays, lists, maps and sets.
 * There are 4 possible behaviours:<ol>
 * <li>treat a node as a "terminal", an opaque object that constitutes a node in 
 * itself</li>
 * <li>treat an object as a visitable object, an intermediate node in the visit, 
 * whose internal fields should be exposed as nodes</li>
 * <li>with collections, treat them as lists, sets, maps, arrays of opaque 
 * objects, so that each element is regarded as a terminal node</li>
 * <li>with collections, treat them as sets, lists, maps, array of visitable
 * objects, each of which is only an intermediate node in the visit</li>
 * </ol>. This annotation provides a way to specify the visit behaviour:</ul>
 * <li>when used on a plain object, it must be able to specify whether it is 
 * behaviour 1. or 2. that is intended;</li> 
 * <li>when used on a collection, it must specify which of the 4 possible 
 * approaches should be applied.</li> 
 * </ul>. Since all 4 semantics apply to collections, and we may assume the <em>
 * lack</em> of the annotation to mean behaviour 1. (no visit, opaque object),
 * there are 3 behaviours left that apply to collections, and this is represented 
 * through two boolean flags, one expressing wehther the node is visitable, one
 * expressing whether the nodes in a collection should be visitable or opaque.
 * The second flag will be ignored when applied to non-sollection nodes. 
 * 
 * @author Andrea Funto'
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@License
public @interface Visitable {
		
	/**
	 * This parameter is applied to all kind of objects, and expresses whether
	 * the node is visitable (it's only an intermediated node in the visit, with
	 * the {@code true} value) or it is an opaque object (expressed through the
	 * default {@code false} value.
	 * 
	 * @return
	 *   {@code true} to state that the object should be visited, {@code false}
	 *   to express that the node is opaque and should be regarded as a terminal
	 *   node in the visit.
	 */
	boolean value() default true;
	
	/**
	 * This parameter is only used when visiting a generic container: if set to
	 * {@code true}, each element in the collection (be it a {@code List}, a 
	 * {@code Map} or a {@code Set}) is visited recursively as a {@code VisitableNode}, 
	 * otherwise it is assumed to be a terminal node (a {@code JavaObject}).
	 * 
	 * @return
	 *   {@code false} by default, meaning that on generic container no recursion 
	 *   is applied; declare it as {@code true} to recurse iteration.
	 */
	boolean recurse() default true;
}
