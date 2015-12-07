package org.openbox.sf5.json.endpoints;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public abstract class AbstractServiceTest {
	public static final String appLocation = "http://localhost:8080/SF5Spring-test/";

	public static final String jsonPath = "json";

	public Client client;

	public ObjectMapper mapper = new ObjectMapper();

	public WebTarget commonTarget;

	public WebTarget serviceTarget;

	public Client createClient() {

		// https://jersey.java.net/documentation/latest/user-guide.html#d0e5127
		// adding universal, digest and non-preemptive authentication.
		HttpAuthenticationFeature authenticationFeature = HttpAuthenticationFeature.digest("admin", "1");

		return ClientBuilder.newBuilder().register(JacksonJaxbJsonProvider.class).register(JacksonFeature.class)
				.register(MultiPartFeature.class).register(authenticationFeature)

				.register(new LoggingFilter())

				.build();
	}

	public void setUpAbstract() {
		client = createClient();
		commonTarget = client.target(appLocation).path(jsonPath);
	}

	public void configureMapper() {
		mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
		mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public void addAdminCredentials(Invocation.Builder invocationBuilder) {
		invocationBuilder.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_DIGEST_USERNAME, "admin")
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_DIGEST_PASSWORD, "1");

	}

	public void addAdminCredentials(WebTarget target) {
		target.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_DIGEST_USERNAME, "admin")
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_DIGEST_PASSWORD, "1");
	}
}
