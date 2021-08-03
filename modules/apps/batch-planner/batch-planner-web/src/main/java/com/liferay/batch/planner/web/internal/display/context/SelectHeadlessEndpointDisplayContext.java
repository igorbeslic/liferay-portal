/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.batch.planner.web.internal.display.context;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author Igor Beslic
 */
public class SelectHeadlessEndpointDisplayContext {

	public SelectHeadlessEndpointDisplayContext(
		Map<String, String> headlessEndpoints) {

		Objects.requireNonNull(headlessEndpoints);

		_headlessEndpoints = Collections.unmodifiableMap(headlessEndpoints);
	}

	public Map<String, String> getHeadlessEndpoints() {
		return _headlessEndpoints;
	}

	private final Map<String, String> _headlessEndpoints;

}