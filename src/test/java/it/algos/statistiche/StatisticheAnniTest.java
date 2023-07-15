package it.algos.statistiche;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.statistiche.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Mon, 01-Aug-2022
 * Time: 13:34
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("statistiche")
@DisplayName("Statistiche Anni")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StatisticheAnniTest extends WikiTest {


    /**
     * Classe principale di riferimento <br>
     */
    private StatisticheAnni istanza;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
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
        System.out.println(("1 - Costruttore base senza parametri"));
        System.out.println(VUOTA);

        istanza = new StatisticheAnni();
        assertNotNull(istanza);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));
    }


    @Test
    @Order(2)
    @DisplayName("2 - Istanza costruita con appContext.getBean (non fa nulla)")
    void costruttoreBean() {
        System.out.println(("2 - Istanza costruita con appContext.getBean (non fa nulla)"));
        System.out.println(VUOTA);

        istanza = appContext.getBean(StatisticheAnni.class);
        assertNotNull(istanza);
        System.out.println(String.format("Istanza costruita con appContext.getBean(%s.class)", istanza.getClass().getSimpleName()));
        System.out.println(String.format("Non fa nulla, occorre (obbligatorio) invocare il metodo esegue()"));
        System.out.println(String.format("Le classi [Statistica] (ed alte) implementano il Design Pattern 'Builder'"));
        System.out.println(String.format("Per permettere la costruzione 'modulare' dell'istanza con variabili come [test] e altre"));
    }

    @Test
    @Order(3)
    @DisplayName("3 - Upload con metodo .test() ed .esegue()")
    void esegueConTest() {
        System.out.println(("3 - Upload con metodo .test() ed .esegue()"));
        System.out.println(VUOTA);

        ottenutoRisultato = appContext.getBean(StatisticheAnni.class).test().esegue();
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
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