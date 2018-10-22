package org.openbox.sf5.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.openbox.sf5.model.AbstractDbEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ObjectsController implements Serializable {

	private static final long serialVersionUID = 1419450577630607967L;

	@Autowired
	private ObjectService service;

	@Autowired
	private ObjectsListService listService;

	public <T extends AbstractDbEntity> void add(T obj) {
		service.add(obj);
	}

	public <T extends AbstractDbEntity> void update(T obj) {
		service.update(obj);
	}

	public <T extends AbstractDbEntity> void remove(Class<T> type, long id) {
		service.remove(type, id);
	}

	public <T extends AbstractDbEntity> void saveOrUpdate(T obj) {
		service.saveOrUpdate(obj);
	}

	public <T extends AbstractDbEntity> T select(Class<T> type, long id) {
		return service.select(type, id);
	}

	public ObjectService getService() {
		return service;
	}

	public void setService(ObjectService service) {
		this.service = service;
	}

	public <T extends AbstractDbEntity> List<T> list(Class<T> type) {
		return listService.list(type);
	}

	public <T extends AbstractDbEntity> List<T> restrictionList(Class<T> type, Criterion criterion) {
		return listService.restrictionList(type, criterion);
	}

	public SessionFactory getSessionFactory() {
		return service.getSessionFactory();
	}

	public Session openSession() {
		return service.openSession();
	}

}
