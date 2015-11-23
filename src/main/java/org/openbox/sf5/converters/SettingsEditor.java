package org.openbox.sf5.converters;

import java.beans.PropertyEditorSupport;

import org.openbox.sf5.model.Settings;
import org.openbox.sf5.service.ObjectsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsEditor extends PropertyEditorSupport {

	@Autowired
	private ObjectsController contr;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		Long Lid = Long.parseLong(text);
		Settings trans = contr.select(Settings.class, Lid.longValue());

		setValue(trans);

	}
}
