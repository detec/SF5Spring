package org.openbox.sf5.application;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.converters.SettingsEditor;
import org.openbox.sf5.converters.SqlTimestampPropertyEditor;
import org.openbox.sf5.converters.TransponderChoice;
import org.openbox.sf5.converters.TransponderChoiceEditor;
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
import org.springframework.web.bind.WebDataBinder;
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

	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private Timestamp TheLastEntry;

	public Timestamp getTheLastEntry() {
		return TheLastEntry;
	}

	public void setTheLastEntry(Timestamp TheLastEntry) {
		this.TheLastEntry = TheLastEntry;
	}

	private Settings SettingsObject;

	public Settings getSettingsObject() {
		return SettingsObject;
	}

	public void setSettingsObject(Settings settingsObject) {
		SettingsObject = settingsObject;
	}

	private Users User;

	private List<SettingsConversionPresentation> dataSettingsConversion = new ArrayList<SettingsConversionPresentation>();

	public List<SettingsConversionPresentation> getDataSettingsConversion() {
		return dataSettingsConversion;
	}

	public void setDataSettingsConversion(
			List<SettingsConversionPresentation> dataSettingsConversion) {
		this.dataSettingsConversion = dataSettingsConversion;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// exactly this order should be maintained!
		binder.setAutoGrowCollectionLimit(4096);

		binder.registerCustomEditor(Users.class, new UserEditor());
		binder.registerCustomEditor(Transponders.class,
				new TransponderChoiceEditor());
		binder.registerCustomEditor(TransponderChoice.class,
				new TransponderChoiceEditor());
		binder.registerCustomEditor(java.sql.Timestamp.class,
				new SqlTimestampPropertyEditor());
		binder.registerCustomEditor(Settings.class, new SettingsEditor());
	}

	public Users getUser() {
		return User;
	}

	public void setUser(Users currentUser) {
		User = currentUser;
	}

	// makes conversion of properties from db layer to controller
	public void writeFromSettingsObjectToSettingsForm() {
		id = SettingsObject.getId();
		Name = SettingsObject.getName();
		User = SettingsObject.getUser();
		TheLastEntry = SettingsObject.getTheLastEntry();

		List<SettingsConversion> listRead = SettingsObject.getConversion();

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
	}

	public void writeFromSettingsFormToSettingsObject(SettingsForm pSetting) {
		SettingsObject = pSetting.SettingsObject;
		SettingsObject.setName(pSetting.Name);

		SettingsObject.setTheLastEntry(new java.sql.Timestamp(System
				.currentTimeMillis()));

		// let's refresh the user because it returns empty.
		readCurrentUser();
		SettingsObject.setUser(pSetting.User);
		dataSettingsConversion = pSetting.dataSettingsConversion;

		// convert to Conversion
		// this.SettingsObject
		// .setConversion((List<SettingsConversion>)
		// this.dataSettingsConversion);

		List<SettingsConversion> tempSCList = new ArrayList<SettingsConversion>();
		dataSettingsConversion.stream().forEach(t -> tempSCList.add(t));
		SettingsObject.setConversion(tempSCList);

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
			User = usersList.get(0);
		}

	}

	private String Name;

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getName() {
		return Name;
	}

	@RequestMapping(params = "save", value = "/editsetting", method = RequestMethod.POST)
	public String editSaveSetting(@ModelAttribute("bean") SettingsForm pSetting) {

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
			@ModelAttribute("setting") SettingsForm pSetting) {

		AppContext.setCurentlyEditedSetting(pSetting);
		// we will not save setting, but will just pass it between requests.
		return "redirect:/transponders?SelectionMode=true&SettingId="
				+ String.valueOf(pSetting.getId());
	}

	// here we save setting
	@RequestMapping(params = "add", value = "/settings/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("bean") SettingsForm pSetting) {

		ObjectsController contr = new ObjectsController();
		writeFromSettingsFormToSettingsObject(pSetting);
		contr.saveOrUpdate(SettingsObject);

		return "editsetting";
	}

	@RequestMapping(params = "selectTransponders", value = "/settings/add", method = RequestMethod.POST)
	public String selectTranspondersFromNew(@ModelAttribute("bean") SettingsForm pSetting) {
		return prepareToSelectTransponders(pSetting);
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
			@RequestParam(value = "id", required = true) long pid, Model model) {

		SettingsForm setting = null;
		// check if we have this object in AppContext
		SettingsForm checkCurrSetting = AppContext.getCurentlyEditedSetting();

		if (checkCurrSetting != null) {
			if (checkCurrSetting.getName() != null) {
				readSettingFromContext();
			} else {
				ObjectsController contr = new ObjectsController();
				// setting = (Settings) contr.select(Settings.class, id);
				SettingsObject = (Settings) contr.select(Settings.class,
						pid);

				// fill form values.
				writeFromSettingsObjectToSettingsForm();
			}
		}

		else {
			ObjectsController contr = new ObjectsController();
			// setting = (Settings) contr.select(Settings.class, id);
			SettingsObject = (Settings) contr.select(Settings.class, pid);

			// fill form values.
			writeFromSettingsObjectToSettingsForm();

		}

		model.addAttribute("bean", this);
		return "editsetting";
	}

	// here we start to create setting
	@RequestMapping(value = "/settings/add", method = RequestMethod.GET)
	public String getAdd(Model model) {

		SettingsObject = new Settings();

		readCurrentUser();

		Name = "New setting";
		TheLastEntry = new java.sql.Timestamp(System
				.currentTimeMillis());

		model.addAttribute("bean", this);
		// model.addAttribute("DataSC",
		// new ArrayList<SettingsConversionPresentation>());

		return "editsetting";

	}

	public void readSettingFromContext() {
		SettingsForm setting = AppContext.getCurentlyEditedSetting();
		id = setting.id;
		Name = setting.Name;
		SettingsObject = setting.SettingsObject;
		User = setting.User;
		TheLastEntry = setting.TheLastEntry;
		dataSettingsConversion = setting.dataSettingsConversion;


		// add transponders, if they are not null
		List<Transponders> selTransList = AppContext.getSelectedTransponders();
		if (selTransList != null) {

			 selTransList.stream().forEach(t -> addNewLine(t, this));
			// warning about final variable.
//			for (Transponders t : selTransList) {
//				addNewLine(t, this);
//			}
			// clean after processing
			selTransList.clear();
			AppContext.setSelectedTransponders(selTransList);
			AppContext.setCurentlyEditedSetting(null);

		}

	}

	public void addNewLine(Transponders trans, SettingsForm pSetting) {
		long newLine = new Long(dataSettingsConversion.size() + 1).longValue();

		SettingsConversionPresentation newLineObject = new SettingsConversionPresentation(
				pSetting.SettingsObject);

		newLineObject.setLineNumber(newLine);
		// newLineObject.setTransponder(new Transponders()); // to prevent null
		// pointer Exception
		newLineObject.setTransponder(trans);
		newLineObject.setNote("");
		// newLineObject.editable = true;

		dataSettingsConversion.add(newLineObject);

	}
}
