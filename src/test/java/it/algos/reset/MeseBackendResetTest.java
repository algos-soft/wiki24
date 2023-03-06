package it.algos.reset;

import it.algos.*;
import it.algos.backend.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Mar-2023
 * Time: 08:05
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("reset")
@DisplayName("Mese Reset")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeseBackendResetTest extends MeseBackendTest {


    /**
     * Chiamato solo dalla sottoclasse
     */
    @Test
    @Order(92)
    @DisplayName("92 - resetForcing")
    protected void resetForcing() {
        super.resetForcing();
    }

}