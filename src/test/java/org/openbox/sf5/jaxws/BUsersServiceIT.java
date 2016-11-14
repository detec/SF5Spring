package org.openbox.sf5.jaxws;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.openbox.sf5.wsmodel.Users;
import org.openbox.sf5.wsmodel.Usersauthorities;
import org.openbox.sf5.wsmodel.WSException;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BUsersServiceIT extends AbstractWSTest {

	@Test
	public void shouldCheckCreateTestLogin() {

		boolean userExists = false;
		try {
			userExists = SF5Port.ifSuchLoginExists(testUsername);
		} catch (WSException e) {

			System.out.println("Error checking whether login exists! " + e);

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

		Usersauthorities checkRoleUser = new Usersauthorities();
		checkRoleUser.setAuthority("ROLE_USER");
		checkRoleUser.setParentId(testUser);
		checkRoleUser.setUsername(testUsername);
		checkRoleUser.setLineNumber(1);

		rolesList.add(checkRoleUser);
		testUser.getAuthorities().addAll(rolesList);

		long userId = 0;
		try {
			userId = SF5Port.createUser(testUser);

		} catch (WSException e) {

			e.printStackTrace();
		}
		assertThat(userId).isNotZero();
	}

	@Test
	public void loginShouldNotBeFound() {
		boolean userId = false;
		try {
			userId = SF5Port.ifSuchLoginExists("loginxxf");
		} catch (WSException e) {
			assertTrue(e instanceof WSException);
		}

	}

	@Test
	public void shouldGetUserAsItself() throws Exception {
		this.setUpAbstract();

		Users testUser = null;
		try {
			testUser = SF5Port.getUserByLogin(testUsername);
		} catch (WSException e) {
			System.out.println("Error posting user to database! " + e);
		}
		assertThat(testUser).isNotNull();
	}

	@Before
	public void setUp() throws Exception {
		this.setUpAbstractAdmin();
	}

}
