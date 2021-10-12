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

package com.liferay.batch.planner.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Igor Beslic
 * @generated
 */
@ProviderType
public interface BatchPlannerLogFinder {

	public int countByC_E(long companyId, boolean export);

	public int filterCountByC_E(long companyId, boolean export);

	public java.util.List<com.liferay.batch.planner.model.BatchPlannerLog>
		filterFindByC_E(
			long companyId, boolean export, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.batch.planner.model.BatchPlannerLog>
					orderByComparator);

	public java.util.List<com.liferay.batch.planner.model.BatchPlannerLog>
		findByC_E(
			long companyId, boolean export, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.batch.planner.model.BatchPlannerLog>
					orderByComparator);

}