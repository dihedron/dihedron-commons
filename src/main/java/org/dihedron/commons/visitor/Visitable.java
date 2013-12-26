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

package org.dihedron.commons.visitor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to identify objects that will be inspected during an object
 * visit; unless annotated, nested objects are treated as opaque types and not
 * recursed upon during a visit. Embedded objects, on the contrary, are treated
 * as recursion points and a new sub-visit is started on them. This behaviour 
 * also occurs on containers such as arrays, lists, maps and sets.
 * 
 * @author Andrea Funto'
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface Visitable {
	
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
	boolean value() default false;
}
