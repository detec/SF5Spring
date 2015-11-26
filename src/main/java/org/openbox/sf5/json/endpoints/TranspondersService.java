package org.openbox.sf5.json.endpoints;

import java.util.List;

import org.openbox.sf5.json.service.TranspondersJsonizer;
import org.openbox.sf5.model.Transponders;
import org.openbox.sf5.service.ObjectsController;
import org.openbox.sf5.service.ObjectsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
@RequestMapping(value = "/json/transponders/", headers = "Accept=*/*", produces = "application/json")
public class TranspondersService {

	@RequestMapping(value = "filter/{type}/{typeValue}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Transponders>> getTranspondersByArbitraryFilter(@PathVariable("type") String fieldName,
			@PathVariable("typeValue") String typeValue) {
		List<Transponders> transList = transpondersJsonizer.getTranspondersByArbitraryFilter(fieldName, typeValue);
		if (transList.isEmpty()) {
			return new ResponseEntity<List<Transponders>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Transponders>>(transList, HttpStatus.OK);
	}

	@RequestMapping(value = "filter/id/{transponderId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Transponders> getTransponderById(@PathVariable("transponderId") long tpId) {
		Transponders trans = objectController.select(Transponders.class, tpId);
		if (trans == null) {
			return new ResponseEntity<Transponders>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Transponders>(trans, HttpStatus.OK);

	}

	@RequestMapping(value = "/{filter}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Transponders>> getTranspondersBySatelliteId(@PathVariable("filter") String ignore,
			@MatrixVariable(required = true, value = "satId") long satId) {

		List<Transponders> transList = transpondersJsonizer.getTranspondersBySatelliteId(satId);
		if (transList.isEmpty()) {
			return new ResponseEntity<List<Transponders>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Transponders>>(transList, HttpStatus.OK);
	}

	@RequestMapping(value = "all/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Transponders>> getTransponders() {
		List<Transponders> transList = listService.ObjectsList(Transponders.class);
		if (transList.isEmpty()) {
			return new ResponseEntity<List<Transponders>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Transponders>>(transList, HttpStatus.OK);
	}

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

	public TranspondersJsonizer getTranspondersJsonizer() {
		return transpondersJsonizer;
	}

	public void setTranspondersJsonizer(TranspondersJsonizer transpondersJsonizer) {
		this.transpondersJsonizer = transpondersJsonizer;
	}

	@Autowired
	private TranspondersJsonizer transpondersJsonizer;

	@Autowired
	private ObjectsController objectController;

	@Autowired
	private ObjectsListService listService;
}
