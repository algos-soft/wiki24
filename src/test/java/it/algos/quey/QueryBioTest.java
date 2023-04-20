package it.algos.quey;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.wiki.query.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 14-Apr-2023
 * Time: 18:48
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("query")
@DisplayName("QueryBio")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryBioTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private QueryBio istanza;

    private WrapBio wrapBio;

    private Bio bio;

    private String tmplBio;


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
        wrapBio = null;
        bio = null;
        tmplBio = VUOTA;
    }


    @Test
    @Order(1)
    @DisplayName("1 - Costruttore base senza parametri")
    void costruttoreBase() {
        System.out.println(("1 - Costruttore base senza parametri"));
        System.out.println(VUOTA);

        istanza = new QueryBio();
        assertNotNull(istanza);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }

    @Test
    @Order(2)
    @DisplayName("2 - getBean base senza parametri")
    void getBean() {
        System.out.println(("2 - getBean base senza parametri"));
        System.out.println(VUOTA);

        istanza = appContext.getBean(QueryBio.class);
        assertNotNull(istanza);
        System.out.println(String.format("getBean base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }


    @Test
    @Order(3)
    @DisplayName("3 - getWrap")
    protected void getWrap() {
        System.out.println("3 - getWrap");
        System.out.println(VUOTA);

        VOCE_BIOGRAFICA().forEach(parameters -> this.getWrap(parameters));
    }

    //--nome della pagina
    //--esiste sul server wiki
    protected void getWrap(Arguments arg) {
        Object[] mat = arg.get();
        if (mat != null && mat.length > 0 && mat[0] instanceof String keyValue) {
            sorgente = keyValue;
        }
        else {
            assertTrue(false);
        }
        if (mat != null && mat.length > 1 && mat[1] instanceof Boolean keyValue) {
            previstoBooleano = keyValue;
        }
        else {
            assertTrue(false);
        }

        wrapBio = appContext.getBean(QueryBio.class).getWrap(sorgente);
        if (wrapBio != null && wrapBio.isValida()) {
            message = String.format("[%s]: Sul server wiki ESISTE una pagina dal titolo%s%s", sorgente, FORWARD, sorgente);
        }
        else {
            message = String.format("[%s]: Sul server wiki NON ESISTE nessuna pagina dal titolo%s%s", sorgente, FORWARD, sorgente);
        }
        System.out.println(VUOTA);
        System.out.println(message);
        assertEquals(wrapBio != null && wrapBio.isValida(), previstoBooleano);
    }


    @Test
    @Order(4)
    @DisplayName("4 - getBio")
    protected void getBio() {
        System.out.println("4 - getBio");
        System.out.println(VUOTA);

        VOCE_BIOGRAFICA().forEach(parameters -> this.getBio(parameters));
    }


    //--nome della pagina
    //--esiste sul server wiki
    protected void getBio(Arguments arg) {
        Object[] mat = arg.get();
        if (mat != null && mat.length > 0 && mat[0] instanceof String keyValue) {
            sorgente = keyValue;
        }
        else {
            assertTrue(false);
        }
        if (mat != null && mat.length > 1 && mat[1] instanceof Boolean keyValue) {
            previstoBooleano = keyValue;
        }
        else {
            assertTrue(false);
        }

        bio = appContext.getBean(QueryBio.class).getBioGrezzo(sorgente);
        if (bio != null) {
            message = String.format("[%s]: Sono riuscito a recuperare una Bio dal wikiTitle%s%s", sorgente, FORWARD, sorgente);
        }
        else {
            message = String.format("[%s]: Non esiste una Bio col wikiTitle%s%s", sorgente, FORWARD, sorgente);
        }
        System.out.println(VUOTA);
        System.out.println(message);
        assertEquals(bio != null, previstoBooleano);
        if (bio != null) {
            System.out.println(String.format("wikiTitle: %s", bio.wikiTitle));
            System.out.println(String.format("pageId: %s", bio.pageId));
        }
    }


    @Test
    @Order(5)
    @DisplayName("5 - getTmplBio")
    protected void getTmplBio() {
        System.out.println("5 - getTmplBio");
        System.out.println(VUOTA);

        VOCE_BIOGRAFICA().forEach(parameters -> this.getTmplBio(parameters));
    }


    //--nome della pagina
    //--esiste sul server wiki
    protected void getTmplBio(Arguments arg) {
        Object[] mat = arg.get();
        if (mat != null && mat.length > 0 && mat[0] instanceof String keyValue) {
            sorgente = keyValue;
        }
        else {
            assertTrue(false);
        }
        if (mat != null && mat.length > 1 && mat[1] instanceof Boolean keyValue) {
            previstoBooleano = keyValue;
        }
        else {
            assertTrue(false);
        }

        ottenuto = appContext.getBean(QueryBio.class).getTmplBio(sorgente);
        if (textService.isValid(ottenuto)) {
            message = String.format("[%s]: Sono riuscito a recuperare il tmplBio che esiste", sorgente, FORWARD, sorgente);
        }
        else {
            message = String.format("[%s]: Non esiste una Bio col wikiTitle%s%s", sorgente, FORWARD, sorgente);
        }
        System.out.println(VUOTA);
        System.out.println(message);
        assertEquals(textService.isValid(ottenuto), previstoBooleano);
        if (textService.isValid(ottenuto)) {
            System.out.println(String.format(ottenuto));
        }
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