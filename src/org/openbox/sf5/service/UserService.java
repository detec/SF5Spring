package org.openbox.sf5.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.application.UserDto;
import org.openbox.sf5.converters.UserNotFoundException;
import org.openbox.sf5.db.Users;
import org.openbox.sf5.db.Usersauthorities;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

	@Transactional
	@Override
	public Users registerNewUserAccount(UserDto accountDto)
			throws UserNotFoundException {

		if (userExists(accountDto.getUsername())) {
			throw new UserNotFoundException(
					"There is an account with that username: "
							+ accountDto.getUsername());
		}

		// the rest of the registration operation
		ObjectsController contr = new ObjectsController();
		Users newUser = new Users();
		newUser.setusername(accountDto.getUsername());
		newUser.setPassword(accountDto.getPassword());
		List<Usersauthorities> listAuthorities = new ArrayList<Usersauthorities>();
		listAuthorities.add(new Usersauthorities("ROLE_USER"));
		newUser.setauthorities(listAuthorities);
		contr.saveOrUpdate(newUser);

		return newUser;

	}

	public boolean userExists(String username) {

		Criterion criterion = Restrictions.eq("username", username);
		List<Users> rec = (List<Users>) ObjectsListService
				.ObjectsCriterionList(Users.class, criterion);
		if (rec.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}
}
