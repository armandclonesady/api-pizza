package fr.univlille.iut.info.r402.pizzeria;

public class Pizzaiolo {
    private Pizza currentPizza;

    public void preparerPizza(PizzaTypes type) {
        this.currentPizza = new Pizza(type);
    }
    public void mettreDansFour(Four four) {
        four.addPizza(currentPizza);
    }
}
