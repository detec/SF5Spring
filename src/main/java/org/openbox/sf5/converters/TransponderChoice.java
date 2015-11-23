package org.openbox.sf5.converters;

import org.openbox.sf5.model.Transponders;
import org.openbox.sf5.service.ObjectsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class TransponderChoice extends Transponders {

	@Autowired
	private ObjectsController contr;

	private static final long serialVersionUID = 3262084796351763445L;

	private boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public TransponderChoice(Transponders transponder) {
		super(transponder);
		checked = false;
	}

	public ObjectsController getContr() {
		return contr;
	}

	public void setContr(ObjectsController contr) {
		this.contr = contr;
	}

	// Spring needs default constructor for components
	public TransponderChoice() {

	}

	public Transponders getTransponder() {
		Transponders trans = contr.select(Transponders.class, super.getId());
		return trans;
	}

}
