package org.openbox.sf5.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.model.Users;
import org.openbox.sf5.model.Usersauthorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminCheck {

	@Autowired
	private ObjectsListService service;

	@Autowired
	private ObjectsController contr;

	public void initialize() {

		Criterion criterea = Restrictions.eq("username", "admin");
		@SuppressWarnings("unchecked")
		List<Users> adminsList = (List<Users>) service.ObjectsCriterionList(Users.class, criterea);

		if (adminsList.isEmpty()) {
			List<Usersauthorities> rolesList = new ArrayList<>();

			Users admin = new Users("admin", "1", true, rolesList);
			contr.saveOrUpdate(admin);

			fillTables(admin, rolesList);
			contr.saveOrUpdate(admin);
		}

		else {

			Users adminUser = adminsList.get(0);
			List<Usersauthorities> rolesList = adminUser.getauthorities();

			fillTables(adminUser, rolesList);
			contr.saveOrUpdate(adminUser);

		}

	}

	public void fillTables(Users adminUser, List<Usersauthorities> rolesList) {
		List<String> textRoles = new ArrayList<String>();
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
