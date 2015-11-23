package org.openbox.sf5.converters;

import java.beans.PropertyEditorSupport;

import org.openbox.sf5.model.Transponders;
import org.openbox.sf5.service.ObjectsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransponderChoiceEditor extends PropertyEditorSupport {

	@Autowired
	private ObjectsController contr;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		// ObjectsController contr = new ObjectsController();
		Long Lid = Long.parseLong(text);
		Transponders trans = (Transponders) contr.select(Transponders.class, Lid.longValue());

		setValue(trans);
	}

	@Override
	public String getAsText() {
		// ObjectsController contr = new ObjectsController();
		Transponders oldTrans = (Transponders) this.getValue();
		Transponders trans = (Transponders) contr.select(Transponders.class, oldTrans.getId());

		return String.valueOf(trans.getFrequency());
	}

}
