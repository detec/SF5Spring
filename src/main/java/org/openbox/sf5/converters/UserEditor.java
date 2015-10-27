package org.openbox.sf5.converters;

import java.beans.PropertyEditorSupport;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.db.Users;
import org.openbox.sf5.service.ObjectsListServiceNonStatic;
import org.springframework.beans.factory.annotation.Autowired;

public class UserEditor extends PropertyEditorSupport {

	@Autowired
	private ObjectsListServiceNonStatic service;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		Criterion criterion = Restrictions.eq("username", text);

		@SuppressWarnings("unchecked")
		List<Users> usersList = (List<Users>) service.ObjectsCriterionList(Users.class, criterion);
		if (!usersList.isEmpty()) {
			setValue(usersList.get(0));
		}

	}

}
