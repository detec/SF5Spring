package org.openbox.sf5.jaxws;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

// https://numberformat.wordpress.com/2013/01/31/generating-and-using-web-service-clients-using-wsimport-and-jax-ws/

public class DigestAuthenticator extends Authenticator {
	private final String user;

	private final String password;

	public DigestAuthenticator(String user, String password) {
		this.user = user;
		this.password = password;
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {

		/*
		 * During this call any of the follwing may be called to decide what to
		 * return
		 *
		 * getRequestingHost() getRequestingPort() getRequestingPrompt()
		 * getRequestingProtocol() getRequestingScheme() getRequestingURL()
		 * getRequestingSite() getRequestorType()
		 */

		return new PasswordAuthentication(user, password.toCharArray());
	}

}
