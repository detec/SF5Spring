package org.openbox.sf5.converters;

import java.beans.PropertyEditorSupport;

import org.openbox.sf5.db.Transponders;
import org.openbox.sf5.service.ObjectsController;

public class TransponderChoiceEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		ObjectsController contr = new ObjectsController();
		Long Lid = Long.parseLong(text);
		Transponders trans = (Transponders) contr.select(Transponders.class,
				Lid.longValue());

		setValue(trans);
	}

}
