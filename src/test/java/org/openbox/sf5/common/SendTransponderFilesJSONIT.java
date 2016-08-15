package org.openbox.sf5.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.openbox.sf5.json.endpoints.AbstractServiceTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

public class SendTransponderFilesJSONIT extends AbstractServiceTest {

	private static final String servicePath = "transponders/";

	@Before
	public void setUp() {
		setUpAbstractTestUser();
		serviceTarget = commonTarget.path(servicePath);
	}

	@Test
	public void shouldSendFileJsonEndpoint() throws URISyntaxException, IOException {
		Stream<Path> transponderFilesPathes = IntersectionsTests.getTransponderFilesStreamPath();

		transponderFilesPathes.forEach(t -> {

			// FileDataBodyPart filePart = new FileDataBodyPart("file",
			// t.toFile());
			//
			// @SuppressWarnings("resource")
			// final FormDataMultiPart multipart = (FormDataMultiPart) new
			// FormDataMultiPart().field("foo", "bar")
			// .bodyPart(filePart);
			//
			// Invocation.Builder invocationBuilder =
			// serviceTarget.request(MediaType.APPLICATION_JSON);
			//
			// Response responsePost = invocationBuilder.post(Entity.entity(
			//
			// multipart, multipart.getMediaType()));

			RestTemplate restTemplate = new RestTemplate();

			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

			map.add("file", new FileSystemResource(t.toFile()));

			HttpHeaders headers = new HttpHeaders();

			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			List<MediaType> acceptTypes = new ArrayList<>();
			acceptTypes.add(MediaType.APPLICATION_JSON);
			headers.setAccept(acceptTypes);

			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
			ResponseEntity<String> stringResponse = restTemplate.exchange(serviceTarget.getUri(), HttpMethod.POST,
					requestEntity, String.class);

			HttpStatus statusCode = stringResponse.getStatusCode();

			assertEquals(statusCode, HttpStatus.OK);

			// Boolean result = responsePost.readEntity(Boolean.class);

			String body = stringResponse.getBody();

			Boolean result = false;
			try {
				result = mapper.readValue(body, Boolean.class);
			} catch (IOException e) {
				e.printStackTrace();
			}

			assertThat(result.booleanValue()).isTrue();

		});
	}

}
