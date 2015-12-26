package org.openbox.sf5.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.openbox.sf5.model.AbstractDbEntity;

public interface DAOList {

	public <T extends AbstractDbEntity> List<T> list(Class<T> type);

	public <T extends AbstractDbEntity> List<T> restrictionList(Class<T> type, Criterion criterion);

	public SessionFactory getSessionFactory();

	public void setSessionFactory(SessionFactory sessionFactory);

}
