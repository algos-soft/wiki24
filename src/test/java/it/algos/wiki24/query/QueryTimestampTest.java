package it.algos.wiki24.query;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.query.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.basetest.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 20-Jan-2024
 * Time: 15:34
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("query")
@DisplayName("Test QueryTimestamp")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryTimestampTest extends QueryTest {


    /**
     * Classe principale di riferimento <br>
     */
    private QueryTimestamp istanza;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = QueryTimestamp.class;
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
    @DisplayName("4 - Test di wrapTime per una singola biografia esistente (urlRequest)")
    void urlRequest() {
        System.out.println(("4 - Test di wrapTime per una singola biografia esistente (urlRequest)"));

        sorgente = "Matteo Salvini";
        listWrapTime = appContext.getBean(QueryTimestamp.class).getLista(List.of(132555L));
        assertNotNull(listWrapTime);
        assertTrue(listWrapTime.size() > 0);

        wrapTime = listWrapTime.get(0);
        System.out.println(VUOTA);
        System.out.println(String.format("Trovata 1 biografia (%s) su wikipedia", sorgente));
        printWrapTime(wrapTime);
    }


    @Test
    @Order(5)
    @DisplayName("5 - Test di wrapTime per due biografie esistenti (urlRequest)")
    void urlRequestLista() {
        System.out.println(("5 - Test di wrapTime per due biografie esistenti (urlRequest)"));

        sorgente = "Salvini + Renzi";
        listaPageIds = new ArrayList<>();
        listaPageIds.add(132555L);
        listaPageIds.add(134246L);
        listWrapTime = appContext.getBean(QueryTimestamp.class).getLista(listaPageIds);
        assertNotNull(listWrapTime);
        assertTrue(listWrapTime.size() > 0);

        for (WrapTime wrapTime : listWrapTime) {
            printWrapTime(wrapTime);
        }
    }


    @Test
    @Order(20)
    @DisplayName("20 - Categoria media")
    void urlRequestListaCat() {
        System.out.println(("20 - Categoria media"));

        sorgente = CATEGORIA_ESISTENTE_MEDIA;
        listaPageIds = queryService.getPageIds(sorgente);

        listWrapTime = appContext.getBean(QueryTimestamp.class).getLista(listaPageIds);
        assertNotNull(listWrapTime);
        assertTrue(listWrapTime.size() > 0);

        message = String.format("Lista di %d wrapTime della categoria [%s] recuperate in %s", listWrapTime.size(), sorgente,
                dateService.deltaText(inizio)
        );
        System.out.println(message);

        for (WrapTime wrapTime : listWrapTime.subList(0, Math.min(10, listWrapTime.size()))) {
            printWrapTime(wrapTime);
        }
    }

    @Test
    @Order(30)
    @DisplayName("30 - Categoria lunga")
    void urlRequestListaCat2() {
        System.out.println(("30 - Categoria lunga"));

        sorgente = CATEGORIA_ESISTENTE_LUNGA;
        listaPageIds = queryService.getPageIds(sorgente);

        listWrapTime = appContext.getBean(QueryTimestamp.class).getLista(listaPageIds);
        assertNotNull(listWrapTime);
        assertTrue(listWrapTime.size() > 0);

        message = String.format("Lista di %d wrapTime della categoria [%s] recuperate in %s", listWrapTime.size(), sorgente,
                dateService.deltaText(inizio)
        );
        System.out.println(message);

        for (WrapTime wrapTime : listWrapTime.subList(0, Math.min(10, listWrapTime.size()))) {
            printWrapTime(wrapTime);
        }
    }

}
