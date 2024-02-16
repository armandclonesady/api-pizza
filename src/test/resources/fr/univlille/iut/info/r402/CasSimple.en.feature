# language: en
# https://cucumber.io/docs/gherkin/reference/
Feature: Ma première fonctionalité :
  En tant que joueur
  je peux préparer une pizza
  afin de la vendre

  Scenario: Un pizza reine
    Given un four
    And un pizzaiolo
    And le pizzaiolo prépare une pizza reine
    When le pizzaiolo met la pizza reine au four
    Then au bout de 30 ticks d'horloge, la pizza est cuite
