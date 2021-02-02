package pl.edu.pjatk.simulator.controller.skm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SkmControllerTestNotLoggedIn {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void goTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/skm/go"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
