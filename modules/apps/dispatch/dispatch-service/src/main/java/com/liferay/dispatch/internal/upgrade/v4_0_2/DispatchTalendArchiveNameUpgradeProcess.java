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

package com.liferay.dispatch.internal.upgrade.v4_0_2;

import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Mahmoud Azzam
 */
public class DispatchTalendArchiveNameUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws IOException, SQLException {
		ResultSet resultSet = null;
		try (Connection connection = DataAccess.getConnection();
			 Statement statement = connection.createStatement()) {

			String sql =
					"(select companyId, dispatchTriggerId from DispatchTrigger " +
							"where dispatchTaskExecutorType = 'talend')";

			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");
				long dispatchTriggerId = resultSet.getLong("dispatchTriggerId");
				ExpandoValueLocalServiceUtil.addValue(
						companyId,
						DispatchTrigger.class.getName(),
						ExpandoTableConstants.DEFAULT_TABLE_NAME, "fileName",
						dispatchTriggerId,
						"File name cannot be retrieved");
			}
		}
		catch (PortalException e) {
			e.printStackTrace();
		}
		finally {
			resultSet.close();
		}
	}
}