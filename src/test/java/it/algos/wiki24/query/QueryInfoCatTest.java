package it.algos.wiki24.query;

import it.algos.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.query.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: ven, 29-apr-2022
 * Time: 21:20
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("query")
@DisplayName("Test QueryInfoCat")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryInfoCatTest extends QueryTest {


    /**
     * Classe principale di riferimento <br>
     */
    private QueryInfoCat istanza;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = QueryInfoCat.class;
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
    @DisplayName("4 - Test per una categoria inesistente")
    void nonEsiste() {
        System.out.println(("4 - Test per una pagina inesistente"));
        System.out.println(VUOTA);

        sorgente = CATEGORIA_INESISTENTE;
        ottenutoRisultato = appContext.getBean(QueryInfoCat.class).urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertFalse(ottenutoRisultato.isValido());
        assertEquals(TypePage.nonEsiste, ottenutoRisultato.getTypePage());
        assertTrue(ottenutoRisultato.getWrapPage() != null);
        assertEquals(TypePage.nonEsiste, ottenutoRisultato.getWrapPage().getType());

        System.out.println(VUOTA);
        System.out.println(String.format("La categoria [[%s]] non esiste su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }


    @Test
    @Order(5)
    @DisplayName("5 - Test per una categoria esistente")
    void urlRequest() {
        System.out.println(("5 - Test per una categoria esistente"));

        sorgente = CATEGORIA_ESISTENTE_MEDIA;
        ottenutoRisultato = appContext.getBean(QueryInfoCat.class).urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        assertTrue(ottenutoRisultato.getWrapPage() != null);
        assertEquals(TypePage.categoria, ottenutoRisultato.getTypePage());
        assertTrue(ottenutoRisultato.getIntValue() > 0);

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(6)
    @DisplayName("6 - Test per una categoria con PIPE nel titolo")
    void urlRequest4() {
        System.out.println(("6 - Test per una categoria con PIPE nel titolo"));

        sorgente = "2741616|27416167";
        ottenutoRisultato = appContext.getBean(QueryInfoCat.class).urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertFalse(ottenutoRisultato.isValido());
        assertTrue(ottenutoRisultato.getWrapPage() == null);
        assertEquals(TypePage.contienePipe, ottenutoRisultato.getTypePage());
        assertTrue(ottenutoRisultato.getIntValue() == 0);

        System.out.println(VUOTA);
        System.out.println(String.format("La categoria [[%s]] non esiste su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(7)
    @DisplayName("7- Test per la categoria BioBot")
    void urlRequest2() {
        System.out.println(("7 - Test per la categoria BioBot"));

        sorgente = CATEGORIA_PRINCIPALE_BIOBOT;
        ottenutoRisultato = appContext.getBean(QueryInfoCat.class).urlRequest(sorgente);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        assertTrue(ottenutoRisultato.getWrapPage() != null);
        assertEquals(TypePage.categoria, ottenutoRisultato.getTypePage());
        assertTrue(ottenutoRisultato.getIntValue() > 0);

        System.out.println(VUOTA);
        System.out.println(String.format("Trovata la categoria [[%s]] su wikipedia", sorgente));
        printRisultato(ottenutoRisultato);
    }


}