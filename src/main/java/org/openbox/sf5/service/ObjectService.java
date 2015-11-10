package org.openbox.sf5.service;

import org.openbox.sf5.dao.DAO;

public interface ObjectService {

	public void add(Object obj);

	public void remove(Class<?> clazz, long id);

	public void update(Object obj);

	public Object select(Class<?> clazz, long id);

	public void saveOrUpdate(Object obj);

	public DAO getDAO();

	public void setDAO(DAO dAO);
}
