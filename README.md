# Documentation depuis les tests

Les tests d'un projet visent 3 objectifs :

* La documentation fonctionnelle du projet
* La non régression
* Faire émerger le code

Il existe différentes façons d'utiliser les tests pour documenter un projet.·

* Utiliser des noms de méthode explicite évoquant avec précision les différentes règles métier.
* Construire un DSL de test avec le vocabulaire du métier.
* Utiliser du Gherkin pour décrire les scénarios de tests
* Générer des fichiers structurés de documentation depuis les tests

Dans le [TP2](../tp2/) nous avons testé la dernière option, dans ce TP nous allons utiliser du Gherkin.

# Du Gherkin

## Ajout de la dépendance

Dans le fichier `pom.xml` ajouter le gestionaire de dépendances vers cucumber.

```xml

<dependencyManagement>
    ...
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-bom</artifactId>
        <version>7.11.1</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
    ...
</dependencyManagement>
```

ainsi que les dépendances :

```xml

<dependencies>
    ...
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-java</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-junit-platform-engine</artifactId>
        <scope>test</scope>
    </dependency>
    ...
</dependencies>
```

## Des exemples ou scenario

Ajoutez un fichier `src/test/resources/fr/univlille/iut/info/r402/CasSimples.en.feature` avec ce contenu :

```gherkin
# language: en
# https://cucumber.io/docs/gherkin/reference/
Feature: Ma première fonctionalité :
  En tant qu'étudiant,
  il "suffit" d'avoir la moyenne dans toutes les UE de la formation
  afin d'avoir son diplome.

  Scenario: Un élève moyen
    Given un élève
    And son dossier présente une moyenne de 12 dans l'UE 1 au Semestre 1
    When il passe en jury de Semestre 1
    Then son UE 1 du Semestre 1 est validée
```

En Gherkin, une "Feature" (Fonctionalité en français) correspond à une histoire utilisateur dans le monde de l'agilité.
On retrouve donc, un titre : `Ma première fonctionalité` et la description de l'histoire utilisateur :

> En tant que **Qui** je fais **Quoi** afin de **Pourquoi**.

Une Feature est illustré par plusieurs exemples, les "Scenario".
Le "Scenario" est lui-même découpé en 3 étapes : "Given", "When" et "Then".

## Une suite de test pour tout lancer

Ajouter un fichier `src/test/java/fr/univlille/iut/info/r402/RunCucumberTest.java` pour lancer la suite de tests :

```java

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("fr/univlille/iut/info/r402")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "fr.univlille.iut.info.r402")
public class RunCucumberTest {
}
```

## Lien avec votre code

Ajouter un fichier `src/test/java/fr/univlille/iut/info/r402/JuryStepdefs.java` pour définir
les étapes Gherkin :

```java
public class JuryStepdefs {
    @Given("^un élève")
    public void unEleve() {
    }

    @And("son dossier présente une moyenne de {int} dans l'UE {int} au Semestre {int}")
    public void sonDossierPresenteUneMoyenneDeDansLUEAuSemestre(int evaluation, int UEId, int semestreId) {
    }

    @When("^il passe en jury de Semestre (\\d+)$")
    public void ilPasseEnJuryDeSemestre(int SemestreId) {
    }

    @Then("son UE {int} du Semestre {int} est validée")
    public void sonUEDuSemestreEstValidee(int UEid, int semestreID) {
    }
}
```

C'est ce que nous appelons du code de glue, c'est-à-dire du code qui permet de faire le lien entre le Gherkin et vos
classes métier.
Le Gherkin ne nous permet pas de définir la façon d'interagir avec notre code, c'est le code de glue qui s'en occupe.

Lancez les tests, ils sont verts, en effet, il n'y a aucun assert dans notre code.

Notez que les `assert` vont dans les méthodes annotés `@Then`

## Codons la glue

Dans la méthode `sonUEDuSemestreEstValidee` nous nous attendons à avoir quelques chose qui raconte que :

* pour l'élève défini en première étape,
* en allant rechercher son semestre 1
* puis l'UE 1
* on peut voir que l'UE 1 est Validée

Ce qui pourrait s'écrire en java comme ceci :

```java
    assertEquals(Validee, eleve.getUEForSemestre(UEid, semestreID).getAcquisition());
```

Sauf que l'élève n'existe pas, on peut donc l'ajouter dans l'étape `unEleve()` :

```java
    eleve = new Etudiant();
```

De la même façon, l'étape `sonDossierPresenteUneMoyenneDeDansLUEAuSemestre()` va créer une UE avec la bonne moyenne,
l'ajouter au semestre qui va lui-même être rattaché à l'élève.

L'étape `ilPasseEnJuryDeSemestre()` va appeler la méthode `deliberation` du jury puis mettre à jour les UEs concernées.

Finir le code de glue et l'implémentation minimum pour faire passer ce premier test au vert.

## Nouveau test

Ajouter le scénario suivant dans le fichier `.feature`

