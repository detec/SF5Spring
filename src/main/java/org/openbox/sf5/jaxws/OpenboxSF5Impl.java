package org.openbox.sf5.jaxws;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.openbox.sf5.json.endpoints.SatellitesService;
import org.openbox.sf5.json.endpoints.SettingsService;
import org.openbox.sf5.json.endpoints.TranspondersService;
import org.openbox.sf5.json.endpoints.UsersService;
import org.openbox.sf5.json.exceptions.NotAuthenticatedException;
import org.openbox.sf5.json.exceptions.UsersDoNotCoincideException;
import org.openbox.sf5.model.Satellites;
import org.openbox.sf5.model.Settings;
import org.openbox.sf5.model.Transponders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.util.UriComponentsBuilder;

// http://java.globinch.com/category/enterprise-java/web-services/jax-ws/

// http://www.mkyong.com/webservices/jax-ws/jax-ws-spring-integration-example/
// About Ws-annotations

@WebService(name = "OpenboxSF5",

targetNamespace = "http://wsmodel.sf5.openbox.org/") // model
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
// http://stackoverflow.com/questions/18513333/spring-mvc-app-with-soap-web-service-using-wsspringservlet
// https://bthurley.wordpress.com/2014/04/27/web-services-with-jax-ws-jaxb-and-spring/
@Component("OpenboxSF5")
public class OpenboxSF5Impl extends SpringBeanAutowiringSupport
// implements IOpenboxSF5

