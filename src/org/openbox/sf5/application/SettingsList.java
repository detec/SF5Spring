package org.openbox.sf5.application;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import opr.openbox.sf5.converters.UserEditor;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.db.Settings;
import org.openbox.sf5.db.Users;
import org.openbox.sf5.service.ObjectsController;
import org.openbox.sf5.service.ObjectsListService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SettingsList {

	private Users currentUser;

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Users.class, new UserEditor());
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getSettings(Model model) {
		List<Settings> settingsList = new ArrayList<Settings>();

		readCurrentUser();

		if (currentUser == null) {
			return "notauthenticated";
		}

		// Retrieve all settings
		Criterion criterion = Restrictions.eq("User", currentUser);
		settingsList = (List<Settings>) ObjectsListService
				.ObjectsCriterionList(Settings.class, criterion);

		// Attach settings to the Model
		model.addAttribute("settings", settingsList);

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

	@RequestMapping(value = "/editsetting", method = RequestMethod.GET)
	public String editSetting(
			@RequestParam(value = "id", required = true) long id, Model model) {

		ObjectsController contr = new ObjectsController();
		Settings setting = (Settings) contr.select(Settings.class, id);
		model.addAttribute("setting", setting);
		return "editsetting";
	}

	@RequestMapping(value = "/settings/delete", method = RequestMethod.GET)
	public String deleteSetting(
			@RequestParam(value = "id", required = true) long id, Model model) {

		ObjectsController contr = new ObjectsController();
		contr.remove(Settings.class, id);
		return getSettings(model);
	}

	@RequestMapping(value = "/editsetting", method = RequestMethod.POST)
	public String editSaveSetting(@ModelAttribute("setting") Settings pSetting) {

		return add(pSetting);
	}

	// here we save setting
	@RequestMapping(value = "/settings/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("setting") Settings pSetting) {

		ObjectsController contr = new ObjectsController();
		pSetting.setTheLastEntry(new java.sql.Timestamp(System
				.currentTimeMillis()));

		// let's refresh the user because it returns empty.
		readCurrentUser();

		pSetting.setUser(currentUser);
		contr.saveOrUpdate(pSetting);
		return "editsetting";
	}

	// here we start to create setting
	@RequestMapping(value = "/settings/add", method = RequestMethod.GET)
	public String getAdd(Model model) {

		Settings setting = new Settings();

		readCurrentUser();

		setting.setUser(currentUser);
		setting.setName("New setting");
		model.addAttribute("setting", setting);

		return "editsetting";

	}

	private void readCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		org.springframework.security.core.userdetails.User secUser = (org.springframework.security.core.userdetails.User) auth
				.getPrincipal();

		String username = secUser.getUsername();
		Criterion criterion = Restrictions.eq("username", username);
		List<Users> usersList = (List<Users>) ObjectsListService
				.ObjectsCriterionList(Users.class, criterion);
		if (!usersList.isEmpty()) {
			currentUser = usersList.get(0);
		}
	}

	public Users getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Users currentUser) {
		this.currentUser = currentUser;
	}

}
