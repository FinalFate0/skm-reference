package pl.edu.pjatk.simulator.controller.compartment;

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
public class CompartmentControllerTestAsUser {

    @Autowired
    MockMvc mockMvc;
    String TOKEN;

    @BeforeAll
    public void logIn() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders.get("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                             "username": "user",
                             "password": "user"
                           }
                        """))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String content = response.getResponse().getContentAsString();
        this.TOKEN = TOKEN_PREFIX + content.split(" ")[1];
    }

    @Test
    public void getTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/compartments")
                .header(HEADER_STRING, TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void putTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/compartments")
                .header(HEADER_STRING, TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                             "id": 1,
                             "capacity": 10
                           }
                        """))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/compartments/2")
                .header(HEADER_STRING, TOKEN))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


}
