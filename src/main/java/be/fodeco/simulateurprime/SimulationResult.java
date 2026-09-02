package be.fodeco.simulateurprime;

/**
 * Resultat d'une simulation, tel qu'affiche au citoyen (US4).
 *
 * @param statut  statut de la simulation
 * @param montant montant final en euros, ou null si non applicable (inéligible/erreur)
 * @param motif   motif d'inéligibilité ou d'erreur, ou null si éligible
 */
public record SimulationResult(Statut statut, Double montant, String motif) {

    public enum Statut { ELIGIBLE, INELIGIBLE, ERREUR }

    /** Libelle du statut tel qu'affiche a l'ecran : "éligible" / "inéligible" / "erreur". */
    public String statutAffiche() {
    return switch (statut) {
        case ELIGIBLE -> "éligible";
        case INELIGIBLE -> "inéligible";
        case ERREUR -> "erreur";
    };
}

    /** Montant formate tel qu'affiche a l'ecran (ex. "1000 €"), ou null si aucun montant. */
    public String montantAffiche() {
    if (montant == null) {
        return null;
    }
    return montant.intValue() + " €";
}
}
