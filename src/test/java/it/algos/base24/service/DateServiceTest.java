package it.algos.base24.service;

import it.algos.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.basetest.*;
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
 * Date: Sun, 22-Oct-2023
 * Time: 10:26
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Date Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DateServiceTest extends ServiceTest {

    @Autowired
    private DateService service;

    @Autowired
    private TextService textService;


    //--durata (long)
    private Stream<Arguments> milliSecondi() {
        return Stream.of(
                Arguments.of(0L),
                Arguments.of(2L),
                Arguments.of(30L),
                Arguments.of(400L),
                Arguments.of(950L),
                Arguments.of(1050L),
                Arguments.of(5000L),
                Arguments.of(59999L),
                Arguments.of(60000L),
                Arguments.of(60001L),
                Arguments.of(70000L),
                Arguments.of(800000L)
        );
    }

    //--durata (long)
    private Stream<Arguments> secondi() {
        return Stream.of(
                Arguments.of(0L),
                Arguments.of(2L),
                Arguments.of(30L),
                Arguments.of(59L),
                Arguments.of(60L),
                Arguments.of(61L),
                Arguments.of(400L),
                Arguments.of(599L),
                Arguments.of(600L),
                Arguments.of(601L),
                Arguments.of(1000L),
                Arguments.of(5000L)
        );
    }


    //--durata (long)
    private Stream<Arguments> minuti() {
        return Stream.of(
                Arguments.of(0L),
                Arguments.of(2L),
                Arguments.of(30L),
                Arguments.of(59L),
                Arguments.of(60L),
                Arguments.of(61L),
                Arguments.of(400L),
                Arguments.of(599L),
                Arguments.of(600L),
                Arguments.of(601L),
                Arguments.of(1000L),
                Arguments.of(5000L)
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = DateService.class;
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
    @Order(11)
    @DisplayName("11 - deltaText")
    void deltaText() {
        System.out.println(("11 - deltaText"));
        System.out.println(VUOTA);

        //--durata (long)
        milliSecondi().forEach(this::fixDeltaText);
    }


    //--durata (long)
    void fixDeltaText(Arguments arg) {
        Object[] mat = arg.get();
        long sorgenteLungo = (Long) mat[0];

        inizio = inizio - sorgenteLungo;
        ottenuto = service.deltaText(inizio);
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Millisecondi [%s]%s%s", sorgenteLungo, FORWARD, ottenuto);
        System.out.println(message);
    }

    @Test
    @Order(12)
    @DisplayName("12 - deltaTextEsatto")
    void deltaTextEsatto() {
        System.out.println(("12 - deltaTextEsatto"));
        System.out.println(VUOTA);

        //--durata (long)
        milliSecondi().forEach(this::fixDeltaTextEsatto);
    }

    //--durata (long)
    void fixDeltaTextEsatto(Arguments arg) {
        Object[] mat = arg.get();
        long sorgenteLungo = (Long) mat[0];

        inizio = inizio - sorgenteLungo;
        ottenuto = service.deltaTextEsatto(inizio);
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Millisecondi [%s]%s%s", sorgenteLungo, FORWARD, ottenuto);
        System.out.println(message);
    }


    @Test
    @Order(13)
    @DisplayName("13 - toText")
    void toText() {
        System.out.println(("13 - toText"));
        System.out.println(VUOTA);

        //--durata (long)
        milliSecondi().forEach(this::fixToText);
    }

    //--durata (long)
    void fixToText(Arguments arg) {
        Object[] mat = arg.get();
        long sorgenteLungo = (Long) mat[0];

        ottenuto = service.toText(sorgenteLungo);
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Millisecondi [%d]%s%s", sorgenteLungo, FORWARD, ottenuto);
        System.out.println(message);
    }


    @Test
    @Order(14)
    @DisplayName("14 - toTextSecondi")
    void toTextSecondi() {
        System.out.println(("14 - toTextSecondi"));
        System.out.println(VUOTA);

        //--durata (long)
        secondi().forEach(this::fixToTextSecondi);
    }

    //--durata (long)
    void fixToTextSecondi(Arguments arg) {
        Object[] mat = arg.get();
        long sorgenteLungo = (Long) mat[0];

        ottenuto = service.toTextSecondi(sorgenteLungo);
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Secondi [%d]%s%s", sorgenteLungo, FORWARD, ottenuto);
        System.out.println(message);
    }


    @Test
    @Order(15)
    @DisplayName("15 - toTextMinuti")
    void toTextMinuti() {
        System.out.println(("15 - toTextMinuti"));
        System.out.println(VUOTA);

        //--durata (long)
        minuti().forEach(this::fixToTextMinuti);
    }

    //--durata (long)
    void fixToTextMinuti(Arguments arg) {
        Object[] mat = arg.get();
        long sorgenteLungo = (Long) mat[0];

        ottenuto = service.toTextMinuti(sorgenteLungo);
        assertTrue(textService.isValid(ottenuto));
        message = String.format("Minuti [%d]%s%s", sorgenteLungo, FORWARD, ottenuto);
        System.out.println(message);
    }

    @Test
    @Order(201)
    @DisplayName("201 - getAllGiorni")
    void getAllGiorni() {
        System.out.println(("201 - getAllGiorni"));
        System.out.println(VUOTA);

        List<HashMap<String, Object>> listaGiorni = service.getAllGiorni();
        assertNotNull(listaGiorni);
        assertEquals(NUM_GIORNI_ANNO,listaGiorni.size());
        message = String.format("Ci sono %d giorni nell'anno. Considerando anche il 29 febbraio", listaGiorni.size());
        System.out.println(message);
    }

}

