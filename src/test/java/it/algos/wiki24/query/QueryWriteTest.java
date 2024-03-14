package it.algos.wiki24.query;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.query.*;
import it.algos.wiki24.backend.upload.*;
import it.algos.wiki24.basetest.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 09-Jan-2024
 * Time: 15:42
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("query")
@DisplayName("Test QueryWrite")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryWriteTest extends QueryTest {


    /**
     * Classe principale di riferimento <br>
     */
    private QueryWrite istanza;

    //--titolo pagina
    //--nuovo testo
    protected static Stream<Arguments> QUERY_TEST() {
        return Stream.of(
                Arguments.of(VUOTA, "Prova delle "),
                Arguments.of("Utente:Biobot/2", VUOTA),
                Arguments.of("Utente:Biobot/2", "Prova delle ")
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = QueryWrite.class;
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

    @ParameterizedTest
    @MethodSource(value = "QUERY_TEST")
    @Order(4)
    @DisplayName("4 - Request")
    void request(String nomePagina, String newText) {
        System.out.println(("4 - Request"));

        sorgente = nomePagina;
        sorgente2 = textService.isValid(newText) ? newText + System.currentTimeMillis() : newText;
        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequest(sorgente, sorgente2);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(5)
    @DisplayName("5 - seconda request identica")
    void nonModificata() {
        System.out.println(("5 - seconda request identica"));

        sorgente = "Utente:Biobot/2";
        sorgente2 = "Testo qualsiasi";

        //prima scrittura
        System.out.println(VUOTA);
        System.out.println("Prima scrittura");
        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequest(sorgente, sorgente2);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        assertTrue(ottenutoRisultato.isModificata());
        printRisultato(ottenutoRisultato);

        //seconda scrittura
        System.out.println(VUOTA);
        System.out.println("Seconda scrittura identica");
        ottenutoRisultato = appContext.getBean(QueryWrite.class).urlRequest(sorgente, sorgente2);
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        assertFalse(ottenutoRisultato.isModificata());
        printRisultato(ottenutoRisultato);
    }


}
