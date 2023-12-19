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
 * Time: 20:03
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("query")
@DisplayName("Test QueryPage")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QueryBioTest extends QueryTest {

    /**
     * Classe principale di riferimento <br>
     */
    private QueryBio istanza;

    private WrapBio wrapBio;
    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = QueryBio.class;
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
    @MethodSource(value = "PAGINE_BIO")
    @Order(101)
    @DisplayName("101 - Recupera wrapBioTitle")
        //--titolo
        //--pagina esistente
    void wrapBioTitle(final String wikiTitleVoce, final boolean paginaEsistente) {
        System.out.println(("101 - Recupera wrapBioTitle"));

        sorgente = wikiTitleVoce;
        wrapBio = appContext.getBean(QueryBio.class).getPage(sorgente);
        assertNotNull(wrapBio);
        if (paginaEsistente) {
            if (wrapBio.isValida()) {
                System.out.println(String.format("Trovata la pagina/categoria [[%s]] su wikipedia", sorgente));
            }
            else {
                System.out.println(String.format("Errore"));
            }
        }
        else {
            if (wrapBio.isValida()) {
                System.out.println(String.format("Bho ?"));
            }
            else {
                System.out.println(String.format("La pagina/categoria [[%s]] non esiste su wikipedia", sorgente));
            }
        }
        printWrapBio(wrapBio);
    }

}
