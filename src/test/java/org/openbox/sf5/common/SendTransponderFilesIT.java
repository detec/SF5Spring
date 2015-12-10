package org.openbox.sf5.common;

import org.openbox.sf5.json.endpoints.AbstractServiceTest;

public class SendTransponderFilesIT extends AbstractServiceTest {

	// http://howtodoinjava.com/2015/08/07/jersey-restful-client-examples/

	// http://stackoverflow.com/questions/31977185/web-application-exception-javax-ws-rs-notsupportedexception-http-415-unsupport
	// about multipart Jesrey

	// public WebDriver driver;
	//
	// @Before
	// public void setUp() throws Exception {
	// driver = new FirefoxDriver();
	// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	// }
	//
	// public void tearDown() throws Exception {
	// driver.quit();
	// }

	// @Test
	// We replaced it with native json upload.
	// public void shouldSendFiles() throws Exception {
	//
	// Stream<Path> transponderFilesPathes =
	// IntersectionsTests.getTransponderFilesStreamPath();
	//
	// // Will use Selenium, as it is more practical.
	// // setUp();
	// LoginIT loginTest = new LoginIT();
	// loginTest.setAppLocation(appLocation);
	// loginTest.driver = driver;
	// loginTest.doLogin();
	//
	// TranspondersUploadIT uploadTest = new TranspondersUploadIT();
	// uploadTest.driver = driver;
	// uploadTest.setAppLocation(appLocation);
	//
	// transponderFilesPathes.forEach(t -> {
	// uploadTest.doUpload(t.toAbsolutePath().toString());
	// });
	//
	// tearDown();
	//
	//
	// }

}
