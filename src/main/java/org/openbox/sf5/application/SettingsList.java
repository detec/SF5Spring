package org.openbox.sf5.application;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.common.SF5SecurityContext;
import org.openbox.sf5.converters.UserEditor;
import org.openbox.sf5.dao.ObjectsController;
import org.openbox.sf5.model.Settings;
import org.openbox.sf5.model.Users;
import org.openbox.sf5.model.Usersauthorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
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

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Users.class, UserEditor);
	}

	// we cannot use such mapping with custom form.
	// @RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getSettings(Model model) {
		List<Settings> settingsList = new ArrayList<>();

		readCurrentUser();

		if (currentUser == null) {
			return "notauthenticated";
		}

		// Retrieve all settings
		Criterion criterion = Restrictions.eq("User", currentUser);

		settingsList = objectsController.restrictionList(Settings.class, criterion);

		// Attach settings to the Model
		model.addAttribute("settings", settingsList);
		model.addAttribute("SelectionMode", SelectionMode);
		model.addAttribute("username", currentUser.getusername());
		model.addAttribute("hasAdminRole", hasAdminRole());

		// This will resolve to /WEB-INF/settings.jsp
		return "settings";
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String settingsGetSettings(Model model) {
		return getSettings(model);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String rootsettingsGetSettings(Model model) {
		return getSettings(model);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String barerootsettingsGetSettings(Model model) {
		return getSettings(model);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/deletesetting", method = RequestMethod.GET)
	public String deleteSetting(@RequestParam(value = "id", required = true) long id, Model model) {

		objectsController.remove(Settings.class, id);
		return getSettings(model);
	}

	private void readCurrentUser() {
		currentUser = securityContext.getCurrentlyAuthenticatedUser();

	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/selectsetting", method = RequestMethod.GET)
	public String selectSetting(@RequestParam(value = "selectionmode", required = true) boolean pSelectionMode,
			Model model) {

		SelectionMode = pSelectionMode;
		return getSettings(model);

	}

	@PreAuthorize("hasRole('ROLE_USER')")
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

	@Autowired
	private ObjectsController objectsController;

	@Autowired
	private UserEditor UserEditor;

	@Autowired
	private SF5SecurityContext securityContext;

	private Users currentUser;

	private boolean SelectionMode;

	public boolean isSelectionMode() {
		return SelectionMode;
	}

	public void setSelectionMode(boolean selectionMode) {
		SelectionMode = selectionMode;
	}

}
