package be.fodeco.simulateurprime;

/**
 * Controle d'acces au simulateur selon l'age du citoyen.
 * Regle metier RG-01 : age < 18 OU age > 80 => acces refuse ; 18 <= age <= 80 => acces autorise.
 */
public class AccesSimulateur {

    /**
     * @param age age calcule du citoyen, en annees
     * @return true si le citoyen peut acceder au formulaire de simulation, false sinon
     */
    public boolean estEligible(int age) {
    return age >= 18 && age <= 80;
}
}