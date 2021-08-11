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

package com.liferay.headless.admin.batch.planner.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.batch.planner.client.dto.v1_0.BatchPlanLog;
import com.liferay.headless.admin.batch.planner.client.http.HttpInvoker;
import com.liferay.headless.admin.batch.planner.client.pagination.Page;
import com.liferay.headless.admin.batch.planner.client.pagination.Pagination;
import com.liferay.headless.admin.batch.planner.client.resource.v1_0.BatchPlanLogResource;
import com.liferay.headless.admin.batch.planner.client.serdes.v1_0.BatchPlanLogSerDes;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtilsBean;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matija Petanjek
 * @generated
 */
@Generated("")
public abstract class BaseBatchPlanLogResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_batchPlanLogResource.setContextCompany(testCompany);

		BatchPlanLogResource.Builder builder = BatchPlanLogResource.builder();

		batchPlanLogResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		BatchPlanLog batchPlanLog1 = randomBatchPlanLog();

		String json = objectMapper.writeValueAsString(batchPlanLog1);

		BatchPlanLog batchPlanLog2 = BatchPlanLogSerDes.toDTO(json);

		Assert.assertTrue(equals(batchPlanLog1, batchPlanLog2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		BatchPlanLog batchPlanLog = randomBatchPlanLog();

		String json1 = objectMapper.writeValueAsString(batchPlanLog);
		String json2 = BatchPlanLogSerDes.toJSON(batchPlanLog);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		BatchPlanLog batchPlanLog = randomBatchPlanLog();

		batchPlanLog.setDispatchTriggerExternalReferenceCode(regex);
		batchPlanLog.setExportTaskExternalReferenceCode(regex);
		batchPlanLog.setImportTaskExternalReferenceCode(regex);

		String json = BatchPlanLogSerDes.toJSON(batchPlanLog);

		Assert.assertFalse(json.contains(regex));

		batchPlanLog = BatchPlanLogSerDes.toDTO(json);

		Assert.assertEquals(
			regex, batchPlanLog.getDispatchTriggerExternalReferenceCode());
		Assert.assertEquals(
			regex, batchPlanLog.getExportTaskExternalReferenceCode());
		Assert.assertEquals(
			regex, batchPlanLog.getImportTaskExternalReferenceCode());
	}

	@Test
	public void testGetBatchPlanLogsPage() throws Exception {
		Page<BatchPlanLog> page = batchPlanLogResource.getBatchPlanLogsPage(
			testGetBatchPlanLogsPage_getId(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetBatchPlanLogsPage_getId();
		Long irrelevantId = testGetBatchPlanLogsPage_getIrrelevantId();

		if (irrelevantId != null) {
			BatchPlanLog irrelevantBatchPlanLog =
				testGetBatchPlanLogsPage_addBatchPlanLog(
					irrelevantId, randomIrrelevantBatchPlanLog());

			page = batchPlanLogResource.getBatchPlanLogsPage(
				irrelevantId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantBatchPlanLog),
				(List<BatchPlanLog>)page.getItems());
			assertValid(page);
		}

		BatchPlanLog batchPlanLog1 = testGetBatchPlanLogsPage_addBatchPlanLog(
			id, randomBatchPlanLog());

		BatchPlanLog batchPlanLog2 = testGetBatchPlanLogsPage_addBatchPlanLog(
			id, randomBatchPlanLog());

		page = batchPlanLogResource.getBatchPlanLogsPage(
			id, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(batchPlanLog1, batchPlanLog2),
			(List<BatchPlanLog>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetBatchPlanLogsPageWithPagination() throws Exception {
		Long id = testGetBatchPlanLogsPage_getId();

		BatchPlanLog batchPlanLog1 = testGetBatchPlanLogsPage_addBatchPlanLog(
			id, randomBatchPlanLog());

		BatchPlanLog batchPlanLog2 = testGetBatchPlanLogsPage_addBatchPlanLog(
			id, randomBatchPlanLog());

		BatchPlanLog batchPlanLog3 = testGetBatchPlanLogsPage_addBatchPlanLog(
			id, randomBatchPlanLog());

		Page<BatchPlanLog> page1 = batchPlanLogResource.getBatchPlanLogsPage(
			id, Pagination.of(1, 2));

		List<BatchPlanLog> batchPlanLogs1 =
			(List<BatchPlanLog>)page1.getItems();

		Assert.assertEquals(
			batchPlanLogs1.toString(), 2, batchPlanLogs1.size());

		Page<BatchPlanLog> page2 = batchPlanLogResource.getBatchPlanLogsPage(
			id, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<BatchPlanLog> batchPlanLogs2 =
			(List<BatchPlanLog>)page2.getItems();

		Assert.assertEquals(
			batchPlanLogs2.toString(), 1, batchPlanLogs2.size());

		Page<BatchPlanLog> page3 = batchPlanLogResource.getBatchPlanLogsPage(
			id, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(batchPlanLog1, batchPlanLog2, batchPlanLog3),
			(List<BatchPlanLog>)page3.getItems());
	}

	protected BatchPlanLog testGetBatchPlanLogsPage_addBatchPlanLog(
			Long id, BatchPlanLog batchPlanLog)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetBatchPlanLogsPage_getId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetBatchPlanLogsPage_getIrrelevantId() throws Exception {
		return null;
	}

	@Test
	public void testGraphQLGetBatchPlanLogsPage() throws Exception {
		Long id = testGetBatchPlanLogsPage_getId();

		GraphQLField graphQLField = new GraphQLField(
			"batchPlanLogs",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);

					put("id", id);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject batchPlanLogsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/batchPlanLogs");

		Assert.assertEquals(0, batchPlanLogsJSONObject.get("totalCount"));

		BatchPlanLog batchPlanLog1 = testGraphQLBatchPlanLog_addBatchPlanLog();
		BatchPlanLog batchPlanLog2 = testGraphQLBatchPlanLog_addBatchPlanLog();

		batchPlanLogsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/batchPlanLogs");

		Assert.assertEquals(2, batchPlanLogsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(batchPlanLog1, batchPlanLog2),
			Arrays.asList(
				BatchPlanLogSerDes.toDTOs(
					batchPlanLogsJSONObject.getString("items"))));
	}

	protected BatchPlanLog testGraphQLBatchPlanLog_addBatchPlanLog()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		BatchPlanLog batchPlanLog1, BatchPlanLog batchPlanLog2) {

		Assert.assertTrue(
			batchPlanLog1 + " does not equal " + batchPlanLog2,
			equals(batchPlanLog1, batchPlanLog2));
	}

	protected void assertEquals(
		List<BatchPlanLog> batchPlanLogs1, List<BatchPlanLog> batchPlanLogs2) {

		Assert.assertEquals(batchPlanLogs1.size(), batchPlanLogs2.size());

		for (int i = 0; i < batchPlanLogs1.size(); i++) {
			BatchPlanLog batchPlanLog1 = batchPlanLogs1.get(i);
			BatchPlanLog batchPlanLog2 = batchPlanLogs2.get(i);

			assertEquals(batchPlanLog1, batchPlanLog2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<BatchPlanLog> batchPlanLogs1, List<BatchPlanLog> batchPlanLogs2) {

		Assert.assertEquals(batchPlanLogs1.size(), batchPlanLogs2.size());

		for (BatchPlanLog batchPlanLog1 : batchPlanLogs1) {
			boolean contains = false;

			for (BatchPlanLog batchPlanLog2 : batchPlanLogs2) {
				if (equals(batchPlanLog1, batchPlanLog2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				batchPlanLogs2 + " does not contain " + batchPlanLog1,
				contains);
		}
	}

	protected void assertValid(BatchPlanLog batchPlanLog) throws Exception {
		boolean valid = true;

		if (batchPlanLog.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("batchPlanId", additionalAssertFieldName)) {
				if (batchPlanLog.getBatchPlanId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"dispatchTriggerExternalReferenceCode",
					additionalAssertFieldName)) {

				if (batchPlanLog.getDispatchTriggerExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"exportTaskExternalReferenceCode",
					additionalAssertFieldName)) {

				if (batchPlanLog.getExportTaskExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"importTaskExternalReferenceCode",
					additionalAssertFieldName)) {

				if (batchPlanLog.getImportTaskExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("size", additionalAssertFieldName)) {
				if (batchPlanLog.getSize() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (batchPlanLog.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("total", additionalAssertFieldName)) {
				if (batchPlanLog.getTotal() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<BatchPlanLog> page) {
		boolean valid = false;

		java.util.Collection<BatchPlanLog> batchPlanLogs = page.getItems();

		int size = batchPlanLogs.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field :
				getDeclaredFields(
					com.liferay.headless.admin.batch.planner.dto.v1_0.
						BatchPlanLog.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		BatchPlanLog batchPlanLog1, BatchPlanLog batchPlanLog2) {

		if (batchPlanLog1 == batchPlanLog2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("batchPlanId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						batchPlanLog1.getBatchPlanId(),
						batchPlanLog2.getBatchPlanId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"dispatchTriggerExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						batchPlanLog1.getDispatchTriggerExternalReferenceCode(),
						batchPlanLog2.
							getDispatchTriggerExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"exportTaskExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						batchPlanLog1.getExportTaskExternalReferenceCode(),
						batchPlanLog2.getExportTaskExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						batchPlanLog1.getId(), batchPlanLog2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"importTaskExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						batchPlanLog1.getImportTaskExternalReferenceCode(),
						batchPlanLog2.getImportTaskExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("size", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						batchPlanLog1.getSize(), batchPlanLog2.getSize())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						batchPlanLog1.getStatus(), batchPlanLog2.getStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("total", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						batchPlanLog1.getTotal(), batchPlanLog2.getTotal())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected Field[] getDeclaredFields(Class clazz) throws Exception {
		Stream<Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_batchPlanLogResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_batchPlanLogResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator, BatchPlanLog batchPlanLog) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("batchPlanId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dispatchTriggerExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					batchPlanLog.getDispatchTriggerExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("exportTaskExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					batchPlanLog.getExportTaskExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("importTaskExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					batchPlanLog.getImportTaskExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("size")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("status")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("total")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected BatchPlanLog randomBatchPlanLog() throws Exception {
		return new BatchPlanLog() {
			{
				batchPlanId = RandomTestUtil.randomLong();
				dispatchTriggerExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				exportTaskExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				importTaskExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				size = RandomTestUtil.randomInt();
				status = RandomTestUtil.randomInt();
				total = RandomTestUtil.randomInt();
			}
		};
	}

	protected BatchPlanLog randomIrrelevantBatchPlanLog() throws Exception {
		BatchPlanLog randomIrrelevantBatchPlanLog = randomBatchPlanLog();

		return randomIrrelevantBatchPlanLog;
	}

	protected BatchPlanLog randomPatchBatchPlanLog() throws Exception {
		return randomBatchPlanLog();
	}

	protected BatchPlanLogResource batchPlanLogResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseBatchPlanLogResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;

	@Inject
	private
		com.liferay.headless.admin.batch.planner.resource.v1_0.
			BatchPlanLogResource _batchPlanLogResource;

}