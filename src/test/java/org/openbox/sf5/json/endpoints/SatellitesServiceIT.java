package org.openbox.sf5.json.endpoints;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openbox.sf5.model.Satellites;
import org.openbox.sf5.model.listwrappers.GenericXMLListWrapper;

@RunWith(JUnit4.class)
public class SatellitesServiceIT extends AbstractServiceTest {

	private String servicePath = "satellites";

	@Before
	public void setUp() {
		setUpAbstractTestUser();
		serviceTarget = commonTarget.path(servicePath);
	}

	@Test
	public void shouldgetSatelliteById() {

		// WebTarget target = null;
		Response response = null;

		// Client client = createClient();
		//
		// target =
		// client.target(appLocation).path(jsonPath).path(servicePath).path("filter").path("id").path("1");

		// response =
		// target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("id").path("1")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		Satellites satellite = response.readEntity(Satellites.class);
		assertThat(satellite).isNotNull();

	}

	@Test
	public void shouldgetSatelliteByIdXML() {

		Response response = null;

		Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("id").path("1")
				.request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);

		response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		Satellites satellite = response.readEntity(Satellites.class);
		assertThat(satellite).isNotNull();

	}

	@Test
	public void shouldgetAllSatellites() {

		Response response = null;
		GenericType<List<Satellites>> genList = new GenericType<List<Satellites>>() {
		};
		Invocation.Builder invocationBuilder = serviceTarget.path("all").request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		List<Satellites> satList = invocationBuilder.get(genList);

		assertThat(satList).isNotNull();
		assertThat(satList.size()).isGreaterThan(0);
	}

	@Test
	public void shouldgetAllSatellitesXML() {

		Response response = null;

		Invocation.Builder invocationBuilder = serviceTarget.path("all").request(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML);
		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		GenericXMLListWrapper<Satellites> satWrapper = response.readEntity(GenericXMLListWrapper.class);

		List<Satellites> satList = satWrapper.getWrappedList();

		assertThat(satList).isNotNull();
		assertThat(satList.size()).isGreaterThan(0);

	}

	@Test
	public void getSatellitesByArbitraryFilter() {

		// WebTarget target = null;

		Response response = null;
		GenericType<List<Satellites>> genList = new GenericType<List<Satellites>>() {
		};
		// Client client = createClient();

		// target = client.target(appLocation + "satellites/filter/Name/13E");
		// target =
		// client.target(appLocation).path(jsonPath).path(servicePath).path("filter").path("Name").path("13E");

		// response =
		// target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("Name").path("13E")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		List<Satellites> satList = invocationBuilder.get(genList);

		assertThat(satList).isNotNull();
		assertThat(satList.size()).isGreaterThan(0);
	}

	@Test
	public void getSatellitesByArbitraryFilterXML() {

		Response response = null;
		Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("Name").path("13E")
				.request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);
		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		GenericXMLListWrapper<Satellites> satWrapper = response.readEntity(GenericXMLListWrapper.class);

		List<Satellites> satList = satWrapper.getWrappedList();

		assertThat(satList).isNotNull();
		assertThat(satList.size()).isGreaterThan(0);

	}

}
