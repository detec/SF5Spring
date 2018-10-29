package org.openbox.sf5.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.openbox.sf5.dao.UserRepository;
import org.openbox.sf5.model.Users;
import org.openbox.sf5.model.Usersauthorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminCheck {

    private static final String ADMIN_USERNAME = "admin";

    @Autowired
    private UserRepository userRepository;

	@PostConstruct
	public void initialize() {
        Users admin;
        List<Usersauthorities> rolesList;

		Optional<Users> adminOptional = this.userRepository.findByUsername(ADMIN_USERNAME);
        if (!adminOptional.isPresent()) {
            rolesList = new ArrayList<>();
            admin = new Users("admin", "1", true, rolesList);
        } else {
            admin = adminOptional.get();
            rolesList = admin.getauthorities();
		}

        fillTables(admin, rolesList);
        this.userRepository.save(admin);
	}

	public void fillTables(Users adminUser, List<Usersauthorities> rolesList) {
		List<String> textRoles = new ArrayList<>();
		textRoles.add("ROLE_ADMIN");
		textRoles.add("ROLE_USER");

		// ROLE_ADMIN
		Usersauthorities checkRoleAdmin = new Usersauthorities(adminUser.getusername(), "ROLE_ADMIN", adminUser, 1);

		if (!rolesList.contains(checkRoleAdmin)) {
			rolesList.add(checkRoleAdmin);
		}

		// ROLE_USER
		Usersauthorities checkRoleUser = new Usersauthorities(adminUser.getusername(), "ROLE_USER", adminUser, 2);

		if (!rolesList.contains(checkRoleUser)) {
			rolesList.add(checkRoleUser);
		}
	}
}
