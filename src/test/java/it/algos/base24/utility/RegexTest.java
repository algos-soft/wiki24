package it.algos.base24.utility;

import it.algos.base24.basetest.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.*;

import java.nio.charset.*;
import java.util.stream.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: sab, 28-ott-2023
 * Time: 06:51
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("quickly")
@DisplayName("Utility")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegexTest extends AlgosTest {

    private String[] parti;

    //--sorgente
    //--positivo
    //--numero
    protected Stream<Arguments> splitType() {
        return Stream.of(
                Arguments.of("\n| {{AFG}} || [[Kabul]] \n|1 791\n|1 023\n|4 273 000\n|4 176,93\n| Mohammad Daud Sultanzoy\n|[[UTC+4:30]]\n", true, 7),
                Arguments.of("\n| {{AFG}} || [[Kabul]] \n|1 791\n|1 023\n|4 273 000\n|4 176,93\n| Mohammad Daud Sultanzoy\n|[[UTC+4:30]]", true, 7),
                Arguments.of("\n| {{AFG}} || [[Kabul]] \n|1 791\n|1 023\n|4 273 000\n|4 176,93\n| Mohammad Daud Sultanzoy\n|[[UTC+4:30]]\n| \n", true, 8),
                Arguments.of("\n| {{AFG}} || [[Kabul]] \n|791\n|[[UTC+4:30]]", true, 3),
                Arguments.of("\\n| {{AFG}} || [[Kabul]] \\n|791\\n|[[UTC+4:30]]", false, 0),
                Arguments.of("\n| {{AFG}} || [[Kabul]] \n|791\n|[[UTC+4:30]]", true, 3)
        );
    }

    //--sorgente
    //--split
    protected Stream<Arguments> splitVario() {
        return Stream.of(
                Arguments.of("{{ATG}}{{,}}{{BHS}}{{,}}{{BRB}}{{,}}{{BLZ}}{{,}}{{CAN}}{{,}}{{CRI}}{{,}}{{CUB}}", "\\{\\{,\\}\\}"),
                Arguments.of("{{ATG}}{{,}}{{BHS}}{{,}}{{BRB}}{{,}}{{BLZ}}{{,}}{{CAN}}{{,}}{{CRI}}{{,}}{{CUB}}", "\\{\\{\\,\\}\\}"),
                Arguments.of("| <kbd>AM-ER</kbd>\n| [[Erevan]]\n| città", CAPO),
                Arguments.of("| <kbd>AM-ER</kbd>\n| [[Erevan]]\n| città", CAPO_REGEX)

        );
    }

    //--sorgente
    protected Stream<Arguments> findRegex() {
        return Stream.of(
                Arguments.of("{|class=\"wikitable"),
                Arguments.of("{|class= \"wikitable"),
                Arguments.of("{| class=\"wikitable"),
                Arguments.of("{| class= \"wikitable"),
                Arguments.of("{ |class=\"wikitable"),
                Arguments.of("{ |class= \"wikitable"),
                Arguments.of("{ | class=\"wikitable"),
                Arguments.of("{ | class= \"wikitable"),
                Arguments.of("{|class =\"wikitable"),
                Arguments.of("{|class = \"wikitable"),
                Arguments.of("{| class =\"wikitable"),
                Arguments.of("{| class = \"wikitable"),
                Arguments.of("{ |class =\"wikitable"),
                Arguments.of("{ |class = \"wikitable"),
                Arguments.of("{ | class =\"wikitable"),
                Arguments.of("{ | class = \"wikitable")
        );
    }


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
    }


    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        parti = null;
    }

    @Test
    @Order(1)
    @DisplayName("1 - split \n")
    void split() {
        System.out.println(("1 - split di un testo con \n"));
        System.out.println(VUOTA);

        //--sorgente
        //--positivo
        //--numero
        splitType().forEach(this::fixSplit);
    }


    //--sorgente
    //--positivo
    //--numero
    void fixSplit(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoBooleano = (boolean) mat[1];
        previstoIntero = (int) mat[2];

        String rawString = CAPO_REGEX;
        byte[] bytes8 = rawString.getBytes(StandardCharsets.UTF_8);
        String utf8EncodedString8 = new String(bytes8, StandardCharsets.UTF_8);

        byte[] bytesSorgente = sorgente.getBytes(StandardCharsets.UTF_8);
        String utf8EncodedStringSorgente = new String(bytesSorgente, StandardCharsets.UTF_16);

        String utf8EncodedString16 = new String(bytes8, StandardCharsets.UTF_16);
        parti = sorgente.split(utf8EncodedString16);
        parti = utf8EncodedStringSorgente.split(utf8EncodedString16);

        String alfa = new String(CAPO.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_16);
        parti = sorgente.split(alfa);
        parti = utf8EncodedStringSorgente.split(alfa);

        String beta = new String(CAPO_REGEX.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_16);
        parti = sorgente.split(beta);

        byte[] bytes16 = rawString.getBytes(StandardCharsets.UTF_16);
        String utf8EncodedString1616 = new String(bytes16, StandardCharsets.UTF_16);

        sorgente2 = "\n";
        parti = sorgente.split(sorgente2);
        assertNotNull(parti);
        System.out.println(String.format("%s%d", FORWARD, parti.length));
        System.out.println(VUOTA);

        sorgente2 = CAPO;
        parti = sorgente.split(sorgente2);
        assertNotNull(parti);
        System.out.println(String.format("%s%d", FORWARD, parti.length));
        System.out.println(VUOTA);

        sorgente2 = CAPO_REGEX;
        parti = sorgente.split(sorgente2);
        assertNotNull(parti);
        System.out.println(String.format("%s%d", FORWARD, parti.length));
        System.out.println(VUOTA);

        System.out.println(String.format("%s%s(%s) %s%d", sorgente, FORWARD, sorgente2, FORWARD, parti.length));
    }


    @Test
    @Order(2)
    @DisplayName("2 - splitVari")
    void splitVari() {
        System.out.println(("2 - splitVari"));
        System.out.println(VUOTA);

        //--sorgente
        //--split
        splitVario().forEach(this::fixSplitVari);
    }

    //--sorgente
    //--split
    void fixSplitVari(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        sorgente2 = (String) mat[1];

        parti = sorgente.split(sorgente2);
        assertNotNull(parti);
        System.out.println(String.format("%s%s%s", sorgente, FORWARD, sorgente2));
        for (String riga : parti) {
            System.out.println(riga);
        }
        System.out.println(VUOTA);
    }


    @Test
    @Order(3)
    @DisplayName("3 - find")
    void find() {
        System.out.println(("3 - find"));
        System.out.println(VUOTA);

        //--sorgente
        findRegex().forEach(this::fixFindRegex);
    }

    //--sorgente
    void fixFindRegex(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];

        sorgente2 = ".*\\{ *\\| *class * *= *\"wikitable";
        ottenutoBooleano = sorgente.matches(sorgente2);
        System.out.print(ottenutoBooleano);
        System.out.print(SPAZIO);
        System.out.println(sorgente);

        //        sorgente2 = "{|class=\\\"wikitable";
        //        sorgente2 = "{|class";
        //        ottenutoBooleano = sorgente.contains(sorgente2);
        //        message = String.format("%s%s%s", sorgente2, FORWARD, sorgente);
        //        if (!ottenutoBooleano) {
        //            message+=" (non trovato)";
        //        }
        //        System.out.println(message);
        //
        //        assertTrue(ottenutoBooleano);
    }

    //    @Test
    void prova() {
        int alfa;
        int beta;
        int risultato = 0;

        alfa = 55;
        beta = 27;

        for (int k = 0; k < beta; k = k + 2) {
            System.out.println(k);
            if (k == 18) {
                System.out.println("ERRORE");
            }

        }

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