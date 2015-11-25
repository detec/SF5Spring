package org.openbox.sf5.json.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.model.Satellites;
import org.openbox.sf5.model.Transponders;
import org.openbox.sf5.service.CriterionService;
import org.openbox.sf5.service.ObjectsController;
import org.openbox.sf5.service.ObjectsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TranspondersJsonizer {

	public List<Transponders> getTranspondersByArbitraryFilter(String fieldName, String typeValue) {
		List<Transponders> transList = new ArrayList<Transponders>();

		Criterion criterion = criterionService.getCriterionByClassFieldAndStringValue(Transponders.class, fieldName,
				typeValue);

		if (criterion == null) {
			return transList;
		}

		transList = listService.ObjectsCriterionList(Transponders.class, criterion);

		return transList;

	}

	public List<Transponders> getTranspondersBySatelliteId(long satId) {
		List<Transponders> transList = new ArrayList<Transponders>();

		Satellites filterSatellite = objectsController.select(Satellites.class, satId);
		if (filterSatellite == null) {
			return transList;
		}
		Criterion criterion = Restrictions.eq("Satellite", filterSatellite);

		transList = listService.ObjectsCriterionList(Transponders.class, criterion);

		return transList;

	}

	@Autowired
	private ObjectsListService listService;

	@Autowired
	private ObjectsController objectsController;

	@Autowired
	private CriterionService criterionService;

	public CriterionService getCriterionService() {
		return criterionService;
	}

	public void setCriterionService(CriterionService criterionService) {
		this.criterionService = criterionService;
	}

	public ObjectsListService getListService() {
		return listService;
	}

	public void setListService(ObjectsListService listService) {
		this.listService = listService;
	}

	public ObjectsController getObjectsController() {
		return objectsController;
	}

	public void setObjectsController(ObjectsController objectsController) {
		this.objectsController = objectsController;
	}

}
