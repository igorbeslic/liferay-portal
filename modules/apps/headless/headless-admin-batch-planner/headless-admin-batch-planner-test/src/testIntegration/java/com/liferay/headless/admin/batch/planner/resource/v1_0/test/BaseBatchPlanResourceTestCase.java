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

import com.liferay.headless.admin.batch.planner.client.dto.v1_0.BatchPlan;
import com.liferay.headless.admin.batch.planner.client.http.HttpInvoker;
import com.liferay.headless.admin.batch.planner.client.pagination.Page;
import com.liferay.headless.admin.batch.planner.client.resource.v1_0.BatchPlanResource;
import com.liferay.headless.admin.batch.planner.client.serdes.v1_0.BatchPlanSerDes;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
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
public abstract class BaseBatchPlanResourceTestCase {

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

		_batchPlanResource.setContextCompany(testCompany);

		BatchPlanResource.Builder builder = BatchPlanResource.builder();

		batchPlanResource = builder.authentication(
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

		BatchPlan batchPlan1 = randomBatchPlan();

		String json = objectMapper.writeValueAsString(batchPlan1);

		BatchPlan batchPlan2 = BatchPlanSerDes.toDTO(json);

		Assert.assertTrue(equals(batchPlan1, batchPlan2));
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

		BatchPlan batchPlan = randomBatchPlan();

		String json1 = objectMapper.writeValueAsString(batchPlan);
		String json2 = BatchPlanSerDes.toJSON(batchPlan);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		BatchPlan batchPlan = randomBatchPlan();

		batchPlan.setExternalType(regex);
		batchPlan.setExternalURL(regex);
		batchPlan.setInternalClassName(regex);
		batchPlan.setName(regex);

		String json = BatchPlanSerDes.toJSON(batchPlan);

		Assert.assertFalse(json.contains(regex));

		batchPlan = BatchPlanSerDes.toDTO(json);

		Assert.assertEquals(regex, batchPlan.getExternalType());
		Assert.assertEquals(regex, batchPlan.getExternalURL());
		Assert.assertEquals(regex, batchPlan.getInternalClassName());
		Assert.assertEquals(regex, batchPlan.getName());
	}

	@Test
	public void testGetBatchPlansPage() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetBatchPlansPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"batchPlans",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject batchPlansJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/batchPlans");

		Assert.assertEquals(0, batchPlansJSONObject.get("totalCount"));

		BatchPlan batchPlan1 = testGraphQLBatchPlan_addBatchPlan();
		BatchPlan batchPlan2 = testGraphQLBatchPlan_addBatchPlan();

		batchPlansJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/batchPlans");

