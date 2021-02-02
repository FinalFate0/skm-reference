package pl.edu.pjatk.simulator.controller.skm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static pl.edu.pjatk.simulator.security.util.SecurityConstants.HEADER_STRING;
import static pl.edu.pjatk.simulator.security.util.SecurityConstants.TOKEN_PREFIX;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SkmControllerTestAsPrivileged {

    @Autowired
    MockMvc mockMvc;
    String TOKEN;

    @BeforeAll
    public void logIn() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders.get("/login").contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                             "username": "privilegeduser",
                             "password": "privilegeduser"
                           }
                        """)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String content = response.getResponse().getContentAsString();
        this.TOKEN = TOKEN_PREFIX + content.split(" ")[1];
    }

    @Test
    public void goTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/skm/go")
                .header(HEADER_STRING, TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
