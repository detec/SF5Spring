package org.openbox.sf5.json.endpoints;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public abstract class AbstractServiceTest {
	public static final String appLocation = "http://localhost:8085/SF5Spring/";

	public static final String jsonPath = "json";

	public Client createClient() {
		return ClientBuilder.newBuilder().register(JacksonJaxbJsonProvider.class).register(MultiPartFeature.class)
				.build();
	}
}
