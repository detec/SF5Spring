package org.openbox.sf5.json.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.common.IniReader;
import org.openbox.sf5.model.Satellites;
import org.openbox.sf5.model.Transponders;
import org.openbox.sf5.service.CriterionService;
import org.openbox.sf5.service.ObjectsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TranspondersJsonizer {

    @Autowired
    private IniReader iniReader;

    @Autowired
    private ObjectsController objectsController;

    @Autowired
    private CriterionService criterionService;

	public Boolean uploadTransponders(MultipartFile file) {
		try {
			iniReader.readMultiPartFile(file);
		} catch (Exception e) {
			return new Boolean(false);
		}
		return new Boolean(iniReader.isResult());
	}

	public List<Transponders> getTranspondersByArbitraryFilter(String fieldName, String typeValue) {
        return Optional
                .ofNullable(criterionService.getCriterionByClassFieldAndStringValue(Transponders.class, fieldName,
                typeValue)).map(cr -> objectsController.restrictionList(Transponders.class, cr))
                .orElse(Collections.emptyList());
	}

	public List<Transponders> getTranspondersBySatelliteId(long satId) {
        return Optional.ofNullable(objectsController.select(Satellites.class, satId))
                .map(sat -> Restrictions.eq("Satellite", sat))
                .map(cr -> objectsController.restrictionList(Transponders.class, cr)).orElse(Collections.emptyList());
	}


	public CriterionService getCriterionService() {
		return criterionService;
	}

	public void setCriterionService(CriterionService criterionService) {
		this.criterionService = criterionService;
	}

	public ObjectsController getObjectsController() {
		return objectsController;
	}

	public void setObjectsController(ObjectsController objectsController) {
		this.objectsController = objectsController;
	}

}
