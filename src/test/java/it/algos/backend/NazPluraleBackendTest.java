package it.algos.backend;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
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

    //--nome nazionalità
    //--typeLista
     static Stream<Arguments> NAZIONALITA2() {
        return Stream.of(
                Arguments.of(VUOTA, AETypeLista.listaBreve),
                Arguments.of(VUOTA, AETypeLista.nazionalitaSingolare),
                Arguments.of("attrice", AETypeLista.attivitaSingolare),
                Arguments.of("tedesco", AETypeLista.nazionalitaSingolare),
                Arguments.of("assiri", AETypeLista.nazionalitaSingolare),
                Arguments.of("azeri", AETypeLista.nazionalitaPlurale),
                Arguments.of("arabi", AETypeLista.nazionalitaPlurale),
                Arguments.of("libanese", AETypeLista.nazionalitaSingolare),
                Arguments.of("afghani", AETypeLista.nazionalitaPlurale),
                Arguments.of("mongoli", AETypeLista.nazionalitaSingolare),
                Arguments.of("assiri", AETypeLista.nazionalitaPlurale),
                Arguments.of("capoverdiani", AETypeLista.nazionalitaPlurale)
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


    @ParameterizedTest
    @MethodSource(value = "NAZIONALITA2")
    @Order(82)
    @DisplayName("82 - Lista di nazionalità singolari per una nazionalità plurale (entityBean)")
        //--nome nazionalità
        //--typeLista
    void findAllFromAttivitaSingolariByPlurale(final String nomeNazionalita, final AETypeLista type) {
        System.out.println("82 - Lista di nazionalità singolari per una nazionalità plurale (entityBean)");
        sorgente = nomeNazionalita;
        List<NazSingolare> listaNazionalitaSingoleComprese = null;

        if (!validoPlurale(nomeNazionalita, type)) {
            return;
        }

        listaNazionalitaSingoleComprese = backend.findAllFromNazionalitaSingolariByPlurale(sorgente);
        if (listaNazionalitaSingoleComprese != null && listaNazionalitaSingoleComprese.size() > 0) {
            message = String.format("Ci sono %d nazionalità singolari che compongono la nazionalità plurale %s", listaNazionalitaSingoleComprese.size(), sorgente);
            System.out.println(message);
            message = String.format("%s%s%s", sorgente,  FORWARD, listaNazionalitaSingoleComprese);
            System.out.println(message);
        }
        else {
            message = String.format("Non esistono nazionalità singolari associate alla nazionalità plurale %s", sorgente);
            System.out.println(message);
            System.out.println("Sembra decisamente un errore");
        }
    }

    @ParameterizedTest
    @MethodSource(value = "NAZIONALITA2")
    @Order(83)
    @DisplayName("83 - Lista di nomi di nazionalità singolari per una nazionalità plurale (String)")
        //--nome nazionalità
        //--typeLista
    void listaBio(final String nomeNazionalita, final AETypeLista type) {
        System.out.println("83 - Lista di nomi di nazionalità singolari per una nazionalità plurale (String)");
        sorgente = nomeNazionalita;
        List<String> listaNomiNazionalitaSingoleComprese = null;

        if (!validoPlurale(nomeNazionalita, type)) {
            return;
        }

        listaNomiNazionalitaSingoleComprese = backend.findAllFromNomiSingolariByPlurale(sorgente);
        if (listaNomiNazionalitaSingoleComprese != null && listaNomiNazionalitaSingoleComprese.size() > 0) {
            message = String.format("Ci sono %d nomi di nazionalità singolari che compongono la nazionalità plurale %s", listaNomiNazionalitaSingoleComprese.size(), sorgente);
            System.out.println(message);
            message = String.format("%s%s%s", sorgente,  FORWARD, listaNomiNazionalitaSingoleComprese);
            System.out.println(message);
        }
        else {
            message = String.format("Non esistono nazionalità singolari associate alla nazionalità plurale %s", sorgente);
            System.out.println(message);
            System.out.println("Sembra decisamente un errore");
        }
    }

    private boolean validoPlurale(final String nomeNazionalita, final AETypeLista type) {
        if (textService.isEmpty(nomeNazionalita)) {
            System.out.println("Manca il nome della nazionalità");
            return false;
        }

        if (type != AETypeLista.nazionalitaSingolare && type != AETypeLista.nazionalitaPlurale) {
            message = String.format("Il type 'AETypeLista.%s' indicato è incompatibile con la classe [%s]", type, NazPlurale.class.getSimpleName());
            System.out.println(message);
            return false;
        }

        if (type == AETypeLista.nazionalitaPlurale && !nazPluraleBackend.isExistByKey(nomeNazionalita)) {
            message = String.format("La nazionalità plurale [%s] indicata NON esiste", nomeNazionalita);
            System.out.println(message);
            return false;
        }

        if (type == AETypeLista.nazionalitaSingolare) {
            if (nazSingolareBackend.isExistByKey(nomeNazionalita)) {
                message = String.format("La nazionalità indicata [%s] è una nazionalità singolare e NON plurale", nomeNazionalita);
            }
            else {
                message = String.format("La nazionalità singolare indicata [%s] NON esiste", nomeNazionalita);
            }
            System.out.println(message);
            return false;
        }

        return true;
    }

}
