package org.openbox.sf5.json.endpoints;

import java.util.ArrayList;
import java.util.List;

import org.openbox.sf5.common.SF5SecurityContext;
import org.openbox.sf5.json.service.SettingsJsonizer;
import org.openbox.sf5.model.Settings;
import org.openbox.sf5.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.util.UriComponentsBuilder;

@EnableWebMvc
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping(value = "/json/usersettings/", headers = "Accept=*/*", produces = "application/json", consumes = "application/json")
public class SettingsService {

	// http://websystique.com/springmvc/spring-mvc-4-restful-web-services-crud-example-resttemplate/

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public ResponseEntity<Void> createSetting(@RequestBody Settings setting, UriComponentsBuilder ucBuilder) {
		// System.out.println("Create setting called");

		Users currentUser = securityContext.getCurrentlyAuthenticatedUser();

		if (currentUser == null) {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}

		if (!currentUser.equals(setting.getUser())) {
			// authenticated user and setting user do not coincide.
			return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		}
		HttpStatus statusResult = settingsJsonizer.saveNewSetting(setting);
		if (statusResult.equals(HttpStatus.CONFLICT)) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Usersettings", "created");
		headers.setLocation(ucBuilder.path("/filter/id/{id}").buildAndExpand(setting.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Settings>> getSettingsByUserLogin() {
		System.out.println("Request all user settings called");

		Users currentUser = securityContext.getCurrentlyAuthenticatedUser();
		if (currentUser == null) {
			return new ResponseEntity<List<Settings>>(HttpStatus.UNAUTHORIZED);
		}

		List<Settings> settList = settingsJsonizer.getSettingsByUser(currentUser);
		if (settList.isEmpty()) {
			return new ResponseEntity<List<Settings>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Settings>>(settList, HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "filter/{type}/{typeValue}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Settings>> getSettingsByArbitraryFilter(@PathVariable("type") String fieldName,
			@PathVariable("typeValue") String typeValue) {

		System.out.println("Request all user settings by arbitrary filter called");
		List<Settings> settList = new ArrayList<>();
		Users currentUser = securityContext.getCurrentlyAuthenticatedUser();
		if (currentUser == null) {
			return new ResponseEntity<List<Settings>>(HttpStatus.UNAUTHORIZED);
		}

		settList = settingsJsonizer.getSettingsByArbitraryFilter(fieldName, typeValue, currentUser);
		if (settList.isEmpty()) {
			return new ResponseEntity<List<Settings>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Settings>>(settList, HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "filter/id/{settingId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Settings> getSettingById(@PathVariable("settingId") long settingId) {
		System.out.println("Request user settings by id");

		Users currentUser = securityContext.getCurrentlyAuthenticatedUser();
		if (currentUser == null) {
			return new ResponseEntity<Settings>(HttpStatus.UNAUTHORIZED);
		}

		Settings setting = settingsJsonizer.getSettingById(settingId, currentUser);
		if (setting == null) {
			return new ResponseEntity<Settings>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Settings>(setting, HttpStatus.OK);

	}

	@Autowired
	private SF5SecurityContext securityContext;

	@Autowired
	private SettingsJsonizer settingsJsonizer;

	public SettingsJsonizer getSettingsJsonizer() {
		return settingsJsonizer;
	}

	public void setSettingsJsonizer(SettingsJsonizer settingsJsonizer) {
		this.settingsJsonizer = settingsJsonizer;
	}

}
