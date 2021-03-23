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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Igor Beslic
 */
public abstract class BaseItemScanner {

	public BaseItemScanner(FileReader fileReader) {
		this.fileReader = fileReader;
	}

	public String nextQuotedValue() throws IOException {
		char[] buffer = new char[1024];
		char[] quotedValue = new char[1024];

		int count = -1;
		int currentIdx = 0;
		int quotedIdx = -1;

		while ((count = fileReader.read(buffer, currentIdx, 1)) != -1) {
			result = Arrays.copyOf(result, result.length + count);

			char charRed = buffer[currentIdx];

			result[charsWritten++] = charRed;

			currentIdx++;

			if (currentIdx == buffer.length) {
				currentIdx = 0;
			}

			if (Objects.equals('\"', charRed)) {
				if (quotedIdx == -1) {
					quotedIdx++;

					continue;
				}

				return new String(quotedValue, 0, quotedIdx);
			}

			if (quotedIdx == -1) {
				continue;
			}

			quotedValue[quotedIdx++] = charRed;

			if (quotedValue.length == quotedIdx) {
				quotedValue = Arrays.copyOf(
					quotedValue, quotedValue.length * 2);
			}
		}

		return null;
	}

	public String nextToken(String token) throws IOException {
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

				return token;
			}

			currentIdx = 0;
		}

		return null;
	}

	public void save(String path) {
		File file = new File(path);

		try (FileWriter fileWriter = new FileWriter(file)) {
			fileWriter.write(result, 0, charsWritten);
		}
		catch (IOException ioException) {
			System.out.println("Unable to write file " + path);
		}
	}

	protected int charsWritten;
	protected final FileReader fileReader;
	protected char[] result = new char[1024];

}