		Assert.assertEquals(2, batchPlansJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(batchPlan1, batchPlan2),
			Arrays.asList(
				BatchPlanSerDes.toDTOs(
					batchPlansJSONObject.getString("items"))));
	}

	@Test
	public void testPostBatchPlan() throws Exception {
		BatchPlan randomBatchPlan = randomBatchPlan();

		BatchPlan postBatchPlan = testPostBatchPlan_addBatchPlan(
			randomBatchPlan);

		assertEquals(randomBatchPlan, postBatchPlan);
		assertValid(postBatchPlan);
	}

	protected BatchPlan testPostBatchPlan_addBatchPlan(BatchPlan batchPlan)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteBatchPlan() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		BatchPlan batchPlan = testDeleteBatchPlan_addBatchPlan();

		assertHttpResponseStatusCode(
			204,
			batchPlanResource.deleteBatchPlanHttpResponse(batchPlan.getId()));

		assertHttpResponseStatusCode(
			404, batchPlanResource.getBatchPlanHttpResponse(batchPlan.getId()));

		assertHttpResponseStatusCode(
			404, batchPlanResource.getBatchPlanHttpResponse(batchPlan.getId()));
	}

	protected BatchPlan testDeleteBatchPlan_addBatchPlan() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteBatchPlan() throws Exception {
		BatchPlan batchPlan = testGraphQLBatchPlan_addBatchPlan();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteBatchPlan",
						new HashMap<String, Object>() {
							{
								put("id", batchPlan.getId());
							}
						})),
				"JSONObject/data", "Object/deleteBatchPlan"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"batchPlan",
					new HashMap<String, Object>() {
						{
							put("id", batchPlan.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetBatchPlan() throws Exception {
		BatchPlan postBatchPlan = testGetBatchPlan_addBatchPlan();

		BatchPlan getBatchPlan = batchPlanResource.getBatchPlan(
			postBatchPlan.getId());

		assertEquals(postBatchPlan, getBatchPlan);
		assertValid(getBatchPlan);
	}

	protected BatchPlan testGetBatchPlan_addBatchPlan() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetBatchPlan() throws Exception {
		BatchPlan batchPlan = testGraphQLBatchPlan_addBatchPlan();

		Assert.assertTrue(
			equals(
				batchPlan,
				BatchPlanSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"batchPlan",
								new HashMap<String, Object>() {
									{
										put("id", batchPlan.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/batchPlan"))));
	}

	@Test
	public void testGraphQLGetBatchPlanNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"batchPlan",
						new HashMap<String, Object>() {
							{
								put("id", irrelevantId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchBatchPlan() throws Exception {
		Assert.assertTrue(false);
	}

	protected BatchPlan testGraphQLBatchPlan_addBatchPlan() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(BatchPlan batchPlan1, BatchPlan batchPlan2) {
		Assert.assertTrue(
			batchPlan1 + " does not equal " + batchPlan2,
			equals(batchPlan1, batchPlan2));
	}

	protected void assertEquals(
		List<BatchPlan> batchPlans1, List<BatchPlan> batchPlans2) {

		Assert.assertEquals(batchPlans1.size(), batchPlans2.size());

		for (int i = 0; i < batchPlans1.size(); i++) {
			BatchPlan batchPlan1 = batchPlans1.get(i);
			BatchPlan batchPlan2 = batchPlans2.get(i);

			assertEquals(batchPlan1, batchPlan2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<BatchPlan> batchPlans1, List<BatchPlan> batchPlans2) {

		Assert.assertEquals(batchPlans1.size(), batchPlans2.size());

		for (BatchPlan batchPlan1 : batchPlans1) {
			boolean contains = false;

			for (BatchPlan batchPlan2 : batchPlans2) {
				if (equals(batchPlan1, batchPlan2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				batchPlans2 + " does not contain " + batchPlan1, contains);
		}
	}

	protected void assertValid(BatchPlan batchPlan) throws Exception {
		boolean valid = true;

		if (batchPlan.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (batchPlan.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"batchPlanMappings", additionalAssertFieldName)) {

				if (batchPlan.getBatchPlanMappings() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("batchPlanPolicy", additionalAssertFieldName)) {
				if (batchPlan.getBatchPlanPolicy() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("export", additionalAssertFieldName)) {
				if (batchPlan.getExport() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("externalType", additionalAssertFieldName)) {
				if (batchPlan.getExternalType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("externalURL", additionalAssertFieldName)) {
				if (batchPlan.getExternalURL() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"internalClassName", additionalAssertFieldName)) {

				if (batchPlan.getInternalClassName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (batchPlan.getName() == null) {
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

	protected void assertValid(Page<BatchPlan> page) {
		boolean valid = false;

		java.util.Collection<BatchPlan> batchPlans = page.getItems();

		int size = batchPlans.size();

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
					com.liferay.headless.admin.batch.planner.dto.v1_0.BatchPlan.
						class)) {

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

	protected boolean equals(BatchPlan batchPlan1, BatchPlan batchPlan2) {
		if (batchPlan1 == batchPlan2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						batchPlan1.getActive(), batchPlan2.getActive())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"batchPlanMappings", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						batchPlan1.getBatchPlanMappings(),
						batchPlan2.getBatchPlanMappings())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("batchPlanPolicy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						batchPlan1.getBatchPlanPolicy(),
						batchPlan2.getBatchPlanPolicy())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("export", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						batchPlan1.getExport(), batchPlan2.getExport())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("externalType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						batchPlan1.getExternalType(),
						batchPlan2.getExternalType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("externalURL", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						batchPlan1.getExternalURL(),
						batchPlan2.getExternalURL())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						batchPlan1.getId(), batchPlan2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"internalClassName", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						batchPlan1.getInternalClassName(),
						batchPlan2.getInternalClassName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						batchPlan1.getName(), batchPlan2.getName())) {

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

		if (!(_batchPlanResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_batchPlanResource;

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
		EntityField entityField, String operator, BatchPlan batchPlan) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("active")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("batchPlanMappings")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("batchPlanPolicy")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("export")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("externalType")) {
			sb.append("'");
			sb.append(String.valueOf(batchPlan.getExternalType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("externalURL")) {
			sb.append("'");
			sb.append(String.valueOf(batchPlan.getExternalURL()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("internalClassName")) {
			sb.append("'");
			sb.append(String.valueOf(batchPlan.getInternalClassName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(batchPlan.getName()));
			sb.append("'");

			return sb.toString();
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

	protected BatchPlan randomBatchPlan() throws Exception {
		return new BatchPlan() {
			{
				active = RandomTestUtil.randomBoolean();
				export = RandomTestUtil.randomBoolean();
				externalType = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalURL = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				internalClassName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected BatchPlan randomIrrelevantBatchPlan() throws Exception {
		BatchPlan randomIrrelevantBatchPlan = randomBatchPlan();

		return randomIrrelevantBatchPlan;
	}

	protected BatchPlan randomPatchBatchPlan() throws Exception {
		return randomBatchPlan();
	}

	protected BatchPlanResource batchPlanResource;
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
		BaseBatchPlanResourceTestCase.class);

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
		com.liferay.headless.admin.batch.planner.resource.v1_0.BatchPlanResource
			_batchPlanResource;

}