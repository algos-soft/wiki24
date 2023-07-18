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
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.core.*;

import java.util.stream.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 18-Jul-2023
 * Time: 07:20
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("WikiUtility Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WikiUtilityTest extends WikiTest {

    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    @InjectMocks
    private WikiUtility service;

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

    private String ottenuto4;

    private String ottenuto5;

    private String ottenuto6;

    //--biografie
    private Stream<Bio> biografie() {
        return Stream.of(
                creaBio("Ray Felix"),
                creaBio("Roberto Rullo"),
                creaBio("Stanley Adams (attore)"),
                creaBio("Jameson Adams"),
                creaBio("Arcangelo Scacchi"),
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
        super.clazz = WikiUtility.class;
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

        //--reindirizzo l'istanza
        service = wikiUtility;
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
    @Order(41)
    @DisplayName("41 - giornoNatoTesta")
        //--biografia
    void giornoNatoTesta(final Bio bio) {
        ottenuto = service.giornoNatoTesta(bio, null);
        ottenuto2 = service.giornoNatoTesta(bio, AETypeLink.linkVoce);
        ottenuto3 = service.giornoNatoTesta(bio, AETypeLink.nessunLink);

        System.out.println(VUOTA);
        System.out.println(("41 - giornoNatoTesta 3 possibilità"));
        System.out.println(VUOTA);

        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(String.format("%s%s%s", "linkLista (base)", FORWARD, ottenuto));
        System.out.println(String.format("%s%s%s", "linkVoce", FORWARD, ottenuto2));
        System.out.println(String.format("%s%s%s", "nessunLink", FORWARD, ottenuto3));
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(51)
    @DisplayName("51 - giornoMortoTesta")
        //--biografia
    void giornoMortoTesta(final Bio bio) {
        ottenuto = service.giornoMortoTesta(bio, null);
        ottenuto2 = service.giornoMortoTesta(bio, AETypeLink.linkVoce);
        ottenuto3 = service.giornoMortoTesta(bio, AETypeLink.nessunLink);

        System.out.println(VUOTA);
        System.out.println(("51 - giornoMortoTesta 3 possibilità"));
        System.out.println(VUOTA);

        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(String.format("%s%s%s", "linkLista (base)", FORWARD, ottenuto));
        System.out.println(String.format("%s%s%s", "linkVoce", FORWARD, ottenuto2));
        System.out.println(String.format("%s%s%s", "nessunLink", FORWARD, ottenuto3));
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(61)
    @DisplayName("61 - annoNatoTesta")
        //--biografia
    void annoNatoTesta(final Bio bio) {
        ottenuto = service.annoNatoTesta(bio, null);
        ottenuto2 = service.annoNatoTesta(bio, AETypeLink.linkVoce);
        ottenuto3 = service.annoNatoTesta(bio, AETypeLink.nessunLink);

        System.out.println(VUOTA);
        System.out.println(("61 - annoNatoTesta 3 possibilità"));
        System.out.println(VUOTA);

        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(String.format("%s%s%s", "linkLista (base)", FORWARD, ottenuto));
        System.out.println(String.format("%s%s%s", "linkVoce", FORWARD, ottenuto2));
        System.out.println(String.format("%s%s%s", "nessunLink", FORWARD, ottenuto3));
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(71)
    @DisplayName("71 - annoMortoTesta")
        //--biografia
    void annoMortoTesta(final Bio bio) {
        ottenuto = service.annoMortoTesta(bio, null);
        ottenuto2 = service.annoMortoTesta(bio, AETypeLink.linkVoce);
        ottenuto3 = service.annoMortoTesta(bio, AETypeLink.nessunLink);

        System.out.println(VUOTA);
        System.out.println(("71 - annoMortoTesta 3 possibilità"));
        System.out.println(VUOTA);

        printBio(bio);
        System.out.println(VUOTA);
        System.out.println(String.format("%s%s%s", "linkLista (base)", FORWARD, ottenuto));
        System.out.println(String.format("%s%s%s", "linkVoce", FORWARD, ottenuto2));
        System.out.println(String.format("%s%s%s", "nessunLink", FORWARD, ottenuto3));
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(141)
    @DisplayName("141 - giornoNatoCoda")
        //--biografia
    void giornoNatoCoda(final Bio bio) {
        ottenuto = service.giornoNatoCoda(bio, null, false);
        ottenuto2 = service.giornoNatoCoda(bio, AETypeLink.linkVoce, false);
        ottenuto3 = service.giornoNatoCoda(bio, AETypeLink.nessunLink, false);

        ottenuto4 = service.giornoNatoCoda(bio, null, true);
        ottenuto5 = service.giornoNatoCoda(bio, AETypeLink.linkVoce, true);
        ottenuto6 = service.giornoNatoCoda(bio, AETypeLink.nessunLink, true);

        System.out.println(VUOTA);
        System.out.println(("141 - giornoNatoCoda 6 possibilità"));
        System.out.println(VUOTA);

        printBio(bio);
        System.out.println(VUOTA);
        System.out.println("Senza parentesi");
        System.out.println(String.format("%s%s%s", "linkLista (base)", FORWARD, ottenuto));
        System.out.println(String.format("%s%s%s", "linkVoce", FORWARD, ottenuto2));
        System.out.println(String.format("%s%s%s", "nessunLink", FORWARD, ottenuto3));

        System.out.println(VUOTA);
        System.out.println("Con parentesi");
        System.out.println(String.format("%s%s%s", "linkLista (base)", FORWARD, ottenuto4));
        System.out.println(String.format("%s%s%s", "linkVoce", FORWARD, ottenuto5));
        System.out.println(String.format("%s%s%s", "nessunLink", FORWARD, ottenuto6));
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(151)
    @DisplayName("151 - giornoMortoCoda")
        //--biografia
    void giornoMortoCoda(final Bio bio) {
        ottenuto = service.giornoMortoCoda(bio, null, false);
        ottenuto2 = service.giornoMortoCoda(bio, AETypeLink.linkVoce, false);
        ottenuto3 = service.giornoMortoCoda(bio, AETypeLink.nessunLink, false);

        ottenuto4 = service.giornoMortoCoda(bio, null, true);
        ottenuto5 = service.giornoMortoCoda(bio, AETypeLink.linkVoce, true);
        ottenuto6 = service.giornoMortoCoda(bio, AETypeLink.nessunLink, true);

        System.out.println(VUOTA);
        System.out.println(("151 - giornoMortoCoda 6 possibilità"));
        System.out.println(VUOTA);

        printBio(bio);
        System.out.println(VUOTA);
        System.out.println("Senza parentesi");
        System.out.println(String.format("%s%s%s", "linkLista (base)", FORWARD, ottenuto));
        System.out.println(String.format("%s%s%s", "linkVoce", FORWARD, ottenuto2));
        System.out.println(String.format("%s%s%s", "nessunLink", FORWARD, ottenuto3));

        System.out.println(VUOTA);
        System.out.println("Con parentesi");
        System.out.println(String.format("%s%s%s", "linkLista (base)", FORWARD, ottenuto4));
        System.out.println(String.format("%s%s%s", "linkVoce", FORWARD, ottenuto5));
        System.out.println(String.format("%s%s%s", "nessunLink", FORWARD, ottenuto6));
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(161)
    @DisplayName("161 - annoNatoCoda")
        //--biografia
    void annoNatoCoda(final Bio bio) {
        ottenuto = service.annoNatoCoda(bio, null,false);
        ottenuto2 = service.annoNatoCoda(bio, AETypeLink.linkVoce, false);
        ottenuto3 = service.annoNatoCoda(bio, AETypeLink.nessunLink, false);

        ottenuto4 = service.annoNatoCoda(bio, null, true);
        ottenuto5 = service.annoNatoCoda(bio, AETypeLink.linkVoce, true);
        ottenuto6 = service.annoNatoCoda(bio, AETypeLink.nessunLink, true);

        System.out.println(VUOTA);
        System.out.println(("161 - annoNatoCoda 6 possibilità"));
        System.out.println(VUOTA);

        printBio(bio);
        System.out.println(VUOTA);
        System.out.println("Senza parentesi");
        System.out.println(String.format("%s%s%s", "linkLista (base)", FORWARD, ottenuto));
        System.out.println(String.format("%s%s%s", "linkVoce", FORWARD, ottenuto2));
        System.out.println(String.format("%s%s%s", "nessunLink", FORWARD, ottenuto3));

        System.out.println(VUOTA);
        System.out.println("Con parentesi");
        System.out.println(String.format("%s%s%s", "linkLista (base)", FORWARD, ottenuto4));
        System.out.println(String.format("%s%s%s", "linkVoce", FORWARD, ottenuto5));
        System.out.println(String.format("%s%s%s", "nessunLink", FORWARD, ottenuto6));
    }


    @ParameterizedTest
    @MethodSource(value = "biografie")
    @Order(171)
    @DisplayName("171 - annoMortoCoda")
        //--biografia
    void annoMortoCoda(final Bio bio) {
        ottenuto = service.annoMortoCoda(bio, null, false);
        ottenuto2 = service.annoMortoCoda(bio, AETypeLink.linkVoce, false);
        ottenuto3 = service.annoMortoCoda(bio, AETypeLink.nessunLink, false);

        ottenuto4 = service.annoMortoCoda(bio, null, true);
        ottenuto5 = service.annoMortoCoda(bio, AETypeLink.linkVoce, true);
        ottenuto6 = service.annoMortoCoda(bio, AETypeLink.nessunLink, true);

        System.out.println(VUOTA);
        System.out.println(("171 - annoMortoCoda 6 possibilità"));
        System.out.println(VUOTA);

        printBio(bio);
        System.out.println(VUOTA);
        System.out.println("Senza parentesi");
        System.out.println(String.format("%s%s%s", "linkLista (base)", FORWARD, ottenuto));
        System.out.println(String.format("%s%s%s", "linkVoce", FORWARD, ottenuto2));
        System.out.println(String.format("%s%s%s", "nessunLink", FORWARD, ottenuto3));

        System.out.println(VUOTA);
        System.out.println("Con parentesi");
        System.out.println(String.format("%s%s%s", "linkLista (base)", FORWARD, ottenuto4));
        System.out.println(String.format("%s%s%s", "linkVoce", FORWARD, ottenuto5));
        System.out.println(String.format("%s%s%s", "nessunLink", FORWARD, ottenuto6));
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
