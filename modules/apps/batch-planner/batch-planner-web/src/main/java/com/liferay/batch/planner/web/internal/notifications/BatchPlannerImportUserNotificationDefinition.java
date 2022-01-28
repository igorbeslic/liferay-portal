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

package com.liferay.batch.planner.web.internal.notifications;




	import com.liferay.batch.planner.constants.BatchPlannerPortletKeys;
	import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
	import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
	import com.liferay.portal.kernel.notifications.UserNotificationDeliveryType;

	import org.osgi.service.component.annotations.Component;

/**
 * @Author Joe Duffy
 */
@Component(
	immediate = true, property = "javax.portlet.name=" + BatchPlannerPortletKeys.BATCH_PLANNER,
	service = UserNotificationDefinition.class
)
public class BatchPlannerImportUserNotificationDefinition
	extends UserNotificationDefinition {

	public BatchPlannerImportUserNotificationDefinition() {
		super(
			BatchPlannerPortletKeys.BATCH_PLANNER, 0,
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			"receive-a-notification-when-someone");
		// add receive-a-notification-when-batch-import-completes to lang and replace key here


		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
				"email", UserNotificationDeliveryConstants.TYPE_EMAIL, true,
				true));
		addUserNotificationDeliveryType(
			new UserNotificationDeliveryType(
				"website", UserNotificationDeliveryConstants.TYPE_WEBSITE, true,
				true));
	}

}

