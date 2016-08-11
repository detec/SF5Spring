package org.openbox.sf5.json.service;

import org.openbox.sf5.service.CriterionService;
import org.openbox.sf5.service.ObjectsController;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractJsonizerTest {

	@Autowired
	public ObjectsController objectController;

	@Autowired
	public CriterionService criterionService;

	public void setUpAbstract() {
		disableLogsWhenTesting();
	}

	public void disableLogsWhenTesting() {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.OFF);

	}
}
