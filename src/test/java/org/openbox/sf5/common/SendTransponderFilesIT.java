package org.openbox.sf5.common;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.Before;
import org.openbox.sf5.application.LoginIT;
import org.openbox.sf5.application.TranspondersUploadIT;
import org.openbox.sf5.json.endpoints.AbstractServiceTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SendTransponderFilesIT extends AbstractServiceTest {

	// http://howtodoinjava.com/2015/08/07/jersey-restful-client-examples/

	// http://stackoverflow.com/questions/31977185/web-application-exception-javax-ws-rs-notsupportedexception-http-415-unsupport
	// about multipart Jesrey

	public WebDriver driver;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void tearDown() throws Exception {
		driver.quit();
	}

	// @Test
	// We replaced it with native json upload.
	public void shouldSendFiles() throws Exception {

		Stream<Path> transponderFilesPathes = IntersectionsTests.getTransponderFilesStreamPath();

		// Will use Selenium, as it is more practical.
		// setUp();
		LoginIT loginTest = new LoginIT();
		loginTest.setAppLocation(appLocation);
		loginTest.driver = driver;
		loginTest.doLogin();

		TranspondersUploadIT uploadTest = new TranspondersUploadIT();
		uploadTest.driver = driver;
		uploadTest.setAppLocation(appLocation);

		transponderFilesPathes.forEach(t -> {
			uploadTest.doUpload(t.toAbsolutePath().toString());
		});

		tearDown();

		// Client client = createClient();
		//
		// final WebTarget target =
		// client.target(appLocation).path(jsonPath).path("transponders").path("upload");
		// // System.out.println(target.getUri());
		//
		// //
		// https://jersey.java.net/documentation/latest/user-guide.html#d0e9265
		// // !!!
		//
		// //
		// http://stackoverflow.com/questions/21397939/spring-security-3-2-csrf-support-for-multipart-requests
		//
		// configureMapper();
		//
		// transponderFilesPathes.forEach(t -> {
		//
		// FileDataBodyPart filePart = new FileDataBodyPart("file", t.toFile());
		//
		// @SuppressWarnings("resource")
		// final FormDataMultiPart multipart = (FormDataMultiPart) new
		// FormDataMultiPart().field("foo", "bar")
		// .bodyPart(filePart);
		//
		// Response lambdaResponse =
		// target.request().post(Entity.entity(multipart,
		// multipart.getMediaType()));
		// String output = lambdaResponse.readEntity(String.class);
		//
		// try {
		// Boolean result = mapper.readValue(output, Boolean.class);
		// assertThat(result.booleanValue()).isTrue();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		//
		// }
		//
		// // assertEquals(Status.OK.getStatusCode(),
		// // lambdaResponse.getStatus());
		//
		// });

	}

}
