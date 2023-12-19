package it.algos.wiki24.query;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.login.*;
import it.algos.wiki24.backend.query.*;
import it.algos.wiki24.basetest.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.core.env.*;

import javax.inject.*;


/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: mer, 11-mag-2022
 * Time: 06:52
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("query")
@DisplayName("Test QueryLogin")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QueryLoginTest extends QueryTest {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata dal framework SpringBoot/Vaadin usando il metodo setter() <br>
     * al termine del ciclo init() del costruttore di questa classe <br>
     */
    @Inject
    public Environment environment;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Inject
    public BotLogin botLogin;

    /**
     * Classe principale di riferimento <br>
     */
    private QueryLogin istanza;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = QueryLogin.class;
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
    @Order(1)
    @DisplayName("1 - Costruttore base senza parametri")
    void costruttoreBase() {
        istanza = (QueryLogin) appContext.getBean(clazz);
        assertNotNull(istanza);
        System.out.println(("1 - Costruttore base senza parametri"));
        System.out.println(VUOTA);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
        printIstanza(istanza);
    }


    @Test
    @Order(11)
    @DisplayName("11 - istanza->urlRequestHamed")
    void istanzaUrlRequestHamed() {
        printBotLogin();

        istanza = (QueryLogin) appContext.getBean(clazz);
        assertNotNull(istanza);
        ottenutoRisultato = istanza.urlRequestHamed();
        assertNotNull(ottenutoRisultato);
        printIstanza(istanza);
        printBotLogin();
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(12)
    @DisplayName("12 - istanza->urlRequestGac")
    void istanzaUrlRequestGac() {
        printBotLogin();

        istanza = (QueryLogin) appContext.getBean(clazz);
        assertNotNull(istanza);
        ottenutoRisultato = istanza.urlRequestGac();
        assertNotNull(ottenutoRisultato);
        printIstanza(istanza);
        printBotLogin();
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(13)
    @DisplayName("13 - istanza->urlRequestBot")
    void istanzaUrlRequestBot() {
        printBotLogin();

        istanza = (QueryLogin) appContext.getBean(clazz);
        assertNotNull(istanza);
        ottenutoRisultato = istanza.urlRequestBot();
        assertNotNull(ottenutoRisultato);
        printIstanza(istanza);
        printBotLogin();
        printRisultato(ottenutoRisultato);
    }


    @Test
    @Order(21)
    @DisplayName("21 - urlRequestHamed")
    void urlRequestHamed() {
        printBotLogin();

        ottenutoRisultato = appContext.getBean(QueryLogin.class).urlRequestHamed();
        assertTrue(ottenutoRisultato.isValido());
        printBotLogin();
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(22)
    @DisplayName("22 - urlRequestGac")
    void urlRequestGac() {
        printBotLogin();

        ottenutoRisultato = appContext.getBean(QueryLogin.class).urlRequestGac();
        assertTrue(ottenutoRisultato.isValido());
        printBotLogin();
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(23)
    @DisplayName("23 - urlRequestBot")
    void urlRequestBot() {
        printBotLogin();

        ottenutoRisultato = appContext.getBean(QueryLogin.class).urlRequestBot();
        assertTrue(ottenutoRisultato.isValido());
        printBotLogin();
        printRisultato(ottenutoRisultato);
    }

    @Test
    @Order(31)
    @DisplayName("31 - urlRequest")
    void urlRequest() {
        ottenutoRisultato = appContext.getBean(QueryLogin.class).urlRequest();
        assertFalse(ottenutoRisultato.isValido());
        printBotLogin();
        printRisultato(ottenutoRisultato);
    }


    protected void printIstanza(Object istanza) {
        System.out.println(VUOTA);
        if (istanza instanceof QueryLogin queryLogin) {
            System.out.println(String.format("Valori attuali di una istanza [%s]", "queryLogin"));

            System.out.println(String.format("isValido: %s", botLogin.isValido()));
            System.out.println(String.format("isBot: %s", botLogin.isBot()));
            System.out.println(String.format("lgusername: %s", botLogin.getUsername()));
            System.out.println(String.format("lguserid: %s", botLogin.getUserid()));
            System.out.println(String.format("typeUser: %s", botLogin.getUserType()));
        }

    }


}