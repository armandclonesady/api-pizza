# TP - Qualité de développement

## Documentation à partir des tests

### Objectifs des tests
Les tests d'un projet visent 3 objectifs :

* La documentation fonctionnelle du projet
* La non régression
* Faire émerger le code

### Comment générer la documentation à partir des tests
Il existe différentes façons d'utiliser les tests pour documenter un projet.·

* Utiliser des **noms de méthode explicite** évoquant avec précision les différentes règles métier.
* **Construire un Domain Specific Language** (DSL) de test avec le vocabulaire du métier.
* Utiliser du **Gherkin pour décrire les scénarios** de tests
* **Générer des fichiers structurés** de documentation depuis les tests

Dans ce TP nous allons utiliser du **Gherkin** pour produire la documentation executable.

## Gherkin

### Ajout de la dépendance

Dans le fichier `pom.xml` ajouter la dépendence vers cucumber.

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

### Des exemples ou scenario

Créez un fichier `src/test/resources/fr/univlille/iut/info/r402/CasSimple.en.feature` et ajoutez-y ce contenu :

```gherkin
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
```

En Gherkin, une "**Feature**" (Fonctionalité en français) correspond à une histoire utilisateur dans le monde de l'agilité.

On retrouve donc, un titre : `Ma première fonctionalité` et la description de l'histoire utilisateur :
- **Qui** : en tant que 
- **Quoi** : je fais
- **Pourquoi** : afin de

1. Une Feature est illustré par plusieurs exemples, les "**Scenari**". 
1. Un "**Scenario**" est lui-même découpé en 3 étapes : 
    1. "**Given**"
    1. "**When**" et 
    1. "**Then**".

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
### Lien avec votre code

#### Créer un fichier java

Créez un fichier `src/test/java/fr/univlille/iut/info/r402/fr.univlille.iut.info.r402.PizzaStepdefs.java` et définissez-y les étapes suivantes :

```java
public class PizzaStepdefs {
    @Given("^un four")
    public void unFour() {
    }

    @And("^un pizzaiolo")
    public void unPizzaiolo() {
    }

    @And("^le pizzaiolo prépare une pizza reine")
    public void preparerReine() {
    }
    @When("^le pizzaiolo met la pizza reine au four$")
    public void pizzaAuFour() {
    }

    @Then("au bout de {int} ticks d'horloge, la pizza est cuite")
    public void pizzaCuite(int ticks) {
    }
}
```

C'est ce que nous appelons du **code de glue**, c'est-à-dire du code qui permet de faire le lien entre le Gherkin et vos classes métier.

**Notez que le Gherkin ne nous permet pas de définir la façon d'interagir avec notre code.**

Lancez les tests, ils doivent être verts.
En effet, il n'y a aucun assert dans notre code.

## Codons la glue

Dans la méthode `pizzaCuite` nous nous attendons à avoir quelques chose qui raconte que si l'on interroge 
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

## Gherkin en français

#### Gherkin

Ajoutez un fichier `src/test/resources/fr/univlille/iut/info/r402/CasSimple.fr.feature` avec ce contenu :

```gherkin
# language: fr
# https://cucumber.io/docs/gherkin/reference/
Fonctionnalité: Ma première fonctionalité :
  En tant que joueur
  je peux préparer une pizza
  afin de la vendre

  Scénario: Un pizza reine
    Étant donné un four
    Et un pizzaiolo
    Et le pizzaiolo prépare une pizza reine
    Quand le pizzaiolo met la pizza reine au four
    Alors au bout de 30 ticks d'horloge, la pizza est cuite
```

# Le jeu de pizza

Ajouter de nouvelles fonctionnalités en ATDD ou BDD. Vous pouvez vérifier le taux de couverture technique en utilisant la commande `mvn install site surefire-report:report` avant d'ouvrir la page `x-www-browser target/site/jacoco/index.html`.

Toutes les interactions se ferons au clavier à travers une interface texte avec un prompte comme un shell dans lequel on entrera les commandes d'actions : `cuire pizza reine`, `préparer pizza reine`, `voir four`, etc.

Fonctionnalités à ajouter :
* Une pizza est cuite au bout de 30s, ajouter une commande pour voir combien il y a de pizza dans le four
* La capacité du four est limité, il n'est pas possible de mettre plus de 3 pizzas dedans
* Il faut faire une action pour vendre une pizza cuite, ajouter l'action `vendre`
* Ajouter le montant de la caisse
* Une pizza cuite depuis plus de 60s est invendable, ajouter une action mettre à la poubelle
* maintenant que vous avez des sous, il va falloir acheter les ingrédients, ajouter l'achat des ingrédients de base pour faire vos différentes pizzas, il n'est alors possible de faire une pizza que si les ingrédients sont en stock.
* Ajouter l'achat d'un four plus grand
* Ajouter des prix différents en fonction des pizzas, avec des prix différents pour les ingrédients.
* Ajouter le fait que les ingrédients ne peuvent pas se garder indéfiniment.
* etc.

# Lancer des traitements en tache de fond

Avec la classe `Timer` et la méthode `scheduleAtFixedRate` il est possible de lancer un traitement à interval régulier. Il existe aussi une classe `CountDownLatch` pour gérer les comptes à rebourg
