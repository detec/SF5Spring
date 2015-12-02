package org.openbox.sf5.json.endpoints;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SatellitesServiceIT extends AbstractServiceTest {

	private String servicePath = "satellites";

	@Before
	public void setUp() {
		setUpAbstract();
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

	}

	@Test
	public void shouldgetAllSatellites() {

		// WebTarget target = null;
		Response response = null;

		// Client client = createClient();

		// target = client.target(appLocation + "satellites/all/");
		// target =
		// client.target(appLocation).path(jsonPath).path(servicePath).path("all");
		//
		// response =
		// target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		Invocation.Builder invocationBuilder = serviceTarget.path("all").request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

	}

	@Test
	public void getSatellitesByArbitraryFilter() {

		// WebTarget target = null;

		Response response = null;

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
	}

}
