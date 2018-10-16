package org.openbox.sf5.json.endpoints;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openbox.sf5.model.Satellites;
import org.openbox.sf5.model.listwrappers.GenericXMLListWrapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.type.TypeReference;

@ExtendWith(SpringExtension.class)
public class SatellitesServiceIT extends AbstractServiceTest {

	private String servicePath = "satellites/";

	private long satelliteId;

    @BeforeEach
	public void setUp() {
		setUpAbstractTestUser();
		serviceTarget = commonTarget.path(servicePath);
	}

	public long getSatelliteId() {

		Response response = null;
		GenericType<List<Satellites>> genList = new GenericType<List<Satellites>>() {
		};

        Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("name").path("13E")
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
        Invocation.Builder invocationBuilder = serviceTarget.request(MediaType.APPLICATION_JSON)
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

		Invocation.Builder invocationBuilder = serviceTarget


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
				.path(String.valueOf(satelliteId)).request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);

		response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		Satellites satellite = response.readEntity(Satellites.class);
		assertThat(satellite).isNotNull();

		assertTrue(satellite instanceof Satellites);
	}

}
