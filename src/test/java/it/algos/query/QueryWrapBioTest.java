package it.algos.query;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.wiki.query.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: dom, 15-mag-2022
 * Time: 08:49
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Tag("query")
@DisplayName("Test QueryWrapBio")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryWrapBioTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private QueryWrapBio istanza;


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
        istanza = new QueryWrapBio();
        assertNotNull(istanza);
        System.out.println(("1- Costruttore base senza parametri"));
        System.out.println(VUOTA);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }

    @Test
    @Order(2)
    @DisplayName("2- Test per una pagina inesistente")
    void nonEsiste() {
        System.out.println(("2- Test per una pagina inesistente"));
        assertTrue(istanza == null);
        istanza = appContext.getBean(QueryWrapBio.class);
        assertNotNull(istanza);

        sorgenteLong = 13257755L;
        ottenutoRisultato = istanza.urlRequest(List.of(sorgenteLong));
        assertNotNull(ottenutoRisultato);
        assertFalse(ottenutoRisultato.isValido());

        listWrapBio = istanza.getWrap(List.of(sorgenteLong));
        assertNull(listWrapBio);

        System.out.println(VUOTA);
        System.out.println(String.format("La pagina [[%d]] non esiste su wikipedia", sorgenteLong));
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(3)
    @DisplayName("3- Test per una singola biografia esistente (urlRequest)")
    void urlRequest() {
        System.out.println(("3- Test per una singola biografia esistente (urlRequest)"));

        listWrapBio = appContext.getBean(QueryWrapBio.class).getWrap(List.of(132555L));
        assertNotNull(listWrapBio);
        assertTrue(listWrapBio.size() > 0);

        wrapBio = listWrapBio.get(0);
        System.out.println(VUOTA);
        System.out.println(String.format("Trovata 1 biografia (%s) su wikipedia", sorgente));
        printWrapBio(wrapBio);
    }


    @Test
    @Order(4)
    @DisplayName("4 - Test per due biografie esistenti (urlRequest)")
    void urlRequestLista() {
        System.out.println(("4 - Test per due biografie esistenti (urlRequest)"));

        listaPageIds = new ArrayList<>();
        listaPageIds.add(132555L);
        listaPageIds.add(134246L);
        listWrapBio = appContext.getBean(QueryWrapBio.class).getWrap(listaPageIds);
        assertNotNull(listWrapBio);
        assertTrue(listWrapBio.size() == 2);

        System.out.println(VUOTA);
        System.out.println(String.format("Lista di biografie (%d)", listWrapBio.size()));

        for (WrapBio wrapBio : listWrapBio) {
            printWrapBio(wrapBio);
        }
    }


    @Test
    @Order(5)
    @DisplayName("5 - Test per altre due biografie esistenti (urlRequest)")
    void urlRequestLista2() {
        System.out.println(("5 - Test per altre due biografie esistenti (urlRequest)"));

        listaPageIds = new ArrayList<>();
        listaPageIds.add(106234L);
        listaPageIds.add(105803L);
        listWrapBio = appContext.getBean(QueryWrapBio.class).getWrap(listaPageIds);
        assertNotNull(listWrapBio);
        assertTrue(listWrapBio.size() == 2);

        System.out.println(VUOTA);
        System.out.println(String.format("Lista di biografie (%d)", listWrapBio.size()));

        for (WrapBio wrapBio : listWrapBio) {
            printWrapBio(wrapBio);
        }
    }

    @Test
    @Order(20)
    @DisplayName("20 - Categoria media")
    void urlRequestListaCat() {
        System.out.println(("20 - Categoria media"));

        sorgente = CATEGORIA_ESISTENTE_MEDIA;
        listaPageIds = queryService.getCatIdsOrdered(sorgente);

        listWrapBio = appContext.getBean(QueryWrapBio.class).getWrap(listaPageIds);
        assertNotNull(listWrapBio);
        assertTrue(listWrapBio.size() > 0);

        message = String.format("Lista di %d biografie della categoria [%s] recuperate in %s", listWrapBio.size(), sorgente, getTime());
        System.out.println(message);

        for (WrapBio wrapBio : listWrapBio.subList(0, Math.min(10, listWrapBio.size()))) {
            printWrapBio(wrapBio);
        }
    }

    //    @Test
    @Order(30)
    @DisplayName("30 - Categoria lunga")
    void urlRequestListaCat2() {
        System.out.println(("30 - Categoria lunga"));
        sorgente = CATEGORIA_ESISTENTE_LUNGA;
        listaPageIds = queryService.getCatIdsOrdered(sorgente);

        listWrapBio = appContext.getBean(QueryWrapBio.class).getWrap(listaPageIds);
        assertNotNull(listWrapBio);
        assertTrue(listWrapBio.size() > 0);

        System.out.println(VUOTA);
        message = String.format("Lista di %d biografie della categoria [%s] recuperate in %s", listWrapBio.size(), sorgente, dateService.deltaText(inizio));
        System.out.println(message);

        for (WrapBio wrapBio : listWrapBio.subList(0, Math.min(10, listWrapBio.size()))) {
            printWrapBio(wrapBio);
        }
    }


//    @Test
    @Order(40)
    @DisplayName("40 - Categoria media bio")
    void urlRequestListaCatBio() {
        System.out.println(("40 - Categoria media bio"));

        sorgente = "Nati nel 1782";
        listaPageIds = queryService.getCatIdsOrdered(sorgente);
        listBio = appContext.getBean(QueryWrapBio.class).getBio(listaPageIds);
        assertNotNull(listBio);
        assertTrue(listBio.size() > 0);

        message = String.format("Lista di %d biografie della categoria [%s] recuperate in %s", listBio.size(), sorgente, getTime());
        System.out.println(message);

        printBio(listBio.subList(0, Math.min(10, listBio.size())));
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