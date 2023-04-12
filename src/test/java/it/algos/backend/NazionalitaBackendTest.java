package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.base.WikiTest.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.service.*;
import it.algos.wiki24.backend.packages.nazionalita.*;
import it.algos.wiki24.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.*;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 14-Mar-2023
 * Time: 07:24
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Tag("backend")
@DisplayName("Nazionalita Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NazionalitaBackendTest extends BackendTest {

    @Autowired
    private NazionalitaBackend backend;

    @InjectMocks
    protected WikiApiService wikiApiService;

    @InjectMocks
    protected QueryService queryService;

    private List<Nazionalita> listaBeans;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        assertNotNull(backend);
        super.entityClazz = Nazionalita.class;
        super.crudBackend = backend;
        super.setUpAll();
    }

//    protected void fixRiferimentiIncrociati() {
//        super.fixRiferimentiIncrociati();
//
//        backend.wikiApiService = wikiApiService;
//        backend.queryService = queryService;
//        wikiApiService.webService = webService;
//        wikiApiService.textService = textService;
//
//        queryService.appContext = appContext;
//    }

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

//        sorgente = "avvocato";
//        ottenutoBooleano = super.isExistById(sorgente);
//        assertFalse(ottenutoBooleano);
//        System.out.println(VUOTA);
//
//        sorgente = "portoricano";
//        ottenutoBooleano = super.isExistById(sorgente);
//        assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(22)
    @DisplayName("22 - isExistByKey")
    protected void isExistByKey() {
        System.out.println("22 - isExistByKey");
        System.out.println(VUOTA);
        System.out.println("Nazionalità ricavata dalla keyProperty");
        System.out.println(VUOTA);

        //--giorno
        //--esistente
        System.out.println(VUOTA);
        NAZIONALITA_SINGOLARE().forEach(this::isExistKeyBase);
    }

    //--giorno
    //--esistente
    void isExistKeyBase(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];

        ottenutoBooleano = backend.isExistByKey(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        if (ottenutoBooleano) {
            System.out.println(String.format("La nazionalità singola [%s] ESISTE", sorgente));
        }
        else {
            System.out.println(String.format("La nazionalità singola [%s] NON esiste", sorgente));
        }
        System.out.println(VUOTA);
    }

    @Test
    @Order(23)
    @DisplayName("23 - isExistByOrder")
    protected void isExistByOrder() {
        System.out.println("23 - isExistByOrder");
        System.out.println(VUOTA);

//        sorgenteIntero = 0;
//        ottenutoBooleano = super.isExistByOrder(sorgenteIntero);
//        assertFalse(ottenutoBooleano);
//        System.out.println(VUOTA);
//
//        sorgenteIntero = 6;
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
//        sorgenteIntero = 27;
//        ottenutoBooleano = super.isExistByProperty(sorgente, sorgenteIntero);
//        assertFalse(ottenutoBooleano);
//        System.out.println(VUOTA);
//
//        sorgente = "pluraleParagrafo";
//        sorgente2 = "Nati il 2 termidoro";
//        ottenutoBooleano = super.isExistByProperty(sorgente, sorgente2);
//        assertFalse(ottenutoBooleano);
//        System.out.println(VUOTA);
//
//        sorgente = "pluraleParagrafo";
//        sorgente2 = "algerini";
//        ottenutoBooleano = super.isExistByProperty(sorgente, sorgente2);
//        assertTrue(ottenutoBooleano);
    }

    @Test
    @Order(31)
    @DisplayName("31 - findById")
    protected void findById() {
        System.out.println("31 - findById");
        System.out.println(VUOTA);

//        sorgente = "avvocato";
//        entityBean = super.findById(sorgente);
//        assertNull(entityBean);
//        System.out.println(VUOTA);
//
//        sorgente = "portoricano";
//        entityBean = super.findById(sorgente);
//        assertNotNull(entityBean);
    }

    @Test
    @Order(32)
    @DisplayName("32 - findByKey")
    protected void findByKey() {
        System.out.println("32 - findByKey");
        System.out.println(VUOTA);

//        sorgente = "algerini";
//        entityBean = super.findByKey(sorgente);
//        assertNull(entityBean);
//        System.out.println(VUOTA);
//
//        sorgente = "algerino";
//        entityBean = super.findByKey(sorgente);
//        assertNotNull(entityBean);
    }

    @Test
    @Order(33)
    @DisplayName("33 - findByOrder")
    protected void findByOrder() {
        System.out.println("33 - findByOrder");
        System.out.println(VUOTA);

//        sorgenteIntero = 0;
//        entityBean = super.findByOrder(sorgenteIntero);
//        assertNull(entityBean);
//        System.out.println(VUOTA);
//
//        sorgenteIntero = 6;
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
//        sorgenteIntero = 27;
//        entityBean = super.findByProperty(sorgente, sorgenteIntero);
//        assertNull(entityBean);
//        System.out.println(VUOTA);
//
//        sorgente = "pluraleParagrafo";
//        sorgente2 = "Nati il 2 termidoro";
//        entityBean = super.findByProperty(sorgente, sorgente2);
//        assertNull(entityBean);
//        System.out.println(VUOTA);
//
//        sorgente = "pluraleParagrafo";
//        sorgente2 = "algerini";
//        entityBean = super.findByProperty(sorgente, sorgente2);
//        assertNotNull(entityBean);
//        System.out.println(VUOTA);
    }


    @Test
    @Order(41)
    @DisplayName("41 - creaIfNotExist")
    protected void creaIfNotExist() {
        System.out.println("41 - creaIfNotExist");
        System.out.println(VUOTA);

        sorgente = "polacco";
        ottenutoBooleano = super.creaIfNotExist(sorgente);
        assertFalse(ottenutoBooleano);
        System.out.println(VUOTA);

        sorgente = "tirolese";
        ottenutoBooleano = super.creaIfNotExist(sorgente);
        assertTrue(ottenutoBooleano);

        entityBean = backend.findByKey(sorgente);
        assertNotNull(entityBean);
        ottenutoBooleano = backend.delete(entityBean);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = crudBackend.isExistByKey(sorgente);
        assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(42)
    @DisplayName("42 - newEntity")
    protected void newEntity() {
        System.out.println("42 - newEntity");
        System.out.println(VUOTA);
        Nazionalita nazionalita;

        String singolare = "tirolese";
        String pluraleParagrafo = "tirolesi";
        String pluraleLista = "tirolesi";
        String linkPaginaNazione = "Tirolo";

        entityBean = backend.newEntity(singolare, pluraleParagrafo, pluraleLista, linkPaginaNazione);
        assertTrue(entityBean instanceof Nazionalita);
        assertNotNull(entityBean);
        nazionalita = (Nazionalita) entityBean;
        assertEquals(singolare, nazionalita.id);
        assertEquals(singolare, nazionalita.singolare);
        assertEquals(pluraleParagrafo, nazionalita.pluraleParagrafo);
        assertEquals(pluraleLista, nazionalita.pluraleLista);
        assertEquals(linkPaginaNazione, nazionalita.linkPaginaNazione);
        message = String.format("Creata correttamente (SOLO IN MEMORIA) la entity: [%s] con keyPropertyName%s'%s'", entityBean.id, FORWARD, entityBean);
        System.out.println(message);
        printBackend(List.of(entityBean));
    }

    @Test
    @Order(45)
    @DisplayName("45 - toString")
    protected void toStringTest() {
    }

    @Test
    @Order(54)
    @DisplayName("54 - findNazionalitaDistinctByPluraliSortPagina")
    protected void findNazionalitaDistinctByPluraliSortPagina() {
        System.out.println("54 - findNazionalitaDistinctByPluraliSortPagina");
        System.out.println(VUOTA);

        listaBeans = backend.findNazionalitaDistinctByPluraliSortPagina();
        assertNotNull(listaBeans);
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
        System.out.println(message);
        printBackend(listaBeans);
    }


    @Test
    @Order(55)
    @DisplayName("55 - findNazionalitaDistinctByPluraliSortPlurali")
    protected void findNazionalitaDistinctByPluraliSortPlurali() {
        System.out.println("55 - findNazionalitaDistinctByPluraliSortPlurali");
        System.out.println(VUOTA);

        listaBeans = backend.findNazionalitaDistinctByPluraliSortPlurali();
        assertNotNull(listaBeans);
        assertNotNull(listaBeans);
        ottenutoIntero = listaBeans.size();
        message = String.format("La collection '%s' della classe [%s] ha in totale %s entities nel database mongoDB", collectionName, clazzName, textService.format(ottenutoIntero));
        System.out.println(message);
        printBackend(listaBeans);
    }

    protected void printTestaEntityBean() {
        System.out.print("singolare");
        System.out.print(SEP);
        System.out.print("pluraleParagrafo");
        System.out.print(SEP);
        System.out.print("pluraleLista");
        System.out.print(SEP);
        System.out.print("linkPaginaNazione");
        System.out.print(SEP);
        System.out.print("numBio");
        System.out.print(SEP);
        System.out.print("numSingolari");
        System.out.print(SEP);
        System.out.print("superaSoglia");
        System.out.print(SEP);
        System.out.print("esistePaginaLista");
        System.out.println(SPAZIO);
    }

    protected void printEntityBeans(Object obj) {
        if (obj instanceof Nazionalita nazionalita) {
            System.out.print(nazionalita.singolare);
            System.out.print(SEP);
            System.out.print(nazionalita.pluraleParagrafo);
            System.out.print(SEP);
            System.out.print(nazionalita.pluraleLista);
            System.out.print(SEP);
            System.out.print(nazionalita.linkPaginaNazione);
            System.out.print(SEP);
            System.out.print(nazionalita.numBio);
            System.out.print(SEP);
            System.out.print(nazionalita.numSingolari);
            System.out.print(SEP);
            System.out.print(nazionalita.superaSoglia);
            System.out.print(SEP);
            System.out.print(nazionalita.esistePaginaLista);
            System.out.println(SPAZIO);
        }
    }

}
