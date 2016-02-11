package org.openbox.sf5.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.json.exceptions.UserNotFoundException;
import org.openbox.sf5.model.UserDto;
import org.openbox.sf5.model.Users;
import org.openbox.sf5.model.Usersauthorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "session")
public class UserService implements IUserService, Serializable {

	private static final long serialVersionUID = -5341605268427837922L;

	@Autowired
	private ObjectsController objectsController;

	@Autowired
	private ObjectsListService listService;

	// @Transactional // IJ031017: You cannot set autocommit during a managed
	// transaction
	@Override
	public Users registerNewUserAccount(UserDto accountDto) throws UserNotFoundException {

		if (userExists(accountDto.getUsername())) {
			throw new UserNotFoundException("There is an account with that username: " + accountDto.getUsername());
		}

		Users newUser = new Users();
		newUser.setusername(accountDto.getUsername());
		newUser.setPassword(accountDto.getPassword());
		List<Usersauthorities> listAuthorities = new ArrayList<Usersauthorities>();

		Usersauthorities newLine = new Usersauthorities(accountDto.getUsername(), "ROLE_USER", newUser, 1);
		listAuthorities.add(newLine);
		newUser.setauthorities(listAuthorities);
		newUser.setenabled(true);
		objectsController.saveOrUpdate(newUser);

		return newUser;

	}

	public boolean userExists(String username) {

		Criterion criterion = Restrictions.eq("username", username);
		List<Users> rec = listService.ObjectsCriterionList(Users.class, criterion);
		if (rec.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	public ObjectsController getObjectsController() {
		return objectsController;
	}

	public void setObjectsController(ObjectsController objectsController) {
		this.objectsController = objectsController;
	}

	public ObjectsListService getListService() {
		return listService;
	}

	public void setListService(ObjectsListService listService) {
		this.listService = listService;
	}

	public static boolean hasAdminRole(Users currentUser) {

		Usersauthorities checkRoleAdmin = new Usersauthorities(currentUser.getusername(), "ROLE_ADMIN", currentUser, 1);

		boolean result;
		if (currentUser.getauthorities().contains(checkRoleAdmin)) {
			result = true;
		}

		else {
			result = false;
		}

		return result;
	}
}
