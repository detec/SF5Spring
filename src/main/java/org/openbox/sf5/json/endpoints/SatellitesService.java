package org.openbox.sf5.json.endpoints;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.openbox.sf5.common.JsonObjectFiller;
import org.openbox.sf5.json.service.SatellitesJsonizer;
import org.openbox.sf5.model.Satellites;
import org.openbox.sf5.model.listwrappers.GenericXMLListWrapper;
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
// http://stackoverflow.com/questions/17128463/overriding-requestmapping-on-springmvc-controller
@RestController
@EnableWebMvc
@RequestMapping(value = "${jaxrs.path}/satellites/", produces = { "application/xml", "application/json" })
public class SatellitesService {

	@RequestMapping(value = "jdx", method = RequestMethod.GET)
	public ResponseEntity<List<Satellites>> getAllSatellitesJDX() {
		List<Satellites> satList = listService.ObjectsList(Satellites.class);
		if (satList.isEmpty()) {
			return new ResponseEntity<List<Satellites>>(HttpStatus.NO_CONTENT);//
		}

		return new ResponseEntity<List<Satellites>>(satList, HttpStatus.OK);
	}

	@RequestMapping(value = "all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Satellites>> getAllSatellites() {
		List<Satellites> satList = listService.ObjectsList(Satellites.class);
		if (satList.isEmpty()) {
			return new ResponseEntity<List<Satellites>>(HttpStatus.NO_CONTENT);//
		}

		return new ResponseEntity<List<Satellites>>(satList, HttpStatus.OK);
	}

	@RequestMapping(value = "all", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML)
	public ResponseEntity<GenericXMLListWrapper<Satellites>> getAllSatellitesXML()
			throws NoSuchFieldException, SecurityException, NoSuchMethodException {
		List<Satellites> satList = listService.ObjectsList(Satellites.class);
		if (satList.isEmpty()) {
			return new ResponseEntity<GenericXMLListWrapper<Satellites>>(HttpStatus.NO_CONTENT);//
		}

		// we should replace XmlElementWrapper name
		// Method method = wrapper.getClass().getMethod("getWrappedList");

		return JsonObjectFiller.returnGenericWrapperResponseBySatList(satList, Satellites.class);
	}

	// private ResponseEntity<GenericXMLListWrapper<Satellites>>
	// returnGenericWrapperResponseBySatList(
	// List<Satellites> satList) {
	//
	// GenericXMLListWrapper<Satellites> wrapper = new
	// GenericXMLListWrapper<Satellites>();
	// wrapper.setWrappedList(satList);
	//
	// //
	// http://stackoverflow.com/questions/14268981/modify-a-class-definitions-annotation-string-parameter-at-runtime
	// //
	// http://stackoverflow.com/questions/16545868/exception-converting-object-to-xml-using-jaxb
	//
	// // We should replace stub for satellites in root element
	// final XmlRootElement classAnnotation =
	// wrapper.getClass().getAnnotation(XmlRootElement.class);
	// ChangeAnnotation.changeAnnotationValue(classAnnotation, "name",
	// "satellites"); // this
	// // seems
	// // to
	// // work
	//
	// // we should also change annotation of @XmlSeeAlso
	// final XmlSeeAlso classSeeAlsoAnnotation =
	// wrapper.getClass().getAnnotation(XmlSeeAlso.class);
	// // Player[] thePlayers = new Player[playerCount + 1];
	// Class[] clazzArray = new Class[1];
	// clazzArray[0] = Satellites.class;
	// ChangeAnnotation.changeAnnotationValue(classSeeAlsoAnnotation, "value",
	// clazzArray);
	//
	// return new ResponseEntity<GenericXMLListWrapper<Satellites>>(wrapper,
	// HttpStatus.OK);
	// }

	@RequestMapping(value = "filter/id/{satelliteId}", method = RequestMethod.GET)
	public ResponseEntity<Satellites> getSatelliteById(@PathVariable("satelliteId") long satId) {
		Satellites sat = objectController.select(Satellites.class, satId);
		if (sat == null) {
			return new ResponseEntity<Satellites>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Satellites>(sat, HttpStatus.OK);
	}

	@RequestMapping(value = "filter/{type}/{typeValue}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Satellites>> getSatellitesByArbitraryFilter(@PathVariable("type") String fieldName,
			@PathVariable("typeValue") String typeValue) {

		List<Satellites> satList = jsonizer.getSatellitesByArbitraryFilter(fieldName, typeValue);
		if (satList.isEmpty()) {
			return new ResponseEntity<List<Satellites>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Satellites>>(satList, HttpStatus.OK);
	}

	@RequestMapping(value = "filter/{type}/{typeValue}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML)
	public ResponseEntity<GenericXMLListWrapper<Satellites>> getSatellitesByArbitraryFilterXML(
			@PathVariable("type") String fieldName, @PathVariable("typeValue") String typeValue) {
		List<Satellites> satList = jsonizer.getSatellitesByArbitraryFilter(fieldName, typeValue);

		if (satList.isEmpty()) {
			return new ResponseEntity<GenericXMLListWrapper<Satellites>>(HttpStatus.NO_CONTENT);//
		}

		return JsonObjectFiller.returnGenericWrapperResponseBySatList(satList, Satellites.class);

	}

	@Autowired
	private ObjectsController objectController;

	// http://www.mkyong.com/spring3/spring-value-default-value/

	// @Value("${jaxrs.path​}")
	// private static String jaxRSPath;

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
