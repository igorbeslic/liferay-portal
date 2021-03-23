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

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Beslic
 */
public abstract class BaseComponentUpgradeProcess
	implements ComponentUpgradeProcess {

	@Override
	public void upgrade(String workspaceDirectory) {
		File directory = new File(workspaceDirectory);

		if (!directory.exists()) {
			throw new ComponentUpgradeException(
				"Unable to locate component directory " + workspaceDirectory);
		}

		List<File> files = getItemFiles(directory);

		if (files.isEmpty()) {
			throw new ComponentUpgradeException(
				"Unable to locate item files in directory " +
					workspaceDirectory);
		}

		System.out.println(
			String.format("Upgrading %d item files", files.size()));

		for (File file : files) {
			upgradeItemFile(file);
		}
	}

	protected List<File> getItemFiles(File directory) {
		List<File> itemFiles = new ArrayList<>();

		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				itemFiles.addAll(getItemFiles(file));

				continue;
			}

			String name = file.getName();

			if (name.endsWith(".item")) {
				itemFiles.add(file);
			}
		}

		return itemFiles;
	}

	protected abstract void upgradeItemFile(File file);

}