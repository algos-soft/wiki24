package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 02-Apr-2023
 * Time: 19:42
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("attivita")
@Tag("backend")
@Tag("wikiBackend")
@DisplayName("AttPlurale Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AttPluraleBackendTest extends WikiBackendTest {

    @InjectMocks
    private AttPluraleBackend backend;

    private List<AttPlurale> listaBeans;

    //--nome attività
    //--typeLista
    protected static Stream<Arguments> ATTIVITA() {
        return Stream.of(
                Arguments.of(VUOTA, AETypeLista.listaBreve),
                Arguments.of(VUOTA, AETypeLista.nazionalitaSingolare),
                Arguments.of("soprano", AETypeLista.giornoNascita),
                Arguments.of("soprano", AETypeLista.attivitaSingolare),
                Arguments.of("1945", AETypeLista.attivitaSingolare),
                Arguments.of("attrice", AETypeLista.attivitaSingolare),
                Arguments.of("nobili", AETypeLista.attivitaPlurale),
                Arguments.of("militari", AETypeLista.attivitaPlurale),
                Arguments.of("ingegneri", AETypeLista.attivitaPlurale),
                Arguments.of("abate", AETypeLista.attivitaSingolare),
                Arguments.of("badessa", AETypeLista.attivitaSingolare),
                Arguments.of("abati e badesse", AETypeLista.attivitaPlurale),
                Arguments.of("bassisti", AETypeLista.attivitaPlurale),
                Arguments.of("allevatori", AETypeLista.attivitaPlurale),
                Arguments.of("diplomatici", AETypeLista.attivitaPlurale),
                Arguments.of("romanziere", AETypeLista.attivitaSingolare),
                Arguments.of("accademici", AETypeLista.attivitaPlurale),
                Arguments.of("dogi", AETypeLista.attivitaPlurale)
        );
    }

    //--nome attività plurale (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    //--crea una nuova entity
    public static Stream<Arguments> ATTIVITA_PLURALE() {
        return Stream.of(
                Arguments.of(VUOTA, false, false, false),
                Arguments.of("politici", true, true, false),
                Arguments.of("politico", false, false, true),
                Arguments.of("direttore di scena", false, false, true),
                Arguments.of("Congolesi (Rep. Dem. del Congo)", false, false, true),
                Arguments.of("produttorediscografico", false, false, true),
                Arguments.of("produttore discografico", false, false, true),
                Arguments.of("produttoridiscografici", true, false, false),
                Arguments.of("produttori discografici", false, true, false),
                Arguments.of("Politici", false, false, true),
                Arguments.of("attore", false, false, true),
                Arguments.of("attrice", false, false, true),
                Arguments.of("attori", true, true, false),
                Arguments.of("attrici", false, false, true),
                Arguments.of("vescovo ariano", false, false, true),
                Arguments.of("vescoviariani", true, false, false),
                Arguments.of("vescovi ariani", false, true, false),
                Arguments.of("errata", false, false, true),
                Arguments.of("britannici", false, false, true)
        );
    }


    //--nome della property
    //--value della property
    //--esiste entityBean
    public static Stream<Arguments> PROPERTY() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, false),
                Arguments.of("propertyInesistente", "valoreInesistente", false),
                Arguments.of("paginaLista", "termidoro", false),
                Arguments.of("paginaLista", "Ammiragli", true),
                Arguments.of("plurale", "avvocati", false),
                Arguments.of("linkAttivita", "Alpinismo", true),
                Arguments.of("linkAttivita", "Pippoz", false)
        );
    }

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

        super.streamCollection = ATTIVITA_PLURALE();
        super.streamProperty = PROPERTY();
    }


    @Test
    @Order(42)
    @DisplayName("42 - newEntity con ID ma non registrata")
    protected void newEntity() {
    }

        @Test
        @Order(75)
        @DisplayName("75 - findAllDistinctByPlurali")
        protected void findAllDistinctByPlurali() {
            System.out.println("75 - findAllDistinctByPlurali");
            message = String.format("Tutti i valori di %s plurali (unici)", nomeModulo);
            System.out.println(message);
            System.out.println(VUOTA);

            listaStr = wikiBackend.findAllDistinctByPlurali();
            assertTrue(listaStr != null);
            assertTrue(listaStr.size() > 0);
            message = String.format("La lista contiene %s elementi.", textService.format(listaStr.size()));
            System.out.println(message);
            print(listaStr);
        }

    @Test
    @Order(81)
    @DisplayName("81 - getMappaPluraleAttivita")
    protected void getMappaSingolarePlurale() {
        System.out.println("81 - getMappaPluraleAttivita");
        System.out.println("Mappa di tutte le attività con la coppia plurale -> attività.");
        System.out.println(VUOTA);

        mappaOttenuta = backend.getMappaPluraleAttivita();
        assertNotNull(mappaOttenuta);
        printMappa(mappaOttenuta);
    }


    @ParameterizedTest
    @MethodSource(value = "ATTIVITA")
    @Order(82)
    @DisplayName("82 - Lista di attività singolari per un'attività plurale (entityBean)")
        //--nome attivita
        //--typeLista
    void findAllFromAttivitaSingolariByPlurale(final String nomeAttivita, final AETypeLista type) {
        System.out.println("82 - Lista di attività singolari per un'attività plurale (entityBean)");
        sorgente = nomeAttivita;
        List<AttSingolare> listaAttivitaSingoleComprese = null;

        if (!validoPlurale(nomeAttivita, type)) {
            return;
        }

        listaAttivitaSingoleComprese = backend.findAllFromAttivitaSingolariByPlurale(sorgente);
        if (listaAttivitaSingoleComprese != null && listaAttivitaSingoleComprese.size() > 0) {
            message = String.format("Ci sono %d attività singolari che compongono l'attività plurale %s", listaAttivitaSingoleComprese.size(), sorgente);
            System.out.println(message);
            message = String.format("%s%s%s", sorgente,  FORWARD, listaAttivitaSingoleComprese);
            System.out.println(message);
        }
        else {
            message = String.format("Non esistono attività singolari associate all'attività plurale %s", sorgente);
            System.out.println(message);
            System.out.println("Sembra decisamente un errore");
        }
    }

    @ParameterizedTest
    @MethodSource(value = "ATTIVITA")
    @Order(83)
    @DisplayName("83 - Lista di nomi di attività singolari per un'attività plurale (String)")
        //--nome attivita
        //--typeLista
    void listaBio(final String nomeAttivita, final AETypeLista type) {
        System.out.println("83 - Lista di nomi di attività singolari per un'attività plurale (String)");
        sorgente = nomeAttivita;
        List<String> listaNomiAttivitaSingoleComprese = null;

        if (!validoPlurale(nomeAttivita, type)) {
            return;
        }

        listaNomiAttivitaSingoleComprese = backend.findAllFromNomiSingolariByPlurale(sorgente);
        if (listaNomiAttivitaSingoleComprese != null && listaNomiAttivitaSingoleComprese.size() > 0) {
            message = String.format("Ci sono %d nomi di attività singolari che compongono l'attività plurale %s", listaNomiAttivitaSingoleComprese.size(), sorgente);
            System.out.println(message);
            message = String.format("%s%s%s", sorgente,  FORWARD, listaNomiAttivitaSingoleComprese);
            System.out.println(message);
        }
        else {
            message = String.format("Non esistono attività singolari associate all'attività plurale %s", sorgente);
            System.out.println(message);
            System.out.println("Sembra decisamente un errore");
        }
    }

    private boolean validoPlurale(final String nomeAttivita, final AETypeLista type) {
        if (textService.isEmpty(nomeAttivita)) {
            System.out.println("Manca il nome dell'attività");
            return false;
        }

        if (type != AETypeLista.attivitaSingolare && type != AETypeLista.attivitaPlurale) {
            message = String.format("Il type 'AETypeLista.%s' indicato è incompatibile con la classe [%s]", type, AttPlurale.class.getSimpleName());
            System.out.println(message);
            return false;
        }

        if (type == AETypeLista.attivitaPlurale && !attPluraleBackend.isExistByKey(nomeAttivita)) {
            message = String.format("L'attività plurale [%s] indicata NON esiste", nomeAttivita);
            System.out.println(message);
            return false;
        }

        if (type == AETypeLista.attivitaSingolare) {
            if (attSingolareBackend.isExistByKey(nomeAttivita)) {
                message = String.format("L'attività indicata [%s] è un'attività singolare e NON plurale", nomeAttivita);
            }
            else {
                message = String.format("L'attività singolare indicata [%s] NON esiste", nomeAttivita);
            }
            System.out.println(message);
            return false;
        }

        return true;
    }

}
