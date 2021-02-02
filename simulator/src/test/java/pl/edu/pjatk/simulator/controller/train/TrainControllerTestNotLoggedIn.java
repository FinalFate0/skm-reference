package pl.edu.pjatk.simulator.controller.train;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TrainControllerTestNotLoggedIn {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trains"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void postTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/trains")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                             "going_to_gdansk": false,
                             "current_pause_time": 0,
                             "compartments":[{"capacity":50}]
                           }
                        """))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void putTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/trains")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                             "id": 1,
                             "current_pause_time": 4
                           }
                        """))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/trains/2"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
