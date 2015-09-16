package org.openbox.sf5.converters;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.openbox.sf5.application.UserDto;

public class PasswordMatchesValidator implements
		ConstraintValidator<PasswordMatches, Object> {

	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		UserDto user = (UserDto) obj;
		return user.getPassword().equals(user.getMatchingPassword());
	}

}
