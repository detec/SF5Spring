package org.openbox.sf5.application;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.common.Intersections;
import org.openbox.sf5.common.XMLExporter;
import org.openbox.sf5.converters.CarrierEditor;
import org.openbox.sf5.converters.FECEditor;
import org.openbox.sf5.converters.SettingsEditor;
import org.openbox.sf5.converters.SqlTimestampPropertyEditor;
import org.openbox.sf5.converters.TransponderChoiceEditor;
import org.openbox.sf5.converters.UserEditor;
import org.openbox.sf5.converters.VersionOfTheDVBEditor;
import org.openbox.sf5.db.CarrierFrequency;
import org.openbox.sf5.db.DVBStandards;
import org.openbox.sf5.db.Settings;
import org.openbox.sf5.db.SettingsConversion;
import org.openbox.sf5.db.Transponders;
import org.openbox.sf5.db.TypesOfFEC;
import org.openbox.sf5.db.Users;
import org.openbox.sf5.service.ObjectsController;
import org.openbox.sf5.service.ObjectsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("request")
public class SettingsForm {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// exactly this order should be maintained!
		binder.setAutoGrowCollectionLimit(4096);

		binder.registerCustomEditor(Users.class, new UserEditor());
		binder.registerCustomEditor(Transponders.class,
				new TransponderChoiceEditor());
		// binder.registerCustomEditor(TransponderChoice.class,
		// new TransponderChoiceEditor());
		binder.registerCustomEditor(java.sql.Timestamp.class,
				new SqlTimestampPropertyEditor());
		binder.registerCustomEditor(Settings.class, new SettingsEditor());
		binder.registerCustomEditor(TypesOfFEC.class, new FECEditor());
		binder.registerCustomEditor(CarrierFrequency.class, new CarrierEditor());
		binder.registerCustomEditor(DVBStandards.class,
				new VersionOfTheDVBEditor());
	}

	// makes conversion of properties from db layer to controller
	public void writeFromSettingsObjectToSettingsForm() {
		id = SettingsObject.getId();
		Name = SettingsObject.getName();
		User = SettingsObject.getUser();
		TheLastEntry = SettingsObject.getTheLastEntry();

		List<SettingsConversion> listRead = SettingsObject.getConversion();

		dataSettingsConversion.clear();

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

	public void writeHeaderFromSettingsFormToSettingsObject(
			SettingsForm pSetting) {

		SettingsObject = (pSetting.SettingsObject == null) ? new Settings()
				: pSetting.SettingsObject;

		SettingsObject.setName(pSetting.Name);

		SettingsObject.setTheLastEntry(new java.sql.Timestamp(System
				.currentTimeMillis()));

		// let's refresh the user because it returns empty.
		readCurrentUser();
		SettingsObject.setUser(pSetting.User);
	}

	public void writeFromSettingsFormToSettingsObject(SettingsForm pSetting) {

		writeHeaderFromSettingsFormToSettingsObject(pSetting);

		dataSettingsConversion = pSetting.dataSettingsConversion;

		List<SettingsConversion> tempSCList = new ArrayList<SettingsConversion>();
		dataSettingsConversion.stream().forEach(t -> {
			t.setparent_id(SettingsObject);
			tempSCList.add(t);
		});
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

	@RequestMapping(params = "save", value = "/editsetting", method = RequestMethod.POST)
	public String editSaveSetting(
			@ModelAttribute("bean") SettingsForm pSetting, Model model) {

		// here we must check session attributes selectedTransponders,
		// selectedSettingsConversionPresentations
		// and add them to model

		return add(pSetting, model);

	}

	// here we save setting
	@RequestMapping(params = "add", value = "/settings/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("bean") SettingsForm pSetting, Model model) {

		saveSettingWithoutContext(pSetting);

		writeFromSettingsObjectToSettingsForm();

		model.addAttribute("bean", this);
		model.addAttribute("viewMsg", "Setting saved!");
		return "editsetting";

	}

	@RequestMapping(params = "selectfromother", value = "/settings/add", method = RequestMethod.POST)
	public String newSelectFromOtherSetting(
			@ModelAttribute("bean") SettingsForm pSetting) {
		return prepareSelectFromOtherSetting(pSetting);
		// return selectTranspondersFromNew(pSetting);
	}

	@RequestMapping(params = "cancel", value = "/settings/add", method = RequestMethod.POST)
	public String newCancelSettingEdit() {
		return "redirect:/settings";
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
		return "redirect:/transponders?SelectionMode=true";
	}

	@RequestMapping(params = "selectTransponders", value = "/settings/add", method = RequestMethod.POST)
	public String selectTranspondersFromNew(
			@ModelAttribute("bean") SettingsForm pSetting) {
		return prepareToSelectTransponders(pSetting);
	}

	@RequestMapping(params = "selectfromother", value = "/editsetting", method = RequestMethod.POST)
	public String prepareSelectFromOtherSetting(
			@ModelAttribute("bean") SettingsForm pSetting) {

		AppContext.setCurentlyEditedSetting(pSetting);
		return "redirect:/settings/select?selectionmode=true";
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
			@RequestParam(value = "id", required = true) long pid,
			@RequestParam(value = "selectionmode", required = true) boolean pSelectionMode,
			Model model) {

		// SettingsForm setting = null;
		// check if we have this object in AppContext
		SettingsForm checkCurrSetting = AppContext.getCurentlyEditedSetting();

		if (checkCurrSetting != null) {
			if (checkCurrSetting.getName() != null && !pSelectionMode) {
				readSettingFromContext();
			} else {
				readAndFillBeanfromSetting(pid);

				SelectionMode = pSelectionMode;
			}
		}

		else {
			readAndFillBeanfromSetting(pid);

			SelectionMode = pSelectionMode;
		}

		renumerateLines();
		model.addAttribute("bean", this);
		return "editsetting";
	}

	// here we start to create setting
	@RequestMapping(value = "/settings/add", method = RequestMethod.GET)
	public String getAdd(Model model) {

		// SettingsForm setting = null;
		// check if we have this object in AppContext
		SettingsForm checkCurrSetting = AppContext.getCurentlyEditedSetting();

		if (checkCurrSetting != null) {
			if (checkCurrSetting.getName() != null) {
				readSettingFromContext();
			} else {

				initializeSettingsFormAsNew();
			}
		}

		else {
			initializeSettingsFormAsNew();
		}

		// check if SettingsObject is initialized
		// = (expression) ? value if true : value if false
		putNewSettingsIntoSettingsObject();
		model.addAttribute("bean", this);

		return "editsetting";

	}

	public void putNewSettingsIntoSettingsObject() {
		SettingsObject = (SettingsObject == null) ? new Settings()
				: SettingsObject;
		// after selection SettingsObject is null.
		dataSettingsConversion.stream().forEach(
				t -> t.setparent_id(SettingsObject));
	}

	public void initializeSettingsFormAsNew() {
		SettingsObject = new Settings();

		readCurrentUser();

		Name = "New setting";
		TheLastEntry = new java.sql.Timestamp(System.currentTimeMillis());

	}

	public void readSettingFromContext() {
		SettingsForm setting = AppContext.getCurentlyEditedSetting();
		id = setting.id;
		Name = setting.Name;
		SettingsObject = setting.SettingsObject;

		User = setting.User;
		TheLastEntry = setting.TheLastEntry;
		dataSettingsConversion = setting.dataSettingsConversion;

		putNewSettingsIntoSettingsObject();

		SelectionMode = setting.SelectionMode;

		// add transponders, if they are not null
		List<Transponders> selTransList = AppContext.getSelectedTransponders();
		if (selTransList != null) {

			selTransList.stream().forEach(t -> addNewLine(t, this));
			// warning about final variable.
			// for (Transponders t : selTransList) {
			// addNewLine(t, this);
			// }
			// clean after processing
			selTransList.clear();
			AppContext.setSelectedTransponders(selTransList);
			AppContext.setCurentlyEditedSetting(null);

		}

		// read prepared SCP rows.
		List<SettingsConversionPresentation> presList = AppContext
				.getSelectedSettingsConversionPresentations();
		if (presList != null) {
			presList.stream().forEach(t -> addForeignSCProw(t));
		}

		// clear selected SCP rows
		AppContext
				.setSelectedSettingsConversionPresentations(new ArrayList<SettingsConversionPresentation>());

	}

	public void addForeignSCProw(SettingsConversionPresentation SCProw) {
		SCProw.setId(0);
		SCProw.setparent_id(SettingsObject);
		dataSettingsConversion.add(SCProw);

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

	@RequestMapping(params = "removeSCrows", value = "/settings/add", method = RequestMethod.POST)
	public String newRemoveSCrows(
			@ModelAttribute("bean") SettingsForm pSetting, Model model) {
		return removwRow(pSetting, model);
	}

	@RequestMapping(params = "removeSCrows", value = "/editsetting", method = RequestMethod.POST)
	public String removwRow(@ModelAttribute("bean") SettingsForm pSetting,
			Model model) {

		writeHeaderFromSettingsFormToSettingsObject(pSetting);

		dataSettingsConversion = pSetting.dataSettingsConversion;
		putNewSettingsIntoSettingsObject();

		// there may be unsaved, delete them as selected collection
		List<SettingsConversionPresentation> firstList = dataSettingsConversion
				.stream().filter(t -> (t.isChecked() && t.getId() == 0))
				.collect(Collectors.toList());
		dataSettingsConversion.removeAll(firstList);

		List<SettingsConversionPresentation> toRemove = new ArrayList<SettingsConversionPresentation>();
		Map<Long, SettingsConversionPresentation> tpMap = new HashMap<>();

		for (SettingsConversionPresentation e : dataSettingsConversion) {
			if (!e.isChecked()) {
				continue;
			} else {
				toRemove.add(e);
				tpMap.put(new Long(e.getId()), e);
			}

		}

		dataSettingsConversion.removeAll(toRemove);

		List<SettingsConversion> tpConversion = SettingsObject.getConversion();

		// int initialSize = tpConversion.size();
		ArrayList<SettingsConversion> deleteArray = new ArrayList<SettingsConversion>();

		// define elements to be deleted
		for (SettingsConversion e : tpConversion) {
			if (tpMap.get(new Long(e.getId())) != null) {
				deleteArray.add(e);
			}
		}

		tpConversion.removeAll(deleteArray);
		// TpConversion may be empty. Let's load it with values.
		// if (tpConversion.size() == 0 && dataSettingsConversion.size() > 0) {

		// we must refresh settings conversion
		tpConversion.clear();
		tpConversion.addAll(dataSettingsConversion);

		// }

		// int finalSize = tpConversion.size();
		renumerateLines();

		// save if row should be deleted from database.
		// if (deleteArray.size() > 0) {
		// if (initialSize != finalSize) {
		SettingsObject.setConversion(tpConversion);
		ObjectsController contr = new ObjectsController();
		contr.saveOrUpdate(SettingsObject);
		// }

		// String idStr = String.valueOf(SettingsObject.getId());
		// String returnAddress = "redirect:/editsetting?id=" + idStr
		// + "&selectionmode=false";
		// return returnAddress;
		model.addAttribute("bean", this);

		return "editsetting";

	}

	@RequestMapping(params = "moveup", value = "/settings/add", method = RequestMethod.POST)
	public String newMoveUp(@ModelAttribute("bean") SettingsForm pSetting,
			Model model) {
		return moveUp(pSetting, model);
	}

	@RequestMapping(params = "movedown", value = "/settings/add", method = RequestMethod.POST)
	public String newMoveDown(@ModelAttribute("bean") SettingsForm pSetting,
			Model model) {
		return moveDown(pSetting, model);
	}

	@RequestMapping(params = "moveup", value = "/editsetting", method = RequestMethod.POST)
	public String moveUp(@ModelAttribute("bean") SettingsForm pSetting,
			Model model) {

		writeHeaderFromSettingsFormToSettingsObject(pSetting);

		readToThisBean(pSetting);

		List<SettingsConversionPresentation> selectedRows = new ArrayList<SettingsConversionPresentation>();
		selectedRows = dataSettingsConversion.stream()
				.filter(t -> t.isChecked()).collect(Collectors.toList());
		selectedRows.stream().forEach(t -> {
			int currentIndex = dataSettingsConversion.indexOf(t);
			if (currentIndex > 0) {
				dataSettingsConversion.add(currentIndex - 1, t);
				dataSettingsConversion.remove(currentIndex + 1); // now it is 1
																	// item
																	// larger
			}
		}

		);

		renumerateLines();

		model.addAttribute("bean", this);

		return "editsetting";

		// String idStr = String.valueOf(SettingsObject.getId());
		// String returnAddress = "redirect:/editsetting?id=" + idStr;
		// return returnAddress;
	}

	public void readToThisBean(SettingsForm pSetting) {
		dataSettingsConversion = pSetting.dataSettingsConversion;

		id = pSetting.id;
		Name = pSetting.Name;
		SettingsObject = pSetting.SettingsObject;

		putNewSettingsIntoSettingsObject();

		TheLastEntry = pSetting.TheLastEntry;
		User = pSetting.User;
		SelectionMode = pSetting.SelectionMode;
	}

	@RequestMapping(params = "movedown", value = "/editsetting", method = RequestMethod.POST)
	public String moveDown(@ModelAttribute("bean") SettingsForm pSetting,
			Model model) {

		writeHeaderFromSettingsFormToSettingsObject(pSetting);

		readToThisBean(pSetting);

		List<SettingsConversionPresentation> selectedRows = new ArrayList<SettingsConversionPresentation>();
		selectedRows = dataSettingsConversion.stream()
				.filter(t -> t.isChecked()).collect(Collectors.toList());

		// if we have 2 elements, they do not move
		Collections.reverse(selectedRows);
		selectedRows.stream().forEach(
				t -> {
					int currentIndex = dataSettingsConversion.indexOf(t);
					if (currentIndex < dataSettingsConversion.size() - 1) {
						dataSettingsConversion.add(currentIndex + 1, t);
						dataSettingsConversion.add(currentIndex,
								dataSettingsConversion.get(currentIndex + 2));
						// removing superfluous copies.
						dataSettingsConversion.remove(currentIndex + 2);
						dataSettingsConversion.remove(currentIndex + 2);
					}

				});
		renumerateLines();
		model.addAttribute("bean", this);

		return "editsetting";
	}

	@RequestMapping(params = "cancelselectRows", value = "/editsetting", method = RequestMethod.POST)
	public String cancelSelectRows(@ModelAttribute("bean") SettingsForm pSetting) {

		String idStr = String.valueOf(AppContext.getCurentlyEditedSetting().id);
		String returnAddress = "redirect:/editsetting?id=" + idStr
				+ "&selectionmode=false";
		return returnAddress;
	}

	@RequestMapping(params = "selectRows", value = "/editsetting", method = RequestMethod.POST)
	public String selectSCProws(@ModelAttribute("bean") SettingsForm pSetting) {
		dataSettingsConversion = pSetting.dataSettingsConversion;

		List<SettingsConversionPresentation> selectedRows = new ArrayList<SettingsConversionPresentation>();
		selectedRows = dataSettingsConversion.stream()
				.filter(t -> t.isChecked()).collect(Collectors.toList());

		AppContext.setSelectedSettingsConversionPresentations(selectedRows);
		String idStr = String.valueOf(AppContext.getCurentlyEditedSetting().id);
		String returnAddress = "redirect:/editsetting?id=" + idStr
				+ "&selectionmode=false";
		return returnAddress;
	}

	@RequestMapping(params = "checkIntersection", value = "/settings/add", method = RequestMethod.POST)
	public String newCheckIntersection(
			@ModelAttribute("bean") SettingsForm pSetting, Model model)
			throws SQLException {
		checkIntersection(pSetting, model);
		return "editsetting";

	}

	@RequestMapping(params = "checkIntersection", value = "/editsetting", method = RequestMethod.POST)
	public void checkIntersection(
			@ModelAttribute("bean") SettingsForm pSetting, Model model)
			throws SQLException {

		dataSettingsConversion = pSetting.dataSettingsConversion;

		readToThisBean(pSetting);

		// let's clear all old intersections and save setting.
		dataSettingsConversion.stream().forEach(
				t -> t.setTheLineOfIntersection(0));

		saveSettingWithoutContext(pSetting);

		int rows = Intersections.checkIntersection(dataSettingsConversion,
				SettingsObject);

		// reloadDataSettingsConversion();

		model.addAttribute("bean", this);
		String mesString = "Intersection check result. Unique problem lines: "
				+ String.valueOf(rows);

		model.addAttribute("viewMsg", mesString);

	}

	public void saveSettingWithoutContext(SettingsForm pSetting) {

		writeFromSettingsFormToSettingsObject(pSetting);

		ObjectsController contr = new ObjectsController();
		contr.saveOrUpdate(SettingsObject);

	}

	public boolean check32Rows() {

		if (dataSettingsConversion.size() != 32) {
			// "Table Transponders must contain exactly 32 records!");
			return false;
		} else {

			return true;
		}
	}

	@RequestMapping(params = "generateSatTpStructure", value = "/settings/add", method = RequestMethod.POST)
	public String newGenerateSatTp(
			@ModelAttribute("bean") SettingsForm pSetting, Model model) {
		generateSatTpStructureUniversal(pSetting, model);

		return "editsetting";
	}

	@RequestMapping(params = "generateSatTpStructure", value = "/editsetting", method = RequestMethod.POST)
	public void generateSatTpStructure(
			@ModelAttribute("bean") SettingsForm pSetting, Model model) {
		generateSatTpStructureUniversal(pSetting, model);
	}

	public void generateSatTpStructureUniversal(SettingsForm pSetting,
			Model model) {
		readToThisBean(pSetting);

		dataSettingsConversion = pSetting.dataSettingsConversion;

		long sat = 1;
		long currentCount = 0;
		for (SettingsConversionPresentation e : dataSettingsConversion) {
			currentCount++;
			e.setSatindex(sat);
			e.setTpindex(currentCount);
			if (currentCount == 4) {
				currentCount = 0;
				sat++;
			}
		}

		model.addAttribute("bean", this);
	}

	@RequestMapping(params = "exportToXML", value = "/editsetting", method = RequestMethod.POST)
	@ResponseBody
	public HttpEntity<String> exportToXML(
			@ModelAttribute("bean") SettingsForm pSetting) {
		return universalexportToXML(pSetting);
	}

	@RequestMapping(params = "exportToXML", value = "/settings/add", method = RequestMethod.POST)
	@ResponseBody
	public HttpEntity<String> newExportToXML(
			@ModelAttribute("bean") SettingsForm pSetting) {
		return universalexportToXML(pSetting);
	}

	@ResponseBody
	public HttpEntity<String> universalexportToXML(SettingsForm pSetting) {
		dataSettingsConversion = pSetting.dataSettingsConversion;

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.TEXT_HTML);

		byte[] bytesBuffer = new byte[32768];

		if (!check32Rows()) {
			ResponseEntity<String> resp = new ResponseEntity<String>(
					new String(
							"Table Transponders must contain exactly 32 rows!"),
					header, HttpStatus.OK);
			return resp;
		}

		String filePath = XMLExporter
				.exportSettingToXML(dataSettingsConversion);

		if (filePath == "") {
			return new ResponseEntity<String>(
					"Error reading export XML file on server!", header,
					HttpStatus.OK);
		}

		header.setContentType(new MediaType("application", "xml"));

		try {

			bytesBuffer = Files.readAllBytes(Paths.get(filePath));

			// clean temporary file
			Files.deleteIfExists(Paths.get(filePath));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<String>(new String(bytesBuffer,
				Charset.forName("UTF8")), header, HttpStatus.OK);

	}

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public String printSetting(
			@RequestParam(value = "id", required = true) long pid, Model model,
			HttpSession session) {

		readAndFillBeanfromSetting(pid);

		renumerateLines();

		XMLExporter.generateSatTp(dataSettingsConversion);

		model.addAttribute("bean", this);
		model.addAttribute("sessiondate",
				new Date(session.getLastAccessedTime()));
		return "settingprintfull";
	}

	@RequestMapping(params = "print", value = "/editsetting", method = RequestMethod.POST)
	public String printSettingPost(@ModelAttribute("bean") SettingsForm pSetting) {

		return "redirect:/print?id=" + String.valueOf(pSetting.id);
	}

	public void readAndFillBeanfromSetting(long pid) {
		ObjectsController contr = new ObjectsController();
		// setting = (Settings) contr.select(Settings.class, id);
		SettingsObject = (Settings) contr.select(Settings.class, pid);

		// fill form values.
		writeFromSettingsObjectToSettingsForm();
	}

	@RequestMapping(value = "/settings/settings", method = RequestMethod.GET)
	public String redirectToSettings() {
		return "redirect:/settings";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isSelectionMode() {
		return SelectionMode;
	}

	public void setSelectionMode(boolean selectionMode) {
		SelectionMode = selectionMode;
	}

	public Timestamp getTheLastEntry() {
		return TheLastEntry;
	}

	public void setTheLastEntry(Timestamp TheLastEntry) {
		this.TheLastEntry = TheLastEntry;
	}

	public Settings getSettingsObject() {
		return SettingsObject;
	}

	public void setSettingsObject(Settings settingsObject) {
		SettingsObject = settingsObject;
	}

	public List<SettingsConversionPresentation> getDataSettingsConversion() {
		return dataSettingsConversion;
	}

	public void setDataSettingsConversion(
			List<SettingsConversionPresentation> dataSettingsConversion) {
		this.dataSettingsConversion = dataSettingsConversion;
	}

	public Users getUser() {
		return User;
	}

	public void setUser(Users currentUser) {
		User = currentUser;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getName() {
		return Name;
	}

	@Autowired
	private SF5ApplicationContext AppContext;

	private long id;

	private boolean SelectionMode;

	private Timestamp TheLastEntry;

	private Settings SettingsObject;

	private Users User;

	private String Name;

	private List<SettingsConversionPresentation> dataSettingsConversion = new ArrayList<SettingsConversionPresentation>();

}
