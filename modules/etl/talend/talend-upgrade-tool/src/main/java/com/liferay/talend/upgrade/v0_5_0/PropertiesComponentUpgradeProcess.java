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

package com.liferay.talend.upgrade.v0_5_0;

import com.liferay.scanner.ReplacingScanner;
import com.liferay.talend.upgrade.BaseComponentUpgradeProcess;
import com.liferay.talend.upgrade.UpgradeProcessRegister;

import java.io.File;
import java.io.FileReader;

/**
 * @author Igor Beslic
 */
public class PropertiesComponentUpgradeProcess
	extends BaseComponentUpgradeProcess {

	@Override
	protected void upgradeItemFile(File file) {
		try (FileReader fileReader = new FileReader(file)) {
			ReplacingScanner replacingScanner = new ReplacingScanner(
				fileReader);

			replacingScanner.replaceAll("0.4.0.SNAPSHOT", "0.5.0.SNAPSHOT");

			replacingScanner.save(file.getAbsolutePath());
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	static {
		UpgradeProcessRegister.register(
			"0.4.0", "0.5.0", new PropertiesComponentUpgradeProcess());
	}

}