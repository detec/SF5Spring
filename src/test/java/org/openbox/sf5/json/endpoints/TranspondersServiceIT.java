package org.openbox.sf5.json.endpoints;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.openbox.sf5.model.Transponders;
import org.openbox.sf5.model.listwrappers.GenericXMLListWrapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.type.TypeReference;

@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
public class TranspondersServiceIT extends AbstractServiceTest {

	private static final String servicePath = "transponders";

	private Validator validator;

	private long transponderId;

	@Before
	public void setUp() {
		setUpAbstractTestUser();
		serviceTarget = commonTarget.path(servicePath);

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void shouldGetTranspondersByArbitraryFilter() {

		Response response = null;

        Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("speed").path("27500")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

        GenericType<List<Transponders>> genList = new GenericType<List<Transponders>>() {
        };

		List<Transponders> newTransList = response.readEntity(genList);

		assertThat(newTransList).isNotNull();
		assertThat(newTransList.size()).isGreaterThan(0);

		validateTranspondersList(newTransList);

		Transponders readTrans = newTransList.get(0);
		transponderId = readTrans.getId();

        invocationBuilder = serviceTarget.path(String.valueOf(transponderId)).request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		Transponders trans = response.readEntity(Transponders.class);

		assertThat(trans).isNotNull();
		Set<ConstraintViolation<Transponders>> constraintViolations = validator.validate(trans);
		assertEquals(0, constraintViolations.size());
	}

	@Test
	public void shouldGetTranspondersByArbitraryFilterXML() {

		Response response = null;
        Invocation.Builder invocationBuilder = serviceTarget.path("filter").path("speed").path("27500")
				.request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);

		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		GenericXMLListWrapper<Transponders> transWrapper = response.readEntity(GenericXMLListWrapper.class);

		List<Transponders> newTransList = xmlMapper.convertValue(transWrapper.getWrappedList(),
                new TypeReference<List<Transponders>>() {
                });

		assertThat(newTransList).isNotNull();
		assertThat(newTransList.size()).isGreaterThan(0);

		Transponders readTrans = newTransList.get(0);
		transponderId = readTrans.getId();

        invocationBuilder = serviceTarget.path(String.valueOf(transponderId)).request(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML);
		response = invocationBuilder.get();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		Transponders trans = response.readEntity(Transponders.class);

		assertThat(trans).isNotNull();
		Set<ConstraintViolation<Transponders>> constraintViolations = validator.validate(trans);
		assertEquals(0, constraintViolations.size());
	}

	@Test
	public void shouldGetTranspondersBySatelliteId() {

		Response response = null;

		SatellitesServiceIT satTest = new SatellitesServiceIT();
		satTest.setUp();

        Invocation.Builder invocationBuilder = serviceTarget.path("satId")
                .path(String.valueOf(satTest.getSatelliteId())).request(MediaType.APPLICATION_JSON)
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
	public void shouldGetTranspondersBySatelliteIdXML() {

		Response response = null;
		SatellitesServiceIT satTest = new SatellitesServiceIT();
		satTest.setUp();

        String satId = String.valueOf(satTest.getSatelliteId());
        Invocation.Builder invocationBuilder = serviceTarget.path("satId")
                .path(satId).request(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML);
		response = invocationBuilder.get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		GenericXMLListWrapper<Transponders> transWrapper = response.readEntity(GenericXMLListWrapper.class);

		List<Transponders> newTransList = xmlMapper.convertValue(transWrapper.getWrappedList(),
                new TypeReference<List<Transponders>>() {
                });

		assertThat(newTransList).isNotNull();
		assertThat(newTransList.size()).isGreaterThan(0);
		validateTranspondersList(newTransList);

		Transponders transponder = newTransList.get(0);
		assertTrue(transponder instanceof Transponders);
	}

	@Test
	public void shouldGetAllTransponders() {

		Response response = null;
        Invocation.Builder invocationBuilder = serviceTarget.path("/").request(MediaType.APPLICATION_JSON)
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

        Invocation.Builder invocationBuilder = serviceTarget.path("/").request(MediaType.APPLICATION_XML)
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

	public void validateTranspondersList(List<Transponders> transList) {
        Integer violationsCount = transList.stream().map(validator::validate)
                .map(Set<ConstraintViolation<Transponders>>::size)
                .reduce(Integer::sum).orElse(0);
        assertEquals(0, violationsCount.intValue());
	}

}
