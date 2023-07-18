package it.algos.service;

import com.mongodb.*;
import com.mongodb.client.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.packages.utility.preferenza.*;
import it.algos.vaad24.backend.service.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.service.*;
import org.bson.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;

import org.springframework.data.mongodb.core.*;

import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Mon, 17-Jul-2023
 * Time: 15:46
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Didascalia Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DidascaliaServiceTest extends WikiTest {

    private Bio bioBean;

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    @InjectMocks
    private DidascaliaService service;

    @InjectMocks
    private BioBackend bioBackend;

    @InjectMocks
    private TextService textService;

    @InjectMocks
    private FileService fileService;

    @InjectMocks
    private WikiUtility wikiUtility;

    @InjectMocks
    private PreferenceService preferenceService;

    @InjectMocks
    private PreferenzaBackend preferenzaBackend;

    @InjectMocks
    public LogService logService;

    @InjectMocks
    public RegexService regexService;

    @InjectMocks
    private MongoService mongoService;

    @Mock
    private MongoOperations mongoOp;


    //--biografie
    private Stream<Bio> biografie() {
        return Stream.of(
                creaBio("Ray Felix"),
                creaBio("Roberto Rullo"),
                creaBio("Stanley Adams (attore)"),
                creaBio("Jameson Adams"),
                creaBio("Jordan Adams (1981)")
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.openMocks(service);
        MockitoAnnotations.openMocks(bioBackend);
        MockitoAnnotations.openMocks(mongoService);
        MockitoAnnotations.openMocks(mongoOp);
        MockitoAnnotations.openMocks(textService);
        MockitoAnnotations.openMocks(fileService);
        MockitoAnnotations.openMocks(wikiUtility);
        MockitoAnnotations.openMocks(preferenceService);
        MockitoAnnotations.openMocks(preferenzaBackend);
        MockitoAnnotations.openMocks(logService);
        MockitoAnnotations.openMocks(regexService);

        service.textService = textService;
        service.wikiUtility = wikiUtility;
        fileService.textService = textService;
        mongoService.textService = textService;
        mongoService.fileService = fileService;
        wikiUtility.textService = textService;
        wikiUtility.logService = logService;
        wikiUtility.regexService = regexService;
        logService.textService = textService;
        preferenceService.preferenzaBackend = preferenzaBackend;
        preferenzaBackend.mongoService = mongoService;

        for (AIGenPref pref : WPref.values()) {
            pref.setPreferenceService(preferenceService);
        }

        bioBean = creaBio("Luigi Giura");
        assertNotNull(bioBean);
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


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(11)
    @DisplayName("11 - nomeCognome")
        //--biografia
    void nomeCognome(final Bio bio) {
        System.out.println(("11 - nomeCognome"));
        System.out.println(VUOTA);

        ottenuto = service.nomeCognome(bio);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(21)
    @DisplayName("21 - attivitaNazionalita")
        //--biografia
    void attivitaNazionalita(final Bio bio) {
        System.out.println(("21 - attivitaNazionalita"));
        System.out.println(VUOTA);

        ottenuto = service.attivitaNazionalita(bio);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(31)
    @DisplayName("31 - luogoNato")
        //--biografia
    void luogoNato(final Bio bio) {
        System.out.println(("31 - luogoNato"));
        System.out.println(VUOTA);

        ottenuto = service.luogoNato(bio);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(32)
    @DisplayName("32 - luogoMorto")
    void luogoMorto(final Bio bio) {
        System.out.println(("32 - luogoMorto"));
        System.out.println(VUOTA);

        ottenuto = service.luogoMorto(bio);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    //    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(3)
    @DisplayName("3 - luogoNato")
    //--biografia
    void luogoNatoAnno(final Bio bio) {
        System.out.println(("3 - luogoNatoAnno"));
        System.out.println(VUOTA);

        ottenuto = service.luogoNatoAnno(bio, AETypeLink.nessunLink);
        assertTrue(textService.isValid(ottenuto));
        System.out.println(ottenuto);
    }

    protected Bio creaBio(String wikiTitle) {
        Bio beanBio = null;
        MongoCollection<Document> collection;
        FindIterable<Document> documents;
        MongoDatabase client = mongoService.getDB("wiki24");
        collection = client.getCollection("bio");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("wikiTitle", wikiTitle);
        Document doc = collection.find(whereQuery).first();

        if (doc != null) {
            beanBio = bioBackend.newEntity(doc);
        }

        return beanBio;
    }


}
