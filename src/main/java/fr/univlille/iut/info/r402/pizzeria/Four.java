package fr.univlille.iut.info.r402.pizzeria;

import java.util.*;

public class Four {
    static final int MAX_STORAGE = 3;
    private int stored = 0;
    private Stack<Pizza> pizzasInStorage = new Stack<Pizza>();

    public Four() {
    }

    public void addPizza(Pizza p) {
        if (stored < MAX_STORAGE) {
            pizzasInStorage.add(p);
            stored++;
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
        return pizzasInStorage.pop();
    }

    public Stack<Pizza> getCurrentlyInStorage() {
        return pizzasInStorage;
    }

    public int getStored() {
        return stored;
    }
}
