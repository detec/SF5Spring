package org.openbox.sf5.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.openbox.sf5.dao.DAOList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjectsListService {

	@Autowired
	private DAOList dao;

	public List<?> ObjectsList(Class<?> clazz) {
		return dao.list(clazz);

	}

	public List<?> ObjectsCriterionList(Class<?> clazz, Criterion criterion) {
		return dao.restrictionList(clazz, criterion);
	}

}
