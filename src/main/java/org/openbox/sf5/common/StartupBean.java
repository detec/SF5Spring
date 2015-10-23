package org.openbox.sf5.common;

import javax.annotation.PostConstruct;

// doesn't work with Eclipse interactive publishment
//@Singleton
//@Startup
public class StartupBean {

	@PostConstruct
	private void startup() {
		// start TableFiller
		TableFiller filler = new TableFiller();
	}

}
