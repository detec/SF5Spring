package org.openbox.sf5.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DAOListImpl implements DAOList {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<?> list(Class<?> clazz) {

		List<?> list = new ArrayList<>();

		Session s = sessionFactory.openSession();
		s.beginTransaction();
		list = s.createQuery("from " + clazz.getName()).list();
		s.getTransaction().commit();
		s.close();
		return list;
	}

	@Override
	public List<?> restrictionList(Class<?> clazz, Criterion criterion) {
		Session s = sessionFactory.openSession();
		Criteria criteria = s.createCriteria(clazz).add(criterion);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY); // kill
																					// duplicates

		List<?> list = criteria.list();

		s.close();
		return list;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
