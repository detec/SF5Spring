package org.openbox.sf5.jaxws;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.openbox.sf5.json.endpoints.SatellitesService;
import org.openbox.sf5.json.endpoints.SettingsService;
import org.openbox.sf5.json.endpoints.TranspondersService;
import org.openbox.sf5.json.endpoints.UsersService;
import org.openbox.sf5.model.Transponders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

// http://java.globinch.com/category/enterprise-java/web-services/jax-ws/
// About Ws-annotations

@Named
@ApplicationScoped
@WebService(name = "IOpenboxSF5", targetNamespace = "http://wsmodel.sf5.openbox.org/") // model
																						// -
																						// to
																						// generate
																						// test
																						// stub
																						// classes
																						// into
																						// logically
																						// nice
																						// package
// @SOAPBinding(style = Style.RPC) // to make definition shorter but it makes
// endpoint '' problem
public class OpenboxSF5 implements Serializable {

	@WebMethod
	public long createUser(@WebParam(name = "inputUser") org.openbox.sf5.model.Users user) throws WSException {
		ResponseEntity<Long> RSResponse = usersService.createUser(user);

		// RSResponse.
		boolean isError = CheckIfThereIsErrorInResponse(RSResponse);
		// if (isError) {
		// return 0;
		// }
		Long newId = RSResponse.getBody();

		// return Long.parseLong(newIdString);

		return newId;
	}

	@WebMethod
	public boolean ifSuchLoginExists(@WebParam(name = "inputLogin") String login) throws WSException {

		ResponseEntity<Boolean> RSResponse = usersService.ifSuchLoginExists(login);
		boolean isError = CheckIfThereIsErrorInResponse(RSResponse);
		// if (isError) {
		// return 0;
		// }

		// String newIdString = (String) RSResponse.getEntity();
		// Long newId = RSResponse.getBody();

		// return Long.parseLong(newIdString);

		return RSResponse.getBody();
	}

	@WebMethod
	public org.openbox.sf5.model.Users getUserByLogin(@WebParam(name = "inputLogin") String login) throws WSException {

		ResponseEntity<org.openbox.sf5.model.Users> RSResponse = usersService.getUserByLogin(login);

		CheckIfThereIsErrorInResponse(RSResponse);

		org.openbox.sf5.model.Users user = RSResponse.getBody();

		return user;
	}

	@WebMethod
	public List<org.openbox.sf5.model.Transponders> getTranspondersByArbitraryFilter(
			@WebParam(name = "inputFieldName") String fieldName, @WebParam(name = "inputFieldValue") String typeValue)
					throws WSException {

		ResponseEntity<List<Transponders>> RSResponse = transpondersService.getTranspondersByArbitraryFilter(fieldName,
				typeValue);
		CheckIfThereIsErrorInResponse(RSResponse);
		List<org.openbox.sf5.model.Transponders> transList = RSResponse.getBody();

		return transList;
	}

	@WebMethod
	public org.openbox.sf5.model.Transponders getTransponderById(@WebParam(name = "inputTransponderId") long tpId)
			throws WSException {
		ResponseEntity<Transponders> RSResponse = transpondersService.getTransponderById(tpId);
		CheckIfThereIsErrorInResponse(RSResponse);

		org.openbox.sf5.model.Transponders trans = RSResponse.getBody();
		return trans;
	}

	@WebMethod
	public List<org.openbox.sf5.model.Transponders> getTranspondersBySatelliteId(
			@WebParam(name = "inputSatId") long satId) throws WSException {
		ResponseEntity<List<Transponders>> RSResponse = transpondersService.getTranspondersBySatelliteId("", satId);
		CheckIfThereIsErrorInResponse(RSResponse);

		List<org.openbox.sf5.model.Transponders> transList = RSResponse.getBody();

		return transList;

	}

	@WebMethod
	public List<org.openbox.sf5.model.Transponders> getTransponders() throws WSException {
		ResponseEntity<List<Transponders>> RSResponse = transpondersService.getTransponders();
		CheckIfThereIsErrorInResponse(RSResponse);

		List<org.openbox.sf5.model.Transponders> transList = RSResponse.getBody();

		return transList;
	}

