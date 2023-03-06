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
 * Time: 08:21
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("reset")
@DisplayName("Giorno Reset")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiornoBackendResetTest extends GiornoBackendTest {

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