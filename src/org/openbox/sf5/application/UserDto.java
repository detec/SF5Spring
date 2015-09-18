package org.openbox.sf5.application;

import javax.validation.constraints.Size;

import org.openbox.sf5.converters.PasswordMatches;

@PasswordMatches
public class UserDto {

	@Size(min = 1, max = 20)
	private String username;

	@Size(min = 1, max = 20)
	private String password;

	@Size(min = 1, max = 20)
	private String matchingPassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	// standard getters and setters
}
