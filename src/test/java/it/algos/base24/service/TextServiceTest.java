package it.algos.base24.service;

import it.algos.*;
import it.algos.base24.basetest.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.service.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sat, 21-Oct-2023
 * Time: 20:31
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Text Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TextServiceTest extends ServiceTest {

    @Autowired
    private TextService service;


    //--sorgente
    //--previsto
    private Stream<Arguments> maiuscola() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA),
                Arguments.of("MARIO", "MARIO"),
                Arguments.of("mario", "Mario"),
                Arguments.of("maRio", "MaRio"),
                Arguments.of(" mario", "Mario"),
                Arguments.of("mario ", "Mario"),
                Arguments.of(" mario ", "Mario")
        );
    }

    //--sorgente
    //--previsto
    private Stream<Arguments> minuscola() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA),
                Arguments.of("mario", "mario"),
                Arguments.of("Mario", "mario"),
                Arguments.of("MaRio", "maRio"),
                Arguments.of(" MARIO", "mARIO"),
                Arguments.of("Mario ", "mario"),
                Arguments.of(" Mario ", "mario")
        );
    }


    //--sorgente
    //--tagIniziale
    //--risultato
    private Stream<Arguments> testa() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, VUOTA, VUOTA, VUOTA),
                Arguments.of(" Levare questa fine Non ", "Levare", "questa fine Non"),
                Arguments.of("Virgole , prima , seconda , terza", ",", "prima , seconda , terza"),
                Arguments.of("{\"parse\":{\"title\":\"Piozzano\",\"pageid\":2741616,\"wikitext\":\"{{Divisione amministrativa\\n|Nome=Piozzano", "wikitext\":\"", "{{Divisione amministrativa\\n|Nome=Piozzano")
        );
    }

    //--sorgente
    //--tagIniziale
    //--risultato leva tag
    //--risultato lascia tag
    private Stream<Arguments> prima() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, VUOTA, VUOTA, VUOTA),
                Arguments.of("Mario<ref>Pippo</ref>", "<ref>", "Pippo</ref>", "<ref>Pippo</ref>"),
                Arguments.of("Mario Rossi|Rossi", "|", "Rossi", "|Rossi"),
                Arguments.of("Elba (isola)|Elba", "|", "Elba", "|Elba")
        );
    }


    //--sorgente
    //--tagFinale
    //--coda
    //--prima
    //--ultima
    private Stream<Arguments> coda() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, VUOTA, VUOTA, VUOTA),
                Arguments.of(" Levare questa fine Non ", "Non", " Levare questa fine", " Levare questa fine", " Levare questa fine"),
                Arguments.of("Non Levare questa fine ", "NonEsisteQuestoTag", "Non Levare questa fine ", "Non Levare questa fine ", "Non Levare questa fine "),
                Arguments.of(" Levare questa fine Non ", "N", " Levare questa fine Non ", " Levare questa fine", " Levare questa fine"),
                Arguments.of("Questa non levare ", VUOTA, "Questa non levare ", "Questa non levare ", "Questa non levare "),
                Arguments.of("Anche questa non levare ", SPAZIO, "Anche questa non levare ", "Anche questa non levare ", "Anche questa non levare "),
                Arguments.of("Non Levare questa fine ", "questa", "Non Levare questa fine ", "Non Levare", "Non Levare"),
                Arguments.of("Tag uno multipli uno finale", "uno", "Tag uno multipli uno finale", "Tag", "Tag uno multipli"),
                Arguments.of("Virgole , prima , seconda , terza ,", ",", "Virgole , prima , seconda , terza", "Virgole", "Virgole , prima , seconda , terza"),
                Arguments.of("Virgole , prima , seconda , terza", ",", "Virgole , prima , seconda , terza", "Virgole", "Virgole , prima , seconda")
        );
    }

    //--sorgente
    //--tagIniziale
    //--tagFinale
    //--testo estratto con
    //--testo estratto senza
    private Stream<Arguments> estrae() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, VUOTA, VUOTA, VUOTA),
                Arguments.of(" Levare questa frase fino alla fine ", "frase", "fine", "frase fino alla fine", "fino alla"),
                Arguments.of("Virgole , prima , seconda , terza", "prima", "terza", "prima , seconda , terza", ", seconda ,"),
                Arguments.of("{{Divisione amministrativa\\n|Nome=Piozzano", "ni", "ti", "nistrati", "stra")
        );
    }

    //--testo sorgente
    //--oldTag da sostituire
    //--newTag da inserire
    //--testo previsto
    private Stream<Arguments> sostituzioni() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, VUOTA, VUOTA),
                Arguments.of("{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}", "Province", "Regioni", "{{Simbolo|Italian Regioni (Crown).svg|24}} {{IT-SU}}"),
                Arguments.of("{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}", VUOTA, VUOTA, "{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}"),
                Arguments.of("{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}", "Province", VUOTA, "{{Simbolo|Italian (Crown).svg|24}} {{IT-SU}}"),
                Arguments.of("{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}", VUOTA, "Regioni", "{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}"),
                Arguments.of("Frase con due ripetizioni per due volte.", "due", "tre", "Frase con tre ripetizioni per tre volte."),
                Arguments.of("Frase con dueripetizioni per duevolte.", "due", "tre", "Frase con treripetizioni per trevolte.")
        );
    }


    //--testo sorgente
    //--tag da eliminare
    //--testo previsto
    private Stream<Arguments> levaTestoStream() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, VUOTA),
                Arguments.of("{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}", "Province", "{{Simbolo|Italian (Crown).svg|24}} {{IT-SU}}"),
                Arguments.of("{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}", VUOTA, "{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}"),
                Arguments.of("{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}", "Regioni", "{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}"),
                Arguments.of("Frase con due ripetizioni per due volte.", "due", "Frase con ripetizioni per volte."),
                Arguments.of("Frase con dueripetizioni per duevolte.", "due", "Frase con ripetizioni per volte.")
        );
    }

    //--testo sorgente
    //--testo previsto
    private Stream<Arguments> levaVirgoleStream() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA),
                Arguments.of("{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}", "{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}"),
                Arguments.of("Frase, con,parecchie, ,virgole,.", "Frase conparecchie virgole."),
                Arguments.of("Frase,,  parecchie ,,, virgole.", "Frase  parecchie virgole.")
        );
    }


    //--testo sorgente
    //--testo previsto
    private Stream<Arguments> levaPuntiStream() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA),
                Arguments.of("{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}", "{{Simbolo|Italian Province (Crown)svg|24}} {{IT-SU}}"),
                Arguments.of("Frase, con.alcuni, ,punti,.", "Frase, conalcuni, ,punti,"),
                Arguments.of("Frase..  con parecchi ... punti.", "Frase  con parecchi punti")
        );
    }

    //--testo sorgente
    //--testo previsto
    private Stream<Arguments> levaSpaziStream() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA),
                Arguments.of("{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}", "{{Simbolo|ItalianProvince(Crown).svg|24}}{{IT-SU}}"),
                Arguments.of("Frase, con.alcuni, ,punti,.", "Frase,con.alcuni,,punti,."),
                Arguments.of(" Frase..  con parecchi ... punti. ", "Frase..conparecchi...punti.")
        );
    }

    //--testo sorgente
    //--testo previsto
    private Stream<Arguments> slashToPointStream() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA),
                Arguments.of("/Users/gac/Documents/IdeaProjects/operativi/wiki24/target", "/Users.gac.Documents.IdeaProjects.operativi.wiki24.target"),
                Arguments.of("/Users/gac/Documents/IdeaProjects/operativi/wiki24/target/", "/Users.gac.Documents.IdeaProjects.operativi.wiki24.target"),
                Arguments.of("IdeaProjects/operativi/wiki24/target", "IdeaProjects.operativi.wiki24.target"),
                Arguments.of("IdeaProjects/operativi/wiki24/target/", "IdeaProjects.operativi.wiki24.target"),
                Arguments.of(" IdeaProjects/operativi/wiki24/target ", "IdeaProjects.operativi.wiki24.target")
        );
    }

    //--testo sorgente
    //--testo previsto
    private Stream<Arguments> pointToSlashStream() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA),
                Arguments.of("/Users.gac.Documents.IdeaProjects.operativi.wiki24.target", "/Users/gac/Documents/IdeaProjects/operativi/wiki24/target"),
                Arguments.of("/Users.gac.Documents.IdeaProjects.operativi.wiki24.target.", "/Users/gac/Documents/IdeaProjects/operativi/wiki24/target/"),
                Arguments.of("operativi.wiki24.target", "operativi/wiki24/target"),
                Arguments.of(" operativi.wiki24.target ", "operativi/wiki24/target"),
                Arguments.of(".operativi.wiki24.target", "/operativi/wiki24/target"),
                Arguments.of(".operativi.wiki24.target.", "/operativi/wiki24/target/")
        );
    }

    //--object sorgente
    //--testo previsto
    //--length
    protected Stream<Arguments> pad() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, 0),
                Arguments.of(VUOTA, "   ", 3),
                Arguments.of("alfa", "alfa", 0),
                Arguments.of("alfa", "alfa", 2),
                Arguments.of("alfa", "alfa", 4),
                Arguments.of("alfa  ", "alfa  ", 6),
                Arguments.of("  alfa", "alfa  ", 6),
                Arguments.of("  alfa  ", "alfa  ", 6),
                Arguments.of("  alfa  ", "alfa", 2),
                Arguments.of("  alfa", "alfa", 4),
                Arguments.of("alfa", "alfa  ", 6)
        );
    }


    //--object sorgente
    //--testo previsto
    //--length
    protected Stream<Arguments> size() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA, 0),
                Arguments.of(VUOTA, "   ", 3),
                Arguments.of("alfa", VUOTA, 0),
                Arguments.of("alfa", "al", 2),
                Arguments.of("alfa", "alfa", 4),
                Arguments.of("alfa  ", "alfa  ", 6),
                Arguments.of("  alfa", "alfa  ", 6),
                Arguments.of("  alfa  ", "alfa  ", 6),
                Arguments.of("  alfa  ", "al", 2),
                Arguments.of("  alfa", "alfa", 4),
                Arguments.of("alfa", "alfa  ", 6),
                Arguments.of("alfetta", "alfett", 6),
                Arguments.of("alfetta", "alfetta ", 8),
                Arguments.of(" alfetta ", "alf", 3)
        );
    }


    //--object sorgente
    //--testo previsto
    //--length
    protected Stream<Arguments> sizeQuadre() {
        return Stream.of(
                Arguments.of(VUOTA, "[]", 0),
                Arguments.of(VUOTA, "[   ]", 3),
                Arguments.of("alfa", "[]", 0),
                Arguments.of("alfa", "[al]", 2),
                Arguments.of("alfa", "[alfa]", 4),
                Arguments.of("alfa  ", "[alfa  ]", 6),
                Arguments.of("  alfa", "[alfa  ]", 6),
                Arguments.of("  alfa  ", "[alfa  ]", 6),
                Arguments.of("  alfa  ", "[al]", 2),
                Arguments.of("  alfa", "[alfa]", 4),
                Arguments.of("alfa", "[alfa  ]", 6),
                Arguments.of("alfetta", "[alfett]", 6),
                Arguments.of("alfetta", "[alfetta ]", 8),
                Arguments.of(" alfetta ", "[alf]", 3)
        );
    }

    //--sorgente
    //--previsto
    protected Stream<Arguments> doppieQuadre() {
        return Stream.of(
                Arguments.of("alfa", "alfa"),
                Arguments.of(" alfa ", "alfa"),
                Arguments.of("alfa ", "alfa"),
                Arguments.of(" alfa ", "alfa"),
                Arguments.of("[alfa]", "[alfa]"),
                Arguments.of("[[alfa]]", "alfa"),
                Arguments.of(" [[alfa]]", "alfa"),
                Arguments.of("[[alfa]] ", "alfa"),
                Arguments.of("[[alfa]], [[beta]]", "[[alfa]], [[beta]]")
        );
    }

    protected static final String[] ARRAY_UNO = {"alfa"};

    protected static final String[] ARRAY_DUE = {"alfa", "beta", "gamma", "delta", "epsilon"};

    protected static final String[] ARRAY_TRE = {"alfa beta gamma delta epsilon"};

    //--testo sorgente
    //--array previsto
    private Stream<Arguments> getArrayStream() {
        return Stream.of(
                Arguments.of("alfa", Arrays.asList(ARRAY_UNO)),
                Arguments.of(" alfa ", Arrays.asList(ARRAY_UNO)),
                Arguments.of(" alfa,", Arrays.asList(ARRAY_UNO)),
                Arguments.of("alfa,beta,gamma,delta,epsilon", Arrays.asList(ARRAY_DUE)),
                Arguments.of(" alfa, beta  , gamma,delta,epsilon ", Arrays.asList(ARRAY_DUE)),
                Arguments.of("alfa beta gamma delta epsilon", Arrays.asList(ARRAY_TRE)),
                Arguments.of(" , ", Arrays.asList()),
                Arguments.of(SPAZIO, Arrays.asList()),
                Arguments.of(VUOTA, Arrays.asList())
        );
    }


    //--object sorgente
    //--testo ottenuto
    private Stream<Arguments> formatStream() {
        return Stream.of(
                Arguments.of(VUOTA, NULLO),
                Arguments.of("{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}", "{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}"),
                Arguments.of(4, "4"),
                Arguments.of(827L, "827"),
                Arguments.of(1827, "1.827"),
                //                Arguments.of("1827,35", "1.827,35"),
                //                Arguments.of("1827,350", "1.827,35"),
                //                Arguments.of("1827,3", "1.827,30"),
                //                Arguments.of("0,3", "0,30"),
                //                Arguments.of("0,350", "0,35"),
                //                Arguments.of("0,358", "0,35"),
                //                Arguments.of("1827.35", "1.827,35"),
                //                Arguments.of("1827.350", "1.827,35"),
                //                Arguments.of(1827.35, "1.827,35"),
                //                Arguments.of(1827.350, "1.827,35"),
                Arguments.of(82735, "82.735"),
                Arguments.of("1827", "1.827")
        );
    }


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = TextService.class;
        super.setUpAll();
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @Test
    @Order(10)
    @DisplayName("10 - isEmpty")
    void isEmpty() {
        System.out.println(("10 - isEmpty"));
        System.out.println(VUOTA);

        previstoBooleano = true;
        ottenutoBooleano = service.isEmpty(null);
        assertEquals(previstoBooleano, ottenutoBooleano);
        message = String.format("Il sorgente di valore '%s' è vuoto%sisEmpty=%s", "(null)", FORWARD, ottenutoBooleano);
        System.out.println(message);
        System.out.println(VUOTA);

        previstoBooleano = true;
        ottenutoBooleano = service.isEmpty(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        message = String.format("Il sorgente di valore '%s' è vuoto%sisEmpty=%s", sorgente, FORWARD, ottenutoBooleano);
        System.out.println(message);
        System.out.println(VUOTA);

        sorgente = "x";
        previstoBooleano = false;
        ottenutoBooleano = service.isEmpty(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        message = String.format("Il sorgente di valore '%s' NON è vuoto%sisEmpty=%s", sorgente, FORWARD, ottenutoBooleano);
        System.out.println(message);
        System.out.println(VUOTA);
    }

    @Test
    @Order(11)
    @DisplayName("11 - isValid")
    void isValid() {
        System.out.println(("11 - isValid"));
        System.out.println(VUOTA);

        previstoBooleano = false;
        ottenutoBooleano = service.isValid(null);
        assertEquals(previstoBooleano, ottenutoBooleano);
        message = String.format("Il sorgente di valore '%s' NON è valido%sisValid=%s", "(null)", FORWARD, ottenutoBooleano);
        System.out.println(message);
        System.out.println(VUOTA);

        sorgente = VUOTA;
        previstoBooleano = false;
        ottenutoBooleano = service.isValid(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        message = String.format("Il sorgente di valore '%s' NON è valido%sisValid=%s", sorgente, FORWARD, ottenutoBooleano);
        System.out.println(message);
        System.out.println(VUOTA);

        sorgente = SPAZIO;
        previstoBooleano = true;
        ottenutoBooleano = service.isValid(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        message = String.format("Il sorgente di valore '%s' è valido%sisValid=%s", sorgente, FORWARD, ottenutoBooleano);
        System.out.println(message);
        System.out.println(VUOTA);

        sorgente = "x";
        previstoBooleano = true;
        ottenutoBooleano = service.isValid(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        message = String.format("Il sorgente di valore '%s' è valido%sisValid=%s", sorgente, FORWARD, ottenutoBooleano);
        System.out.println(message);
        System.out.println(VUOTA);
    }


    @Test
    @Order(20)
    @DisplayName("20 - prima maiuscola")
    void primaMaiuscola() {
        System.out.println(("20 - prima maiuscola"));
        System.out.println(VUOTA);
        System.out.println(("Forza il primo carattere (solo il primo) del testo da indifferenziata (maiuscola o minuscola) in maiuscola"));
        System.out.println(VUOTA);
        maiuscola().forEach(this::maiuscola);
    }

    //--sorgente
    //--previsto
    void maiuscola(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previsto = (String) mat[1];

        ottenuto = service.primaMaiuscola(sorgente);
        assertEquals(previsto, ottenuto);
        System.out.println(String.format("%s%s%s", service.isValid(sorgente) ? sorgente : NULLO, FORWARD, service.isValid(ottenuto) ? ottenuto : NULLO));
    }

    @Test
    @Order(21)
    @DisplayName("21 - prima minuscola")
    void primaMinuscola() {
        System.out.println(("20 - prima minuscola"));
        System.out.println(VUOTA);
        System.out.println(("Forza il primo carattere (solo il primo) del testo da indifferenziata (maiuscola o minuscola) in minuscola"));
        System.out.println(VUOTA);
        minuscola().forEach(this::minuscola);
    }

    //--sorgente
    //--previsto
    void minuscola(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previsto = (String) mat[1];

        ottenuto = service.primaMinuscola(sorgente);
        assertEquals(previsto, ottenuto);
        System.out.println(String.format("%s%s%s", service.isValid(sorgente) ? sorgente : NULLO, FORWARD, service.isValid(ottenuto) ? ottenuto : NULLO));
    }


    @Test
    @Order(30)
    @DisplayName("30 - levaTesta")
    void levaTesta() {
        System.out.println(("30 - levaTesta"));
        System.out.println(VUOTA);
        System.out.println(("Elimina dal testo il tagIniziale (compreso), se esiste"));
        System.out.println(VUOTA);
        testa().forEach(this::fixTesta);
    }

    //--sorgente
    //--tagIniziale
    //--risultato
    void fixTesta(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        String tagIniziale = (String) mat[1];
        previsto = (String) mat[2];

        ottenuto = service.levaTesta(sorgente, tagIniziale);
        assertEquals(previsto, ottenuto);
        message = String.format("[%s]%s[%s]%s[%s]", sorgente, FORWARD, tagIniziale, FORWARD, ottenuto);
        System.out.println(message);
    }


    @Test
    @Order(31)
    @DisplayName("31 - levaPrimaAncheTag")
    void levaPrimaAncheTag() {
        System.out.println(("31 - levaPrimaAncheTag"));
        System.out.println(VUOTA);
        System.out.println(("Elimina il testo che precede il tagIniziale e leva anche il tag"));
        System.out.println(VUOTA);
        prima().forEach(this::fixLevaPrimaAncheTag);
    }

    //--sorgente
    //--tagIniziale
    //--risultato leva tag
    //--risultato lascia tag
    void fixLevaPrimaAncheTag(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        String tagIniziale = (String) mat[1];
        previsto = (String) mat[2];
        previsto2 = (String) mat[3];

        ottenuto = service.levaPrimaAncheTag(sorgente, tagIniziale);
        assertEquals(previsto, ottenuto);
        message = String.format("[%s]%s[%s]%s[%s]", sorgente, FORWARD, tagIniziale, FORWARD, ottenuto);
        System.out.println(message);
    }


    @Test
    @Order(32)
    @DisplayName("32 - levaPrimaDelTag")
    void levaPrimaDelTag() {
        System.out.println(("32 - levaPrimaDelTag"));
        System.out.println(VUOTA);
        System.out.println(("Elimina il testo che precede il tagIniziale lasciando il tag"));
        System.out.println(VUOTA);
        prima().forEach(this::fixLevaPrimaDelTag);
    }

    //--sorgente
    //--tagIniziale
    //--risultato leva tag
    //--risultato lascia tag
    void fixLevaPrimaDelTag(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        String tagIniziale = (String) mat[1];
        previsto = (String) mat[2];
        previsto2 = (String) mat[3];

        ottenuto = service.levaPrimaDelTag(sorgente, tagIniziale);
        assertEquals(previsto2, ottenuto);
        message = String.format("[%s]%s[%s]%s[%s]", sorgente, FORWARD, tagIniziale, FORWARD, ottenuto);
        System.out.println(message);
    }


    @Test
    @Order(40)
    @DisplayName("40 - levaCoda")
    void levaCoda() {
        System.out.println(("40 - levaCoda"));
        System.out.println(VUOTA);
        System.out.println(("Elimina dal testo il tagFinale, se esiste"));
        System.out.println(("Elimina il testo dalla prima occorrenza di tagInterrompi (compreso) in poi"));
        System.out.println(("Elimina il testo dall'ultima occorrenza di tagInterrompi (compreso) in poi"));
        System.out.println(VUOTA);
        coda().forEach(this::fixCoda);
    }

    //--sorgente
    //--tagFinale
    //--coda
    //--prima
    //--ultima
    void fixCoda(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        String tagFinale = (String) mat[1];
        previsto = (String) mat[2];
        String previstoPrima = (String) mat[3];
        String previstoDopo = (String) mat[4];
        String ottenutoPrima;
        String ottenutoDopo;
        String risultato;

        ottenuto = service.levaCoda(sorgente, tagFinale);
        ottenutoPrima = service.levaCodaDaPrimo(sorgente, tagFinale);
        ottenutoDopo = service.levaCodaDaUltimo(sorgente, tagFinale);

        previsto = service.isValid(previsto) ? previsto : NULLO;
        previstoPrima = service.isValid(previstoPrima) ? previstoPrima : NULLO;
        previstoDopo = service.isValid(previstoDopo) ? previstoDopo : NULLO;
        ottenuto = service.isValid(ottenuto) ? ottenuto : NULLO;
        ottenutoPrima = service.isValid(ottenutoPrima) ? ottenutoPrima : NULLO;
        ottenutoDopo = service.isValid(ottenutoDopo) ? ottenutoDopo : NULLO;

        risultato = previsto.equals(ottenuto) ? GIUSTO : SBAGLIATO;
        message = String.format("%s di [%s] con tagFinale [%s]: '%s'%s'%s' %s", "levaCoda", sorgente, tagFinale, previsto, FORWARD, ottenuto, risultato);
        System.out.println(message);
        risultato = previstoPrima.equals(ottenutoPrima) ? GIUSTO : SBAGLIATO;
        message = String.format("%s di [%s] con tagFinale [%s]: '%s'%s'%s' %s", "levaCodaDaPrimo", sorgente, tagFinale, previstoPrima, FORWARD, ottenutoPrima, risultato);
        System.out.println(message);
        risultato = previstoDopo.equals(ottenutoDopo) ? GIUSTO : SBAGLIATO;
        message = String.format("%s di [%s] con tagFinale [%s]: '%s'%s'%s' %s", "levaCodaDaUltimo", sorgente, tagFinale, previstoDopo, FORWARD, ottenutoDopo, risultato);
        System.out.println(message);
        System.out.println(VUOTA);

        assertEquals(previsto, ottenuto);
        assertEquals(previstoPrima, ottenutoPrima);
        assertEquals(previstoDopo, ottenutoDopo);
    }


    @Test
    @Order(51)
    @DisplayName("51 - sostituisce")
    void sostituisce() {
        System.out.println(("51 - sostituisce"));
        System.out.println(VUOTA);
        System.out.println(("Sostituisce nel testo tutte le occorrenze di oldTag con newTag"));
        System.out.println(VUOTA);
        sostituzioni().forEach(this::fixSostituisce);
    }

    //--testo sorgente
    //--oldTag da sostituire
    //--newTag da inserire
    //--testo previsto
    void fixSostituisce(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        sorgente2 = (String) mat[1];
        sorgente3 = (String) mat[2];
        previsto = (String) mat[3];

        ottenuto = service.sostituisce(sorgente, sorgente2, sorgente3);
        assertEquals(previsto, ottenuto);
        sorgente2 = service.isValid(sorgente2) ? sorgente2 : NULLO;
        sorgente3 = service.isValid(sorgente3) ? sorgente3 : NULLO;
        sorgente = service.isValid(sorgente) ? sorgente : NULLO;
        ottenuto = service.isValid(ottenuto) ? ottenuto : NULLO;
        System.out.println(String.format("[%s%s%s] %s%s%s", sorgente2, FORWARD, sorgente3, sorgente, FORWARD, ottenuto));
    }

    @Test
    @Order(52)
    @DisplayName("52 - levaTesto")
    void levaTesto() {
        System.out.println(("52 - levaTesto"));
        System.out.println(VUOTA);
        System.out.println(("Elimina dal tutte le occorrenze di tag"));
        System.out.println(VUOTA);
        levaTestoStream().forEach(this::fixLevaTesto);
    }

    //--testo sorgente
    //--tag da eliminare
    //--testo previsto
    void fixLevaTesto(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        sorgente2 = (String) mat[1];
        previsto = (String) mat[2];

        ottenuto = service.levaTesto(sorgente, sorgente2);
        assertEquals(previsto, ottenuto);
        sorgente2 = service.isValid(sorgente2) ? sorgente2 : NULLO;
        sorgente = service.isValid(sorgente) ? sorgente : NULLO;
        ottenuto = service.isValid(ottenuto) ? ottenuto : NULLO;
        System.out.println(String.format("[%s] %s%s%s", sorgente2, sorgente, FORWARD, ottenuto));
    }


    @Test
    @Order(53)
    @DisplayName("53 - levaVirgole")
    void levaVirgole() {
        System.out.println(("53 - levaVirgole"));
        System.out.println(VUOTA);
        System.out.println(("Elimina dal testo tutte le virgole"));
        System.out.println(VUOTA);
        levaVirgoleStream().forEach(this::fixLevaVirgole);
    }

    //--testo sorgente
    //--testo previsto
    void fixLevaVirgole(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previsto = (String) mat[1];

        ottenuto = service.levaVirgole(sorgente);
        assertEquals(previsto, ottenuto);
        sorgente = service.isValid(sorgente) ? sorgente : NULLO;
        ottenuto = service.isValid(ottenuto) ? ottenuto : NULLO;
        System.out.println(String.format("%s%s%s", sorgente, FORWARD, ottenuto));
    }


    @Test
    @Order(54)
    @DisplayName("54 - levaPunti")
    void levaPunti() {
        System.out.println(("54 - levaPunti"));
        System.out.println(VUOTA);
        System.out.println(("Elimina dal testo tutte i punti"));
        System.out.println(VUOTA);
        levaPuntiStream().forEach(this::fixLevaPunti);
    }

    //--testo sorgente
    //--testo previsto
    void fixLevaPunti(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previsto = (String) mat[1];

        ottenuto = service.levaPunti(sorgente);
        assertEquals(previsto, ottenuto);
        sorgente = service.isValid(sorgente) ? sorgente : NULLO;
        ottenuto = service.isValid(ottenuto) ? ottenuto : NULLO;
        System.out.println(String.format("%s%s%s", sorgente, FORWARD, ottenuto));
    }


    @Test
    @Order(55)
    @DisplayName("55 - levaSpazi")
    void levaSpazi() {
        System.out.println(("55 - levaSpazi"));
        System.out.println(VUOTA);
        System.out.println(("Elimina dal testo gli spazi vuoti"));
        System.out.println(VUOTA);
        levaSpaziStream().forEach(this::fixLevaSpazi);
    }

    //--testo sorgente
    //--testo previsto
    void fixLevaSpazi(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previsto = (String) mat[1];

        ottenuto = service.levaSpazi(sorgente);
        assertEquals(previsto, ottenuto);
        sorgente = service.isValid(sorgente) ? sorgente : NULLO;
        ottenuto = service.isValid(ottenuto) ? ottenuto : NULLO;
        System.out.println(String.format("%s%s%s", sorgente, FORWARD, ottenuto));
    }


    @Test
    @Order(56)
    @DisplayName("56 - slashToPoint")
    void slashToPoint() {
        System.out.println(("56 - slashToPoint"));
        System.out.println(VUOTA);
        System.out.println(("Sostituisce tutti gli slash in punti"));
        System.out.println(VUOTA);
        slashToPointStream().forEach(this::fixSlashToPoint);
    }

    //--testo sorgente
    //--testo previsto
    void fixSlashToPoint(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previsto = (String) mat[1];

        ottenuto = service.slashToPoint(sorgente);
        assertEquals(previsto, ottenuto);
        sorgente = service.isValid(sorgente) ? sorgente : NULLO;
        ottenuto = service.isValid(ottenuto) ? ottenuto : NULLO;
        System.out.println(String.format("%s%s%s", sorgente, FORWARD, ottenuto));
    }


    @Test
    @Order(57)
    @DisplayName("57 - pointToSlash")
    void pointToSlash() {
        System.out.println(("57 - pointToSlash"));
        System.out.println(VUOTA);
        System.out.println(("Sostituisce tutti i punti in slash"));
        System.out.println(VUOTA);
        pointToSlashStream().forEach(this::fixPointToSlash);
    }

    //--testo sorgente
    //--testo previsto
    void fixPointToSlash(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previsto = (String) mat[1];

        ottenuto = service.pointToSlash(sorgente);
        assertEquals(previsto, ottenuto);
        sorgente = service.isValid(sorgente) ? sorgente : NULLO;
        ottenuto = service.isValid(ottenuto) ? ottenuto : NULLO;
        System.out.println(String.format("%s%s%s", sorgente, FORWARD, ottenuto));
    }


    @Test
    @Order(60)
    @DisplayName("60 - getArray")
    void getArray() {
        System.out.println(("60 - getArray"));
        System.out.println(VUOTA);
        System.out.println(("Sostituisce tutti i punti in slash"));
        getArrayStream().forEach(this::fixGetArray);
    }

    //--testo sorgente
    //--array previsto
    void fixGetArray(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previstoArray = (List) mat[1];

        ottenutoArray = service.getArray(sorgente);
        assertEquals(previstoArray, ottenutoArray);
        System.out.println(VUOTA);
        System.out.println(sorgente);
        printLista(ottenutoArray);

    }


    @Test
    @Order(70)
    @DisplayName("70 - format")
    void format() {
        System.out.println(("70 - format"));
        System.out.println(VUOTA);
        System.out.println(("Formattazione di un numero"));
        System.out.println(VUOTA);
        formatStream().forEach(this::fixFormat);
    }

    //--object sorgente
    //--testo ottenuto
    void fixFormat(Arguments arg) {
        Object[] mat = arg.get();
        object = mat[0];
        previsto = (String) mat[1];

        ottenuto = service.format(object);
        object = object != null ? object : NULLO;
        ottenuto = service.isValid(ottenuto) ? ottenuto : NULLO;
        System.out.println(String.format("%s%s%s", object, FORWARD, ottenuto));
        assertEquals(previsto, ottenuto);
    }

    @Test
    @Order(80)
    @DisplayName("80 - rightPad")
    void rightPad() {
        System.out.println(("80 - rightPad"));
        System.out.println(VUOTA);
        System.out.println(("Allunga un testo alla lunghezza desiderata"));
        System.out.println(VUOTA);
        pad().forEach(this::fixPad);
    }


    //--object sorgente
    //--testo previsto
    //--length
    void fixPad(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previsto = (String) mat[1];
        sorgenteIntero = (int) mat[2];

        ottenuto = service.rightPad(sorgente, sorgenteIntero);
        message = String.format("[%s] (%d) %s[%s]", sorgente, sorgenteIntero, FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }


    @Test
    @Order(81)
    @DisplayName("81 - fixSize")
    void fixSize() {
        System.out.println(("81 - fixSize"));
        System.out.println(VUOTA);
        System.out.println(("Forza un testo alla lunghezza desiderata"));
        System.out.println(VUOTA);
        size().forEach(this::fixFixSize);
    }


    //--object sorgente
    //--testo previsto
    //--length
    void fixFixSize(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previsto = (String) mat[1];
        sorgenteIntero = (int) mat[2];

        ottenuto = service.fixSize(sorgente, sorgenteIntero);
        message = String.format("[%s] (%d) %s[%s]", sorgente, sorgenteIntero, FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }


    @Test
    @Order(82)
    @DisplayName("82 - fixSizeQuadre")
    void fixSizeQuadre() {
        System.out.println(("82 - fixSizeQuadre"));
        System.out.println(VUOTA);
        System.out.println(("Forza un testo alla lunghezza desiderata e aggiunge singole parentesi quadre in testa e coda"));
        System.out.println(VUOTA);
        sizeQuadre().forEach(this::fixFixSizeQuadre);
    }


    //--object sorgente
    //--testo previsto
    //--length
    void fixFixSizeQuadre(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previsto = (String) mat[1];
        sorgenteIntero = (int) mat[2];

        ottenuto = service.fixSizeQuadre(sorgente, sorgenteIntero);
        message = String.format("[%s] (%d) %s%s", sorgente, sorgenteIntero, FORWARD, ottenuto);
        System.out.println(message);
        assertEquals(previsto, ottenuto);
    }


    @Test
    @Order(91)
    @DisplayName("91 - estrae estremi compresi")
    void estraeCon() {
        System.out.println(("91 - estrae estremi compresi"));
        System.out.println(VUOTA);
        System.out.println(("Elimina dal testo il contenuto compreso tra tagIniziale e tagFinale, se esistono"));
        System.out.println(VUOTA);
        estrae().forEach(this::fixEstraeCon);
    }


    //--sorgente
    //--tagIniziale
    //--tagFinale
    //--testo estratto con
    //--testo estratto senza
    void fixEstraeCon(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        sorgente2 = (String) mat[1];
        sorgente3 = (String) mat[2];
        previsto = (String) mat[3];

        ottenuto = service.estraeCon(sorgente, sorgente2, sorgente3);
        assertEquals(previsto, ottenuto);
        sorgente2 = service.isValid(sorgente2) ? sorgente2 : NULLO;
        sorgente3 = service.isValid(sorgente3) ? sorgente3 : NULLO;
        sorgente = service.isValid(sorgente) ? sorgente : NULLO;
        ottenuto = service.isValid(ottenuto) ? ottenuto : NULLO;
        System.out.println(String.format("[%s%s%s] %s%s[%s]", sorgente2, FORWARD, sorgente3, sorgente, FORWARD, ottenuto));
    }


    @Test
    @Order(92)
    @DisplayName("92 - estrae estremi esclusi")
    void estraeSenza() {
        System.out.println(("92 - estrae estremi esclusi"));
        System.out.println(VUOTA);
        System.out.println(("Elimina dal testo il contenuto compreso tra tagIniziale e tagFinale, se esistono"));
        System.out.println(VUOTA);
        estrae().forEach(this::fixEstraeSenza);
    }


    //--sorgente
    //--tagIniziale
    //--tagFinale
    //--testo estratto con
    //--testo estratto senza
    void fixEstraeSenza(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        sorgente2 = (String) mat[1];
        sorgente3 = (String) mat[2];
        previsto = (String) mat[4];

        ottenuto = service.estraeSenza(sorgente, sorgente2, sorgente3);
        assertEquals(previsto, ottenuto);
        sorgente2 = service.isValid(sorgente2) ? sorgente2 : NULLO;
        sorgente3 = service.isValid(sorgente3) ? sorgente3 : NULLO;
        sorgente = service.isValid(sorgente) ? sorgente : NULLO;
        ottenuto = service.isValid(ottenuto) ? ottenuto : NULLO;
        System.out.println(String.format("[%s%s%s] %s%s[%s]", sorgente2, FORWARD, sorgente3, sorgente, FORWARD, ottenuto));
    }


    @Test
    @Order(120)
    @DisplayName("120 - setNoDoppieQuadre")
    void setNoDoppieQuadre() {
        System.out.println(("120 - elimina le doppie quadre"));
        System.out.println(VUOTA);
        doppieQuadre().forEach(this::fixDoppieQuadre);
    }


    @Test
    @Order(210)
    @DisplayName("210 - escape")
    void escape() {
        System.out.println(("210 - escape"));
        System.out.println(VUOTA);

        sorgente = "<!-- NON MODIFICATE DIRETTAMENTE QUESTA PAGINA - GRAZIE -->\n" +
                "<noinclude>__EXPECTED_UNCONNECTED_PAGE____NOEDITSECTION__{{Torna a|399 a.C.}}{{ListaBio|bio=2|data=7 mar 2024|progetto=biografie}}</noinclude>\n" +
                "{{Lista persone per anno\n" +
                "|titolo=Morti nel 399 a.C.\n" +
                "|voci=2\n" +
                "|testo=<nowiki></nowiki>\n" +
                "*[[Amirteo]], sovrano egizio\n" +
                "*[[Socrate]], filosofo greco antico}}\n" +
                "<noinclude>\n" +
                "== Altri progetti ==\n" +
                "{{interprogetto}}\n" +
                "{{Portale|biografie}}\n" +
                "\n" +
                "*<nowiki>[[Categoria:Liste di morti nel IV secolo a.C.| 60200]]</nowiki>\n" +
                "*<nowiki>[[Categoria:Morti nel 399 a.C.| ]]</nowiki></noinclude>";
        sorgente2 = "{{Lista persone per anno\n" +
                "|titolo=Morti nel 399 a.C.\n" +
                "|voci=2\n" +
                "|testo=<nowiki></nowiki>\n" +
                "*[[Amirteo]], sovrano egizio\n" +
                "*[[Socrate]], filosofo greco antico}}";

        ottenutoBooleano = sorgente.contains(sorgente2);
        System.out.println(ottenutoBooleano);
    }

    //--sorgente
    //--previsto
    void fixDoppieQuadre(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previsto = (String) mat[1];

        ottenuto = service.setNoDoppieQuadre(sorgente);
        assertEquals(previsto, ottenuto);
        System.out.println(String.format("%s%s%s", sorgente, FORWARD, ottenuto));
    }

}

