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

package com.liferay.talend.upgrade;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Beslic
 */
public class UpgradeProcessRegister {

	public static ComponentUpgradeProcess getComponentUpgradeProcess(
		String fromVersion, String toVersion) {

		Map<String, ComponentUpgradeProcess> componentUpgradeProcesses =
			_componentUpgradeProcesses.get(toVersion);

		if ((componentUpgradeProcesses == null) ||
			!componentUpgradeProcesses.containsKey(fromVersion)) {

			throw new ComponentUpgradeException(
				String.format(
					"Unable to locate upgrade process %s to %s", fromVersion,
					toVersion));
		}

		return componentUpgradeProcesses.get(fromVersion);
	}

	public static void register(
		String startVersion, String endVersion,
		ComponentUpgradeProcess componentUpgradeProcess) {

		Map<String, ComponentUpgradeProcess> versionComponentUpgradeProcess =
			_componentUpgradeProcesses.get(endVersion);

		if (versionComponentUpgradeProcess == null) {
			versionComponentUpgradeProcess = new HashMap<>();

			_componentUpgradeProcesses.put(
				endVersion, versionComponentUpgradeProcess);
		}

		versionComponentUpgradeProcess.put(
			startVersion, componentUpgradeProcess);
	}

	private static final Map<String, Map<String, ComponentUpgradeProcess>>
		_componentUpgradeProcesses = new HashMap<>();

}