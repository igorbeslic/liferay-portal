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

package com.liferay.talend.json;

/**
 * @author Igor Beslic
 */
public class JSONFormatter {

	public static String formatted(String source) {
		int count = 0;

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < source.length(); i++) {
			if (((source.charAt(i) == '{') || (source.charAt(i) == '[')) &&
				!_isInsideValue(source, i)) {

				count++;

				sb.append(source.charAt(i));
				sb.append("\n");

				for (int j = 0; j < count; j++) {
					sb.append("\t");
				}

				continue;
			}

			if (((source.charAt(i) == '}') || (source.charAt(i) == ']')) &&
				!_isInsideValue(source, i)) {

				sb.append("\n");

				for (int j = 0; j < count; j++) {
					sb.append("\t");
				}

				sb.append(source.charAt(i));

				count--;

				continue;
			}

			if ((source.charAt(i) == ',') && !_isInsideValue(source, i)) {
				sb.append(source.charAt(i));
				sb.append("\n");

				for (int j = 0; j < count; j++) {
					sb.append("\t");
				}

				continue;
			}

			if ((source.charAt(i) == '[') && !_isInsideValue(source, i)) {
			}

			sb.append(source.charAt(i));
		}

		return sb.toString();
	}

	private static boolean _isInsideValue(String source, int idx) {
		if ((source.charAt(idx) == ',') && (source.length() > (idx + 1)) &&
			(source.charAt(idx + 1) == '\\')) {

			return true;
		}

		if ((source.charAt(idx) == '{') && (source.length() > (idx + 1)) &&
			(source.charAt(idx + 1) != '"')) {

			return true;
		}

		if ((source.charAt(idx) == '}') && (source.length() > (idx + 1)) &&
			(source.charAt(idx + 1) != ',') &&
			(source.charAt(idx + 1) != '}')) {

			return true;
		}

		return false;
	}

}