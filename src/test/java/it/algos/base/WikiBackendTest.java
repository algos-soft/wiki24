package it.algos.base;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import it.algos.wiki24.backend.packages.nome.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 26-Feb-2023
 * Time: 19:12
 */
public abstract class WikiBackendTest extends BackendTest {

    protected WikiBackend wikiBackend;

    @Autowired
    protected BioBackend bioBackend;

    @Autowired
    protected GiornoWikiBackend giornoWikiBackend;

    @Autowired
    protected AnnoWikiBackend annoWikiBackend;

    @Autowired
    protected WikiApiService wikiApiService;

    @Autowired
    protected WikiUtility wikiUtility;

    @Autowired
    protected QueryService queryService;

    @Autowired
    protected AttSingolareBackend attSingolareBackend;

    @Autowired
    protected AttPluraleBackend attPluraleBackend;

    @Autowired
    protected NazSingolareBackend nazSingolareBackend;

    @Autowired
    protected NazPluraleBackend nazPluraleBackend;

    @Autowired
    protected NomeBackend nomeBackend;

    protected WResult wResult;

    protected String nomeModulo;


    //--nome attività plurale (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    public static Stream<Arguments> ATTIVITA_PLURALE() {
        return Stream.of(
                Arguments.of(VUOTA, false, false),
                Arguments.of("politici", true, true),
                Arguments.of("politico", false, false),
                Arguments.of("direttore di scena", false, false),
                Arguments.of("Congolesi (Rep. Dem. del Congo)", false, false),
                Arguments.of("produttorediscografico", false, false),
                Arguments.of("produttore discografico", false, false),
                Arguments.of("produttoridiscografici", true, false),
                Arguments.of("produttori discografici", false, true),
                Arguments.of("Politici", false, false),
                Arguments.of("attore", false, false),
                Arguments.of("attrice", false, false),
                Arguments.of("attori", true, true),
                Arguments.of("attrici", false, false),
                Arguments.of("vescovo ariano", false, false),
                Arguments.of("vescoviariani", true, false),
                Arguments.of("vescovi ariani", false, true),
                Arguments.of("errata", false, false),
                Arguments.of("britannici", false, false)
        );
    }

    //--nome nazionalità singolare (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    public static Stream<Arguments> NAZIONALITA_SINGOLARE() {
        return Stream.of(
                Arguments.of(VUOTA, false, false),
                Arguments.of("turco", true, true),
                Arguments.of("tedesca", true, true),
                Arguments.of("direttore di scena", false, false),
                Arguments.of("Congolesi (Rep. Dem. del Congo)", false, false),
                Arguments.of("brasiliano", true, true),
                Arguments.of("burgunda", true, true),
                Arguments.of("Burgunda", false, false),
                Arguments.of("italiano", true, true),
                Arguments.of("Italiano", false, false),
                Arguments.of("italiana", true, true),
                Arguments.of("italiani", false, false),
                Arguments.of("vescovo ariano", false, false),
                Arguments.of("errata", false, false),
                Arguments.of("britannici", false, false),
                Arguments.of("tedesco", true, true),
                Arguments.of("tedeschi", false, false)
        );
    }


    //--nome nazionalità plurale (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    public static Stream<Arguments> NAZIONALITA_PLURALE() {
        return Stream.of(
                Arguments.of(VUOTA, false, false),
                Arguments.of("turchi", true, true),
                Arguments.of("tedeschi", true, true),
                Arguments.of("direttore di scena", false, false),
                Arguments.of("Congolesi (Rep. Dem. del Congo)", false, false),
                Arguments.of("brasiliano", false, false),
                Arguments.of("burgunda", false, false),
                Arguments.of("italiano", false, false),
                Arguments.of("Italiano", false, false),
                Arguments.of("italiana", false, false),
                Arguments.of("italiani", true, true),
                Arguments.of("Burgunda", false, false),
                Arguments.of("vescovo ariano", false, false),
                Arguments.of("errata", false, false),
                Arguments.of("britannici", true, true),
                Arguments.of("tedesco", false, false),
                Arguments.of("tedeschi", true, true)
        );
    }

    //--nome nazionalità plurale (maiuscola o minuscola)
    public static Stream<Arguments> NAZIONALITA() {
        return Stream.of(
                Arguments.of(VUOTA),
                Arguments.of("britannici"),
                Arguments.of("italiani"),
                Arguments.of("tedeschi"),
                Arguments.of("burkinabé"),
                Arguments.of("germanici"),
                Arguments.of("barbadiani"),
                Arguments.of("Congolesi (Rep. Dem. del Congo)"),
                Arguments.of("berberi"),
                Arguments.of("Berberi"),
                Arguments.of("galli"),
                Arguments.of("etiopi"),
                Arguments.of("danesi"),
                Arguments.of("maltesi"),
                Arguments.of("angolani")
        );
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        assertNotNull(bioBackend);
        assertNotNull(giornoBackend);
        assertNotNull(giornoWikiBackend);
        assertNotNull(annoBackend);
        assertNotNull(annoWikiBackend);
        assertNotNull(wikiApiService);
        assertNotNull(wikiUtility);
        assertNotNull(queryService);
        assertNotNull(attSingolareBackend);
        assertNotNull(attPluraleBackend);
        assertNotNull(nazSingolareBackend);
        assertNotNull(nazPluraleBackend);
        assertNotNull(nomeBackend);

        super.crudBackend = wikiBackend;
        super.setUpAll();
    }

    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUpEach() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @Override
    protected void setUpEach() {
        super.setUpEach();

        this.wResult = WResult.errato();
    }


    @Test
    @Order(10)
    @DisplayName("10--------")
    void test10() {
        System.out.println("11 - isExistsCollection");
        System.out.println("12 - count");
        System.out.println("13 - resetOnlyEmpty");
        System.out.println("14 - resetForcing");
        System.out.println("15 - elabora (solo su wiki)");
        System.out.println("16 - upload (solo su wiki)");

        System.out.println(VUOTA);
    }


    @Test
    @Order(15)
    @DisplayName("15 - elabora (solo su wiki)")
    protected void elabora() {
        System.out.println("15 - elabora (solo su wiki)");
        System.out.println(VUOTA);

        if (entityClazz == null) {
            System.out.println("Manca la variabile entityClazz in questo test");
        }

        wResult = wikiBackend.elabora();
        printRisultato(wResult);
    }

    @Test
    @Order(16)
    @DisplayName("16 - upload (non previsto per questa collection)")
    protected void upload() {
        System.out.println("16 - upload (non previsto per questa collection)");
    }

    @Test
    @Order(70)
    @DisplayName("70--------")
    void test70() {
        System.out.println("71 - findAllByPlurale (entityBeans)");
        System.out.println("72 - findAllForKeyByPlurale (String)");
    }

    //Segnaposto
    @Order(72)
    protected void libero72() {
    }

    //Segnaposto
    @Order(73)
    protected void libero73() {
    }

    //Segnaposto
    @Order(74)
    protected void libero74() {
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
    @Order(80)
    @DisplayName("80--------")
    void test80() {
    }

    @Test
    @Order(90)
    @DisplayName("90--------")
    void test90() {
        System.out.println("91 - riordinaModulo (upload test in ordine alfabetico)");
    }

    @Test
    @Order(91)
    @DisplayName("91 - riordinaModulo (upload test in ordine alfabetico)")
    protected void riordinaModulo() {
        System.out.println("91 - riordinaModulo (upload test in ordine alfabetico)");
        System.out.println(VUOTA);

        wResult = wikiBackend.riordinaModulo();
        printRisultato(wResult);
        assertTrue(wResult.isValido());
    }

    protected void printRisultato(WResult result) {
        List lista = result.getLista();
        lista = lista != null && lista.size() > 20 ? lista.subList(0, 10) : lista;
        Map<String, Object> mappa = result.getMappa();

        System.out.println(VUOTA);
        System.out.println("Risultato");
        System.out.println(String.format("Valido: %s", result.isValido()));
        System.out.println(String.format("Eseguito: %s", result.isEseguito()));
        System.out.println(String.format("Modificata: %s", result.isModificata()));
        System.out.println(String.format("Result: %s", result.getTypeResult() != null ? result.getTypeResult().name() : VUOTA));
        System.out.println(String.format("Method: %s", result.getMethod()));
        System.out.println(String.format("TypeLog: %s", result.getTypeLog()));
        System.out.println(String.format("TypeCopy: %s", result.getTypeCopy()));
        System.out.println(String.format("CopyType: %s", result.getTypeCopy() != null ? result.getTypeCopy().getType() : VUOTA));
        //        System.out.println(String.format("Title: %s", result.getWikiTitle()));
        System.out.println(String.format("Target: %s", result.getTarget()));
        System.out.println(String.format("WikiTitle: %s", result.getWikiTitle()));
        System.out.println(String.format("TypeResultText: %s", result.getTypeResult() != null ? result.getTypeResult().getTag() : VUOTA));
        System.out.println(String.format("TypeText: %s", result.getTypeTxt()));
        System.out.println(String.format("Message code: %s", result.getCodeMessage()));
        System.out.println(String.format("Message: %s", result.getMessage()));
        System.out.println(String.format("Exception: %s", result.getException() != null ? result.getException().getMessage() : VUOTA));
        System.out.println(String.format("Error code: %s", result.getErrorCode()));
        System.out.println(String.format("Error message: %s", result.getErrorMessage()));
        System.out.println(String.format("Valid message: %s", result.getValidMessage()));
        System.out.println(String.format("Numeric value: %s", textService.format(result.getIntValue())));
        System.out.println(String.format("List value: %s", lista));
        System.out.println("Map value: ");
        if (mappa != null) {
            for (String key : mappa.keySet()) {
                if (mappa.get(key) instanceof List mapList) {
                    System.out.println(String.format("%s%s (%d): %s", TAB, key, mapList.size(), mapList));
                }
            }
            System.out.println(String.format("Tempo: %s", result.deltaSec()));
        }
    }

}
