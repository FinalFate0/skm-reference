package pl.edu.pjatk.simulator.controller.compartment;


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
public class CompartmentControllerTestNotLoggedIn {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/compartments"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void putTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/compartments")
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
        mockMvc.perform(MockMvcRequestBuilders.delete("/compartments/2"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
