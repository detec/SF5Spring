package org.openbox.sf5.application;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.converters.UserEditor;
import org.openbox.sf5.db.Settings;
import org.openbox.sf5.db.Users;
import org.openbox.sf5.db.Usersauthorities;
import org.openbox.sf5.service.ObjectsController;
import org.openbox.sf5.service.ObjectsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
@Scope("request")
public class SettingsList {

	@Autowired
	private ObjectsListService service;

	@Autowired
	private ObjectsController contr;

	private Users currentUser;

	private boolean SelectionMode;

	public boolean isSelectionMode() {
		return SelectionMode;
	}

	public void setSelectionMode(boolean selectionMode) {
		SelectionMode = selectionMode;
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Users.class, new UserEditor());
	}

	// we cannot use such mapping with custom form.
	// @RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getSettings(Model model) {
		List<Settings> settingsList = new ArrayList<Settings>();

		readCurrentUser();

		if (currentUser == null) {
			return "notauthenticated";
		}

		// Retrieve all settings
		Criterion criterion = Restrictions.eq("User", currentUser);

		settingsList = (List<Settings>) service.ObjectsCriterionList(Settings.class, criterion);

		// Attach settings to the Model
		model.addAttribute("settings", settingsList);
		model.addAttribute("SelectionMode", SelectionMode);
		model.addAttribute("username", currentUser.getusername());
		model.addAttribute("hasAdminRole", hasAdminRole());

		// This will resolve to /WEB-INF/settings.jsp
		return "settings";
	}

	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String settingsGetSettings(Model model) {
		return getSettings(model);
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String rootsettingsGetSettings(Model model) {
		return getSettings(model);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String barerootsettingsGetSettings(Model model) {
		return getSettings(model);
	}

	@RequestMapping(value = "/settings/delete", method = RequestMethod.GET)
	public String deleteSetting(@RequestParam(value = "id", required = true) long id, Model model) {

		// ObjectsController contr = new ObjectsController();
		contr.remove(Settings.class, id);
		return getSettings(model);
	}

	private void readCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		org.springframework.security.core.userdetails.User secUser = null;

		if (auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
			secUser = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
		} else {
			return;
		}

		String username = secUser.getUsername();
		Criterion criterion = Restrictions.eq("username", username);

		List<Users> usersList = (List<Users>) service.ObjectsCriterionList(Users.class, criterion);
		if (!usersList.isEmpty()) {
			currentUser = usersList.get(0);
		}
	}

	@RequestMapping(value = "/settings/select", method = RequestMethod.GET)
	public String selectSetting(@RequestParam(value = "selectionmode", required = true) boolean pSelectionMode,
			Model model) {

		SelectionMode = pSelectionMode;
		return getSettings(model);

	}

	@RequestMapping(value = "/selectedsetting", method = RequestMethod.GET)
	public String openSettingForSelection(@RequestParam(value = "id", required = true) long id) {

		String idStr = String.valueOf(id);
		String returnAddress = "redirect:/editsetting?id=" + idStr + "&selectionmode=true";
		return returnAddress;
	}

	private boolean hasAdminRole() {

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

	public Users getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Users currentUser) {
		this.currentUser = currentUser;
	}

}
