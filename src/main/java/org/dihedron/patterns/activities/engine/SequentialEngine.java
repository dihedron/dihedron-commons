/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved. See LICENSE for details.
 */ 

package org.dihedron.patterns.activities.engine;

import org.dihedron.patterns.activities.TypedVector;
import org.dihedron.patterns.activities.exceptions.ActivityException;
import org.dihedron.patterns.activities.types.ActivityData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class SequentialEngine implements ActivityEngine {
	
	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(SequentialEngine.class);

	/**
	 * @throws ActivityException 
	 * @see org.dihedron.patterns.activities.engine.ActivityEngine#execute(org.dihedron.commons.TypedVector)
	 */
	@Override
	public ActivityData execute(TypedVector<ActivityInfo> infos) throws ActivityException {
		ActivityData data = null;
		if(infos != null) {
			logger.trace("executing {} activities...", infos.size());			
			for(ActivityInfo info : infos) {
				logger.trace("starting activity '{}'...", info.getActivity().getId());
				if(data == null) {
					logger.trace("... using data from the input");
					data = info.getData();
				} else {
					logger.trace("... using data from the previous step");
				}
				data = info.getActivity().perform(info.getContext(), data);
			}
		} else {
			logger.warn("no activities to execute");
		}
		return data;
	}

}
