package org.openbox.sf5.converters;

import java.beans.PropertyEditorSupport;

import org.openbox.sf5.dao.ObjectsController;
import org.openbox.sf5.model.Transponders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransponderChoiceEditor extends PropertyEditorSupport {

	@Autowired
	private ObjectsController objectsController;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long Lid = Long.parseLong(text);
		Transponders trans = objectsController.select(Transponders.class, Lid.longValue());

		setValue(trans);
	}

	@Override
	public String getAsText() {

		Transponders oldTrans = (Transponders) this.getValue();
		Transponders trans = objectsController.select(Transponders.class, oldTrans.getId());

		return String.valueOf(trans.getFrequency());
	}

}
