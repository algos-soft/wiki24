package it.algos.query;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.wiki.query.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.*;

import java.time.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Tue, 07-Jun-2022
 * Time: 17:43
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("query")
@DisplayName("Test QueryWrite")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryWriteTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private QueryWrite istanza;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
        assertNull(istanza);
    }


    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
        istanza = null;
    }


    @Test
    @Order(1)
    @DisplayName("1- Costruttore base senza parametri")
    void costruttoreBase() {
        istanza = new QueryWrite();
        assertNotNull(istanza);
        System.out.println(("1- Costruttore base senza parametri"));
        System.out.println(VUOTA);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }

    //    @Test
    @Order(2)
    @DisplayName("2 - Request errata. Manca il wikiTitle")
    void errata2() {
        System.out.println(("2 - Request errata. Manca il wikiTitle"));

        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequest(sorgente, sorgente2);
        assertNotNull(ottenutoRisultato);
        assertFalse(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
    }


    //    @Test
    @Order(3)
    @DisplayName("3 - Request errata. Manca il newText")
    void errata3() {
        System.out.println(("3 - Request errata. Manca il newText"));

        sorgente = "Utente:Biobot/2";
        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequest(sorgente, sorgente2);
        assertNotNull(ottenutoRisultato);
        assertFalse(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
    }


    //    @Test
    @Order(4)
    @DisplayName("4 - Request valida")
    void corretta() {
        System.out.println(("4 - Request valida"));

        sorgente = "Utente:Biobot/2";
        sorgente2 = String.format("Prova delle %s", System.currentTimeMillis());
        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequest(sorgente, sorgente2);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
        printWrapBio(ottenutoRisultato.getWrap());
    }

    //    @Test
    @Order(5)
    @DisplayName("5 - Request valida con summary")
    void summary() {
        System.out.println(("5 - Request valida con summary"));

        sorgente = "Utente:Biobot/2";
        sorgente2 = String.format("Prova delle %s", System.currentTimeMillis());
        sorgente3 = "Test";
        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequest(sorgente, sorgente2, sorgente3);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
        printWrapBio(ottenutoRisultato.getWrap());
    }

    //    @Test
    @Order(6)
    @DisplayName("6 - Request con controllo dati/tmpl iniziale")
    void limitata() {
        System.out.println(("6 - Request con controllo dati/tmpl iniziale"));
        System.out.println(VUOTA);
        System.out.println("Prima leggo un contenuto corposa da un'altra pagina e lo inserisco nella pagina di prova");
        System.out.println("Provo a riscrivere la pagina di prova precisando la parte di testo SIGNIFICATIVO");
        System.out.println("Se cambia SOLO la parte 'modificabile' iniziale e non quella 'significativa', la query NON deve scrivere");
        System.out.println("Se cambia ANCHE la parte 'significativa' finale, la query DEVE scrivere");

        System.out.println(VUOTA);
        System.out.println("Leggo una corposa pagina esistente");
        sorgente3 = "Progetto:Biografie/Attività/Abati e badesse";
        ottenuto = appContext.getBean(QueryRead.class).getText(sorgente3);
        assertTrue(textService.isValid(ottenuto));
        System.out.println("Fatto");

        System.out.println(VUOTA);
        System.out.println("Scrivo un contenuto corposo nella pagina test. Controllo che sia stato scritto");
        sorgente = "Utente:Biobot/2";
        sorgente2 = ottenuto;
        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequest(sorgente, sorgente2);
        ottenuto2 = appContext.getBean(QueryRead.class).getText(sorgente);
        assertEquals(ottenuto, ottenuto2);
        System.out.println("Fatto");

        System.out.println(VUOTA);
        System.out.println("Provo a riscrivere lo stesso contenuto, senza nessun controllo. Non scrive.");
        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequest(sorgente, sorgente2);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        assertFalse(ottenutoRisultato.isModificata());
        printRisultato(ottenutoRisultato);

        System.out.println(VUOTA);
        System.out.println("Provo a scrivere il contenuto con una minima (xyz) modifica iniziale, senza nessun controllo. Scrive.");
        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequest(sorgente, "xyz" + sorgente2);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        assertTrue(ottenutoRisultato.isModificata());
        printRisultato(ottenutoRisultato);

        System.out.println(VUOTA);
        System.out.println("Provo a scrivere il contenuto, con check control. Mancano dati.");
        sorgente3 = "";
        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequestCheck(sorgente, sorgente2, sorgente3);
        assertNotNull(ottenutoRisultato);
        assertFalse(ottenutoRisultato.isValido());
        assertFalse(ottenutoRisultato.isModificata());
        printRisultato(ottenutoRisultato);

        System.out.println(VUOTA);
        System.out.println("Provo a scrivere il contenuto, con check control. Dati errati.");
        sorgente3 = "Pippoz";
        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequestCheck(sorgente, sorgente2, sorgente3);
        assertNotNull(ottenutoRisultato);
        assertFalse(ottenutoRisultato.isValido());
        assertFalse(ottenutoRisultato.isModificata());
        printRisultato(ottenutoRisultato);

        System.out.println(VUOTA);
        System.out.println("Provo a scrivere il contenuto, con check control. Testo significativo NON modificato. Non scrive.");
        sorgente3 = sorgente2.substring(sorgente2.length() - 50, sorgente2.length() - 25) + sorgente2.substring(sorgente2.length() - 25);
        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequestCheck(sorgente, sorgente2, sorgente3);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        assertFalse(ottenutoRisultato.isModificata());
        printRisultato(ottenutoRisultato);

        System.out.println(VUOTA);
        System.out.println("Ripristino il contenuto nella parte iniziale. Rimetto senza (xyz).");
        System.out.println("Modifico il contenuto nella parte significativa. Scrive senza controllo.");
        String sorgente2Old = sorgente2;
        String sorgente3Old = sorgente2.substring(sorgente2.length() - 100);
        sorgente2 = sorgente2.substring(0, sorgente2.length() - 25) + "x" + sorgente2.substring(sorgente2.length() - 25);
        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequest(sorgente, sorgente2);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        assertTrue(ottenutoRisultato.isModificata());
        printRisultato(ottenutoRisultato);

        System.out.println(VUOTA);
        System.out.println("Provo a scrivere il contenuto, con check control. Testo significativo modificato. Scrive.");
        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequestCheck(sorgente, sorgente2Old, sorgente3Old);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        assertTrue(ottenutoRisultato.isModificata());
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(11)
    @DisplayName("11 - Upload")
    void upload() {
        WResult result = WResult.crea();

        sorgente = "Utente:Biobot/Abba2";
        sorgente2 = "Primo testo creazione " + LocalDateTime.now();
        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequest(sorgente, sorgente2);
        if (ottenutoRisultato.isValido()) {
            switch (ottenutoRisultato.getTypeResult()) {
                case queryWriteCreata -> result.typeResult(AETypeResult.uploadNuova);
                case queryWriteModificata -> result.typeResult(AETypeResult.uploadModificata);
                case queryWriteEsistente -> result.typeResult(AETypeResult.uploadUguale);
                default -> {}
            }
        }
        else {
            result.typeResult(AETypeResult.uploadErrato);
        }
        printRisultato(result);
        assertEquals(result.getTypeResult(), AETypeResult.uploadModificata);
        System.out.println(VUOTA);
        System.out.println(VUOTA);

        sorgente2 = "Nuovo testo modificato";
        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequest(sorgente, sorgente2);
        if (ottenutoRisultato.isValido()) {
            switch (ottenutoRisultato.getTypeResult()) {
                case queryWriteCreata -> result.typeResult(AETypeResult.uploadNuova);
                case queryWriteModificata -> result.typeResult(AETypeResult.uploadModificata);
                case queryWriteEsistente -> result.typeResult(AETypeResult.uploadUguale);
                default -> {}
            }
        }
        else {
            result.typeResult(AETypeResult.uploadErrato);
        }
        printRisultato(result);
        assertEquals(result.getTypeResult(), AETypeResult.uploadModificata);
        System.out.println(VUOTA);
        System.out.println(VUOTA);

        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequest(sorgente, sorgente2);
        if (ottenutoRisultato.isValido()) {
            switch (ottenutoRisultato.getTypeResult()) {
                case queryWriteCreata -> result.typeResult(AETypeResult.uploadNuova);
                case queryWriteModificata -> result.typeResult(AETypeResult.uploadModificata);
                case queryWriteEsistente -> result.typeResult(AETypeResult.uploadUguale);
                default -> {}
            }
        }
        else {
            result.typeResult(AETypeResult.uploadErrato);
        }
        printRisultato(result);
        assertEquals(result.getTypeResult(), AETypeResult.uploadUguale);

    }


    /**
     * Qui passa al termine di ogni singolo test <br>
     */
    @AfterEach
    void tearDown() {
    }


    /**
     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
     */
    @AfterAll
    void tearDownAll() {
    }

}