package org.openbox.sf5.json.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.openbox.sf5.model.Users;
import org.openbox.sf5.service.CriterionService;
import org.openbox.sf5.service.ObjectsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersJsonizer {

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

	public CriterionService getCriterionService() {
		return criterionService;
	}

	public void setCriterionService(CriterionService criterionService) {
		this.criterionService = criterionService;
	}
}
