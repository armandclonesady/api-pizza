package fr.univlille.iut.info.r402.pizzeria;

import java.util.*;

public class Four {
    static final int MAX_STORAGE = 3;
    private int stored = 0;
    private Stack<Pizza> pizzasInStorage = new Stack<Pizza>();

    public Four() {
    }

    public boolean addPizza(Pizza p) {
        if (stored < MAX_STORAGE) {
            pizzasInStorage.add(p);
            stored++;
            return true;
        } else {
            return false;
        }
    }
    public void cuire() {
        for (Pizza p : pizzasInStorage) {
            p.cook();
        }
    }



    public void cuireUnCertainTemps(int ticks) {
        while (ticks > 0) {
            cuire();
            ticks--;
        }
    }

    public Pizza retirerPizza() {
        stored--;
        return pizzasInStorage.pop();
    }

    public Pizza retirerPizza(PizzaTypes type) {
        for (Pizza p : pizzasInStorage) {
            if (p.type.equals(type)) {
                pizzasInStorage.remove(p);
                stored--;
                return p;
            }
        }
        throw new IllegalStateException("La pizza " + type + " n'est pas dans le four");
    }

    public Stack<Pizza> getCurrentlyInStorage() {
        return pizzasInStorage;
    }

    public int getStored() {
        return stored;
    }
}
