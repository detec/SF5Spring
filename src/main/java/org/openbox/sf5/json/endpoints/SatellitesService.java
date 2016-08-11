package org.openbox.sf5.json.endpoints;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.openbox.sf5.common.JsonObjectFiller;
import org.openbox.sf5.json.service.SatellitesJsonizer;
import org.openbox.sf5.model.Satellites;
import org.openbox.sf5.model.listwrappers.GenericXMLListWrapper;
import org.openbox.sf5.service.ObjectsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// http://websystique.com/springmvc/spring-mvc-4-restful-web-services-crud-example-resttemplate/
// http://stackoverflow.com/questions/17128463/overriding-requestmapping-on-springmvc-controller
@RestController
@EnableWebMvc
@RequestMapping(value = "${jaxrs.path}/satellites/", produces = { "application/xml", "application/json" })
public class SatellitesService {

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Satellites>> getAllSatellites() {
		List<Satellites> satList = objectController.list(Satellites.class);
		if (satList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(satList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML)
	public ResponseEntity<GenericXMLListWrapper<Satellites>> getAllSatellitesXML()
			throws NoSuchFieldException, SecurityException, NoSuchMethodException {
		List<Satellites> satList = objectController.list(Satellites.class);
		if (satList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);//
		}

		// we should replace XmlElementWrapper name
		// Method method = wrapper.getClass().getMethod("getWrappedList");

		return JsonObjectFiller.returnGenericWrapperResponseBySatList(satList, Satellites.class);
	}

	@RequestMapping(value = "{satelliteId}", method = RequestMethod.GET)
	public ResponseEntity<Satellites> getSatelliteById(@PathVariable("satelliteId") long satId) {
		Satellites sat = objectController.select(Satellites.class, satId);
		if (sat == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(sat, HttpStatus.OK);
	}

	@RequestMapping(value = "filter/{type}/{typeValue}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Satellites>> getSatellitesByArbitraryFilter(@PathVariable("type") String fieldName,
			@PathVariable("typeValue") String typeValue) {

		List<Satellites> satList = jsonizer.getSatellitesByArbitraryFilter(fieldName, typeValue);
		if (satList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(satList, HttpStatus.OK);
	}

	@RequestMapping(value = "filter/{type}/{typeValue}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML)
	public ResponseEntity<GenericXMLListWrapper<Satellites>> getSatellitesByArbitraryFilterXML(
			@PathVariable("type") String fieldName, @PathVariable("typeValue") String typeValue) {
		List<Satellites> satList = jsonizer.getSatellitesByArbitraryFilter(fieldName, typeValue);

		if (satList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);//
		}

		return JsonObjectFiller.returnGenericWrapperResponseBySatList(satList, Satellites.class);

	}

	@Autowired
	private ObjectsController objectController;

	// http://www.mkyong.com/spring3/spring-value-default-value/

	@Autowired
	private SatellitesJsonizer jsonizer;

	public ObjectsController getObjectController() {
		return objectController;
	}

	public void setObjectController(ObjectsController objectController) {
		this.objectController = objectController;
	}

	public SatellitesJsonizer getJsonizer() {
		return jsonizer;
	}

	public void setJsonizer(SatellitesJsonizer jsonizer) {
		this.jsonizer = jsonizer;
	}

}
