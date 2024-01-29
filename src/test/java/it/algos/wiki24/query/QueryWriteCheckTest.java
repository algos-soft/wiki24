package it.algos.wiki24.query;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.wiki24.backend.query.*;
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
 * Date: Mon, 29-Jan-2024
 * Time: 09:11
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("query")
@DisplayName("Test QueryWriteCheck")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryWriteCheckTest extends QueryTest {

    /**
     * Classe principale di riferimento <br>
     */
    private QueryWriteCheck istanza;

    //--titolo pagina
    //--nuovo testo
    //--testo significativo
    //--result valido
    //--result modificata
    protected static Stream<Arguments> QUERY_TEST() {
        return Stream.of(
                Arguments.of(VUOTA, "Testo qualsiasi", "Non arriva qui", false, false),
                Arguments.of("Utente:Biobot/2", VUOTA, "Non arriva qui", false, false),
                Arguments.of("Utente:Biobot/2", "Testo qualsiasi", VUOTA, true, true),
                Arguments.of("Utente:Biobot/2", "Testo 23 significativo", "testo significativo", true, true),
                Arguments.of("Utente:Biobot/2", "Testo 14 significativo", "testo significativo", true, true),
                Arguments.of("Utente:Biobot/2", "Testo 14 testo significativo", "testo significativo", true, true),
                Arguments.of("Utente:Biobot/2", "Testo 23 testo significativo", "testo significativo", true, false)
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = QueryWriteCheck.class;
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
    void request(String nomePagina, String newText, String checkText, boolean valido, boolean modificata) {
        System.out.println(("4 - Request"));

        sorgente = nomePagina;
        sorgente2 = newText;
        sorgente3 = checkText;
        ottenutoRisultato = appContext.getBean(QueryWriteCheck.class).urlRequestCheck(sorgente, sorgente2, sorgente3);
        assertNotNull(ottenutoRisultato);
        assertEquals(valido, ottenutoRisultato.isValido());
        assertEquals(modificata, ottenutoRisultato.isModificata());
        printRisultato(ottenutoRisultato);
    }

}
