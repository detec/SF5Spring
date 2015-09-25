package org.openbox.sf5.service;

import org.openbox.sf5.application.UserDto;
import org.openbox.sf5.converters.UserNotFoundException;
import org.openbox.sf5.db.Users;

public interface IUserService {
	Users registerNewUserAccount(UserDto accountDto)
			throws UserNotFoundException;
}
