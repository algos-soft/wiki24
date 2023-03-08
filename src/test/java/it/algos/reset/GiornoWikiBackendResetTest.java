package it.algos.reset;

import it.algos.*;
import it.algos.backend.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 07-Mar-2023
 * Time: 19:22
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("reset")
@DisplayName("GiornoWiki Reset")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiornoWikiBackendResetTest extends GiornoWikiBackendTest {

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
