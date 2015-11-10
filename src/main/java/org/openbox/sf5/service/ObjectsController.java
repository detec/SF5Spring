package org.openbox.sf5.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ObjectsController implements Serializable{

	private static final long serialVersionUID = 1419450577630607967L;
	@Autowired
	ObjectService Service;

	public void add(Object obj) {
		Service.add(obj);
	}

	public void update(Object obj) {
		Service.update(obj);
	}

	public void remove(Class<?> clazz, long id) {
		Service.remove(clazz, id);
	}

	public void saveOrUpdate(Object obj) {
		Service.saveOrUpdate(obj);
	}

	public Object select(Class<?> clazz, long id) {
		return Service.select(clazz, id);
	}

}
