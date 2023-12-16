package it.algos.wiki24.query;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.query.*;
import static it.algos.wiki24.backend.query.QueryAssert.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: mar, 17-mag-2022
 * Time: 17:03
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("query")
@DisplayName("Test QueryAssert")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryAssertTest extends QueryTest {


    /**
     * Classe principale di riferimento <br>
     */
    private QueryAssert istanza;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = QueryAssert.class;
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
    @Order(11)
    @DisplayName("11 - noBotLogin")
    void noBotLogin() {
        System.out.println(("11 - noBotLogin"));
        assertNotNull(botLogin);
        botLogin = null;
        assertNull(botLogin);

        istanza = (QueryAssert) appContext.getBean(clazz);
        istanza.botLogin = null;
        ottenutoRisultato = istanza.urlRequest();
        assertFalse(ottenutoRisultato.isValido());
        assertEquals(ERROR_JSON_BOT_NO_LOGIN, ottenutoRisultato.getErrorCode());
        System.out.println(VUOTA);
        message = String.format("Nell'istanza di [%s]%s%s %s", clazzName, FORWARD, "(code) ", ottenutoRisultato.getErrorCode());
        System.out.println(message);
        message = String.format("Nell'istanza di [%s]%s%s %s", clazzName, FORWARD, "(message)", ottenutoRisultato.getErrorMessage());
        System.out.println(message);
    }


    @Test
    @Order(12)
    @DisplayName("12 - notQueryLogin")
    void notQueryLogin() {
        System.out.println(("12 - notQueryLogin"));
        assertNotNull(botLogin);
        botLogin.reset();
        assertNotNull(botLogin);

        istanza = (QueryAssert) appContext.getBean(clazz);
        ottenutoRisultato = istanza.urlRequest();
        assertFalse(ottenutoRisultato.isValido());
        assertEquals(ERROR_JSON_BOT_NO_QUERY, ottenutoRisultato.getErrorCode());
        System.out.println(VUOTA);
        message = String.format("Nell'istanza di [%s]%s%s %s", clazzName, FORWARD, "(code) ", ottenutoRisultato.getErrorCode());
        System.out.println(message);
        message = String.format("Nell'istanza di [%s]%s%s %s", clazzName, FORWARD, "(message)", ottenutoRisultato.getErrorMessage());
        System.out.println(message);
    }


    @Test
    @Order(13)
    @DisplayName("13 - noCookies")
    void noCookies() {
        System.out.println(("13 - noCookies"));
        assertNotNull(botLogin);

        appContext.getBean(QueryLogin.class).urlRequestHamed();
        previstoRisultato = botLogin.getResult();
        previstoRisultato.setCookies(null);
        botLogin.setResult(previstoRisultato);

        istanza = (QueryAssert) appContext.getBean(clazz);
        ottenutoRisultato = istanza.urlRequest();
        assertEquals(ERROR_JSON_BOT_NO_COOKIES, ottenutoRisultato.getErrorCode());
        System.out.println(VUOTA);
        message = String.format("Nell'istanza di [%s]%s%s %s", clazzName, FORWARD, "(code) ", ottenutoRisultato.getErrorCode());
        System.out.println(message);
        message = String.format("Nell'istanza di [%s]%s%s %s", clazzName, FORWARD, "(message)", ottenutoRisultato.getErrorMessage());
        System.out.println(message);
    }


    @Test
    @Order(14)
    @DisplayName("14 - assertBotFailed")
    void assertBotFailed() {
        System.out.println(("14 - assertBotFailed"));
        assertNotNull(botLogin);

        appContext.getBean(QueryLogin.class).urlRequestHamed();

        istanza = (QueryAssert) appContext.getBean(clazz);
        ottenutoRisultato = istanza.urlRequest();
        assertFalse(ottenutoRisultato.isValido());
        assertEquals(ERROR_JSON_BOT_NO_RIGHT, ottenutoRisultato.getErrorCode());
        System.out.println(VUOTA);
        message = String.format("Nell'istanza di [%s]%s%s %s", clazzName, FORWARD, "(code) ", ottenutoRisultato.getErrorCode());
        System.out.println(message);
        message = String.format("Nell'istanza di [%s]%s%s %s", clazzName, FORWARD, "(message)", ottenutoRisultato.getErrorMessage());
        System.out.println(message);
    }


    @Test
    @Order(15)
    @DisplayName("15 - assertBotValid")
    void assertBotValid() {
        System.out.println(("15 - assertBotValid"));
        assertNotNull(botLogin);

        appContext.getBean(QueryLogin.class).urlRequestBot();
        printBotLogin();

        istanza = (QueryAssert) appContext.getBean(clazz);
        ottenutoRisultato = istanza.urlRequest();
        assertTrue(ottenutoRisultato.isValido());
        assertEquals(ERROR_JSON_BOT_NO_RIGHT, ottenutoRisultato.getErrorCode());
        System.out.println(VUOTA);
        message = String.format("Nell'istanza di [%s]%s%s %s", clazzName, FORWARD, "(code) ", ottenutoRisultato.getCodeMessage());
        System.out.println(message);
        message = String.format("Nell'istanza di [%s]%s%s %s", clazzName, FORWARD, "(message)", ottenutoRisultato.getValidMessage());
        System.out.println(message);
    }

    //    @Test
    @Order(3)
    @DisplayName("3 - Collegato come bot")
    void collegatoBot() {
        appContext.getBean(QueryLogin.class).urlRequest();
        assertNotNull(botLogin);
        assertTrue(botLogin.isValido());
        assertEquals(botLogin.getUserType(), TypeUser.bot);

        ottenutoBooleano = appContext.getBean(QueryAssert.class).isEsiste();
        assertTrue(ottenutoBooleano);
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