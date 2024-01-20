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
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
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
        ottenuto = appContext.getBean(QueryRead.class).getContent(sorgente);
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
        ottenuto = appContext.getBean(QueryRead.class).getContent(sorgente);
        assertTrue(textService.isValid(ottenuto));

        ottenuto = ottenuto.length() < MAX ? ottenuto : ottenuto.substring(0, Math.min(MAX, ottenuto.length()));
        System.out.println((ottenuto));
    }

    @Test
    @Order(7)
    @DisplayName("7 - PageId")
    void pageId() {
        System.out.println(("7 - PageId"));
        System.out.println(VUOTA);

        sorgenteLong = 3100691L;
        ottenuto = appContext.getBean(QueryRead.class).getContent(sorgenteLong);
        assertTrue(textService.isValid(ottenuto));

        ottenuto = ottenuto.length() < MAX ? ottenuto : ottenuto.substring(0, Math.min(MAX, ottenuto.length()));
        System.out.println((ottenuto));
    }


    @Test
    @Order(101)
    @DisplayName("101 - Legge tramite QueryService (wikiTitle)")
    void leggeTitle() {
        System.out.println(("101 - Legge tramite QueryService (wikiTitle)"));
        System.out.println(VUOTA);

        sorgente = "Othon & Tomasini";
        ottenuto = queryService.legge(sorgente);
        assertTrue(textService.isValid(ottenuto));

        System.out.println(("Pagina e testo trovati"));
        ottenuto = ottenuto.length() < MAX ? ottenuto : ottenuto.substring(0, Math.min(MAX, ottenuto.length()));
        System.out.println(VUOTA);
        System.out.println((ottenuto));
    }

    @Test
    @Order(102)
    @DisplayName("102 - Legge tramite QueryService (pageId)")
    void leggePageId() {
        System.out.println(("102 - Legge tramite QueryService (pageId)"));
        System.out.println(VUOTA);

        sorgenteLong = 3100691L;
        ottenuto = queryService.legge(sorgenteLong);
        assertTrue(textService.isValid(ottenuto));

        System.out.println(("Pagina e testo trovati"));
        ottenuto = ottenuto.length() < MAX ? ottenuto : ottenuto.substring(0, Math.min(MAX, ottenuto.length()));
        System.out.println(VUOTA);
        System.out.println((ottenuto));
    }

}