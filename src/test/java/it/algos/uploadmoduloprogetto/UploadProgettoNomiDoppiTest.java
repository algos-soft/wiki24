package it.algos.uploadmoduloprogetto;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.upload.moduloProgettoAncheBot.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 01-Jul-2023
 * Time: 17:12
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("uploadnomi")
@DisplayName("UploadProgettoNomiDoppi")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadProgettoNomiDoppiTest extends AlgosTest {


    /**
     * Classe principale di riferimento <br>
     */
    private UploadProgettoNomiDoppi istanza;


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

        istanza = new UploadProgettoNomiDoppi();
        assertNotNull(istanza);
        System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));

        System.out.println(VUOTA);
        System.out.println("L'istanza è stata costruita SENZA usare SpringBoot.");
        System.out.println("NON passa da @PostConstruct().");
        System.out.println("@Autowired NON funziona.");
        assertNotNull(istanza);
        assertNull(istanza.wikiTitleUpload);
        assertNull(istanza.wikiBackend);
    }

    @Test
    @Order(2)
    @DisplayName("2 - getBean base senza parametri")
    void getBean() {
        System.out.println(("2 - getBean base senza parametri"));
        System.out.println(VUOTA);

        istanza = appContext.getBean(UploadProgettoNomiDoppi.class);

        System.out.println(String.format("getBean base senza parametri per un'istanza di %s", istanza.getClass().getSimpleName()));

        System.out.println(VUOTA);
        System.out.println("L'istanza è stata costruita USANDO SpringBoot.");
        System.out.println("PASSA da @PostConstruct().");
        System.out.println("@Autowired dovrebbe funzionare.");
        assertNotNull(istanza);
        assertNotNull(istanza.wikiTitleUpload);
        assertNotNull(istanza.wikiBackend);
    }


    @Test
    @Order(3)
    @DisplayName("3 - esegue upload di test")
    void esegue() {
        System.out.println(("3 - esegue upload di test"));
        System.out.println(VUOTA);

        ottenutoRisultato = appContext.getBean(UploadProgettoNomiDoppi.class).test().esegue();
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