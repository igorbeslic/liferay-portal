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
package com.liferay.blogs.web.internal.notifications;

import com.liferay.batch.planner.constants.BatchPlannerNotificationConstants;
import com.liferay.batch.planner.constants.BatchPlannerPortletKeys;
import com.liferay.batch.planner.constants.BatchPlannerConstants;
import com.liferay.batch.planner.web.internal.notifications.BatchPlannerImportSubscriptionSender;
import com.liferay.batch.planner.web.internal.portlet.BatchPlannerAdminPortlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GroupSubscriptionCheckSubscriptionSender;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import org.osgi.service.component.annotations.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

/**
 * class BatchPlannerImportNotificationEventSender: This is the admin login notification
 * sender that implements the import completion event sender.
 *
 * @author Joe Duffy
 */

public class BatchPlannerImportNotificationEventSender  {

	public void notifySubscribers(long userId, String fromHost, long companyId, ServiceContext serviceContext)
		throws PortalException {

		// hard code stuff ...
		//  later fix this to source from configuration.

		String entryTitle = "Batch Import Completion";

		String fromName = PropsUtil.get(BatchPlannerConstants.EMAIL_FROM_NAME);
		String fromAddress = GetterUtil.getString(PropsUtil.get(BatchPlannerConstants.EMAIL_FROM_ADDRESS), PropsUtil.get(PropsKeys.ADMIN_EMAIL_FROM_ADDRESS));

		LocalizedValuesMap subjectLocalizedValuesMap = new LocalizedValuesMap();
		LocalizedValuesMap bodyLocalizedValuesMap = new LocalizedValuesMap();

		subjectLocalizedValuesMap.put(Locale.ENGLISH, "Batch Import Completion");
		bodyLocalizedValuesMap.put(Locale.ENGLISH, "Batch Import Has Completed.");

		BatchPlannerImportSubscriptionSender subscriptionSender =
			new BatchPlannerImportSubscriptionSender();

		subscriptionSender.setFromHost(fromHost);

		subscriptionSender.setClassPK(0);
		subscriptionSender.setClassName(BatchPlannerAdminPortlet.class.getName());
		subscriptionSender.setCompanyId(companyId);

		subscriptionSender.setCurrentUserId(userId);
		subscriptionSender.setEntryTitle(entryTitle);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);

		subscriptionSender.setMailId("xxxTO_BE_GIVEN_VALUExxx", 0);

		int notificationType = BatchPlannerNotificationConstants.BATCH_IMPORT_COMPLETION;

		subscriptionSender.setNotificationType(notificationType);

		String portletId = BatchPlannerPortletKeys.BATCH_PLANNER_ADMIN_IMPORT;

		subscriptionSender.setPortletId(portletId);

		subscriptionSender.setReplyToAddress(fromAddress);
		subscriptionSender.setServiceContext(serviceContext);

		subscriptionSender.addPersistedSubscribers(BatchPlannerAdminPortlet.class.getName(), 0);

		subscriptionSender.flushNotificationsAsync();
	}

}


