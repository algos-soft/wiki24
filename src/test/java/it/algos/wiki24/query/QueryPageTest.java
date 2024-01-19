package it.algos.wiki24.query;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.query.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 19-Dec-2023
 * Time: 07:31
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("query")
@DisplayName("Test QueryPage")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryPageTest extends QueryTest {


    /**
     * Classe principale di riferimento <br>
     */
    private QueryPage istanza;

    private WrapPage wrapPage;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = QueryPage.class;
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
        wrapPage = null;
    }


    @ParameterizedTest
    @MethodSource(value = "PAGINE_E_CATEGORIE")
    @Order(51)
    @DisplayName("51 - Recupera wrapPage dal wikiTitle")
        //--titolo
        //--pagina esistente
    void wrapPageTitle(final String wikiTitleVoceOppureCategoria, final boolean paginaEsistente) {
        System.out.println(("51 - Recupera wrapPage dal wikiTitle"));

        sorgente = wikiTitleVoceOppureCategoria;
        wrapPage = appContext.getBean(QueryPage.class).getPage(sorgente);
        assertNotNull(wrapPage);
        System.out.println(VUOTA);
        if (paginaEsistente) {
            if (wrapPage.isValida()) {
                System.out.println(String.format("Trovata la pagina/categoria [[%s]] su wikipedia", sorgente));
            }
            else {
                System.out.println(String.format("Errore"));
            }
        }
        else {
            if (wrapPage.isValida()) {
                System.out.println(String.format("Bho ?"));
            }
            else {
                System.out.println(String.format("La pagina/categoria [[%s]] non esiste su wikipedia", sorgente));
            }
        }
        printWrapPage(wrapPage, true);
    }


    @ParameterizedTest
    @MethodSource(value = "PAGINE")
    @Order(52)
    @DisplayName("52 - Recupera wrapPage dal pageIds")
        //--titolo
        //--pagina esistente
    void wrapPageIds(final long pageIds, final boolean paginaEsistente) {
        System.out.println(("52 - Recupera wrapPage dal pageIds"));

        wrapPage = appContext.getBean(QueryPage.class).getPage(pageIds);
        assertNotNull(wrapPage);
        System.out.println(VUOTA);
        if (paginaEsistente) {
            if (wrapPage.isValida()) {
                System.out.println(String.format("Trovata la pagina/categoria [[%s]] su wikipedia", pageIds));
            }
            else {
                System.out.println(String.format("Errore"));
            }
        }
        else {
            if (wrapPage.isValida()) {
                System.out.println(String.format("Bho ?"));
            }
            else {
                System.out.println(String.format("La pagina/categoria [[%s]] non esiste su wikipedia", pageIds));
            }
        }
        printWrapPage(wrapPage, true);
    }

    @Test
    @Order(101)
    @DisplayName("101 - QueryService.getPage tramite wikiTile (text)")
    void getPageText() {
        System.out.println(("101 - QueryService.getPage tramite wikiTile (text)"));

        sorgente = "Louis Winslow Austin";
        wrapPage = queryService.getPage(sorgente);
        assertNotNull(wrapPage);
        printWrapPage(wrapPage, true);
    }

    @Test
    @Order(102)
    @DisplayName("102 - QueryService.getPage tramite pageIds (long)")
    void getPageLong() {
        System.out.println(("102 - QueryService.getPage tramite pageIds (long)"));

        sorgenteLong = 8978579;
        wrapPage = queryService.getPage(sorgenteLong);
        assertNotNull(wrapPage);
        printWrapPage(wrapPage, true);
    }


}