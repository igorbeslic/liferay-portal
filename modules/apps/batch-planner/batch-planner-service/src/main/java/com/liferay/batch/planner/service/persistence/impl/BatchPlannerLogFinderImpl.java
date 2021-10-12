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

package com.liferay.batch.planner.service.persistence.impl;

import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.model.impl.BatchPlannerLogImpl;
import com.liferay.batch.planner.service.persistence.BatchPlannerLogFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(service = BatchPlannerLogFinder.class)
public class BatchPlannerLogFinderImpl
	extends BatchPlannerLogFinderBaseImpl implements BatchPlannerLogFinder {

	public static final String COUNT_BY_C_E =
		BatchPlannerLogFinder.class.getName() + ".countByC_E";

	public static final String FIND_BY_C_E =
		BatchPlannerLogFinder.class.getName() + ".findByC_E";

	@Override
	public int countByC_E(long companyId, boolean export) {
		return doCountByC_E(companyId, export, false);
	}

	@Override
	public int filterCountByC_E(long companyId, boolean export) {
		return doCountByC_E(companyId, export, true);
	}

	@Override
	public List<BatchPlannerLog> filterFindByC_E(
		long companyId, boolean export, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator) {

		return doFindByC_E(
			companyId, export, start, end, orderByComparator, true);
	}

	@Override
	public List<BatchPlannerLog> findByC_E(
		long companyId, boolean export, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator) {

		return doFindByC_E(
			companyId, export, start, end, orderByComparator, false);
	}

	protected int doCountByC_E(
		long companyId, boolean export, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_C_E);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, BatchPlannerPlan.class.getName(),
					"BatchPlannerLog.batchPlannerPlanId", companyId);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);
			queryPos.add(export);

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<BatchPlannerLog> doFindByC_E(
		long companyId, boolean export, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_E);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, BatchPlannerPlan.class.getName(),
					"BatchPlannerLog.batchPlannerPlanId", companyId);
			}

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				BatchPlannerLogImpl.TABLE_NAME, BatchPlannerLogImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);
			queryPos.add(export);

			return (List<BatchPlannerLog>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Reference
	private CustomSQL _customSQL;

}