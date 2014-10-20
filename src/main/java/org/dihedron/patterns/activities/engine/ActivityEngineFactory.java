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
package org.dihedron.patterns.activities.engine;

import org.dihedron.patterns.activities.engine.javase.FixedThreadPoolEngine;

/**
 * A factory to retrieve the default instance of the execution engine. By default 
 * it is a JavaSE fixed thread pool executor-service based engine, but this
 * factory can be used to store a new engine and have it used as the default.
 *  
 * @author Andrea Funto'
 */
public final class ActivityEngineFactory {
	
	/**
	 * The reference to the default engine.
	 */
	private static ActivityEngine defaultEngine;
	
	/**
	 * Creates a new instance of the default engine type.
	 * 
	 * @return
	 *   the reference to the new engine.
	 */
	private static ActivityEngine makeDefaultEngine() {
		return new FixedThreadPoolEngine(20);
	}
	
	/**
	 * Updates the reference to the currently registered default engine.
	 * 
	 * @param engine
	 *   the new engine reference, to be used as rthe new default.
	 */
	public static void setDefaultEngine(ActivityEngine engine) {
		defaultEngine = engine;
	}
	
	/**
	 * Retrieves the currently registered instance of engine, as a default.
	 *  
	 * @return
	 *   the currently registered instance of engine, as a default.
	 */
	public static ActivityEngine getDefaultEngine() {
		if (defaultEngine == null) {
			synchronized (ActivityEngineFactory.class) {
				if (defaultEngine == null) {
					defaultEngine = makeDefaultEngine();
				}				
			}
		}		
		return defaultEngine;
	}
	
	/**
	 * Private constructor to prevent construction.
	 */
	private ActivityEngineFactory() {	
	}
}
