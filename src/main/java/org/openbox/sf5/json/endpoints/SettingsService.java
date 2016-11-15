package org.openbox.sf5.json.endpoints;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.xml.transform.stream.StreamResult;

import org.openbox.sf5.common.JsonObjectFiller;
import org.openbox.sf5.common.SF5SecurityContext;
import org.openbox.sf5.common.XMLExporter;
import org.openbox.sf5.json.exceptions.NotAuthenticatedException;
import org.openbox.sf5.json.exceptions.UsersDoNotCoincideException;
import org.openbox.sf5.json.service.SettingsJsonizer;
import org.openbox.sf5.model.Sat;
import org.openbox.sf5.model.Settings;
import org.openbox.sf5.model.SettingsConversion;
import org.openbox.sf5.model.Users;
import org.openbox.sf5.model.listwrappers.GenericXMLListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "${jaxrs.path}/usersettings/")
// @PreAuthorize("hasRole('USER')")
public class SettingsService {

	private static final String CONSTANT_COULDNT_GET_USER = "Couldn't get currently authenticated user!";

	@Value("${jaxrs.path}")
	private String jaxRSPath;

	@Autowired
	private Jaxb2Marshaller springMarshaller;

	@Autowired
	private SF5SecurityContext securityContext;

	@Autowired
	private SettingsJsonizer settingsJsonizer;

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Long> createSetting(@RequestBody Settings setting, UriComponentsBuilder ucBuilder)
			throws NotAuthenticatedException, UsersDoNotCoincideException {

		Users currentUser = getVerifyAuthenticatedUser();

		if (!currentUser.equals(setting.getUser())) {
			// authenticated user and setting user do not coincide.
			throw new UsersDoNotCoincideException("Authenticated user " + currentUser.getId()
					+ " and the one in setting - " + setting.getUser().getId() + " do not coincide!");
		}

		try {
			HttpStatus statusResult = settingsJsonizer.saveNewSetting(setting);
		} catch (Exception e) {
			throw new IllegalStateException("Error when saving new setting", e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("SettingId", Long.toString(setting.getId()));
		// it can be called out of JAX-WS
		try {
			headers.setLocation(ucBuilder.path("/" + jaxRSPath + "/").path("usersettings/filter/id/{id}")
					.buildAndExpand(setting.getId()).toUri());
		} catch (Exception e) {

		}
		return new ResponseEntity<>(new Long(setting.getId()), headers, HttpStatus.CREATED);
	}

	// @PreAuthorize("hasAuthority('ROLE_USER')")
	@PreAuthorize("hasRole('ROLE_USER')")
	@Secured("ROLE_USER")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Settings>> getSettingsByUserLogin() throws NotAuthenticatedException {

		Users currentUser = getVerifyAuthenticatedUser();

		List<Settings> settList = settingsJsonizer.getSettingsByUser(currentUser);
		if (settList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(settList, HttpStatus.OK);

	}

	// @PreAuthorize("hasAuthority('ROLE_USER')")
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML)
	public ResponseEntity<GenericXMLListWrapper<Settings>> getSettingsByUserLoginXML()
			throws NotAuthenticatedException {

		Users currentUser = getVerifyAuthenticatedUser();

		List<Settings> settList = settingsJsonizer.getSettingsByUser(currentUser);
		if (settList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return JsonObjectFiller.returnGenericWrapperResponseBySatList(settList, Settings.class);
	}

	// http://community.hpe.com/t5/Software-Developers/A-Comprehensive-Example-of-a-Spring-MVC-Application-Part-3/ba-p/6135449

	// @PreAuthorize("hasAuthority('ROLE_USER')")
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "filter/{type}/{typeValue}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Settings>> getSettingsByArbitraryFilter(@PathVariable("type") String fieldName,
			@PathVariable("typeValue") String typeValue) throws NotAuthenticatedException {

		List<Settings> settList = new ArrayList<>();

		Users currentUser = getVerifyAuthenticatedUser();

		settList = settingsJsonizer.getSettingsByArbitraryFilter(fieldName, typeValue, currentUser);
		if (settList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(settList, HttpStatus.OK);

	}

	// @PreAuthorize("hasAuthority('ROLE_USER')")
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "filter/{type}/{typeValue}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML)
	public ResponseEntity<GenericXMLListWrapper<Settings>> getSettingsByArbitraryFilterXML(
			@PathVariable("type") String fieldName, @PathVariable("typeValue") String typeValue)
			throws NotAuthenticatedException {

		List<Settings> settList = new ArrayList<>();

		Users currentUser = getVerifyAuthenticatedUser();

		settList = settingsJsonizer.getSettingsByArbitraryFilter(fieldName, typeValue, currentUser);
		if (settList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return JsonObjectFiller.returnGenericWrapperResponseBySatList(settList, Settings.class);
	}

	// @PreAuthorize("hasAuthority('ROLE_USER')")
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "{settingId}", method = RequestMethod.GET)
	public ResponseEntity<Settings> getSettingById(@PathVariable("settingId") long settingId)
			throws NotAuthenticatedException {

		Users currentUser = getVerifyAuthenticatedUser();

		Settings setting = settingsJsonizer.getSettingById(settingId, currentUser);
		if (setting == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(setting, HttpStatus.OK);

	}

	// @PreAuthorize("hasAuthority('ROLE_USER')")
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "{settingId}/sf5", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN // This
																											// removes
																											// <String>
																											// tags

	)
	public ResponseEntity<String> getSettingByIdSF5(@PathVariable("settingId") long settingId)
			throws NotAuthenticatedException {

		Users currentUser = getVerifyAuthenticatedUser();

		Settings setting = settingsJsonizer.getSettingById(settingId, currentUser);
		if (setting == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		StringWriter sw = new StringWriter();

		List<SettingsConversion> conversionLines = setting.getConversion();
		Sat sat = XMLExporter.exportSettingsConversionToSF5Format(conversionLines);

		// There is no easy way of using different marshallers
		// marshalling sat
		springMarshaller.marshal(sat, new StreamResult(sw));

		// return new ResponseEntity<String>(sw.toString(), HttpStatus.OK);
		return new ResponseEntity<>(sw.toString(), HttpStatus.OK);

		// Cannot fix <String>&lt;sat> and so on
		// return sw.toString();

		// uses Jackson, unfortunately
		// return new ResponseEntity<Sat>(sat, HttpStatus.OK);

		// http://stackoverflow.com/questions/26982466/spring-mvc-response-body-xml-has-extra-string-tags-why
		// useful link
	}

	private Users getVerifyAuthenticatedUser() throws NotAuthenticatedException {
		Users currentUser = securityContext.getCurrentlyAuthenticatedUser();
		if (currentUser == null) {

			throw new NotAuthenticatedException(CONSTANT_COULDNT_GET_USER);
		}

		return currentUser;

	}

	public SettingsJsonizer getSettingsJsonizer() {
		return settingsJsonizer;
	}

	public void setSettingsJsonizer(SettingsJsonizer settingsJsonizer) {
		this.settingsJsonizer = settingsJsonizer;
	}

}
