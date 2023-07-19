package it.algos.service;

import com.mongodb.*;
import com.mongodb.client.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.packages.utility.preferenza.*;
import it.algos.vaad24.backend.service.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.genere.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
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
    private GenereBackend genereBackend;

    @InjectMocks
    private AttSingolareBackend attSingolareBackend;

    @InjectMocks
    private AnnotationService annotationService;

    @InjectMocks
    private ReflectionService reflectionService;

    @InjectMocks
    public LogService logService;

    @InjectMocks
    public RegexService regexService;

    @InjectMocks
    private MongoService mongoService;

    @Mock
    private MongoOperations mongoOp;

    private String senza1;

    private String senza2;

    private String senza3;

    private String senza4;

    private String senza5;

    private String senza6;

    private String con1;

    private String con2;

    private String con3;

    private String con4;

    private String con5;

    private String con6;

    private WrapLista wrapLista;

    private WrapLista wrapListaSenza1;

    private WrapLista wrapListaSenza2;

    private WrapLista wrapListaSenza3;

    private WrapLista wrapListaCon1;

    private WrapLista wrapListaCon2;

    private WrapLista wrapListaCon3;

    //--biografie
    private Stream<Bio> biografie() {
        return Stream.of(
                creaBio("Ray Felix"),
                creaBio("Roberto Rullo"),
                creaBio("Stanley Adams (attore)"),
                creaBio("Jameson Adams"),
                creaBio("Marianna Saltini"),
                creaBio("Giuseppe Marchetti"),
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
        super.clazz = DidascaliaService.class;

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
        MockitoAnnotations.openMocks(genereBackend);
        MockitoAnnotations.openMocks(attSingolareBackend);
        MockitoAnnotations.openMocks(annotationService);
        MockitoAnnotations.openMocks(reflectionService);

        service.textService = textService;
        service.wikiUtility = wikiUtility;
        service.genereBackend = genereBackend;
        fileService.textService = textService;
        mongoService.textService = textService;
        mongoService.fileService = fileService;
        wikiUtility.textService = textService;
        wikiUtility.logService = logService;
        wikiUtility.regexService = regexService;
        logService.textService = textService;
        preferenceService.preferenzaBackend = preferenzaBackend;
        preferenzaBackend.mongoService = mongoService;
        genereBackend.textService = textService;
        genereBackend.attSingolareBackend = attSingolareBackend;
        genereBackend.reflectionService = reflectionService;
        genereBackend.annotationService = annotationService;
        genereBackend.mongoService = mongoService;
        genereBackend.bioBackend = bioBackend;
        annotationService.textService = textService;
        annotationService.reflectionService = reflectionService;
        attSingolareBackend.annotationService = annotationService;
        attSingolareBackend.reflectionService = reflectionService;
        attSingolareBackend.textService = textService;
        attSingolareBackend.mongoService = mongoService;
        preferenceService.textService = textService;

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
        this.senza1 = VUOTA;
        this.senza2 = VUOTA;
        this.senza3 = VUOTA;
        this.senza4 = VUOTA;
        this.senza5 = VUOTA;
        this.senza6 = VUOTA;
        this.con1 = VUOTA;
        this.con2 = VUOTA;
        this.con3 = VUOTA;
        this.con4 = VUOTA;
        this.con5 = VUOTA;
        this.con6 = VUOTA;
        this.wrapLista = null;
        this.wrapListaCon1 = null;
        this.wrapListaCon2 = null;
        this.wrapListaCon3 = null;
        this.wrapListaSenza1 = null;
        this.wrapListaSenza2 = null;
        this.wrapListaSenza3 = null;
    }

    @Test
    @Order(1)
    @DisplayName("1 - costruttore")
    void costruttoreBase() {
        System.out.println(("1 - Costruttore base"));
        System.out.println(VUOTA);

        System.out.println(String.format("La classe [%s] è un SINGLETON (service) ed ha un costruttore SENZA parametri", clazz != null ? clazz.getSimpleName() : VUOTA));
        System.out.println(String.format("Viene creata da SpringBoot", clazz != null ? clazz.getSimpleName() : VUOTA));
    }

    @Test
    @Order(2)
    @DisplayName("2 - getBean")
    void getBean() {
        System.out.println(("2 - getBean"));
        System.out.println(VUOTA);

        System.out.println(String.format("La classe [%s] è un SINGLETON (service)", clazz != null ? clazz.getSimpleName() : VUOTA));
        System.out.println(String.format("Viene creata da SpringBoot", clazz != null ? clazz.getSimpleName() : VUOTA));
        System.out.println(String.format("Non si può usare appContext.getBean(%s.class)", clazz != null ? clazz.getSimpleName() : VUOTA));
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
    @DisplayName("41 - luogoNatoAnno")
        //--biografia
    void luogoNatoAnno(final Bio bio) {
        ottenuto = service.luogoNatoAnno(bio);
        senza1 = service.luogoNatoAnno(bio, AETypeLink.linkLista, false);
        senza2 = service.luogoNatoAnno(bio, AETypeLink.linkVoce, false);
        senza3 = service.luogoNatoAnno(bio, AETypeLink.nessunLink, false);

        con1 = service.luogoNatoAnno(bio, AETypeLink.linkLista, true);
        con2 = service.luogoNatoAnno(bio, AETypeLink.linkVoce, true);
        con3 = service.luogoNatoAnno(bio, AETypeLink.nessunLink, true);

        System.out.println(VUOTA);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(("41 - luogoNatoAnno 7 possibilità"));
        icona(ottenuto, senza1, senza2, senza3, con1, con2, con3);
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(51)
    @DisplayName("51 - luogoMortoAnno")
        //--biografia
    void luogoMortoAnno(final Bio bio) {
        System.out.println(("51 - luogoMortoAnno"));
        System.out.println(VUOTA);

        ottenuto = service.luogoMortoAnno(bio);
        senza1 = service.luogoMortoAnno(bio, AETypeLink.linkLista, false);
        senza2 = service.luogoMortoAnno(bio, AETypeLink.linkVoce, false);
        senza3 = service.luogoMortoAnno(bio, AETypeLink.nessunLink, false);

        con1 = service.luogoMortoAnno(bio, AETypeLink.linkLista, true);
        con2 = service.luogoMortoAnno(bio, AETypeLink.linkVoce, true);
        con3 = service.luogoMortoAnno(bio, AETypeLink.nessunLink, true);

        System.out.println(VUOTA);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(("51 - luogoMortoAnno 7 possibilità"));
        icona(ottenuto, senza1, senza2, senza3, con1, con2, con3);
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(61)
    @DisplayName("61 - luogoNatoMorto")
        //--biografia
    void luogoNatoMorto(final Bio bio) {
        ottenuto = service.luogoNatoMorto(bio);
        senza1 = service.luogoNatoMorto(bio, AETypeLink.linkLista, false);
        senza2 = service.luogoNatoMorto(bio, AETypeLink.linkVoce, false);
        senza3 = service.luogoNatoMorto(bio, AETypeLink.nessunLink, false);

        con1 = service.luogoNatoMorto(bio, AETypeLink.linkLista, true);
        con2 = service.luogoNatoMorto(bio, AETypeLink.linkVoce, true);
        con3 = service.luogoNatoMorto(bio, AETypeLink.nessunLink, true);

        System.out.println(VUOTA);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(("61 - luogoNatoMorto 7 possibilità"));
        icona(ottenuto, senza1, senza2, senza3, con1, con2, con3);
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(71)
    @DisplayName("71 - lista")
        //--biografia
    void lista(final Bio bio) {
        ottenuto = service.lista(bio);
        senza1 = service.lista(bio, AETypeLink.linkLista, false);
        senza2 = service.lista(bio, AETypeLink.linkVoce, false);
        senza3 = service.lista(bio, AETypeLink.nessunLink, false);

        con1 = service.lista(bio, AETypeLink.linkLista, true);
        con2 = service.lista(bio, AETypeLink.linkVoce, true);
        con3 = service.lista(bio, AETypeLink.nessunLink, true);

        ottenuto2 = service.lista(bio, true);
        assertEquals(ottenuto, ottenuto2);
        ottenuto3 = service.lista(bio, false);
        assertNotEquals(ottenuto, ottenuto3);

        ottenuto4 = service.lista(bio, AETypeLink.linkLista);
        assertEquals(ottenuto, ottenuto4);

        System.out.println(VUOTA);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(("71 - lista 7 possibilità"));
        icona(ottenuto, senza1, senza2, senza3, con1, con2, con3);
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(81)
    @DisplayName("81 - getWrapNomi")
        //--biografia
    void getWrapNomi(final Bio bio) {
        wrapLista = service.getWrapNomi(bio);

        wrapListaCon1 = service.getWrapNomi(bio, AETypeLink.linkLista, true);
        wrapListaCon2 = service.getWrapNomi(bio, AETypeLink.linkVoce, true);
        wrapListaCon3 = service.getWrapNomi(bio, AETypeLink.nessunLink, true);

        wrapListaSenza1 = service.getWrapNomi(bio, AETypeLink.linkLista, false);
        wrapListaSenza2 = service.getWrapNomi(bio, AETypeLink.linkVoce, false);
        wrapListaSenza3 = service.getWrapNomi(bio, AETypeLink.nessunLink, false);

        System.out.println(VUOTA);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(String.format("81 - getWrapNomi di %s 7 possibilità", bio.wikiTitle));
        printWrap(wrapLista, wrapListaSenza1, wrapListaSenza2, wrapListaSenza3, wrapListaCon1, wrapListaCon2, wrapListaCon3);
    }


    protected Bio creaBio(String wikiTitle) {
        Bio beanBio = null;
        MongoCollection<Document> collection;
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

    protected void printBio(Bio bio) {
        System.out.println(String.format("wikiTitle: %s", bio.wikiTitle));
        System.out.println(String.format("pageId: %s", bio.pageId));
        System.out.println(String.format("nome: %s", textService.isValid(bio.nome) ? bio.nome : VUOTA));
        System.out.println(String.format("cognome: %s", textService.isValid(bio.cognome) ? bio.cognome : VUOTA));
        System.out.println(String.format("sesso: %s", textService.isValid(bio.sesso) ? bio.sesso : VUOTA));
        System.out.println(String.format("luogoNato: %s", textService.isValid(bio.luogoNato) ? bio.luogoNato : VUOTA));
        System.out.println(String.format("luogoNatoLink: %s", textService.isValid(bio.luogoNatoLink) ? bio.luogoNatoLink : VUOTA));
        System.out.println(String.format("giornoNato: %s", textService.isValid(bio.giornoNato) ? bio.giornoNato : VUOTA));
        System.out.println(String.format("annoNato: %s", textService.isValid(bio.annoNato) ? bio.annoNato : VUOTA));
        System.out.println(String.format("luogoMorto: %s", textService.isValid(bio.luogoMorto) ? bio.luogoMorto : VUOTA));
        System.out.println(String.format("luogoMortoLink: %s", textService.isValid(bio.luogoMortoLink) ? bio.luogoMortoLink : VUOTA));
        System.out.println(String.format("giornoMorto: %s", textService.isValid(bio.giornoMorto) ? bio.giornoMorto : VUOTA));
        System.out.println(String.format("annoMorto: %s", textService.isValid(bio.annoMorto) ? bio.annoMorto : VUOTA));
        System.out.println(String.format("attivita: %s", textService.isValid(bio.attivita) ? bio.attivita : VUOTA));
        System.out.println(String.format("attivita2: %s", textService.isValid(bio.attivita2) ? bio.attivita2 : VUOTA));
        System.out.println(String.format("attivita3: %s", textService.isValid(bio.attivita3) ? bio.attivita3 : VUOTA));
        System.out.println(String.format("nazionalita: %s", textService.isValid(bio.nazionalita) ? bio.nazionalita : VUOTA));
    }


    protected void icona(String standard, String senza1, String senza2, String senza3, String con1, String con2, String con3) {

        System.out.println(VUOTA);
        System.out.println("Standard (base)");
        System.out.println(String.format("%s%s%s", "WPref.linkCrono - WPref.usaSimboliCrono - senzaParentesi", FORWARD, standard));

        System.out.println(VUOTA);
        System.out.println("Senza icona");
        System.out.println(String.format("%s%s%s", "linkLista (base)", FORWARD, senza1));
        System.out.println(String.format("%s%s%s", "linkVoce", FORWARD, senza2));
        System.out.println(String.format("%s%s%s", "nessunLink", FORWARD, senza3));

        System.out.println(VUOTA);
        System.out.println("Con icona");
        System.out.println(String.format("%s%s%s", "linkLista (base)", FORWARD, con1));
        System.out.println(String.format("%s%s%s", "linkVoce", FORWARD, con2));
        System.out.println(String.format("%s%s%s", "nessunLink", FORWARD, con3));
    }


    protected void printWrap(WrapLista standard, WrapLista senza1, WrapLista senza2, WrapLista senza3, WrapLista con1, WrapLista con2, WrapLista con3) {

        System.out.println(String.format("Usa: %s%s", "WPref.linkParagrafiNomi - WPref.linkCrono - WPref.usaSimboliCrono - conParentesi", FORWARD));
        System.out.println(VUOTA);
        System.out.println(String.format("%s%s%s%s", "Standard (base): Con icona", SEP, "linkLista", FORWARD));
        printWrap(standard);

        System.out.println(VUOTA);
        System.out.println(String.format("%s%s%s%s", "Con icona", SEP, "linkLista (base)", FORWARD));
        printWrap(con1);
        System.out.println(String.format("%s%s%s%s", "Con icona", SEP, "linkVoce", FORWARD));
        printWrap(con2);
        System.out.println(String.format("%s%s%s%s", "Con icona", SEP, "nessunLink", FORWARD));
        printWrap(con3);

        System.out.println(VUOTA);
        System.out.println(String.format("%s%s%s%s", "Senza icona", SEP, "linkLista (base)", FORWARD));
        printWrap(senza1);
        System.out.println(String.format("%s%s%s%s", "Senza icona", SEP, "linkVoce", FORWARD));
        printWrap(senza2);
        System.out.println(String.format("%s%s%s%s", "Senza icona", SEP, "nessunLink", FORWARD));
        printWrap(senza3);
    }

    protected void printWrap(WrapLista wrap) {
        if (wrap == null) {
            return;
        }

        System.out.println(String.format("Titolo paragrafo: %s", textService.isValid(wrap.titoloParagrafo) ? wrap.titoloParagrafo : VUOTA));
        System.out.println(String.format("Paragrafo link: %s", textService.isValid(wrap.titoloParagrafoLink) ? wrap.titoloParagrafoLink : VUOTA));
        System.out.println(String.format("Sottoparagrafo: %s", textService.isValid(wrap.titoloSottoParagrafo) ? wrap.titoloSottoParagrafo : VUOTA));
        System.out.println(String.format("Ordinamento: %s", textService.isValid(wrap.ordinamento) ? wrap.ordinamento : VUOTA));
        System.out.println(String.format("Breve: %s", textService.isValid(wrap.didascaliaBreve) ? wrap.didascaliaBreve : VUOTA));
        System.out.println(String.format("Lunga: %s", textService.isValid(wrap.didascaliaLunga) ? wrap.didascaliaLunga : VUOTA));
        System.out.println(VUOTA);
    }

}
