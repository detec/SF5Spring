package org.openbox.sf5.json.mockmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openbox.sf5.config.AppTestConfiguration;
import org.openbox.sf5.config.ManualWebMvcConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig({ ManualWebMvcConfiguration.class, AppTestConfiguration.class })
public class TranspondersTests {

    @Autowired
    private WebApplicationContext context;

    // @Mock
    // private TranspondersJsonizer transpondersJsonizer;
    //
    // @Mock
    // private ObjectsController objectController;
    //
    // @InjectMocks
    // private TranspondersService transpondersService;

    @Value("${jaxrs.path}")
    private String jsonPath;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        // MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

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
