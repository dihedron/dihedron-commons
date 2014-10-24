/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.engine;

import org.dihedron.core.License;
import org.dihedron.patterns.activities.engine.javase.FixedThreadPoolEngine;

/**
 * A factory to retrieve the default instance of the execution engine. By default 
 * it is a JavaSE fixed thread pool executor-service based engine, but this
 * factory can be used to store a new engine and have it used as the default.
 *  
 * @author Andrea Funto'
 */
@License
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
