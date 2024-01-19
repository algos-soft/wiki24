package it.algos.wiki24.query;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.boot.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.query.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: ven, 29-apr-2022
 * Time: 14:21
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Tag("query")
@DisplayName("Test QueryExist")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryExistTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private QueryExist istanza;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();
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
        botLogin.reset();
    }


    @Test
    @Order(1)
    @DisplayName("1 - Costruttore base senza parametri")
    void costruttoreBase() {
        System.out.println(("1 - Costruttore base senza parametri"));
        assertTrue(istanza == null);
        istanza = appContext.getBean(QueryExist.class);
        assertNotNull(istanza);

        System.out.println(VUOTA);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }

    @Test
    @Order(2)
    @DisplayName("2 - Test per una pagina inesistente")
    void nonEsiste() {
        System.out.println(("2 - Test per una pagina inesistente"));
        assertTrue(istanza == null);
        istanza = appContext.getBean(QueryExist.class);
        assertNotNull(istanza);

        sorgente = PAGINA_INESISTENTE;
        ottenutoRisultato = istanza.urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertFalse(ottenutoRisultato.isValido());
        assertTrue(ottenutoRisultato.getWrapPage() == null);
        assertEquals(TypePage.nonEsiste, ottenutoRisultato.getTypePage());

        ottenutoBooleano = istanza.isEsiste(sorgente);
        assertFalse(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println(String.format("La pagina [[%s]] non esiste su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }


    @Test
    @Order(3)
    @DisplayName("3 - Test per una pagina inesistente come pageid")
    void nonEsistePageid() {
        System.out.println(("3 - Test per una pagina inesistente come pageid"));
        assertTrue(istanza == null);
        istanza = appContext.getBean(QueryExist.class);
        assertNotNull(istanza);

        sorgenteLong = 27416167;
        ottenutoRisultato = istanza.urlRequest(sorgenteLong);
        assertNotNull(ottenutoRisultato);
        assertFalse(ottenutoRisultato.isValido());
        assertTrue(ottenutoRisultato.getWrapPage() == null);
        assertEquals(TypePage.nonEsiste, ottenutoRisultato.getTypePage());

        ottenutoBooleano = istanza.isEsiste(sorgente);
        assertFalse(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println(String.format("La pagina '%d' (pageid) non esiste su wikipedia", sorgenteLong));
        printRisultato(ottenutoRisultato);
    }


    @Test
    @Order(4)
    @DisplayName("4 - Test per una pagina esistente (urlRequest)")
    void urlRequest() {
        System.out.println(("4 - Test per una pagina esistente (urlRequest)"));

        sorgente = BIO_SALVINI;
        ottenutoRisultato = appContext.getBean(QueryExist.class).urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        assertTrue(ottenutoRisultato.getWrapPage() == null);
        assertEquals(TypePage.pagina, ottenutoRisultato.getTypePage());

        System.out.println(VUOTA); System.out.println(String.format("Trovata la pagina [[%s]] su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(5)
    @DisplayName("5 - Test per una categoria esistente (urlRequest)")
    void urlRequest2() {
        System.out.println(("5 - Test per una categoria esistente (urlRequest)"));

        sorgente = CAT + CATEGORIA_ESISTENTE_LUNGA;
        ottenutoRisultato = appContext.getBean(QueryExist.class).urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        assertTrue(ottenutoRisultato.getWrapPage() == null);
        assertEquals(TypePage.categoria, ottenutoRisultato.getTypePage());

        System.out.println(VUOTA); System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }


    @Test
    @Order(6)
    @DisplayName("6 - Test per una pagina esistente come pageid")
    void esistePageid() {
        System.out.println(("6 - Test per una pagina esistente come pageid"));
        assertTrue(istanza == null);
        istanza = appContext.getBean(QueryExist.class);
        assertNotNull(istanza);

        sorgenteLong = 2741616;
        ottenutoRisultato = istanza.urlRequest(sorgenteLong);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        assertTrue(ottenutoRisultato.getWrapPage() == null);
        assertEquals(TypePage.pagina, ottenutoRisultato.getTypePage());

        ottenutoBooleano = istanza.isEsiste(sorgenteLong);
        assertTrue(ottenutoBooleano);

        System.out.println(VUOTA);
        System.out.println(VUOTA); System.out.println(String.format("Trovata la pagina '%d' (pageid) su wikipedia", sorgenteIntero));
        printRisultato(ottenutoRisultato);
    }


    @ParameterizedTest
    @MethodSource(value = "PAGINE_E_CATEGORIE")
    @Order(7)
    @DisplayName("7 - Test per pagine e categorie (isEsiste)")
        //--titolo
        //--pagina esistente
    void isEsiste(final String wikiTitleVoceOCategoria, final boolean paginaEsistente) {
        System.out.println(("7 - Test per pagine e categorie (isEsiste)"));

        sorgente = wikiTitleVoceOCategoria;
        ottenutoBooleano = appContext.getBean(QueryExist.class).isEsiste(sorgente);
        assertEquals(paginaEsistente, ottenutoBooleano);

        System.out.println(VUOTA);
        if (ottenutoBooleano) {
            System.out.println(String.format("Trovata la pagina/categoria [[%s]] su wikipedia", sorgente));
        }
        else {
            System.out.println(String.format("La pagina/categoria [[%s]] non esiste su wikipedia", sorgente));
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