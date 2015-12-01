package org.openbox.sf5.application;

import org.openqa.selenium.WebDriver;

public class AbstractSeleniumTest {

	public WebDriver driver;

	public StringBuffer verificationErrors = new StringBuffer();

	public void fail(String exceptionString) throws Exception {
		throw new Exception(exceptionString);
	}

}
