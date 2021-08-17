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

package com.liferay.batch.planner.web.internal.portlet.action;

import com.liferay.batch.planner.constants.BatchPlannerPortletKeys;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerMappingService;
import com.liferay.batch.planner.service.BatchPlannerPlanService;
import com.liferay.batch.planner.service.BatchPlannerPolicyService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BatchPlannerPortletKeys.BATCH_PLANNER,
		"mvc.command.name=/batch_planner/add_batch_planner_plan"
	},
	service = MVCActionCommand.class
)
public class AddBatchPlannerPlanMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Enumeration<String> enumeration = actionRequest.getParameterNames();

		while (enumeration.hasMoreElements()) {
			String attributeName = enumeration.nextElement();

			if (attributeName.startsWith("value")) {
				_log.error(
					"I see attribute value: " +
						actionRequest.getParameter(attributeName));
			}
		}

		String name = ParamUtil.getString(actionRequest, "name");
		boolean export = ParamUtil.getBoolean(actionRequest, "export");
		String externalType = ParamUtil.getString(
			actionRequest, "externalType");
		String externalURL = ParamUtil.getString(actionRequest, "externalURL");
		String internalClassName = ParamUtil.getString(
			actionRequest, "internalClassName");

		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanService.addBatchPlannerPlan(
				export, externalType, externalURL, internalClassName, name);

		List<PolicyDTO> policyDTOS = _getPolicyDTOs(actionRequest);

		for (PolicyDTO policyDTO : policyDTOS) {
			_batchPlannerPolicyService.addBatchPlannerPolicy(
				batchPlannerPlan.getBatchPlannerPlanId(), policyDTO._policyName,
				policyDTO._policyValue);
		}

		List<MappingDTO> mappingDTOS = _getMappingDTOs(actionRequest);

		for (MappingDTO mappingDTO : mappingDTOS) {
			_batchPlannerMappingService.addBatchPlannerMapping(
				batchPlannerPlan.getBatchPlannerPlanId(),
				mappingDTO._externalFieldName, "String",
				mappingDTO._internalFiledName, "String", StringPool.BLANK);
		}
	}

	private List<MappingDTO> _getMappingDTOs(ActionRequest actionRequest) {
		Enumeration<String> enumeration = actionRequest.getParameterNames();

		List<MappingDTO> mappingDTOS = new ArrayList<>();

		while (enumeration.hasMoreElements()) {
			String externalFieldNameParameter = enumeration.nextElement();

			if (!externalFieldNameParameter.startsWith("externalFieldName_")) {
				continue;
			}

			String reminder = _getReminder(
				"externalFieldName_", externalFieldNameParameter);

			MappingDTO mappingDTO = new MappingDTO();

			mappingDTO._externalFieldName = ParamUtil.getString(
				actionRequest, externalFieldNameParameter);
			mappingDTO._internalFiledName = ParamUtil.getString(
				actionRequest, "internalFieldName_" + reminder);

			mappingDTOS.add(mappingDTO);
		}

		return mappingDTOS;
	}

	private List<PolicyDTO> _getPolicyDTOs(ActionRequest actionRequest) {
		Enumeration<String> enumeration = actionRequest.getParameterNames();

		List<PolicyDTO> policyDTOs = new ArrayList<>();

		while (enumeration.hasMoreElements()) {
			String policyNameParameter = enumeration.nextElement();

			if (!policyNameParameter.startsWith("policyName_")) {
				continue;
			}

			String reminder = _getReminder("policyName_", policyNameParameter);

			PolicyDTO mappingDTO = new PolicyDTO();

			mappingDTO._policyName = ParamUtil.getString(
				actionRequest, policyNameParameter);
			mappingDTO._policyValue = ParamUtil.getString(
				actionRequest, "policyValue_" + reminder);

			policyDTOs.add(mappingDTO);
		}

		return policyDTOs;
	}

	private String _getReminder(String prefix, String value) {
		return value.substring(prefix.length());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddBatchPlannerPlanMVCActionCommand.class);

	@Reference
	private BatchPlannerMappingService _batchPlannerMappingService;

	@Reference
	private BatchPlannerPlanService _batchPlannerPlanService;

	@Reference
	private BatchPlannerPolicyService _batchPlannerPolicyService;

	private static class MappingDTO {

		private String _externalFieldName;
		private String _internalFiledName;

	}

	private static class PolicyDTO {

		private String _policyName;
		private String _policyValue;

	}

}