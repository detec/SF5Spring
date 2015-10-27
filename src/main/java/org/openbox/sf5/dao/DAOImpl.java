package org.openbox.sf5.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DAOImpl implements DAO {

	@Autowired
	private SessionFactory sessionFactory;

	public DAOImpl() {

	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public DAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void add(Object obj) {
		Session s = sessionFactory.openSession();

		s.beginTransaction();
		s.save(obj);
		s.getTransaction().commit();
		s.close();

	}

	@Override
	public void remove(Class<?> clazz, long id) {
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		Object c = s.get(clazz, id);
		s.delete(c);
		s.getTransaction().commit();
		s.close();
	}

	@Override
	public void update(Object obj) {
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		s.update(obj);
		s.getTransaction().commit();
		s.close();
	}

	@Override
	public Object select(Class<?> clazz, long id) {
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		Object obj = s.get(clazz, id);
		s.close();
		return obj;
	}

	@Override
	public void saveOrUpdate(Object obj) {
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		s.saveOrUpdate(obj);
		s.getTransaction().commit();
		s.close();
	}
}
