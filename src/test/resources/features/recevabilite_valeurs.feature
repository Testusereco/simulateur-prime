@simulateur-prime @US2 @FODECOT5-3
Feature: Contrôle de recevabilité des valeurs de simulation
  En tant que citoyen éligible par l'âge
  Je veux saisir les 8 valeurs numériques demandées (A à H)
  Afin que le système vérifie si ma situation est recevable pour un calcul de prime

  # Jeu de données de base réutilisé (voir aussi calcul_prime.feature) :
  # A=1000, B=1000, C=0, D=0, E=1, F=1, G=1, H=1

  @RG-03
  Scenario Outline: Rejet si la valeur A est insuffisante
    Given les valeurs A=<A>, B=1000, C=0, D=0, E=1, F=1, G=1, H=1
    When le citoyen valide la simulation
    Then le résultat est "<resultat>"

    Examples:
      | A    | resultat               |
      | 999  | inéligible (A < 1000)  |
      | 1000 | recevable              |

  @RG-04
  Scenario Outline: Rejet si la valeur B est excessive
    Given les valeurs A=1000, B=<B>, C=0, D=0, E=1, F=1, G=1, H=1
    When le citoyen valide la simulation
    Then le résultat est "<resultat>"

    Examples:
      | B      | resultat                  |
      | 100001 | inéligible (B > 100000)   |
      | 100000 | recevable                 |

  @RG-05
  Scenario Outline: Erreur technique si H est invalide
    Given les valeurs A=1000, B=1000, C=0, D=0, E=1, F=1, G=1, H=<H>
    When le citoyen valide la simulation
    Then le résultat est "<resultat>"

    Examples:
      | H  | resultat                  |
      | 0  | erreur (H doit être > 0)  |
      | -3 | erreur (H doit être > 0)  |
      | 1  | recevable                 |

  @RG-09
  Scenario: Le premier contrôle en échec détermine seul le message affiché
    Given les valeurs A=500, B=200000, C=0, D=0, E=1, F=1, G=1, H=0
    When le citoyen valide la simulation
    Then le résultat est "inéligible (A < 1000)"
    And aucun autre message d'inéligibilité ou d'erreur n'est affiché