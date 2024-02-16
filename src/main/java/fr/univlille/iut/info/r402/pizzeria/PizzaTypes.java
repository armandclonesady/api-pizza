package fr.univlille.iut.info.r402.pizzeria;

import fr.univlille.iut.info.r402.exceptions.UnknownPizzaException;

public enum PizzaTypes {
    REINE("reine"),
    MARGHERITA("margherita"),
    VEGETARIANA("vegetariana"),
    QUATRE_FROMAGES("quatre-fromages"),
    CALZONE("calzone"),
    PNEU("pneu"),
    PNEU_CALZONE("pneu-calzone");
    private String name;
    PizzaTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PizzaTypes fromString(String name) throws UnknownPizzaException {
        for (PizzaTypes type : PizzaTypes.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        throw new UnknownPizzaException("La pizza " + name + " n'existe pas");
    }
}
