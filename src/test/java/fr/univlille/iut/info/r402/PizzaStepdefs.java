package fr.univlille.iut.info.r402;

import fr.univlille.iut.info.r402.pizzeria.Four;
import fr.univlille.iut.info.r402.pizzeria.Pizza;
import fr.univlille.iut.info.r402.pizzeria.PizzaTypes;
import fr.univlille.iut.info.r402.pizzeria.Pizzaiolo;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PizzaStepdefs {
    Four four;
    Pizzaiolo farid;
    Pizza pizza;
    @Given("^un four")
    public void unFour() {
        four = new Four();
    }

    @And("^un pizzaiolo")
    public void unPizzaiolo() {
        farid = new Pizzaiolo();
    }

    @And("^le pizzaiolo prépare une pizza reine")
    public void preparerReine() {
        farid.preparerPizza(PizzaTypes.REINE);
    }
    @When("^le pizzaiolo met la pizza reine au four$")
    public void pizzaAuFour() {
        farid.mettreDansFour(four);
    }

    @Then("au bout de {int} ticks d'horloge, la pizza est cuite")
    public void pizzaCuite(int ticks) {
        four.cuireUnCertainTemps(ticks);
        pizza = four.retirerPizza();
        assertEquals(true, pizza.getCooked());
    }
}
