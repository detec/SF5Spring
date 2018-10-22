package org.openbox.sf5.common;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.dao.ObjectsController;
import org.openbox.sf5.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Helper class to get User from authentication.
 * 
 * @author Andrii Duplyk
 *
 */
@Service
// @PreAuthorize("hasAuthority('ROLE_USER')")
public class SF5SecurityContext {

	public Users getCurrentlyAuthenticatedUser() {
		Users returnUser = null;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// http://stackoverflow.com/questions/26101738/why-is-the-anonymoususer-authenticated-in-spring-security
		if (auth instanceof AnonymousAuthenticationToken) {
			return returnUser;
		}

		org.springframework.security.core.userdetails.User secUser = null;

		if (auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
			secUser = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
		} else {
			return returnUser;
		}

		String username = secUser.getUsername();
		Criterion criterion = Restrictions.eq("username", username);

		List<Users> usersList = objectsController.restrictionList(Users.class, criterion);
		if (!usersList.isEmpty()) {
			returnUser = usersList.get(0);
		}

		return returnUser;
	}

	@Autowired
	private ObjectsController objectsController;

}
