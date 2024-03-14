package it.algos.wiki24.query;

import it.algos.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
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
//@Tag("query")
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
        super.ammessoCostruttoreVuoto = true;
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
    @Order(31)
    @DisplayName("31 - urlRequestGac")
    void urlRequestGac() {
        System.out.println("Valori del BotLogin PRIMA di iniziare il test");
        printBotLogin();
        System.out.println(VUOTA);
        System.out.println("Inizio dei test");
        System.out.println(VUOTA);

        ottenutoRisultato = appContext.getBean(QueryLogin.class).urlRequestGac();
        assertNotNull(ottenutoRisultato);
        printBotLogin();
        printRisultato(ottenutoRisultato);
    }


    @Test
    @Order(32)
    @DisplayName("32 - urlRequestBot")
    void urlRequestBot() {
        System.out.println("Valori del BotLogin PRIMA di iniziare il test");
        printBotLogin();
        System.out.println(VUOTA);
        System.out.println("Inizio dei test");
        System.out.println(VUOTA);

        ottenutoRisultato = appContext.getBean(QueryLogin.class).urlRequestBot();
        assertNotNull(ottenutoRisultato);
        printBotLogin();
        printRisultato(ottenutoRisultato);
    }


    @Test
    @Order(33)
    @DisplayName("33 - urlRequestHamed")
    void urlRequestHamed() {
        System.out.println("Valori del BotLogin PRIMA di iniziare il test");
        printBotLogin();
        System.out.println(VUOTA);
        System.out.println("Inizio dei test");
        System.out.println(VUOTA);

        ottenutoRisultato = appContext.getBean(QueryLogin.class).urlRequestHamed();
        assertNotNull(ottenutoRisultato);
        printBotLogin();
        printRisultato(ottenutoRisultato);
    }


    @Test
    @Order(41)
    @DisplayName("41 - urlRequest")
    void urlRequest() {
        System.out.println("Una request generica senza parametri NON è ammessa.");
        System.out.println("Occorre specificare -> urlRequestHamed ; -> urlRequestGac ; -> urlRequestBot");
        System.out.println("Fallendo la query, il BotLogin rimane inalterato ai valori precedenti.");
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