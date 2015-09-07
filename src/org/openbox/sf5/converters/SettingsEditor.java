package org.openbox.sf5.converters;

import java.beans.PropertyEditorSupport;

import org.openbox.sf5.db.Settings;
import org.openbox.sf5.service.ObjectsController;

public class SettingsEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		ObjectsController contr = new ObjectsController();
		Long Lid = Long.parseLong(text);
		Settings trans = (Settings) contr.select(Settings.class,
				Lid.longValue());

		setValue(trans);

	}
}
