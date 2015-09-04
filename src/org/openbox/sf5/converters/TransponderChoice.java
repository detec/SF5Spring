package org.openbox.sf5.converters;

import org.openbox.sf5.db.Transponders;

public class TransponderChoice extends Transponders {

	/**
	 *
	 */
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

	// Spring needs default constructor for components
	public TransponderChoice() {

	}

}
