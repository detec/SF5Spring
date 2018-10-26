package org.openbox.sf5.json.mockmvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openbox.sf5.common.IniReader;
import org.openbox.sf5.common.IntersectionsTests;
import org.openbox.sf5.config.AppTestConfiguration;
import org.openbox.sf5.config.ManualWebMvcConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig({ ManualWebMvcConfiguration.class, AppTestConfiguration.class })
public class TranspondersTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private IniReader iniReader;

    @Value("${jaxrs.path}")
    private String jsonPath;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws URISyntaxException, IOException {
        // MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

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
    public void getMockTransponders() throws Exception {
        this.mockMvc.perform(get(String.join("", "/", jsonPath, "/transponders/")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getMockFilterTypeTranspondersJson() throws Exception {
        this.mockMvc.perform(get(String.join("", "/", jsonPath, "/transponders/", "filter/", "Speed/", "27500"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getMockFilterTypeTranspondersXml() throws Exception {
        this.mockMvc
                .perform(get(String.join("", "/", jsonPath, "/transponders/", "filter/", "Speed/", "27500"))
                        .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk());
    }
}
