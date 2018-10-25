package org.openbox.sf5.json.endpoints;

import java.util.List;
import javax.servlet.annotation.MultipartConfig;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.openbox.sf5.common.JsonObjectFiller;
import org.openbox.sf5.dao.TranspondersRepository;
import org.openbox.sf5.json.service.FileBucket;
import org.openbox.sf5.json.service.TranspondersJsonizer;
import org.openbox.sf5.model.Transponders;
import org.openbox.sf5.model.listwrappers.GenericXMLListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    public static final String FILTER_TYPE_PATTERN = "filter/{type}/{typeValue}";

    private static final String FILTER_SAT_ID_PATTERN = "satId/{satId}";

    @Autowired
    private TranspondersJsonizer transpondersJsonizer;

    @Autowired
    private TranspondersRepository transpondersRepository;

    @PostMapping(headers = "content-type=multipart/form-data")
    public ResponseEntity<Boolean> uploadTransponders(@Valid FileBucket fileBucket, BindingResult result, ModelMap model) {

        Boolean returnResult = false;

		if (result.hasErrors()) {
			System.out.println("validation errors");
			return new ResponseEntity<>(returnResult, HttpStatus.NOT_IMPLEMENTED);

		} else {
			System.out.println("Fetching file");
		}

		MultipartFile file = fileBucket.getFile();

		if (!file.isEmpty()) {

			returnResult = transpondersJsonizer.uploadTransponders(file);
		} else {
			return new ResponseEntity<>(returnResult, HttpStatus.NOT_IMPLEMENTED);
		}

		return new ResponseEntity<>(returnResult, HttpStatus.OK);
	}

    @GetMapping(value = FILTER_TYPE_PATTERN, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Transponders>> getTranspondersByArbitraryFilter(@PathVariable("type") String fieldName,
			@PathVariable("typeValue") String typeValue) {

		List<Transponders> transList = transpondersJsonizer.getTranspondersByArbitraryFilter(fieldName, typeValue);
        return transList.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(transList, HttpStatus.OK);
	}

    @GetMapping(value = FILTER_TYPE_PATTERN, produces = MediaType.APPLICATION_XML)
	public ResponseEntity<GenericXMLListWrapper<Transponders>> getTranspondersByArbitraryFilterXML(
			@PathVariable("type") String fieldName, @PathVariable("typeValue") String typeValue) {

		List<Transponders> transList = transpondersJsonizer.getTranspondersByArbitraryFilter(fieldName, typeValue);
        return transList.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : JsonObjectFiller.returnGenericWrapperResponseBySatList(transList, Transponders.class);
	}

    @GetMapping("{transponderId}")
	public ResponseEntity<Transponders> getTransponderById(@PathVariable("transponderId") long tpId) {
        return transpondersRepository.findById(tpId).map(trans -> new ResponseEntity<>(trans, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
	}

    @GetMapping(value = FILTER_SAT_ID_PATTERN, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Transponders>> getTranspondersBySatelliteId(@PathVariable("satId") long satId) {

		List<Transponders> transList = transpondersJsonizer.getTranspondersBySatelliteId(satId);
        return transList.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(transList, HttpStatus.OK);
	}

    @GetMapping(value = FILTER_SAT_ID_PATTERN, produces = MediaType.APPLICATION_XML)
	public ResponseEntity<GenericXMLListWrapper<Transponders>> getTranspondersBySatelliteIdXML(
			@PathVariable(value = "satId") long satId) {

        List<Transponders> transList = transpondersJsonizer.getTranspondersBySatelliteId(satId);
        return transList.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : JsonObjectFiller.returnGenericWrapperResponseBySatList(transList, Transponders.class);
	}

    @GetMapping(produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Transponders>> getTransponders() {
        List<Transponders> transList = this.transpondersRepository.findAll();
        return transList.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(transList, HttpStatus.OK);
	}

    @GetMapping(produces = MediaType.APPLICATION_XML)
	public ResponseEntity<GenericXMLListWrapper<Transponders>> getTranspondersXML() {
        List<Transponders> transList = this.transpondersRepository.findAll();
        return transList.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : JsonObjectFiller.returnGenericWrapperResponseBySatList(transList, Transponders.class);
	}

	public TranspondersJsonizer getTranspondersJsonizer() {
		return transpondersJsonizer;
	}

	public void setTranspondersJsonizer(TranspondersJsonizer transpondersJsonizer) {
		this.transpondersJsonizer = transpondersJsonizer;
	}
}
