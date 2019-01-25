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

package com.liferay.rest.generator.resource;

import com.liferay.commerce.openapi.core.context.Pagination;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.rest.generator.model.CollectionDTO;
import com.liferay.rest.generator.model.ItemDTO;

import javax.annotation.Generated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

/**
 * @author Igor Beslic
 */
@Generated("OSGiRESTModuleGenerator")
@Path("/1.0.0/item")
public interface ItemResource {

	@GET
	@Produces("application/json")
	@RequiresScope("RestGenerator.read")
	public CollectionDTO<ItemDTO> getItems(
			@QueryParam("size") String size, @Context Pagination pagination)
		throws Exception;

}