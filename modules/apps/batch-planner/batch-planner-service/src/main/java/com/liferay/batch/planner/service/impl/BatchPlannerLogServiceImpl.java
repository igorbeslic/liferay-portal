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

package com.liferay.batch.planner.service.impl;

import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.service.base.BatchPlannerLogServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	property = {
		"json.web.service.context.name=batchplanner",
		"json.web.service.context.path=BatchPlannerLog"
	},
	service = AopService.class
)
public class BatchPlannerLogServiceImpl extends BatchPlannerLogServiceBaseImpl {

	@Override
	public List<BatchPlannerLog> getBatchPlannerLogs(long batchPlannerPlanId) {
		return batchPlannerLogPersistence.filterFindByBatchPlannerPlanId(
			batchPlannerPlanId);
	}

	@Override
	public List<BatchPlannerLog> getBatchPlannerLogs(
		long batchPlannerPlanId, int start, int end) {

		return batchPlannerLogPersistence.filterFindByBatchPlannerPlanId(
			batchPlannerPlanId, start, end);
	}

	public int getBatchPlannerLogsCount(long batchPlannerPlanId) {
		return batchPlannerLogPersistence.filterCountByBatchPlannerPlanId(
			batchPlannerPlanId);
	}

	@Override
	public List<BatchPlannerLog> getCompanyBatchPlannerLogs(
		long companyId, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator) {

		return batchPlannerLogPersistence.filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCompanyBatchPlannerLogsCount(long companyId) {
		return batchPlannerLogPersistence.filterCountByCompanyId(companyId);
	}

}