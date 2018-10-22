package org.openbox.sf5.converters;

import java.beans.PropertyEditorSupport;

import org.openbox.sf5.dao.ObjectsController;
import org.openbox.sf5.model.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsEditor extends PropertyEditorSupport {

	@Autowired
	private ObjectsController objectsController;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		Long Lid = Long.parseLong(text);
		Settings trans = objectsController.select(Settings.class, Lid.longValue());

		setValue(trans);

	}
}
