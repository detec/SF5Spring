package org.openbox.sf5.json.mockmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openbox.sf5.config.AppTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(AppTestConfiguration.class)
public class UserTests {

    @Autowired
    private WebApplicationContext context;

    @Value("${jaxrs.path}")
    private String jsonPath;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void checkAdminLoginExists() throws Exception {
        this.mockMvc.perform(
                get(String.join("", "/", jsonPath, "/users/", "exists/", "username/", "admin"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void checkotherLoginDoesNotExist() throws Exception {
        this.mockMvc.perform(get(String.join("", "/", jsonPath, "/users/", "exists/", "username/", "ITUser"))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }
}