{

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.openbox.sf5.jaxws.IOpenboxSF5#createUser(org.openbox.sf5.model.Users)
	 */
	// @Override
	@WebMethod
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public long createUser(@WebParam(name = "inputUser") org.openbox.sf5.model.Users user) throws WSException {
		ResponseEntity<Long> RSResponse = null;
		try {
			RSResponse = usersService.createUser(user);
		} catch (Exception e) {
			throw new WSException("Error saving new user to database", e, RSResponse.getStatusCode().value());
		}

		// RSResponse.
		// boolean isError = CheckIfThereIsErrorInResponse(RSResponse);
		// if (isError) {
		// return 0;
		// }
		Long newId = RSResponse.getBody();

		// return Long.parseLong(newIdString);

		return newId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.openbox.sf5.jaxws.IOpenboxSF5#ifSuchLoginExists(java.lang.String)
	 */
	// @Override
	@WebMethod
	public boolean ifSuchLoginExists(@WebParam(name = "inputLogin") String login) throws WSException {

		ResponseEntity<Boolean> RSResponse = usersService.ifSuchLoginExists(login);
		// boolean isError = CheckIfThereIsErrorInResponse(RSResponse);
		// if (isError) {
		// return 0;
		// }

		// String newIdString = (String) RSResponse.getEntity();
		// Long newId = RSResponse.getBody();

		// return Long.parseLong(newIdString);

		return RSResponse.getBody();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.openbox.sf5.jaxws.IOpenboxSF5#getUserByLogin(java.lang.String)
	 */
	// @Override
	@WebMethod
	@PreAuthorize("hasRole('ROLE_ADMIN') or (#login == authentication.name)")
	// @PreAuthorize("hasRole('USER')")
	public org.openbox.sf5.model.Users getUserByLogin(@WebParam(name = "inputLogin") String login) throws WSException {

		ResponseEntity<org.openbox.sf5.model.Users> RSResponse = usersService.getUserByLogin(login);

		// CheckIfThereIsErrorInResponse(RSResponse);

		org.openbox.sf5.model.Users user = RSResponse.getBody();

		return user;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.openbox.sf5.jaxws.IOpenboxSF5#getTranspondersByArbitraryFilter(java.
	 * lang.String, java.lang.String)
	 */
	// @Override
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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.openbox.sf5.jaxws.IOpenboxSF5#getTransponderById(long)
	 */
	// @Override
	@WebMethod
	public org.openbox.sf5.model.Transponders getTransponderById(@WebParam(name = "inputTransponderId") long tpId)
			throws WSException {
		ResponseEntity<Transponders> RSResponse = transpondersService.getTransponderById(tpId);
		CheckIfThereIsErrorInResponse(RSResponse);

		org.openbox.sf5.model.Transponders trans = RSResponse.getBody();
		return trans;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.openbox.sf5.jaxws.IOpenboxSF5#getTranspondersBySatelliteId(long)
	 */
	// @Override
	@WebMethod
	public List<org.openbox.sf5.model.Transponders> getTranspondersBySatelliteId(
			@WebParam(name = "inputSatId") long satId) throws WSException {
		ResponseEntity<List<Transponders>> RSResponse = transpondersService.getTranspondersBySatelliteId("", satId);
		CheckIfThereIsErrorInResponse(RSResponse);

		List<org.openbox.sf5.model.Transponders> transList = RSResponse.getBody();

		return transList;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.openbox.sf5.jaxws.IOpenboxSF5#getTransponders()
	 */
	// @Override
	@WebMethod
	public List<org.openbox.sf5.model.Transponders> getTransponders() throws WSException {
		ResponseEntity<List<Transponders>> RSResponse = transpondersService.getTransponders();
		CheckIfThereIsErrorInResponse(RSResponse);

		List<org.openbox.sf5.model.Transponders> transList = RSResponse.getBody();

		return transList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.openbox.sf5.jaxws.IOpenboxSF5#createSetting(org.openbox.sf5.model.
	 * Settings, java.lang.String,
	 * org.springframework.web.util.UriComponentsBuilder)
	 */
	// @Override
	@WebMethod
	@PreAuthorize("hasRole('ROLE_USER')")
	public long createSetting(@WebParam(name = "inputSetting") org.openbox.sf5.model.Settings setting)
			throws WSException {
		ResponseEntity<Long> RSResponse = null;
		try {
			RSResponse = settingsService.createSetting(setting, ucBuilder);
		} catch (NotAuthenticatedException e) {
			throw new WSException("Failed to authenticate!", e);
		} catch (UsersDoNotCoincideException e) {
			throw new WSException(e.getMessage(), e);
		}
		// boolean isError = CheckIfThereIsErrorInResponse(RSResponse);
		// if (isError) {
		// return 0;
		// }
		// String newIdString = (String) RSResponse.getBody();
		Long Id = RSResponse.getBody();

		// return Long.parseLong(newIdString);
		return Id;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.openbox.sf5.jaxws.IOpenboxSF5#getSettingsByUserLogin(java.lang.
	 * String)
	 */
	// @Override
	@WebMethod
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<org.openbox.sf5.model.Settings> getSettingsByUserLogin(@WebParam(name = "inputLogin") String login)
			throws WSException {
		ResponseEntity<List<Settings>> RSResponse = null;
		try {
			RSResponse = settingsService.getSettingsByUserLogin();
		} catch (NotAuthenticatedException e) {
			throw new WSException("Failed to authenticate!", e);
		}
		// CheckIfThereIsErrorInResponse(RSResponse);

		List<org.openbox.sf5.model.Settings> settList = RSResponse.getBody();

		return settList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.openbox.sf5.jaxws.IOpenboxSF5#getSettingsByArbitraryFilter(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	// @Override
	@WebMethod
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<org.openbox.sf5.model.Settings> getSettingsByArbitraryFilter(
			@WebParam(name = "inputFieldName") String fieldName, @WebParam(name = "inputFieldValue") String typeValue)
					throws WSException {

		ResponseEntity<List<Settings>> RSResponse = null;
		try {
			RSResponse = settingsService.getSettingsByArbitraryFilter(fieldName, typeValue);
		} catch (NotAuthenticatedException e) {
			throw new WSException("Failed to authenticate!", e);
		}
		CheckIfThereIsErrorInResponse(RSResponse);

		List<org.openbox.sf5.model.Settings> settList = RSResponse.getBody();

		return settList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.openbox.sf5.jaxws.IOpenboxSF5#getSettingById(long,
	 * java.lang.String)
	 */
	// @Override
	@WebMethod
	@PreAuthorize("hasRole('ROLE_USER')")
	public org.openbox.sf5.model.Settings getSettingById(@WebParam(name = "inputSettingId") long settingId)
			throws WSException {

		ResponseEntity<Settings> RSResponse = null;
		try {
			RSResponse = settingsService.getSettingById(settingId);
		} catch (NotAuthenticatedException e) {
			throw new WSException("Failed to get setting by id!", e);
		}
		CheckIfThereIsErrorInResponse(RSResponse);

		org.openbox.sf5.model.Settings setting = RSResponse.getBody();

		return setting;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.openbox.sf5.jaxws.IOpenboxSF5#getAllSatellites()
	 */
	// @Override
	@WebMethod
	public List<org.openbox.sf5.model.Satellites> getAllSatellites() throws WSException {
		ResponseEntity<List<Satellites>> RSResponse = satellitesService.getAllSatellites();
		CheckIfThereIsErrorInResponse(RSResponse);

		List<org.openbox.sf5.model.Satellites> satList = RSResponse.getBody();

		return satList;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.openbox.sf5.jaxws.IOpenboxSF5#getSatellitesByArbitraryFilter(java.
	 * lang.String, java.lang.String)
	 */
	// @Override
	@WebMethod
	public List<org.openbox.sf5.model.Satellites> getSatellitesByArbitraryFilter(
			@WebParam(name = "inputFieldName") String fieldName, @WebParam(name = "inputFieldValue") String typeValue)
					throws WSException {

		ResponseEntity<List<Satellites>> RSResponse = satellitesService.getSatellitesByArbitraryFilter(fieldName,
				typeValue);
		CheckIfThereIsErrorInResponse(RSResponse);

		List<org.openbox.sf5.model.Satellites> satList = RSResponse.getBody();

		return satList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.openbox.sf5.jaxws.IOpenboxSF5#getSatelliteById(long)
	 */
	// @Override
	@WebMethod
	public org.openbox.sf5.model.Satellites getSatelliteById(@WebParam(name = "inputSatelliteId") long satId)
			throws WSException {

		ResponseEntity<Satellites> RSResponse = satellitesService.getSatelliteById(satId);
		CheckIfThereIsErrorInResponse(RSResponse);
		org.openbox.sf5.model.Satellites satellite = RSResponse.getBody();

		return satellite;
	}

	@Autowired
	private UsersService usersService;

	@Autowired
	private TranspondersService transpondersService;

	@Autowired
	private SettingsService settingsService;

	@Autowired
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

			Object errorObject = rSResponse.getBody();
			if (errorObject instanceof Boolean) {
				throw new WSException("Entity not found", statusCode);
			}

			else if (errorObject instanceof String) {
				throw new WSException((String) errorObject, statusCode);
			}

			else if (errorObject instanceof Long) {
				throw new WSException("Error code returned: " + errorObject.toString(), statusCode);
			}
		}

		return isError;
	}

	public OpenboxSF5Impl() {

	}

	@Autowired
	UriComponentsBuilder ucBuilder;

}
