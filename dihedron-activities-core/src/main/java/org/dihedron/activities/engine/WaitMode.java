/**
 * Copyright (c) 2013, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Activities library ("Activities").
 *
 * Activities is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * Activities is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with Activities. If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.activities.engine;

/**
 * An enumeration representing how the activity should behave with respect 
 * to its sub-activities completion.
 * 
 * @author Andrea Funto'
 */
public enum WaitMode {
	/**
	 * Wait for all sub-activities to complete before returning.
	 */
	WAIT_FOR_ALL,
	
	/**
	 * Return as soon as any of the sub-activities has completed.
	 */
	WAIT_FOR_ANY
}	