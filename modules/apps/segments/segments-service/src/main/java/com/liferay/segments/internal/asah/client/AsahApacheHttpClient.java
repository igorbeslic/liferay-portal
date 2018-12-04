package com.liferay.segments.internal.asah.client;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author David Arques
 */
@Component(immediate = true, service = AsahApacheHttpClient.class)
public class AsahApacheHttpClient {

	@Activate
	protected void activate() {
		String json = _get();

		System.out.println("AsahApacheHttpClient: " + json);
	}

	private String _get() {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			HttpGet httpget = new HttpGet(
				"https://osbasahfarobackend-" +
					"asah93fdaf9914e34506bf664b9ab652fc01.eu-west-1.lfr.cloud");

			ResponseHandler<String> responseHandler =
				response -> {
					int status = response.getStatusLine().getStatusCode();

					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();

						if (entity != null) {
							return EntityUtils.toString(entity);
						}

						return null;
					}
					else {
						throw new ClientProtocolException(
							"Unexpected response status: " + status);
					}
				};

			return httpclient.execute(httpget, responseHandler);

		}catch (Exception e) {
			return "AsahApacheHttpClient: " + e.getMessage();

		} finally {
			try {
				httpclient.close();
			}
			catch (IOException ioe) {
			}
		}
	}

}