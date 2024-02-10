package it.algos.wiki24.basetest;

import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.base24.basetest.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.login.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.bio.bioserver.*;
import it.algos.wiki24.backend.packages.tabelle.attplurale.*;
import it.algos.wiki24.backend.packages.tabelle.attsingolare.*;
import it.algos.wiki24.backend.packages.tabelle.nazplurale.*;
import it.algos.wiki24.backend.packages.tabelle.nazsingolare.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;
import org.springframework.context.*;

import javax.inject.*;
import java.util.*;
import java.util.stream.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: ven, 29-apr-2022
 * Time: 20:57
 * Classe astratta che contiene le regolazioni essenziali <br>
 */
public abstract class WikiTest extends AlgosTest {

    protected boolean byPassaErrori;

    protected String clazzName;

    public static int MAX = 175;

    protected static final String OBBLIGATORIO = "(obbligatorio - ancora da regolare)";

    protected static final String FACOLTATIVO = "(facoltativo - potrebbe non interessare per questa classe)";


    protected static final String tagCheck = "AlgosBuilderPattern";

    /**
     * Istanza di una interfaccia <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Inject
    public ApplicationContext appContext;

    @Inject
    protected MongoService mongoService;

    @Inject
    public WikiBotService wikiBotService;

    @Inject
    public LogService logger;

    @Inject
    protected TextService textService;

    @Inject
    protected BioMongoModulo bioMongoModulo;

    //    @Autowired
    //    public ElaboraService elaboraService;

    //    @Autowired
    //    public DidascaliaService didascaliaService;

    //    @Autowired
    //    public QueryService queryService;

    //    @Autowired
    //    public BioService bioService;

    //    @Autowired
    //    public DownloadService downloadService;

    //    @Autowired
    //    public BioBackend bioBackend;

    //    @Autowired
    //    public AttivitaBackend attivitaBackend;
    //
    //    @Autowired
    //    public NazionalitaBackend nazionalitaBackend;

    @Inject
    public BotLogin botLogin;

    @Inject
    public DateService dateService;

    @Inject
    protected GiornoModulo giornoModulo;

    @Inject
    protected AnnoModulo annoModulo;

    @Inject
    protected AttSingolareModulo attSingolareModulo;

    @Inject
    protected AttPluraleModulo attPluraleModulo;

    @Inject
    protected NazSingolareModulo nazSingolareModulo;

    @Inject
    protected NazPluraleModulo nazPluraleModulo;

    //    protected CrudBackend crudBackend;

    //    protected WikiBackend wikiBackend;

    //    @Autowired
    //    public WikiUtility wikiUtility;

    //    @Autowired
    //    public GiornoBackend giornoBackend;

    //    @Autowired
    //    public MeseBackend meseBackend;

    //    @Autowired
    //    public AnnoBackend annoBackend;

    //    @Autowired
    //    public CognomeBackend cognomeBackend;
    //
    //    @Autowired
    //    public PaginaBackend paginaBackend;
    //
    //    @Autowired
    //    public GiornoWikiBackend giornoWikiBackend;
    //
    //    @Autowired
    //    public AnnoWikiBackend annoWikiBackend;
    //
    //    @Autowired
    //    public AttSingolareBackend attSingolareBackend;
    //
    //    @Autowired
    //    public AttPluraleBackend attPluraleBackend;
    //
    //    @Autowired
    //    public NazSingolareBackend nazSingolareBackend;
    //
    //    @Autowired
    //    public NazPluraleBackend nazPluraleBackend;

    protected final static long BIO_SALVINI_PAGEID = 132555;

    protected final static long BIO_RENZI_PAGEID = 134246;


    protected final static String PAGINA_INESISTENTE = "Pippooz Belloz";

    protected final static String BIO_SALVINI = "Matteo Salvini";

    protected final static String BIO_RENZI = "Matteo Renzi";

    protected final static String PAGINA_ESISTENTE_TRE = "Piozzano";

    protected final static String CATEGORIA_INESISTENTE = "Supercalifragilistichespiralidoso";

    protected final static String CATEGORIA_ESISTENTE_BREVE = "Ambasciatori britannici in Brasile";

    protected final static String CATEGORIA_ESISTENTE_MEDIA = "Nati nel 1435";

    protected final static String CATEGORIA_ESISTENTE_LUNGA = "Nati nel 1938";

    protected final static String CATEGORIA_PRINCIPALE_BIOBOT = "BioBot";


    protected WResult previstoRisultato;

    protected WResult ottenutoRisultato;

    protected WrapBio wrapBio;

    protected WrapTime wrapTime;

    //    protected Bio bio;

    protected List<Long> listaPageIds;

    protected List<WrapBio> listWrapBio;

    //    protected List<Bio> listBio;

    //    protected List<WrapDidascalia> listWrapDidascalia;

    //    protected List<WrapLista> listWrapLista;

    protected List<WrapTime> listWrapTime;

    //    protected LinkedHashMap<String, LinkedHashMap<String, List<WrapDidascalia>>> mappaLista;

    protected TypeUser typeUser;

    protected static final String PAGINA_UNO = "Roman Protasevič";

    protected static final String PAGINA_DUE = "Aldelmo di Malmesbury";

    protected static final String PAGINA_TRE = "Aelfric il grammatico";

    protected static final String PAGINA_QUATTRO = "Elfleda di Whitby";

    protected static final String PAGINA_CINQUE = "Werburga";

    protected static final String PAGINA_SEI = "Bernart Arnaut d'Armagnac";

    protected static final String PAGINA_SETTE = "Gaetano Anzalone";

    protected static final String PAGINA_OTTO = "Colin Campbell (generale)";

    protected static final String PAGINA_NOVE = "Louis Winslow Austin";

    protected static final String PAGINA_DIECI = "Maximilian Stadler";

    protected static final String PAGINA_DISAMBIGUA = "Rossi";

    protected static final String PAGINA_REDIRECT = "Regno di Napoli (1805-1815)";

    protected static final String PAGINA_UNDICI = "Muhammad ibn Ali al-Taqi al-Jawad";

    protected long pageId;

    public static final String SINGOLARE = "singolare";

    public static final String PLURALE = "pluraleLista";

    protected static String PARAMETRO = "nomeLista";

    protected static String CHECK = "checkValidita()";

    protected static String FUNZIONE = "isExistByKey";

    //    protected LinkedHashMap<String, List<WrapLista>> mappaWrap;

    protected Class clazz;

    protected String clazzTestName;

    //    protected WrapLista wrapLista;

    protected String moduloClazzName;

    protected boolean usaCollectionName;

    protected String collectionName;

    protected boolean ammessoCostruttoreVuoto;

    protected boolean istanzaValidaSubitoDopoCostruttore = false;

    protected String nomeParametro;

    protected String metodiEseguibili;

    protected String metodiDaRegolare;

    protected String metodiBuilderPattern;

    protected String metodoDefault;

    protected List<BioMongoEntity> listaBio = new ArrayList<>();

    protected boolean usaTypeLista;

    protected TypeLista currentType;

    protected boolean usaCurrentModulo;

    protected CrudModulo currentModulo;

    private boolean setUpAllEffettuato = false;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    protected void setUpAll() {
        MockitoAnnotations.openMocks(this);
        clazzName = clazz != null ? clazz.getSimpleName() : VUOTA;
        clazzTestName = this.getClass().getSimpleName();
        usaCollectionName = false;
        collectionName = VUOTA;
        usaCurrentModulo = false;
        currentModulo = null;
        usaTypeLista = false;
        currentType = TypeLista.nessunaLista;
        setUpAllEffettuato = true;
        ammessoCostruttoreVuoto = false;

        listaBio.add(creaBio("Junior Mapuku"));
        listaBio.add(creaBio("Johann Schweikhard von Kronberg"));
        listaBio.add(creaBio("Vincenzo Ferreri"));
        listaBio.add(creaBio("Roberto Rullo"));
        listaBio.add(creaBio("Stanley Adams (attore)"));
        listaBio.add(creaBio("Jameson Adams"));
        listaBio.add(creaBio("Nicola Castroceli"));
        listaBio.add(creaBio("Publio Ovidio Nasone"));
        listaBio.add(creaBio("Jordan Adams (1981)"));
        listaBio.add(creaBio("Marianna Saltini"));
        listaBio.add(creaBio("Patty Farchetto"));
        listaBio.add(creaBio("Gianangelo Bof"));

    }


    /**
     * Qui passa prima di ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUpEach() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    protected void setUpEach() {
        super.setUpEach();

        previstoRisultato = null;
        ottenutoRisultato = null;
        wrapBio = null;
        wrapTime = null;
        //        bio = null;
        listaPageIds = null;
        listWrapBio = null;
        listWrapTime = null;
        //        listWrapDidascalia = null;
        //        listWrapLista = null;
        //        listMiniWrap = null;
        //        mappaWrap = null;
        //        mappaLista = null;
        typeUser = null;
        pageId = 0L;
        //        wrapLista = null;
        message = VUOTA;
    }

    @Test
    @Order(0)
    @DisplayName("0 - Check iniziale dei parametri necessari per il test")
    void checkIniziale() {
        System.out.println("0 - Check iniziale dei parametri necessari per il test");

        if (!setUpAllEffettuato) {
            message = String.format("Manca il rinvio alla superclasse nel metodo setUpAll() della classe [%s]", this.getClass().getSimpleName());
            logger.error(new WrapLog().message(message).type(TypeLog.test));
            assertTrue(false);
            return;
        }

        System.out.println(VUOTA);
        message = String.format("Nella classe [%s] nel metodo setUpAll() e PRIMA di invocare il metodo super.setUpAll()", clazzTestName);
        System.out.println(message);

        if (mancaClazz()) {
            return;
        }
        if (mancaClazzName()) {
            return;
        }

        System.out.println(VUOTA);
        message = String.format("Nella classe [%s] nel metodo setUpAll() e DOPO aver invocato il metodo super.setUpAll() ", clazzTestName);
        System.out.println(message);

        if (mancaCollectionName()) {
            return;
        }
        if (mancaCurrentModulo()) {
            return;
        }
        if (mancaCurrentType()) {
            return;
        }
        if (costruttoreNonValido()) {
            return;
        }
    }

    private boolean mancaClazz() {
        if (clazz == null) {
            message = String.format("Manca il flag '%s' nel metodo setUpAll() della classe [%s]", "clazz", this.getClass().getSimpleName());
            logger.error(new WrapLog().message(message).type(TypeLog.test));
            assertTrue(false);
            return true;
        }
        message = String.format("Il flag '%s' è previsto e regolato (=%s) nel metodo setUpAll() del test [%s]", "clazz", clazz, clazzTestName);
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        return false;
    }

    private boolean mancaClazzName() {
        if (textService.isEmpty(clazzName)) {
            message = String.format("Manca la regolazione del flag '%s' nel metodo setUpAll() della classe [%s]", "clazzName", "WikiTest");
            logger.error(new WrapLog().message(message).type(TypeLog.test));
            assertTrue(false);
            return true;
        }
        message = String.format("Il flag '%s' è stato regolato (=%s) nel metodo setUpAll() della classe [%s]", "clazzName", clazzName, "WikiTest");
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        return false;
    }

    private boolean mancaCollectionName() {
        if (usaCollectionName) {
            if (textService.isEmpty(collectionName)) {
                message = String.format("Usa '%s' e il flag è regolato (%s=true) nel metodo setUpAll() di una sottoclasse (astratta) di [%s]", "collectionName", "usaCollectionName", "WikiTest");
                logger.info(new WrapLog().message(message).type(TypeLog.test));
                message = String.format("Manca il flag '%s' nel metodo setUpAll() di una sottoclasse (astratta) di [%s]", "collectionName", clazzTestName);
                logger.error(new WrapLog().message(message).type(TypeLog.test));
                assertTrue(false);
                return true;
            }
            message = String.format("Il flag '%s' è previsto e regolato (=%s) nel metodo setUpAll() del test [%s]", "collectionName", collectionName, clazzTestName);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
        }
        else {
            message = String.format("Non usa '%s' e il flag è regolato (%s=false) nel metodo setUpAll() della classe [%s]", "collectionName", "usaCollectionName", "WikiTest");
            logger.info(new WrapLog().message(message).type(TypeLog.test));
        }
        return false;
    }

    private boolean mancaCurrentModulo() {
        if (usaCurrentModulo) {
            if (currentModulo == null) {
                message = String.format("Usa '%s' e il flag è regolato (%s=true) nel metodo setUpAll() di una sottoclasse (astratta) di [%s]", "currentModulo", "usaCurrentModulo", "WikiTest");
                logger.info(new WrapLog().message(message).type(TypeLog.test));
                message = String.format("Manca il flag '%s' nel metodo setUpAll() della classe [%s]", "currentModulo", clazzTestName);
                logger.error(new WrapLog().message(message).type(TypeLog.test));
                assertTrue(false);
                return true;
            }
            message = String.format("Il flag '%s' è previsto e regolato (=%s) nel metodo setUpAll() del test [%s]", "currentModulo", currentModulo.getClass().getSimpleName(), clazzTestName);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
        }
        else {
            message = String.format("Non usa '%s' e il flag è regolato (%s=false) nel metodo setUpAll() della classe [%s]", "currentModulo", "usaCurrentModulo", "WikiTest");
            logger.info(new WrapLog().message(message).type(TypeLog.test));
        }
        return false;
    }

    private boolean mancaCurrentType() {
        if (usaTypeLista) {
            if (currentType == null || currentType == TypeLista.nessunaLista) {
                message = String.format("Usa '%s' e il flag è regolato (%s=true) nel metodo setUpAll() di una sottoclasse (astratta) di [%s]", "currentType", "usaTypeLista", "WikiTest");
                logger.info(new WrapLog().message(message).type(TypeLog.test));
                message = String.format("Manca il flag '%s' nel metodo setUpAll() della classe [%s]", "currentType", clazzTestName);
                logger.error(new WrapLog().message(message).type(TypeLog.test));
                assertTrue(false);
                return true;
            }
            message = String.format("Il flag '%s' è previsto e regolato (=%s) nel metodo setUpAll() del test [%s]", "currentType", currentType.name(), clazzTestName);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
        }
        else {
            message = String.format("Non usa '%s' e il flag è regolato (%s=false) nel metodo setUpAll() della classe [%s]", "currentType", "usaTypeLista", "WikiTest");
            logger.info(new WrapLog().message(message).type(TypeLog.test));
        }
        return false;
    }

    private boolean costruttoreNonValido() {

        if (ammessoCostruttoreVuoto) {
            message = String.format("Il flag '%s' è regolato (=%s) nel metodo setUpAll() della classe [%s]", "ammessoCostruttoreVuoto",
                    ammessoCostruttoreVuoto, clazzTestName
            );
            logger.info(new WrapLog().message(message).type(TypeLog.test));
        }
        else {
            message = String.format("Il flag '%s' è regolato (=%s) nel metodo setUpAll() della classe [%s]", "ammessoCostruttoreVuoto", ammessoCostruttoreVuoto, "WikiTest");
            logger.info(new WrapLog().message(message).type(TypeLog.test));
        }

        //        message = String.format("Il flag '%s' è = %s nel metodo setUpAll() della classe [%s]", "ammessoCostruttoreVuoto", ammessoCostruttoreVuoto, clazzTestName);
        //        logger.info(new WrapLog().message(message).type(TypeLog.test));

        //        message = String.format("Il flag '%s' è = %s nel metodo setUpAll() della classe [%s]", "ammessoCostruttoreVuoto", ammessoCostruttoreVuoto, clazzTestName);
        //        logger.info(new WrapLog().message(message).type(TypeLog.test));
        //
        //        message = String.format("Il flag '%s' è = %s nel metodo setUpAll() della classe [%s]", "istanzaValidaSubitoDopoCostruttore", istanzaValidaSubitoDopoCostruttore, clazzTestName);
        //        logger.info(new WrapLog().message(message).type(TypeLog.test));

        return true;
    }

    @Test
    @Order(1)
    @DisplayName("1 - Costruttore base con/senza parametri")
    void costruttoreBase() {
        if (this.ammessoCostruttoreVuoto) {
            System.out.println(String.format("1 - Costruttore base new %s() SENZA parametri", clazzName));
            System.out.println(VUOTA);

            System.out.println(String.format("La classe [%s] prevede un costruttore SENZA parametri", clazzName));
            System.out.println(String.format("new %s() funziona", clazzName));
            System.out.println(String.format("new %s(xxx) NON funziona", clazzName));
            System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", clazzName));
            System.out.println("Questa classe NON accetta parametri nel costruttore");
        }
        else {
            System.out.println(String.format("1 - Costruttore base new %s(xxx) con ALMENO un parametro", clazzName));
            System.out.println(VUOTA);

            System.out.println(String.format("La classe [%s] NON prevede un costruttore SENZA parametri", clazzName));
            System.out.println(String.format("Non è possibile creare un'istanza di [%s] SENZA parametri", clazzName));
            System.out.println(String.format("new %s() NON funziona (dà errore)", clazzName));
            System.out.println("È OBBLIGATORIO usare il costruttore con 1 PARAMETRO per la creazione.");
            System.out.println("Ci potrebbero essere anche altri costruttori oltre a quello base con un parametro.");


        }
    }


    @Test
    @Order(2)
    @DisplayName("2 - appContext.getBean con/senza parametri")
    void getBean() {
        Object istanzaGenerica;

        if (this.ammessoCostruttoreVuoto) {
            System.out.println(String.format("2 - appContext.getBean(%s.class) SENZA parametri", clazzName));
            System.out.println(VUOTA);

            System.out.println(String.format("La classe [%s] prevede un costruttore SENZA parametri", clazzName));
            System.out.println(String.format("appContext.getBean(%s.class) funziona", clazzName));
            System.out.println(String.format("appContext.getBean(%s.class, xxx) NON funziona", clazzName));
            System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", clazzName));
            System.out.println("Questa classe NON accetta parametri nel costruttore");
            System.out.println(VUOTA);

            try {
                istanzaGenerica = appContext.getBean(clazz);
            } catch (Exception unErrore) {
                logger.error(new WrapLog().exception(unErrore));
                return;
            }
            assertNotNull(istanzaGenerica);

            if (istanzaGenerica instanceof AlgosBuilderPattern builderPattern) {
                System.out.println(String.format("Questa classe (e altre) implementa il Design Pattern 'Builder'"));
                System.out.println(String.format("Per permettere la costruzione 'modulare' dell'istanza con variabili come [test] e altre"));
                if (builderPattern.isPatternCompleto()) {
                    System.out.println("Finito il ciclo del costruttore e il metodo @PostConstruct, l'istanza è pronta");
                    System.out.println("L'istanza è valida/eseguibile da subito, senza ulteriori regolazioni del BuilderPattern");
                }
                else {
                    System.out.println("Dopo il ciclo del costruttore init() ed il metodo @PostConstruct(), l'istanza NON è ancora pronta");
                    System.out.println("Mancano ulteriori regolazioni previste nel BuilderPattern per essere operativa");
                }
            }
            else {
                System.out.println("Questa classe NON implementa l'interfaccia di controllo AlgosBuilderPattern");
                System.out.println("Non posso quindi sapere se l'istanza è valida/eseguibile subito dopo il costruttore");
            }
        }
        else {
            System.out.println(String.format("2 - appContext.getBean(%s.class, xxx) con ALMENO un parametro", clazzName));
            System.out.println(VUOTA);
            System.out.println("Errore previsto (nel test). Tipo warning.");

            System.out.println(String.format("La classe [%s] NON prevede un costruttore SENZA parametri", clazzName));
            System.out.println(String.format("Non è possibile creare un'istanza di [%s] SENZA parametri", clazzName));
            System.out.println(String.format("appContext.getBean(%s.class) NON funziona (dà errore)", clazzName));
            System.out.println("È OBBLIGATORIO usare il costruttore con 1 PARAMETRO per la creazione.");
            System.out.println("Ci potrebbero essere anche altri costruttori oltre a quello base con un parametro.");
            System.out.println(VUOTA);

            try {
                istanzaGenerica = appContext.getBean(clazz);
            } catch (Exception unErrore) {
                logger.warn(new WrapLog().exception(unErrore));
                return;
            }
            assertNull(istanzaGenerica);
        }
    }

    // 3 - senzaParametroNelCostruttore

    protected void fixSenzaParametroNelCostruttore() {
        System.out.println("3 - senzaParametroNelCostruttore");
        System.out.println("Prova a costruire un'istanza senza parametri");
        System.out.println(VUOTA);
        boolean istanzaCostruitaSenzaParametri = false;

        try {
            appContext.getBean(clazz);
            istanzaCostruitaSenzaParametri = true;
        } catch (Exception unErrore) {
            istanzaCostruitaSenzaParametri = false;
            //            message = String.format("La classe [%s] prevede un parametro nel costruttore", clazzName);
            //            logService.info(new WrapLog().message(message).type(AETypeLog.test));

            //            message = String.format("Istanza NON valida (come prevedibile) perché nel costruttore MANCA il parametro indispensabile");
            //            logService.warn(new WrapLog().message(message).type(AETypeLog.test));
        }

        if (ammessoCostruttoreVuoto) {
            message = String.format("La classe [%s] prevede un costruttore senza parametri", clazzName);
            logger.info(new WrapLog().message(message).type(TypeLog.test));

            if (istanzaCostruitaSenzaParametri) {
                message = String.format("Sono riuscito a costruire un'istanza della classe [%s]", clazzName);
                logger.info(new WrapLog().message(message).type(TypeLog.test));

                message = String.format("Come previsto appContext.getBean(%s.class) funziona", clazzName);
                logger.info(new WrapLog().message(message).type(TypeLog.test));

                message = String.format("Il flag '%s' nel metodo setUpAll() di questa classe [%s] è corretto", "ammessoCostruttoreVuoto=false", this.getClass().getSimpleName());
                logger.info(new WrapLog().message(message).type(TypeLog.test));

                message = String.format("Nella classe [%s] o in una sua superclasse esiste un costruttore senza parametri", clazzName);
                logger.info(new WrapLog().message(message).type(TypeLog.test));

                message = String.format("Costruita come previsto un'istanza valida della classe [%s]", clazzName);
                logger.warn(new WrapLog().message(message).type(TypeLog.test));
                assertTrue(true);
            }
            else {
                message = String.format("Non sono riuscito a costruire un'istanza della classe [%s]", clazzName);
                logger.info(new WrapLog().message(message).type(TypeLog.test));

                message = String.format("Contrariamente al previsto appContext.getBean(%s.class) NON funziona", clazzName);
                logger.info(new WrapLog().message(message).type(TypeLog.test));

                message = String.format("Potrebbe essere sbagliato il flag '%s' nel metodo setUpAll() di questa classe [%s]", "ammessoCostruttoreVuoto", this.getClass().getSimpleName());
                logger.info(new WrapLog().message(message).type(TypeLog.test));

                message = String.format("Potrebbe mancare il costruttore senza parametri nella classe [%s] o nelle sue superclassi", clazzName);
                logger.info(new WrapLog().message(message).type(TypeLog.test));

                message = String.format("Nella classe [%s] manca il costruttore senza parametri", clazzName);
                logger.warn(new WrapLog().message(message).type(TypeLog.test));
                assertTrue(false);
            }
        }
        else {
            message = String.format("La classe [%s] prevede un parametro (obbligatorio) nel costruttore", clazzName);
            logger.info(new WrapLog().message(message).type(TypeLog.test));

            if (istanzaCostruitaSenzaParametri) {
                message = String.format("Sono riuscito a costruire un'istanza della classe [%s] anche SENZA un parametro nel costruttore", clazzName);
                logger.info(new WrapLog().message(message).type(TypeLog.test));

                message = String.format("Contrariamente al previsto appContext.getBean(%s.class) funziona", clazzName);
                logger.info(new WrapLog().message(message).type(TypeLog.test));

                message = String.format("Potrebbe essere sbagliato il flag '%s' nel metodo setUpAll() di questa classe [%s]", "ammessoCostruttoreVuoto", this.getClass().getSimpleName());
                logger.info(new WrapLog().message(message).type(TypeLog.test));

                message = String.format("Potrebbe esserci un costruttore senza parametri nella classe [%s] o nelle sue superclassi", clazzName);
                logger.info(new WrapLog().message(message).type(TypeLog.test));

                message = String.format("La classe [%s] ha un costruttore NON previsto", clazzName);
                logger.warn(new WrapLog().message(message).type(TypeLog.test));
                assertTrue(false);
            }
            else {
                message = String.format("Non sono riuscito a costruire un'istanza della classe [%s] SENZA un parametro nel costruttore", clazzName);
                logger.info(new WrapLog().message(message).type(TypeLog.test));

                message = String.format("Come previsto appContext.getBean(%s.class) NON funziona", clazzName);
                logger.info(new WrapLog().message(message).type(TypeLog.test));

                message = String.format("Il flag '%s' nel metodo setUpAll() di questa classe [%s] di test è corretto", "ammessoCostruttoreVuoto", this.getClass().getSimpleName());
                logger.info(new WrapLog().message(message).type(TypeLog.test));

                message = String.format("Nella classe [%s] non esiste un costruttore senza parametri", clazzName);
                logger.info(new WrapLog().message(message).type(TypeLog.test));

                message = String.format("La classe [%s] ha solo costruttori coi parametri (come previsto)", clazzName);
                logger.warn(new WrapLog().message(message).type(TypeLog.test));
                assertTrue(true);
            }
        }
    }


    protected void fixCheckParametroNelCostruttore(String parametro, String valore, String check, String funzione) {
        System.out.println("4 - checkParametroNelCostruttore");
        System.out.println(String.format("Costruisce un'istanza con un parametro farlocco (tipo '%s')", valore));
        System.out.println(VUOTA);
        Object istanza = null;
        boolean isCostruttoreValido = false;

        try {
            istanza = appContext.getBean(clazz, valore);
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(new AlgosException(unErrore)));
            assertTrue(false);
        }
        assertNotNull(istanza);

        if (istanza instanceof AlgosBuilderPattern istanzaCheck) {
            isCostruttoreValido = istanzaCheck.isCostruttoreValido();
        }
        else {
            message = String.format("La classe [%s] NON implementa l'interfaccia %s", clazzName, tagCheck);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            message = String.format("La classe [%s] NON controlla la validità del parametro usato nel costruttore", clazzName);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            message = String.format("Istanza costruita con appContext.getBean(%s.class, %s)", clazzName, valore);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            message = String.format("L'istanza esiste ma non ci sono altre informazioni sulla fruibilità della stessa");
            logger.warn(new WrapLog().message(message).type(TypeLog.test));
            message = String.format("Considera la possibilità di implementare l'interfaccia %s per la classe [%s]", tagCheck, clazzName);
            logger.warn(new WrapLog().message(message).type(TypeLog.test));
            return;
        }

        message = String.format("La classe [%s] implementa l'interfaccia %s", clazzName, tagCheck);
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        message = String.format("Controlla la validità del parametro usato nel costruttore");
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        message = String.format("Istanza costruita con appContext.getBean(%s.class, %s)", clazzName, valore);
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        message = String.format("Controllo nel metodo %s.%s, invocato da  @PostConstruct", clazzName, check);
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        message = String.format("Funzione%s%s.%s(%s)", FORWARD, moduloClazzName, funzione, valore);
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        System.out.println(VUOTA);

        if (isCostruttoreValido) {
            message = String.format("Istanza valida col valore accettabile [%s] del parametro '%s' nel costruttore", valore, parametro);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
        }
        else {
            message = String.format("Istanza NON valida perché il valore [%s] per il parametro chiave della collection '%s' non è previsto", valore, collectionName);
            logger.warn(new WrapLog().message(message).type(TypeLog.test));
        }
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }

    protected void fixBeanStandard(String nomeParametroCostruttore, String valore, String metodiEseguibili, String metodoDaRegolare, String metodiBuilderPattern) {
        System.out.println(String.format("5 - Istanza della classe [%s] costruita col solo parametro e SENZA altre regolazioni", clazzName));
        System.out.println(VUOTA);
        Object istanza = null;
        boolean isCostruttoreValido;
        boolean isBuildPatternValido;

        if (clazz == null) {
            message = String.format("Manca il flag '%s' nel metodo setUpAll() della classe '%s'", "clazz", this.getClass().getSimpleName());
            logger.error(new WrapLog().message(message).type(TypeLog.test));
            assertTrue(false);
            return;
        }
        if (textService.isEmpty(collectionName)) {
            message = String.format("Manca il flag '%s' nel metodo setUpAll() della classe '%s'", "collectionName", this.getClass().getSimpleName());
            logger.error(new WrapLog().message(message).type(TypeLog.test));
            assertTrue(false);
            return;
        }

        try {
            istanza = appContext.getBean(clazz, valore);
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(new AlgosException(unErrore)));
        }
        assertNotNull(istanza);

        if (istanza instanceof AlgosBuilderPattern istanzaCheck) {
            isCostruttoreValido = istanzaCheck.isCostruttoreValido();
        }
        else {
            message = String.format("La classe [%s] NON implementa l'interfaccia %s", clazzName, tagCheck);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            message = String.format("La classe [%s] NON controlla la validità del parametro usato nel costruttore", clazzName);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            message = String.format("Istanza costruita con appContext.getBean(%s.class, %s)", clazzName, valore);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            message = String.format("L'istanza esiste ma non ci sono altre informazioni sulla fruibilità della stessa");
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            return;
        }

        if (istanza instanceof AlgosBuilderPattern istanzaBuildPattern) {
            isBuildPatternValido = istanzaBuildPattern.isPatternCompleto();
        }
        else {
            message = String.format("La classe [%s] NON implementa l'interfaccia %s", clazzName, tagCheck);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            message = String.format("La classe [%s] NON controlla se esistono ulteriori regolazioni del BuilderPattern", clazzName);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            message = String.format("Istanza costruita con appContext.getBean(%s.class, %s)", clazzName, valore);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            message = String.format("L'istanza esiste ma non ci sono altre informazioni sulla fruibilità della stessa");
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            return;
        }

        message = String.format("Costruisce un'istanza col parametro '%s'", valore);
        logger.warn(new WrapLog().message(message).type(TypeLog.test));
        if (isCostruttoreValido) {
            message = String.format("La classe [%s] implementa l'interfaccia %s", clazzName, tagCheck);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            message = String.format("Istanza con costruttore valido - esiste il valore del parametro chiave [%s] nella collection '%s'", valore, collectionName);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
        }
        else {
            message = String.format("La classe [%s] implementa l'interfaccia %s", clazzName, tagCheck);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            message = String.format("Istanza NON valida - non esiste il valore del parametro chiave [%s] nella collection '%s'", valore, collectionName);
            logger.warn(new WrapLog().message(message).type(TypeLog.test));
            System.out.println(VUOTA);
            System.out.println(VUOTA);
            return;
        }

        message = String.format("La classe [%s] implementa l'interfaccia %s", clazzName, tagCheck);
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        if (isBuildPatternValido) {
            message = String.format("L'istanza è valida/eseguibile da subito, senza ulteriori regolazioni del BuilderPattern");
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            message = String.format("BuilderPattern%s%s", FORWARD, metodiBuilderPattern);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            message = String.format("Istanza [%s.%s] pronta per %s", collectionName, textService.primaMaiuscola(valore), metodiEseguibili);
            logger.warn(new WrapLog().message(message).type(TypeLog.test));
            System.out.println(VUOTA);
            //            this.debug("xxx",valore,"(nessuno)",true,true);
        }
        else {
            message = "L'istanza NON è utilizzabile, perché occorrono ulteriori regolazioni del BuilderPattern";
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            message = String.format("BuilderPattern%s%s", FORWARD, metodiBuilderPattern);
            logger.info(new WrapLog().message(message).type(TypeLog.test));

            message = String.format("Prova ad usare i metodi%s%s", FORWARD, metodiDaRegolare);
            logger.warn(new WrapLog().message(message).type(TypeLog.test));
            System.out.println(VUOTA);
        }
    }



    protected boolean fixListe(final String nomeLista, final TypeLista typeSuggerito) {
        if (textService.isEmpty(nomeLista)) {
            message = String.format("Manca il nome di %s per un'istanza di type%s[%s]", typeSuggerito.getGiornoAnno(), FORWARD, typeSuggerito.name());
            System.out.println(message);
            return false;
        }

        if (typeSuggerito == null) {
            message = String.format("Manca il typeLista per l'istanza di %s", nomeLista);
            System.out.println(message);
            return false;
        }

        if (typeSuggerito == TypeLista.nessunaLista) {
            message = String.format("Non è specificato il typeLista per l'istanza di %s", nomeLista);
            System.out.println(message);
            return false;
        }

        currentType = typeSuggerito;

        currentModulo = switch (typeSuggerito) {
            case giornoNascita, giornoMorte -> giornoModulo;
            case annoNascita, annoMorte -> annoModulo;
            case attivitaSingolare -> attSingolareModulo;
            case attivitaPlurale -> attPluraleModulo;
            case nazionalitaSingolare -> nazSingolareModulo;
            case nazionalitaPlurale -> nazPluraleModulo;
            default -> null;
        };

        if (currentModulo == null) {
            System.out.println("Manca l'indicazione del currentModulo");
            return false;
        }

        if (currentModulo.findByKey(nomeLista) == null) {
            message = String.format("%s [%s] indicato NON esiste per un'istanza di type%s[%s]", textService.primaMaiuscola(typeSuggerito.getGiornoAnno()), nomeLista, FORWARD, currentType.name());
            System.out.println(message);
            return false;
        }

        if (currentType != typeSuggerito) {
            message = String.format("Il type suggerito%s[%s] è incompatibile per un'istanza che prevede type%s[%s]", FORWARD, typeSuggerito, FORWARD, currentType);
            System.out.println(message);
            return false;
        }

        return true;
    }

    protected void debug(String incipit, String valore, String metodoEseguito, boolean costruttoreValido, boolean patternValido) {
        System.out.println(incipit);
        System.out.println(String.format("Debug%s%s", FORWARD, valore));
        System.out.println(String.format("Classe%s%s", FORWARD, clazz.getSimpleName()));
        System.out.println(String.format("istanzaValidaSubitoDopoCostruttore%s%s", FORWARD, costruttoreValido));
        System.out.println(String.format("istanzaEffettivamenteValida%s%s", FORWARD, false));
        System.out.println(String.format("metodo BuilderPattern Eseguito%s%s", FORWARD, metodoEseguito));
        System.out.println(String.format("builderPattern valido%s%s", FORWARD, patternValido));
    }

    protected void fixBeanStandard(final Object istanza, String valore, String metodiEseguibili, String metodoDaRegolare, String metodiBuilderPattern) {
        System.out.println(String.format("7 - Istanza valida della classe [%s] costruita col parametro SENZA altre regolazioni", clazzName));
        System.out.println(VUOTA);
        System.out.println(String.format("L'istanza della classe [%s] è stata creata con '%s' come parametro nel costruttore", clazzName, valore));

        if (this.istanzaValidaSubitoDopoCostruttore) {
            System.out.println("L'istanza è valida/eseguibile da subito, senza ulteriori regolazioni del BuilderPattern");
            System.out.println(String.format("BuilderPattern%s%s", FORWARD, metodiBuilderPattern));
            System.out.println(String.format("Non fa nulla ma è pronta per '%s' o altri metodi", metodiEseguibili));
            System.out.println(VUOTA);

            assertNotNull(istanza);
        }
        else {
            System.out.println("L'istanza NON è utilizzabile");
            System.out.println("L'istanza NON è valida, perché occorrono ulteriori regolazioni del BuilderPattern");
            System.out.println(String.format("Ad esempio la regolazione di %s", metodoDaRegolare));
            System.out.println(String.format("BuilderPattern%s%s", FORWARD, metodiBuilderPattern));
            System.out.println(VUOTA);

            assertNotNull(istanza);
        }
    }

    protected void fixBeanStandardNo(String nomeParametro, String valore, String check, String funzione, String sorgente, String metodiBuilderPattern) {
        System.out.println(String.format("8 - Istanza NON valida costruita col parametro '%s' nel costruttore della classe [%s]", valore, clazzName));
        System.out.println(VUOTA);

        System.out.println(String.format("Controllo nel metodo %s.%s, invocato da  @PostConstruct", clazzName, check));
        System.out.println(String.format("Funzione%s%s.%s(%s)", FORWARD, moduloClazzName, funzione, sorgente));
        message = String.format("Il valore '%s' non è accettabile per un'istanza valida di classe [%s]", valore, clazzName);
        logger.warn(new WrapLog().message(message));
    }


    @Deprecated
    protected void fixSenzaParametroNelCostruttore(String nomeParametro, String nomeMetodo) {
        System.out.println(String.format("6 - Costruttore senza parametro", nomeMetodo));
        System.out.println(VUOTA);

        System.out.println(String.format("Tentativo di invocare il metodo '%s'", nomeMetodo));
        System.out.println(String.format("Istanza di [%s] costruita SENZA parametro", clazzName));
        System.out.println(String.format("Non è possibile creare un'istanza della classe [%s] SENZA parametri", clazzName));
        System.out.println(String.format("appContext.getBean(%s.class) NON funziona (dà errore)", clazzName));
        System.out.println(String.format("È obbligatorio il '%s' nel costruttore.", nomeParametro));
        System.out.println(String.format("Seguendo il Pattern Builder, non si può chiamare il metodo '%s' se l'istanza non è correttamente istanziata.", nomeMetodo));
        message = String.format("Istanza di [%s] costruita SENZA parametro", clazzName);
        logger.warn(new WrapLog().message(message));
    }


    // 9 - Metodi builderPattern per validare l'istanza
    protected void fixBuilderPatternIniziale() {
        System.out.println("9 - Metodi builderPattern per validare l'istanza");
        System.out.println(VUOTA);

        if (istanzaValidaSubitoDopoCostruttore) {
            message = String.format("Una istanza di classe [%s] è immediatamente eseguibile dopo un costruttore (valido)", clazzName);
            logger.warn(new WrapLog().message(message).type(TypeLog.test));
        }
        else {
            message = String.format("Anche con un costruttore valido, una istanza di classe [%s] NON è immediatamente eseguibile", clazzName);
            logger.warn(new WrapLog().message(message).type(TypeLog.test));
        }

        System.out.println(String.format("Istanza di classe [%s]", clazzName));
        System.out.println(String.format("Istanza valida subito dopo il costruttore: %s", istanzaValidaSubitoDopoCostruttore));
        System.out.println(String.format("Metodi BuilderPattern disponibili: %s", metodiBuilderPattern));
        System.out.println(String.format("Metodo di default: %s", textService.isValid(metodoDefault) ? metodoDefault : NULLO));
        System.out.println(String.format("Metodi da regolare: %s", textService.isValid(metodiDaRegolare) ? metodiDaRegolare : NULLO));
        System.out.println(String.format("Metodi eseguibili: %s", textService.isValid(metodiEseguibili) ? metodiEseguibili : NULLO));

        if (istanzaValidaSubitoDopoCostruttore) {
            message = String.format("Si possono invocare i metodi eseguibili", clazz.getSimpleName());
            logger.warn(new WrapLog().message(message).type(TypeLog.test));
        }
        else {
            message = String.format("Occorre invocare uno dei metodi builderPattern da regolare");
            logger.warn(new WrapLog().message(message).type(TypeLog.test));
        }

        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }


    protected void debug(Object istanzaGenerica, String keyValue, String metodoEseguito) {
        AlgosBuilderPattern istanza = null;
        if (istanzaGenerica == null) {
            return;
        }
        assertNotNull(istanzaGenerica);

        if (istanzaGenerica instanceof AlgosBuilderPattern) {
            istanza = (AlgosBuilderPattern) istanzaGenerica;
        }
        else {
            message = String.format("La classe [%s] non implementa l'interfaccia %s", clazzName, "AlgosBuilderPattern");
            logger.error(new WrapLog().message(message).type(TypeLog.test));
            return;
        }

        if (!istanza.isCostruttoreValido()) {
            message = String.format("Il valore '%s' non è accettabile per un'istanza di classe [%s]", istanza.getNome(), clazzName);
            logger.warn(new WrapLog().message(message));
            return;
        }

        if (istanza.isPatternCompleto()) {
            if (istanzaValidaSubitoDopoCostruttore) {
                if (textService.isEmpty(metodoEseguito)) {
                    //                    System.out.println(String.format("L'istanza è immediatamente eseguibile dopo il costruttore anche senza nessun metodo BuilderPattern"));
                    System.out.println(String.format("Istanza di classe%s%s", FORWARD, clazz.getSimpleName()));
                    System.out.println(String.format("NomeLista%s%s", FORWARD, keyValue));
                    System.out.println(String.format("Istanza valida subito dopo il costruttore%s%s", FORWARD, istanza.isCostruttoreValido()));
                    System.out.println(String.format("Istanza effettivamente valida%s%s", FORWARD, true));
                    System.out.println(String.format("Metodo BuilderPattern eseguito%s%s", FORWARD, metodoDefault));
                }
                else {
                    System.out.println(String.format("Istanza di classe%s%s", FORWARD, clazz.getSimpleName()));
                    System.out.println(String.format("NomeLista%s%s", FORWARD, keyValue));
                    System.out.println(String.format("Istanza valida subito dopo il costruttore%s%s", FORWARD, istanza.isCostruttoreValido()));
                    System.out.println(String.format("Istanza effettivamente valida%s%s", FORWARD, true));
                    System.out.println(String.format("Metodo BuilderPattern eseguito%s%s", FORWARD, metodoEseguito));
                    System.out.println(String.format("L'istanza era eseguibile anche senza il metodo '%s'", metodoEseguito));
                }
            }
            else {
                assertTrue(istanza.isPatternCompleto());
                System.out.println(String.format("La chiamata del metodo '%s' rende utilizzabile l'istanza", metodoEseguito));
                System.out.println(String.format("Istanza di classe%s%s", FORWARD, clazz.getSimpleName()));
                System.out.println(String.format("NomeLista%s%s", FORWARD, keyValue));
                System.out.println(String.format("Istanza valida subito dopo il costruttore%s%s", FORWARD, istanza.isCostruttoreValido()));
                System.out.println(String.format("Istanza effettivamente valida%s%s", FORWARD, true));
                System.out.println(String.format("Metodo BuilderPattern eseguito%s%s", FORWARD, metodoEseguito));
            }

            if (textService.isEmpty(metodoEseguito)) {
                message = String.format("L'istanza è utilizzabile subito dopo il costruttore senza bisogno di nessun metodo BuilderPattern");
            }
            else {
                message = String.format("Il metodo builderPattern '%s' è eseguibile e l'istanza è utilizzabile", metodoEseguito);
            }
            logger.info(new WrapLog().message(message));
        }
        else {
            assertFalse(istanza.isPatternCompleto());
            System.out.println(String.format("Istanza di classe%s%s", FORWARD, clazz.getSimpleName()));
            System.out.println(String.format("NomeLista%s%s", FORWARD, keyValue));
            System.out.println(String.format("Istanza valida subito dopo il costruttore%s%s", FORWARD, istanza.isCostruttoreValido()));
            System.out.println(String.format("Istanza effettivamente valida%s%s", FORWARD, false));
            System.out.println(String.format("Metodo BuilderPattern eseguito%s%s", FORWARD, metodoEseguito));

            if (textService.isEmpty(metodoEseguito)) {
                message = String.format("Senza chiamare nessun metodo builderPattern, l'istanza NON è utilizzabile");
            }
            else {
                message = String.format("Il metodo builderPattern '%s' NON è congruo e l'istanza NON è utilizzabile", metodoEseguito);
            }
            logger.warn(new WrapLog().message(message));
        }

        System.out.println(VUOTA);
    }

    @Deprecated
    protected void fixConParametroNelCostruttore(String nomeParametro, String metodoDaEseguire, String metodiDaRegolare) {
        String tempo = "27 secondi";
        System.out.println(String.format("7 - Costruttore con parametro", metodoDaEseguire));
        System.out.println(VUOTA);
        System.out.println(String.format("L'istanza della classe [%s] è stata creata col parametro '%s'", clazzName, nomeParametro));

        if (this.istanzaValidaSubitoDopoCostruttore) {
            System.out.println(String.format("Questa classe funziona anche SENZA ulteriori regolazioni già inserite in fixPreferenze()."));
            System.out.println(String.format("L'invocazione del metodo '%s'' è stata eseguita con successo in %s", metodoDaEseguire, tempo));
        }
        else {
            System.out.println(String.format("Questa classe funziona SOLO dopo la regolazione di '%s'.", metodiDaRegolare));
            System.out.println(String.format("L'invocazione del metodo '%s'' NON è ammessa", metodoDaEseguire));
        }
    }

    protected void fixConParametroNelCostruttore(String nomeParametro, String metodoDaEseguire, String metodiDaRegolare, boolean valida, long inizio) {
        String tempo = dateService.deltaTextEsatto(inizio);

        System.out.println(String.format("8 - Costruttore con parametro", metodoDaEseguire));
        System.out.println(VUOTA);
        System.out.println(String.format("L'istanza della classe [%s] è stata creata col parametro '%s'", clazzName, nomeParametro));

        if (this.istanzaValidaSubitoDopoCostruttore) {
            System.out.println(String.format("Questa classe funziona anche SENZA ulteriori regolazioni già inserite in fixPreferenze()."));
            if (valida) {
                System.out.println(String.format("L'invocazione del metodo '%s'' è stata eseguita con successo in %s", metodoDaEseguire, tempo));
            }
            else {
                System.out.println(String.format("L'invocazione del metodo '%s'' NON ha funzionato (mentre invece avrebbe dovuto)", metodoDaEseguire));
                assertTrue(false);
            }
        }
        else {
            System.out.println(String.format("Questa classe funziona SOLO dopo la regolazione di '%s'.", metodiDaRegolare));
            System.out.println(String.format("Il metodo '%s'' NON è stato invocato", metodoDaEseguire));
        }
    }

    //    protected void fixMappaWrapKey(final String sorgente, final LinkedHashMap<String, List<WrapLista>> mappaWrap) {
    //        System.out.println(VUOTA);
    //        System.out.println("40 - Key della mappaWrap");
    //
    //        if (mappaWrap != null && mappaWrap.size() > 0) {
    //            message = String.format("La mappaWrap della lista %s ha %d chiavi (paragrafi) per %d didascalie", sorgente, mappaWrap.size(), wikiUtility.getSizeAllWrap(mappaWrap));
    //            System.out.println(message);
    //            printMappaWrapKeyOrder(mappaWrap);
    //        }
    //        else {
    //            message = "La mappaWrap è nulla";
    //            System.out.println(message);
    //        }
    //    }

    //    protected void fixMappaWrapDidascalie(final String sorgente, final LinkedHashMap<String, List<WrapLista>> mappaWrap) {
    //        fixMappaWrapDidascalie(sorgente, mappaWrap, "50 - Mappa STANDARD wrapLista (paragrafi e righe)");
    //    }

    //    protected void fixMappaWrapDidascalie(final String sorgente, final LinkedHashMap<String, List<WrapLista>> mappaWrap, String incipit) {
    //        System.out.println(VUOTA);
    //        System.out.println(incipit);
    //
    //        if (mappaWrap != null && mappaWrap.size() > 0) {
    //            message = String.format("La mappaWrap della lista %s ha %d didascalie", sorgente, wikiUtility.getSizeAllWrap(mappaWrap));
    //            System.out.println(message);
    //            printMappaDidascalie(mappaWrap);
    //        }
    //        else {
    //            message = "La mappaWrap è nulla";
    //            System.out.println(message);
    //        }
    //    }

    protected void printRisultato(WResult result) {
        if (result == null) {
            return;
        }

        List lista = result.getLista();
        lista = lista != null && lista.size() > 20 ? lista.subList(0, 10) : lista;
        String newText = result.getNewtext();
        newText = newText.length() < MAX ? newText : newText.substring(0, Math.min(MAX, newText.length()));
        String content = result.getContent();
        content = content.length() < MAX ? content : content.substring(0, Math.min(MAX, content.length()));

        System.out.println(VUOTA);
        System.out.println("Risultato");
        System.out.println(String.format("Status: %s", result.isValido() ? "true" : "false"));
        System.out.println(String.format("Modificata: %s", result.isModificata() ? "true" : "false"));
        System.out.println(String.format("Query: %s", result.getQueryType()));
        System.out.println(String.format("Page: %s", result.getTypePage()));
        System.out.println(String.format("TypeResult: %s", result.getTypeResult()));
        System.out.println(String.format("Title: %s", result.getWikiTitle()));
        System.out.println(String.format("PageId: %s", result.getPageid()));
        System.out.println(String.format("Namespace: %s", result.getNameSpace()));
        System.out.println(String.format("Type: %s", result.getTypePage()));
        System.out.println(String.format("User: %s", result.getUserType()));
        System.out.println(String.format("Limit: %d", result.getLimit()));
        System.out.println(String.format("Summary: %s", result.getSummary()));
        System.out.println(String.format("Timestamp: %s", result.getTimeStamp()));
        System.out.println(String.format("Preliminary url: %s", result.getUrlPreliminary()));
        System.out.println(String.format("Secondary url: %s", result.getUrlRequest()));
        System.out.println(String.format("Get request url: %s", result.getGetRequest()));
        System.out.println(String.format("Cookies: %s", result.getCookies()));
        System.out.println(String.format("Preliminary response: %s", result.getPreliminaryResponse()));
        System.out.println(String.format("Token: %s", result.getToken()));
        System.out.println(String.format("Post: %s", result.getPost()));
        System.out.println(String.format("New text: %s", newText));
        System.out.println(String.format("Secondary response: %s", result.getResponse()));
        System.out.println(String.format("Message code: %s", result.getCodeMessage()));
        System.out.println(String.format("Message: %s", result.getMessage()));
        System.out.println(String.format("Error code: %s", result.getErrorCode()));
        System.out.println(String.format("Error message: %s", result.getErrorMessage()));
        System.out.println(String.format("Valid message: %s", result.getValidMessage()));
        System.out.println(String.format("NewTimeStamp: %s", result.getNewtimestamp()));
        System.out.println(String.format("Numeric value: %s", textService.format(result.getIntValue())));
        System.out.println(String.format("Cicli: %d", result.getCicli())); ;
        System.out.println(String.format("Text value: %s", result.getTxtValue()));
        System.out.println(String.format("List value: %s", lista));
        System.out.println(String.format("Map value: %s", result.getMappa()));
        System.out.println(String.format("Content value: %s", content));
        System.out.println(String.format("Risultato ottenuto in %s", dateService.toText(result.getDurata())));
        System.out.println(String.format("Risultato ottenuto in %s", dateService.deltaTextEsatto(result.getInizio(), result.getFine())));
        printWrapPage(result.getWrapPage(), false);
        printWrapBio(result.getWrapBio());
    }


    protected void printWrapPage(WrapPage wrap, boolean printContent) {
        if (wrap != null) {
            System.out.println(VUOTA);
            System.out.println(String.format("WrapPage valido: %s", wrap.isValida()));
            System.out.println(String.format("WrapPage type: %s", wrap.getType()));
            System.out.println(String.format("WrapPage nameSpace: %s", wrap.getNameSpace()));
            System.out.println(String.format("WrapPage pageid: %s", wrap.getPageid()));
            System.out.println(String.format("WrapPage title: %s", wrap.getTitle()));
            System.out.println(String.format("WrapPage timeStamp: %s", wrap.getTimeStamp()));
            if (printContent) {
                System.out.println(String.format("WrapPage content:"));
                System.out.println(String.format("%s", wrap.getContent()));
            }
        }
    }

    protected void printWrapBio(WrapBio wrapBio) {
        if (wrapBio != null) {
            System.out.println(VUOTA);
            System.out.println(String.format("WrapBio valida: %s", wrapBio.isValida()));
            System.out.println(String.format("WrapBio pageid: %s", wrapBio.getPageid()));
            System.out.println(String.format("WrapBio title: %s", wrapBio.getTitle()));
            System.out.println(String.format("WrapBio timeStamp: %s", wrapBio.getTimeStamp()));
            System.out.println(String.format("WrapBio creataBioServer: %s", wrapBio.isCreataBioServer()));
            System.out.println(String.format("WrapBio creataBioMongo: %s", wrapBio.isCreataBioMongo()));
            System.out.println(String.format("WrapBio tmplBio:"));
            System.out.println(String.format("%s", wrapBio.getTemplBio()));
        }
    }

    protected void printWrapTime(WrapTime wrapTime) {
        if (wrapTime != null) {
            System.out.println(VUOTA);
            System.out.println(String.format("WrapTime pageId: %s", wrapTime.getPageid()));
            System.out.println(String.format("WrapTime wikiTitle: %s", wrapTime.getWikiTitle()));
            System.out.println(String.format("WrapTime timestamp (originale): %s", wrapTime.getLastWikiModifica()));
            System.out.println(String.format("WrapTime last (formattato): %s", dateService.get(wrapTime.getLastWikiModifica())));
        }
    }

    protected void printBioServer(BioServerEntity bio) {
        if (bio != null) {
            System.out.println(VUOTA);
            System.out.println(String.format("BioEntity pageId: %s", bio.getPageId()));
            System.out.println(String.format("BioEntity wikiTitle: %s", bio.getWikiTitle()));
            System.out.println(String.format("BioEntity timestamp: %s", bio.getTimestamp()));
            System.out.println(String.format("BioEntity tmplBio:"));
            System.out.println(String.format("%s", wrapBio.getTemplBio()));
        }
    }


    protected void printBioMongo(BioMongoEntity bio) {
        if (bio != null) {
            System.out.println(VUOTA);
            System.out.println(String.format("BioMongo pageId: %s", bio.getPageId()));
            System.out.println(String.format("BioMongo wikiTitle: %s", bio.getWikiTitle()));
            System.out.println(String.format("BioMongo nome: %s", bio.getNome()));
            System.out.println(String.format("BioMongo cognome: %s", bio.getCognome()));
            //            System.out.println(String.format("BioMongo sesso: %s", bio.get()));
            System.out.println(String.format("BioMongo luogo nato: %s", bio.getLuogoNato()));
            System.out.println(String.format("BioMongo giorno nato: %s", bio.getGiornoNato()));
            System.out.println(String.format("BioMongo anno nato: %s", bio.getAnnoNato()));
            System.out.println(String.format("BioMongo luogo morto: %s", bio.getLuogoMorto()));
            System.out.println(String.format("BioMongo giorno morto: %s", bio.getGiornoMorto()));
            System.out.println(String.format("BioMongo anno morto: %s", bio.getAnnoMorto()));
        }
    }


    protected void printLista(final List lista) {
        int cont = 0;
        System.out.println(VUOTA);

        if (lista != null) {
            if (lista.size() > 0) {
                System.out.println(String.format("La lista contiene %d elementi", lista.size()));
                for (Object obj : lista) {
                    cont++;
                    System.out.print(cont);
                    System.out.print(PARENTESI_TONDA_END);
                    System.out.print(SPAZIO);
                    System.out.println(obj);
                }
            }
            else {
                System.out.println("Non ci sono elementi nella lista");
            }
        }
        else {
            System.out.println("Manca la lista");
        }
    }

    //    protected void printMiniWrap(List<WrapTime> listaMiniWrap) {
    //        if (listaMiniWrap != null) {
    //            System.out.println(VUOTA);
    //            System.out.println(String.format("Pageid (wikiTitle) and last timestamp"));
    //            System.out.println(VUOTA);
    //            for (WrapTime wrap : listaMiniWrap) {
    //                System.out.println(String.format("%s (%s)%s%s", wrap.getPageid(), wrap.getWikiTitle(), FORWARD, wrap.getLastModifica()));
    //            }
    //        }
    //    }

    protected void printWrapBio(List<WrapBio> listaWrapBio) {
        if (listaWrapBio != null) {
            System.out.println(VUOTA);
            System.out.println(String.format("Wrap pageid e wikiTitle"));
            System.out.println(VUOTA);
            for (WrapBio wrap : listaWrapBio) {
                System.out.println(String.format("%s (%s)", wrap.getPageid(), wrap.getTitle()));
            }
        }
    }

    //    protected void printBio(List<Bio> listaBio, String titolo) {
    //        int cont = 0;
    //        String message = textService.isValid(titolo) ? String.format(" di %s", titolo) : VUOTA;
    //        if (listaBio != null) {
    //            System.out.println(String.format("Nella lista ci sono %d biografie%s", listaBio.size(), message));
    //            System.out.println(VUOTA);
    //            for (Bio bio : listaBio) {
    //                cont++;
    //                System.out.print(cont);
    //                System.out.print(PARENTESI_TONDA_END);
    //                System.out.print(SPAZIO);
    //                System.out.println(bio != null ? bio.wikiTitle : VUOTA);
    //            }
    //        }
    //    }

    //    protected void printBio(List<Bio> listaBio) {
    //        printBio(listaBio, VUOTA);
    //    }


    protected void printDidascalia(final String sorgente, final String sorgente2, final String sorgente3, final String ottenuto) {
        System.out.println(VUOTA);
        System.out.println(String.format("Titolo effettivo della voce: %s", sorgente));
        System.out.println(String.format("Nome: %s", sorgente2));
        System.out.println(String.format("Cognome: %s", sorgente3));
        System.out.println(String.format("Didascalia: %s", ottenuto));
    }

    protected void printBotLogin() {
        System.out.println(VUOTA);
        System.out.println("Valori attuali del singleton BotLogin");
        System.out.println(String.format("Valido: %s", botLogin.isValido() ? "true" : "false"));
        System.out.println(String.format("Bot: %s", botLogin.isBot() ? "true" : "false"));
        System.out.println(String.format("Userid: %d", botLogin.getUserid()));
        System.out.println(String.format("Username: %s", botLogin.getUsername()));
        System.out.println(String.format("UserType: %s", botLogin.getUserType()));
        System.out.println(String.format("UrlResponse: %s", botLogin.getUrlResponse()));
        System.out.print(String.format("Cookies: "));
        if (botLogin.getCookies() != null && botLogin.getCookies().size() > 0) {
            System.out.println();
            printMappaTab(botLogin.getCookies());
        }
        else {
            System.out.println(NULLO);
        }
    }


    protected void printMappa(Map<String, String> mappa) {
        System.out.println(VUOTA);
        System.out.println(String.format("Mappa di %s elementi", mappa.size()));
        System.out.println(VUOTA);

        if (mappa != null && mappa.size() > 0) {
            for (String key : mappa.keySet()) {
                System.out.println(String.format("%s%s%s", key, UGUALE_SPAZIATO, mappa.get(key)));
            }
        }
    }

    protected void printMappaTab(Map<String, String> mappa) {
        if (mappa != null && mappa.size() > 0) {
            for (String key : mappa.keySet()) {
                System.out.println(String.format("%s%s%s%s", TAB, key, UGUALE, mappa.get(key)));
            }
        }
    }

    protected void printLong(List<Long> listaLong, int max) {
        if (listaLong != null) {
            System.out.println(VUOTA);
            System.out.println(String.format("Pageid"));
            System.out.println(VUOTA);
            for (Long pageId : listaLong.subList(0, Math.min(max, listaLong.size()))) {
                System.out.println(String.format("%s", pageId));
            }
        }
    }


    protected void printString(List<String> listaString) {
        printString(listaString, listaString.size());
    }

    protected void printString(List<String> listaString, int max) {
        int cont = 0;

        if (listaString != null) {
            System.out.println(VUOTA);
            System.out.println(String.format("Valori (%d):", listaString.size()));
            for (String riga : listaString.subList(0, Math.min(max, listaString.size()))) {
                cont++;
                System.out.print(cont);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.println(String.format("%s", riga));
            }
        }
    }

    protected String getMax(String message) {
        message = message.length() < MAX ? message : message.substring(0, Math.min(MAX, message.length()));
        if (message.contains(CAPO)) {
            message = message.replaceAll(CAPO, SPAZIO);
        }

        return message;
    }

    //    protected void printBioLista(List<Bio> listaBio) {
    //        String message;
    //        int max = 10;
    //        int tot = listaBio.size();
    //        int iniA = 0;
    //        int endA = Math.min(max, tot);
    //        int iniB = tot - max > 0 ? tot - max : 0;
    //        int endB = tot;
    //
    //        if (listaBio != null) {
    //            message = String.format("Faccio vedere una lista delle prime e delle ultime %d biografie", max);
    //            System.out.println(message);
    //            message = "Ordinate per forzaOrdinamento";
    //            System.out.println(message);
    //            message = "Ordinamento, wikiTitle, nome, cognome";
    //            System.out.println(message);
    //            System.out.println(VUOTA);
    //
    //            printBioBase(listaBio.subList(iniA, endA), iniA);
    //            System.out.println(TRE_PUNTI);
    //            printBioBase(listaBio.subList(iniB, endB), iniB);
    //        }
    //    }

    //    protected void printBioBase(List<Bio> listaBio, final int inizio) {
    //        int cont = inizio;
    //
    //        for (Bio bio : listaBio) {
    //            cont++;
    //            System.out.print(cont);
    //            System.out.print(PARENTESI_TONDA_END);
    //            System.out.print(SPAZIO);
    //
    //            System.out.print(textService.setQuadre(bio.ordinamento));
    //            System.out.print(SPAZIO);
    //
    //            System.out.print(textService.setQuadre(bio.wikiTitle));
    //            System.out.print(SPAZIO);
    //
    //            System.out.print(textService.setQuadre(textService.isValid(bio.nome) ? bio.nome : KEY_NULL));
    //            System.out.print(SPAZIO);
    //
    //            System.out.print(textService.setQuadre(textService.isValid(bio.cognome) ? bio.cognome : KEY_NULL));
    //            System.out.print(SPAZIO);
    //
    //            System.out.println(SPAZIO);
    //        }
    //    }

    //    protected void printWrapDidascalia(List<WrapDidascalia> lista) {
    //        int cont = 0;
    //        String value;
    //
    //        if (lista != null) {
    //            for (WrapDidascalia wrap : lista) {
    //                cont++;
    //                value = wrap.getWikiTitle();
    //
    //                System.out.print(TAB);
    //                System.out.print(TAB);
    //                System.out.print(cont);
    //                System.out.print(PARENTESI_TONDA_END);
    //                System.out.print(SPAZIO);
    //
    //                System.out.print(textService.setQuadre(value));
    //                System.out.print(SPAZIO);
    //
    //                System.out.println(VUOTA);
    //            }
    //        }
    //    }


    protected void printMappaDidascalia(String tag, String attivita, LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaDidascalie) {
        int cont = 0;
        LinkedHashMap<String, List<String>> mappaSub;

        if (mappaDidascalie != null) {
            message = String.format("Didascalie per %s %s", tag, attivita);
            System.out.println(message);
            message = "Nazionalità/Attività, primoCarattere, didascalia";
            System.out.println(message);
            System.out.println(VUOTA);

            for (String key : mappaDidascalie.keySet()) {
                mappaSub = mappaDidascalie.get(key);
                cont++;
                System.out.print(cont);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);

                System.out.print(key);
                System.out.print(SPAZIO);

                System.out.println(VUOTA);

                printMappaSubDidascalie(mappaSub);
                System.out.println(VUOTA);
            }
        }
    }


    protected void printMappaSubDidascalie(LinkedHashMap<String, List<String>> mappaSub) {
        int cont = 0;
        List lista;

        if (mappaSub != null) {
            for (String key : mappaSub.keySet()) {
                lista = mappaSub.get(key);
                cont++;
                System.out.print(TAB);
                System.out.print(cont);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);

                System.out.print(key);
                System.out.print(SPAZIO);

                System.out.println(VUOTA);

                printDidascalia(lista);
            }
        }
    }

    protected void printDidascalia(List<String> lista) {
        int cont = 0;

        if (lista != null) {
            for (String didascalia : lista) {
                cont++;

                System.out.print(TAB);
                System.out.print(TAB);
                System.out.print(cont);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);

                System.out.print(didascalia);
                System.out.print(SPAZIO);

                System.out.println(VUOTA);
            }
        }
    }

    protected void printAllSingolari(String plurale, List<String> listaSingolari, String tag) {
        System.out.println(String.format("Ci sono %d %s singolari per %s", listaSingolari.size(), tag, plurale));
        System.out.println(VUOTA);

        for (String singolare : listaSingolari) {
            System.out.println(singolare);
        }
    }

    //    protected void printMappaSub(LinkedHashMap<String, List<WrapDidascalia>> mappaSub) {
    //        int cont = 0;
    //        List lista;
    //
    //        if (mappaSub != null) {
    //            for (String key : mappaSub.keySet()) {
    //                lista = mappaSub.get(key);
    //                cont++;
    //                System.out.print(TAB);
    //                System.out.print(cont);
    //                System.out.print(PARENTESI_TONDA_END);
    //                System.out.print(SPAZIO);
    //
    //                System.out.print(textService.setQuadre(key));
    //                System.out.print(SPAZIO);
    //
    //                System.out.println(VUOTA);
    //
    //                printWrapDidascalia(lista);
    //            }
    //        }
    //    }

    //    protected void printWrapLista(List<WrapLista> listWrapLista) {
    //        int max = 15;
    //        if (listWrapLista != null) {
    //            message = String.format("Faccio vedere una lista delle prime %d didascalie", max);
    //            System.out.println(message);
    //            message = "Paragrafo, paragrafoLink, ordinamento, sottoParagrafo, didascaliaBreve";
    //            System.out.println(message);
    //            System.out.println(VUOTA);
    //            printSub(listWrapLista.subList(0, Math.min(max, listWrapLista.size())));
    //            System.out.println();
    //        }
    //    }

    //    protected void printSub(List<WrapLista> listWrapLista) {
    //        if (listWrapLista == null) {
    //            return;
    //        }
    //
    //        for (WrapLista wrap : listWrapLista) {
    //            System.out.print(wrap.titoloParagrafo);
    //            System.out.print(SEP);
    //            if (textService.isValid(wrap.titoloParagrafoLink)) {
    //                System.out.print(wrap.titoloParagrafoLink);
    //            }
    //            else {
    //                System.out.print("null");
    //            }
    //            System.out.print(SEP);
    //            if (textService.isValid(wrap.ordinamento)) {
    //                System.out.print(wrap.ordinamento);
    //            }
    //            else {
    //                System.out.print("null");
    //            }
    //            System.out.print(SEP);
    //            if (textService.isValid(wrap.titoloSottoParagrafo)) {
    //                System.out.print(wrap.titoloSottoParagrafo);
    //            }
    //            else {
    //                System.out.print("null");
    //            }
    //            System.out.print(SEP);
    //            System.out.println(wrap.didascaliaBreve);
    //        }
    //        System.out.println(VUOTA);
    //    }

    //    protected void printMappaWrap(LinkedHashMap<String, List<WrapLista>> mappaWrap) {
    //        List<WrapLista> lista;
    //
    //        if (mappaWrap != null) {
    //            message = String.format("Faccio vedere una mappa delle didascalie");
    //            System.out.println(VUOTA);
    //            for (String paragrafo : mappaWrap.keySet()) {
    //                System.out.print("==");
    //                System.out.print(paragrafo);
    //                System.out.print("==");
    //                System.out.print(CAPO);
    //                lista = mappaWrap.get(paragrafo);
    //                printSub(lista);
    //            }
    //        }
    //    }

    protected void printMappaList(Map<String, List<String>> mappa) {
        List<String> lista;

        if (mappa != null) {
            message = String.format("Faccio vedere una mappa delle didascalie");
            System.out.println(message);
            System.out.println(VUOTA);
            for (String paragrafo : mappa.keySet()) {
                System.out.print("==");
                System.out.print(paragrafo);
                System.out.print("==");
                System.out.print(CAPO);
                lista = mappa.get(paragrafo);
                for (String didascalia : lista) {
                    System.out.println(didascalia);
                }
                System.out.println(VUOTA);
            }
        }
    }

    //    protected void printMappaDidascalie(LinkedHashMap<String, List<WrapLista>> mappaWrap) {
    //        List<WrapLista> lista;
    //        String titoloParagrafo = VUOTA;
    //        WrapLista wrapZero;
    //
    //        if (mappaWrap != null) {
    //            message = String.format("Faccio vedere una mappa delle didascalie con i relativi paragrafi");
    //            System.out.println(message);
    //            System.out.println(VUOTA);
    //            for (String paragrafo : mappaWrap.keySet()) {
    //                lista = mappaWrap.get(paragrafo);
    //                wrapZero = lista.get(0) != null ? lista.get(0) : null;
    //                if (wrapZero != null) {
    //                    titoloParagrafo = textService.isValid(wrapZero.titoloParagrafoLink) ? wrapZero.titoloParagrafoLink : wrapZero.titoloParagrafo;
    //                }
    //                System.out.print("==");
    //                System.out.print(titoloParagrafo);
    //                System.out.print("==");
    //                System.out.print(CAPO);
    //
    //                for (WrapLista wrap : lista) {
    //                    System.out.println(wrap.didascalia);
    //                }
    //                System.out.println(VUOTA);
    //            }
    //        }
    //    }

    //    protected void printBio(Bio bio) {
    //        System.out.println(String.format("wikiTitle: %s", bio.wikiTitle));
    //        System.out.println(String.format("pageId: %s", bio.pageId));
    //        System.out.println(String.format("nome: %s", textService.isValid(bio.nome) ? bio.nome : VUOTA));
    //        System.out.println(String.format("cognome: %s", textService.isValid(bio.cognome) ? bio.cognome : VUOTA));
    //        System.out.println(String.format("sesso: %s", textService.isValid(bio.sesso) ? bio.sesso : VUOTA));
    //        System.out.println(String.format("luogoNato: %s", textService.isValid(bio.luogoNato) ? bio.luogoNato : VUOTA));
    //        System.out.println(String.format("luogoNatoLink: %s", textService.isValid(bio.luogoNatoLink) ? bio.luogoNatoLink : VUOTA));
    //        System.out.println(String.format("giornoNato: %s", textService.isValid(bio.giornoNato) ? bio.giornoNato : VUOTA));
    //        System.out.println(String.format("annoNato: %s", textService.isValid(bio.annoNato) ? bio.annoNato : VUOTA));
    //        System.out.println(String.format("luogoMorto: %s", textService.isValid(bio.luogoMorto) ? bio.luogoMorto : VUOTA));
    //        System.out.println(String.format("luogoMortoLink: %s", textService.isValid(bio.luogoMortoLink) ? bio.luogoMortoLink : VUOTA));
    //        System.out.println(String.format("giornoMorto: %s", textService.isValid(bio.giornoMorto) ? bio.giornoMorto : VUOTA));
    //        System.out.println(String.format("annoMorto: %s", textService.isValid(bio.annoMorto) ? bio.annoMorto : VUOTA));
    //        System.out.println(String.format("attivita: %s", textService.isValid(bio.attivita) ? bio.attivita : VUOTA));
    //        System.out.println(String.format("attivita2: %s", textService.isValid(bio.attivita2) ? bio.attivita2 : VUOTA));
    //        System.out.println(String.format("attivita3: %s", textService.isValid(bio.attivita3) ? bio.attivita3 : VUOTA));
    //        System.out.println(String.format("nazionalita: %s", textService.isValid(bio.nazionalita) ? bio.nazionalita : VUOTA));
    //    }

    //    protected void printBioTmpl(Bio bio) {
    //        String tmplBio = bio.tmplBio != null ? bio.tmplBio : VUOTA;
    //        printBio(bio);
    //
    //        if (textService.isValid(tmplBio)) {
    //            tmplBio = textService.sostituisce(tmplBio, CAPO, SPAZIO);
    //            System.out.println(String.format("tmpl: %s", tmplBio));
    //        }
    //    }

    protected void printMappaBio(Map<String, String> mappa) {
        if (mappa != null) {
            for (String key : mappa.keySet()) {
                System.out.print(key);
                System.out.print(FORWARD);
                System.out.println(mappa.get(key));
            }
        }
    }

    protected void print(List<String> lista) {
        int k = 1;
        if (lista != null) {
            for (String stringa : lista) {
                System.out.print(k++);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.println(stringa);
            }
        }
    }

    //    protected void printMappaWrapKeyOrder(LinkedHashMap<String, List<WrapLista>> mappaWrap) {
    //        List<WrapLista> lista;
    //        int pos = 0;
    //
    //        if (mappaWrap != null) {
    //            System.out.println(VUOTA);
    //            message = String.format("Faccio vedere le %d chiavi ordinate di una mappa", mappaWrap.keySet().size());
    //            System.out.println(message);
    //            for (String key : mappaWrap.keySet()) {
    //                pos++;
    //                System.out.print(pos);
    //                System.out.print(PARENTESI_TONDA_END);
    //                System.out.print(SPAZIO);
    //                System.out.println(key);
    //            }
    //        }
    //    }

    //    protected void printWrap(WrapLista wrap, TextService textService) {
    //        if (wrap == null) {
    //            return;
    //        }
    //
    //        System.out.println(String.format("Titolo pagina: %s", textService.isValid(wrap.titoloPagina) ? wrap.titoloPagina : NULL));
    //        System.out.println(String.format("Titolo paragrafo: %s", textService.isValid(wrap.titoloParagrafo) ? wrap.titoloParagrafo : NULL));
    //        System.out.println(String.format("Paragrafo link: %s", textService.isValid(wrap.titoloParagrafoLink) ? wrap.titoloParagrafoLink : NULL));
    //        System.out.println(String.format("Sottoparagrafo: %s", textService.isValid(wrap.titoloSottoParagrafo) ? wrap.titoloSottoParagrafo : NULL));
    //        System.out.println(String.format("Ordinamento: %s", textService.isValid(wrap.ordinamento) ? wrap.ordinamento : NULL));
    //        System.out.println(String.format("Lista: %s", textService.isValid(wrap.lista) ? wrap.lista : NULL));
    //        System.out.println(String.format("giornoNato: %s", textService.isValid(wrap.giornoNato) ? wrap.giornoNato : NULL));
    //        System.out.println(String.format("giornoMorto: %s", textService.isValid(wrap.giornoMorto) ? wrap.giornoMorto : NULL));
    //        System.out.println(String.format("annoNato: %s", textService.isValid(wrap.annoNato) ? wrap.annoNato : NULL));
    //        System.out.println(String.format("annoMorto: %s", textService.isValid(wrap.annoMorto) ? wrap.annoMorto : NULL));
    //        System.out.println(String.format("Didascalia: %s", textService.isValid(wrap.didascalia) ? wrap.didascalia : NULL));
    //        System.out.println(VUOTA);
    //    }

    protected Stream<BioMongoEntity> getBio() {
        return listaBio.stream();
    }

    protected BioMongoEntity creaBio(String wikiTitle) {
        return bioMongoModulo.findByWikiTitle(wikiTitle);
    }

    protected void printWrap(WrapDidascalia wrap, String previsto) {
        System.out.println(VUOTA);
        System.out.println(String.format("Type: %s (%s)", wrap.getType(), previsto));
        System.out.println(String.format("Didascalia: %s", wrap.getDidascalia()));
        System.out.println(String.format("Ordinamento: %s", wrap.getOrdinamento()));
        System.out.println(String.format("Primo livello: %s", wrap.getPrimoLivello()));
        System.out.println(String.format("Secondo livello: %s", wrap.getSecondoLivello()));
        System.out.println(String.format("Terzo livello: %s", wrap.getTerzoLivello()));
        System.out.println(String.format("Quarto livello: %s", wrap.getQuartoLivello()));
    }

    protected boolean validoGiornoAnno(final String nomeLista, final TypeLista typeSuggerito) {
        if (textService.isEmpty(nomeLista)) {
            message = String.format("Manca il nome di %s per un'istanza di type%s[%s]", typeSuggerito.getGiornoAnno(), FORWARD, typeSuggerito.name());
            System.out.println(message);
            return false;
        }

        if (currentModulo.findByKey(nomeLista) == null) {
            message = String.format("%s [%s] indicato NON esiste per un'istanza di type%s[%s]", textService.primaMaiuscola(typeSuggerito.getGiornoAnno()), nomeLista, FORWARD, currentType.name());
            System.out.println(message);
            return false;
        }

        if (currentType != typeSuggerito) {
            message = String.format("Il type suggerito%s[%s] è incompatibile per un'istanza che prevede type%s[%s]", FORWARD, typeSuggerito, FORWARD, currentType);
            System.out.println(message);
            return false;
        }

        return true;
    }

    protected void printMancanoBio(String manca, String nomeLista, TypeLista typeSuggerito) {
        message = String.format("%s di type%s[%s] per %s [%s] è vuoto/a", manca, FORWARD, typeSuggerito.name(), typeSuggerito.getGiornoAnno(), nomeLista);
        System.out.println(message);
        System.out.println("Probabilmente non ci sono biografie valide");
    }

}