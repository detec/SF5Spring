package org.openbox.sf5.json.endpoints;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openbox.sf5.model.Settings;
import org.openbox.sf5.model.Users;

@RunWith(JUnit4.class)
public class SettingsServiceIT extends AbstractServiceTest {

	private static final String servicePath = "usersettings";

	private Users adminUser;

	@Before
	public void setUp() {
		setUpAbstract();
		serviceTarget = commonTarget.path(servicePath);

		// here we should create a setting.

		// check that admin user exists
		Invocation.Builder invocationBuilder = commonTarget.path("users").path("filter").path("username").path("admin")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.get();

		// if (response.getStatus() == Status.OK.getStatusCode()) {
		// adminUser = response.readEntity(Users.class);
		// }
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		adminUser = response.readEntity(Users.class);

		Settings setting = new Settings();
		setting.setName("Simple");
		setting.setUser(adminUser);

		// http://howtodoinjava.com/2015/08/07/jersey-restful-client-examples/#post
		invocationBuilder = serviceTarget.path("create").request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		addAdminCredentials(invocationBuilder);

		response = invocationBuilder.post(Entity.entity(setting, MediaType.APPLICATION_JSON));

		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldGetSettings() {
		assertThat(adminUser).isNotNull();

		WebTarget target = null;
		Response response = null;
		Client client = createClient();

		List<Settings> settList = getUserSettings(client);
		if (settList.size() == 0) {
			return;
		}

		Settings sett = settList.get(0);

		// target = client.target(appLocation + "usersettings/filter/id/" +
		// Long.toString(sett.getId()) + ";login=admin");

		target = client.target(appLocation).path(jsonPath).path(servicePath).path("filter").path("id")
				.path(Long.toString(sett.getId())).matrixParam("login", "admin");

		response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	// getting all user settings with authentication
	private List<Settings> getUserSettings(Client client) {

		assertThat(adminUser).isNotNull();

		List<Settings> settList = new ArrayList<Settings>();

		// Let's check, if there is user with login admin
		// WebTarget target = client.target(appLocation +
		// "users/filter/login/admin");

		// WebTarget target =
		// client.target(appLocation).path(jsonPath).path("users").path("filter").path("username")
		// .path("admin");

		Invocation.Builder invocationBuilder = commonTarget.path("users").path("filter").path("username").path("admin")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.get();

		if (response.getStatus() == (Status.NOT_FOUND.getStatusCode())) {
			return settList; // no user with login admin
		}

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		GenericType<List<Settings>> genList = new GenericType<List<Settings>>() {
		};

		WebTarget target = client.target(appLocation).path(jsonPath).path(servicePath).path("all");
		addAdminCredentials(target);

		// setting credentials
		// invocationBuilder =
		// serviceTarget.path("all").request(MediaType.APPLICATION_JSON)
		// .accept(MediaType.APPLICATION_JSON);
		//
		// addAdminCredentials(invocationBuilder);
		//
		// response = invocationBuilder.get();

		// response =
		// target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		// response =
		// target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(genList);

		// http://stackoverflow.com/questions/27643822/marshal-un-marshal-list-objects-in-jersey-jax-rs-using-jaxb
		// Jersey 2

		// List<Book> books =
		// client.target(REST_SERVICE_URL).request().get(bookType);

		// settList = response.readEntity(genList);
		// settList =
		// target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(genList);

		response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertThat(settList).isNotNull();
		assertThat(settList.size()).isGreaterThan(0);
		return settList;

	}

	@Test
	public void shouldGetSettingsByArbitraryFilter() {
		WebTarget target = null;
		Response response = null;
		// Client client = createClient();

		// here we should check, if such user exists and find only his settings.
		List<Settings> settList = getUserSettings(client);
		if (settList == null || settList.size() == 0) {
			return;
		}

		Settings sett = settList.get(0);

		// target = client.target(appLocation + "usersettings/filter/Name/" +
		// sett.getName() + ";login=admin");
		target = client.target(appLocation).path(jsonPath).path(servicePath).path("filter").path("Name")
				.path(sett.getName()).matrixParam("login", "admin");

		response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

	}

	@Override
	public Client createClient() {
		HttpAuthenticationFeature authenticationFeature = HttpAuthenticationFeature.universalBuilder().build();

		return ClientBuilder.newBuilder().register(JacksonFeature.class).register(authenticationFeature).build();
	}

}
