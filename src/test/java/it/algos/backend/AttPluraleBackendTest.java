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

}
