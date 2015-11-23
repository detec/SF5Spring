package org.openbox.sf5.service;

import java.io.Serializable;

import org.openbox.sf5.model.AbstractDbEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ObjectsController implements Serializable {

	private static final long serialVersionUID = 1419450577630607967L;

	@Autowired
	ObjectService Service;

	public <T extends AbstractDbEntity> void add(T obj) {
		Service.add(obj);
	}

	public <T extends AbstractDbEntity> void update(T obj) {
		Service.update(obj);
	}

	public <T extends AbstractDbEntity> void remove(Class<T> type, long id) {
		Service.remove(type, id);
	}

	public <T extends AbstractDbEntity> void saveOrUpdate(T obj) {
		Service.saveOrUpdate(obj);
	}

	public <T extends AbstractDbEntity> T select(Class<T> type, long id) {
		return Service.select(type, id);
	}

	public ObjectService getService() {
		return Service;
	}

	public void setService(ObjectService service) {
		Service = service;
	}

}
