package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.upload.moduli.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 25-Mar-2023
 * Time: 20:30
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("backend")
@Tag("wikiBackend")
@DisplayName("AttSingolare Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AttSingolareBackendTest extends WikiBackendTest {

    private AttSingolareBackend backend;

    private List<AttSingolare> listaBeans;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.attSingolareBackend;
        super.entityClazz = AttSingolare.class;
        super.typeBackend = TypeBackend.nessuno;
        super.crudBackend = backend;
        super.wikiBackend = backend;

        super.setUpAll();
    }


    @Test
    @Order(21)
    @DisplayName("21 - isExistById")
    protected void isExistById() {
        System.out.println("21 - isExistById");
        System.out.println(VUOTA);

        System.out.println(VUOTA);
        ATTIVITA_SINGOLARE().forEach(this::isExistByIdBase);
    }

    //--nome attività singolare (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    void isExistByIdBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];

        ottenutoBooleano = super.isExistById(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }


    @Test
    @Order(22)
    @DisplayName("22 - isExistByKey")
    protected void isExistByKey() {
        System.out.println("22 - isExistByKey");
        System.out.println(VUOTA);

        System.out.println(VUOTA);
        ATTIVITA_SINGOLARE().forEach(this::isExistByKeyBase);
    }

    //--nome attività singolare (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    void isExistByKeyBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[2];

        ottenutoBooleano = super.isExistByKey(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }


    @Test
    @Order(24)
    @DisplayName("24 - isExistByProperty")
    protected void isExistByProperty() {
        System.out.println("24 - isExistByProperty");
        System.out.println(VUOTA);

        sorgente = "propertyInesistente";
        sorgente2 = "termidoro";
        ottenutoBooleano = super.isExistByProperty(sorgente, sorgente2);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "plurale";
        sorgente2 = "termidoro";
        ottenutoBooleano = super.isExistByProperty(sorgente, sorgente2);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "plurale";
        sorgente2 = "avvocati";
        ottenutoBooleano = super.isExistByProperty(sorgente, sorgente2);
        assertTrue(ottenutoBooleano);
    }

    @Test
    @Order(31)
    @DisplayName("31 - findById")
    protected void findById() {
        System.out.println("31 - isExistByKey");
        System.out.println(VUOTA);

        System.out.println(VUOTA);
        ATTIVITA_SINGOLARE().forEach(this::findByIdBase);
    }


    //--nome attività singolare (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    void findByIdBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];

        entityBean = super.findById(sorgente);
        assertEquals(previstoBooleano, entityBean != null);
    }

    @Test
    @Order(32)
    @DisplayName("32 - findByKey")
    protected void findByKey() {
        System.out.println("32 - findByKey");
        System.out.println(VUOTA);

        System.out.println(VUOTA);
        ATTIVITA_SINGOLARE().forEach(this::findByKeyBase);
    }

    //--nome attività singolare (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    void findByKeyBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[2];

        entityBean = super.findByKey(sorgente);
        assertEquals(previstoBooleano, entityBean != null);
    }


    @Test
    @Order(34)
    @DisplayName("34 - findByProperty")
    protected void findByProperty() {
        System.out.println("34 - findByProperty");
        System.out.println(VUOTA);

        sorgente = "propertyInesistente";
        sorgente2 = "termidoro";
        entityBean = super.findByProperty(sorgente, sorgente2);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "plurale";
        sorgente2 = "termidoro";
        entityBean = super.findByProperty(sorgente, sorgente2);
        assertNull(entityBean);
        System.out.println(VUOTA);

        sorgente = "plurale";
        sorgente2 = "avvocati";
        entityBean = super.findByProperty(sorgente, sorgente2);
        assertNotNull(entityBean);
        System.out.println(VUOTA);
    }

    @Test
    @Order(65)
    @DisplayName("65 - findAllByPlurale (entityBeans)")
    protected void findAllByPlurale() {
        System.out.println("65 - findAllByPlurale");
        System.out.println("Tutte le attività singolari di una attività plurale");

        System.out.println(VUOTA);
        ATTIVITA_SINGOLARE().forEach(this::findAllByPluraleBase);
    }


    //--nome attività singolare (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    void findAllByPluraleBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[2];

        if (previstoBooleano) {
            entityBean = backend.findByKey(sorgente);
            assertTrue(entityBean != null);
            sorgente2 = ((AttSingolare) entityBean).plurale;
            listaBeans = backend.findAllByPlurale(sorgente2);
            assertTrue(listaBeans != null);
            System.out.print(sorgente2);
            System.out.print(FORWARD);
            System.out.println(listaBeans);
        }
        else {
            return;
        }
    }


    @Test
    @Order(66)
    @DisplayName("66 - findAllForKeyByPlurale (String)")
    protected void findAllForKeyByPlurale() {
        System.out.println("66 - findAllForKeyByPlurale");
        System.out.println("Tutte le attività singolari di una attività plurale");

        System.out.println(VUOTA);
        ATTIVITA_SINGOLARE().forEach(this::findAllForKeyByPluraleBase);
    }


    //--nome attività singolare (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    void findAllForKeyByPluraleBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[2];

        if (previstoBooleano) {
            entityBean = backend.findByKey(sorgente);
            assertTrue(entityBean != null);
            sorgente2 = ((AttSingolare) entityBean).plurale;
            listaStr = backend.findAllForKeyByPlurale(sorgente2);
            assertTrue(listaStr != null);
            System.out.print(sorgente2);
            System.out.print(FORWARD);
            System.out.println(listaStr);
        }
        else {
            return;
        }
    }

    @Test
    @Order(76)
    @DisplayName("76 - findAllByExSortKey")
    protected void findAllByExSortKey() {
        System.out.println("71 - findAllByExSortKey");
        System.out.println("Tutte le attività singolari col flag ex=true");

        listaBeans = backend.findAllByExSortKey();
        assertTrue(listaBeans != null);
        assertTrue(listaBeans.size() > 0);
        printSubLista(listaBeans);
    }

    @Test
    @Order(77)
    @DisplayName("77 - findAllByNotExSortKey")
    protected void findAllByNotExSortKey() {
        System.out.println("77 - findAllByNotExSortKey");
        System.out.println("Tutte le attività singolari col flag ex=false");

        listaBeans = backend.findAllByNotExSortKey();
        assertTrue(listaBeans != null);
        assertTrue(listaBeans.size() > 0);
        printSubLista(listaBeans);
    }

    @Test
    @Order(78)
    @DisplayName("78 - findAllDistinctByPlurali")
    protected void findAllDistinctByPlurali() {
        System.out.println("78 - findAllDistinctByPlurali");
        System.out.println("Tutte i valori di attività plurali (unici)");
        System.out.println(VUOTA);

        listaStr = backend.findAllDistinctByPlurali();
        assertTrue(listaStr != null);
        assertTrue(listaStr.size() > 0);
        message = String.format("La lista contiene %s elementi.", textService.format(listaStr.size()));
        System.out.println(message);
        print(listaStr);
    }

    @Test
    @Order(81)
    @DisplayName("81 - getMappaSingolarePlurale")
    protected void getMappaSingolarePlurale() {
        System.out.println("81 - getMappaSingolarePlurale");
        System.out.println("Mappa di tutte le attività con la coppia singolare -> plurale.");
        System.out.println(VUOTA);

        mappaOttenuta = backend.getMappaSingolarePlurale();
        assertNotNull(mappaOttenuta);
        printMappa(mappaOttenuta);
    }

}
