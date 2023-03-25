package it.algos.base;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.attivita.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.packages.nazionalita.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.service.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;

import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 26-Feb-2023
 * Time: 19:12
 */
public abstract class WikiBackendTest extends BackendTest {

    //    @Autowired
    protected WikiBackend wikiBackend;

    @Autowired
    protected BioBackend bioBackend;

    @Autowired
    protected GiornoWikiBackend giornoWikiBackend;

    @Autowired
    protected AnnoWikiBackend annoWikiBackend;

    @Autowired
    protected AttivitaBackend attivitaBackend;

    @Autowired
    protected NazionalitaBackend nazionalitaBackend;

    @Autowired
    protected WikiApiService wikiApiService;

    @Autowired
    protected WikiUtility wikiUtility;

    @Autowired
    protected QueryService queryService;

    @Autowired
    protected AttSingolareBackend attSingolareBackend;

    @Autowired
    protected AttPluraleBackend attPluraleBackend;

    @Autowired
    protected NazSingolareBackend nazSingolaBackend;

    @Autowired
    protected NazPluraleBackend nazPluraleBackend;


    //--nome attività singolare (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    public static Stream<Arguments> ATTIVITA_SINGOLARE() {
        return Stream.of(
                Arguments.of(VUOTA, false),
                Arguments.of("abati e badesse", false),
                Arguments.of("politico", false),
                Arguments.of("politici", true),
                Arguments.of("direttore di scena", true),
                Arguments.of("attrice", true),
                Arguments.of("attore", true),
                Arguments.of("attori", false),
                Arguments.of("brasiliano", true),
                Arguments.of("vescovo ariano", false),
                Arguments.of("errata", false),
                Arguments.of("britannici", false),
                Arguments.of("tedesco", true),
                Arguments.of("tedeschi", false)
        );
    }


    //--nome nazionalità singolare (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    public static Stream<Arguments> NAZIONALITA_SINGOLARE() {
        return Stream.of(
                Arguments.of(VUOTA, false, false),
                Arguments.of("turco", true, true),
                Arguments.of("tedesca", true, true),
                Arguments.of("direttore di scena", false, false),
                Arguments.of("Congolesi (Rep. Dem. del Congo)", false, false),
                Arguments.of("brasiliano", true, true),
                Arguments.of("burgunda", true, true),
                Arguments.of("italiano", true, true),
                Arguments.of("Italiano", true, false),
                Arguments.of("italiana", true, true),
                Arguments.of("italiani", false, false),
                Arguments.of("Burgunda", true, false),
                Arguments.of("vescovo ariano", false, false),
                Arguments.of("errata", false, false),
                Arguments.of("britannici", false, false),
                Arguments.of("tedesco", true, true),
                Arguments.of("tedeschi", false, false)
        );
    }


    //--nome nazionalità plurale (maiuscola o minuscola)
    //--esiste ID
    //--esiste key
    public static Stream<Arguments> NAZIONALITA_PLURALE() {
        return Stream.of(
                Arguments.of(VUOTA, false, false),
                Arguments.of("turchi", true, true),
                Arguments.of("tedeschi", true, true),
                Arguments.of("direttore di scena", false, false),
                Arguments.of("Congolesi (Rep. Dem. del Congo)", false, false),
                Arguments.of("brasiliano", false, false),
                Arguments.of("burgunda", false, false),
                Arguments.of("italiano", false, false),
                Arguments.of("Italiano", false, false),
                Arguments.of("italiana", false, false),
                Arguments.of("italiani", true, true),
                Arguments.of("Burgunda", false, false),
                Arguments.of("vescovo ariano", false, false),
                Arguments.of("errata", false, false),
                Arguments.of("britannici", true, true),
                Arguments.of("tedesco", false, false),
                Arguments.of("tedeschi", true, true)
        );
    }

    //--nome nazionalità plurale (maiuscola o minuscola)
    public static Stream<Arguments> NAZIONALITA() {
        return Stream.of(
                Arguments.of(VUOTA),
                Arguments.of("britannici"),
                Arguments.of("italiani"),
                Arguments.of("tedeschi"),
                Arguments.of("burkinabé"),
                Arguments.of("germanici"),
                Arguments.of("barbadiani"),
                Arguments.of("Congolesi (Rep. Dem. del Congo)"),
                Arguments.of("berberi"),
                Arguments.of("Berberi"),
                Arguments.of("galli"),
                Arguments.of("etiopi"),
                Arguments.of("danesi"),
                Arguments.of("maltesi"),
                Arguments.of("angolani")
        );
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        assertNotNull(bioBackend);
        assertNotNull(giornoBackend);
        assertNotNull(giornoWikiBackend);
        assertNotNull(annoBackend);
        assertNotNull(annoWikiBackend);
        assertNotNull(attivitaBackend);
        assertNotNull(nazionalitaBackend);
        assertNotNull(wikiApiService);
        assertNotNull(wikiUtility);
        assertNotNull(queryService);

        super.crudBackend = wikiBackend;
        super.setUpAll();
    }

    /**
     * Regola tutti riferimenti incrociati <br>
     * Deve essere fatto dopo aver costruito tutte le referenze 'mockate' <br>
     * Nelle sottoclassi devono essere regolati i riferimenti dei service specifici <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    //    protected void fixRiferimentiIncrociati() {
    //        super.fixRiferimentiIncrociati();
    //
    //        giornoBackend.textService = textService;
    //        giornoBackend.mongoService = mongoService;
    //        giornoBackend.reflectionService = reflectionService;
    //        giornoBackend.annotationService = annotationService;
    //
    //        annoBackend.textService = textService;
    //        annoBackend.mongoService = mongoService;
    //        annoBackend.reflectionService = reflectionService;
    //        annoBackend.annotationService = annotationService;
    //
    //        secoloBackend.mongoService = mongoService;
    //        secoloBackend.reflectionService = reflectionService;
    //        secoloBackend.annotationService = annotationService;
    //
    //        meseBackend.mongoService = mongoService;
    //        meseBackend.reflectionService = reflectionService;
    //        meseBackend.annotationService = annotationService;
    //
    //        wikiBackend.giornoBackend = giornoBackend;
    //        wikiBackend.giornoBackend.textService = textService;
    //        wikiBackend.giornoBackend.mongoService = mongoService;
    //        wikiBackend.giornoBackend.reflectionService = reflectionService;
    //        wikiBackend.giornoBackend.annotationService = annotationService;
    //
    //        wikiBackend.annoBackend = annoBackend;
    //        wikiBackend.annoBackend.textService = textService;
    //        wikiBackend.annoBackend.mongoService = mongoService;
    //        wikiBackend.annoBackend.reflectionService = reflectionService;
    //        wikiBackend.annoBackend.annotationService = annotationService;
    //
    //        wikiBackend.wikiUtility = wikiUtility;
    //        wikiBackend.wikiUtility.textService = textService;
    //        wikiBackend.wikiUtility.regexService = regexService;
    //        wikiBackend.wikiUtility.queryService = queryService;
    //
    //        wikiBackend.meseBackend = meseBackend;
    //        wikiBackend.meseBackend.mongoService = mongoService;
    //        wikiBackend.meseBackend.reflectionService = reflectionService;
    //        wikiBackend.meseBackend.annotationService = annotationService;
    //
    //        attivitaBackend.wikiApiService = wikiApiService;
    //        nazionalitaBackend.wikiApiService = wikiApiService;
    //    }

}
