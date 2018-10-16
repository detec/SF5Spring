package org.openbox.sf5.jaxws;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openbox.sf5.wsmodel.OpenboxSF5;
import org.openbox.sf5.wsmodel.OpenboxSF5ImplService;

public abstract class AbstractWSTest {

	public static final Logger LOGGER = Logger.getLogger(AbstractWSTest.class.getName());

	public static final String appLocation = "http://localhost:8080/";

	public static OpenboxSF5ImplService SF5Service;

	public static URL url;

	public static OpenboxSF5 SF5Port;

	public final String testUsername = "ITUserWS";

	public final String testUserPassword = "Test123";

	public static Properties property = new Properties();

    @BeforeAll
	public static void beforeClass() {

		loadProperties();

		String contextPath = property.getProperty("context.path");
		try {
			url = new URL(appLocation + contextPath + "/");
			SF5Service = new OpenboxSF5ImplService(new URL(url, "OpenboxSF5Service?wsdl"),
					new QName("http://wsmodel.sf5.openbox.org/", "OpenboxSF5Service"));

			SF5Port = SF5Service.getOpenboxSF5Port();
		} catch (MalformedURLException e) {
			// put exception into log.
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

	}

	public static void loadProperties() {

		// using try with resources
		try (InputStream in = AbstractWSTest.class.getResourceAsStream("/application.properties")) {
			property.load(in);
		} catch (IOException e) {
			// put exception into log.
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

	}

	public void setUpAbstract() throws Exception {

		// we moved to WildFly 9+ and it mounts service to the root.
		// + property.getProperty("jaxws.path") + "/"

		BasicAuthenticator auth = new BasicAuthenticator(testUsername, testUserPassword);
		Authenticator.setDefault(auth);

		BindingProvider bp = (BindingProvider) SF5Port;
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "username");
		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "password");

	}

	public void setUpAbstractAdmin() throws Exception {
		// loadProperties();

		// String contextPath = property.getProperty("context.path");
		// url = new URL(appLocation + contextPath + "/");
		//
		// URL wsdlURL = new URL(url, "OpenboxSF5Service?wsdl");

		// SF5Service = new OpenboxSF5ImplService(wsdlURL,
		// new QName("http://wsmodel.sf5.openbox.org/", "OpenboxSF5Service"));
		//
		// SF5Port = SF5Service.getOpenboxSF5Port();

		BasicAuthenticator auth = new BasicAuthenticator("admin", "1");
		Authenticator.setDefault(auth);

		// https://java.net/projects/metro/lists/users/archive/2010-06/message/258
		// But when i put only the basic
		// following code just after the authenticator creation then digest
		// authentication works and my authenticator is used!

		// BindingProvider bp = (BindingProvider) SF5Port;
		// bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
		// "username");
		// bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
		// "password");

		// Another article
		// http://www.mkyong.com/webservices/jax-ws/application-authentication-with-jax-ws/

	}

	public void setUpAbstractNoAuth() throws MalformedURLException {
		// loadProperties();

		// String contextPath = property.getProperty("context.path");
		// url = new URL(appLocation + contextPath + "/");
		//
		// URL wsdlURL = new URL(url, "OpenboxSF5Service?wsdl");
		//
		// SF5Service = new OpenboxSF5ImplService(wsdlURL,
		// new QName("http://wsmodel.sf5.openbox.org/", "OpenboxSF5Service"));
		//
		// SF5Port = SF5Service.getOpenboxSF5Port();
	}

	public void tearAuthentication() {
		staticTearAuthentication();
	}

    @AfterAll
	public static void staticTearAuthentication() {
		BindingProvider bp = (BindingProvider) SF5Port;
		Map<String, Object> contextMap = bp.getRequestContext();

		if (contextMap.containsKey(BindingProvider.USERNAME_PROPERTY)) {
			contextMap.remove(BindingProvider.USERNAME_PROPERTY);
		}

		if (contextMap.containsKey(BindingProvider.PASSWORD_PROPERTY)) {
			contextMap.remove(BindingProvider.PASSWORD_PROPERTY);
		}

		// trying to clear authenticator.
		Authenticator.setDefault(null);

	}

}
