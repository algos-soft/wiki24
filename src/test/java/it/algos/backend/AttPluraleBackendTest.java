package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 02-Apr-2023
 * Time: 19:42
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("backend")
@DisplayName("AttPlurale Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AttPluraleBackendTest extends WikiBackendTest {

    @InjectMocks
    private AttPluraleBackend backend;

    private List<AttPlurale> listaBeans;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.attPluraleBackend;
        super.entityClazz = AttPlurale.class;
        super.typeBackend = TypeBackend.nessuno;
        super.crudBackend = backend;
        super.wikiBackend = backend;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

//    @Test
//    @Order(13)
//    @DisplayName("13 - resetOnlyEmpty  (non previsto su wiki)")
//    protected void resetOnlyEmpty() {
//        ottenutoRisultato = crudBackend.resetOnlyEmpty();
//    }

//    @Test
//    @Order(14)
//    @DisplayName("14 - resetForcing")
//    protected void resetForcing() {
//    }

    @Test
    @Order(21)
    @DisplayName("21 - isExistById")
    protected void isExistById() {
        System.out.println("21 - isExistById");
        System.out.println(VUOTA);

        System.out.println(VUOTA);
        NAZIONALITA_PLURALE().forEach(this::isExistByIdBase);
    }

    //--nome atrtività plurale (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    void isExistByIdBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];

        ottenutoBooleano = super.isExistById(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }

}
