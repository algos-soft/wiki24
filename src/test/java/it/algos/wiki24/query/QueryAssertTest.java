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
//@Tag("query")
@DisplayName("Test QueryAssert")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryAssertTest extends QueryTest {


    /**
     * Classe principale di riferimento <br>
     */
    private QueryAssert istanza;

    private TypeUser type;

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

        istanza = appContext.getBean(QueryAssert.class);
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
    @DisplayName("12 - botLoginEmpty")
    void botLoginEmpty() {
        System.out.println(("12 - botLoginEmpty"));
        assertNotNull(botLogin);
        botLogin.reset();
        assertFalse(botLogin.isValido());

        ottenutoRisultato = appContext.getBean(QueryAssert.class).urlRequest();
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
        botLogin.setUrlResponse("Solo per questo test");
        botLogin.setCookies(null);

        ottenutoRisultato = appContext.getBean(QueryAssert.class).urlRequest();
        assertFalse(ottenutoRisultato.isValido());
        assertEquals(ERROR_JSON_BOT_NO_COOKIES, ottenutoRisultato.getErrorCode());
        System.out.println(VUOTA);
        message = String.format("Nell'istanza di [%s]%s%s %s", clazzName, FORWARD, "(code) ", ottenutoRisultato.getErrorCode());
        System.out.println(message);
        message = String.format("Nell'istanza di [%s]%s%s %s", clazzName, FORWARD, "(message)", ottenutoRisultato.getErrorMessage());
        System.out.println(message);
    }


    @Test
    @Order(21)
    @DisplayName("21 - assertHamed")
    void assertHamed() {
        System.out.println(("21 - assertHamed"));
        appContext.getBean(QueryLogin.class).urlRequestHamed(); //type User
        assertTrue(botLogin.isValido());

        ottenutoBooleano = appContext.getBean(QueryAssert.class).isAnon();
        assertFalse(ottenutoBooleano);
        ottenutoBooleano = appContext.getBean(QueryAssert.class).isUser();
        assertTrue(ottenutoBooleano);
        ottenutoBooleano = appContext.getBean(QueryAssert.class).isBot();
        assertFalse(ottenutoBooleano);
        type = appContext.getBean(QueryAssert.class).getTypeUser();
        assertEquals(TypeUser.user, type);
        message = String.format("L'utente [%s] è regolarmente collegato come [%s]", "Hamed", type);
        System.out.println((message));
    }


    @Test
    @Order(22)
    @DisplayName("22 - assertGac")
    void assertGac() {
        System.out.println(("22 - assertGac"));
        appContext.getBean(QueryLogin.class).urlRequestGac(); //type User
        assertTrue(botLogin.isValido());

        ottenutoBooleano = appContext.getBean(QueryAssert.class).isAnon();
        assertFalse(ottenutoBooleano);
        ottenutoBooleano = appContext.getBean(QueryAssert.class).isUser();
        assertTrue(ottenutoBooleano);
        ottenutoBooleano = appContext.getBean(QueryAssert.class).isBot();
        assertFalse(ottenutoBooleano);
        type = appContext.getBean(QueryAssert.class).getTypeUser();
        assertEquals(TypeUser.user, type);
        message = String.format("L'utente [%s] è regolarmente collegato come [%s]", "Gac", type);
        System.out.println((message));
    }
    @Test
    @Order(23)
    @DisplayName("23 - assertBiobot")
    void assertBiobot() {
        System.out.println(("23 - assertBiobot"));
        appContext.getBean(QueryLogin.class).urlRequestBot(); //type User
        assertTrue(botLogin.isValido());

        ottenutoBooleano = appContext.getBean(QueryAssert.class).isAnon();
        assertFalse(ottenutoBooleano);
        ottenutoBooleano = appContext.getBean(QueryAssert.class).isUser();
        assertTrue(ottenutoBooleano);
        ottenutoBooleano = appContext.getBean(QueryAssert.class).isBot();
        assertTrue(ottenutoBooleano);
        type = appContext.getBean(QueryAssert.class).getTypeUser();
        assertEquals(TypeUser.bot, type);
        message = String.format("L'utente [%s] è regolarmente collegato come [%s]", "Biobot", type);
        System.out.println((message));
    }

}