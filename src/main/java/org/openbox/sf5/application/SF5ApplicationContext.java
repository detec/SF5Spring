package org.openbox.sf5.application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.openbox.sf5.model.SettingsConversionPresentation;
import org.openbox.sf5.model.Transponders;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// this class is intended to store objects between controllers.
@Component
@Scope(value = "session")
public class SF5ApplicationContext implements Serializable {

	private static final long serialVersionUID = -7611964920327488619L;

	public SF5ApplicationContext() {

	}

	private List<Transponders> selectedTransponders = new ArrayList<Transponders>();

	private List<SettingsConversionPresentation> selectedSettingsConversionPresentations = new ArrayList<SettingsConversionPresentation>();

	private SettingsForm curentlyEditedSetting;

	public List<Transponders> getSelectedTransponders() {
		return selectedTransponders;
	}

	public void setSelectedTransponders(List<Transponders> selectedTransponders) {
		this.selectedTransponders = selectedTransponders;
	}

	public List<SettingsConversionPresentation> getSelectedSettingsConversionPresentations() {
		return selectedSettingsConversionPresentations;
	}

	public void setSelectedSettingsConversionPresentations(
			List<SettingsConversionPresentation> selectedSettingsConversionPresentations) {
		this.selectedSettingsConversionPresentations = selectedSettingsConversionPresentations;
	}

	public SettingsForm getCurentlyEditedSetting() {
		return curentlyEditedSetting;
	}

	public void setCurentlyEditedSetting(SettingsForm curentlyEditedSetting) {
		this.curentlyEditedSetting = curentlyEditedSetting;
	}

}
