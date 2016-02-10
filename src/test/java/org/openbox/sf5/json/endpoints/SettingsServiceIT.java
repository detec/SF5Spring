package org.openbox.sf5.json.endpoints;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.openbox.sf5.model.Settings;
import org.openbox.sf5.model.SettingsConversion;
import org.openbox.sf5.model.Transponders;
import org.openbox.sf5.model.Users;
import org.openbox.sf5.model.listwrappers.GenericXMLListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.fasterxml.jackson.core.type.TypeReference;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(JUnit4.class)
public class SettingsServiceIT extends AbstractServiceTest {

	private static final String servicePath = "usersettings";

	private Users getTestUser() {
		Invocation.Builder invocationBuilder = commonTarget.path("users").path("filter").path("username")
				.path(testUsername).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		Users testUser = response.readEntity(Users.class);

		return testUser;

	}

	private Users getTestUserXML() {
		Invocation.Builder invocationBuilder = commonTarget.path("users").path("filter").path("username")
				.path(testUsername).request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);

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

		Response response = null;

		// here we should create a setting.
		Users adminUser = getTestUser();

		assertThat(adminUser).isNotNull();

		// get some transponders to make lines objects.
		Invocation.Builder invocationBuilder = commonTarget.path("transponders").path("filter").path("Speed")
				.path("27500").request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		GenericType<List<Transponders>> genList = new GenericType<List<Transponders>>() {
		};

		List<Transponders> newTransList = response.readEntity(genList);

		Settings setting = new Settings();
		setting.setName("Simple");
		setting.setUser(adminUser);
		setting.setTheLastEntry(new java.sql.Timestamp(System.currentTimeMillis()));

		fillTranspondersToSetting(newTransList, setting);

		// //
		// http://howtodoinjava.com/2015/08/07/jersey-restful-client-examples/#post
		invocationBuilder = serviceTarget.path("create").request(MediaType.APPLICATION_JSON);
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

