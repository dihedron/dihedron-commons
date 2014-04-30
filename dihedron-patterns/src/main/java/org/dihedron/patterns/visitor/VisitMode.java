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
package org.dihedron.patterns.visitor;

/**
 * An enumeration representing whether the object properties must be accessed
 * in read only mode or in read/write mode.
 *  
 * @author Andrea Funto'
 */
public enum VisitMode {
	
	/**
	 * The sub-nodes will be accessed in read-only mode.
	 */
	READ_ONLY,
	
	/**
	 * The sub-nodes will be accessed in read and write mode.
	 */
	READ_WRITE
}