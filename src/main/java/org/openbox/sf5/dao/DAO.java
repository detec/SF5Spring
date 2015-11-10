package org.openbox.sf5.dao;

import org.hibernate.SessionFactory;

public interface DAO {

	public void add(Object obj);

	public void remove(Class<?> clazz, long id);

	public void update(Object obj);

	public Object select(Class<?> clazz, long id);

	public void saveOrUpdate(Object obj);

	public SessionFactory getSessionFactory();

	public void setSessionFactory(SessionFactory sessionFactory);

}
