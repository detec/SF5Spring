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
import org.openbox.sf5.model.Transponders;
import org.openbox.sf5.model.listwrappers.GenericXMLListWrapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.core.type.TypeReference;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/spring/root-context.xml" })
@WebAppConfiguration
public class TranspondersServiceIT extends AbstractServiceTest {

	private static final String servicePath = "transponders";

	@Before
	public void setUp() {
		setUpAbstractTestUser();
		serviceTarget = commonTarget.path(servicePath);
	}

	@Test
	public void shouldGetTranspondersByArbitraryFilter() {

		// WebTarget target = null;
		Response response = null;
		// Client client = createClient();
		// target = client.target(appLocation +
		// "transponders/filter/Speed/27500");
		// target =
		// client.target(appLocation).path(jsonPath).path(servicePath).path("filter").path("Speed").path("27500");

		// response =
		// target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("Speed").path("27500")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		GenericType<List<Transponders>> genList = new GenericType<List<Transponders>>() {
		};

		List<Transponders> newTransList = response.readEntity(genList);

		assertThat(newTransList).isNotNull();
		assertThat(newTransList.size()).isGreaterThan(0);

	}

	@Test
	public void shouldGetTranspondersByArbitraryFilterXML() {

		Response response = null;
		Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("Speed").path("27500")
				.request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);

		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		// GenericType<List<Transponders>> genList = new
		// GenericType<List<Transponders>>() {
		// };
		//
		// List<Transponders> newTransList = response.readEntity(genList);

		GenericXMLListWrapper<Transponders> transWrapper = response.readEntity(GenericXMLListWrapper.class);

		List<Transponders> newTransList = xmlMapper.convertValue(transWrapper.getWrappedList(),
				new TypeReference<List<Transponders>>() {
				});

		assertThat(newTransList).isNotNull();
		assertThat(newTransList.size()).isGreaterThan(0);

	}

	@Test
	public void shouldGetTransponderById() {
		Response response = null;

		Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("id").path("1")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		Transponders trans = response.readEntity(Transponders.class);
		assertThat(trans).isNotNull();

	}

	@Test
	public void shouldGetTransponderByIdXML() {

		Response response = null;

		Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("id").path("1")
				.request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);
		response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		Transponders trans = response.readEntity(Transponders.class);
		assertThat(trans).isNotNull();
	}

	@Test
	public void shouldGetTranspondersBySatelliteId() {

		Response response = null;

		Invocation.Builder invocationBuilder = serviceTarget.path("filter").matrixParam("satId", "1")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		GenericType<List<Transponders>> genList = new GenericType<List<Transponders>>() {
		};

		List<Transponders> newTransList = response.readEntity(genList);

		assertThat(newTransList).isNotNull();
		assertThat(newTransList.size()).isGreaterThan(0);
	}

	@Test
	public void shouldGetTranspondersBySatelliteIdXML() {

		Response response = null;

		Invocation.Builder invocationBuilder = serviceTarget.path("filter").matrixParam("satId", "1")
				.request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);
		response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		GenericXMLListWrapper<Transponders> transWrapper = response.readEntity(GenericXMLListWrapper.class);

		List<Transponders> newTransList = xmlMapper.convertValue(transWrapper.getWrappedList(),
				new TypeReference<List<Transponders>>() {
				});

		assertThat(newTransList).isNotNull();
		assertThat(newTransList.size()).isGreaterThan(0);

		Transponders transponder = newTransList.get(0);
		assertTrue(transponder instanceof Transponders);

	}

	@Test
	public void shouldGetAllTransponders() {

		Response response = null;

		Invocation.Builder invocationBuilder = serviceTarget.path("all").request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		GenericType<List<Transponders>> genList = new GenericType<List<Transponders>>() {
		};

		List<Transponders> newTransList = response.readEntity(genList);

		assertThat(newTransList).isNotNull();
		assertThat(newTransList.size()).isGreaterThan(0);
	}

	@Test
	public void shouldGetAllTranspondersXML() {

		Response response = null;

		Invocation.Builder invocationBuilder = serviceTarget.path("all").request(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML);
		response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		GenericXMLListWrapper<Transponders> transWrapper = response.readEntity(GenericXMLListWrapper.class);

		List<Transponders> newTransList = xmlMapper.convertValue(transWrapper.getWrappedList(),
				new TypeReference<List<Transponders>>() {
				});

		assertThat(newTransList).isNotNull();
		assertThat(newTransList.size()).isGreaterThan(0);

		Transponders transponder = newTransList.get(0);
		assertTrue(transponder instanceof Transponders);
	}

}
