@simulateur-prime @US3 @FODECOT5-4
Feature: Calcul du montant de la prime
  En tant que citoyen recevable
  Je veux que le système calcule automatiquement le montant de ma prime
  Afin de connaître la somme à laquelle je peux prétendre

  # Jeu de données "forfait" : A=1500, B=15000, C=600, D=1000, E=5, F=2, G=0, H=10
  #   => toutes les conditions RG-06 sont réunies (forfait fixe de 1000 €)
  # Jeu de données "formule" : A=1000, B=1000, C=0, D=0, E=1, F=1, G=1, H=1
  #   => C=0 < 500 rend RG-06 fausse, la formule RG-07 s'applique (prime de base = 2000 €)

  @RG-06
  Scenario Outline: Prime forfaitaire non pondérée par l'âge
    Given les valeurs A=1500, B=15000, C=600, D=1000, E=5, F=2, G=0, H=10
    And que le citoyen a <age> ans
    When le calcul est déclenché
    Then le montant de la prime est 1000 €

    Examples:
      | age |
      | 25  |
      | 45  |
      | 70  |

  @RG-07 @RG-08
  Scenario Outline: Application du taux selon la tranche d'âge exacte
    Given les valeurs A=1000, B=1000, C=0, D=0, E=1, F=1, G=1, H=1
    And que le citoyen a <age> ans
    When le calcul est déclenché
    Then le montant de la prime est <montant> €

    Examples:
      | age | montant |
      | 18  | 2000    |
      | 39  | 2000    |
      | 40  | 1800    |
      | 59  | 1800    |
      | 60  | 1600    |
      | 80  | 1600    |