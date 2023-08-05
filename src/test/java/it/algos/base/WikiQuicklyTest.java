package it.algos.base;

import com.mongodb.client.*;
import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.packages.utility.logger.*;
import it.algos.vaad24.backend.service.*;
import it.algos.wiki24.backend.login.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.wiki.query.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

import java.util.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Sun, 30-Jul-2023
 * Time: 08:39
 * Unit test di una classe service o backend o query <br>
 * Estende la classe astratta AlgosTest che contiene le regolazioni essenziali <br>
 * Nella superclasse AlgosTest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse AlgosTest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
public abstract class WikiQuicklyTest extends AlgosTest {

    protected static String currentDataBaseName = "wiki24";

    @Autowired
    public ApplicationContext appContext;

    @InjectMocks
    protected TextService textService;

    @InjectMocks
    protected ArrayService arrayService;

    @InjectMocks
    protected DateService dateService;

    @InjectMocks
    protected LogService logService;

    @InjectMocks
    protected UtilityService utilityService;

    @InjectMocks
    protected FileService fileService;


    @InjectMocks
    protected ALoggerBackend aLoggerBackend;

    @InjectMocks
    protected BotLogin botLogin;

    @InjectMocks
    protected ArrayService array;

    @InjectMocks
    public AnnotationService annotationService;

    @InjectMocks
    protected WikiBotService wikiBotService;

    @InjectMocks
    protected WikiApiService wikiApiService;

    @InjectMocks
    protected HtmlService htmlService;

    @InjectMocks
    protected QueryLogin queryLogin;

    @InjectMocks
    protected QueryInfoCat queryInfoCat;

    @InjectMocks
    protected QueryWrapBio queryWrapBio;

    @InjectMocks
    protected QueryCat queryCat;

    @InjectMocks
    public WikiBotService wikiBot;

    @InjectMocks
    public BioBackend bioBackend;
    @InjectMocks
    public BioService bioService;

    @InjectMocks
    public MongoService mongoService;

    protected MongoDatabase dataBase;

    protected MongoCollection collection;
    protected List<Long> listaPageIds;
    protected List<Bio> listBio;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        //        super.setUpAll();
        this.initMocks();
        this.checkMocks();
        this.crossReferences();
        this.fixRegolazioni();
    }

    protected void initMocks() {
        //        super.initMocks();
        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.openMocks(textService);
        MockitoAnnotations.openMocks(arrayService);
        MockitoAnnotations.openMocks(annotationService);
        MockitoAnnotations.openMocks(dateService);
        MockitoAnnotations.openMocks(logService);
        MockitoAnnotations.openMocks(utilityService);
        MockitoAnnotations.openMocks(fileService);
        MockitoAnnotations.openMocks(aLoggerBackend);
        MockitoAnnotations.openMocks(botLogin);
        MockitoAnnotations.openMocks(array);
        MockitoAnnotations.openMocks(wikiBotService);
        MockitoAnnotations.openMocks(wikiApiService);
        MockitoAnnotations.openMocks(htmlService);
        MockitoAnnotations.openMocks(queryLogin);
        MockitoAnnotations.openMocks(queryInfoCat);
        MockitoAnnotations.openMocks(queryWrapBio);
        MockitoAnnotations.openMocks(queryCat);
        MockitoAnnotations.openMocks(wikiBot);
        MockitoAnnotations.openMocks(bioBackend);
        MockitoAnnotations.openMocks(bioService);
        MockitoAnnotations.openMocks(mongoService);
        //        MockitoAnnotations.openMocks(dataBase);
        //        MockitoAnnotations.openMocks(mongoClient);
        //        MockitoAnnotations.openMocks(collection);
    }

    protected void checkMocks() {
        //        assertNotNull(appContext);
        assertNotNull(textService);
        assertNotNull(arrayService);
        assertNotNull(annotationService);
        assertNotNull(dateService);
        assertNotNull(logService);
        assertNotNull(utilityService);
        assertNotNull(fileService);
        assertNotNull(aLoggerBackend);
        assertNotNull(botLogin);
        assertNotNull(array);
        assertNotNull(wikiBotService);
        assertNotNull(wikiApiService);
        assertNotNull(htmlService);
        assertNotNull(queryLogin);
        assertNotNull(queryInfoCat);
        assertNotNull(queryWrapBio);
        assertNotNull(queryCat);
        assertNotNull(wikiBot);
        assertNotNull(bioBackend);
        assertNotNull(bioService);
        assertNotNull(mongoService);
        //        assertNotNull(dataBase);
        //        assertNotNull(mongoClient);
        //        assertNotNull(collection);
    }

    protected void crossReferences() {
        dateService.textService = textService;
        logService.textService = textService;
        logService.utilityService = utilityService;
        utilityService.fileService = fileService;
        fileService.textService = textService;
        utilityService.textService = textService;
        logService.aLoggerBackend = aLoggerBackend;
        aLoggerBackend.fileService = fileService;
        aLoggerBackend.textService = textService;
        wikiBotService.wikiApi = wikiApiService;
        wikiApiService.textService = textService;
        wikiApiService.htmlService = htmlService;
        htmlService.textService = textService;
        queryLogin.logger = logService;
        queryLogin.textService = textService;
        queryLogin.botLogin = botLogin;
        queryInfoCat.textService = textService;
        queryWrapBio.botLogin = botLogin;
        queryWrapBio.array = array;
        queryWrapBio.logger = logService;
        queryWrapBio.dateService = dateService;
        queryWrapBio.wikiBot = wikiBotService;
        queryWrapBio.textService = textService;
        queryCat.botLogin = botLogin;
        queryCat.textService = textService;
        queryCat.wikiBot = wikiBot;
        wikiBot.wikiApiService = wikiApiService;
        bioBackend.mongoService = mongoService;
        mongoService.textService = textService;
        mongoService.fileService = fileService;
        mongoService.logService = logService;
        mongoService.annotationService = annotationService;
        logService.textService = textService;
        bioService.textService = textService;
    }

    protected void fixRegolazioni() {
        queryLogin.urlRequestHamed();
        dataBase = mongoService.getDB(currentDataBaseName);
    }

    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    protected void printBioLista(List<Bio> listaBio) {
        String message;
        int max = 10;
        int tot = listaBio.size();
        int iniA = 0;
        int endA = Math.min(max, tot);
        int iniB = tot - max > 0 ? tot - max : 0;
        int endB = tot;

        if (listaBio != null) {
            message = String.format("Faccio vedere una lista delle prime e delle ultime %d biografie", max);
            System.out.println(message);
            message = "Ordinate per forzaOrdinamento";
            System.out.println(message);
            message = "Ordinamento, wikiTitle, nome, cognome";
            System.out.println(message);
            System.out.println(VUOTA);

            printBioBase(listaBio.subList(iniA, endA), iniA);
            System.out.println(TRE_PUNTI);
            printBioBase(listaBio.subList(iniB, endB), iniB);
        }
    }


    protected void printBioBase(List<Bio> listaBio, final int inizio) {
        int cont = inizio;

        for (Bio bio : listaBio) {
            cont++;
            System.out.print(cont);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(bio.ordinamento));
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(bio.wikiTitle));
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(textService.isValid(bio.nome) ? bio.nome : KEY_NULL));
            System.out.print(SPAZIO);

            System.out.print(textService.setQuadre(textService.isValid(bio.cognome) ? bio.cognome : KEY_NULL));
            System.out.print(SPAZIO);

            System.out.println(SPAZIO);
        }
    }

}