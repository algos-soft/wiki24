package it.algos.service;

import it.algos.*;
import it.algos.base.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.service.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Sat, 11-Mar-2023
 * Time: 17:41
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("quickly")
@Tag("service")
@DisplayName("Download Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DownloadServiceTest extends WikiTest {

    @Mock
    private MongoOperations mongoOp;

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    @InjectMocks
    private DownloadService service;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.clazz = WikiUtility.class;

        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.openMocks(service);
//        MockitoAnnotations.openMocks(bioBackend);
//        MockitoAnnotations.openMocks(mongoService);
//        MockitoAnnotations.openMocks(mongoOp);
//        MockitoAnnotations.openMocks(textService);
//        MockitoAnnotations.openMocks(fileService);
//        MockitoAnnotations.openMocks(wikiUtility);
//        MockitoAnnotations.openMocks(preferenceService);
//        MockitoAnnotations.openMocks(preferenzaBackend);
//        MockitoAnnotations.openMocks(logService);
//        MockitoAnnotations.openMocks(regexService);

//        service.textService = textService;
//        service.wikiUtility = wikiUtility;
//        fileService.textService = textService;
//        mongoService.textService = textService;
//        mongoService.fileService = fileService;
//        wikiUtility.textService = textService;
//        wikiUtility.logService = logService;
//        wikiUtility.regexService = regexService;
//        logService.textService = textService;
//        preferenceService.preferenzaBackend = preferenzaBackend;
//        preferenzaBackend.mongoService = mongoService;

//        for (AIGenPref pref : Pref.values()) {
//            pref.setPreferenceService(preferenceService);
//        }
//        for (AIGenPref pref : WPref.values()) {
//            pref.setPreferenceService(preferenceService);
//        }

        //--reindirizzo l'istanza
//        service = downloadService;
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
    @Order(1)
    @DisplayName("Primo test")
    void getLabelHost() {
        List<Long> listaPageIdsDaCreare=Arrays.asList(689981L,702527L);

        service.creaNewEntities(listaPageIdsDaCreare);
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
