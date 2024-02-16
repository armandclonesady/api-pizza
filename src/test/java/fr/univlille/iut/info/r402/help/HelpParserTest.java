package fr.univlille.iut.info.r402.help;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelpParserTest {
    HelpParser hp = new HelpParser();

    @Test
    public void testGetFileText() {
        try {
            Assertions.assertEquals("", hp.getFileText());
            hp.extractFileText();
            Assertions.assertNotEquals("", hp.getFileText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
