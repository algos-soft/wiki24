package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 22-Mar-2023
 * Time: 18:49
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("backend")
@DisplayName("NazSingola Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NazSingolareBackendTest extends WikiBackendTest {

    private NazSingolareBackend backend;
    private List<NazSingolare> listaBeans;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.nazSingolaBackend;
        super.entityClazz = NazSingolare.class;
        super.typeBackend = TypeBackend.nessuno;
        super.crudBackend = backend;
        super.wikiBackend = backend;

        super.setUpAll();
    }


}
