package org.openbox.sf5.common;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openbox.sf5.json.service.AbstractJsonizerTest;
import org.openbox.sf5.model.Sat;
import org.openbox.sf5.model.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(locations = { "file:src/main/resources/spring/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class VerifyXMLExporterTests extends AbstractJsonizerTest {

	@Autowired
	public Jaxb2Marshaller springMarshaller;

	@Before
	public void setUp() {
		super.setUpAbstract();
	}

	@Test
	public void shouldVerifyXMLExport() throws IOException {
		// we previously saved this setting
		Criterion criterion = criterionService.getCriterionByClassFieldAndStringValue(Settings.class, "Name",
				"Intersections test");

		List<Settings> settList = listService.ObjectsCriterionList(Settings.class, criterion);

		assertEquals(1, settList.size());

		Settings setting = settList.get(0);

		Sat sat = XMLExporter.exportSettingsConversionPresentationToSF5Format(setting.getConversion());

		// StringBuffer outputBuffer = new StringBuffer();
		// JAXB.marshal(sat, outputBuffer);
		String result = "";
		// JAXB.marshal(sat, result);
		// springMarshaller.marshal(sat, result);

		// http://winterbe.com/posts/2015/03/25/java8-examples-string-number-math-files/
		ArrayList<String> lines = new ArrayList<String>();
		lines.add(result);
		Files.write(Paths.get("resources/sf5output.xml"), lines);

	}

}
