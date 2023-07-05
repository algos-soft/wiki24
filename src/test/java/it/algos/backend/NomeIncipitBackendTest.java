package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.packages.nomeincipit.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 18-Jun-2023
 * Time: 12:10
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("nomibackend")
@Tag("nomi")
@Tag("backend")
@DisplayName("NomeIncipit Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NomeIncipitBackendTest extends WikiBackendTest {

    @InjectMocks
    private NomeIncipitBackend backend;

    private List<NomeIncipit> listaBeans;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.nomeTemplateBackend;
        super.entityClazz = NomeIncipit.class;
        super.typeBackend = TypeBackend.nessuno;
        super.crudBackend = backend;
        super.wikiBackend = backend;
        super.nomeModulo = "nomemodulo";

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    @Test
    @Order(15)
    @DisplayName("15 - elabora (solo su wiki)")
    protected void elabora() {
        System.out.println("15 - elabora (solo su wiki)");
        System.out.println(VUOTA);

        if (entityClazz == null) {
            System.out.println("Manca la variabile entityClazz in questo test");
            return;
        }

        ottenutoRisultato = wikiBackend.elabora();
        assertNotNull(ottenutoRisultato);
        if (ottenutoRisultato.isValido()) {
            System.out.println(ottenutoRisultato.getMessage());
            printRisultato(ottenutoRisultato);

            System.out.println(VUOTA);
            printBackend(ottenutoRisultato.getLista());
        }
        else {
            logger.warn(new WrapLog().message(ottenutoRisultato.getErrorMessage()));
        }
        assertTrue(ottenutoRisultato.isValido());
    }

    @Test
    @Order(75)
    @DisplayName("75 - findAllDistinctByPlurali")
    protected void findAllDistinctByPlurali() {
        System.out.println("75 - findAllDistinctByPlurali (non previsto per questa collection)");
    }

    @Test
    @Order(94)
    @DisplayName("94 - findAllByNotUgualiSort")
    void findAllByNotUgualiSort() {
        System.out.println(("94 - findAllByNotUgualiSort"));
        System.out.println(VUOTA);

        listaBeans = backend.findAllByNotUguali();
        assertNotNull(listaBeans);
        printBackend(listaBeans, 200);
    }

}
