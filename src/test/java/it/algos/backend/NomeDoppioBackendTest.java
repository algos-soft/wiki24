package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import it.algos.wiki24.backend.packages.nomedoppio.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 18-Jun-2023
 * Time: 12:09
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("nomi")
@Tag("backend")
@DisplayName("NomeDoppio Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NomeDoppioBackendTest extends WikiBackendTest {

    @InjectMocks
    private NomeDoppioBackend backend;

    private List<NomeDoppio> listaBeans;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.nomeDoppioBackend;
        super.entityClazz = NomeDoppio.class;
        super.typeBackend = TypeBackend.nessuno;
        super.crudBackend = backend;
        super.wikiBackend = backend;
        super.nomeModulo = "nomedoppio";

        super.setUpAll();
    }


    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    @Test
    @Order(75)
    @DisplayName("75 - findAllDistinctByPlurali")
    protected void findAllDistinctByPlurali() {
        System.out.println("75 - findAllDistinctByPlurali (non previsto per questa collection)");
    }

}
