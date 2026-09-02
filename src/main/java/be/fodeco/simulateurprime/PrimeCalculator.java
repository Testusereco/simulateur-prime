package be.fodeco.simulateurprime;

/**
 * Controle de recevabilite et calcul du montant de la prime.
 * Regles metier concernees : RG-02 a RG-09 (voir le catalogue des regles metier).
 */
public class PrimeCalculator {

    /**
     * Controle de recevabilite (RG-03, RG-04, RG-05), evalue dans cet ordre (RG-09).
     *
     * @return null si les valeurs sont recevables, sinon le motif de rejet
     *         (ex. "inéligible (A < 1000)", "erreur (H doit être > 0)")
     */
    public String verifierRecevabilite(int a, int b, int h) {
        throw new UnsupportedOperationException("TODO : implementer RG-03 / RG-04 / RG-05 / RG-09");
    }

    /**
     * Calcule le montant final de la prime, en supposant la recevabilite deja validee.
     * Applique RG-06 (forfait) ou RG-07 (formule) + RG-02/RG-08 (coefficient d'age).
     */
    public double simuler(int age, int a, int b, int c, int d, int e, int f, int g, int h) {
        throw new UnsupportedOperationException("TODO : implementer RG-02 / RG-06 / RG-07 / RG-08");
    }
   
}
