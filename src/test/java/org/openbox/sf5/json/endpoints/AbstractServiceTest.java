package org.openbox.sf5.json.endpoints;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.openbox.sf5.json.service.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider;

public abstract class AbstractServiceTest {
	public static final String appLocation = "http://localhost:8080/SF5Spring-test/";

	// public static final String jsonPath = "json";

	// it doesn't work in abstract class
	// @Value("${jaxrs.path}")
	public static String jsonPath;

	@Autowired
	public CustomObjectMapper mapper;

	public Client client;

	// public ObjectMapper mapper = new ObjectMapper();

	public WebTarget commonTarget;

	public WebTarget serviceTarget;

	public String testUsername = "ITUser";

	public String testUserPassword = "Test123";

	public Client createAdminClient() {

		// https://jersey.java.net/documentation/latest/user-guide.html#d0e5127
		// adding universal, digest and non-preemptive authentication.
		HttpAuthenticationFeature authenticationFeature = HttpAuthenticationFeature.digest("admin", "1");

		return ClientBuilder.newBuilder().register(JacksonJaxbJsonProvider.class)

				.register(JacksonJaxbXMLProvider.class)

				.register(JacksonFeature.class).register(MultiPartFeature.class).register(authenticationFeature)

				// .register(new LoggingFilter())

				.build();
	}

	public Client createTestUserClient() {
		HttpAuthenticationFeature authenticationFeature = HttpAuthenticationFeature.digest(this.testUsername,
				this.testUserPassword);

		return ClientBuilder.newBuilder().register(JacksonJaxbJsonProvider.class).register(JacksonFeature.class)

				.register(JacksonJaxbXMLProvider.class)

				.register(MultiPartFeature.class).register(authenticationFeature)

				// .register(new LoggingFilter())

				.build();
	}

	private void readJAXRSPath() {
		Properties property = new Properties();

		try (InputStream in = getClass().getResourceAsStream("/application.properties")) {
			property.load(in);
			AbstractServiceTest.jsonPath = property.getProperty("jaxrs.path");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void setUpAbstractTestUser() {
		client = createTestUserClient();
		readJAXRSPath();
		commonTarget = client.target(appLocation).path(jsonPath);
	}

	public void setUpAbstractAdmin() {
		client = createAdminClient();
		readJAXRSPath();
		commonTarget = client.target(appLocation).path(jsonPath);
	}

	// public void configureMapper() {
	// mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
	// mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING,
	// true);
	// mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
	// mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
	// false);
	// }

}
