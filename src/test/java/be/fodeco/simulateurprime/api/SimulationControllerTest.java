package be.fodeco.simulateurprime.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SimulationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void acces_refuse_quandAgeInferieurA18() throws Exception {
        mockMvc.perform(post("/api/acces")
                .contentType("application/json")
                .content("{\"age\": 17}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.autorise").value(false))
            .andExpect(jsonPath("$.message").value("Citoyen inéligible : âge hors limites"));
    }

    @Test
    void acces_autorise_quandAgeValide() throws Exception {
        mockMvc.perform(post("/api/acces")
                .contentType("application/json")
                .content("{\"age\": 25}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.autorise").value(true));
    }
}
