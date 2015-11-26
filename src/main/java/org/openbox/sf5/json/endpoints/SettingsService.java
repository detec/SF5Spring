package org.openbox.sf5.json.endpoints;

import java.util.List;

import javax.ws.rs.PathParam;

import org.openbox.sf5.json.service.SettingsJsonizer;
import org.openbox.sf5.model.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@RestController
@RequestMapping(value = "/json/usersettings/", headers = "Accept=*/*", produces = "application/json")
public class SettingsService {

	@RequestMapping(value = "filter/login/{typeValue}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Settings>> getSettingsByUserLogin(@PathParam("typeValue") String typeValue) {
		List<Settings> settList = settingsJsonizer.getSettingsByUserLogin(typeValue);
		if (settList.isEmpty()) {
			return new ResponseEntity<List<Settings>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Settings>>(settList, HttpStatus.OK);
	}

	@RequestMapping(value = "filter/{type}/{typeValue}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Settings>> getSettingsByArbitraryFilter(@PathVariable("type") String fieldName,
			@PathVariable("typeValue") String typeValue, @MatrixVariable("login") String login) {

		List<Settings> settList = settingsJsonizer.getSettingsByArbitraryFilter(fieldName, typeValue, login);
		if (settList.isEmpty()) {
			return new ResponseEntity<List<Settings>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Settings>>(settList, HttpStatus.OK);

	}

	@RequestMapping(value = "filter/id/{settingId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Settings> getSettingById(@PathVariable("settingId") long settingId,
			@MatrixVariable("login") String login) {

		Settings setting = settingsJsonizer.getSettingById(settingId, login);
		if (setting == null) {
			return new ResponseEntity<Settings>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Settings>(setting, HttpStatus.OK);

	}

	@Autowired
	private SettingsJsonizer settingsJsonizer;

	public SettingsJsonizer getSettingsJsonizer() {
		return settingsJsonizer;
	}

	public void setSettingsJsonizer(SettingsJsonizer settingsJsonizer) {
		this.settingsJsonizer = settingsJsonizer;
	}

}
