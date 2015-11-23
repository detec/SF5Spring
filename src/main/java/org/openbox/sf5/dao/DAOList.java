package org.openbox.sf5.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;

public interface DAOList {

	public <T> List<T> list(Class<T> type);

	public <T> List<T> restrictionList(Class<T> type, Criterion criterion);

	public SessionFactory getSessionFactory();

	public void setSessionFactory(SessionFactory sessionFactory);

}
