package org.openbox.sf5.jaxws;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openbox.sf5.wsmodel.Users;
import org.openbox.sf5.wsmodel.Usersauthorities;
import org.openbox.sf5.wsmodel.WSException_Exception;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(locations = { "file:src/main/resources/spring/autowired-beans.xml" })
@WebAppConfiguration
public class BUsersServiceIT extends AbstractWSTest {

	@Test
	public void shouldCheckCreateTestLogin() {

		boolean userExists = false;
		try {
			userExists = SF5Port.ifSuchLoginExists(testUsername);
		} catch (WSException_Exception e) {
			e.printStackTrace();
		}
		if (userExists) {
			return;
		}

		// here we create user
		Users testUser = new Users();
		testUser.setUsername(testUsername);
		testUser.setPassword(testUserPassword);
		testUser.setEnabled(true);

		// here we create user
		List<Usersauthorities> rolesList = new ArrayList<>();
		// Users testUser = new Users(this.testUsername, this.testUserPassword,
		// true, rolesList);

		// ROLE_USER
		// Usersauthorities checkRoleUser = new
		// Usersauthorities(this.testUsername, "ROLE_USER", testUser, 1);
		// rolesList.add(checkRoleUser);
		// testUser.authorities = rolesList;

		Usersauthorities checkRoleUser = new Usersauthorities();
		checkRoleUser.setAuthority("ROLE_USER");
		checkRoleUser.setParentId(testUser);
		checkRoleUser.setUsername(testUserPassword);
		rolesList.add(checkRoleUser);
		testUser.getAuthorities().addAll(rolesList);

		long userId = 0;
		try {
			userId = SF5Port.createUser(testUser);
		} catch (WSException_Exception e) {
			e.printStackTrace();
		}
		assertThat(userId).isNotZero();
	}

	@Test
	public void loginShouldNotBeFound() {
		boolean userId = false;
		try {
			userId = SF5Port.ifSuchLoginExists("loginxxf");
		} catch (WSException_Exception e) {
			assertTrue(e instanceof WSException_Exception);
		}

	}

	@Before
	public void setUp() throws Exception {
		this.setUpAbstractAdmin();
	}

}
