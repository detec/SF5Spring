package org.openbox.sf5.json.endpoints;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import com.fasterxml.jackson.core.type.TypeReference;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "file:src/main/resources/spring/autowired-beans.xml" })
//@WebAppConfiguration
@RunWith(JUnit4.class)
public class SatellitesServiceIT extends AbstractServiceTest {

	private String servicePath = "satellites/";

	private long satelliteId;

	@Before
	public void setUp() {
		setUpAbstractTestUser();
		serviceTarget = commonTarget.path(servicePath);
	}

	public long getSatelliteId() {

		Response response = null;
		GenericType<List<Satellites>> genList = new GenericType<List<Satellites>>() {
		};

		Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("Name").path("13E")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		List<Satellites> satList = invocationBuilder.get(genList);

		assertThat(satList).isNotNull();
		assertThat(satList.size()).isGreaterThan(0);

		Satellites satellite = satList.get(0);
		assertTrue(satellite instanceof Satellites);
		satelliteId = satellite.getId();

		return satelliteId;

	}

	@Test
	public void shouldgetAllSatellites() {

		Response response = null;
		GenericType<List<Satellites>> genList = new GenericType<List<Satellites>>() {
		};
		Invocation.Builder invocationBuilder = serviceTarget

				// .path("")

				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		List<Satellites> satList = invocationBuilder.get(genList);

		assertThat(satList).isNotNull();
		assertThat(satList.size()).isGreaterThan(0);
	}

	@Test
	public void shouldgetAllSatellitesXML() {

		Response response = null;

		Invocation.Builder invocationBuilder = serviceTarget

				// .path("all")

				.request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);
		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		GenericXMLListWrapper<Satellites> satWrapper = response.readEntity(GenericXMLListWrapper.class);

		// List<Satellites> satList = satWrapper.getWrappedList();
		// List<Satellites> satList =
		// mapper.convertValue(satWrapper.getWrappedList(),
		// new TypeReference<List<Satellites>>() {
		// });

		List<Satellites> satList = xmlMapper.convertValue(satWrapper.getWrappedList(),
				new TypeReference<List<Satellites>>() {
				});

		assertThat(satList).isNotNull();
		assertThat(satList.size()).isGreaterThan(0);

		// http://stackoverflow.com/questions/15430715/casting-linkedhashmap-to-complex-object

		Satellites satellite = satList.get(0);
		assertTrue(satellite instanceof Satellites);
	}

	@Test
	public void getSatellitesByArbitraryFilter() {
		Response response = null;
		getSatelliteId();

		Invocation.Builder invocationBuilder = serviceTarget

				// .path("filter").path("id")

				.path(String.valueOf(satelliteId)).request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		Satellites satellite = response.readEntity(Satellites.class);
		assertThat(satellite).isNotNull();

	}

	public long getSatelliteIdXML() {
		Response response = null;
		Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("Name").path("13E")
				.request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);
		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		GenericXMLListWrapper<Satellites> satWrapper = response.readEntity(GenericXMLListWrapper.class);

		List<Satellites> satList = xmlMapper.convertValue(satWrapper.getWrappedList(),
				new TypeReference<List<Satellites>>() {
				});

		assertThat(satList).isNotNull();
		assertThat(satList.size()).isGreaterThan(0);

		Satellites satellite = satList.get(0);
		assertTrue(satellite instanceof Satellites);

		satelliteId = satellite.getId();

		return satelliteId;
	}

	@Test
	public void getSatellitesByArbitraryFilterXML() {
		Response response = null;
		getSatelliteIdXML();

		Invocation.Builder invocationBuilder = serviceTarget

				// .path("filter").path("id")

				.path(String.valueOf(satelliteId)).request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);

		response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		Satellites satellite = response.readEntity(Satellites.class);
		assertThat(satellite).isNotNull();

		assertTrue(satellite instanceof Satellites);

	}

}
