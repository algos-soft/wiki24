package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.base.WikiTest.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.upload.moduli.*;
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
 * Date: Sat, 25-Mar-2023
 * Time: 20:30
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("attivita")
@Tag("backend")
@Tag("wikiBackend")
@DisplayName("AttSingolare Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AttSingolareBackendTest extends WikiBackendTest {

    private AttSingolareBackend backend;

    private List<AttSingolare> listaBeans;


    //--nome
    //--esiste come ID
    //--esiste come key
    //--crea una nuova entity
    public static Stream<Arguments> ATTIVITA() {
        return Stream.of(
                Arguments.of(VUOTA, false, false, false),
                Arguments.of("politico", true, true, false),
                Arguments.of("politici", false, false, true),
                Arguments.of("errata", false, false, true),
                Arguments.of("fantasmi", false, false, true),
                Arguments.of("produttore discografico", false, true, false),
                Arguments.of("attrice", true, true, false),
                Arguments.of("attore", true, true, false),
                Arguments.of("attori", false, false, true),
                Arguments.of("nessuna", false, false, true),
                Arguments.of("direttore di scena", false, false, true),
                Arguments.of("accademici", false, false, true),
                Arguments.of("vescovo ariano", false, true, false)
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


    //--nome plurale
    //--esiste
    public static Stream<Arguments> ATTIVITA_PLURALI() {
        return Stream.of(
                Arguments.of(VUOTA, false),
                Arguments.of("politici", true),
                Arguments.of("politico", false),
                Arguments.of("fantasmi", false),
                Arguments.of("attori", true),
                Arguments.of("abati e badesse", true),
                Arguments.of("attrice", false),
                Arguments.of("attrici", false),
                Arguments.of("nessuna", false),
                Arguments.of("accademici", true)
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
    @Order(42)
    @DisplayName("42 - newEntity con ID ma non registrata")
    protected void newEntity() {
    }

    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_PLURALI")
    @Order(71)
    @DisplayName("71 - findAllByPlurale (entityBeans)")
        //--nome plurale
        //--esiste
    void findAllByPlurale(final String nomeAttivitaPlurale, final boolean esisteAttivitaPlurale) {
        System.out.println("71 - findAllByPlurale (entityBeans)");
        System.out.println("Tutte le attività singolari di una attività plurale");
        System.out.println(VUOTA);

        sorgente = nomeAttivitaPlurale;
        previstoBooleano = esisteAttivitaPlurale;

        listaBeans = backend.findAllByPlurale(sorgente);
        ottenutoBooleano = listaBeans != null && listaBeans.size() > 0;
        if (ottenutoBooleano) {
            message = String.format("Ci sono %s attività singolari che alimentano l'attività plurale [%s].", listaBeans.size(), sorgente);
        }
        else {
            message = String.format("Nessuna attività singolare corrisponde all'attività plurale [%s]. Sembrerebbe un errore", sorgente);
            System.out.println(message);
            return;
        }
        assertEquals(previstoBooleano, ottenutoBooleano);

        System.out.println(message);
        printSubLista(listaBeans);
    }

    @ParameterizedTest
    @MethodSource(value = "ATTIVITA_PLURALI")
    @Order(72)
    @DisplayName("72 - findAllForKeyByPlurale (String)")
        //--nome plurale
        //--esiste
    void findAllForKeyByPlurale(final String nomeAttivitaPlurale, final boolean esisteAttivitaPlurale) {
        System.out.println("72 - findAllForKeyByPlurale (String)");
        System.out.println("Tutte i nomi delle attività singolari di una attività plurale");
        System.out.println(VUOTA);

        sorgente = nomeAttivitaPlurale;
        previstoBooleano = esisteAttivitaPlurale;

        listaStr = backend.findAllForKeyByPlurale(sorgente);
        ottenutoBooleano = listaStr != null && listaStr.size() > 0;
        if (ottenutoBooleano) {
            message = String.format("Ci sono %s nomi di attività singolari che appartengono all'attività plurale [%s].", listaBeans.size(), sorgente);
        }
        else {
            message = String.format("Nessuna attività singolare corrisponde all'attività plurale [%s]. Sembrerebbe un errore", sorgente);
            System.out.println(message);
            return;
        }
        assertEquals(previstoBooleano, ottenutoBooleano);

        System.out.println(message);
        printSubLista(listaStr);
    }


    @Test
    @Order(76)
    @DisplayName("76 - findAllByExSortKey")
    protected void findAllByExSortKey() {
        System.out.println("76 - findAllByExSortKey");
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
    @Order(81)
    @DisplayName("81 - getMappaSingolarePlurale")
    protected void getMappaSingolarePlurale() {
        System.out.println("81 - getMappaSingolarePlurale");
        System.out.println("Mappa di tutte le attività con la coppia singolare -> plurale.");

        mappaOttenuta = backend.getMappaSingolarePlurale();
        assertNotNull(mappaOttenuta);
        printMappa(mappaOttenuta);
    }


    @ParameterizedTest
    @MethodSource(value = "ATTIVITA")
    @Order(82)
    @DisplayName("82 - findAllForKeyBySingolare (String)")
        //--nome singolare
        //--esiste come ID
        //--esiste come key
    void findAllForKeyBySingolare(final String nomeAttivitaSingolare, boolean nonUsato, boolean esisteComeKey) {
        System.out.println("82 - findAllForKeyBySingolare (String)");
        System.out.println("Tutte le attività singolari dello stesso 'gruppo' di un'attività singolare");
        System.out.println(VUOTA);

        sorgente = nomeAttivitaSingolare;
        previstoBooleano = esisteComeKey;

        if (!previstoBooleano) {
            return;
        }

        listaStr = backend.findAllForKeyBySingolare(sorgente);
        ottenutoBooleano = listaStr != null && listaStr.size() > 0;
        assertTrue(ottenutoBooleano);
        message = String.format("Ci sono %s nomi di attività singolari dello stesso 'gruppo' di [%s].", listaStr.size(), sorgente);
        System.out.println(message);
        printSubLista(listaStr);
    }

}
