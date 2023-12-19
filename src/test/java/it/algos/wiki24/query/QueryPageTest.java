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
        wrapPage = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - Costruttore base senza parametri")
    void costruttoreBase() {
        istanza = new QueryPage();
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


    @ParameterizedTest
    @MethodSource(value = "PAGINE_E_CATEGORIE")
    @Order(101)
    @DisplayName("101 - Recupera wrapPage")
        //--titolo
        //--pagina esistente
    void wrapPage(final String wikiTitleVoceOppureCategoria, final boolean paginaEsistente) {
        System.out.println(("101 - Recupera wrapPage"));

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
        printWrapPage(wrapPage);
    }


}