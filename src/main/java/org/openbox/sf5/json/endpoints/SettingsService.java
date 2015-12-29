package org.openbox.sf5.json.endpoints;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.openbox.sf5.common.JsonObjectFiller;
import org.openbox.sf5.common.SF5SecurityContext;
import org.openbox.sf5.json.service.SettingsJsonizer;
import org.openbox.sf5.model.Settings;
import org.openbox.sf5.model.Users;
import org.openbox.sf5.model.listwrappers.GenericXMLListWrapper;
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
// @PreAuthorize("hasRole('ROLE_USER')")
// Be careful not to use annotations produces, consumes - it kicks away
// requests.
@RequestMapping(value = "/jaxrs/usersettings/")
public class SettingsService {

	// http://websystique.com/springmvc/spring-mvc-4-restful-web-services-crud-example-resttemplate/

	// !!!!!! Be careful with annotations in RequestMapping Consumes, Produces
	// !!! For ResponseEntity<List<T>>
	// it should be empty or produces = "application/json".

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public ResponseEntity<Void> createSetting(@RequestBody Settings setting, UriComponentsBuilder ucBuilder) {

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
		headers.add("SettingId", Long.toString(setting.getId()));
		headers.setLocation(
				ucBuilder.path("/json/usersettings/filter/id/{id}").buildAndExpand(setting.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Settings>> getSettingsByUserLogin() {

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
	@RequestMapping(value = "all", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML)
	public ResponseEntity<GenericXMLListWrapper<Settings>> getSettingsByUserLoginXML() {

		Users currentUser = securityContext.getCurrentlyAuthenticatedUser();
		if (currentUser == null) {
			return new ResponseEntity<GenericXMLListWrapper<Settings>>(HttpStatus.UNAUTHORIZED);
		}

		List<Settings> settList = settingsJsonizer.getSettingsByUser(currentUser);
		if (settList.isEmpty()) {
			return new ResponseEntity<GenericXMLListWrapper<Settings>>(HttpStatus.NO_CONTENT);
		}

		return JsonObjectFiller.returnGenericWrapperResponseBySatList(settList, Settings.class);
	}

	// http://community.hpe.com/t5/Software-Developers/A-Comprehensive-Example-of-a-Spring-MVC-Application-Part-3/ba-p/6135449

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "filter/{type}/{typeValue}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Settings>> getSettingsByArbitraryFilter(@PathVariable("type") String fieldName,
			@PathVariable("typeValue") String typeValue) {

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
	@RequestMapping(value = "filter/{type}/{typeValue}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML)
	public ResponseEntity<GenericXMLListWrapper<Settings>> getSettingsByArbitraryFilterXML(
			@PathVariable("type") String fieldName, @PathVariable("typeValue") String typeValue) {

		List<Settings> settList = new ArrayList<>();
		Users currentUser = securityContext.getCurrentlyAuthenticatedUser();
		if (currentUser == null) {
			return new ResponseEntity<GenericXMLListWrapper<Settings>>(HttpStatus.UNAUTHORIZED);
		}

		settList = settingsJsonizer.getSettingsByArbitraryFilter(fieldName, typeValue, currentUser);
		if (settList.isEmpty()) {
			return new ResponseEntity<GenericXMLListWrapper<Settings>>(HttpStatus.NO_CONTENT);
		}

		return JsonObjectFiller.returnGenericWrapperResponseBySatList(settList, Settings.class);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "filter/id/{settingId}", method = RequestMethod.GET)
	public ResponseEntity<Settings> getSettingById(@PathVariable("settingId") long settingId) {

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
