package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.upload.moduli.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.*;
import java.util.stream.*;

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


    //--nome nella collection
    //--esiste come ID
    //--esiste come key
    public static Stream<Arguments> ATTIVITA() {
        return Stream.of(
                Arguments.of(VUOTA, false, false),
                Arguments.of("politico", true, true),
                Arguments.of("politici", false, false),
                Arguments.of("errata", false, false),
                Arguments.of("fantasmi", false, false),
                Arguments.of("produttorediscografico", true, false),
                Arguments.of("produttore discografico", false, true),
                Arguments.of("attrice", true, true),
                Arguments.of("attore", true, true),
                Arguments.of("attori", false, false),
                Arguments.of("nessuna", false, false),
                Arguments.of("direttore di scena", false, false),
                Arguments.of("accademici", false, false),
                Arguments.of("vescovo ariano", false, true)
        );
    }

    //--nome della property
    //--value della property
    //--esiste entityBean
    public static Stream<Arguments> PROPERTY() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of("propertyInesistente", "valoreInesistente", false),
                Arguments.of("plurale", "termidoro", false),
                Arguments.of("plurale", "avvocati", true),
                Arguments.of(FIELD_NAME_PLURALE, "termidoro", false),
                Arguments.of(FIELD_NAME_PLURALE, "avvocati", true)
        );
    }

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
        super.nomeModulo = "attività";

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        super.streamCollection = ATTIVITA();
        super.streamProperty = PROPERTY();
    }


    @Test
    @Order(71)
    @DisplayName("71 - findAllByPlurale (entityBeans)")
    protected void findAllByPlurale() {
        System.out.println("71 - findAllByPlurale (entityBeans)");
        System.out.println("Tutte le attività singolari di una attività plurale");

//        System.out.println(VUOTA);
//        ATTIVITA_SINGOLARE().forEach(parameters -> super.findAllByPlurale(parameters));
    }


    @Test
    @Order(72)
    @DisplayName("72 - findAllForKeyByPlurale (String)")
    protected void findAllForKeyByPlurale() {
        System.out.println("72 - findAllForKeyByPlurale (String)");
        System.out.println("Tutte le attività singolari di una attività plurale");

//        System.out.println(VUOTA);
//        ATTIVITA_SINGOLARE().forEach(parameters -> super.findAllForKeyByPlurale(parameters));
    }


    @Test
    @Order(75)
    @DisplayName("75 - findAllByExSortKey")
    protected void findAllByExSortKey() {
        System.out.println("75 - findAllByExSortKey");
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

    //    @Test
    //    @Order(78)
    //    @DisplayName("78 - findAllDistinctByPlurali")
    //    protected void findAllDistinctByPlurali() {
    //        System.out.println("78 - findAllDistinctByPlurali");
    //        System.out.println("Tutte i valori di attività plurali (unici)");
    //        System.out.println(VUOTA);
    //
    //        listaStr = backend.findAllDistinctByPlurali();
    //        assertTrue(listaStr != null);
    //        assertTrue(listaStr.size() > 0);
    //        message = String.format("La lista contiene %s elementi.", textService.format(listaStr.size()));
    //        System.out.println(message);
    //        print(listaStr);
    //    }

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