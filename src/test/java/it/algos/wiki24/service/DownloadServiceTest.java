package it.algos.wiki24.service;

import com.mongodb.client.*;
import it.algos.*;
import it.algos.base24.basetest.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.service.*;
import it.algos.vbase.backend.wrapper.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.*;

import javax.inject.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 04-Mar-2024
 * Time: 10:44
 */
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Download Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DownloadServiceTest extends ServiceTest {

    @Inject
    private DownloadService service;

    @Inject
    private BioServerModulo bioServerModulo;

    @Inject
    private MongoService mongoService;

    @Inject
    private WikiBotService wikiBotService;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = DownloadService.class;
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
    @DisplayName("10 - downloadVelocità")
    void downloadVelocità() {
        System.out.println(("10 - downloadVelocità"));
        System.out.println(VUOTA);
        List<Long> lista;
        MongoCursor<Long> cursore;
        long fine;
        long durataUno;
        long durataDue;

        // List<Long> lista = bioServerModulo.findOnlyPageId();
        inizio = System.currentTimeMillis();
        lista = bioServerModulo.findOnlyPageId();
        assertNotNull(lista);
        assertTrue(lista.size() > 0);
        fine = System.currentTimeMillis();
        durataUno = inizio - fine;

        logger.info(new WrapLog().message("bioServerModulo.findOnlyPageId()"));
        message = String.format("Tempo (esatto) impiegato: [%s]", dateService.deltaTextEsatto(inizio));
        logger.info(new WrapLog().message(message));

        // cursore = mongoService.getCollection("bioserver").distinct("pageId", Long.class).iterator()
        inizio = System.currentTimeMillis();
        cursore = mongoService.getCollection("bioserver").distinct("pageId", Long.class).iterator();
        while (cursore.hasNext()) {
            lista.add(cursore.next());
        }
        fine = System.currentTimeMillis();
        durataDue = inizio - fine;

        logger.info(new WrapLog().message("cursore"));
        message = String.format("Tempo (esatto) impiegato: [%s]", dateService.deltaTextEsatto(inizio));
        logger.info(new WrapLog().message(message));

        message = String.format("I due metodi hanno una differenza di [%s] millisecondi", durataUno - durataDue);
        logger.info(new WrapLog().message(message));
    }


    @Test
    @Order(20)
    @DisplayName("20 - projectionWrapTime")
    void projectionWrapTime() {
        System.out.println(("20 - projectionWrapTime"));
        System.out.println(VUOTA);
        List<WrapTime> listaWrap;
        sorgente = "bioserver";
        MongoCursor<Long> cursore;
        long fine;
        long durataUno;
        long durataDue;
        int max = mongoService.count(sorgente);

        // List<WrapTime> projectionWrapTime();
        inizio = System.currentTimeMillis();
        listaWrap = wikiBotService.projectionWrapTime();
        assertNotNull(listaWrap);
        assertTrue(listaWrap.size() > 0);
        fine = System.currentTimeMillis();
        durataUno = inizio - fine;

        logger.info(new WrapLog().message("wikiBotService.projectionWrapTime()"));
        message = String.format("Tempo (esatto) impiegato: [%s]", dateService.deltaTextEsatto(inizio));
        logger.info(new WrapLog().message(message));

        // List<WrapTime> projectionWrapTimeNew();
        inizio = System.currentTimeMillis();
        listaWrap = wikiBotService.projectionWrapTimeNew();
        assertNotNull(listaWrap);
        assertTrue(listaWrap.size() > 0);
        fine = System.currentTimeMillis();
        durataDue = inizio - fine;

        logger.info(new WrapLog().message("wikiBotService.projectionWrapTime()"));
        message = String.format("Tempo (esatto) impiegato: [%s]", dateService.deltaTextEsatto(inizio));
        logger.info(new WrapLog().message(message));

        message = String.format("I due metodi hanno una differenza di [%s] millisecondi", durataUno - durataDue);
        logger.info(new WrapLog().message(message));

        assertEquals(max, listaWrap.size());
        message = String.format("Sono stati recuperati tutti i [%s] WrapTime della collection [%s]", textService.format(listaWrap.size()), sorgente);
        logger.info(new WrapLog().message(message));
    }

}
