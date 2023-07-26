package it.algos.uploadmoduloprogetto;

import it.algos.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.upload.moduloProgettoAncheBot.*;
import it.algos.wiki24.backend.upload.moduloProgettoSoloAdmin.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.*;

import java.text.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 02-Jul-2023
 * Time: 07:05
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("uploadnomi")
@DisplayName("UploadModuloIncipitNomi")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadModuloNomiIncipitTest extends AlgosTest {


    /**
     * Classe principale di riferimento <br>
     */
    private UploadModuloNomiIncipit istanza;


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

        istanza = new UploadModuloNomiIncipit();
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

        istanza = appContext.getBean(UploadModuloNomiIncipit.class);

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

//        ottenutoRisultato = appContext.getBean(UploadModuloNomiIncipit.class).test().esegue();
        assertNotNull(ottenutoRisultato);
        assertTrue(ottenutoRisultato.isValido());
        printRisultato(ottenutoRisultato);
    }


    @Test
    @Order(91)
    @DisplayName("91 - ordinamento")
    void ordinamento() {
        System.out.println(("91 - ordinamento"));
        System.out.println(VUOTA);

        String input = "openai";

        // Convert the string to an array of characters
        char[] charArray = input.toCharArray();

        // Sort the array of characters
        Arrays.sort(charArray);

        // Convert the sorted array back to a string
        String sortedString = new String(charArray);

        System.out.println("Original string: " + input);
        System.out.println("Sorted string: " + sortedString);
    }


    @Test
    @Order(92)
    @DisplayName("92 - ordinamento")
    void ordinamento2() {
        System.out.println(("92 - ordinamento"));
        System.out.println(VUOTA);

        List<String> stringList = new ArrayList<>();
        stringList.add("banana");
        stringList.add("apple");
        stringList.add("orange");
        stringList.add("Grape");

        System.out.println("Original list: " + stringList);

        // Sort the list of strings
        Collections.sort(stringList);
        System.out.println("Sorted list sensitiveOrder: " + stringList);

        Collections.sort(stringList, String.CASE_INSENSITIVE_ORDER);
        System.out.println(VUOTA);
        System.out.println("Sorted list insensitiveOrder: " + stringList);
    }


    @Test
    @Order(93)
    @DisplayName("93 - ordinamento")
    void ordinamento3() {
        System.out.println(("93 - ordinamento"));
        System.out.println(VUOTA);

        List<String> stringList = new ArrayList<>();
        stringList.add("Giovanni");
        stringList.add("Sabina");
        stringList.add("Édouard");
        stringList.add("Ángel");

        System.out.println("Original list: " + stringList);

        // Create a Collator with the desired locale
        Collator collator = Collator.getInstance(Locale.getDefault());

        // Sort the list using the Collator
        Collections.sort(stringList, collator);

        System.out.println("Sorted list: " + stringList);
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