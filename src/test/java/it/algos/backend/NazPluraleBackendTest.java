package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 24-Mar-2023
 * Time: 06:48
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("backend")
@Tag("wikiBackend")
@DisplayName("NazPlurale Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NazPluraleBackendTest extends WikiBackendTest {

    @InjectMocks
    private NazPluraleBackend backend;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.nazPluraleBackend;
        super.entityClazz = NazPlurale.class;
        super.typeBackend = TypeBackend.nessuno;
        super.crudBackend = backend;
        super.wikiBackend = backend;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @Test
    @Order(21)
    @DisplayName("21 - isExistById")
    protected void isExistById() {
        System.out.println("21 - isExistById");
        System.out.println(VUOTA);

        System.out.println(VUOTA);
        NAZIONALITA_PLURALE().forEach(this::isExistByIdBase);
    }

    //--nome nazionalità plurale (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    void isExistByIdBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];

//        ottenutoBooleano = super.isExistById(sorgente);
//        assertEquals(previstoBooleano, ottenutoBooleano);
    }


    @Test
    @Order(22)
    @DisplayName("22 - isExistByKey")
    protected void isExistByKey() {
        System.out.println("22 - isExistByKey");
        System.out.println(VUOTA);

        System.out.println(VUOTA);
        NAZIONALITA_PLURALE().forEach(this::isExistByKeyBase);
    }

    //--nome nazionalità plurale (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    void isExistByKeyBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[2];

//        ottenutoBooleano = super.isExistByKey(sorgente);
//        assertEquals(previstoBooleano, ottenutoBooleano);
    }


    @Test
    @Order(23)
    @DisplayName("23 - isExistByOrder")
    protected void isExistByOrder() {
        System.out.println("23 - isExistByOrder");
        System.out.println(VUOTA);

//        sorgenteIntero = 87;
//        ottenutoBooleano = super.isExistByOrder(sorgenteIntero);
//        assertFalse(ottenutoBooleano);
//        System.out.println(VUOTA);
//
//        sorgenteIntero = 0;
//        ottenutoBooleano = super.isExistByOrder(sorgenteIntero);
//        assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(24)
    @DisplayName("24 - isExistByProperty")
    protected void isExistByProperty() {
        System.out.println("24 - isExistByProperty");
        System.out.println(VUOTA);

//        sorgente = "propertyInesistente";
//        sorgente2 = "termidoro";
//        ottenutoBooleano = super.isExistByProperty(sorgente, sorgente2);
//        assertFalse(ottenutoBooleano);
//        System.out.println(VUOTA);
//
//        sorgente = "linkNazione";
//        sorgente2 = "australiano";
//        ottenutoBooleano = super.isExistByProperty(sorgente, sorgente2);
//        assertFalse(ottenutoBooleano);
//        System.out.println(VUOTA);
//
//        sorgente = "linkNazione";
//        sorgente2 = "Australia";
//        ottenutoBooleano = super.isExistByProperty(sorgente, sorgente2);
//        assertTrue(ottenutoBooleano);
    }

    @Test
    @Order(31)
    @DisplayName("31 - findById")
    protected void findById() {
        System.out.println("22 - isExistByKey");
        System.out.println(VUOTA);

        System.out.println(VUOTA);
        NAZIONALITA_PLURALE().forEach(this::findByIdBase);
    }


    //--nome nazionalità plurale (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    void findByIdBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];

//        entityBean = super.findById(sorgente);
//        assertEquals(previstoBooleano, entityBean != null);
    }

    @Test
    @Order(32)
    @DisplayName("32 - findByKey")
    protected void findByKey() {
        System.out.println("32 - findByKey");
        System.out.println(VUOTA);

        System.out.println(VUOTA);
        NAZIONALITA_PLURALE().forEach(this::findByKeyBase);
    }

    //--nome nazionalità plurale (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    void findByKeyBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[2];

//        entityBean = super.findByKey(sorgente);
//        assertEquals(previstoBooleano, entityBean != null);
    }

    @Test
    @Order(33)
    @DisplayName("33 - findByOrder")
    protected void findByOrder() {
        System.out.println("33 - findByOrder");
        System.out.println(VUOTA);

//        sorgenteIntero = 87;
//        entityBean = super.findByOrder(sorgenteIntero);
//        assertNull(entityBean);
//        System.out.println(VUOTA);
//
//        sorgenteIntero = 0;
//        entityBean = super.findByOrder(sorgenteIntero);
//        assertNull(entityBean);
    }


    @Test
    @Order(34)
    @DisplayName("34 - findByProperty")
    protected void findByProperty() {
        System.out.println("34 - findByProperty");
        System.out.println(VUOTA);

//        sorgente = "propertyInesistente";
//        sorgente2 = "termidoro";
//        entityBean = super.findByProperty(sorgente, sorgente2);
//        assertNull(entityBean);
//        System.out.println(VUOTA);
//
//        sorgente = "linkNazione";
//        sorgente2 = "bulgari";
//        entityBean = super.findByProperty(sorgente, sorgente2);
//        assertNull(entityBean);
//        System.out.println(VUOTA);
//
//        sorgente = "linkNazione";
//        sorgente2 = "Australia";
//        entityBean = super.findByProperty(sorgente, sorgente2);
//        assertNotNull(entityBean);
//        System.out.println(VUOTA);
    }

    @ParameterizedTest
    @MethodSource(value = "NAZIONALITA")
    @Order(71)
    @DisplayName("71 - findSingolari")
        //--nome nazionalità plurale (maiuscola o minuscola)
    void findSingolari(final String nomePlurale) {
        System.out.println("71 - findSingolari");
        System.out.println(VUOTA);

        sorgente = nomePlurale;
        listaStr = backend.findSingolari(sorgente);
        message = String.format("Nazionalità singolari comprese nella nazionalità plurale '%s'", sorgente);
        System.out.println(message);
        System.out.println(VUOTA);

        print(listaStr);
    }


    @Test
    @Order(81)
    @DisplayName("81 - getMappaPluraleNazione")
    protected void getMappaSingolarePlurale() {
        System.out.println("81 - getMappaPluraleNazione");
        System.out.println("Mappa di tutte le nazionalità con la coppia plurale -> nazione.");
        System.out.println(VUOTA);

        mappaOttenuta = backend.getMappaPluraleNazione();
        assertNotNull(mappaOttenuta);
        printMappa(mappaOttenuta);
    }

}
