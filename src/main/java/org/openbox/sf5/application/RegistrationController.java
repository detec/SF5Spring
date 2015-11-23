package org.openbox.sf5.application;

import javax.servlet.http.HttpServletRequest;

import org.openbox.sf5.converters.UserNotFoundException;
import org.openbox.sf5.model.Users;
import org.openbox.sf5.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
@Scope("request")
public class RegistrationController {

	@Autowired
	private IUserService service;

	@Autowired
	@Qualifier("authenticationManager")
	protected AuthenticationManager authenticationManager;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegistrationForm(WebRequest request, Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("user", userDto);
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUserAccount(@ModelAttribute("user") UserDto accountDto, BindingResult result,
			HttpServletRequest request, Errors errors, Model model) {

		// Let's manually check if password and other fields are empty
		if (accountDto.getUsername().equals("")) {
			model.addAttribute("viewErrMsg", "Field 'Username' cannot be empty!");
			return "register";
		}

		if (accountDto.getPassword().equals("")) {
			model.addAttribute("viewErrMsg", "Field 'Password' cannot be empty!");
			// return new ModelAndView("register",
			return "register";
		}

		if (accountDto.getMatchingPassword().equals("")) {
			model.addAttribute("viewErrMsg", "Field 'Matching password' cannot be empty!");
			return "register";
		}

		if (!accountDto.getPassword().equals(accountDto.getMatchingPassword())) {
			model.addAttribute("viewErrMsg", "Passwords do not match!");
			return "register";
		}

		Users user = new Users();
		if (!result.hasErrors()) {
			user = createUserAccount(accountDto, result);
		}
		if (user == null) {
			// result.rejectValue("username", "User not created!");
			// result.reject("username", "User not created!");
			model.addAttribute("viewErrMsg", "User not created! There is a user with such name!");
			return "register";
		}

		if (result.hasErrors()) {
			// return new ModelAndView("register", "user", accountDto);
			model.addAttribute("viewErrMsg", "Unknown error!");
			return "register";
		} else {

			// I added this from stackoverflow example
			authenticateUserAndSetSession(user, request);
			// return new ModelAndView("login", "user", accountDto);
			// return new ModelAndView("settings");
			model.addAttribute("username", user.getusername());
			model.addAttribute("viewMsg", user.getusername() + " successfully registered!");
			return "settings";
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

	private void authenticateUserAndSetSession(Users user, HttpServletRequest request) {
		String username = user.getusername();
		String password = user.getPassword();
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

		// generate session if one doesn't exist
		request.getSession();

		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager.authenticate(token);

		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) boolean loginError, Model model) {

		if (loginError) {
			// indicate about bad credentials.
			model.addAttribute("errormessage", "Bad username/password!");
		}
		return "login";
	}

}
