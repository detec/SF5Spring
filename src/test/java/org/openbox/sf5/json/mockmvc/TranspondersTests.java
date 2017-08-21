package org.openbox.sf5.json.mockmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openbox.sf5.config.AppTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ContextConfiguration(classes = { AppTestConfiguration.class })
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class TranspondersTests {

    @Autowired
    private WebApplicationContext context;

    @Value("${jaxrs.path}")
    private String jsonPath;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void getMockTransponders() throws Exception {
        this.mockMvc.perform(
                get(String.join("", "/", jsonPath, "/transponders/")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getMockFilterTypeTranspondersJson() throws Exception {
        this.mockMvc.perform(get(String.join("", "/", jsonPath, "/transponders/", "filter/", "Speed/", "27500"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getMockFilterTypeTranspondersXml() throws Exception {
        this.mockMvc
                .perform(get(String.join("", "/", jsonPath, "/transponders/", "filter/", "Speed/", "27500"))
                        .accept(MediaType.APPLICATION_XML))
                .andExpect(status().is(200));
    }
}
