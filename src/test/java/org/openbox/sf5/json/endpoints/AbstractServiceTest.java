package org.openbox.sf5.json.endpoints;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public abstract class AbstractServiceTest {
	public static final String appLocation = "http://localhost:8080/SF5Spring-test/";

	public static final String jsonPath = "json";

	public ObjectMapper mapper = new ObjectMapper();

	public Client createClient() {
		return ClientBuilder.newBuilder().register(JacksonJaxbJsonProvider.class).register(MultiPartFeature.class)
				.build();
	}

	public void configureMapper() {
		mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
		mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
}
