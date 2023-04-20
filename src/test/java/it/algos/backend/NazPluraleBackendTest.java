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

import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 24-Mar-2023
 * Time: 06:48
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("nazionalita")
@Tag("backend")
@Tag("wikiBackend")
@DisplayName("NazPlurale Backend")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NazPluraleBackendTest extends WikiBackendTest {

    @InjectMocks
    private NazPluraleBackend backend;

    //--nome nazionalità singolare (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    //--crea una nuova entity
    public static Stream<Arguments> NAZIONALITA() {
        return Stream.of(
                Arguments.of(VUOTA, false, false, false),
                Arguments.of("turchi", true, true, false),
                Arguments.of("tedeschi", true, true, false),
                Arguments.of("direttore di scena", false, false, true),
                Arguments.of("Congolesi (Rep. Dem. del Congo)", false, false, true),
                Arguments.of("brasiliano", false, false, true),
                Arguments.of("burgunda", false, false, true),
                Arguments.of("italiano", false, false, true),
                Arguments.of("Italiano", false, false, true),
                Arguments.of("italiana", false, false, true),
                Arguments.of("italiani", true, true, false),
                Arguments.of("Burgunda", false, false, true),
                Arguments.of("vescovo ariano", false, false, true),
                Arguments.of("errata", false, false, true),
                Arguments.of("britannici", true, true, false),
                Arguments.of("tedesco", false, false, true),
                Arguments.of("tedeschi", true, true, false)
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
                Arguments.of("paginaLista", "Arabi", true),
                Arguments.of("plurale", "avvocati", false),
                Arguments.of("linkNazione", "Bulgaria", true),
                Arguments.of("linkNazione", "Pippoz", false)
        );
    }

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

        super.streamCollection = NAZIONALITA();
        super.streamProperty = PROPERTY();
    }

    @Test
    @Order(42)
    @DisplayName("42 - newEntity con ID ma non registrata")
    protected void newEntity() {
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
