package org.openbox.sf5.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.openbox.sf5.model.AbstractDbEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DAOListImpl implements DAOList, Serializable {

	private static final long serialVersionUID = 9132749811478277495L;
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractDbEntity> List<T> list(Class<T> type) {

		List<T> list = new ArrayList<>();

		Session s = sessionFactory.openSession();
		s.beginTransaction();
		list = s.createQuery("from " + type.getName() + " order by id").list();
		s.getTransaction().commit();
		s.close();
		return list;
	}

	@Override
	public <T extends AbstractDbEntity> List<T> restrictionList(Class<T> type, Criterion criterion) {
		Session s = sessionFactory.openSession();
		Criteria criteria = s.createCriteria(type)

				.add(criterion)

				.addOrder(Order.desc("id"));
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
