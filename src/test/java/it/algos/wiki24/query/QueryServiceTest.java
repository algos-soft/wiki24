package it.algos.wiki24.query;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.basetest.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.basetest.*;
import static it.algos.wiki24.basetest.WikiTest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.inject.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 21-Dec-2023
 * Time: 14:47
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("query")
@DisplayName("Test QueryService")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryServiceTest extends ServiceTest {

    @Inject
    private QueryService service;

    private WrapPage wrapPage;

    private WrapBio wrapBio;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = QueryService.class;
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
    }

    @Test
    @Order(101)
    @DisplayName("101 - Content una singola pagina")
    void legge() {
        System.out.println(("101 - Content una singola pagina"));
        System.out.println(VUOTA);

        sorgente = "Othon & Tomasini";
        ottenuto = service.legge(sorgente);
        assertTrue(textService.isValid(sorgente));

        System.out.println(("Pagina e testo trovati"));
        ottenuto = ottenuto.length() < MAX ? ottenuto : ottenuto.substring(0, Math.min(MAX, ottenuto.length()));
        System.out.println(VUOTA);
        System.out.println((ottenuto));
    }

    @Test
    @Order(102)
    @DisplayName("102 - WrapPage tramite wikiTitle (text)")
    void getPageText() {
        System.out.println(("102 - WrapPage tramite wikiTitle (text)"));

        sorgente = "Louis Winslow Austin";
        wrapPage = service.getPage(sorgente);
        assertNotNull(wrapPage);
        printWrapPage(wrapPage);
    }

    @Test
    @Order(103)
    @DisplayName("103 - WrapPage tramite pageIds (long)")
    void getPageLong() {
        System.out.println(("103 - WrapPage tramite pageIds (long)"));

        sorgenteLong = 8978579;
        wrapPage = service.getPage(sorgenteLong);
        assertNotNull(wrapPage);
        printWrapPage(wrapPage);
    }


    @Test
    @Order(104)
    @DisplayName("104 - WrapBio tramite wikiTitle (text)")
    void getBioText() {
        System.out.println(("104 - WrapBio tramite wikiTitle (text)"));

        sorgente = "Louis Winslow Austin";
        wrapBio = service.getBio(sorgente);
        assertNotNull(wrapBio);
        printWrapBio(wrapBio);
    }
    @Test
    @Order(105)
    @DisplayName("105 - WrapBio tramite pageIds (long)")
    void getBioLong() {
        System.out.println(("105 - WrapBio tramite pageIds (long)"));

        sorgenteLong = 8978579;
        wrapBio = service.getBio(sorgenteLong);
        assertNotNull(wrapBio);
        printWrapBio(wrapBio);
    }

    protected void printWrapPage(WrapPage wrap) {
        if (wrap != null) {
            System.out.println(VUOTA);
            System.out.println(String.format("WrapPage valido: %s", wrap.isValida()));
            System.out.println(String.format("WrapPage type: %s", wrap.getType()));
            System.out.println(String.format("WrapPage nameSpace: %s", wrap.getNameSpace()));
            System.out.println(String.format("WrapPage pageid: %s", wrap.getPageid()));
            System.out.println(String.format("WrapPage title: %s", wrap.getTitle()));
            System.out.println(String.format("WrapPage timeStamp: %s", wrap.getTimeStamp()));
            System.out.println(String.format("WrapPage content:"));
            System.out.println(String.format("%s", wrap.getContent()));
        }
    }


    protected void printWrapBio(WrapBio wrapBio) {
        if (wrapBio != null) {
            System.out.println(VUOTA);
            System.out.println(String.format("WrapBio valida: %s", wrapBio.isValida()));
            System.out.println(String.format("WrapBio pageid: %s", wrapBio.getPageid()));
            System.out.println(String.format("WrapBio title: %s", wrapBio.getTitle()));
            System.out.println(String.format("WrapBio timeStamp: %s", wrapBio.getTimeStamp()));
            System.out.println(String.format("WrapBio creataBioServer: %s", wrapBio.isCreataBioServer()));
            System.out.println(String.format("WrapBio creataBioMongo: %s", wrapBio.isCreataBioMongo()));
            System.out.println(String.format("WrapBio tmplBio:"));
            System.out.println(String.format("%s", wrapBio.getTemplBio()));
        }
    }

}
