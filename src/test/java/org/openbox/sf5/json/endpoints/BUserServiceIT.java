package org.openbox.sf5.json.endpoints;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openbox.sf5.model.Users;
import org.openbox.sf5.model.Usersauthorities;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(locations = { "file:src/main/resources/spring/autowired-beans.xml" })
@WebAppConfiguration
public class BUserServiceIT extends AbstractServiceTest {

	private static final String servicePath = "users";

	@Before
	public void setUp() {
		setUpAbstractAdmin();

		serviceTarget = commonTarget.path(servicePath);
	}

	@Test
	public void loginShouldNotBeFound() {
		Response response = null;

		Invocation.Builder invocationBuilder = serviceTarget.path("exists").path("username").path("loginxxf")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		response = invocationBuilder.get();

		// there is no such login.
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());

	}

	@Test
	public void loginShouldNotBeFoundXML() {
		Response response = null;

		Invocation.Builder invocationBuilder = serviceTarget.path("exists").path("username").path("loginxxf")
				.request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);

		response = invocationBuilder.get();

		// there is no such login.
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldCheckCreateTestLogin() {
		Response response = null;

		Invocation.Builder invocationBuilder = serviceTarget.path("exists").path("username").path(testUsername)
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		response = invocationBuilder.get();

		if (response.getStatus() == Status.ACCEPTED.getStatusCode()) {
			return;
		}

		// here we create user
		List<Usersauthorities> rolesList = new ArrayList<>();
		Users testUser = new Users(testUsername, testUserPassword, true, rolesList);

		// ROLE_USER
		Usersauthorities checkRoleUser = new Usersauthorities(testUsername, "ROLE_USER", testUser, 1);
		rolesList.add(checkRoleUser);
		testUser.authorities = rolesList;

		invocationBuilder = serviceTarget.path("create").request(MediaType.APPLICATION_JSON);
		Response responsePost = invocationBuilder.post(Entity.entity(testUser, MediaType.APPLICATION_JSON));
		assertEquals(Status.CREATED.getStatusCode(), responsePost.getStatus());
	}

	@Test
	public void shouldCheckCreateAnotherTestLoginXML() {
		Response response = null;

		Invocation.Builder invocationBuilder = serviceTarget.path("exists").path("username").path(testUsername)
				.request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);
		response = invocationBuilder.get();

		if (response.getStatus() == Status.ACCEPTED.getStatusCode()) {
			return;
		}

		// here we create user
		List<Usersauthorities> rolesList = new ArrayList<>();
		Users testUser = new Users("fake", "123", true, rolesList);

		// ROLE_USER
		Usersauthorities checkRoleUser = new Usersauthorities("fake", "ROLE_USER", testUser, 1);
		rolesList.add(checkRoleUser);
		testUser.authorities = rolesList;

		invocationBuilder = serviceTarget.path("create").request(MediaType.APPLICATION_XML);
		Response responsePost = invocationBuilder.post(Entity.entity(testUser, MediaType.APPLICATION_XML));
		assertEquals(Status.CREATED.getStatusCode(), responsePost.getStatus());

	}

}
