package fr.univlille.iut.info.r402.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum RecognizedWords {

    CUIRE(2, "cuire", "cook", "bake", "make"),
    PREPARER(2, "preparer", "prep", "prepare"),
    RETIRER(2, "retirer", "remove", "take", "take-out"),
    VOIR(1,"voir", "show", "display", "list", "see"),
    HELP(0, "help" , "aide"),
    MAN(1, "man"),
    EXIT(0, "exit", "quit", "be-a-bitch");
    private final String[] commande;
    private final int nombreDeParametres;

    RecognizedWords(int nombreDeParametres, String... commande) {
        this.commande= commande;
        this.nombreDeParametres= nombreDeParametres;
    }

    public List<String> getCommande() {
        return List.of(this.commande);
    }

    static public List<String> getCommandeAsArray() {
        ArrayList<String> res = new ArrayList<>();
        for (RecognizedWords rw : RecognizedWords.values()) {
            for (String s : rw.getCommande()) {
                res.add(s);
            }
        }
        return res;
    }

    static public String getPrimaryCommandWord(String commandWord) {
        for (RecognizedWords rw: RecognizedWords.values()
             ) {
            if (rw.getCommande().contains(commandWord)) {
                return rw.getCommande().get(0);
            }
        }
        return commandWord;
    }

    public int getNombreDeParametres() {
        return nombreDeParametres;
    }
}
