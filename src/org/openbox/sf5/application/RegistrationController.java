package org.openbox.sf5.application;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.openbox.sf5.converters.UserNotFoundException;
import org.openbox.sf5.db.Users;
import org.openbox.sf5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("request")
public class RegistrationController {

	@Autowired
	private UserService service;

	@Autowired
	protected AuthenticationManager authenticationManager;

	@RequestMapping(value = "/user/registration", method = RequestMethod.GET)
	public String showRegistrationForm(WebRequest request, Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("user", userDto);
		return "register";
	}

	@RequestMapping(value = "/user/registration", method = RequestMethod.POST)
	public ModelAndView registerUserAccount(
			@ModelAttribute("user") @Valid UserDto accountDto,
			BindingResult result, HttpServletRequest request, Errors errors) {

		Users user = new Users();
		if (!result.hasErrors()) {
			user = createUserAccount(accountDto, result);
		}
		if (user == null) {
			result.rejectValue("username", "message.regError");
		}
		if (result.hasErrors()) {
			return new ModelAndView("register", "user", accountDto);
		} else {

			// I added this from stackoverflow example
			authenticateUserAndSetSession(user, request);
			// return new ModelAndView("login", "user", accountDto);
			return new ModelAndView("settings");
		}
	}

	private Users createUserAccount(UserDto accountDto, BindingResult result) {
		Users registered = null;
		try {
			registered = service.registerNewUserAccount(accountDto);
		} catch (UserNotFoundException e) {
			return null;
		}
		return registered;
	}

	private void authenticateUserAndSetSession(Users user,
			HttpServletRequest request) {
		String username = user.getusername();
		String password = user.getPassword();
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				username, password);

		// generate session if one doesn't exist
		request.getSession();

		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager
				.authenticate(token);

		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	}

}
