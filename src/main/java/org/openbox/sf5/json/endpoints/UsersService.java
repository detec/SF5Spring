package org.openbox.sf5.json.endpoints;

import java.util.Optional;

import org.openbox.sf5.json.service.UsersJsonizer;
import org.openbox.sf5.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${jaxrs.path}/users/")
public class UsersService {

    @Autowired
    private UsersJsonizer usersJsonizer;

	@PreAuthorize("hasRole('ROLE_ADMIN') or (#login  == authentication.name)")
    @GetMapping("filter/username/{login}")
	public ResponseEntity<Users> getUserByLogin(@PathVariable("login") String login) {
		Users retUser = null;
		try {
			retUser = usersJsonizer.getUserByLogin(login);
		} catch (Exception e) {
			throw new IllegalStateException("Error getting user from database!", e);
		}

        return Optional.ofNullable(retUser).map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseThrow(
                () -> new IllegalArgumentException(String.join("", "No user found in database for login: ", login)));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody Users user) {
		// check if such user exists.
		Boolean result = usersJsonizer.checkIfUsernameExists(user.getusername());
		if (result) {
			throw new IllegalArgumentException("Not created! Username already exists.");
		}

		try {
            usersJsonizer.saveNewUser(user);
        } catch (Exception e) {
			throw new IllegalStateException("Error when saving user to database", e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("UserId", Long.toString(user.getId()));
		return new ResponseEntity<>(new Long(user.getId()), headers, HttpStatus.CREATED);
	}

    @GetMapping("exists/username/{login}")
	public ResponseEntity<Boolean> ifSuchLoginExists(@PathVariable("login") String login) {
        return usersJsonizer.checkIfUsernameExists(login) ? new ResponseEntity<>(true, HttpStatus.ACCEPTED)
                : new ResponseEntity<>(false, HttpStatus.NO_CONTENT);
	}

	public UsersJsonizer getUsersJsonizer() {
		return usersJsonizer;
	}

	public void setUsersJsonizer(UsersJsonizer usersJsonizer) {
		this.usersJsonizer = usersJsonizer;
	}
}
