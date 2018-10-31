package org.openbox.sf5.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.xml.bind.JAXBException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openbox.sf5.config.AppTestConfiguration;
import org.openbox.sf5.dao.TranspondersRepository;
import org.openbox.sf5.model.Sat;
import org.openbox.sf5.model.Settings;
import org.openbox.sf5.model.SettingsConversion;
import org.openbox.sf5.model.Transponders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.xml.transform.StringSource;

@SpringJUnitConfig(AppTestConfiguration.class)
public class VerifyXMLExporterTests {

	@Autowired
	public Jaxb2Marshaller springMarshaller;

    @Autowired
    private IniReader iniReader;

    @Autowired
    private TranspondersRepository transpondersRepository;

    @BeforeEach
    public void setUp() throws URISyntaxException {

        int positiveResult = 0;
        try {
            positiveResult = getIniImportResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(3, positiveResult);
	}

    public int getIniImportResult() throws IOException, URISyntaxException {
        List<Boolean> resultList = new ArrayList<>();
        Stream<Path> streamPath = IntersectionsTests.getTransponderFilesStreamPath();

        streamPath.forEach(t -> {
            iniReader.setFilePath(t.toString());
            try {
                iniReader.readData();
                resultList.add(iniReader.isResult());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return (int) resultList.size();
    }

	@Test
	public void shouldVerifyXMLExport() throws IOException, JAXBException, URISyntaxException {

        Settings setting = new Settings();
        List<Transponders> transList = this.transpondersRepository.findAll();
        ConversionLinesHelper.fillTranspondersToSetting(transList, setting);

        List<SettingsConversion> conversionLines = setting.getConversion();
        assertThat(conversionLines.size()).isGreaterThan(0);

		Sat sat = XMLExporter.exportSettingsConversionToSF5Format(conversionLines);

/*        try (FileOutputStream fos = new FileOutputStream("sf5JunitOutput.xml");) {
            springMarshaller.marshal(sat, new StreamResult(fos));
        }

        catch (Exception e) {
            e.printStackTrace();
        }*/

		StringWriter sw = new StringWriter();
		URL responseFile = ClassLoader.getSystemResource("xml/sf5JunitOutput.xml");
		assertThat(responseFile).isNotNull();
		URI uri = responseFile.toURI();
		assertThat(uri).isNotNull();
        String content = new String(Files.readAllBytes(Paths.get(uri)), StandardCharsets.UTF_8);

        Sat retrievedSat = (Sat) springMarshaller.unmarshal(new StringSource(content));
        assertThat(retrievedSat).isEqualToComparingFieldByFieldRecursively(sat);
	}
}
