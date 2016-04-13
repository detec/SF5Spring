package org.openbox.sf5.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openbox.sf5.model.AbstractDbEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DAOImpl implements DAO, Serializable {

	@Override
	public <T extends AbstractDbEntity> void add(T obj) {
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		s.save(obj);
		s.getTransaction().commit();
		s.close();

		// Session s = sessionFactory.getCurrentSession();
		// Transaction tx = s.getTransaction();
		// if (tx == null) {
		// s.beginTransaction();
		// }
		// s.merge(obj);
		//
		// if (tx == null) {
		// s.getTransaction().commit();
		// }
	}

	@Override
	public <T extends AbstractDbEntity> void remove(Class<T> type, long id) {
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		Object c = s.get(type, id);
		s.delete(c);
		s.getTransaction().commit();
		s.close();

		// Session s = sessionFactory.getCurrentSession();
		// s.delete(s.get(type, id));
	}

	@Override
	public <T extends AbstractDbEntity> void update(T obj) {
		// Session s = sessionFactory.openSession();
		// s.beginTransaction();
		// s.update(obj);
		// s.getTransaction().commit();
		// s.close();

		add(obj);
	}

	@Override
	public <T extends AbstractDbEntity> T select(Class<T> type, long id) {

		Session s = sessionFactory.openSession();
		s.beginTransaction();
		@SuppressWarnings("unchecked")
		T obj = s.get(type, id);

		// Session s = sessionFactory.getCurrentSession();
		// T obj = s.get(type, id);

		s.close();
		return obj;
	}

	@Override
	public <T extends AbstractDbEntity> void saveOrUpdate(T obj) {
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		s.saveOrUpdate(obj);
		s.getTransaction().commit();
		s.close();

		// Session s = sessionFactory.getCurrentSession();
		// s.saveOrUpdate(obj);
	}

	@Autowired
	private SessionFactory sessionFactory;

	public DAOImpl() {

	}

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public DAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private static final long serialVersionUID = 643710250463318145L;
}