	@WebMethod
	public long createSetting(org.openbox.sf5.model.Settings setting, @WebParam(name = "inputLogin") String login,
			UriComponentsBuilder ucBuilder) throws WSException {
		ResponseEntity<Long> RSResponse = settingsService.createSetting(setting, ucBuilder);
		boolean isError = CheckIfThereIsErrorInResponse(RSResponse);
		if (isError) {
			return 0;
		}
		// String newIdString = (String) RSResponse.getBody();
		Long Id = RSResponse.getBody();

		// return Long.parseLong(newIdString);
		return Id;
	}
	//
	// @WebMethod
	// public List<org.openbox.sf5.model.Settings>
	// getSettingsByUserLogin(@WebParam(name = "inputLogin") String login)
	// throws WSException {
	// Response RSResponse = settingsService.getSettingsByUserLogin(login);
	// CheckIfThereIsErrorInResponse(RSResponse);
	//
	// List<org.openbox.sf5.model.Settings> settList =
	// (List<org.openbox.sf5.model.Settings>) RSResponse.getEntity();
	//
	// return settList;
	// }
	//
	// @WebMethod
	// public List<org.openbox.sf5.model.Settings> getSettingsByArbitraryFilter(
	// @WebParam(name = "inputFieldName") String fieldName, @WebParam(name =
	// "inputFieldValue") String typeValue,
	// @WebParam(name = "inputLogin") String login) throws WSException {
	//
	// Response RSResponse =
	// settingsService.getSettingsByArbitraryFilter(fieldName, typeValue,
	// login);
	// CheckIfThereIsErrorInResponse(RSResponse);
	//
	// List<org.openbox.sf5.model.Settings> settList =
	// (List<org.openbox.sf5.model.Settings>) RSResponse.getEntity();
	//
	// return settList;
	// }
	//
	// @WebMethod
	// public org.openbox.sf5.model.Settings getSettingById(@WebParam(name =
	// "inputSettingId") long settingId,
	// @WebParam(name = "inputLogin") String login) throws WSException {
	//
	// Response RSResponse = settingsService.getSettingById(settingId, login);
	// CheckIfThereIsErrorInResponse(RSResponse);
	//
	// org.openbox.sf5.model.Settings setting = (org.openbox.sf5.model.Settings)
	// RSResponse.getEntity();
	//
	// return setting;
	// }
	//
	// @WebMethod
	// public List<org.openbox.sf5.model.Satellites> getAllSatellites() throws
	// WSException {
	// Response RSResponse = satellitesService.getAllSatellites();
	// CheckIfThereIsErrorInResponse(RSResponse);
	//
	// List<org.openbox.sf5.model.Satellites> satList =
	// (List<org.openbox.sf5.model.Satellites>) RSResponse
	// .getEntity();
	//
	// return satList;
	//
	// }
	//
	// @WebMethod
	// public List<org.openbox.sf5.model.Satellites>
	// getSatellitesByArbitraryFilter(
	// @WebParam(name = "inputFieldName") String fieldName, @WebParam(name =
	// "inputFieldValue") String typeValue)
	// throws WSException {
	//
	// Response RSResponse =
	// satellitesService.getSatellitesByArbitraryFilter(fieldName, typeValue);
	// CheckIfThereIsErrorInResponse(RSResponse);
	//
	// List<org.openbox.sf5.model.Satellites> satList =
	// (List<org.openbox.sf5.model.Satellites>) RSResponse
	// .getEntity();
	//
	// return satList;
	// }
	//
	// @WebMethod
	// public org.openbox.sf5.model.Satellites getSatelliteById(@WebParam(name =
	// "inputSatelliteId") long satId)
	// throws WSException {
	//
	// Response RSResponse = satellitesService.getSatelliteById(satId);
	// CheckIfThereIsErrorInResponse(RSResponse);
	// org.openbox.sf5.model.Satellites satellite =
	// (org.openbox.sf5.model.Satellites) RSResponse.getEntity();
	//
	// return satellite;
	// }

	@Inject
	private UsersService usersService;

	@Inject
	private TranspondersService transpondersService;

	@Inject
	private SettingsService settingsService;

	@Inject
	private SatellitesService satellitesService;

	private final Logger LOG = Logger.getLogger(getClass().getName());

	@Resource
	private WebServiceContext context;

	// private HttpServletResponse getResponse() {
	// MessageContext ctx = context.getMessageContext();
	// HttpServletResponse response = (HttpServletResponse)
	// ctx.get(MessageContext.SERVLET_RESPONSE);
	//
	// return response;
	// }

	// find if there is an error code and throw error
	private <T> boolean CheckIfThereIsErrorInResponse(ResponseEntity<T> rSResponse) throws WSException {

		List<Integer> normalStatusCodes = new ArrayList<Integer>();
		normalStatusCodes.add(new Integer(200));
		normalStatusCodes.add(new Integer(201));
		normalStatusCodes.add(new Integer(202));

		// checking status of response
		int statusCode = rSResponse.getStatusCode().value();

		boolean isError = (normalStatusCodes.contains(new Integer(statusCode))) ? false : true;

		// if (statusCode != 200 && statusCode != 202) {

		// This code is not working
		// HttpServletResponse servletResponse = getResponse();
		// // String errorMessage = response.readEntity(String.class);
		// String errorMessage = (String) response.getEntity();
		// isError = true;
		// try {
		// servletResponse.sendError(statusCode, errorMessage);
		// }
		//
		// catch (IOException e) {
		// LOG.severe(e.getMessage());
		// }

		// }

		if (isError) {
			String errorMessage = (String) rSResponse.getBody();
			throw new WSException(errorMessage, statusCode);
		}

		return isError;
	}

	public OpenboxSF5() {

	}

	private static final long serialVersionUID = -3436633533409493578L;

}
