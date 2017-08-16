package org.openbox.sf5.json.service;

import java.util.Collections;
import java.util.Optional;

import org.openbox.sf5.model.Users;
import org.openbox.sf5.service.CriterionService;
import org.openbox.sf5.service.ObjectsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UsersJsonizer {

    @Autowired
    private CriterionService criterionService;

    @Autowired
    private ObjectsController objectsController;

	public HttpStatus saveNewUser(Users user) {
		long id = user.getId();
		// if we receive non-empty id
		if (id != 0) {
			return HttpStatus.CONFLICT;
		}
		objectsController.saveOrUpdate(user);
		return HttpStatus.CREATED;

	}

	public Users getUserByLogin(String typeValue) {
        return Optional
                .ofNullable(
                criterionService.getCriterionByClassFieldAndStringValue(Users.class, "username", typeValue))
                .map(cr -> objectsController.restrictionList(Users.class, cr)).orElse(Collections.emptyList()).stream()
                .findFirst().orElse(null);
	}

	// returns false if there is no such user. Otherwise - false.
	public Boolean checkIfUsernameExists(String typeValue) {
        return Optional
                .ofNullable(
                criterionService.getCriterionByClassFieldAndStringValue(Users.class, "username", typeValue))
                .map(cr -> objectsController.restrictionList(Users.class, cr)).orElse(Collections.emptyList()).stream()
                .findAny().isPresent();
	}

	public ObjectsController getObjectsController() {
		return objectsController;
	}

	public void setObjectsController(ObjectsController objectsController) {
		this.objectsController = objectsController;
	}

	public CriterionService getCriterionService() {
		return criterionService;
	}

	public void setCriterionService(CriterionService criterionService) {
		this.criterionService = criterionService;
	}
}
