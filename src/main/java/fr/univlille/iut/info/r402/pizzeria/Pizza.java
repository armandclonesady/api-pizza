package fr.univlille.iut.info.r402.pizzeria;

public class Pizza {
    private boolean isCooked = false;
    private boolean underCooked = true;
    static final int TTC = 30;
    private int timeCooked = 0;
    PizzaTypes type;

    public Pizza(PizzaTypes type) {
        this.type = type;
    }

    public Pizza() {
        this(PizzaTypes.MARGHERITA);
    }

    public boolean getCooked() {
        return isCooked;
    }

    public void cook() {
        timeCooked++;
        if (timeCooked >= TTC) {
            setCooked();
        }
    }

    private void setCooked() {
        isCooked = true;
        underCooked = false;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "isCooked=" + isCooked +
                ", underCooked=" + underCooked +
                ", timeCooked=" + timeCooked +
                ", type=" + type +
                '}';
    }
}
