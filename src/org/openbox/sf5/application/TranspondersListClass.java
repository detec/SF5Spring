package org.openbox.sf5.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.db.Satellites;
import org.openbox.sf5.db.Transponders;
import org.openbox.sf5.service.ObjectsController;
import org.openbox.sf5.service.ObjectsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope("request")
public class TranspondersListClass {

	@Autowired
	private SF5ApplicationContext AppContext;

	@ModelAttribute("selectionMode")
	public boolean isSelectionMode() {
		return SelectionMode;
	}

	public void setSelectionMode(boolean selectionMode) {
		SelectionMode = selectionMode;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
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

	private boolean multiple;

	private Satellites filterSatellite;

	private List<Transponders> TranspondersList;

	private List<TransponderChoice> TransponderChoiceList = new ArrayList<TransponderChoice>();

	// @RequestMapping(value = "/transponders", method = RequestMethod.GET)
	// public String selectTransponders(
	// @RequestParam(value = "SelectionMode", required = false) Boolean
	// pSelectionMode,
	// @RequestParam(value = "SettingId", required = false) Long pSettingId,
	// Model model, @ModelAttribute("currentObject") Settings pSetting) {
	//
	// TranspondersList = (List<Transponders>) ObjectsListService
	// .ObjectsList(Transponders.class);
	//
	// if (pSelectionMode != null) {
	// this.SelectionMode = pSelectionMode;
	// }
	//
	// if (pSettingId != null) {
	// this.SettingId = pSettingId.longValue();
	// }
	//
	// model.addAttribute("bean", this);
	// // because I cannot cope with table binding
	// if (this.SelectionMode) {
	// model.addAttribute("tableItems", getTransponderChoiceList());
	// } else {
	// model.addAttribute("tableItems", this.TranspondersList);
	// }
	//
	// model.addAttribute("setting", pSetting);
	//
	// // return "redirect:/editsetting?id=" + String.valueOf(SettingId);
	// return "transponders";
	// }

	@RequestMapping(value = "/transponders", method = RequestMethod.GET)
	public String getTransponders(
			@RequestParam(value = "filtersatid", required = false) Long filtersatid,
			@RequestParam(value = "SelectionMode", required = false) Boolean pSelectionMode,
			Model model) {

		if (filtersatid != null) {

			if (filtersatid.longValue() != 0) {
				ObjectsController contr = new ObjectsController();
				filterSatellite = (Satellites) contr.select(Satellites.class,
						filtersatid.longValue());
			}
		}

		if (filterSatellite != null) {
			Criterion criterion = Restrictions.eq("Satellite", filterSatellite);
			TranspondersList = (List<Transponders>) ObjectsListService
					.ObjectsCriterionList(Transponders.class, criterion);
		} else {
			// return (List<Transponders>) ObjectsListService
			// .ObjectsList(Transponders.class);
			TranspondersList = (List<Transponders>) ObjectsListService
					.ObjectsList(Transponders.class);

		}

		if (pSelectionMode != null) {
			SelectionMode = pSelectionMode;
		}

		model.addAttribute("bean", this);
		// because I cannot cope with table binding
		if (SelectionMode) {
			model.addAttribute("tableItems", getTranspondersChoice());
		} else {
			model.addAttribute("tableItems", TranspondersList);
		}

		return "transponders";
	}

	@RequestMapping(params = "filter", value = "/transponders", method = RequestMethod.POST)
	public String postGetTransponders(
			@ModelAttribute("bean") TranspondersListClass bean,
			BindingResult result, Model model) {

		// this.filterSatellite = bean.filterSatellite;
		// String returnString = "";
		// if (bean.filterSatellite != null) {
		// returnString = "/transponders?filtersatid="
		// + String.valueOf(bean.filterSatellite.getId());
		// } else {
		// returnString = "/transponders";
		// }
		// return returnString;
		if (bean.filterSatelliteId != null) {
			ObjectsController contr = new ObjectsController();
			filterSatellite = (Satellites) contr.select(Satellites.class,
					bean.filterSatelliteId.longValue());

			Criterion criterion = Restrictions.eq("Satellite", filterSatellite);
			TranspondersList = (List<Transponders>) ObjectsListService
					.ObjectsCriterionList(Transponders.class, criterion);
		}

		else {
			TranspondersList = (List<Transponders>) ObjectsListService
					.ObjectsList(Transponders.class);
		}

		// because I cannot cope with table binding
		if (SelectionMode) {
			model.addAttribute("tableItems", getTranspondersChoice());
		} else {
			model.addAttribute("tableItems", TranspondersList);
		}
		model.addAttribute("bean", this);

		return "transponders";
	}

	@RequestMapping(params = "select", value = "/transponders", method = RequestMethod.POST)
	public String postSelectTransponders(
			@ModelAttribute("tableItems") ArrayList<TransponderChoice> tableItems) {

		// selectedTranspondersList.clear();

		AppContext.getSelectedTransponders().clear();

		List<Transponders> transList = new ArrayList<Transponders>();
		// TransponderChoiceList = tableItems;
		// for (TransponderChoice e : TransponderChoiceList) {
		// if (e.checked) {
		// selectedTranspondersList.add(e);
		// }
		// }
		tableItems.stream().filter(t -> t.checked)
				.forEach(t -> transList.add(t));

		AppContext.setSelectedTransponders(transList);

		String idStr = String.valueOf(AppContext.getCurentlyEditedSetting()
				.getId());
		String returnAddress = "";
		if (idStr.equals("0")) {
			// if it is a new unsaved settings - use add scenario
			returnAddress = "redirect:/settings/add";
		} else {
			returnAddress = "redirect:/editsetting?id=" + idStr;
		}
		return returnAddress;
	}

	@ModelAttribute("transpondersList")
	public List<Transponders> getTranspondersList() {
		return TranspondersList;
	}

	public void setTranspondersList(List<Transponders> transpondersList) {
		TranspondersList = transpondersList;
	}

	@ModelAttribute("satellites")
	public HashMap<Long, String> getSatellites() {

		HashMap<Long, String> satMap = new HashMap<Long, String>();

		((List<Satellites>) ObjectsListService.ObjectsList(Satellites.class))
				.stream().forEach(
						t -> satMap.put(new Long(t.getId()), t.getName()));

		return satMap;

	}

	public List<TransponderChoice> getTranspondersChoice() {

		TransponderChoiceList.clear();

		for (Transponders e : TranspondersList) {
			TransponderChoiceList.add(new TransponderChoice(e));
		}

		return TransponderChoiceList;
	}

	public class TransponderChoice extends Transponders {

		/**
		 *
		 */
		private static final long serialVersionUID = 3262084796351763445L;
		private boolean checked;

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}

		public TransponderChoice(Transponders transponder) {
			super(transponder);
			checked = false;
		}

		// Spring needs default constructor for components
		public TransponderChoice() {

		}

		public void init() {

		}
	}

}
