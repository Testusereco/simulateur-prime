@simulateur-prime @US1 @FODECOT5-2
Feature: Accès au simulateur selon l'âge
  En tant que citoyen belge
  Je veux pouvoir accéder à l'outil de simulation de la prime
  Afin de savoir si je remplis la condition d'âge pour effectuer une simulation

  # RG-01 : âge < 18 OU âge > 80 => accès refusé ; 18 <= âge <= 80 => accès autorisé

  @RG-01
  Scenario: Accès refusé pour un citoyen trop jeune
    Given que l'âge calculé du citoyen est 17 ans
    When le citoyen valide son formulaire d'identité
    Then l'accès au simulateur est refusé
    And le message "Citoyen inéligible : âge hors limites" est affiché

  @RG-01
  Scenario: Accès autorisé à la borne basse
    Given que l'âge calculé du citoyen est 18 ans
    When le citoyen valide son formulaire d'identité
    Then le citoyen accède au formulaire de simulation

  @RG-01
  Scenario Outline: Décision d'accès selon l'âge (valeurs limites)
    Given que l'âge calculé du citoyen est <age> ans
    When le citoyen valide son formulaire d'identité
    Then le résultat est "<resultat>"

    Examples:
      | age | resultat       |
      | 17  | accès refusé   |
      | 18  | accès autorisé |
      | 40  | accès autorisé |
      | 80  | accès autorisé |
      | 81  | accès refusé   |