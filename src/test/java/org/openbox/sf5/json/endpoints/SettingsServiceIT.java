package org.openbox.sf5.json.endpoints;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.client.ClientProtocolException;
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
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		Users adminUser = response.readEntity(Users.class);

		return adminUser;
	}

	// private boolean checkThatUsernameExists(String username) {
	// Invocation.Builder invocationBuilder =
	// commonTarget.path("users").path("exists").path("username").path(username)
	// .request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
	//
	// Response response = invocationBuilder.get();
	// assertEquals(Status.OK.getStatusCode(), response.getStatus());
	// Boolean result = response.readEntity(Boolean.class);
	//
	// return result.booleanValue();
	//
	// }

	private Users getTestUser() {
		Invocation.Builder invocationBuilder = commonTarget.path("users").path("filter").path("username")
				.path(this.testUsername).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		Users testUser = response.readEntity(Users.class);

		return testUser;

	}

	@Before
	public void setUp() {
		setUpAbstractTestUser();

		serviceTarget = commonTarget.path(servicePath);
	}

	@Test
	public void shouldCreateAndGetSettingById() {

		// serviceTarget = commonTarget.path(servicePath);

		// here we should create a setting.
		Users adminUser = getTestUser();

		assertThat(adminUser).isNotNull();

		Settings setting = new Settings();
		setting.setName("Simple");
		setting.setUser(adminUser);

		// //
		// http://howtodoinjava.com/2015/08/07/jersey-restful-client-examples/#post
		Invocation.Builder invocationBuilder = serviceTarget.path("create").request(MediaType.APPLICATION_JSON);
		Response responsePost = invocationBuilder.post(Entity.entity(setting, MediaType.APPLICATION_JSON));
		assertEquals(Status.CREATED.getStatusCode(), responsePost.getStatus());

		// get setting id
		MultivaluedMap<String, String> headersMap = responsePost.getStringHeaders();
		List<String> locStringList = headersMap.get("SettingId");
		assertEquals(1, locStringList.size());

		String settingIdString = locStringList.get(0);
		long id = Long.parseLong(settingIdString);

		assertThat(id).isGreaterThan(0);
		setting.setId(id);

		// Here we test getting setting by id.
		invocationBuilder = serviceTarget.path("filter").path("id").path(Long.toString(setting.getId()))

				.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		Settings settingRead = response.readEntity(Settings.class);
		assertThat(settingRead).isNotNull();
	}

	// getting all user settings with authentication
	private List<Settings> getUserSettings(Client client) {

		List<Settings> settList = new ArrayList<Settings>();

		GenericType<List<Settings>> genList = new GenericType<List<Settings>>() {
		};

		WebTarget target = serviceTarget.path("all");

		// Response result =
		// target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		settList = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(genList);

		assertThat(settList).isNotNull();
		assertThat(settList.size()).isGreaterThan(0);
		return settList;

	}

	@Test
	public void shouldGetUserSettings() {
		getUserSettings(client);
	}

	@Test
	public void shouldGetSettingsByArbitraryFilter() throws ClientProtocolException, IOException {
		Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("Name").path("Simple")
				.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		// 09.12.2015
		GenericType<List<Settings>> genList = new GenericType<List<Settings>>() {
		};

		List<Settings> newSettingsList = response.readEntity(genList);

		assertThat(newSettingsList).isNotNull();
		assertThat(newSettingsList.size()).isGreaterThan(0);
		// 09.12.2015

		// HttpComponentsClientHttpRequestFactory factory = new
		// HttpComponentsClientHttpRequestFactory();

		// DefaultHttpClient client = (DefaultHttpClient)
		// factory.getHttpClient();
		// 3 client.getCredentialsProvider().setCredentials(
		// 4 new AuthScope(hostName, portNumber),
		// 5 new UsernamePasswordCredentials(username, password));
		// 6 RestTemplate restTemplate = new RestTemplate(factory);

		// URI url =
		// serviceTarget.path("filter").path("Name").path("Simple").getUri();
		//
		// UsernamePasswordCredentials credentials = new
		// UsernamePasswordCredentials("admin", "1");
		// AuthScope authScope = new AuthScope(new HttpHost("localhost", 8080));
		// // // credProvider = new CredentialsProvider(authScope, credentials);
		// // HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		//
		// CredentialsProvider credentialsProvider = new
		// BasicCredentialsProvider();
		// credentialsProvider.setCredentials(authScope, credentials);
		// CloseableHttpClient httpclient =
		// HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider)
		// .build();
		//
		// HttpHost target = new HttpHost("localhost", 8080, "http");
		//
		// try {
		//
		// // Create AuthCache instance
		// AuthCache authCache = new BasicAuthCache();
		// // Generate DIGEST scheme object, initialize it and add it to the
		// // local
		// // auth cache
		// DigestScheme digestAuth = new DigestScheme();
		// // Suppose we already know the realm name
		// digestAuth.overrideParamter("realm", "some realm");
		// // Suppose we already know the expected nonce value
		// digestAuth.overrideParamter("nonce", "whatever");
		// authCache.put(target, digestAuth);
		//
		// // Add AuthCache to the execution context
		// HttpClientContext localContext = HttpClientContext.create();
		// localContext.setAuthCache(authCache);
		//
		// HttpGet httpget = new HttpGet(url);
		//
		// System.out.println("Executing request " + httpget.getRequestLine() +
		// " to target " + target);
		// //for (int i = 0; i < 3; i++) {
		// CloseableHttpResponse response = httpclient.execute(target, httpget,
		// localContext);
		// HttpEntity entity = response.getEntity();
		//
		// try {
		// System.out.println("----------------------------------------");
		// System.out.println(response.getStatusLine());
		// System.out.println(EntityUtils.toString(response.getEntity()));
		// } finally {
		// response.close();
		// }
		// //}
		// } finally {
		// httpclient.close();
		// }
	}

}
