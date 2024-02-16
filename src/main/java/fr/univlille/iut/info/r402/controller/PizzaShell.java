package fr.univlille.iut.info.r402.controller;


import fr.univlille.iut.info.r402.exceptions.IllegalCommandException;
import fr.univlille.iut.info.r402.exceptions.UnknownArgumentException;
import fr.univlille.iut.info.r402.exceptions.UnknownPizzaException;
import fr.univlille.iut.info.r402.exceptions.WrongArgumentNumberException;
import fr.univlille.iut.info.r402.help.HelpParser;
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

    public Pizzaiolo getFarid() {
        return farid;
    }

    public Four getFour() {
        return four;
    }

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
        setCurrentCommand(inputedLine);
    }

    public void setCurrentCommand(String inputedLine) {
        String[] splitedLine = inputedLine.split(" ");
        this.currentCommand = new ArrayList<String>(List.of(splitedLine));
    }

    private void showPrompt() {
        System.out.print("-->[FaridsPizza]--> ");
    }

    public void treatCommand() throws IllegalCommandException, Exception {
        String commandWord = currentCommand.get(0);
        if (RecognizedWords.getCommandeAsArray().contains(commandWord)) {
            treatFirstCommandWord(commandWord);
        } else {
            throw new IllegalCommandException("Commande non reconnue:" + commandWord + "veuillez entrer une commande valide");
        }
    }

    public void treatCommand(String command) throws IllegalCommandException, Exception {
        setCurrentCommand(command);
        treatCommand();
    }

    private void treatFirstCommandWord(String commandWord) throws Exception {
        for (RecognizedWords rw : RecognizedWords.values()) {
            if (rw.getCommande().contains(commandWord)) {
                if (rw.getNombreDeParametres() == currentCommand.size() - 1) {
                    okNowActuallyTreatTheCommand(rw);
                } else {
                    throw new WrongArgumentNumberException("Nb argument incorrect pour la commande \"" + rw.getCommande() + "\" devrait être " + rw.getNombreDeParametres() + " mais est " + (currentCommand.size() - 1));
                }
            }
        }
    }

    private void okNowActuallyTreatTheCommand(RecognizedWords rw) throws Exception {
        switch (rw) {
            case CUIRE:
                treatCuire(currentCommand.get(1), currentCommand.get(2));
                break;
            case PREPARER:
                treatPreparer(currentCommand.get(1), currentCommand.get(2));
                break;
            case RETIRER:
                treatRetirer(currentCommand.get(1), currentCommand.get(2));
                break;
            case VOIR:
                treatVoir(currentCommand.get(1));
                break;
            case MAN:
                treatMan(currentCommand.get(1));
                break;
            case HELP:
                treatHelp();
                break;
            default:
                break;
        }
    }

    private void treatMan(String arg1) {
        HelpParser hp = new HelpParser();
        arg1 = RecognizedWords.getPrimaryCommandWord(arg1);
        hp.showCommandMan(arg1);
    }

    private void treatHelp() {
        System.out.println("Commandes reconnues:");
        for (RecognizedWords rw : RecognizedWords.values()) {
            System.out.println(rw.getCommande().get(0));
            for (int i = 1; i < rw.getCommande().size(); i++) {
                System.out.println("\t" + rw.getCommande().get(i) + " ");
            }
        }
    }

    private void treatRetirer(String arg1, String arg2) throws Exception {
        if (!arg1.equals("pizza")) {
            throw new UnknownArgumentException("Argument non valide: " + arg1 + " veuillez entrer une commande valide");
        }
        farid.retirerPizza(PizzaTypes.fromString(arg2), four);
    }

    private void treatPreparer(String arg1, String arg2) throws UnknownArgumentException, UnknownPizzaException {
        if (!arg1.equals("pizza")) {
            throw new UnknownArgumentException("Argument non valide: " + arg1 + " veuillez entrer une commande valide");
        }
        farid.preparerPizza(PizzaTypes.fromString(arg2));
    }

    private void treatCuire(String arg1, String arg2) throws Exception {
        if (!arg1.equals("pizza")) {
            throw new UnknownArgumentException("Argument non valide: " + arg1 + " veuillez entrer une commande valide");
        }
        if (arg2.equals("tout")) {
            farid.mettreDansFour(four);
        } else {
            farid.mettreDansFour(PizzaTypes.fromString(arg2), four);
        }
    }

    private void treatVoir(String arg1) {
        if (arg1.equals("four")) {
            voirFour();
        } else if (arg1.equals("pizza-types")) {
            voirPizzas();
        } else if (arg1.equals("pizza")) {
            voirPizza();
        } else {
            throw new IllegalArgumentException("Argument non valide: " + currentCommand.get(1) + " veuillez entrer une commande valide");
        }
    }

    private void voirPizza() {
        System.out.println(farid.toString());
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
