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

    @Test
void simulation_renvoieResultatEligible_quandDonneesValides() throws Exception {
    mockMvc.perform(post("/api/simulation")
            .contentType("application/json")
            .content("{\"age\":25,\"a\":1000,\"b\":1000,\"c\":0,\"d\":0,\"e\":1,\"f\":1,\"g\":1,\"h\":1}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statut").value("ELIGIBLE"))
        .andExpect(jsonPath("$.montant").value(2000.0));
}

@Test
void simulation_renvoieIneligible_quandAInsuffisant() throws Exception {
    mockMvc.perform(post("/api/simulation")
            .contentType("application/json")
            .content("{\"age\":25,\"a\":500,\"b\":1000,\"c\":0,\"d\":0,\"e\":1,\"f\":1,\"g\":1,\"h\":1}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statut").value("INELIGIBLE"))
        .andExpect(jsonPath("$.motif").value("inéligible (A < 1000)"));
}

@Test
void cors_autoriseOrigineAngular() throws Exception {
    mockMvc.perform(post("/api/acces")
            .header("Origin", "http://localhost:4200")
            .contentType("application/json")
            .content("{\"age\": 25}"))
        .andExpect(status().isOk())
        .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:4200"));
}
}
