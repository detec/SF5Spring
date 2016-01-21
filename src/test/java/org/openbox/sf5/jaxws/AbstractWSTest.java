package org.openbox.sf5.jaxws;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.openbox.sf5.wsmodel.IOpenboxSF5;
import org.openbox.sf5.wsmodel.OpenboxSF5Service;

public abstract class AbstractWSTest {

	public OpenboxSF5Service SF5Service;

	public URL url;

	public IOpenboxSF5 SF5Port;

	public static final String appLocation = "http://localhost:8080/";

	public String testUsername = "ITUserWS";

	public String testUserPassword = "Test123";

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

		SF5Service = new OpenboxSF5Service(new URL(url, "OpenboxSF5Service?wsdl"),
				new QName("http://wsmodel.sf5.openbox.org/", "OpenboxSF5ImplService"));

		SF5Port = SF5Service.getIOpenboxSF5Port();

		DigestAuthenticator auth = new DigestAuthenticator(this.testUsername, this.testUserPassword);
		Authenticator.setDefault(auth);

	}

	public void setUpAbstractAdmin() throws Exception {
		loadProperties();

		String contextPath = property.getProperty("context.path");
		url = new URL(appLocation + contextPath + "/" + property.getProperty("jaxws.path") + "/");

		SF5Service = new OpenboxSF5Service(new URL(url, "OpenboxSF5Service?wsdl"),
				new QName("http://wsmodel.sf5.openbox.org/", "OpenboxSF5ImplService"));

		SF5Port = SF5Service.getIOpenboxSF5Port();

		DigestAuthenticator auth = new DigestAuthenticator("admin", "1");
		Authenticator.setDefault(auth);

		BindingProvider bp = (BindingProvider) SF5Port;
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "username");
		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "password");

	}

}
