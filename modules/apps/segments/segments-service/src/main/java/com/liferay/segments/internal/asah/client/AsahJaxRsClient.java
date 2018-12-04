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

package com.liferay.segments.internal.asah.client;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(immediate = true, service = AsahJaxRsClient.class)
public class AsahJaxRsClient {

	@Activate
	protected void activate() {
		_client = _clientBuilder.build();

		_client.register(JacksonJsonProvider.class);

		String json = _getRoot();

		System.out.println("AsahJaxRsClient: " + json);

		if (_log.isInfoEnabled()) {
			_log.info(json);
		}
	}

	private String _get(
		String url, Map<String, Object> parameters,
		Map<String, String> headers) {

		WebTarget target = _client.target(
			"https://osbasahfarobackend-asah93fdaf9914e34506bf664b9ab652fc01." +
				"eu-west-1.lfr.cloud");

		target = target.path(url);

		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			target = target.queryParam(entry.getKey(), entry.getValue());
		}

		Invocation.Builder builder = target.request(
			MediaType.APPLICATION_JSON_TYPE);

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			builder.header(entry.getKey(), entry.getValue());
		}

		Response response = builder.get();

		return response.readEntity(String.class);
	}

	private String _getRoot() {
		try {
			return _get(
				StringPool.SLASH, Collections.emptyMap(),
				Collections.emptyMap());
		}
		catch (Exception e) {
			_log.error(e, e);

			return "ERROR: " + e.getMessage();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AsahJaxRsClient.class);

	private Client _client;

	@Reference
	private ClientBuilder _clientBuilder;

}