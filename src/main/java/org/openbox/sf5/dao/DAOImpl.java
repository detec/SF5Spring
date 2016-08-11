package org.openbox.sf5.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

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
		Session s = getSessionFactory().openSession();
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
		Session s = getSessionFactory().openSession();
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

		Session s = getSessionFactory().openSession();
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
		Session s = getSessionFactory().openSession();
		s.beginTransaction();
		s.saveOrUpdate(obj);
		s.getTransaction().commit();
		s.close();

		// Session s = sessionFactory.getCurrentSession();
		// s.saveOrUpdate(obj);
	}

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	// @Autowired
	private SessionFactory sessionFactory;

	public DAOImpl() {

	}

	@Override
	public SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			// Session session = entityManager.unwrap(Session.class);
			// sessionFactory = session.getSessionFactory();

			sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);

		}
		return sessionFactory;

	}

	@Override
	public Session openSession() {
		Session session = getSessionFactory().openSession();
		// Session session = entityManager.unwrap(Session.class);
		return session;
	}

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public DAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private EntityManager entityManager;

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	private static final long serialVersionUID = 643710250463318145L;
}
