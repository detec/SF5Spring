package org.openbox.sf5.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.converters.UserEditor;
import org.openbox.sf5.db.Settings;
import org.openbox.sf5.db.SettingsConversion;
import org.openbox.sf5.db.Transponders;
import org.openbox.sf5.db.Users;
import org.openbox.sf5.service.ObjectsController;
import org.openbox.sf5.service.ObjectsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
@Scope("request")
public class SettingsForm {

	@Autowired
	private SF5ApplicationContext AppContext;

	private Users currentUser;

	private List<SettingsConversionPresentation> dataSettingsConversion = new ArrayList<SettingsConversionPresentation>();

	public List<SettingsConversionPresentation> getDataSettingsConversion() {
		return dataSettingsConversion;
	}

	public void setDataSettingsConversion(
			List<SettingsConversionPresentation> dataSettingsConversion) {
		this.dataSettingsConversion = dataSettingsConversion;
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Users.class, new UserEditor());
	}

	public Users getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Users currentUser) {
		this.currentUser = currentUser;
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

	@RequestMapping(params = "save", value = "/editsetting", method = RequestMethod.POST)
	public String editSaveSetting(@ModelAttribute("setting") Settings pSetting) {

		// here we must check session attributes selectedTransponders,
		// selectedSettingsConversionPresentations
		// and add them to model

		return add(pSetting);
	}

	@RequestMapping(params = "cancel", value = "/editsetting", method = RequestMethod.POST)
	public String cancelSettingEdit() {

		return "redirect:/settings";
	}

	@RequestMapping(params = "selectTransponders", value = "/editsetting", method = RequestMethod.POST)
	public String prepareToSelectTransponders(
			@ModelAttribute("setting") Settings pSetting, Model model) {

		AppContext.setCurentlyEditedSetting(pSetting);
		// we will not save setting, but will just pass it between requests.
		return "redirect:/transponders?SelectionMode=true&SettingId="
				+ String.valueOf(pSetting.getId());
	}

	// here we save setting
	@RequestMapping(params = "add", value = "/settings/add", method = RequestMethod.POST)
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

	public void renumerateLines() {

		int i = 1;

		for (SettingsConversionPresentation e : dataSettingsConversion) {
			e.setLineNumber(i);
			i++;
		}
	}

	@RequestMapping(value = "/editsetting", method = RequestMethod.GET)
	public String editSetting(
			@RequestParam(value = "id", required = true) long id, Model model) {

		Settings setting = null;
		// check if we have this object in AppContext
		if (AppContext.getCurentlyEditedSetting() != null) {
			setting = readSettingFromContext();
		}

		else {
			ObjectsController contr = new ObjectsController();
			setting = (Settings) contr.select(Settings.class, id);

		}
		model.addAttribute("setting", setting);
		// load transponders and so on

		dataSettingsConversion.clear();
		List<SettingsConversion> listRead = setting.getConversion();

		// for new item it is null
		if (listRead != null) {
			// sort in ascending order
			Collections
					.sort(listRead, (b1, b2) -> (int) (b1.getLineNumber() - b2
							.getLineNumber()));

			for (SettingsConversion e : listRead) {
				dataSettingsConversion
						.add(new SettingsConversionPresentation(e));
			}

		}

		model.addAttribute("DataSC", dataSettingsConversion);
		return "editsetting";
	}

	// here we start to create setting
	@RequestMapping(value = "/settings/add", method = RequestMethod.GET)
	public String getAdd(Model model) {

		Settings setting = new Settings();

		// check if we have this object in AppContext
		if (AppContext.getCurentlyEditedSetting() != null) {
			setting = readSettingFromContext();
		}

		else {
			readCurrentUser();

			setting.setUser(currentUser);
			setting.setName("New setting");

		}

		model.addAttribute("setting", setting);
		model.addAttribute("DataSC",
				new ArrayList<SettingsConversionPresentation>());

		return "editsetting";

	}

	public Settings readSettingFromContext() {
		Settings setting = AppContext.getCurentlyEditedSetting();

		// add transponders, if they are not null
		List<Transponders> selTransList = AppContext.getSelectedTransponders();
		if (selTransList != null) {

			// selTransList.stream().forEach(t -> addNewLine(t, setting));
			// warning about final variable.
			for (Transponders t : selTransList) {
				addNewLine(t, setting);
			}
			// clean after processing
			selTransList.clear();
			AppContext.setSelectedTransponders(selTransList);
			AppContext.setCurentlyEditedSetting(null);

		}
		return setting;
	}

	public void addNewLine(Transponders trans, Settings pSetting) {
		long newLine = new Long(dataSettingsConversion.size() + 1).longValue();

		SettingsConversionPresentation newLineObject = new SettingsConversionPresentation(
				pSetting);

		newLineObject.setLineNumber(newLine);
		// newLineObject.setTransponder(new Transponders()); // to prevent null
		// pointer Exception
		newLineObject.setTransponder(trans);
		newLineObject.setNote("");
		// newLineObject.editable = true;

		dataSettingsConversion.add(newLineObject);

	}
}
