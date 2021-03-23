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

package com.liferay.talend.upgrade.v0_4_0;

import com.liferay.scanner.ItemScanner;
import com.liferay.scanner.ReplacingScanner;
import com.liferay.talend.common.json.JsonFinder;
import com.liferay.talend.json.JSONUtil;
import com.liferay.talend.upgrade.BaseComponentUpgradeProcess;
import com.liferay.talend.upgrade.UpgradeProcessRegister;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * @author Igor Beslic
 */
public class PropertiesComponentUpgradeProcess
	extends BaseComponentUpgradeProcess {

	@Override
	public void upgradeItemFile(File file) {
		try (FileReader fileReader = new FileReader(file)) {
			ReplacingScanner replacingScanner = new ReplacingScanner(
				fileReader);

			replacingScanner.replaceAll("0.1.0.SNAPSHOT", "0.4.0.SNAPSHOT");

			replacingScanner.save(file.getAbsolutePath());
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}

		try (FileReader fileReader = new FileReader(file)) {
			System.out.println();
			System.out.println("-------------------------------------------");
			System.out.println("Process item file " + file.getAbsolutePath());
			System.out.println("-------------------------------------------");

			ItemScanner itemScanner = _getItemScanner(fileReader);

			itemScanner.save(file.getAbsolutePath());
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private String _asString(JsonValue jsonValue) {
		if (jsonValue.getValueType() == JsonValue.ValueType.NUMBER) {
			JsonNumber jsonNumber = (JsonNumber)jsonValue;

			return jsonNumber.toString();
		}
		else if (jsonValue.getValueType() == JsonValue.ValueType.STRING) {
			JsonString jsonString = (JsonString)jsonValue;

			return jsonString.getString();
		}
		else if (jsonValue.getValueType() == JsonValue.ValueType.FALSE) {
			return "false";
		}
		else if (jsonValue.getValueType() == JsonValue.ValueType.TRUE) {
			return "true";
		}

		return null;
	}

	private ItemScanner _getItemScanner(FileReader fileReader)
		throws FileNotFoundException, IOException {

		ItemScanner itemScanner = new ItemScanner(fileReader);

		while (itemScanner.nextToken(_COMPONENT_NAME_ATTRIBUTE) != null) {
			String componentName = itemScanner.nextQuotedValue();

			if (!componentName.startsWith(
					_COMPONENT_NAME_ATTRIBUTE_VALUE_PREFIX)) {

				continue;
			}

			System.out.println();
			System.out.println("Processing " + componentName);

			String componentUniqueName = _getNextValueAttribute(
				itemScanner, _ELEMENT_PARAMETER_TAG,
				_NAME_ATTRIBUTE_UNIQUE_NAME);

			while (itemScanner.nextToken(_ELEMENT_PARAMETER_TAG) != null) {
				if (itemScanner.nextToken(_NAME_ATTRIBUTE) == null) {
					continue;
				}

				String quotedValue = itemScanner.nextQuotedValue();

				if (!Objects.equals(_NAME_ATTRIBUTE_PROPERTIES, quotedValue)) {
					continue;
				}

				System.out.println();

				String token = itemScanner.nextToken(_VALUE_ATTRIBUTE);

				Objects.requireNonNull(
					token,
					String.format(
						"Unable to locate %s in node %s where %s is %s",
						_VALUE_ATTRIBUTE, _ELEMENT_PARAMETER_TAG,
						_NAME_ATTRIBUTE, _NAME_ATTRIBUTE_PROPERTIES));

				String serializedObject = itemScanner.nextQuotedValue();

				_printUpgradeData(
					componentName, componentUniqueName,
					_sanitize(serializedObject));

				itemScanner.removeLastQuotedValue();

				break;
			}
		}

		return itemScanner;
	}

	private String _getNextValueAttribute(
			ItemScanner itemScanner, String tag, String nameAttributeValue)
		throws IOException {

		while (itemScanner.nextToken(tag) != null) {
			if (itemScanner.nextToken(_NAME_ATTRIBUTE) == null) {
				continue;
			}

			String quotedValue = itemScanner.nextQuotedValue();

			if (!Objects.equals(nameAttributeValue, quotedValue)) {
				continue;
			}

			Objects.requireNonNull(
				itemScanner.nextToken(_VALUE_ATTRIBUTE),
				String.format(
					"Unable to locate %s in node %s where %s is %s",
					_VALUE_ATTRIBUTE, tag, _NAME_ATTRIBUTE,
					nameAttributeValue));

			return itemScanner.nextQuotedValue();
		}

		return "NAME_MISSES";
	}

	private void _prettyPrint(
		String componentUniqueName, String finderExpression, String value) {

		System.out.println(
			String.format(
				"|%20s |%20s | %s |", componentUniqueName,
				finderExpression.substring(0, finderExpression.length() - 11),
				value));
	}

	private void _printUpgradeData(
		String componentName, String componentUniqueName,
		String serializedObject) {

		JsonObject jsonObject = JSONUtil.toJsonObject(serializedObject);

		for (Map.Entry<String, String> entry : _upgradeInstruction.entrySet()) {
			String key = entry.getKey();

			if (!key.startsWith(componentName)) {
				continue;
			}

			String finderExpression = key.substring(componentName.length() + 1);

			JsonFinder jsonFinder = new JsonFinder();

			JsonValue jsonValue = jsonFinder.getDescendantJsonValue(
				finderExpression, jsonObject);

			if ((jsonValue.getValueType() != JsonValue.ValueType.OBJECT) &&
				(jsonValue.getValueType() != JsonValue.ValueType.ARRAY)) {

				_prettyPrint(
					componentUniqueName, finderExpression,
					_asString(jsonValue));

				continue;
			}

			_prettyPrint(
				componentUniqueName, finderExpression, jsonValue.toString());
		}
	}

	private String _sanitize(String value) {
		return value.replace("&quot;", "\"");
	}

	private static final String _COMPONENT_NAME_ATTRIBUTE = "componentName";

	private static final String _COMPONENT_NAME_ATTRIBUTE_VALUE_PREFIX =
		"tLiferay";

	private static final String _ELEMENT_PARAMETER_TAG = "elementParameter";

	private static final String _NAME_ATTRIBUTE = "name";

	private static final String _NAME_ATTRIBUTE_PROPERTIES = "PROPERTIES";

	private static final String _NAME_ATTRIBUTE_UNIQUE_NAME = "UNIQUE_NAME";

	private static final String _VALUE_ATTRIBUTE = "value";

	private static final Map<String, String> _upgradeInstruction =
		new HashMap<String, String>() {
			{
				put(
					"tLiferayConnection:apiSpecURL>storedValue",
					"destinationFinderExpression");
				put(
					"tLiferayConnection:basicAuthorizationProperties>password>storedValue",
					"destinationFinderExpression");
				put(
					"tLiferayConnection:basicAuthorizationProperties>userId>storedValue",
					"destinationFinderExpression");
				put(
					"tLiferayConnection:loginType>storedValue",
					"destinationFinderExpression");
				put(
					"tLiferayConnection:oAuthAuthorizationProperties>oauthClientId>storedValue",
					"destinationFinderExpression");
				put(
					"tLiferayConnection:oAuthAuthorizationProperties>oauthClientSecret>storedValue",
					"destinationFinderExpression");
				put(
					"tLiferayConnection:referencedComponent>referencedDefinitionName>storedValue",
					"destinationFinderExpression");

				put(
					"tLiferayOutput:connection>apiSpecURL>storedValue",
					"destinationFinderExpression");
				put(
					"tLiferayOutput:resource>endpoint>storedValue",
					"destinationFinderExpression");
				put(
					"tLiferayOutput:resource>operations>storedValue",
					"destinationFinderExpression");
				put(
					"tLiferayOutput:resource>parametersTable>columnName>storedValue",
					"destinationFinderExpression");
				put(
					"tLiferayOutput:resource>parametersTable>valueColumnName>storedValue",
					"destinationFinderExpression");
				put(
					"tLiferayInput:connection>apiSpecURL>storedValue",
					"destinationFinderExpression");
				put(
					"tLiferayInput:resource>endpoint>storedValue",
					"destinationFinderExpression");
				put(
					"tLiferayInput:resource>operations>storedValue",
					"destinationFinderExpression");
				put(
					"tLiferayInput:resource>parametersTable>columnName>storedValue",
					"destinationFinderExpression");
				put(
					"tLiferayInput:resource>parametersTable>valueColumnName>storedValue",
					"destinationFinderExpression");
			}
		};

	static {
		UpgradeProcessRegister.register(
			"0.1.0", "0.4.0", new PropertiesComponentUpgradeProcess());
	}

}