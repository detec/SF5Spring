package org.openbox.sf5.converters;

import java.beans.PropertyEditorSupport;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.model.Users;
import org.openbox.sf5.service.ObjectsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserEditor extends PropertyEditorSupport {

	@Autowired
	private ObjectsListService service;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		Criterion criterion = Restrictions.eq("username", text);

		List<Users> usersList = service.ObjectsCriterionList(Users.class, criterion);
		if (!usersList.isEmpty()) {
			setValue(usersList.get(0));
		}

	}
}
