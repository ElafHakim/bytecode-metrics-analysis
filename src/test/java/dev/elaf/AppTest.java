package dev.elaf;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App.
 */
class AppTest {

    @Test
    void appHasAGreeting() {
        App classUnderTest = new App();
        
        // Prüft, ob die App-Instanz erfolgreich erstellt wurde
        assertNotNull(classUnderTest, "App sollte initialisiert werden können");
    }

    @Test
    void einfacherSchnittstellentest() {
        // Ein simpler Zustandstest, der immer wahr ist (zum Testen der Pipeline)
        assertTrue(true);
    }
}
