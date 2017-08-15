package org.openbox.sf5.json.service;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.openbox.sf5.model.Satellites;
import org.openbox.sf5.service.CriterionService;
import org.openbox.sf5.service.ObjectsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SatellitesJsonizer implements Serializable {
    private static final long serialVersionUID = 3401682206534536724L;

    @Autowired
    private ObjectsController objectsController;

    @Autowired
    private CriterionService criterionService;

	public List<Satellites> getSatellitesByArbitraryFilter(String fieldName, String typeValue) {
        return Optional.ofNullable(
                criterionService.getCriterionByClassFieldAndStringValue(Satellites.class, fieldName, typeValue))
                .map(cr -> objectsController.restrictionList(Satellites.class, cr)).orElse(Collections.emptyList());
	}

	public CriterionService getCriterionService() {
		return criterionService;
	}

	public void setCriterionService(CriterionService criterionService) {
		this.criterionService = criterionService;
	}


}
