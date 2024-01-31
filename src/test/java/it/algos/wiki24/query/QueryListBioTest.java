package it.algos.wiki24.query;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.query.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.*;

import javax.inject.*;
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
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("query")
@DisplayName("Test QueryListBio")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryListBioTest extends QueryTest {


    /**
     * Classe principale di riferimento <br>
     */
    private QueryListBio istanza;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = QueryListBio.class;
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
    @DisplayName("4 - Test per una singola biografia esistente (urlRequest)")
    void urlRequest() {
        System.out.println(("4 - Test per una singola biografia esistente (urlRequest)"));

        sorgente = "Matteo Salvini";
        listWrapBio = appContext.getBean(QueryListBio.class).getLista(List.of(132555L));
        assertNotNull(listWrapBio);
        assertTrue(listWrapBio.size() > 0);

        wrapBio = listWrapBio.get(0);
        System.out.println(VUOTA);
        System.out.println(String.format("Trovata 1 biografia (%s) su wikipedia", sorgente));
        printWrapBio(wrapBio);
    }


    @Test
    @Order(5)
    @DisplayName("5 - Test per due biografie esistenti (urlRequest)")
    void urlRequestLista() {
        System.out.println(("5 - Test per due biografie esistenti (urlRequest)"));

        sorgente = "Salvini + Renzi";
        listaPageIds = new ArrayList<>();
        listaPageIds.add(132555L);
        listaPageIds.add(134246L);
        listWrapBio = appContext.getBean(QueryListBio.class).getLista(listaPageIds);
        assertNotNull(listWrapBio);
        assertTrue(listWrapBio.size() == 2);

        System.out.println(VUOTA);
        System.out.println(String.format("Lista di biografie (%d)%s%s", listWrapBio.size(), FORWARD, sorgente));

        for (WrapBio wrapBio : listWrapBio) {
            printWrapBio(wrapBio);
        }
    }


    @Test
    @Order(6)
    @DisplayName("6 - Test per altre due biografie esistenti (urlRequest)")
    void urlRequestLista2() {
        System.out.println(("6 - Test per altre due biografie esistenti (urlRequest)"));

        sorgente = "Salvini + Renzi";
        listaPageIds = new ArrayList<>();
        listaPageIds.add(106234L);
        listaPageIds.add(105803L);
        listWrapBio = appContext.getBean(QueryListBio.class).getLista(listaPageIds);
        assertNotNull(listWrapBio);
        assertTrue(listWrapBio.size() == 2);

        System.out.println(VUOTA);
        System.out.println(String.format("Lista di biografie (%d)%s%s", listWrapBio.size(), FORWARD, sorgente));

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
        listaPageIds = queryService.getPageIds(sorgente);

        listWrapBio = appContext.getBean(QueryListBio.class).getLista(listaPageIds);
        assertNotNull(listWrapBio);
        assertTrue(listWrapBio.size() > 0);

        message = String.format("Lista di %d biografie della categoria [%s] recuperate in %s", listWrapBio.size(), sorgente, dateService.deltaText(inizio));
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
        listaPageIds = queryService.getPageIds(sorgente);

        listWrapBio = appContext.getBean(QueryListBio.class).getLista(listaPageIds);
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
        listaPageIds = queryService.getPageIds(sorgente);
        //        listBio = appContext.getBean(QueryWrapBio.class).getBio(listaPageIds);
        //        assertNotNull(listBio);
        //        assertTrue(listBio.size() > 0);
        //
        //        message = String.format("Lista di %d biografie della categoria [%s] recuperate in %s", listBio.size(), sorgente, getTime());
        //        System.out.println(message);
        //
        //        printBio(listBio.subList(0, Math.min(10, listBio.size())));
    }


}