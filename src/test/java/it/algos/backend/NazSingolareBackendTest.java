package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 22-Mar-2023
 * Time: 18:49
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("nazionalita")
@Tag("backend")
@Tag("wikiBackend")
@DisplayName("NazSingolare Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NazSingolareBackendTest extends WikiBackendTest {

    private NazSingolareBackend backend;

    private List<NazSingolare> listaBeans;


    //--nome nazionalità singolare (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    //--crea una nuova entity
    public static Stream<Arguments> NAZIONALITA() {
        return Stream.of(
                Arguments.of(VUOTA, false, false, false),
                Arguments.of("turco", true, true, false),
                Arguments.of("tedesca", true, true, false),
                Arguments.of("direttore di scena", false, false, true),
                Arguments.of("Congolesi (Rep. Dem. del Congo)", false, false, true),
                Arguments.of("brasiliano", true, true, false),
                Arguments.of("burgunda", true, true, false),
                Arguments.of("Burgunda", false, false, true),
                Arguments.of("italiano", true, true, false),
                Arguments.of("Italiano", false, false, true),
                Arguments.of("Berberi", false, false, true),
                Arguments.of("galli", false, false, true),
                Arguments.of("etiopi", false, false, true),
                Arguments.of("danesi", false, false, true),
                Arguments.of("maltesi", false, false, true),
                Arguments.of("italiana", true, true, false),
                Arguments.of("italiani", false, false, true),
                Arguments.of("vescovo ariano", false, false, true),
                Arguments.of("errata", false, false, true),
                Arguments.of("britannici", false, false, true),
                Arguments.of("tedesco", true, true, false),
                Arguments.of("tedeschi", false, false, true)
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
                Arguments.of("plurale", "arabi", true),
                Arguments.of(FIELD_NAME_PLURALE, "termidoro", false),
                Arguments.of(FIELD_NAME_PLURALE, "arabi", true)
        );
    }

    //--nome nazionalità plurale (maiuscola o minuscola)
    //--esiste
    public static Stream<Arguments> NAZIONALITA_PLURALI() {
        return Stream.of(
                Arguments.of(VUOTA, false),
                Arguments.of("turchi", true),
                Arguments.of("tedeschi", true),
                Arguments.of("direttore di scena", false),
                Arguments.of("Congolesi (Rep. Dem. del Congo)", false),
                Arguments.of("brasiliano", false),
                Arguments.of("burgunda", false),
                Arguments.of("italiano", false),
                Arguments.of("Italiano", false),
                Arguments.of("italiana", false),
                Arguments.of("italiani", true),
                Arguments.of("Burgunda", false),
                Arguments.of("vescovo ariano", false),
                Arguments.of("errata", false),
                Arguments.of("britannici", true),
                Arguments.of("tedesco", false),
                Arguments.of("tedeschi", true)
        );
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        this.backend = super.nazSingolareBackend;
        super.entityClazz = NazSingolare.class;
        super.typeBackend = TypeBackend.nessuno;
        super.crudBackend = backend;
        super.wikiBackend = backend;
        super.nomeModulo = "attività";

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        super.streamCollection = NAZIONALITA();
        super.streamProperty = PROPERTY();
    }


    @Test
    @Order(42)
    @DisplayName("42 - newEntity con ID ma non registrata")
    protected void newEntity() {
    }

    @ParameterizedTest
    @MethodSource(value = "NAZIONALITA_PLURALI")
    @Order(71)
    @DisplayName("71 - findAllByPlurale (entityBeans)")
        //--nome plurale
        //--esiste
    void findAllByPlurale(final String nomeNazionalitaPlurale, final boolean esisteNazionalitaPlurale) {
        System.out.println("71 - findAllByPlurale (entityBeans)");
        System.out.println("Tutte le nazionalità singolari di una nazionalità plurale");
        System.out.println(VUOTA);

        sorgente = nomeNazionalitaPlurale;
        previstoBooleano = esisteNazionalitaPlurale;

        listaBeans = backend.findAllByPlurale(sorgente);
        ottenutoBooleano = listaBeans != null && listaBeans.size() > 0;
        if (ottenutoBooleano) {
            message = String.format("Ci sono %s nazionalità singolari che alimentano la nazionalità plurale [%s].", listaBeans.size(), sorgente);
        }
        else {
            message = String.format("Nessuna nazionalità singolare corrisponde alla nazionalità plurale [%s]. Sembrerebbe un errore", sorgente);
            System.out.println(message);
            return;
        }
        assertEquals(previstoBooleano, ottenutoBooleano);

        System.out.println(message);
        printSubLista(listaBeans);
    }

    @ParameterizedTest
    @MethodSource(value = "NAZIONALITA_PLURALI")
    @Order(72)
    @DisplayName("72 - findAllForKeyByPlurale (String)")
        //--nome plurale
        //--esiste
    void findAllForKeyByPlurale(final String nomeNazionalitaPlurale, final boolean esisteNazionalitaPlurale) {
        System.out.println("72 - findAllForKeyByPlurale (String)");
        System.out.println("Tutte i nomi delle nazionalità singolari di una nazionalità plurale");
        System.out.println(VUOTA);

        sorgente = nomeNazionalitaPlurale;
        previstoBooleano = esisteNazionalitaPlurale;

        listaStr = backend.findAllForKeyByPlurale(sorgente);
        ottenutoBooleano = listaStr != null && listaStr.size() > 0;
        if (ottenutoBooleano) {
            message = String.format("Ci sono %s nomi di nazionalità singolari che appartengono alla nazionalità plurale [%s].", listaBeans.size(), sorgente);
        }
        else {
            message = String.format("Nessuna nazionalità singolare corrisponde alla nazionalità plurale [%s]. Sembrerebbe un errore", sorgente);
            System.out.println(message);
            return;
        }
        assertEquals(previstoBooleano, ottenutoBooleano);

        System.out.println(message);
        printSubLista(listaStr);
    }


    @Test
    @Order(78)
    @DisplayName("78 - findAllDistinctByPlurali")
    protected void findAllDistinctByPlurali() {
        System.out.println("78 - findAllDistinctByPlurali");
        System.out.println("Tutte i valori di nazionalità plurali (unici)");
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
        System.out.println("Mappa di tutte le nazionalità con la coppia singolare -> plurale.");
        System.out.println(VUOTA);

        mappaOttenuta = backend.getMappaSingolarePlurale();
        assertNotNull(mappaOttenuta);
        printMappa(mappaOttenuta);
    }


    @ParameterizedTest
    @MethodSource(value = "NAZIONALITA")
    @Order(82)
    @DisplayName("82 - findAllForKeyBySingolare (String)")
        //--nome singolare
        //--esiste come ID
        //--esiste come key
    void findAllForKeyBySingolare(final String nomeNazionalitaSingolare, boolean nonUsato, boolean esisteComeKey) {
        System.out.println("82 - findAllForKeyBySingolare (String)");
        System.out.println("Tutte le nazionalità singolari dello stesso 'gruppo' di una nazionalità singolare");
        System.out.println(VUOTA);

        sorgente = nomeNazionalitaSingolare;
        previstoBooleano = esisteComeKey;

        if (!previstoBooleano) {
            return;
        }

        listaStr = backend.findAllForKeyBySingolare(sorgente);
        ottenutoBooleano = listaStr != null && listaStr.size() > 0;
        assertTrue(ottenutoBooleano);
        message = String.format("Ci sono %s nomi di nazionalità singolari dello stesso 'gruppo' di [%s].", listaStr.size(), sorgente);
        System.out.println(message);
        printSubLista(listaStr);
    }

}

