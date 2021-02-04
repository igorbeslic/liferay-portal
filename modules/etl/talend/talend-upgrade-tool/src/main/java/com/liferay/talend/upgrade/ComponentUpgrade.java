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

import com.liferay.talend.upgrade.v0_4_0.PropertiesComponentUpgradeProcess;

/**
 * @author Igor Beslic
 */
public class ComponentUpgrade {

	public static void main(String[] args) {
		String fromVersion = args[0];
		String toVersion = args[1];
		String path = args[2];

		_initializeUpgradeProcesses();

		ComponentUpgradeProcess componentUpgradeProcess =
			UpgradeProcessRegister.getComponentUpgradeProcess(
				fromVersion, toVersion);

		componentUpgradeProcess.upgrade(path);
	}

	private static void _initializeUpgradeProcesses() {
		new PropertiesComponentUpgradeProcess();
		new com.liferay.talend.upgrade.v0_5_0.
			PropertiesComponentUpgradeProcess();
		new com.liferay.talend.upgrade.v0_6_0.PropertiesComponentUpgradeProcess();
	}

}