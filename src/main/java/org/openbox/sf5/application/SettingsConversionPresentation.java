package org.openbox.sf5.application;

import org.openbox.sf5.model.Settings;
import org.openbox.sf5.model.SettingsConversion;

// http://stackoverflow.com/questions/21988598/how-to-get-selected-tablecell-in-javafx-tableview
// If you need data from multiple sources in single table, it is better to
// make a new class that aggregates all the data and use that as a TableView
// source.
public class SettingsConversionPresentation extends SettingsConversion {

	/**
	 *
	 */
	private static final long serialVersionUID = 7708887469121053073L;

	private transient boolean editable;

	private transient boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public SettingsConversionPresentation(Settings object) {
		// TODO Auto-generated constructor stub
		super.setparent_id(object);

		// Note shouldn't be null
		super.setNote("");
	}

	// we have to manually construct presentation class from super
	public SettingsConversionPresentation(SettingsConversion superItem) {
		super(superItem);

	}

	public SettingsConversionPresentation(
			SettingsConversionPresentation original, Settings parent) {

		this.setTransponder(original.getTransponder());
		this.setparent_id(parent);
		this.setNote(original.getNote());

	}

	// Spring needs default constructor
	public SettingsConversionPresentation() {

	}

}
