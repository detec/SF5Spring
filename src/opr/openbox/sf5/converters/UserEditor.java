package opr.openbox.sf5.converters;

import java.beans.PropertyEditorSupport;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.db.Users;
import org.openbox.sf5.service.ObjectsListService;

public class UserEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		Criterion criterion = Restrictions.eq("username", text);

		List<Users> usersList = (List<Users>) ObjectsListService
				.ObjectsCriterionList(Users.class, criterion);
		if (!usersList.isEmpty()) {
			setValue(usersList.get(0));
		}

	}

}
