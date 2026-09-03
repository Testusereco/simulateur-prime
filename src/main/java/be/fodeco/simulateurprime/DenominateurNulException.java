package be.fodeco.simulateurprime;

/**
 * Levee quand le denominateur de la formule RG-07 -- (F x G) + (B - A) -- vaut zero.
 * Cas non couvert par l'enonce metier initial ; traite comme une erreur technique,
 * au meme titre que RG-05 (H <= 0).
 */
public class DenominateurNulException extends RuntimeException {

    public DenominateurNulException(String message) {
        super(message);
    }
}