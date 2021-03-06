package org.openbox.sf5.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openbox.sf5.model.AbstractDbEntity;

public interface DAO {

	public <T extends AbstractDbEntity> void add(T obj);

	public <T extends AbstractDbEntity> void remove(Class<T> type, long id);

	public <T extends AbstractDbEntity> void update(T obj);

	public <T extends AbstractDbEntity> T select(Class<T> type, long id);

	public <T extends AbstractDbEntity> void saveOrUpdate(T obj);

	public void setSessionFactory(SessionFactory sessionFactory);

	public SessionFactory getSessionFactory();

	public Session openSession();

}
