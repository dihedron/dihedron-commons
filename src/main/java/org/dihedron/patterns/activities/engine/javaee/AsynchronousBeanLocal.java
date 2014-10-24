/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.engine.javaee;

import java.util.concurrent.Future;

import javax.ejb.Asynchronous;
import javax.ejb.Local;

import org.dihedron.core.License;
import org.dihedron.patterns.activities.engine.ActivityCallable;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.ActivityData;


/**
 * @author Andrea Funto'
 */
@Local
@License
public interface AsynchronousBeanLocal<T> {
	@Asynchronous
	public Future<ActivityData> submit(ActivityCallable callable) throws ActivityException;
}

