package org.openbox.sf5.json.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.openbox.sf5.model.Users;
import org.openbox.sf5.service.CriterionService;
import org.openbox.sf5.service.ObjectsController;
import org.openbox.sf5.service.ObjectsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UsersJsonizer {

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
		Users returnUser = null;
		Criterion criterion = criterionService.getCriterionByClassFieldAndStringValue(Users.class, "username",
				typeValue);

		if (criterion == null) {
			return returnUser;
		}

		List<Users> userList = listService.ObjectsCriterionList(Users.class, criterion);
		if (userList.size() == 0) {
			return returnUser;
		}
		returnUser = userList.get(0);
		return returnUser;
	}

	// returns false if there is no such user. Otherwise - false.
	public Boolean checkIfUsernameExists(String typeValue) {
		Boolean result = false;
		Criterion criterion = criterionService.getCriterionByClassFieldAndStringValue(Users.class, "username",
				typeValue);

		if (criterion == null) {
			return result;
		}
		List<Users> userList = listService.ObjectsCriterionList(Users.class, criterion);
		// if (userList.size() == 0) {
		// return result;
		// } else {
		// result = true;
		// }
		result = (userList.size() == 0) ? false : true;

		return result;
	}

	public ObjectsListService getListService() {
		return listService;
	}

	public void setListService(ObjectsListService listService) {
		this.listService = listService;
	}

	@Autowired
	private ObjectsListService listService;

	@Autowired
	private CriterionService criterionService;

	@Autowired
	private ObjectsController objectsController;

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
