package org.openbox.sf5.service;

import org.openbox.sf5.dao.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjectServiceImpl implements ObjectService {

	@Autowired
	private DAO DAO;

	@Override
	public void add(Object obj) {
		DAO.add(obj);
	}

	@Override
	public DAO getDAO() {
		return DAO;
	}

	@Override
	public void setDAO(DAO dAO) {
		DAO = dAO;
	}

	@Override
	public void remove(Class<?> clazz, long id) {
		DAO.remove(clazz, id);
	}

	@Override
	public void update(Object obj) {
		DAO.update(obj);
	}

	@Override
	public Object select(Class<?> clazz, long id) {
		return DAO.select(clazz, id);
	}

	@Override
	public void saveOrUpdate(Object obj) {
		DAO.saveOrUpdate(obj);
	}

}
