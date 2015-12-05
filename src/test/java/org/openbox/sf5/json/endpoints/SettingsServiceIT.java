package org.openbox.sf5.json.endpoints;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.openbox.sf5.model.Settings;
import org.openbox.sf5.model.Users;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SettingsServiceIT extends AbstractServiceTest {

	private static final String servicePath = "usersettings";

	private Users getAdminUser() {

		// check that admin user exists
		Invocation.Builder invocationBuilder = commonTarget.path("users").path("filter").path("username").path("admin")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.get();

		// if (response.getStatus() == Status.OK.getStatusCode()) {
		// adminUser = response.readEntity(Users.class);
		// }
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		Users adminUser = response.readEntity(Users.class);

		return adminUser;
	}

	@Before
	public void setUp() {
		setUpAbstract();

		serviceTarget = commonTarget.path(servicePath);
	}

	@Test
	public void shouldCreateSetting() {

		serviceTarget = commonTarget.path(servicePath);

		// here we should create a setting.
		Users adminUser = getAdminUser();

		assertThat(adminUser).isNotNull();

		Settings setting = new Settings();
		setting.setName("Simple");
		setting.setUser(adminUser);

		// //
		// http://howtodoinjava.com/2015/08/07/jersey-restful-client-examples/#post
		Invocation.Builder invocationBuilder = serviceTarget.path("create").request(MediaType.APPLICATION_JSON);
		// addAdminCredentials(invocationBuilder);

		Response responsePost = invocationBuilder.post(Entity.entity(setting, MediaType.APPLICATION_JSON));
		// System.out.println(responsePost.toString());
		assertEquals(Status.CREATED.getStatusCode(), responsePost.getStatus());

		// RestTemplate restTemplate = new RestTemplate();
		//
		// URI uri =
		// restTemplate.postForLocation(serviceTarget.getUri().toString() +
		// "/create", setting, Settings.class);
		// assertThat(uri.toASCIIString()).isNotEqualTo("http://localhost:8080/SF5Spring-test/login?time=1");
		// System.out.println("Location : " + uri.toASCIIString());

	}

	@Test
	public void shouldGetSettingById() {

		Users adminUser = getAdminUser();
		assertThat(adminUser).isNotNull();

		// WebTarget target = null;
		// Response response = null;
		// Client client = createClient();

		List<Settings> settList = getUserSettings(client);
		if (settList.size() == 0) {
			return;
		}

		Settings sett = settList.get(0);

		// target = client.target(appLocation + "usersettings/filter/id/" +
		// Long.toString(sett.getId()) + ";login=admin");

		// target =
		// client.target(appLocation).path(jsonPath).path(servicePath).path("filter").path("id")
		// .path(Long.toString(sett.getId())).matrixParam("login", "admin");
		//
		// response =
		// target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("id").path(Long.toString(sett.getId()))
				.request(MediaType.APPLICATION_JSON);
		// addAdminCredentials(invocationBuilder);

		Response response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	// getting all user settings with authentication
	private List<Settings> getUserSettings(Client client) {
		Users adminUser = getAdminUser();
		assertThat(adminUser).isNotNull();

		List<Settings> settList = new ArrayList<Settings>();

		// Let's check, if there is user with login admin
		// WebTarget target = client.target(appLocation +
		// "users/filter/login/admin");

		// WebTarget target =
		// client.target(appLocation).path(jsonPath).path("users").path("filter").path("username")
		// .path("admin");

		// We have already checked that there is admin user.
		// Invocation.Builder invocationBuilder =
		// commonTarget.path("users").path("filter").path("username").path("admin")
		// .request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		//
		// Response response = invocationBuilder.get();
		//
		// if (response.getStatus() == (Status.NOT_FOUND.getStatusCode())) {
		// return settList; // no user with login admin
		// }

		// assertEquals(Status.OK.getStatusCode(), response.getStatus());

		GenericType<List<Settings>> genList = new GenericType<List<Settings>>() {
		};

		WebTarget target = serviceTarget.path("all");

		settList = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(genList);

		assertThat(settList).isNotNull();
		assertThat(settList.size()).isGreaterThan(0);
		return settList;

		// // getting settings by userlogin
		// target = client.target(appLocation +
		// "usersettings/filter/login/admin");
		//
		// response =
		// target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		// assertEquals(Status.OK.getStatusCode(), response.getStatus());
		//
		// //
		// http://stackoverflow.com/questions/27643822/marshal-un-marshal-list-objects-in-jersey-jax-rs-using-jaxb
		// // Jersey 2
		//
		// settList =
		// target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(genList);
		//
		// assertThat(settList).isNotNull();
		// return settList;
	}

	@Test
	public void shouldGetSettingsByArbitraryFilter() {

		// WebTarget target = null;
		// Response response = null;
		// Client client = createClient();

		// here we should check, if such user exists and find only his settings.
		List<Settings> settList = getUserSettings(client);
		if (settList == null || settList.size() == 0) {
			return;
		}

		Settings sett = settList.get(0);

		// target = client.target(appLocation + "usersettings/filter/Name/" +
		// sett.getName() + ";login=admin");
		// target =
		// client.target(appLocation).path(jsonPath).path(servicePath).path("filter").path("Name")
		// .path(sett.getName());
		// addAdminCredentials(target);
		//
		// response =
		// target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("Name").path(sett.getName())
				.request(MediaType.APPLICATION_JSON);
		addAdminCredentials(invocationBuilder);

		Response response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

	}

	// @Override
	// public Client createClient() {
	// HttpAuthenticationFeature authenticationFeature =
	// HttpAuthenticationFeature.universalBuilder().build();
	//
	// return
	// ClientBuilder.newBuilder().register(JacksonFeature.class).register(authenticationFeature).build();
	// }

}
