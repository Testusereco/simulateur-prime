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
        if (a < 1000) {
            return "inéligible (A < 1000)";
        }
        if (b > 100000) {
            return "inéligible (B > 100000)";
        }
        if (h <= 0) {
            return "erreur (H doit être > 0)";
        }
        return null;
    }

    /**
     * Calcule le montant final de la prime, en supposant la recevabilite deja validee.
     * Applique RG-06 (forfait) ou RG-07 (formule) + RG-02/RG-08 (coefficient d'age).
     *
     * @throws DenominateurNulException si le denominateur (F x G) + (B - A) vaut zero
     *         (cas non couvert par l'enonce metier initial, traite comme une erreur technique)
     */
    public double simuler(int age, int a, int b, int c, int d, int e, int f, int g, int h) {
        boolean forfaitApplicable = a >= 1000 && b <= 20000 && c >= 500 && d < 3500 && e <= 10 && g == 0;

        if (forfaitApplicable) {
            return 1000;
        }

        double denominateur = (f * g) + (b - a);
        if (denominateur == 0) {
            throw new DenominateurNulException(
                "Impossible de calculer la prime : le dénominateur (F×G)+(B−A) vaut zéro"
            );
        }

        double numerateur = (a + b + d + ((double) c / h)) * e;
        double montantFormule = numerateur / denominateur;

        double coefficient;
        if (age < 40) {
            coefficient = 1.0;
        } else if (age < 60) {
            coefficient = 0.9;
        } else {
            coefficient = 0.8;
        }

        return montantFormule * coefficient;
    }
}