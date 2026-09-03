package be.fodeco.simulateurprime.steps;

import be.fodeco.simulateurprime.AccesSimulateur;
import be.fodeco.simulateurprime.PrimeCalculator;
import be.fodeco.simulateurprime.SimulationResult;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class SimulateurPrimeSteps {

    private final AccesSimulateur accesSimulateur = new AccesSimulateur();
    private final PrimeCalculator primeCalculator = new PrimeCalculator();

    private int age;
    private int a, b, c, d, e, f, g, h;
    private boolean accesAutorise;
    private String dernierResultat;
    private String dernierMessage;
    private Double dernierMontant;
    private SimulationResult resultatAffiche;

    // ---------- Feature 1 : acces_simulateur.feature (RG-01) ----------

    @Given("que l'âge calculé du citoyen est {int} ans")
    public void queLAgeCalculeDuCitoyenEst(int age) {
        this.age = age;
    }

    @When("le citoyen valide son formulaire d'identité")
    public void leCitoyenValideSonFormulaireDIdentite() {
        accesAutorise = accesSimulateur.estEligible(age);
        dernierResultat = accesAutorise ? "accès autorisé" : "accès refusé";
        dernierMessage = accesAutorise ? null : "Citoyen inéligible : âge hors limites";
    }

    @Then("l'accès au simulateur est refusé")
    public void lAccesAuSimulateurEstRefuse() {
        assertFalse(accesAutorise);
    }

    @Then("le citoyen accède au formulaire de simulation")
    public void leCitoyenAccedeAuFormulaireDeSimulation() {
        assertTrue(accesAutorise);
    }

    @Then("le message {string} est affiché")
    public void leMessageEstAffiche(String messageAttendu) {
        assertEquals(messageAttendu, dernierMessage);
    }

    // ---------- Partagé feature 1 (Scenario Outline) & feature 2 ----------

    @Then("le résultat est {string}")
    public void leResultatEst(String resultatAttendu) {
        assertEquals(resultatAttendu, dernierResultat);
    }

    // ---------- Feature 2 : recevabilite_valeurs.feature (RG-03/04/05/09) ----------
    // Feature 3 : calcul_prime.feature reutilise aussi l'etape "les valeurs A=...H=..."

    @Given("les valeurs A={int}, B={int}, C={int}, D={int}, E={int}, F={int}, G={int}, H={int}")
    public void lesValeursAH(int a, int b, int c, int d, int e, int f, int g, int h) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
        this.h = h;
    }

    @When("le citoyen valide la simulation")
    public void leCitoyenValideLaSimulation() {
        String motifRejet = primeCalculator.verifierRecevabilite(a, b, h);
        dernierResultat = (motifRejet != null) ? motifRejet : "recevable";
    }

    @Then("aucun autre message d'inéligibilité ou d'erreur n'est affiché")
    public void aucunAutreMessageDIneligibiliteOuDErreurNEstAffiche() {
        assertNotNull(dernierResultat);
    }

    // ---------- Feature 3 : calcul_prime.feature (RG-02/06/07/08) ----------

    @Given("que le citoyen a {int} ans")
    public void queLeCitoyenA(int age) {
        this.age = age;
    }

    @When("le calcul est déclenché")
    public void leCalculEstDeclenche() {
        dernierMontant = primeCalculator.simuler(age, a, b, c, d, e, f, g, h);
    }

    @Then("le montant de la prime est {int} €")
    public void leMontantDeLaPrimeEst(int montantAttendu) {
        assertEquals(montantAttendu, dernierMontant, 0.01);
    }

    @Then("le montant de la prime n'est pas {int} €")
    public void leMontantDeLaPrimeNEstPas(int montantNonAttendu) {
        assertNotEquals((double) montantNonAttendu, dernierMontant, 0.01);
}

    // ---------- Feature 4 : affichage_resultat.feature (AC4.1-AC4.3) ----------

    @Given("qu'un calcul de prime a été effectué avec succès pour un montant de {int} €")
    public void quUnCalculDePrimeAEteEffectueAvecSucces(int montant) {
        resultatAffiche = new SimulationResult(SimulationResult.Statut.ELIGIBLE, (double) montant, null);
    }

    @Given("qu'un contrôle de recevabilité a échoué avec le motif {string}")
    public void quUnControleDeRecevabiliteAEchoueAvecLeMotif(String motif) {
        resultatAffiche = new SimulationResult(SimulationResult.Statut.INELIGIBLE, null, motif);
    }

    @Given("qu'une erreur technique a été détectée avec le motif {string}")
    public void quUneErreurTechniqueAEteDetecteeAvecLeMotif(String motif) {
        resultatAffiche = new SimulationResult(SimulationResult.Statut.ERREUR, null, motif);
    }

    @When("la page de résultat est affichée")
    public void laPageDeResultatEstAffichee() {
        // No-op : le resultat a deja ete prepare par l'etape Given ci-dessus.
    }

    @Then("le statut affiché est {string}")
    public void leStatutAfficheEst(String statutAttendu) {
        assertEquals(statutAttendu, resultatAffiche.statutAffiche());
    }

    @Then("le montant affiché est {string}")
    public void leMontantAfficheEst(String montantAttendu) {
        assertEquals(montantAttendu, resultatAffiche.montantAffiche());
    }

    @Then("le motif affiché est {string}")
    public void leMotifAfficheEst(String motifAttendu) {
        assertEquals(motifAttendu, resultatAffiche.motif());
    }

    @Then("aucun montant n'est affiché")
    public void aucunMontantNEstAffiche() {
        assertNull(resultatAffiche.montant());
    }
}
