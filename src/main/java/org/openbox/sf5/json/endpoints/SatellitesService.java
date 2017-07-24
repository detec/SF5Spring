package org.openbox.sf5.json.endpoints;

import java.util.List;
import java.util.Optional;

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

@RestController
@EnableWebMvc
@RequestMapping(value = "${jaxrs.path}/satellites/", produces = { "application/xml", "application/json" })
public class SatellitesService {

    @Autowired
    private ObjectsController objectController;

    @Autowired
    private SatellitesJsonizer jsonizer;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Satellites>> getAllSatellites() {
		List<Satellites> satList = objectController.list(Satellites.class);
        return satList.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(satList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML)
	public ResponseEntity<GenericXMLListWrapper<Satellites>> getAllSatellitesXML()
			throws NoSuchFieldException, SecurityException, NoSuchMethodException {

		List<Satellites> satList = objectController.list(Satellites.class);
        return satList.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : JsonObjectFiller.returnGenericWrapperResponseBySatList(satList, Satellites.class);
	}

	@RequestMapping(value = "{satelliteId}", method = RequestMethod.GET)
	public ResponseEntity<Satellites> getSatelliteById(@PathVariable("satelliteId") long satId) {

        return Optional.ofNullable(objectController.select(Satellites.class, satId))
                .map(sat -> new ResponseEntity<>(sat, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
	}

	@RequestMapping(value = "filter/{type}/{typeValue}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Satellites>> getSatellitesByArbitraryFilter(@PathVariable("type") String fieldName,
			@PathVariable("typeValue") String typeValue) {

		List<Satellites> satList = jsonizer.getSatellitesByArbitraryFilter(fieldName, typeValue);
        return satList.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(satList, HttpStatus.OK);
	}

	@RequestMapping(value = "filter/{type}/{typeValue}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML)
	public ResponseEntity<GenericXMLListWrapper<Satellites>> getSatellitesByArbitraryFilterXML(
			@PathVariable("type") String fieldName, @PathVariable("typeValue") String typeValue) {

		List<Satellites> satList = jsonizer.getSatellitesByArbitraryFilter(fieldName, typeValue);
        return satList.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : JsonObjectFiller.returnGenericWrapperResponseBySatList(satList, Satellites.class);
	}


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
