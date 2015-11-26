package org.openbox.sf5.json.service;

import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.openbox.sf5.dao.DAO;
import org.openbox.sf5.dao.DAOImpl;
import org.openbox.sf5.dao.DAOList;
import org.openbox.sf5.dao.DAOListImpl;
import org.openbox.sf5.model.AbstractDbEntity;
import org.openbox.sf5.service.ObjectService;
import org.openbox.sf5.service.ObjectServiceImpl;
import org.openbox.sf5.service.ObjectsController;
import org.openbox.sf5.service.ObjectsListService;
import org.reflections.Reflections;

public abstract class AbstractJsonizerTest {

	public DAO DAO;

	public DAOList DAOList;

	public ObjectService Service;

	public ObjectsController objectController;

	public ObjectsListService listService;

	public SessionFactory sessionFactory;

	public void setUpAbstract() {

		disableLogsWhenTesting();

		Configuration configuration = new Configuration().configure();

		Set<Class<? extends AbstractDbEntity>> annotatedSet = getAllSubclassesAbstractDbEntity();

		// adding classes as annotated.
		annotatedSet.stream().forEach(t -> configuration.addAnnotatedClass(t));

		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		sessionFactory = configuration.buildSessionFactory(builder.build());

		sessionFactory.openSession();

		DAO = new DAOImpl();
		DAO.setSessionFactory(sessionFactory);

		Service = new ObjectServiceImpl();
		Service.setDAO(DAO);

		objectController = new ObjectsController();
		objectController.setService(Service);

		DAOList = new DAOListImpl();
		DAOList.setSessionFactory(sessionFactory);

		listService = new ObjectsListService();
		listService.setDao(DAOList);

	}

	public static Set<Class<? extends AbstractDbEntity>> getAllSubclassesAbstractDbEntity() {
		Reflections reflections = new Reflections("org.openbox.sf5");

		Set<Class<? extends AbstractDbEntity>> subTypes = reflections.getSubTypesOf(AbstractDbEntity.class);
		return subTypes;

	}

	public void disableLogsWhenTesting() {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.OFF);

	}
}
