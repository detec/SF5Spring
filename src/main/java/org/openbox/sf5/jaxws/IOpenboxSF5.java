package org.openbox.sf5.jaxws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.web.util.UriComponentsBuilder;

@WebService
public interface IOpenboxSF5 {

	@WebMethod
	long createUser(org.openbox.sf5.model.Users user) throws WSException;

	@WebMethod
	boolean ifSuchLoginExists(String login) throws WSException;

	@WebMethod
	org.openbox.sf5.model.Users getUserByLogin(String login) throws WSException;

	@WebMethod
	List<org.openbox.sf5.model.Transponders> getTranspondersByArbitraryFilter(String fieldName, String typeValue)
			throws WSException;

	@WebMethod
	org.openbox.sf5.model.Transponders getTransponderById(long tpId) throws WSException;

	@WebMethod
	List<org.openbox.sf5.model.Transponders> getTranspondersBySatelliteId(long satId) throws WSException;

	@WebMethod
	List<org.openbox.sf5.model.Transponders> getTransponders() throws WSException;

	@WebMethod
	long createSetting(org.openbox.sf5.model.Settings setting, String login, UriComponentsBuilder ucBuilder)
			throws WSException;

	@WebMethod
	List<org.openbox.sf5.model.Settings> getSettingsByUserLogin(String login) throws WSException;

	@WebMethod
	List<org.openbox.sf5.model.Settings> getSettingsByArbitraryFilter(String fieldName, String typeValue, String login)
			throws WSException;

	@WebMethod
	org.openbox.sf5.model.Settings getSettingById(long settingId, String login) throws WSException;

	@WebMethod
	List<org.openbox.sf5.model.Satellites> getAllSatellites() throws WSException;

	@WebMethod
	List<org.openbox.sf5.model.Satellites> getSatellitesByArbitraryFilter(String fieldName, String typeValue)
			throws WSException;

	@WebMethod
	org.openbox.sf5.model.Satellites getSatelliteById(long satId) throws WSException;

}