@simulateur-prime @US4 @FODECOT5-5
Feature: Affichage du résultat de la simulation
  En tant que citoyen ayant complété la simulation
  Je veux visualiser le résultat (montant et éligibilité)
  Afin de connaître précisément l'issue de ma demande

  Scenario: Affichage du montant pour un citoyen éligible
    Given qu'un calcul de prime a été effectué avec succès pour un montant de 1000 €
    When la page de résultat est affichée
    Then le statut affiché est "éligible"
    And le montant affiché est "1000 €"

  Scenario: Affichage du motif pour un citoyen inéligible
    Given qu'un contrôle de recevabilité a échoué avec le motif "A < 1000"
    When la page de résultat est affichée
    Then le statut affiché est "inéligible"
    And le motif affiché est "A < 1000"
    And aucun montant n'est affiché

  Scenario: Affichage du motif en cas d'erreur technique
    Given qu'une erreur technique a été détectée avec le motif "H doit être > 0"
    When la page de résultat est affichée
    Then le statut affiché est "erreur"
    And le motif affiché est "H doit être > 0"
    And aucun montant n'est affiché