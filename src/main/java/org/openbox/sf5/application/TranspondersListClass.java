package org.openbox.sf5.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.converters.TransponderChoice;
import org.openbox.sf5.converters.TransponderChoiceEditor;
import org.openbox.sf5.converters.TransponderChoiceListWrapper;
import org.openbox.sf5.converters.UserEditor;
import org.openbox.sf5.dao.ObjectsController;
import org.openbox.sf5.model.Satellites;
import org.openbox.sf5.model.Transponders;
import org.openbox.sf5.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope("request")
public class TranspondersListClass {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// exactly this order should be maintained!
		binder.setAutoGrowCollectionLimit(4096);

		binder.registerCustomEditor(Users.class, UserEditor);
		binder.registerCustomEditor(Transponders.class, TransponderChoiceEditor);
		// binder.registerCustomEditor(TransponderChoice.class,
		// TransponderChoiceEditor);

	}

	@RequestMapping(value = "/transponders", method = RequestMethod.GET)
	public String getTransponders(@RequestParam(value = "filtersatid", required = false) Long filtersatid,
			@RequestParam(value = "SelectionMode", required = false) Boolean pSelectionMode, Model model,
			HttpServletRequest request) {

		if (filtersatid != null) {

			if (filtersatid.longValue() != 0) {
				filterSatellite = objectsController.select(Satellites.class, filtersatid.longValue());
			}
		}

		if (filterSatellite != null) {
			Criterion criterion = Restrictions.eq("Satellite", filterSatellite);
			TranspondersList = objectsController.restrictionList(Transponders.class, criterion);
		} else {

			TranspondersList = objectsController.list(Transponders.class);

		}

		if (pSelectionMode != null) {
			SelectionMode = pSelectionMode;
		}

		TransponderChoiceListWrapper wrapper = new TransponderChoiceListWrapper();
		wrapper.setTclist(getTransponderChoiceList());
		model.addAttribute("bean", this);
		model.addAttribute("wrapper", wrapper);

		return "transponders";
	}

	@RequestMapping(params = "filter", value = "/transponders", method = RequestMethod.POST)
	public String postGetTransponders(@ModelAttribute("bean") TranspondersListClass bean, Model model) {

		if (bean.filterSatelliteId != null) {

			filterSatellite = objectsController.select(Satellites.class, bean.filterSatelliteId.longValue());

			Criterion criterion = Restrictions.eq("Satellite", filterSatellite);
			TranspondersList = objectsController.restrictionList(Transponders.class, criterion);
		}

		else {
			TranspondersList = objectsController.list(Transponders.class);
		}

		SelectionMode = bean.SelectionMode;

		model.addAttribute("bean", this);
		TransponderChoiceListWrapper wrapper = new TransponderChoiceListWrapper();
		wrapper.setTclist(getTransponderChoiceList());
		model.addAttribute("wrapper", wrapper);

		return "transponders";
	}

	@RequestMapping(params = "select", value = "/transponders", method = RequestMethod.POST)
	public String postSelectTransponders(@ModelAttribute("bean") TranspondersListClass bean,
			@ModelAttribute("wrapper") TransponderChoiceListWrapper wrapper, BindingResult result) {

		AppContext.getSelectedTransponders().clear();

		List<Transponders> transList = new ArrayList<>();

		wrapper.getTclist().stream().filter(t -> t.isChecked()).forEach(t -> {
			t.setObjectsController(objectsController); // or we get NPE
			transList.add(t.getTransponder());

		});

		AppContext.setSelectedTransponders(transList);

		String idStr = String.valueOf(AppContext.getCurentlyEditedSetting().getId());
		String returnAddress = "";
		if (idStr.equals("0")) {
			// if it is a new unsaved settings - use add scenario
			// returnAddress = "redirect:/settings/add";
			returnAddress = "redirect:/addsetting";
		} else {
			returnAddress = "redirect:/editsetting?id=" + idStr + "&selectionmode=false";
		}
		return returnAddress;
	}

	@ModelAttribute("transpondersList")
	public List<Transponders> getTranspondersList() {
		return TranspondersList;
	}

	public void setTranspondersList(ArrayList<Transponders> transpondersList) {
		TranspondersList = transpondersList;
	}

	@ModelAttribute("satellites")
	public HashMap<Long, String> getSatellites() {

		HashMap<Long, String> satMap = new HashMap<>();

		objectsController.list(Satellites.class).stream().forEach(t -> satMap.put(new Long(t.getId()), t.getName()));

		return satMap;

	}

	public List<TransponderChoice> getTransponderChoiceList() {

		TransponderChoiceList.clear();

		for (Transponders e : TranspondersList) {
			TransponderChoiceList.add(new TransponderChoice(e));
		}

		return TransponderChoiceList;
	}

	@Autowired
	private UserEditor UserEditor;

	@Autowired
	private TransponderChoiceEditor TransponderChoiceEditor;

	@Autowired
	private ObjectsController objectsController;

	@Autowired
	private SF5ApplicationContext AppContext;

	@ModelAttribute("selectionMode")
	public boolean isSelectionMode() {
		return SelectionMode;
	}

	public void setSelectionMode(boolean selectionMode) {
		SelectionMode = selectionMode;
	}

	public Satellites getFilterSatellite() {
		return filterSatellite;
	}

	public void setFilterSatellite(Satellites filterSatellite) {
		this.filterSatellite = filterSatellite;
	}

	private Long filterSatelliteId;

	@ModelAttribute("filterSatelliteId")
	public Long getFilterSatelliteId() {
		return filterSatelliteId;
	}

	public void setFilterSatelliteId(Long filterSatelliteId) {
		this.filterSatelliteId = filterSatelliteId;
	}

	private boolean SelectionMode;

	private Satellites filterSatellite;

	private List<Transponders> TranspondersList = new ArrayList<>();

	private List<TransponderChoice> TransponderChoiceList = new ArrayList<>();

}
