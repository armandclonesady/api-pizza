package fr.univlille.iut.info.r402.pizzeria;

import java.util.ArrayList;
import java.util.List;

public class Pizzaiolo {
    private List<Pizza> currentPizza = new ArrayList<>();

    public void preparerPizza(PizzaTypes type) {
        this.currentPizza.add(new Pizza(type));
    }
    public void mettreDansFour(Four four) {
        if (this.currentPizza.isEmpty()) {
            throw new IllegalStateException("Aucune pizza à mettre dans le four");
        }
        for (Pizza pizza : this.currentPizza) {
            mettreDansFour(pizza.type, four);
        }
        this.currentPizza.clear();
    }
    public void mettreDansFour(PizzaTypes pizza, Four four) {
        Pizza pizzaToPutInFour = getPizza(pizza);
        if (four.addPizza(pizzaToPutInFour)) {
            this.currentPizza.remove(pizza);
        } else {
            throw new IllegalStateException("Le four est plein");
        }

    }

    private Pizza getPizza(PizzaTypes type) {
        for (Pizza pizza : this.currentPizza) {
            if (pizza.type.equals(type)) {
                return pizza;
            }
        }
        throw new IllegalStateException("La pizza " + type + " n'est pas dans la liste des pizzas");
    }

    public List<Pizza> getCurrentPizza() {
        return this.currentPizza;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Pizza pizza : this.currentPizza) {
            sb.append(pizza).append("\n");
        }
        return sb.toString();
    }

    public void retirerPizza(PizzaTypes pizzaTypes, Four four) {
        addPizza(four.retirerPizza(pizzaTypes));
    }

    public void addPizza(Pizza pizza) {
        this.currentPizza.add(pizza);
    }
}
