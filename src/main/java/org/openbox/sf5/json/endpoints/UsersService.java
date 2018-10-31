package org.openbox.sf5.json.endpoints;

import java.util.Optional;

import org.openbox.sf5.dao.UserRepository;
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
    private UserRepository userRepository;

	@PreAuthorize("hasRole('ROLE_ADMIN') or (#login  == authentication.name)")
    @GetMapping("filter/username/{login}")
	public ResponseEntity<Users> getUserByLogin(@PathVariable("login") String login) {
        return this.userRepository.findByUsername(login).map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(
                () -> new IllegalArgumentException(String.join("", "No user found in database for login: ", login)));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody Users user) {
        Optional<Users> userOptional = this.userRepository.findByUsername(user.getusername());

        if (userOptional.isPresent()) {
			throw new IllegalArgumentException("Not created! Username already exists.");
		}

        user = this.userRepository.save(user);

		HttpHeaders headers = new HttpHeaders();
		headers.add("UserId", Long.toString(user.getId()));
        return new ResponseEntity<>(user.getId(), headers, HttpStatus.CREATED);
	}

    @GetMapping("exists/username/{login}")
	public ResponseEntity<Boolean> ifSuchLoginExists(@PathVariable("login") String login) {
        Optional<Users> userOptional = this.userRepository.findByUsername(login);
        return userOptional.isPresent() ? new ResponseEntity<>(true, HttpStatus.OK)
                : new ResponseEntity<>(false, HttpStatus.NO_CONTENT);
	}

}
