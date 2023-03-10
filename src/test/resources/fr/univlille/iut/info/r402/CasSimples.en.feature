# language: en
# https://cucumber.io/docs/gherkin/reference/
Feature:

  Scenario: Un élève moyen
    Given un élève
    And son dossier présente une moyenne de 12 dans l'UE 1 au Semestre 1
    When il passe en jury de Semestre 1
    Then son UE 1 du Semestre 1 est validée

  Scenario: Un mauvais élève
    Given un élève
    And son dossier présente une moyenne de 8 dans l'UE 1 au Semestre 1
    When il passe en jury de Semestre 1
    Then son UE 1 du Semestre 1 n'est pas validée