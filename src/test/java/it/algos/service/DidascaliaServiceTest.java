package it.algos.service;

import com.mongodb.*;
import com.mongodb.client.*;
import it.algos.base.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.interfaces.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.utility.preferenza.*;
import it.algos.vaad24.backend.service.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.anno.*;
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

    private static String CON = "con parentesi";

    private static String CON_ICONA = "Con icona";

    private static String SENZA = "senza parentesi";

    private static String SENZA_ICONA = "Senza icona";

    private Bio bioBean;

    private static String tagNato;

    private static String tagMorto;

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
    private AnnoBackend annoBackend;

    @InjectMocks
    private AnnoWikiBackend annoWikiBackend;

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

    private WrapLista wrapCon1;

    private WrapLista wrapCon2;

    private WrapLista wrapCon3;

    private WrapLista wrapCon4;

    private WrapLista wrapCon5;

    private WrapLista wrapCon6;

    private WrapLista wrapCon7;

    private WrapLista wrapCon8;

    private WrapLista wrapCon9;

    private WrapLista wrapSenza1;

    private WrapLista wrapSenza2;

    private WrapLista wrapSenza3;

    private WrapLista wrapSenza4;

    private WrapLista wrapSenza5;

    private WrapLista wrapSenza6;

    private WrapLista wrapSenza7;

    private WrapLista wrapSenza8;

    private WrapLista wrapSenza9;


    //--biografie
    private Stream<Bio> biografie() {
        return Stream.of(
                creaBio("Johann Schweikhard von Kronberg"),
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
        MockitoAnnotations.openMocks(annoBackend);
        MockitoAnnotations.openMocks(annoWikiBackend);

        service.textService = textService;
        service.wikiUtility = wikiUtility;
        service.genereBackend = genereBackend;
        fileService.textService = textService;
        mongoService.textService = textService;
        mongoService.fileService = fileService;
        wikiUtility.textService = textService;
        wikiUtility.logService = logService;
        wikiUtility.regexService = regexService;
        wikiUtility.annoBackend = annoBackend;
        wikiUtility.annoWikiBackend = annoWikiBackend;
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
        annoBackend.annotationService = annotationService;
        annoBackend.reflectionService = reflectionService;
        annoBackend.textService = textService;
        annoBackend.mongoService = mongoService;
        annoWikiBackend.annotationService = annotationService;
        annoWikiBackend.reflectionService = reflectionService;
        annoWikiBackend.textService = textService;
        annoWikiBackend.mongoService = mongoService;

        for (AIGenPref pref : Pref.values()) {
            pref.setPreferenceService(preferenceService);
        }
        for (AIGenPref pref : WPref.values()) {
            pref.setPreferenceService(preferenceService);
        }

        bioBean = creaBio("Luigi Giura");
        assertNotNull(bioBean);

        tagNato = WPref.simboloNato.getStr();
        assertTrue(textService.isValid(tagNato));
        tagMorto = WPref.simboloMorto.getStr();
        assertTrue(textService.isValid(tagMorto));
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
        this.wrapCon1 = null;
        this.wrapCon2 = null;
        this.wrapCon3 = null;
        this.wrapCon4 = null;
        this.wrapCon5 = null;
        this.wrapCon6 = null;
        this.wrapCon7 = null;
        this.wrapCon8 = null;
        this.wrapCon9 = null;
        this.wrapSenza1 = null;
        this.wrapSenza2 = null;
        this.wrapSenza3 = null;
        this.wrapSenza4 = null;
        this.wrapSenza5 = null;
        this.wrapSenza6 = null;
        this.wrapSenza7 = null;
        this.wrapSenza8 = null;
        this.wrapSenza9 = null;
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

        con1 = service.luogoNatoAnno(bio, AETypeLink.linkLista, true);
        con2 = service.luogoNatoAnno(bio, AETypeLink.linkVoce, true);
        con3 = service.luogoNatoAnno(bio, AETypeLink.nessunLink, true);

        senza1 = service.luogoNatoAnno(bio, AETypeLink.linkLista, false);
        senza2 = service.luogoNatoAnno(bio, AETypeLink.linkVoce, false);
        senza3 = service.luogoNatoAnno(bio, AETypeLink.nessunLink, false);

        System.out.println(VUOTA);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(String.format("41 - luogoNatoAnno di '%s', 7 possibilità", bio.wikiTitle));
        icona(ottenuto, con1, con2, con3, senza1, senza2, senza3, SENZA);
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

        con1 = service.luogoMortoAnno(bio, AETypeLink.linkLista, true);
        con2 = service.luogoMortoAnno(bio, AETypeLink.linkVoce, true);
        con3 = service.luogoMortoAnno(bio, AETypeLink.nessunLink, true);

        senza1 = service.luogoMortoAnno(bio, AETypeLink.linkLista, false);
        senza2 = service.luogoMortoAnno(bio, AETypeLink.linkVoce, false);
        senza3 = service.luogoMortoAnno(bio, AETypeLink.nessunLink, false);

        System.out.println(VUOTA);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(String.format("51 - luogoMortoAnno di '%s', 7 possibilità", bio.wikiTitle));
        icona(ottenuto, con1, con2, con3, senza1, senza2, senza3, SENZA);
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(61)
    @DisplayName("61 - luogoNatoMorto")
        //--biografia
    void luogoNatoMorto(final Bio bio) {
        ottenuto = service.luogoNatoMorto(bio);

        con1 = service.luogoNatoMorto(bio, AETypeLink.linkLista, true);
        con2 = service.luogoNatoMorto(bio, AETypeLink.linkVoce, true);
        con3 = service.luogoNatoMorto(bio, AETypeLink.nessunLink, true);

        senza1 = service.luogoNatoMorto(bio, AETypeLink.linkLista, false);
        senza2 = service.luogoNatoMorto(bio, AETypeLink.linkVoce, false);
        senza3 = service.luogoNatoMorto(bio, AETypeLink.nessunLink, false);

        System.out.println(VUOTA);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(String.format("61 - luogoNatoMorto di '%s', 7 possibilità", bio.wikiTitle));
        icona(ottenuto, con1, con2, con3, senza1, senza2, senza3, CON);
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(71)
    @DisplayName("71 - lista")
        //--biografia
    void lista(final Bio bio) {
        ottenuto = service.lista(bio);

        con1 = service.lista(bio, AETypeLink.linkLista, true);
        con2 = service.lista(bio, AETypeLink.linkVoce, true);
        con3 = service.lista(bio, AETypeLink.nessunLink, true);

        senza1 = service.lista(bio, AETypeLink.linkLista, false);
        senza2 = service.lista(bio, AETypeLink.linkVoce, false);
        senza3 = service.lista(bio, AETypeLink.nessunLink, false);

        ottenuto2 = service.lista(bio, true);
        assertEquals(ottenuto, ottenuto2);
        ottenuto3 = service.lista(bio, false);
        assertNotEquals(ottenuto, ottenuto3);

        ottenuto4 = service.lista(bio, AETypeLink.linkLista);
        assertEquals(ottenuto, ottenuto4);

        System.out.println(VUOTA);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(String.format("71 - lista di '%s', 7 possibilità", bio.wikiTitle));
        icona(ottenuto, con1, con2, con3, senza1, senza2, senza3, CON);
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(120)
    @DisplayName("120 - getWrap per giornoNato STANDARD con linkParagrafi=nessunLink")
        //--biografie
    void getWrapGiornoNato(final Bio bio) {
        wrapLista = service.getWrap(bio, AETypeLista.giornoNascita);
        assertTrue(checkIcona(wrapLista, true));
        assertTrue(checkParagrafo(wrapLista, AETypeLink.nessunLink));
        assertTrue(checkCrono(wrapLista, AETypeLink.linkLista));

        System.out.println(VUOTA);
        System.out.println("120 - getWrap per giornoNato STANDARD con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true");
        System.out.println(String.format("120 - getWrap di '%s' per la pagina [%s]", bio.wikiTitle, wikiUtility.wikiTitle(AETypeLista.giornoNascita, bio.giornoNato)));
        System.out.println(VUOTA);
        printWrap(wrapLista);
    }

    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(160)
    @DisplayName("160 - getWrap per nomi STANDARD con linkParagrafi=nessunLink")
        //--biografie
    void getWrapxNomi(final Bio bio) {
        wrapLista = service.getWrap(bio, AETypeLista.nomi);
        assertTrue(checkIcona(wrapLista, true));
        assertTrue(checkParagrafo(wrapLista, AETypeLink.nessunLink));
        assertTrue(checkCrono(wrapLista, AETypeLink.linkLista));

        System.out.println(VUOTA);
        System.out.println("160 - getWrap per nomi STANDARD con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true");
        System.out.println(String.format("120 - getWrap di %s per la pagina %s", bio.wikiTitle, PATH_NOMI + bio.nome));
        System.out.println(VUOTA);
        printWrap(wrapLista);
    }

    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(170)
    @DisplayName("170 - getWrap per cognomi STANDARD con linkParagrafi=nessunLink")
        //--biografie
    void getWrapxCognomi(final Bio bio) {
        wrapLista = service.getWrap(bio, AETypeLista.cognomi);
        assertTrue(checkIcona(wrapLista, true));
        assertTrue(checkParagrafo(wrapLista, AETypeLink.nessunLink));
        assertTrue(checkCrono(wrapLista, AETypeLink.linkLista));

        System.out.println(VUOTA);
        System.out.println("170 - getWrap per cognomi STANDARD con linkParagrafi=nessunLink e linkCrono=linkLista e usaIcona=true");
        System.out.println(String.format("121 - getWrap di %s per la pagina %s", bio.wikiTitle, PATH_COGNOMI + bio.cognome));
        System.out.println(VUOTA);
        printWrap(wrapLista);
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(180)
    @DisplayName("180 - getWrapNomi (con alternative)")
        //--biografia
    void getWrapNomi(final Bio bio) {
        wrapLista = service.getWrapNomi(bio);
        assertTrue(checkIcona(wrapLista, true));
        assertTrue(checkParagrafo(wrapLista, AETypeLink.nessunLink));
        assertTrue(checkCrono(wrapLista, AETypeLink.linkLista));

        wrapCon1 = service.getWrapNomi(bio, AETypeLink.linkLista, AETypeLink.linkLista, true);
        assertTrue(checkIcona(wrapCon1, true));
        assertTrue(checkParagrafo(wrapCon1, AETypeLink.linkLista));
        assertTrue(checkCrono(wrapCon1, AETypeLink.linkLista));

        wrapCon2 = service.getWrapNomi(bio, AETypeLink.linkLista, AETypeLink.linkVoce, true);
        assertTrue(checkIcona(wrapCon2, true));
        assertTrue(checkParagrafo(wrapCon2, AETypeLink.linkLista));
        assertTrue(checkCrono(wrapCon2, AETypeLink.linkVoce));

        wrapCon3 = service.getWrapNomi(bio, AETypeLink.linkLista, AETypeLink.nessunLink, true);
        assertTrue(checkIcona(wrapCon3, true));
        assertTrue(checkParagrafo(wrapCon3, AETypeLink.linkLista));
        assertTrue(checkCrono(wrapCon3, AETypeLink.nessunLink));

        wrapCon4 = service.getWrapNomi(bio, AETypeLink.linkVoce, AETypeLink.linkLista, true);
        assertTrue(checkIcona(wrapCon4, true));
        assertTrue(checkParagrafo(wrapCon4, AETypeLink.linkVoce));
        assertTrue(checkCrono(wrapCon4, AETypeLink.linkLista));

        wrapCon5 = service.getWrapNomi(bio, AETypeLink.linkVoce, AETypeLink.linkVoce, true);
        assertTrue(checkIcona(wrapCon5, true));
        assertTrue(checkParagrafo(wrapCon5, AETypeLink.linkVoce));
        assertTrue(checkCrono(wrapCon5, AETypeLink.linkVoce));

        wrapCon6 = service.getWrapNomi(bio, AETypeLink.linkVoce, AETypeLink.nessunLink, true);
        assertTrue(checkIcona(wrapCon6, true));
        assertTrue(checkParagrafo(wrapCon6, AETypeLink.linkVoce));
        assertTrue(checkCrono(wrapCon6, AETypeLink.nessunLink));

        wrapCon7 = service.getWrapNomi(bio, AETypeLink.nessunLink, AETypeLink.linkLista, true);
        assertTrue(checkIcona(wrapCon7, true));
        assertTrue(checkParagrafo(wrapCon7, AETypeLink.nessunLink));
        assertTrue(checkCrono(wrapCon7, AETypeLink.linkLista));

        wrapCon8 = service.getWrapNomi(bio, AETypeLink.nessunLink, AETypeLink.linkVoce, true);
        assertTrue(checkIcona(wrapCon8, true));
        assertTrue(checkParagrafo(wrapCon8, AETypeLink.nessunLink));
        assertTrue(checkCrono(wrapLista, AETypeLink.linkVoce));

        wrapCon9 = service.getWrapNomi(bio, AETypeLink.nessunLink, AETypeLink.nessunLink, true);
        assertTrue(checkIcona(wrapCon9, true));
        assertTrue(checkParagrafo(wrapCon9, AETypeLink.nessunLink));
        assertTrue(checkCrono(wrapCon8, AETypeLink.nessunLink));

        wrapSenza1 = service.getWrapNomi(bio, AETypeLink.linkLista, AETypeLink.linkLista, false);
        assertTrue(checkIcona(wrapSenza1, false));
        assertTrue(checkParagrafo(wrapCon1, AETypeLink.linkLista));
        assertTrue(checkCrono(wrapSenza1, AETypeLink.linkLista));

        wrapSenza2 = service.getWrapNomi(bio, AETypeLink.linkLista, AETypeLink.linkVoce, false);
        assertTrue(checkIcona(wrapSenza2, false));
        assertTrue(checkParagrafo(wrapSenza2, AETypeLink.linkLista));
        assertTrue(checkCrono(wrapSenza2, AETypeLink.linkVoce));

        wrapSenza3 = service.getWrapNomi(bio, AETypeLink.linkLista, AETypeLink.nessunLink, false);
        assertTrue(checkIcona(wrapSenza3, false));
        assertTrue(checkParagrafo(wrapSenza3, AETypeLink.linkLista));
        assertTrue(checkCrono(wrapSenza3, AETypeLink.nessunLink));

        wrapSenza4 = service.getWrapNomi(bio, AETypeLink.linkVoce, AETypeLink.linkLista, false);
        assertTrue(checkIcona(wrapSenza4, false));
        assertTrue(checkParagrafo(wrapSenza4, AETypeLink.linkVoce));
        assertTrue(checkCrono(wrapSenza4, AETypeLink.linkLista));

        wrapSenza5 = service.getWrapNomi(bio, AETypeLink.linkVoce, AETypeLink.linkVoce, false);
        assertTrue(checkIcona(wrapSenza5, false));
        assertTrue(checkParagrafo(wrapSenza5, AETypeLink.linkVoce));
        assertTrue(checkCrono(wrapSenza5, AETypeLink.linkVoce));

        wrapSenza6 = service.getWrapNomi(bio, AETypeLink.linkVoce, AETypeLink.nessunLink, false);
        assertTrue(checkIcona(wrapSenza6, false));
        assertTrue(checkParagrafo(wrapSenza6, AETypeLink.linkVoce));
        assertTrue(checkCrono(wrapSenza6, AETypeLink.nessunLink));

        wrapSenza7 = service.getWrapNomi(bio, AETypeLink.nessunLink, AETypeLink.linkLista, false);
        assertTrue(checkIcona(wrapSenza7, false));
        assertTrue(checkParagrafo(wrapSenza7, AETypeLink.nessunLink));
        assertTrue(checkCrono(wrapSenza7, AETypeLink.linkLista));

        wrapSenza8 = service.getWrapNomi(bio, AETypeLink.nessunLink, AETypeLink.linkVoce, false);
        assertTrue(checkIcona(wrapSenza8, false));
        assertTrue(checkParagrafo(wrapSenza8, AETypeLink.nessunLink));
        assertTrue(checkCrono(wrapSenza8, AETypeLink.linkVoce));

        wrapSenza9 = service.getWrapNomi(bio, AETypeLink.nessunLink, AETypeLink.nessunLink, false);
        assertTrue(checkIcona(wrapSenza9, false));
        assertTrue(checkParagrafo(wrapSenza9, AETypeLink.nessunLink));
        assertTrue(checkCrono(wrapSenza9, AETypeLink.nessunLink));

        System.out.println(VUOTA);
        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(String.format("180 - getWrapNomi di %s, 19 possibilità", bio.wikiTitle));
        printWrap(wrapLista, wrapCon1, wrapCon2, wrapCon3, wrapCon4, wrapCon5, wrapCon6, wrapCon7, wrapCon8, wrapCon9, wrapSenza1, wrapSenza2, wrapSenza3, wrapSenza4, wrapSenza5, wrapSenza6, wrapSenza7, wrapSenza8, wrapSenza9);
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


    protected void icona(String standard, String con1, String con2, String con3, String senza1, String senza2, String senza3, String parentesi) {

        System.out.println(VUOTA);
        System.out.println(String.format("WPref.usaSimboliCrono - WPref.linkCrono - %s", parentesi));
        System.out.println(String.format("%s%s%s", "Standard (base): Con icona e linkLista", FORWARD, standard));

        System.out.println(VUOTA);
        System.out.println("Con icona");
        System.out.println(String.format("%s%s%s", "linkLista (base)", FORWARD, con1));
        System.out.println(String.format("%s%s%s", "linkVoce", FORWARD, con2));
        System.out.println(String.format("%s%s%s", "nessunLink", FORWARD, con3));

        System.out.println(VUOTA);
        System.out.println("Senza icona");
        System.out.println(String.format("%s%s%s", "linkLista (base)", FORWARD, senza1));
        System.out.println(String.format("%s%s%s", "linkVoce", FORWARD, senza2));
        System.out.println(String.format("%s%s%s", "nessunLink", FORWARD, senza3));

    }


    protected void printWrap(WrapLista standard, WrapLista con1, WrapLista con2, WrapLista con3, WrapLista con4, WrapLista con5, WrapLista con6, WrapLista con7, WrapLista con8, WrapLista con9, WrapLista senza1, WrapLista senza2, WrapLista senza3, WrapLista senza4, WrapLista senza5, WrapLista senza6, WrapLista senza7, WrapLista senza8, WrapLista senza9) {

        System.out.println(VUOTA);
        System.out.println(String.format("WPref.usaSimboliCrono - WPref.linkParagrafiNomi - WPref.linkCrono"));
        System.out.println(String.format("%s%s", "Standard (base): Con icona e nessunLink (paragrafi) e linkLista (crono)", FORWARD));
        printWrap(standard);

        System.out.println(String.format("%s%s%s%s%s%s", CON_ICONA, SEP, "linkParagrafi=linkLista", SEP, "linkCrono=linkLista", FORWARD));
        printWrap(con1);
        System.out.println(String.format("%s%s%s%s%s%s", CON_ICONA, SEP, "linkParagrafi=linkLista", SEP, "linkCrono=linkVoce", FORWARD));
        printWrap(con2);
        System.out.println(String.format("%s%s%s%s%s%s", CON_ICONA, SEP, "linkParagrafi=linkLista", SEP, "linkCrono=nessunLink", FORWARD));
        printWrap(con3);

        System.out.println(String.format("%s%s%s%s%s%s", CON_ICONA, SEP, "linkParagrafi=linkVoce", SEP, "linkCrono=linkLista", FORWARD));
        printWrap(con4);
        System.out.println(String.format("%s%s%s%s%s%s", CON_ICONA, SEP, "linkParagrafi=linkVoce", SEP, "linkCrono=linkVoce", FORWARD));
        printWrap(con5);
        System.out.println(String.format("%s%s%s%s%s%s", CON_ICONA, SEP, "linkParagrafi=linkVoce", SEP, "linkCrono=nessunLink", FORWARD));
        printWrap(con6);

        System.out.println(String.format("%s%s%s%s%s%s", CON_ICONA, SEP, "linkParagrafi=nessunLink", SEP, "linkCrono=linkLista", FORWARD));
        printWrap(con7);
        System.out.println(String.format("%s%s%s%s%s%s", CON_ICONA, SEP, "linkParagrafi=nessunLink", SEP, "linkCrono=linkVoce", FORWARD));
        printWrap(con8);
        System.out.println(String.format("%s%s%s%s%s%s", CON_ICONA, SEP, "linkParagrafi=nessunLink", SEP, "linkCrono=nessunLink", FORWARD));
        printWrap(con9);

        System.out.println(String.format("%s%s%s%s%s%s", SENZA_ICONA, SEP, "linkParagrafi=linkLista", SEP, "linkCrono=linkLista", FORWARD));
        printWrap(senza1);
        System.out.println(String.format("%s%s%s%s%s%s", SENZA_ICONA, SEP, "linkParagrafi=linkLista", SEP, "linkCrono=linkVoce", FORWARD));
        printWrap(senza2);
        System.out.println(String.format("%s%s%s%s%s%s", SENZA_ICONA, SEP, "linkParagrafi=linkLista", SEP, "linkCrono=nessunLink", FORWARD));
        printWrap(senza3);

        System.out.println(String.format("%s%s%s%s%s%s", SENZA_ICONA, SEP, "linkParagrafi=linkVoce", SEP, "linkCrono=linkLista", FORWARD));
        printWrap(senza4);
        System.out.println(String.format("%s%s%s%s%s%s", SENZA_ICONA, SEP, "linkParagrafi=linkVoce", SEP, "linkCrono=linkVoce", FORWARD));
        printWrap(senza5);
        System.out.println(String.format("%s%s%s%s%s%s", SENZA_ICONA, SEP, "linkParagrafi=linkVoce", SEP, "linkCrono=nessunLink", FORWARD));
        printWrap(senza6);

        System.out.println(String.format("%s%s%s%s%s%s", SENZA_ICONA, SEP, "linkParagrafi=nessunLink", SEP, "linkCrono=linkLista", FORWARD));
        printWrap(senza7);
        System.out.println(String.format("%s%s%s%s%s%s", SENZA_ICONA, SEP, "linkParagrafi=nessunLink", SEP, "linkCrono=linkVoce", FORWARD));
        printWrap(senza8);
        System.out.println(String.format("%s%s%s%s%s%s", SENZA_ICONA, SEP, "linkParagrafi=nessunLink", SEP, "linkCrono=nessunLink", FORWARD));
        printWrap(senza9);

    }

    protected void printWrap(WrapLista wrap) {
        if (wrap == null) {
            return;
        }

        System.out.println(String.format("Titolo paragrafo: %s", textService.isValid(wrap.titoloParagrafo) ? wrap.titoloParagrafo : VUOTA));
        System.out.println(String.format("Paragrafo link: %s", textService.isValid(wrap.titoloParagrafoLink) ? wrap.titoloParagrafoLink : VUOTA));
        System.out.println(String.format("Sottoparagrafo: %s", textService.isValid(wrap.titoloSottoParagrafo) ? wrap.titoloSottoParagrafo : VUOTA));
        System.out.println(String.format("Ordinamento: %s", textService.isValid(wrap.ordinamento) ? wrap.ordinamento : VUOTA));
        System.out.println(String.format("Lista: %s", textService.isValid(wrap.lista) ? wrap.didascaliaBreve : VUOTA));
        System.out.println(String.format("giornoNato: %s", textService.isValid(wrap.giornoNato) ? wrap.giornoNato : VUOTA));
        System.out.println(String.format("giornoMorto: %s", textService.isValid(wrap.giornoMorto) ? wrap.giornoMorto : VUOTA));
        System.out.println(String.format("annoNato: %s", textService.isValid(wrap.annonato) ? wrap.annonato : VUOTA));
        System.out.println(String.format("annoMorto: %s", textService.isValid(wrap.annoMorto) ? wrap.annoMorto : VUOTA));
        System.out.println(VUOTA);
    }

    protected boolean checkIcona(WrapLista wrap, boolean previsto) {
        boolean status = false;
        String target = wrap.lista;

        if (textService.isValid(target)) {
            status = target.contains(tagNato) || target.contains(tagMorto);
        }

        return status == previsto;
    }

    protected boolean checkParagrafo(WrapLista wrap, AETypeLink typeLinkParagrafi) {
        String target = wrap.titoloParagrafoLink;

        return switch (typeLinkParagrafi) {
            case linkLista -> textService.isValid(target) && target.contains(DUE_PUNTI);
            case linkVoce -> textService.isValid(target) && target.contains(DOPPIE_QUADRE_INI) && target.contains(DOPPIE_QUADRE_END);
            case nessunLink -> textService.isEmpty(target);
        };
    }

    protected boolean checkCrono(WrapLista wrap, AETypeLink typeLinkCrono) {
        String target = wrap.lista;
        if (textService.isEmpty(target)) {
            return false;
        }
        boolean esisteNato = textService.isValid(target) && target.contains(service.NATI);
        boolean esisteMorto = textService.isValid(target) && target.contains(service.MORTI);
        boolean mancaNato = !esisteNato;
        boolean mancaMorto = !esisteMorto;
        boolean noneEsisteCrono = mancaNato && mancaMorto;

        boolean status = switch (typeLinkCrono) {
            case linkLista -> textService.isValid(target) && (target.contains(service.NATI) || target.contains(service.MORTI));
            case linkVoce -> textService.isValid(target) && target.contains(DOPPIE_QUADRE_INI) && target.contains(DOPPIE_QUADRE_END);
            case nessunLink -> noneEsisteCrono;
        };
        return status;
    }

}
