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

package com.liferay.headless.admin.batch.planner.internal.resource.v1_0;

import com.liferay.headless.admin.batch.planner.dto.v1_0.BatchPlan;
import com.liferay.headless.admin.batch.planner.resource.v1_0.BatchPlanResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Matija Petanjek
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseBatchPlanResourceImpl implements BatchPlanResource {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-batch-planner/v1.0/batch-plans'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the batch plans.")
	@Override
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/batch-plans")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BatchPlan")})
	public Page<BatchPlan> getBatchPlansPage(@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-batch-planner/v1.0/batch-plans' -d $'{"active": ___, "batchPlanMappings": ___, "batchPlanPolicy": ___, "export": ___, "externalType": ___, "externalURL": ___, "id": ___, "internalClassName": ___, "name": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Creates a new batch plan.")
	@Override
	@Parameters(
		value = {@Parameter(in = ParameterIn.QUERY, name = "fieldNameMapping")}
	)
	@Path("/batch-plans")
	@POST
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BatchPlan")})
	public BatchPlan postBatchPlan(
			@Parameter(hidden = true) @QueryParam("fieldNameMapping") String
				fieldNameMapping,
			BatchPlan batchPlan)
		throws Exception {

		return new BatchPlan();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-batch-planner/v1.0/batch-plans/{id}'  -u 'test@liferay.com:test'
	 */
	@DELETE
	@Operation(description = "Deletes the batch plan.")
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "id")})
	@Path("/batch-plans/{id}")
	@Tags(value = {@Tag(name = "BatchPlan")})
	public Response deleteBatchPlan(
			@NotNull @Parameter(hidden = true) @PathParam("id") Long id)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-batch-planner/v1.0/batch-plans/{id}'  -u 'test@liferay.com:test'
	 */
	@GET
	@Operation(description = "Retrieves the batch plan.")
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "id")})
	@Path("/batch-plans/{id}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "BatchPlan")})
	public BatchPlan getBatchPlan(
			@NotNull @Parameter(hidden = true) @PathParam("id") Long id)
		throws Exception {

		return new BatchPlan();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-admin-batch-planner/v1.0/batch-plans/{id}' -d $'{"active": ___, "batchPlanMappings": ___, "batchPlanPolicy": ___, "export": ___, "externalType": ___, "externalURL": ___, "id": ___, "internalClassName": ___, "name": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes({"application/json", "application/xml"})
	@Operation(description = "Updates the batch plan.")
	@Override
	@Parameters(value = {@Parameter(in = ParameterIn.PATH, name = "id")})
	@PATCH
	@Path("/batch-plans/{id}")
	@Tags(value = {@Tag(name = "BatchPlan")})
	public Response patchBatchPlan(
			@NotNull @Parameter(hidden = true) @PathParam("id") Long id,
			BatchPlan batchPlan)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(
		com.liferay.portal.kernel.model.Company contextCompany) {

		this.contextCompany = contextCompany;
	}

	public void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {

		this.contextHttpServletRequest = contextHttpServletRequest;
	}

	public void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {

		this.contextHttpServletResponse = contextHttpServletResponse;
	}

	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
	}

	public void setContextUser(
		com.liferay.portal.kernel.model.User contextUser) {

		this.contextUser = contextUser;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public void setRoleLocalService(RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	protected Map<String, String> addAction(
		String actionName, GroupedModel groupedModel, String methodName) {

		return ActionUtil.addAction(
			actionName, getClass(), groupedModel, methodName,
			contextScopeChecker, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName, Long ownerId,
		String permissionName, Long siteId) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			ownerId, permissionName, siteId, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName,
		ModelResourcePermission modelResourcePermission) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			modelResourcePermission, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, String methodName, String permissionName,
		Long siteId) {

		return addAction(
			actionName, siteId, methodName, null, permissionName, siteId);
	}

	protected <T, R> List<R> transform(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	protected AcceptLanguage contextAcceptLanguage;
	protected com.liferay.portal.kernel.model.Company contextCompany;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected Object contextScopeChecker;
	protected UriInfo contextUriInfo;
	protected com.liferay.portal.kernel.model.User contextUser;
	protected GroupLocalService groupLocalService;
	protected ResourceActionLocalService resourceActionLocalService;
	protected ResourcePermissionLocalService resourcePermissionLocalService;
	protected RoleLocalService roleLocalService;

}