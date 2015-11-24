package org.openbox.sf5.json.endpoints;

import java.util.List;

import org.openbox.sf5.model.Transponders;
import org.openbox.sf5.service.ObjectsController;
import org.openbox.sf5.service.ObjectsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/json/transponders/")
public class TranspondersService {

	@RequestMapping(value = "all/", method = RequestMethod.GET)
	public ResponseEntity<List<Transponders>> getTransponders() {
		List<Transponders> transList = listService.ObjectsList(Transponders.class);
		if (transList.isEmpty()) {
			return new ResponseEntity<List<Transponders>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Transponders>>(transList, HttpStatus.OK);
	}

	@Autowired
	private ObjectsController objectController;

	@Autowired
	private ObjectsListService listService;
}
