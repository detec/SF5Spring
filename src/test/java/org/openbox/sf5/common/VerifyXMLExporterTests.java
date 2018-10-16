package org.openbox.sf5.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.hibernate.criterion.Criterion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openbox.sf5.config.AppTestConfiguration;
import org.openbox.sf5.json.service.AbstractJsonizerTest;
import org.openbox.sf5.model.Sat;
import org.openbox.sf5.model.Settings;
import org.openbox.sf5.model.SettingsConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.xml.transform.StringSource;

@ContextConfiguration(classes = { AppTestConfiguration.class })
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class VerifyXMLExporterTests extends AbstractJsonizerTest {

	@Autowired
	public Jaxb2Marshaller springMarshaller;

    @BeforeEach
	public void setUp() {
		super.setUpAbstract();
	}

	@Test
	public void shouldVerifyXMLExport() throws IOException, JAXBException, URISyntaxException {
		// we previously saved this setting
		Criterion criterion = criterionService.getCriterionByClassFieldAndStringValue(Settings.class, "Name",
				"Intersections test");

		List<Settings> settList = objectController.restrictionList(Settings.class, criterion);

		assertEquals(1, settList.size());

		Settings setting = settList.get(0);

		List<SettingsConversion> conversionLines = setting.getConversion();
		assertThat(conversionLines.size()).isGreaterThan(0);

		Sat sat = XMLExporter.exportSettingsConversionToSF5Format(conversionLines);

		// try (FileOutputStream fos = new
		// FileOutputStream("sf5JunitOutput.xml");) {
		// springMarshaller.marshal(sat, new StreamResult(fos));
		// }
		//
		// catch (Exception e) {
		// e.printStackTrace();
		// }

		StringWriter sw = new StringWriter();
		URL responseFile = ClassLoader.getSystemResource("xml/sf5JunitOutput.xml");
		assertThat(responseFile).isNotNull();
		URI uri = responseFile.toURI();
		assertThat(uri).isNotNull();
		String content = new String(Files.readAllBytes(Paths.get(uri)), Charset.forName("UTF-8"));

		//
		// content = content.replace("\r\n\r\n", "\r\n"); // it adds
		//
		// // marshalling sat
		// springMarshaller.marshal(sat, new StreamResult(sw));
		//
		// assertEquals(content, sw.toString());

		Sat retrievedSat = (Sat) springMarshaller.unmarshal(new StringSource(content));
		// trying to compare resolved Sats.
		assertEquals(retrievedSat, sat);
	}
}
