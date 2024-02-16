package fr.univlille.iut.info.r402.controller;


import fr.univlille.iut.info.r402.exceptions.IllegalCommandException;
import fr.univlille.iut.info.r402.exceptions.UnknownArgumentException;
import fr.univlille.iut.info.r402.exceptions.WrongArgumentNumberException;
import fr.univlille.iut.info.r402.pizzeria.Four;
import fr.univlille.iut.info.r402.pizzeria.Pizza;
import fr.univlille.iut.info.r402.pizzeria.PizzaTypes;
import fr.univlille.iut.info.r402.pizzeria.Pizzaiolo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class PizzaShell {
    public List<String> currentCommand = new ArrayList<>();
    static final private Scanner userInput = new Scanner(System.in);

    Pizzaiolo farid = new Pizzaiolo();
    Four four = new Four();

    public void run() {
        doTheWholeShebang();
        while (!treatExit()) {
            doTheWholeShebang();
        }
    }

    private void doTheWholeShebang() {
        askForInput();
        try {
            treatCommand();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean treatExit() {
        return currentCommand.get(0).equals("exit");
    }

    private void askForInput() {
        showPrompt();
        String inputedLine = userInput.nextLine().toLowerCase();
        String[] splitedLine = inputedLine.split(" ");
        this.currentCommand = new ArrayList<String>(List.of(splitedLine));
    }

    private void showPrompt() {
        System.out.print("-->[FaridsPizza]--> ");
    }

    private void treatCommand() throws IllegalCommandException, WrongArgumentNumberException, UnknownArgumentException {
        String commandWord = currentCommand.get(0);
        if (RecognizedWords.getCommandeAsArray().contains(commandWord)) {
            treatFirstCommandWord(commandWord);
        } else {
            throw new IllegalCommandException("Commande non reconnue:" + commandWord + "veuillez entrer une commande valide");
        }
    }

    private void treatFirstCommandWord(String commandWord) throws WrongArgumentNumberException, UnknownArgumentException {
        for (RecognizedWords rw : RecognizedWords.values()) {
            if (rw.getCommande().contains(commandWord)) {
                System.out.println("Commande reconnue -> " + rw.getCommande() + " ; " + rw.getNombreDeParametres());
                if (rw.getNombreDeParametres() == currentCommand.size() - 1) {
                    okNowActuallyTreatTheCommand(rw);
                } else {
                    throw new WrongArgumentNumberException("Nb argument incorrect pour la commande \"" + rw.getCommande() + "\" devrait être " + rw.getNombreDeParametres() + " mais est " + (currentCommand.size() - 1));
                }
            }
        }
    }

    private void okNowActuallyTreatTheCommand(RecognizedWords rw) throws UnknownArgumentException {
        switch (rw) {
            case CUIRE:
                treatCuire();
                break;
            case PREPARER:
                treatPreparer(currentCommand.get(1), currentCommand.get(2));
                break;
            case RETIRER:
                treatRetirer();
                break;
            case VOIR:
                treatVoir(currentCommand.get(1));
                break;
            case MAN:
                treatMan();
                break;
            default:
                break;
        }
    }

    private void treatMan() {
        System.out.println("Commandes reconnues:");
        for (RecognizedWords rw : RecognizedWords.values()) {
            System.out.println(rw.getCommande().get(0));
            for (int i = 1; i < rw.getCommande().size(); i++) {
                System.out.println("\t" + rw.getCommande().get(i) + " ");
            }
        }
    }

    private void treatRetirer() {
    }

    private void treatPreparer(String arg1, String arg2) throws UnknownArgumentException {
        if (!arg1.equals("pizza")) {
            throw new UnknownArgumentException("Argument non valide: " + arg1 + " veuillez entrer une commande valide");
        }
    }

    private void treatCuire() {
    }

    private void treatVoir(String arg1) {
        if (arg1.equals("four")) {
            voirFour();
        } else if (arg1.equals("pizza-types")) {
            voirPizzas();
        } else {
            throw new IllegalArgumentException("Argument non valide: " + currentCommand.get(1) + " veuillez entrer une commande valide");
        }
    }

    private void voirPizzas() {
        for (PizzaTypes pt : PizzaTypes.values()) {
            System.out.println(pt.getName());
        }
    }

    private void voirFour() {
        Stack<Pizza> pizzasInStorage = four.getCurrentlyInStorage();
        if (pizzasInStorage.empty()) {
            System.out.println("Aucune pizza dans le four");
        } else {
            System.out.println("Pizzas dans le four:" + pizzasInStorage.size());
            for (Pizza p : pizzasInStorage) {
                System.out.println(p);
            }
        }
    }
}
