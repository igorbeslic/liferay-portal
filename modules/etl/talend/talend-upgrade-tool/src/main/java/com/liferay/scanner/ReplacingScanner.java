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
import java.io.IOException;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Igor Beslic
 */
public class ReplacingScanner extends BaseItemScanner {

	public ReplacingScanner(FileReader fileReader) {
		super(fileReader);
	}

	public void replaceAll(String token, String replacement)
		throws IOException {

		while (replaceNext(token, replacement));
	}

	public boolean replaceNext(String token, String replacement)
		throws IOException {

		Objects.requireNonNull(token, "Unable to scan for token");

		char[] buffer = new char[1];

		int currentIdx = 0;

		while (fileReader.read(buffer, 0, 1) != -1) {
			if (result.length == charsWritten) {
				result = Arrays.copyOf(result, result.length * 2);
			}

			result[charsWritten++] = buffer[0];

			if (token.charAt(currentIdx) == buffer[0]) {
				if (++currentIdx < token.length()) {
					continue;
				}

				_replace(token, replacement);

				return true;
			}

			currentIdx = 0;
		}

		return false;
	}

	private void _replace(String token, String replacement) {
		charsWritten = charsWritten - token.length();

		for (int i = 0; i < replacement.length(); i++) {
			result[charsWritten++] = replacement.charAt(i);

			if (result.length == charsWritten) {
				result = Arrays.copyOf(result, result.length * 2);
			}
		}
	}

}