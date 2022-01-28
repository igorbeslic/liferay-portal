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

import com.liferay.batch.planner.constants.BatchPlannerConstants;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.SubscriptionSender;

public class BatchPlannerImportSubscriptionSender  extends SubscriptionSender {

	private static final long serialVersionUID = -7152698157653361441L;
	//from Dave Nebingers example - may have to revise

	protected void populateNotificationEventJSONObject(
		JSONObject notificationEventJSONObject) {

		super.populateNotificationEventJSONObject(notificationEventJSONObject);

		notificationEventJSONObject.put(BatchPlannerConstants.FROM_HOST, _fromHost);
	}

	@Override
	protected boolean hasPermission(Subscription subscription, String className, long classPK, User user) throws Exception {
		return true;
	}

	@Override
	protected boolean hasPermission(Subscription subscription, User user) throws Exception {
		return true;
	}

	@Override
	protected void sendNotification(User user) throws Exception {
		// remove the super classes filtering of not notifying user who is self.
		// makes sense in most cases, but we want a notification of admin login so
		// we know when never any admin logs in from anywhere at any time.

		// will be a pain if we get notified because of our own login, but we want to
		// know if some hacker gets our admin credentials and logs in and it's not really us.

		sendEmailNotification(user);
		sendUserNotification(user);
	}

	public void setFromHost(String fromHost) {
		this._fromHost = fromHost;
	}

	private String _fromHost;
}
