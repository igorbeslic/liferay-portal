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

package com.liferay.scanner;

import java.io.FileReader;

/**
 * @author Igor Beslic
 */
public class ItemScanner extends BaseItemScanner {

	public ItemScanner(FileReader fileReader) {
		super(fileReader);
	}

	public void removeLastQuotedValue() {
		char overwriteChar = result[charsWritten--];

		if (result[charsWritten] != '\"') {
			throw new IllegalStateException(
				"Unable to remove quoted content at " + charsWritten);
		}

		do {
			result[charsWritten--] = overwriteChar;
		}
		while (result[charsWritten] != '\"');

		charsWritten++;

		result[charsWritten++] = '\"';
	}

}