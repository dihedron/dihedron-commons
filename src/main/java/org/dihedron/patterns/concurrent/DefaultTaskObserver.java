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
package org.dihedron.patterns.concurrent;


/**
 * A do-nothing implementation of the TaskObserver interface.
 * 
 * @author Andrea Funto'
 */
public class DefaultTaskObserver<T> implements TaskObserver<T> {

	/**
	 * @see org.dihedron.patterns.concurrent.TaskObserver#onTaskStarting(org.dihedron.patterns.concurrent.Task)
	 */
	@Override
	public void onTaskStarting(Task<T> task) {
	}

	/**
	 * @see org.dihedron.patterns.concurrent.TaskObserver#onTaskStarted(org.dihedron.patterns.concurrent.Task)
	 */
	@Override
	public void onTaskStarted(Task<T> task) {
	}

	/**
	 * @see org.dihedron.patterns.concurrent.TaskObserver#onTaskComplete(org.dihedron.patterns.concurrent.Task, java.lang.Object)
	 */
	@Override
	public void onTaskComplete(Task<T> task, T result) {
	}

}