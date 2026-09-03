package be.fodeco.simulateurprime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimulationOrchestratorTest {

    private final SimulationOrchestrator orchestrator = new SimulationOrchestrator();

    @Test
    void orchestrer_renvoieIneligible_quandAEstInsuffisant() {
        SimulationResult resultat = orchestrator.orchestrer(25, 500, 1000, 0, 0, 1, 1, 1, 1);

        assertEquals(SimulationResult.Statut.INELIGIBLE, resultat.statut());
        assertEquals("inéligible (A < 1000)", resultat.motif());
        assertNull(resultat.montant());
    }

    @Test
    void orchestrer_renvoieErreur_quandHEstInvalide() {
        SimulationResult resultat = orchestrator.orchestrer(25, 1000, 1000, 0, 0, 1, 1, 1, 0);

        assertEquals(SimulationResult.Statut.ERREUR, resultat.statut());
        assertEquals("erreur (H doit être > 0)", resultat.motif());
    }

    @Test
    void orchestrer_renvoieForfait_quandConditionsReunies() {
        SimulationResult resultat = orchestrator.orchestrer(25, 1500, 15000, 600, 1000, 5, 2, 0, 10);

        assertEquals(SimulationResult.Statut.ELIGIBLE, resultat.statut());
        assertEquals(1000.0, resultat.montant());
    }

    @Test
    void orchestrer_renvoieErreur_quandDenominateurNul() {
        SimulationResult resultat = orchestrator.orchestrer(25, 1000, 1000, 0, 0, 1, 0, 0, 1);

        assertEquals(SimulationResult.Statut.ERREUR, resultat.statut());
        assertTrue(resultat.motif().contains("dénominateur"));
    }
}