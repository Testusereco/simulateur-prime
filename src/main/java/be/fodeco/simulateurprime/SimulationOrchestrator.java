package be.fodeco.simulateurprime;

/**
 * Orchestre l'appel aux regles metier (RG-02 a RG-09) pour produire un SimulationResult complet.
 * Suppose que l'age a deja ete valide par AccesSimulateur (RG-01) en amont.
 */
public class SimulationOrchestrator {

    private final PrimeCalculator primeCalculator = new PrimeCalculator();

    public SimulationResult orchestrer(int age, int a, int b, int c, int d, int e, int f, int g, int h) {
        String motifRejet = primeCalculator.verifierRecevabilite(a, b, h);
        if (motifRejet != null) {
            SimulationResult.Statut statut = motifRejet.startsWith("erreur")
                ? SimulationResult.Statut.ERREUR
                : SimulationResult.Statut.INELIGIBLE;
            return new SimulationResult(statut, null, motifRejet);
        }

        try {
            double montant = primeCalculator.simuler(age, a, b, c, d, e, f, g, h);
            return new SimulationResult(SimulationResult.Statut.ELIGIBLE, montant, null);
        } catch (DenominateurNulException ex) {
            return new SimulationResult(SimulationResult.Statut.ERREUR, null, ex.getMessage());
        }
    }
}
