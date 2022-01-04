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

package com.liferay.batch.engine.internal.reader;

import com.liferay.petra.io.unsync.UnsyncBufferedReader;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class CSVBatchEngineImportTaskItemReaderImpl
	implements BatchEngineImportTaskItemReader {

	public CSVBatchEngineImportTaskItemReaderImpl(
			String delimiter, InputStream inputStream)
		throws IOException {

		_delimiter = delimiter;

		_inputStream = inputStream;

		_unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(_inputStream));

		_fieldNames = StringUtil.split(
			_unsyncBufferedReader.readLine(), delimiter);
	}

	@Override
	public void close() throws IOException {
		_unsyncBufferedReader.close();
	}

	@Override
	public Map<String, Object> read() throws Exception {
		String line = _unsyncBufferedReader.readLine();

		String escapedDelimiter = _delimiter;
		if (line == null) {
			return null;
		}
		switch (escapedDelimiter) {
			case StringPool.OPEN_BRACKET:
			case StringPool.CLOSE_BRACKET:
			case StringPool.OPEN_PARENTHESIS:
			case StringPool.CLOSE_PARENTHESIS:
			case StringPool.OPEN_CURLY_BRACE:
			case StringPool.CLOSE_CURLY_BRACE:
			case StringPool.QUESTION:
			case StringPool.PERIOD:
			case StringPool.STAR:
			case StringPool.CARET:
			case StringPool.DOLLAR:
			case StringPool.PLUS:
			case StringPool.EXCLAMATION:
			case StringPool.PIPE:
					escapedDelimiter = StringPool.BACK_SLASH + _delimiter;
					break;
			default:
		}
		String regex = escapedDelimiter + "(?=(?:[^\"|']*[\"|'][^\"|']*[\"|'])*[^\"|']*$)";

		Map<String, Object> fieldNameValueMap = new HashMap<>();
		String[] values = Validator.isNull(line) ? _EMPTY_STRING_ARRAY :
			line.split(regex);

		for (int i = 0; i < values.length; i++) {
			String fieldName = _fieldNames[i];

			if (fieldName == null) {
				continue;
			}

			String value = values[i].trim();

			if (value.isEmpty()) {
				value = null;
			}

			int lastDelimiterIndex = fieldName.lastIndexOf('_');

			if (lastDelimiterIndex == -1) {
				fieldNameValueMap.put(fieldName, value);
			}
			else {
				BatchEngineImportTaskItemReaderUtil.handleMapField(
					fieldName, fieldNameValueMap, lastDelimiterIndex, value);
			}
		}

		return fieldNameValueMap;
	}

	private final String _delimiter;
	private final String[] _fieldNames;
	private final InputStream _inputStream;
	private final UnsyncBufferedReader _unsyncBufferedReader;

	private static final String[] _EMPTY_STRING_ARRAY = new String[0];

}