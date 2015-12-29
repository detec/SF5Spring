package org.openbox.sf5.json.endpoints;

import org.openbox.sf5.json.service.UsersJsonizer;
import org.openbox.sf5.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
@RequestMapping(value = "/jaxrs/users/")
public class UsersService {

	// https://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html
	// We allow only for admin and enabled user.
	// @PreAuthorize("hasRole('ROLE_ADMIN') or (#login == principal and
	// principal.enabled)")
	@PreAuthorize("hasRole('ROLE_ADMIN') or (#login  == authentication.name)")
	@RequestMapping(value = "filter/username/{login}", method = RequestMethod.GET)
	public ResponseEntity<Users> getUserByLogin(@PathVariable("login") String login) {
		Users retUser = usersJsonizer.getUserByLogin(login);
		if (retUser == null) {
			return new ResponseEntity<Users>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Users>(retUser, HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody Users user) {
		// check if such user exists.
		Boolean result = usersJsonizer.checkIfUsernameExists(user.getusername());
		if (result) {
			return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		}

		HttpStatus statusResult = usersJsonizer.saveNewUser(user);
		if (statusResult.equals(HttpStatus.CONFLICT)) {
			return new ResponseEntity<Void>(statusResult);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("UserId", Long.toString(user.getId()));
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "exists/username/{login}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> ifSuchLoginExists(@PathVariable("login") String login) {
		Boolean result = usersJsonizer.checkIfUsernameExists(login);
		if (!result) {
			return new ResponseEntity<Boolean>(result, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Boolean>(result, HttpStatus.ACCEPTED);
	}

	public UsersJsonizer getUsersJsonizer() {
		return usersJsonizer;
	}

	public void setUsersJsonizer(UsersJsonizer usersJsonizer) {
		this.usersJsonizer = usersJsonizer;
	}

	@Autowired
	private UsersJsonizer usersJsonizer;

}
