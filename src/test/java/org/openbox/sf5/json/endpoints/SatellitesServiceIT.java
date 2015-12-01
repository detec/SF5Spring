package org.openbox.sf5.json.endpoints;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SatellitesServiceIT extends AbstractServiceTest {

	private static final String servicePath = "satellites";

	@Test
	public void shouldgetSatelliteById() {

		WebTarget target = null;
		Response response = null;

		Client client = createClient();

		// target = client.target(appLocation + "satellites/filter/id/1");
		target = client.target(appLocation).path(jsonPath).path(servicePath).path("filter").path("id").path("1");

		response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

	}

	@Test
	public void shouldgetAllSatellites() {

		WebTarget target = null;
		Response response = null;
		Client client = createClient();

		// target = client.target(appLocation + "satellites/all/");
		target = client.target(appLocation).path(jsonPath).path(servicePath).path("all");

		response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

	}

	@Test
	public void getSatellitesByArbitraryFilter() {
		WebTarget target = null;
		Response response = null;
		Client client = createClient();

		// target = client.target(appLocation + "satellites/filter/Name/13E");
		target = client.target(appLocation).path(jsonPath).path(servicePath).path("filter").path("Name").path("13E");

		response = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	// Client createClient() {
	// return
	// ClientBuilder.newBuilder().register(JacksonJaxbJsonProvider.class).build();
	// }

}
