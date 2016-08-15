package org.openbox.sf5.json.endpoints;

import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.openbox.sf5.common.JsonObjectFiller;
import org.openbox.sf5.json.service.FileBucket;
import org.openbox.sf5.json.service.TranspondersJsonizer;
import org.openbox.sf5.model.Transponders;
import org.openbox.sf5.model.listwrappers.GenericXMLListWrapper;
import org.openbox.sf5.service.ObjectsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
@RequestMapping("${jaxrs.path}/transponders/")
@MultipartConfig(fileSizeThreshold = 5242880, maxFileSize = 5242880, // 5
		// MB
		maxRequestSize = 20971520) // 20 MB
public class TranspondersService {

	private static final String filterTypePattern = "filter/{type}/{typeValue}";

	private static final String filterSatIdPattern = "satId/{satId}";

	@RequestMapping(method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public ResponseEntity<Boolean> uploadTransponders(

			// @RequestParam("file") MultipartFile file
			// @ModelAttribute("file") UploadedFile uploadedFile

			@Valid FileBucket fileBucket, BindingResult result, ModelMap model

	) {

		Boolean returnResult = new Boolean(false);

		if (result.hasErrors()) {
			System.out.println("validation errors");
			return new ResponseEntity<>(returnResult, HttpStatus.NOT_IMPLEMENTED);

		} else {
			System.out.println("Fetching file");
			// MultipartFile multipartFile = fileBucket.getFile();

			// Now do something with file...
			// FileCopyUtils.copy(fileBucket.getFile().getBytes(), new File(
			// UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename()));
			// String fileName = multipartFile.getOriginalFilename();
			// model.addAttribute("fileName", fileName);
			// return "success";
		}

		// MultipartFile file = uploadedFile.getFile();

		MultipartFile file = fileBucket.getFile();

		if (!file.isEmpty()) {

			returnResult = transpondersJsonizer.uploadTransponders(file);
		} else {
			return new ResponseEntity<>(returnResult, HttpStatus.NOT_IMPLEMENTED);
		}

		return new ResponseEntity<>(returnResult, HttpStatus.OK);
	}

	@RequestMapping(value = filterTypePattern, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Transponders>> getTranspondersByArbitraryFilter(@PathVariable("type") String fieldName,
			@PathVariable("typeValue") String typeValue) {
		List<Transponders> transList = transpondersJsonizer.getTranspondersByArbitraryFilter(fieldName, typeValue);
		if (transList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(transList, HttpStatus.OK);
	}

	@RequestMapping(value = filterTypePattern, method = RequestMethod.GET, produces = MediaType.APPLICATION_XML)
	public ResponseEntity<GenericXMLListWrapper<Transponders>> getTranspondersByArbitraryFilterXML(
			@PathVariable("type") String fieldName, @PathVariable("typeValue") String typeValue) {
		List<Transponders> transList = transpondersJsonizer.getTranspondersByArbitraryFilter(fieldName, typeValue);
		if (transList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return JsonObjectFiller.returnGenericWrapperResponseBySatList(transList, Transponders.class);
	}

	@RequestMapping(value = "{transponderId}", method = RequestMethod.GET)
	public ResponseEntity<Transponders> getTransponderById(@PathVariable("transponderId") long tpId) {
		Transponders trans = objectController.select(Transponders.class, tpId);
		if (trans == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(trans, HttpStatus.OK);

	}

	@RequestMapping(value = filterSatIdPattern, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Transponders>> getTranspondersBySatelliteId(@PathVariable("satId") long satId) {

		List<Transponders> transList = transpondersJsonizer.getTranspondersBySatelliteId(satId);
		if (transList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(transList, HttpStatus.OK);
	}

	@RequestMapping(value = filterSatIdPattern, method = RequestMethod.GET, produces = MediaType.APPLICATION_XML)
	public ResponseEntity<GenericXMLListWrapper<Transponders>> getTranspondersBySatelliteIdXML(
			@PathVariable(value = "satId") long satId) {

		List<Transponders> transList = transpondersJsonizer.getTranspondersBySatelliteId(satId);
		if (transList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return JsonObjectFiller.returnGenericWrapperResponseBySatList(transList, Transponders.class);
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Transponders>> getTransponders() {
		List<Transponders> transList = objectController.list(Transponders.class);
		if (transList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(transList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML)
	public ResponseEntity<GenericXMLListWrapper<Transponders>> getTranspondersXML() {
		List<Transponders> transList = objectController.list(Transponders.class);
		if (transList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return JsonObjectFiller.returnGenericWrapperResponseBySatList(transList, Transponders.class);

	}

	public ObjectsController getObjectController() {
		return objectController;
	}

	public void setObjectController(ObjectsController objectController) {
		this.objectController = objectController;
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

}
