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

import org.dihedron.activities.exceptions.ActivityException;
import org.dihedron.activities.types.ActivityData;
import org.dihedron.commons.TypedVector;
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
	 * @see org.dihedron.activities.engine.ActivityEngine#execute(org.dihedron.commons.TypedVector)
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
