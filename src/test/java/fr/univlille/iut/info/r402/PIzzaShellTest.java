package fr.univlille.iut.info.r402;

import fr.univlille.iut.info.r402.controller.PizzaShell;
import fr.univlille.iut.info.r402.pizzeria.Four;
import fr.univlille.iut.info.r402.pizzeria.Pizza;
import fr.univlille.iut.info.r402.pizzeria.Pizzaiolo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Stack;

public class PIzzaShellTest {
    PizzaShell ps = new PizzaShell();
    Four four = ps.getFour();
    Pizzaiolo farid = ps.getFarid();
    @Test
    public void testCuireTout() throws Exception {
        Assertions.assertEquals(0, four.getStored());
        ps.treatCommand("prep pizza reine");
        ps.treatCommand("prep pizza reine");
        ps.treatCommand("prep pizza reine");
        ps.treatCommand("cuire pizza tout");
        Assertions.assertEquals(3, four.getStored());
        ps.treatCommand("retirer pizza reine");
        Assertions.assertEquals(2, four.getStored());
        ps.treatCommand("cuire pizza reine");
        Assertions.assertEquals(3, four.getStored());
    }
}
