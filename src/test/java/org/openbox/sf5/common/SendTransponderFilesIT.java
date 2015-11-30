package org.openbox.sf5.common;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.stream.Stream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.junit.Test;
import org.openbox.sf5.json.endpoints.AbstractServiceTest;

public class SendTransponderFilesIT extends AbstractServiceTest {

	// http://howtodoinjava.com/2015/08/07/jersey-restful-client-examples/

	@Test
	public void shouldSendFiles() throws URISyntaxException, IOException {

		// WebTarget target = null;
		// Response response = null;

		Stream<Path> transponderFilesPathes = IntersectionsTests.getTransponderFilesStreamPath();

		Client client = createClient();

		// WebResource webResource = resource();

		// Client client = Client.create();

		final WebTarget target = client.target(appLocation).path("upload");
		// Invocation.Builder invocationBuilder =
		// target.request(MediaType.TEXT_HTML);

		// http://stackoverflow.com/questions/5772225/trying-to-upload-a-file-to-a-jax-rs-jersey-server

		// InputStream stream =
		// getClass().getClassLoader().getResourceAsStream(fileName);
		// FormDataMultiPart part = new FormDataMultiPart().field("file",
		// stream, MediaType.TEXT_PLAIN_TYPE);
		//
		// WebResource resource = Client.create().resource(url);
		// String response =
		// resource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(String.class,
		// part);
		// assertEquals("Hello, World", response);

		// https://jersey.java.net/documentation/latest/user-guide.html#d0e9265
		// !!!

		transponderFilesPathes.forEach(t -> {
			// InputStream stream =
			// getClass().getClassLoader().getResourceAsStream(t.toString());
			// FormDataMultiPart part = new FormDataMultiPart().field("file",
			// stream, MediaType.TEXT_PLAIN_TYPE);

			// invocationBuilder.accept(MediaType.TEXT_HTML).post(part);

			// FileDataBodyPart filePart = new FileDataBodyPart("my_pom", new
			// File("pom.xml"));

			// files are really looped.
			// System.out.println(t.toString());

			FileDataBodyPart filePart = new FileDataBodyPart("file", t.toFile());
			FormDataMultiPart multipart = new FormDataMultiPart();
			multipart.bodyPart(filePart);

			// final Response response = target.request()
			// .post(Entity.entity(multipart, multipart.getMediaType()));

			Response lambdaResponse = target.request().post(Entity.entity(multipart, multipart.getMediaType()));
			assertEquals(Status.OK.getStatusCode(), lambdaResponse.getStatus());
		});

	}

}
