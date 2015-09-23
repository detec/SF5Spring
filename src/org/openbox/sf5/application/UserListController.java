package org.openbox.sf5.application;

import java.util.ArrayList;
import java.util.List;

import org.openbox.sf5.db.Users;
import org.openbox.sf5.service.ObjectsController;
import org.openbox.sf5.service.ObjectsListService;
import org.openbox.sf5.service.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope("request")
public class UserListController {

	private List<Users> UsersList = new ArrayList<Users>();

	@RequestMapping(value = "/users/", method = RequestMethod.GET)
	public String getUserList(Model model) {

		UsersList = (ArrayList<Users>) ObjectsListService
				.ObjectsList(Users.class);

		model.addAttribute("users", UsersList);

		return "users";
	}

	@RequestMapping(value = "/users/settings", method = RequestMethod.GET)
	public String redirectToSettings() {
		return "redirect:/settings";
	}

	@RequestMapping(value = "/users/logout", method = RequestMethod.POST)
	public String redirectToLogout() {
		return "redirect:/logout";
	}

	@RequestMapping(value = "/users/transponders", method = RequestMethod.GET)
	public String redirectToTransponders() {
		return "redirect:/transponders";
	}

	@RequestMapping(value = "/users/change", method = RequestMethod.GET)
	public String changeState(
			@RequestParam(value = "id", required = true) long pid, Model model) {
		ObjectsController contr = new ObjectsController();
		Users user = (Users) contr.select(Users.class, pid);

		String username = user.getusername();

		if (UserService.hasAdminRole(user)) {
			model.addAttribute("viewErrMsg",
					"It is forbidden to change state of admin user!");
			UsersList = (ArrayList<Users>) ObjectsListService
					.ObjectsList(Users.class);

			model.addAttribute("users", UsersList);
			return "users";
		}

		user.setenabled(!user.getenabled());

		String state = user.getenabled() ? "enabled" : "disabled";

		contr.saveOrUpdate(user);

		model.addAttribute("viewMsg", "Successfully " + state + " user "
				+ username + "!");
		UsersList = (ArrayList<Users>) ObjectsListService
				.ObjectsList(Users.class);

		model.addAttribute("users", UsersList);
		return "users";

	}

	@RequestMapping(value = "/users/delete", method = RequestMethod.GET)
	public String deleteUser(
			@RequestParam(value = "id", required = true) long pid, Model model) {

		ObjectsController contr = new ObjectsController();
		Users userToDelete = (Users) contr.select(Users.class, pid);

		String username = userToDelete.getusername();

		if (UserService.hasAdminRole(userToDelete)) {
			model.addAttribute("viewErrMsg",
					"It is forbidden to remove admin user!");
			UsersList = (ArrayList<Users>) ObjectsListService
					.ObjectsList(Users.class);

			model.addAttribute("users", UsersList);
			return "users";
		}

		contr.remove(Users.class, pid);
		// return "redirect:/users/";
		model.addAttribute("viewMsg", "Successfully deleted user " + username
				+ "!");
		UsersList = (ArrayList<Users>) ObjectsListService
				.ObjectsList(Users.class);

		model.addAttribute("users", UsersList);
		return "users";

	}

	public List<Users> getUsersList() {
		return UsersList;
	}

	public void setUsersList(List<Users> usersList) {
		UsersList = usersList;
	}

}