		response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		Settings settingRead = response.readEntity(Settings.class);
		assertThat(settingRead).isNotNull();
	}

	private void fillTranspondersToSetting(List<Transponders> newTransList, Settings setting) {

		// filter up to 32 transponders
		newTransList.stream().filter(t -> newTransList.indexOf(t) <= 31).forEach(t -> {
			int currentIndex = newTransList.indexOf(t);
			int currentNumber = currentIndex + 1;
			int satIndex = (int) Math.ceil((double) currentNumber / 4);
			// int tpIndex = currentNumber - (satIndex * 4);
			int tpIndex = (currentNumber % 4 == 0) ? 4 : currentNumber % 4; // %
																			// is
																			// remainder

			SettingsConversion sc = new SettingsConversion(setting, t, satIndex, tpIndex,
					Long.toString(t.getFrequency()), 0);
			sc.setLineNumber(currentNumber);
			setting.getConversion().add(sc);
		});
	}

	@Test
	public void shouldCreateAndGetSettingByIdXML() throws IOException, URISyntaxException {
		Response response = null;

		// here we should create a setting.
		Users adminUser = getTestUserXML();

		assertThat(adminUser).isNotNull();

		// get some transponders to make lines objects.
		Invocation.Builder invocationBuilder = commonTarget.path("transponders").path("filter").path("Speed")
				.path("27500").request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);
		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		GenericXMLListWrapper<Transponders> transWrapper = response.readEntity(GenericXMLListWrapper.class);

		List<Transponders> newTransList = mapper.convertValue(transWrapper.getWrappedList(),
				new TypeReference<List<Transponders>>() {
				});

		Settings setting = new Settings();
		setting.setName("Second");
		setting.setUser(adminUser);
		setting.setTheLastEntry(new java.sql.Timestamp(System.currentTimeMillis()));

		fillTranspondersToSetting(newTransList, setting);

		invocationBuilder = serviceTarget.path("create").request(MediaType.APPLICATION_XML);
		Response responsePost = invocationBuilder.post(Entity.entity(setting, MediaType.APPLICATION_XML));
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

				.request(MediaType.APPLICATION_XML);

		response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		Settings settingRead = response.readEntity(Settings.class);
		assertThat(settingRead).isNotNull();

		assertTrue(settingRead instanceof Settings);

		// getting device specific output
		invocationBuilder = serviceTarget.path("filter").path("id").path(Long.toString(setting.getId())).path("sf5")

				.request(MediaType.TEXT_PLAIN);

		response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		String deviceSettings = response.readEntity(String.class);

		assertThat(deviceSettings).isNotNull();

		// write test files
		ArrayList<String> lines = new ArrayList<String>();
		lines.add(deviceSettings);
		Files.write(Paths.get("f:\\temp\\sf5IToutput.xml"), lines);

		// URL responseFile =
		// ClassLoader.getSystemResource("xml/sf5IToutput.xml");
		// assertThat(responseFile).isNotNull();
		//
		// URI uri = responseFile.toURI();
		// assertThat(uri).isNotNull();
		//
		// String content = new String(Files.readAllBytes(Paths.get(uri)),
		// Charset.forName("UTF-8"));
		// content = content.replace("\r\n\r\n", "\r\n"); // it adds
		// // superfluous
		// // \r\n
		//
		// content = content.replace("\r\n", ""); // it seems to be without crlf
		//
		// assertEquals(content, deviceSettings);
	}

	// getting all user settings with authentication
	private List<Settings> getUserSettings() {

		List<Settings> settList = new ArrayList<Settings>();

		GenericType<List<Settings>> genList = new GenericType<List<Settings>>() {
		};

		WebTarget target = serviceTarget.path("all");

		settList = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(genList);

		assertThat(settList).isNotNull();
		assertThat(settList.size()).isGreaterThan(0);
		return settList;

	}

	private List<Settings> getUserSettingsXML() {

		// WebTarget target = serviceTarget.path("all");
		//
		// Builder response =
		// target.request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML).b;
		//
		// GenericXMLListWrapper<Transponders> settingWrapper =
		// response.readEntity(GenericXMLListWrapper.class);

		Response response = null;

		Invocation.Builder invocationBuilder = serviceTarget.path("all").request(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML);
		response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		GenericXMLListWrapper<Settings> settingWrapper = response.readEntity(GenericXMLListWrapper.class);

		List<Settings> settingList = mapper.convertValue(settingWrapper.getWrappedList(),
				new TypeReference<List<Settings>>() {
				});

		assertThat(settingList).isNotNull();
		assertThat(settingList.size()).isGreaterThan(0);

		Settings settingRead = settingList.get(0);
		assertTrue(settingRead instanceof Settings);

		return settingList;

	}

	@Test
	public void shouldGetUserSettings() {
		getUserSettings();
	}

	@Test
	public void shouldGetUserSettingsXML() {
		getUserSettingsXML();
	}

	@Test
	public void shouldGetSettingsByArbitraryFilter() throws IOException {
		Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("Name").path("Simple")
				.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		GenericType<List<Settings>> genList = new GenericType<List<Settings>>() {
		};

		List<Settings> newSettingsList = response.readEntity(genList);

		assertThat(newSettingsList).isNotNull();
		assertThat(newSettingsList.size()).isGreaterThan(0);

	}

	@Test
	public void shouldGetSettingsByArbitraryFilterXML() {
		Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("Name").path("Second")
				.request(MediaType.APPLICATION_XML);

		Response response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		GenericXMLListWrapper<Settings> settingWrapper = response.readEntity(GenericXMLListWrapper.class);

		List<Settings> settingList = mapper.convertValue(settingWrapper.getWrappedList(),
				new TypeReference<List<Settings>>() {
				});

		assertThat(settingList).isNotNull();
		assertThat(settingList.size()).isGreaterThan(0);

		Settings settingRead = settingList.get(0);
		assertTrue(settingRead instanceof Settings);

	}

	@Autowired
	public Jaxb2Marshaller springMarshaller;

}
