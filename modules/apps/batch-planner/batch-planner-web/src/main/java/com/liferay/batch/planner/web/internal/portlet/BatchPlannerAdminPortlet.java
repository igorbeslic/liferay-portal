package com.liferay.batch.planner.web.internal.portlet;

import com.liferay.batch.planner.constants.BatchPlannerPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.SubscriptionLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=admin-notification Portlet",
		"javax.portlet.name=" + BatchPlannerPortletKeys.BATCH_PLANNER_ADMIN_IMPORT ,
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class BatchPlannerAdminPortlet  extends MVCPortlet {

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws
		IOException, PortletException {
		// pass in the service.
		renderRequest.setAttribute("subscriptionLocalService", _subscriptionLocalService);

		super.render(renderRequest, renderResponse);
	}

	@Reference(unbind = "-")
	protected void setSubscriptionLocalService(final SubscriptionLocalService subscriptionLocalService) {
		_subscriptionLocalService = subscriptionLocalService;
	}

	private SubscriptionLocalService _subscriptionLocalService;
}