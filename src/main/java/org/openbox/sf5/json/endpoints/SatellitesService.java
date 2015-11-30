package org.openbox.sf5.json.endpoints;

import java.util.List;

import org.openbox.sf5.json.service.SatellitesJsonizer;
import org.openbox.sf5.model.Satellites;
import org.openbox.sf5.service.ObjectsController;
import org.openbox.sf5.service.ObjectsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// http://websystique.com/springmvc/spring-mvc-4-restful-web-services-crud-example-resttemplate/
@RestController
@EnableWebMvc
@RequestMapping(value = "/json/satellites/", headers = "Accept=*/*", produces = "application/json")
public class SatellitesService {

	@RequestMapping(value = "all/", method = RequestMethod.GET, produces = "application/json") // ,
																								// headers
																								// =
	// "Accept=application/xml,
	// application/json")
	public ResponseEntity<List<Satellites>> getAllSatellites() {
		List<Satellites> satList = listService.ObjectsList(Satellites.class);
		if (satList.isEmpty()) {
			return new ResponseEntity<List<Satellites>>(HttpStatus.NO_CONTENT);//
		}

		return new ResponseEntity<List<Satellites>>(satList, HttpStatus.OK);
	}

	@RequestMapping(value = "filter/id/{satelliteId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Satellites> getSatelliteById(@PathVariable("satelliteId") long satId) {
		Satellites sat = objectController.select(Satellites.class, satId);
		if (sat == null) {
			return new ResponseEntity<Satellites>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Satellites>(sat, HttpStatus.OK);
	}

	@RequestMapping(value = "filter/{type}/{typeValue}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Satellites>> getSatellitesByArbitraryFilter(@PathVariable("type") String fieldName,
			@PathVariable("typeValue") String typeValue) {

		List<Satellites> satList = jsonizer.getSatellitesByArbitraryFilter(fieldName, typeValue);
		if (satList.isEmpty()) {
			return new ResponseEntity<List<Satellites>>(HttpStatus.NO_CONTENT);//
		}

		return new ResponseEntity<List<Satellites>>(satList, HttpStatus.OK);
	}

	@Autowired
	private ObjectsController objectController;

	@Autowired
	private ObjectsListService listService;

	@Autowired
	private SatellitesJsonizer jsonizer;

	public ObjectsController getObjectController() {
		return objectController;
	}

	public void setObjectController(ObjectsController objectController) {
		this.objectController = objectController;
	}

	public ObjectsListService getListService() {
		return listService;
	}

	public void setListService(ObjectsListService listService) {
		this.listService = listService;
	}

	public SatellitesJsonizer getJsonizer() {
		return jsonizer;
	}

	public void setJsonizer(SatellitesJsonizer jsonizer) {
		this.jsonizer = jsonizer;
	}

}
