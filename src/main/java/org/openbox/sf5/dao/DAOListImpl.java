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

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> list(Class<T> type) {

		List<T> list = new ArrayList<>();

		Session s = sessionFactory.openSession();
		s.beginTransaction();
		list = s.createQuery("from " + type.getName()).list();
		s.getTransaction().commit();
		s.close();
		return list;
	}

	@Override
	public <T> List<T> restrictionList(Class<T> type, Criterion criterion) {
		Session s = sessionFactory.openSession();
		Criteria criteria = s.createCriteria(type).add(criterion);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY); // kill
																					// duplicates

		@SuppressWarnings("unchecked")
		List<T> list = criteria.list();

		s.close();
		return list;
	}

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
