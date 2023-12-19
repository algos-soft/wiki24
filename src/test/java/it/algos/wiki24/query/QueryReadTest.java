package it.algos.wiki24.query;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.login.*;
import it.algos.wiki24.backend.query.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.core.env.*;

import javax.inject.*;


/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: mer, 11-mag-2022
 * Time: 06:52
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("query")
@DisplayName("Test QueryRead")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryReadTest extends QueryTest {

    /**
     * Classe principale di riferimento <br>
     */
    private QueryRead istanza;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = QueryRead.class;
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
    @DisplayName("1 - Costruttore base senza parametri")
    void costruttoreBase() {
        istanza = new QueryRead();
        assertNotNull(istanza);
        System.out.println(("1 - Costruttore base senza parametri"));
        System.out.println(VUOTA);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }

    @Test
    @Order(2)
    @DisplayName("2 - Request errata. Manca il wikiTitle")
    void manca() {
        System.out.println(("2 - Request errata. Manca il wikiTitle"));
        System.out.println(VUOTA);

        ottenutoRisultato = appContext.getBean(QueryRead.class).urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertFalse(ottenutoRisultato.isValido());
        System.out.println(VUOTA);
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(3)
    @DisplayName("3 - Request errata. Non esiste la pagina")
    void inesistente() {
        System.out.println(("3 - Request errata. Non esiste la pagina"));
        System.out.println(VUOTA);

        sorgente = "Pippoz Belloz";
        ottenutoRisultato = appContext.getBean(QueryRead.class).urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertFalse(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(4)
    @DisplayName("4 - Request valida")
    void valida() {
        System.out.println(("4 - Request valida"));
        System.out.println(VUOTA);

        sorgente = "Utente:Biobot/2";
        ottenutoRisultato = appContext.getBean(QueryRead.class).urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(5)
    @DisplayName("5 - Altra request valida")
    void valida2() {
        System.out.println(("5 - Altra request valida"));
        System.out.println(VUOTA);

        sorgente = "Piozzano";
        ottenuto = appContext.getBean(QueryRead.class).getText(sorgente);
        assertTrue(textService.isValid(ottenuto));

        ottenuto = ottenuto.length() < MAX ? ottenuto : ottenuto.substring(0, Math.min(MAX, ottenuto.length()));
        System.out.println((ottenuto));
    }
    @Test
    @Order(6)
    @DisplayName("6 - Titolo 'strano'")
    void valida6() {
        System.out.println(("6 - Titolo 'strano'"));
        System.out.println(VUOTA);

        sorgente = "Othon & Tomasini";
        ottenuto = appContext.getBean(QueryRead.class).getText(sorgente);
        assertTrue(textService.isValid(ottenuto));

        ottenuto = ottenuto.length() < MAX ? ottenuto : ottenuto.substring(0, Math.min(MAX, ottenuto.length()));
        System.out.println((ottenuto));
    }




}