package it.algos.base24.service;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.packages.geografia.stato.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.basetest.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: dom, 22-ott-2023
 * Time: 20:02
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Web Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WebServiceTest extends ServiceTest {

    public static final String ISO_PREFIX = "ISO 3166-2:";

    public static final String URL_ERRATO = "htp://www.altos.it/hellogac.html";

    public static final String URL_WEB_GAC = "http://www.algos.it/hellogac.html";

    private static String URL_WIKI_GENERICO = "https://it.wikipedia.org/wiki/Utente:Gac/T11";

    @Autowired
    private WebService service;

    @Autowired
    private TextService textService;

    @Autowired
    private StatoModulo statoModulo;

    //--domain
    //--esiste
    //--testo
    private Stream<Arguments> domain() {
        return Stream.of(
                Arguments.of(VUOTA, false, VUOTA),
                Arguments.of(URL_ERRATO, false, VUOTA),
                Arguments.of(URL_WEB_GAC, true, "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" ")
        );
    }

    //--title
    //--esiste table
    private Stream<Arguments> wikiPage() {
        return Stream.of(
                Arguments.of("Piozzano", false),
                Arguments.of("Capitali degli Stati del mondo", true),
                Arguments.of("ISO 3166-1", true),
                Arguments.of("Template:AT-5", false),
                Arguments.of("Template:FR-HDF", false),
                Arguments.of("Template:DE-HH", false),
                //                Arguments.of("Modulo:Bio/Plurale_attività", false),
                Arguments.of("ISO 3166-2:AL", true),
                Arguments.of("ISO 3166-2:FI", true),
                Arguments.of("ISO 3166-2:GB", true),
                Arguments.of("ISO 3166-2:IT", true),
                Arguments.of("ISO 3166-2:MD", true),
                Arguments.of("ISO 3166-2:AD", true),
                Arguments.of("ISO 3166-2:AM", true),
                Arguments.of("ISO 3166-2:AT", true),
                Arguments.of("ISO 3166-2:HR", true)
        );
    }

    //--title
    //--num prima
    //--num seconda
    //--num terza
    //--num quarta
    private Stream<Arguments> multiTable() {
        return Stream.of(
                Arguments.of("Piozzano", 0, 0, 0, 0),
                Arguments.of("Capitali degli Stati del mondo", 195, 0, 0, 0),
                Arguments.of("ISO 3166-1", 249, 0, 0, 0),
                Arguments.of("Template:AT-5", 0, 0, 0, 0),
                Arguments.of("ISO 3166-2:AT", 9, 0, 0, 0),
                Arguments.of("ISO 3166-2:FR", 12, 4, 95, 5),
                Arguments.of("ISO 3166-2:IT", 20, 14, 92, 0)
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = WebService.class;
        super.setUpAll();
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    @Test
    @Order(10)
    @DisplayName("10 - legge")
    void legge() {
        System.out.println(("10 - Legge il testo di una generica pagina web"));
        System.out.println(VUOTA);
        domain().forEach(this::fixLegge);
    }

    //--domain
    //--esiste
    //--testo
    void fixLegge(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];
        previsto = (String) mat[2];
        String esistenza = previstoBooleano ? "Esiste" : "Non esiste";
        String inizio;
        int max = 80;

        ottenuto = service.legge(sorgente);
        assertEquals(previstoBooleano, textService.isValid(ottenuto));

        inizio = ottenuto != null ? ottenuto.substring(0, Math.min(max, ottenuto.length())) : "";
        sorgente = textService.isValid(sorgente) ? sorgente : NULLO;
        inizio = textService.isValid(inizio) ? inizio : NULLO;
        message = String.format("[%s] %s%s%s", esistenza, sorgente, FORWARD, inizio);
        System.out.println(message);
    }

    @Test
    @Order(11)
    @DisplayName("11 - esiste pagina")
    void esiste() {
        System.out.println(("11 - Controlla l'esistenza di una pagina wiki"));
        System.out.println(VUOTA);

        sorgente = "Piozzano";
        previstoBooleano = true;
        ottenutoBooleano = service.isEsisteWiki(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);

        sorgente = "Non esiste";
        previstoBooleano = false;
        ottenutoBooleano = service.isEsisteWiki(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }

    @Test
    @Order(21)
    @DisplayName("21 - leggeWikiParse")
    void leggeWikiParse() {
        System.out.println(("21 - Legge il contento [wikitext]  di una pagina wiki"));
        System.out.println(VUOTA);
        wikiPage().forEach(this::fixLeggeWikiParse);
    }

    //--title
    //--esiste table
    void fixLeggeWikiParse(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];
        String inizio;
        int max = 120;

        ottenuto = service.leggeWikiParse(sorgente);
        assertTrue(textService.isValid(ottenuto));
        assertFalse(ottenuto.contains("The page you specified doesn't exist"));
        if (previstoBooleano) {
            message = String.format("Prevista la table. Vedi test n. 22");
        }
        else {
            message = String.format("Table non prevista per la pagina wiki [%s]", sorgente);
            System.out.println(message);
            inizio = ottenuto != null ? ottenuto.substring(0, Math.min(max, ottenuto.length())) : VUOTA;
            System.out.println(inizio);
        }
    }


    @Test
    @Order(22)
    @DisplayName("22 - leggeWikiTable")
    void leggeWikiTable() {
        System.out.println(("22 - Legge il testo di una tabella/table di una pagina wiki"));
        System.out.println(VUOTA);
        wikiPage().forEach(this::fixLeggeWikiTable);
    }

    //--title
    //--esiste table
    void fixLeggeWikiTable(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];
        String inizio;
        int max = 140;

        ottenuto = service.leggeWikiTable(sorgente);
        assertEquals(previstoBooleano, textService.isValid(ottenuto));

        if (previstoBooleano) {
            inizio = ottenuto != null ? ottenuto.substring(0, Math.min(max, ottenuto.length())) : VUOTA;
            System.out.print(sorgente);
            System.out.print(FORWARD);
            System.out.println(inizio);
        }
        else {
            message = String.format("Table non prevista per la pagina wiki [%s]", sorgente);
            System.out.println(message);
            inizio = ottenuto != null ? ottenuto.substring(0, Math.min(max, ottenuto.length())) : VUOTA;
            System.out.println(inizio);
        }

        assertFalse(ottenuto.contains("The page you specified doesn't exist"));
    }

    @Test
    @Order(23)
    @DisplayName("23 - getWikiTable")
    void getWikiTable() {
        System.out.println(("23 - Legge una lista di valori di una tabella/table di una pagina wiki"));
        System.out.println(VUOTA);
        wikiPage().forEach(this::fixGetWikiTable);
    }

    //--title
    //--esiste table
    void fixGetWikiTable(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];
        String inizio;
        int max = 120;

        listaTable = service.getWikiTable(sorgente);
        assertEquals(previstoBooleano, listaTable != null);

        if (previstoBooleano) {
            assertNotNull(listaTable);
            System.out.print(FORWARD);
            System.out.println(sorgente);
            System.out.println(listaTable.get(0));
            System.out.println(listaTable.size() > 1 ? listaTable.get(1) : VUOTA);
            System.out.println(listaTable.size() > 2 ? listaTable.get(2) : VUOTA);
            System.out.println(VUOTA);
        }
        else {
            message = String.format("Table non prevista per la pagina wiki [%s]", sorgente);
            System.out.println(message);
            inizio = ottenuto != null ? ottenuto.substring(0, Math.min(max, ottenuto.length())) : VUOTA;
            System.out.println(inizio);
        }
    }

    //    @Test
    @Order(24)
    @DisplayName("24 - getWikiTableIso")
    void getWikiTableIso() {
        System.out.println(("24 - Legge una lista di valori di una tabella/table di una pagina wiki di regioni"));
        System.out.println(VUOTA);
        String nomePaginaWiki;

        List<StatoEntity> listaEuropa = statoModulo.findAllEuropa();
        if (listaEuropa != null) {
            for (StatoEntity stato : listaEuropa) {
                if (stato.alfa2.equals("VA")) {
                    break;
                }
                nomePaginaWiki = ISO_PREFIX + stato.alfa2;
                assertTrue(textService.isValid(nomePaginaWiki));
                listaTable = service.getWikiTable(nomePaginaWiki);
                assertNotNull(listaTable);
                message = String.format("[%s]%spagina [%s]", stato.nome, FORWARD, nomePaginaWiki);
                System.out.println(message);
                System.out.println(listaTable.get(0));
                if (listaTable.size() > 1) {
                    System.out.println(listaTable.get(1));
                }
                if (listaTable.size() > 2) {
                    System.out.println(listaTable.get(2));
                }
                System.out.println(VUOTA);
            }
        }
    }

    @Test
    @Order(25)
    @DisplayName("25 - getWikiTableMulti")
    void getWikiTableMulti() {
        System.out.println(("25 - Legge una lista di valori di una tabella/table di una pagina wiki con diverse tabelle"));
        System.out.println(VUOTA);
        multiTable().forEach(this::fixTableMulti);
    }

    //--title
    //--num prima
    //--num seconda
    //--num terza
    //--num quarta
    void fixTableMulti(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        int prima = (int) mat[1];
        int seconda = (int) mat[2];
        int terza = (int) mat[3];
        int quarta = (int) mat[4];

        sorgenteIntero = 1;
        listaTable = service.getWikiTable(sorgente, sorgenteIntero);
        if (prima > 0) {
            if (listaTable == null) {
                message = String.format("Nella pagina [%s] non ci sono tabelle che invece erano previste", sorgente);
                System.out.println(message);
                assertNotNull(listaTable);
            }
            else {
                assertEquals(prima, listaTable.size());
                message = String.format("Nella pagina [%s] esiste almeno una tabella e contiene %d elementi", sorgente, prima);
                printTable(listaTable, message);
            }
        }
        else {
            if (listaTable != null) {
                message = String.format("Nella pagina [%s] c'è una tabella che non era prevista)", sorgente);
                System.out.println(message);
                assertNull(listaTable);
            }
            else {
                message = String.format("Nella pagina [%s] non ci sono tabelle (e non erano previste)", sorgente);
                System.out.println(message);
            }
        }

        sorgenteIntero = 2;
        listaTable = service.getWikiTable(sorgente, sorgenteIntero);
        if (seconda > 0) {
            if (listaTable == null) {
                message = String.format("Nella pagina [%s] manca la seconda tabella che invece era prevista)", sorgente);
                System.out.println(message);
                assertNotNull(listaTable);
            }
            else {
                assertEquals(seconda, listaTable.size());
                message = String.format("Nella pagina [%s] esiste la seconda tabella e contiene %d elementi", sorgente, listaTable.size());
                printTable(listaTable, message);
            }
        }
        else {
            if (listaTable != null) {
                message = String.format("Nella pagina [%s] c'è una seconda tabella che non era prevista)", sorgente);
                System.out.println(message);
                assertNull(listaTable);
            }
            else {
                message = String.format("Nella pagina [%s] manca la seconda tabella che non era prevista", sorgente);
                System.out.println(message);
            }
        }

        sorgenteIntero = 3;
        listaTable = service.getWikiTable(sorgente, sorgenteIntero);
        if (terza > 0) {
            if (listaTable == null) {
                message = String.format("Nella pagina [%s] manca la terza tabella che invece era prevista)", sorgente);
                System.out.println(message);
                assertNotNull(listaTable);
            }
            else {
                assertEquals(terza, listaTable.size());
                message = String.format("Nella pagina [%s] esiste la terza tabella e contiene %d elementi", sorgente, listaTable.size());
                printTable(listaTable, message);
            }
        }
        else {
            if (listaTable != null) {
                message = String.format("Nella pagina [%s] c'è una terza tabella che non era prevista)", sorgente);
                System.out.println(message);
                assertNull(listaTable);
            }
            else {
                message = String.format("Nella pagina [%s] manca la terza tabella che non era prevista", sorgente);
                System.out.println(message);
            }
        }

        sorgenteIntero = 4;
        listaTable = service.getWikiTable(sorgente, sorgenteIntero);
        if (quarta > 0) {
            if (listaTable == null) {
                message = String.format("Nella pagina [%s] manca la quarta tabella che invece era prevista)", sorgente);
                System.out.println(message);
                assertNotNull(listaTable);
            }
            else {
                assertEquals(quarta, listaTable.size());
                message = String.format("Nella pagina [%s] esiste la quarta tabella e contiene %d elementi", sorgente, listaTable.size());
                printTable(listaTable, message);
            }
        }
        else {
            if (listaTable != null) {
                message = String.format("Nella pagina [%s] c'è una quarta tabella che non era prevista)", sorgente);
                System.out.println(message);
                assertNull(listaTable);
            }
            else {
                message = String.format("Nella pagina [%s] manca la quarta tabella che non era prevista", sorgente);
                System.out.println(message);
            }
        }
        System.out.println(VUOTA);
    }

    protected void printTable(List<List<String>> listaTable, String message) {
        System.out.println(VUOTA);

        System.out.println(message);
        System.out.println(listaTable.get(0));
        if (listaTable.size() > 1) {
            System.out.println(listaTable.get(1));
        }
        if (listaTable.size() > 2) {
            System.out.println(listaTable.get(2));
        }
        System.out.println(VUOTA);
    }

}
