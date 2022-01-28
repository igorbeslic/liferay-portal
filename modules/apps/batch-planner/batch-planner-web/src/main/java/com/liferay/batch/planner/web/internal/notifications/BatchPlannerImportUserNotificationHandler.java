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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;


import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;



/**
 * @author Joe Duffy
 */

@Component(
	immediate = true,
	property = {"javax.portlet.name=" + BatchPlannerPortletKeys.BATCH_PLANNER_ADMIN_IMPORT},
	service = UserNotificationHandler.class
)
public class BatchPlannerImportUserNotificationHandler extends BaseUserNotificationHandler {

	/**
	 * AdminLoginUserNotificationHandler: Constructor class.
	 */
	public BatchPlannerImportUserNotificationHandler() {
		setPortletId(BatchPlannerPortletKeys.BATCH_PLANNER_ADMIN_IMPORT);
	}


	@Override
	protected String getBody(UserNotificationEvent userNotificationEvent, ServiceContext serviceContext) throws Exception {
		String username = LanguageUtil.get(serviceContext.getLocale(), _UKNOWN_USER_KEY);

		// okay, we need to get the user for the event
		User user = _userLocalService.fetchUser(userNotificationEvent.getUserId());

		if (Validator.isNotNull(user)) {
			// get the company the user belongs to.
			Company company = _companyLocalService.fetchCompany(user.getCompanyId());

			// based on the company auth type, find the user name to display.
			// so we'll get screen name or email address or whatever they're using to log in.

			if (Validator.isNotNull(company)) {
				if (company.getAuthType().equals(CompanyConstants.AUTH_TYPE_EA)) {
					username = user.getEmailAddress();
				} else if (company.getAuthType().equals(CompanyConstants.AUTH_TYPE_SN)) {
					username = user.getScreenName();
				} else if (company.getAuthType().equals(CompanyConstants.AUTH_TYPE_ID)) {
					username = String.valueOf(user.getUserId());
				}
			}
		}

		// we'll be stashing the client address in the payload of the event, so let's extract it here.
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

//              String fromHost = jsonObject.getString(Constants.FROM_HOST);
		String fromHost = jsonObject.getString("fromHost");

		// fetch our strings via the language bundle.
		String title = LanguageUtil.get(serviceContext.getLocale(), _TITLE_KEY);

		String body = LanguageUtil.format(serviceContext.getLocale(), _BODY_KEY, new Object[] {username, fromHost});

		// build the html using our template.
		String html = StringUtil.replace(_BODY_TEMPLATE, _BODY_REPLACEMENTS, new String[] {title, body});

		return html;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(final UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}
	@Reference(unbind = "-")
	protected void setCompanyLocalService(final CompanyLocalService companyLocalService) {
		_companyLocalService = companyLocalService;
	}

	private UserLocalService _userLocalService;
	private CompanyLocalService _companyLocalService;

	private static final String _TITLE_KEY = "title.batch.planner";
	private static final String _BODY_KEY = "body.batch.planner";
	private static final String _UKNOWN_USER_KEY = "unknown.user";

	private static final String _BODY_TEMPLATE = "<div class=\"title\">[$TITLE$]</div><div class=\"body\">[$BODY$]</div>";
	private static final String[] _BODY_REPLACEMENTS = new String[] {"[$TITLE$]", "[$BODY$]"};

	private static final Log _log = LogFactoryUtil.getLog(BatchPlannerImportUserNotificationHandler.class);
}

