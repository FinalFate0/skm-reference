package pl.edu.pjatk.simulator.controller.user;

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
public class UserControllerTestAsAdmin {

    @Autowired
    MockMvc mockMvc;
    String TOKEN;

    @BeforeAll
    public void logIn() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders.get("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                             "username": "admin",
                             "password": "admin"
                           }
                        """))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String content = response.getResponse().getContentAsString();
        this.TOKEN = TOKEN_PREFIX + content.split(" ")[1];
    }

    @Test
    public void getTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .header(HEADER_STRING, TOKEN))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .header(HEADER_STRING, TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                             "username": "test",
                             "password": "test",
                             "authorities": "ROLE_PRIVILEGED"
                           }
                        """))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    public void putTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/users")
                .header(HEADER_STRING, TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                             "id": 1,
                             "username": "not_admin",
                             "authorities": "ROLE_USER"
                           }
                        """))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
                .header(HEADER_STRING, TOKEN))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }
}
