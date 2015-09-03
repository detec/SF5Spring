package org.openbox.sf5.application;

import java.util.ArrayList;
import java.util.List;

import org.openbox.sf5.db.Settings;
import org.openbox.sf5.db.Transponders;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

// this class is intended to store objects between controllers.
@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class SF5ApplicationContext {

	public SF5ApplicationContext () {

	}

	private List<Transponders> selectedTransponders = new ArrayList<Transponders>();

	private List<SettingsConversionPresentation> selectedSettingsConversionPresentations = new ArrayList<SettingsConversionPresentation>();

	private Settings curentlyEditedSetting;

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

	public Settings getCurentlyEditedSetting() {
		return curentlyEditedSetting;
	}

	public void setCurentlyEditedSetting(Settings curentlyEditedSetting) {
		this.curentlyEditedSetting = curentlyEditedSetting;
	}

}
