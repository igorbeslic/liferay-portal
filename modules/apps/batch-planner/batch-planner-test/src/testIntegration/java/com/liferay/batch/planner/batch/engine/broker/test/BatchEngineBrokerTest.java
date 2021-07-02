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

package com.liferay.batch.planner.batch.engine.broker.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.planner.batch.engine.broker.BatchEngineBroker;
import com.liferay.batch.planner.constants.BatchPlannerPlanConstants;
import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerLogServiceUtil;
import com.liferay.batch.planner.service.BatchPlannerPlanLocalService;
import com.liferay.batch.planner.service.test.util.BatchPlannerMappingTestUtil;
import com.liferay.batch.planner.service.test.util.BatchPlannerPlanTestUtil;
import com.liferay.batch.planner.service.test.util.BatchPlannerPolicyTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import java.net.URI;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Beslic
 */
@RunWith(Arquillian.class)
public class BatchEngineBrokerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testImport() throws Exception {
		File file = _getRandomImportFile(true, 200, "description", "title");

		URI fileURI = file.toURI();

		BatchPlannerPlan batchPlannerPlan =
			BatchPlannerPlanTestUtil.addBatchPlannerPlan(
				false, BatchPlannerPlanConstants.EXTERNAL_TYPE_CSV,
				fileURI.toString(),
				"com.liferay.headless.delivery.dto.v1_0.BlogPosting");

		Assert.assertEquals(false, batchPlannerPlan.isActive());

		BatchPlannerPolicyTestUtil.addBatchPlannerPolicy(
			batchPlannerPlan.getBatchPlannerPlanId(), "delimiter",
			StringPool.SEMICOLON);
		BatchPlannerPolicyTestUtil.addBatchPlannerPolicy(
			batchPlannerPlan.getBatchPlannerPlanId(), "hasColumnHeaders",
			StringPool.TRUE);

		BatchPlannerMappingTestUtil.addBatchPlannerMapping(
			batchPlannerPlan.getBatchPlannerPlanId(), "description_external",
			"description", "title_external", "title");

		_batchEngineBroker.submit(batchPlannerPlan.getBatchPlannerPlanId());

		List<BatchPlannerLog> batchPlannerLogs =
			BatchPlannerLogServiceUtil.getBatchPlannerLogs(
				batchPlannerPlan.getBatchPlannerPlanId());

		Assert.assertEquals(
			"Batch planner logs count", 1, batchPlannerLogs.size());

		batchPlannerPlan = _batchPlannerPlanLocalService.getBatchPlannerPlan(
			batchPlannerPlan.getBatchPlannerPlanId());

		Assert.assertEquals(true, batchPlannerPlan.isActive());
	}

	private File _getRandomImportFile(
			boolean header, long lineCount, String... columns)
		throws Exception {

		File tempFile = FileUtil.createTempFile("import-", "csv");

		try (PrintWriter printWriter = new PrintWriter(
				new FileOutputStream(tempFile))) {

			if (header) {
				for (int i = 0; i < columns.length; i++) {
					printWriter.print(columns[i]);

					if ((i + 1) < columns.length) {
						printWriter.print(StringPool.SEMICOLON);
					}
				}

				printWriter.println();
			}

			for (int i = 0; i <= lineCount; i++) {
				for (int j = 0; j < columns.length; j++) {
					printWriter.print(RandomTestUtil.randomString());

					if ((i + 1) < columns.length) {
						printWriter.print(StringPool.SEMICOLON);
					}
				}
			}

			printWriter.flush();
		}

		return tempFile;
	}

	@Inject
	private BatchEngineBroker _batchEngineBroker;

	@Inject
	private BatchPlannerPlanLocalService _batchPlannerPlanLocalService;

}