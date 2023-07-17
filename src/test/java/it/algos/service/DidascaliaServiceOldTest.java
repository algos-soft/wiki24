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
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = {Wiki24App.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Didascalia Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DidascaliaServiceOldTest extends AlgosTest {

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

    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(41)
    @DisplayName("41 - giornoNatoTesta base (linkLista)")
        //--biografia
    void giornoNatoTesta(final Bio bio) {
        System.out.println(("41 - giornoNatoTesta base"));
        System.out.println(VUOTA);

        ottenuto = wikiUtility.giornoNatoTesta(bio, null);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(42)
    @DisplayName("42 - giornoNatoTesta linkVoce")
        //--biografia
    void giornoNatoTesta2(final Bio bio) {
        System.out.println(("42 - giornoNatoTesta linkVoce"));
        System.out.println(VUOTA);

        ottenuto = wikiUtility.giornoNatoTesta(bio, AETypeLink.linkVoce);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(43)
    @DisplayName("43 - giornoNatoTesta nessunLink")
        //--biografia
    void giornoNatoTesta3(final Bio bio) {
        System.out.println(("43 - giornoNatoTesta nessunLink"));
        System.out.println(VUOTA);

        ottenuto = wikiUtility.giornoNatoTesta(bio, AETypeLink.nessunLink);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(51)
    @DisplayName("51 - giornoMortoTesta base (linkLista)")
        //--biografia
    void giornoMortoTesta(final Bio bio) {
        System.out.println(("51 - giornoMortoTesta base"));
        System.out.println(VUOTA);

        ottenuto = wikiUtility.giornoMortoTesta(bio, null);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(52)
    @DisplayName("52 - giornoMortoTesta linkVoce")
        //--biografia
    void giornoMortoTesta2(final Bio bio) {
        System.out.println(("52 - giornoMortoTesta linkVoce"));
        System.out.println(VUOTA);

        ottenuto = wikiUtility.giornoMortoTesta(bio, AETypeLink.linkVoce);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(53)
    @DisplayName("53 - giornoMortoTesta nessunLink")
        //--biografia
    void giornoMortoTesta3(final Bio bio) {
        System.out.println(("53 - giornoMortoTesta nessunLink"));
        System.out.println(VUOTA);

        ottenuto = wikiUtility.giornoMortoTesta(bio, AETypeLink.nessunLink);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(61)
    @DisplayName("61 - annoNatoTesta base (linkLista)")
        //--biografia
    void annoNatoTesta(final Bio bio) {
        System.out.println(("61 - annoNatoTesta base"));
        System.out.println(VUOTA);

        ottenuto = wikiUtility.annoNatoTesta(bio, null);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(62)
    @DisplayName("62 - annoNatoTesta linkVoce")
        //--biografia
    void annoNatoTesta2(final Bio bio) {
        System.out.println(("62 - annoNatoTesta linkVoce"));
        System.out.println(VUOTA);

        ottenuto = wikiUtility.annoNatoTesta(bio, AETypeLink.linkVoce);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(63)
    @DisplayName("63 - annoNatoTesta nessunLink")
        //--biografia
    void annoNatoTesta3(final Bio bio) {
        System.out.println(("63 - annoNatoTesta nessunLink"));
        System.out.println(VUOTA);

        ottenuto = wikiUtility.annoNatoTesta(bio, AETypeLink.nessunLink);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(71)
    @DisplayName("71 - annoMortoTesta base (linkLista)")
        //--biografia
    void annoMortoTesta(final Bio bio) {
        System.out.println(("71 - annoMortoTesta base"));
        System.out.println(VUOTA);

        ottenuto = wikiUtility.annoMortoTesta(bio, null);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(72)
    @DisplayName("72 - annoMortoTesta linkVoce")
        //--biografia
    void annoMortoTesta2(final Bio bio) {
        System.out.println(("72 - annoMortoTesta linkVoce"));
        System.out.println(VUOTA);

        ottenuto = wikiUtility.annoMortoTesta(bio, AETypeLink.linkVoce);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(ottenuto);
    }

    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(73)
    @DisplayName("73 - annoMortoTesta nessunLink")
        //--biografia
    void annoMortoTesta3(final Bio bio) {
        System.out.println(("73 - annoMortoTesta nessunLink"));
        System.out.println(VUOTA);

        ottenuto = wikiUtility.annoMortoTesta(bio, AETypeLink.nessunLink);
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


    private Bio creaBio(String wikiTitle) {
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


    protected void printBio(final Bio bio) {
        System.out.print(bio.wikiTitle);
        System.out.print(SEP);
        System.out.print(bio.pageId);
        System.out.print(SEP);
        System.out.print(bio.nome);
        System.out.print(SEP);
        System.out.print(bio.cognome);
        System.out.print(SEP);
        System.out.print(bio.giornoNato);
        System.out.print(SEP);
        System.out.print(bio.annoNato);
        System.out.print(SEP);
        System.out.print(bio.luogoNato);
        System.out.print(SEP);
        System.out.print(bio.luogoNatoLink);
        System.out.print(SEP);
        System.out.print(bio.giornoMorto);
        System.out.print(SEP);
        System.out.print(bio.annoMorto);
        System.out.print(SEP);
        System.out.print(bio.luogoMorto);
        System.out.print(SEP);
        System.out.print(bio.luogoMortoLink);
        System.out.print(SEP);
        System.out.print(bio.attivita);
        System.out.print(SEP);
        System.out.print(bio.attivita2);
        System.out.print(SEP);
        System.out.print(bio.attivita3);
        System.out.print(SEP);
        System.out.print(bio.nazionalita);
        System.out.println(VUOTA);
    }

}
