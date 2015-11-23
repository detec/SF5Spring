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

	public <T> List<T> ObjectsList(Class<T> type) {
		return dao.list(type);
	}

	public <T> List<T> ObjectsCriterionList(Class<T> type, Criterion criterion) {
		return dao.restrictionList(type, criterion);
	}

	public DAOList getDao() {
		return dao;
	}

	public void setDao(DAOList dao) {
		this.dao = dao;
	}

}
