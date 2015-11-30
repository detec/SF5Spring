package org.openbox.sf5.json.endpoints;

import org.openbox.sf5.json.service.UsersJsonizer;
import org.openbox.sf5.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
@RequestMapping(value = "/json/users/", headers = "Accept=*/*", produces = "application/json")
public class UsersService {

	@RequestMapping(value = "filter/username/{login}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Users> getUserByLogin(@PathVariable("login") String login) {
		Users retUser = usersJsonizer.getUserByLogin(login);
		if (retUser == null) {
			return new ResponseEntity<Users>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Users>(retUser, HttpStatus.OK);

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
