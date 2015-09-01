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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TranspondersListClass {

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

	@RequestMapping(value = "/transponders", method = RequestMethod.GET)
	public String getTransponders(
			@RequestParam(value = "filtersatid", required = false) Long id,
			Model model) {

		if (id != null) {

			if (id.longValue() != 0) {
				ObjectsController contr = new ObjectsController();
				filterSatellite = (Satellites) contr.select(Satellites.class,
						id.longValue());
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

		model.addAttribute("bean", this);

		return "transponders";
	}

	@RequestMapping(value = "/transponders", method = RequestMethod.POST)
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
			this.filterSatellite = (Satellites) contr.select(Satellites.class,
					bean.filterSatelliteId.longValue());

			Criterion criterion = Restrictions.eq("Satellite",
					this.filterSatellite);
			TranspondersList = (List<Transponders>) ObjectsListService
					.ObjectsCriterionList(Transponders.class, criterion);
		}

		else {
			TranspondersList = (List<Transponders>) ObjectsListService
					.ObjectsList(Transponders.class);
		}

		model.addAttribute("bean", this);

		return "transponders";
	}

	@ModelAttribute("transpondersList")
	public List<Transponders> getTranspondersList() {
		return TranspondersList;
	}

	public void setTranspondersList(List<Transponders> transpondersList) {
		TranspondersList = transpondersList;
	}

	public List<TransponderChoice> getTransponderChoiceList() {
		return TransponderChoiceList;
	}

	public void setTransponderChoiceList(
			List<TransponderChoice> transponderChoiceList) {
		TransponderChoiceList = transponderChoiceList;
	}

	@ModelAttribute("satellites")
	public HashMap<Long, String> getSatellites() {

		HashMap<Long, String> satMap = new HashMap<Long, String>();

		((List<Satellites>) ObjectsListService.ObjectsList(Satellites.class))
				.stream().forEach(
						t -> satMap.put(new Long(t.getId()), t.getName()));

		return satMap;
		// return (List<Satellites>) ObjectsListService
		// .ObjectsList(Satellites.class);
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
	}

}
