package org.openbox.sf5.converters;

import org.openbox.sf5.dao.ObjectsController;
import org.openbox.sf5.model.Transponders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class TransponderChoice extends Transponders {

	@Autowired
	private ObjectsController objectsController;

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

	public ObjectsController getObjectsController() {
		return objectsController;
	}

	public void setObjectsController(ObjectsController objectsController) {
		this.objectsController = objectsController;
	}

	// Spring needs default constructor for components
	public TransponderChoice() {

	}

	public Transponders getTransponder() {
		Transponders trans = objectsController.select(Transponders.class, super.getId());
		return trans;
	}

}
