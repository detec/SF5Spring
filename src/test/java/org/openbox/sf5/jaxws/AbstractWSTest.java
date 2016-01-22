package org.openbox.sf5.jaxws;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.openbox.sf5.wsmodel.OpenboxSF5;
import org.openbox.sf5.wsmodel.OpenboxSF5ImplService;

public abstract class AbstractWSTest {

	public OpenboxSF5ImplService SF5Service;

	public URL url;

	public OpenboxSF5 SF5Port;

	public static final String appLocation = "http://localhost:8080/";

	public final String testUsername = "ITUserWS";

	public final String testUserPassword = "Test123";

	public Logger LOGGER = Logger.getLogger(getClass().getName());

	public Properties property = new Properties();

	public void loadProperties() {

		// using try with resources
		try (InputStream in = getClass().getResourceAsStream("/application.properties")) {
			property.load(in);
		} catch (IOException e) {
			e.printStackTrace();

			// put exception into log.
			LOGGER.log(Level.SEVERE, e.getMessage(), e);

		}

	}

	public void setUpAbstract() throws Exception {
		loadProperties();

		String contextPath = property.getProperty("context.path");
		url = new URL(appLocation + contextPath + "/" + property.getProperty("jaxws.path") + "/");

		SF5Service = new OpenboxSF5ImplService(new URL(url, "OpenboxSF5Service?wsdl"),
				new QName("http://wsmodel.sf5.openbox.org/", "OpenboxSF5ImplService"));

		SF5Port = SF5Service.getOpenboxSF5Port();

		BasicAuthenticator auth = new BasicAuthenticator(testUsername, testUserPassword);
		Authenticator.setDefault(auth);

		BindingProvider bp = (BindingProvider) SF5Port;
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "username");
		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "password");

//		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, testUsername);
//		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, testUserPassword);

	}

	public void setUpAbstractAdmin() throws Exception {
		loadProperties();

		String contextPath = property.getProperty("context.path");
		url = new URL(appLocation + contextPath + "/" + property.getProperty("jaxws.path") + "/");

		URL wsdlURL = new URL(url, "OpenboxSF5Service?wsdl");

		SF5Service = new OpenboxSF5ImplService(wsdlURL,
				new QName("http://wsmodel.sf5.openbox.org/", "OpenboxSF5ImplService"));

		SF5Port = SF5Service.getOpenboxSF5Port();

		BasicAuthenticator auth = new BasicAuthenticator("admin", "1");
		Authenticator.setDefault(auth);

		// https://java.net/projects/metro/lists/users/archive/2010-06/message/258
		// But when i put only the basic
		// following code just after the authenticator creation then digest
		// authentication works and my authenticator is used!

		BindingProvider bp = (BindingProvider) SF5Port;
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "username");
		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "password");

		// URLConnection ins = wsdlURL.openConnection();
		// ins.getInputStream().close();

		// Another article
		// http://www.mkyong.com/webservices/jax-ws/application-authentication-with-jax-ws/

	}

	public void setUpAbstractNoAuth() throws MalformedURLException {
		loadProperties();

		String contextPath = property.getProperty("context.path");
		url = new URL(appLocation + contextPath + "/" + property.getProperty("jaxws.path") + "/");

		URL wsdlURL = new URL(url, "OpenboxSF5Service?wsdl");

		SF5Service = new OpenboxSF5ImplService(wsdlURL,
				new QName("http://wsmodel.sf5.openbox.org/", "OpenboxSF5ImplService"));

		SF5Port = SF5Service.getOpenboxSF5Port();
	}

}
