package it.algos.base;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.service.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sun, 26-Feb-2023
 * Time: 19:12
 */
public abstract class WikiBackendTest extends BackendTest {

    protected WikiBackend wikiBackend;

    @InjectMocks
    protected GiornoBackend giornoBackend;

    @InjectMocks
    protected GiornoWikiBackend giornoWikiBackend;

    @InjectMocks
    protected MeseBackend meseBackend;

    @InjectMocks
    protected AnnoBackend annoBackend;
    @InjectMocks
    protected AnnoWikiBackend annoWikiBackend;

    @InjectMocks
    protected WikiUtility wikiUtility;

    @InjectMocks
    protected QueryService queryService;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        assertNotNull(giornoBackend);
        assertNotNull(giornoWikiBackend);
        assertNotNull(annoBackend);
        assertNotNull(annoWikiBackend);
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
    protected void fixRiferimentiIncrociati() {
        super.fixRiferimentiIncrociati();

        wikiBackend.giornoBackend = giornoBackend;
        wikiBackend.giornoBackend.mongoService = mongoService;
        wikiBackend.giornoBackend.annotationService = annotationService;
        wikiBackend.giornoBackend.textService = textService;
        wikiBackend.wikiUtility = wikiUtility;
        wikiBackend.wikiUtility.textService = textService;
        wikiBackend.wikiUtility.regexService = regexService;
        wikiBackend.wikiUtility.queryService = queryService;

        wikiBackend.meseBackend = meseBackend;
        wikiBackend.meseBackend.mongoService = mongoService;
        wikiBackend.meseBackend.reflectionService = reflectionService;
        wikiBackend.meseBackend.annotationService = annotationService;

    }

}