```gherkin
  Scenario: Un mauvais élève
    Given un élève
    And son dossier présente une moyenne de 8 dans l'UE 1 au Semestre 1
    When il passe en jury de Semestre 1
    Then son UE 1 du Semestre 1 n'est pas validée
```

* Ajouter la définition d'étape cucumber correspondante
* Le faire passer au vert.

## Gherkin en français

Ajoutez un fichier `src/test/resources/fr/univlille/iut/info/r402/BUT.fr.feature` avec ce contenu :

```gherkin
# language: fr
# https://cucumber.io/docs/gherkin/reference/
Fonctionnalité: Ma première fonctionalité :
  En tant qu'étudiant,
  il "suffit" d'avoir la moyenne dans toutes les UE de la formation
  afin d'avoir son diplome.

  Scénario: Un élève moyen
    Étant donné un élève
    Et que son dossier présente une moyenne de 12 dans l'UE 1 au Semestre 1
    Quand il passe en jury de Semestre 1
    Alors son UE 1 du Semestre 1 est validée

  Scénario: Un mauvais élève
    Étant donné un élève
    Et que son dossier présente une moyenne de 8 dans l'UE 1 au Semestre 1
    Quand il passe en jury de Semestre 1
    Alors son UE 1 du Semestre 1 n'est pas validée
```

# BUT

Écrire et implémenter toutes les règles de validation du BUT en TDD avec les tests rédigés en Gherkin.

Le journal officiel présente les conditions de validation du BUT de la façon suivante :

4.3 Conditions de validation
Le bachelor universitaire de technologie s'obtient soit par acquisition de
chaque unité d'enseignement constitutive, soit par application des modalités de
compensation. Le bachelor universitaire de technologie obtenu par l'une ou
l'autre voie confère la totalité des 180 crédits européens. Une unité
d'enseignement est définitivement acquise et capitalisable dès lors que la
moyenne obtenue à l’ensemble « pôle ressources » et « SAÉ » est égale ou
supérieure à 10. L'acquisition de l'unité d'enseignement emporte l'acquisition
des crédits européens correspondants. À l'intérieur de chaque unité
d'enseignement, le poids relatif des éléments constitutifs, soit des pôles «
ressources » et « SAÉ », varie dans un rapport de 40 à 60%. En troisième année
ce rapport peut toutefois être apprécié sur l’ensemble des deux unités
d’enseignement d’une même compétence.

La validation des deux UE du niveau d’une compétence emporte la validation de
l’ensemble des UE du niveau inférieur de cette même compétence.

4.4 Compensation
La compensation s’effectue au sein de chaque unité d’enseignement ainsi qu’au
sein de chaque regroupement cohérent d’UE. Seules les UE se référant à un même
niveau d’une même compétence finale peuvent ensemble constituer un regroupement
cohérent. Des UE se référant à des niveaux de compétence finales différents ou
à des compétences finales différentes ne peuvent pas appartenir à un même
regroupement cohérent. Aucune UE ne peut appartenir à plus d’un regroupement
cohérent. Au sein de chaque regroupement cohérent d’UE, la compensation est
intégrale. Si une UE n’a pas été acquise en raison d’une moyenne inférieure à
10, cette UE sera acquise par compensation si et seulement si l’étudiant a
obtenu la moyenne au regroupement cohérent auquel l’UE appartient.

4.5 Règles de progression
La poursuite d'études dans un semestre pair d’une même année est de droit pour
tout étudiant. La poursuite d’études dans un semestre impair est possible si et
seulement si l’étudiant a obtenu : la moyenne à plus de la moitié des
regroupements cohérents d’UE ; et une moyenne égale ou supérieure à 8 sur 20 à
chaque regroupement cohérent d’UE. La poursuite d'études dans le semestre 5
nécessite de plus la validation de toutes les UE des semestres 1 et 2 dans les
conditions de validation des points 4.3 et 4.4, ou par décision de jury.
Durant la totalité du cursus conduisant au bachelor universitaire de
technologie, l'étudiant peut être autorisé à redoubler une seule fois chaque
semestre dans la limite de 4 redoublements. Le directeur de l'IUT peut
autoriser un redoublement supplémentaire en cas de force majeure dûment
justifiée et appréciée par ses soins. Tout refus d'autorisation de redoubler
est pris après avoir entendu l'étudiant à sa demande. Il doit être motivé et
assorti de conseils d'orientation.

4.6 Jury
Le jury présidé par le directeur de l’IUT délibère souverainement à partir de
l'ensemble des résultats obtenus par l'étudiant. Il se réunit chaque semestre
pour se prononcer sur la progression des étudiants, la validation des unités
d’enseignement, l’attribution du diplôme universitaire de technologie au terme
de l’acquisition des 120 premiers crédits européens du cursus et l’attribution
de la licence professionnelle « bachelor universitaire de technologie ».

Les textes de références sont :

* https://www.enseignementsup-recherche.gouv.fr/fr/bo/21/Special4/ESRS2114777A.htm
* https://cache.media.education.gouv.fr/file/SP4-MESRI-26-5-2022/10/0/spe617_annexe1_1426100.pdf

