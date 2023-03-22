package it.algos.base;

import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.attivita.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.packages.nazionalita.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.service.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;

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
    protected NazSingolareBackend nazSingolaBackend;

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
