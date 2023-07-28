package it.algos.base;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.packages.crono.mese.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.login.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.cognome.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import it.algos.wiki24.backend.packages.nazsingolare.*;
import it.algos.wiki24.backend.packages.pagina.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: ven, 29-apr-2022
 * Time: 20:57
 * Unit test di una classe di servizio <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
public abstract class WikiTest extends AlgosTest {

    public static int MAX = 175;

    protected static final String OBBLIGATORIO = "(obbligatorio - ancora da regolare)";

    protected static final String FACOLTATIVO = "(facoltativo - potrebbe non interessare per questa classe)";


    protected static final String tagCheck = "AlgosBuilderPattern";

    /**
     * Istanza di una interfaccia <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    @Autowired
    protected MongoService mongoService;

    @Autowired
    public WikiBotService wikiBotService;

    @Autowired
    public ElaboraService elaboraService;

    @Autowired
    public DidascaliaService didascaliaService;

    @Autowired
    public QueryService queryService;

    @Autowired
    public BioService bioService;

    @Autowired
    public DownloadService downloadService;

    @Autowired
    public BioBackend bioBackend;

    //    @Autowired
    //    public AttivitaBackend attivitaBackend;
    //
    //    @Autowired
    //    public NazionalitaBackend nazionalitaBackend;

    @Autowired
    public BotLogin botLogin;

    @Autowired
    public DateService dateService;

    protected CrudBackend crudBackend;

    protected WikiBackend wikiBackend;

    @Autowired
    public WikiUtility wikiUtility;

    @Autowired
    public GiornoBackend giornoBackend;

    @Autowired
    public MeseBackend meseBackend;

    @Autowired
    public AnnoBackend annoBackend;

    @Autowired
    public CognomeBackend cognomeBackend;

    @Autowired
    public PaginaBackend paginaBackend;

    @Autowired
    public GiornoWikiBackend giornoWikiBackend;

    @Autowired
    public AnnoWikiBackend annoWikiBackend;

    @Autowired
    public AttSingolareBackend attSingolareBackend;

    @Autowired
    public AttPluraleBackend attPluraleBackend;

    @Autowired
    public NazSingolareBackend nazSingolareBackend;

    @Autowired
    public NazPluraleBackend nazPluraleBackend;

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

    protected Bio bio;

    protected List<Long> listaPageIds;

    protected List<WrapBio> listWrapBio;

    protected List<Bio> listBio;

    protected List<WrapDidascalia> listWrapDidascalia;

    protected List<WrapLista> listWrapLista;

    protected List<WrapTime> listMiniWrap;

    protected LinkedHashMap<String, LinkedHashMap<String, List<WrapDidascalia>>> mappaLista;

    protected AETypeUser typeUser;

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

    protected LinkedHashMap<String, List<WrapLista>> mappaWrap;

    protected Class clazz;

    protected String clazzName;

    protected WrapLista wrapLista;

    protected String backendClazzName;

    protected String collectionName;

    protected boolean ammessoCostruttoreVuoto = false;

    protected boolean istanzaValidaSubitoDopoCostruttore = false;

    protected String nomeParametro;

    protected String metodiEseguibili;

    protected String metodiDaRegolare;

    protected String metodiBuilderPattern;

    protected String metodoDefault;

    //--nome della pagina
    //--esiste sul server wiki
    public static Stream<Arguments> VOCE_BIOGRAFICA() {
        return Stream.of(
                Arguments.of(VUOTA, false),
                Arguments.of("paginaInesistente", false),
                Arguments.of("Matteo Salvini", true),
                Arguments.of("Matteo Renzi", true),
                Arguments.of("Rocco Commisso", true),
                Arguments.of("Charles Collins", true),
                Arguments.of("Bernadette Soubirous", true)

        );
    }

    //--valore grezzo
    //--valore valido
    protected static Stream<Arguments> NOMI() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA),
                Arguments.of("Marcello<ref>Da levare</ref>", "Marcello"),
                Arguments.of("Marcello <ref>Da levare</ref>", "Marcello"),
                Arguments.of("Marcello {{#tag:ref", "Marcello"),
                Arguments.of("Marcello{{#tag:ref", "Marcello"),
                Arguments.of("Marcello <!--", "Marcello"),
                Arguments.of("Marcello<!--", "Marcello"),
                Arguments.of("Marcello{{graffe iniziali", "Marcello"),
                Arguments.of("Marcello {{graffe iniziali", "Marcello"),
                Arguments.of("Marcello <nowiki>", "Marcello"),
                Arguments.of("Marcello<nowiki>", "Marcello"),
                Arguments.of("Marcello=", VUOTA),
                Arguments.of("Marcello =", VUOTA),
                Arguments.of("Marcello ?", VUOTA),
                Arguments.of("Marcello?", VUOTA),
                Arguments.of("Marcello ecc.", VUOTA),
                Arguments.of("Marcelloecc.", VUOTA),
                Arguments.of("Antonio [html:pippoz]", "Antonio"),
                Arguments.of("Roberto Marco Maria", "Roberto"),
                Arguments.of("Colin Campbell (generale)", "Colin"),
                Arguments.of("Giovan Battista", "Giovan Battista"),
                Arguments.of("Anna Maria", "Anna Maria"),
                Arguments.of("testo errato", "Testo"),
                Arguments.of("antonio", "Antonio"),
                Arguments.of("(antonio)", "Antonio"),
                Arguments.of("[antonio]", "Antonio"),
                Arguments.of("[[Roberto]]", "Roberto")
        );
    }

    //--valore grezzo
    //--valore valido
    protected static Stream<Arguments> COGNOMI() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA),
                Arguments.of("Brambilla<ref>Da levare</ref>", "Brambilla"),
                Arguments.of("Brambilla <ref>Da levare</ref>", "Brambilla"),
                Arguments.of("Brambilla {{#tag:ref", "Brambilla"),
                Arguments.of("Brambilla{{#tag:ref", "Brambilla"),
                Arguments.of("Brambilla <!--", "Brambilla"),
                Arguments.of("Brambilla<!--", "Brambilla"),
                Arguments.of("Brambilla{{graffe iniziali", "Brambilla"),
                Arguments.of("Brambilla {{graffe iniziali", "Brambilla"),
                Arguments.of("Brambilla <nowiki>", "Brambilla"),
                Arguments.of("Brambilla<nowiki>", "Brambilla"),
                Arguments.of("Brambilla=", VUOTA),
                Arguments.of("Brambilla =", VUOTA),
                Arguments.of("Brambilla ?", VUOTA),
                Arguments.of("Brambilla?", VUOTA),
                Arguments.of("Brambilla ecc.", VUOTA),
                Arguments.of("Brambillaecc.", VUOTA),
                Arguments.of("Brambilla [html:pippoz]", "Brambilla"),
                Arguments.of("Brambilla Generale", "Brambilla Generale"),
                Arguments.of("Brambilla (generale)", "Brambilla"),
                Arguments.of("Bayley", "Bayley"),
                Arguments.of("Mora Porras", "Mora Porras"),
                Arguments.of("Ørsted", "Ørsted"),
                Arguments.of("bruillard", "Bruillard"),
                Arguments.of("de Bruillard", "de Bruillard"),
                Arguments.of("(Brambilla)", "Brambilla"),
                Arguments.of("[Brambilla]", "Brambilla"),
                Arguments.of("[[Brambilla]]", "Brambilla")
        );
    }


    //--valore grezzo
    //--valore valido
    protected static Stream<Arguments> GIORNI() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA),
                Arguments.of("3 dicembre<ref>Da levare</ref>", "3 dicembre"),
                Arguments.of("3 dicembre <ref>Da levare</ref>", "3 dicembre"),
                Arguments.of("3 dicembre {{#tag:ref", "3 dicembre"),
                Arguments.of("3 dicembre{{#tag:ref", "3 dicembre"),
                Arguments.of("3 dicembre <!--", "3 dicembre"),
                Arguments.of("3 dicembre<!--", "3 dicembre"),
                Arguments.of("3 dicembre{{graffe iniziali", "3 dicembre"),
                Arguments.of("3 dicembre {{graffe iniziali", "3 dicembre"),
                Arguments.of("3 dicembre <nowiki>", "3 dicembre"),
                Arguments.of("3 dicembre<nowiki>", "3 dicembre"),
                Arguments.of("3 luglio=", VUOTA),
                Arguments.of("27  ottobre=", VUOTA),
                Arguments.of("17 marzo ecc.", VUOTA),
                Arguments.of("17 marzoecc..", VUOTA),
                Arguments.of("31 febbraio", VUOTA),
                Arguments.of("4 termidoro", VUOTA),
                Arguments.of("17 marzo [html:pippoz]", "17 marzo"),
                Arguments.of("17 marzo", "17 marzo"),
                Arguments.of("testo errato", VUOTA),
                Arguments.of("4 termidoro", VUOTA),
                Arguments.of("12 [[Luglio]] <ref>Da levare</ref>", "12 luglio"),
                Arguments.of("24aprile", "24 aprile"),
                Arguments.of("2 Novembre", "2 novembre"),
                Arguments.of("2Novembre", "2 novembre"),
                Arguments.of("2novembre", "2 novembre"),
                Arguments.of("3 dicembre?", VUOTA),
                Arguments.of("3 dicembre ?", VUOTA),
                Arguments.of("?", VUOTA),
                Arguments.of("(?)", VUOTA),
                Arguments.of("3 dicembre circa", VUOTA),
                Arguments.of("[[3 dicembre]]ca", VUOTA),
                Arguments.of("(8 agosto)", "8 agosto"),
                Arguments.of("[8 agosto]", "8 agosto"),
                Arguments.of("[[8 agosto]]", "8 agosto"),
                Arguments.of("21[Maggio]", "21 maggio"),
                Arguments.of("21 [Maggio]", "21 maggio"),
                Arguments.of("21 [[Maggio]]", "21 maggio"),
                Arguments.of("[4 febbraio]", "4 febbraio"),
                Arguments.of("settembre 5", VUOTA),
                Arguments.of("27 ottobre <!--eh eh eh-->", "27 ottobre"),
                Arguments.of("29 giugno <nowiki> levare", "29 giugno"),
                Arguments.of("dicembre", VUOTA),
                Arguments.of("12/5", VUOTA),
                Arguments.of("12-5", VUOTA),
                Arguments.of("9 pianepsione", VUOTA),
                Arguments.of("9 [[pianepsione]]", VUOTA)
        );
    }


    //--valore grezzo
    //--valore valido
    protected static Stream<Arguments> ANNI() {
        return Stream.of(
                Arguments.of(VUOTA, VUOTA),
                Arguments.of("3145", VUOTA),
                Arguments.of("1874", "1874"),
                Arguments.of("(1954)", "1954"),
                Arguments.of("[1954]", "1954"),
                Arguments.of("[[1954]]", "1954"),
                Arguments.of("1649 circa", VUOTA),
                Arguments.of("[[1649]]ca", VUOTA),
                Arguments.of("[[1649]]ca.", VUOTA),
                Arguments.of("[[1649]]circa", VUOTA),
                Arguments.of("1649ca", VUOTA),
                Arguments.of("1649 ca", VUOTA),
                Arguments.of("1649 ca.", VUOTA),
                Arguments.of("1649ca.", VUOTA),
                Arguments.of("1649<ref>Da levare</ref>", "1649"),
                Arguments.of("1649 <ref>Da levare</ref>", "1649"),
                Arguments.of("879{{#tag:ref", "879"),
                Arguments.of("879 {{#tag:ref", "879"),
                Arguments.of("1250<!--", "1250"),
                Arguments.of("1250 <!--", "1250"),
                Arguments.of("1817{{graffe iniziali", "1817"),
                Arguments.of("1817 {{graffe iniziali", "1817"),
                Arguments.of("1940<nowiki>", "1940"),
                Arguments.of("1940 <nowiki>", "1940"),
                Arguments.of("1936=", VUOTA),
                Arguments.of("1936 =", VUOTA),
                Arguments.of("1512 ?", VUOTA),
                Arguments.of("1512?", VUOTA),
                Arguments.of("1512 (?)", VUOTA),
                Arguments.of("1512(?)", VUOTA),
                Arguments.of("1880ecc.", VUOTA),
                Arguments.of("1880 ecc.", VUOTA),
                Arguments.of("novecento", VUOTA),
                Arguments.of("secolo", VUOTA),
                Arguments.of("3 secolo", VUOTA),
                Arguments.of("1532/1537", VUOTA),
                Arguments.of("368 a.C. circa", VUOTA),
                Arguments.of("testo errato", VUOTA),
                Arguments.of("424 [html:pippoz]", "424"),
                Arguments.of("754 a.C.", "754 a.C."),
                Arguments.of("754 a.c.", "754 a.C."),
                Arguments.of("754a.c.", "754 a.C."),
                Arguments.of("754a.C.", "754 a.C."),
                Arguments.of("754 A.C.", "754 a.C."),
                Arguments.of("754A.C.", "754 a.C."),
                Arguments.of("754 AC", "754 a.C."),
                Arguments.of("754AC", "754 a.C."),
                Arguments.of("754 ac", "754 a.C."),
                Arguments.of("754ac", "754 a.C."),
                Arguments.of("[[390 a.C.|390]]/[[389 a.C.|389]] o 389/[[388 a.C.]]", VUOTA)
        );
    }

    //--nome anno
    //--type nato/morto
    //--previsto
    protected static Stream<Arguments> TITOLO_ANNI() {
        return Stream.of(
                Arguments.of(null, AETypeLista.annoNascita, VUOTA),
                Arguments.of(VUOTA, AETypeLista.annoMorte, VUOTA),
                Arguments.of("214 a.C.", AETypeLista.annoNascita, "Nati nel 214 a.C."),
                Arguments.of("214", AETypeLista.annoMorte, "Morti nel 214"),
                Arguments.of("735", AETypeLista.annoNascita, "Nati nel 735"),
                Arguments.of("735", AETypeLista.annoMorte, "Morti nel 735"),
                Arguments.of("18 a.C.", AETypeLista.annoNascita, "Nati nel 18 a.C."),
                Arguments.of("18 a.C.", AETypeLista.annoMorte, "Morti nel 18 a.C."),
                Arguments.of("123", AETypeLista.annoNascita, "Nati nel 123"),
                Arguments.of("123", AETypeLista.annoMorte, "Morti nel 123"),
                Arguments.of("1", AETypeLista.annoNascita, "Nati nell'1"),
                Arguments.of("1", AETypeLista.annoMorte, "Morti nell'1"),
                Arguments.of("11", AETypeLista.annoNascita, "Nati nell'11"),
                Arguments.of("11", AETypeLista.annoMorte, "Morti nell'11"),
                Arguments.of("11 a.C.", AETypeLista.annoNascita, "Nati nell'11 a.C."),
                Arguments.of("11 a.C.", AETypeLista.annoMorte, "Morti nell'11 a.C."),
                Arguments.of("24 a.C.", AETypeLista.annoNascita, "Nati nel 24 a.C."),
                Arguments.of("24 a.C.", AETypeLista.annoMorte, "Morti nel 24 a.C."),
                Arguments.of("865 a.C.", AETypeLista.annoNascita, "Nati nell'865 a.C."),
                Arguments.of("865 a.C.", AETypeLista.annoMorte, "Morti nell'865 a.C."),
                Arguments.of("1 a.C.", AETypeLista.annoNascita, "Nati nell'1 a.C."),
                Arguments.of("1 a.C.", AETypeLista.annoMorte, "Morti nell'1 a.C.")
        );
    }


    //--nome giorno
    //--type nato/morto
    //--previsto
    protected static Stream<Arguments> TITOLO_GIORNI() {
        return Stream.of(
                Arguments.of(null, AETypeLista.giornoNascita, VUOTA),
                Arguments.of(VUOTA, AETypeLista.giornoMorte, VUOTA),
                Arguments.of("7 aprile", AETypeLista.giornoNascita, "Nati il 7 aprile"),
                Arguments.of("7 aprile", AETypeLista.giornoMorte, "Morti il 7 aprile"),
                Arguments.of("1º ottobre", AETypeLista.giornoNascita, "Nati il 1º ottobre"),
                Arguments.of("1º ottobre", AETypeLista.giornoMorte, "Morti il 1º ottobre"),
                Arguments.of("7 agosto", AETypeLista.giornoNascita, "Nati il 7 agosto"),
                Arguments.of("7 agosto", AETypeLista.giornoMorte, "Morti il 7 agosto"),
                Arguments.of("8 dicembre", AETypeLista.giornoNascita, "Nati l'8 dicembre"),
                Arguments.of("8 dicembre", AETypeLista.giornoMorte, "Morti l'8 dicembre"),
                Arguments.of("11 maggio", AETypeLista.giornoNascita, "Nati l'11 maggio"),
                Arguments.of("11 maggio", AETypeLista.giornoMorte, "Morti l'11 maggio")
        );
    }

    //--titolo
    //--pagina valida
    protected static Stream<Arguments> PAGINE_BIO() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(VUOTA, false),
                Arguments.of("Roberto il Forte", true),
                Arguments.of("Claude de Chastellux", true),
                Arguments.of("John Murphy (politico statunitense)", true),
                Arguments.of("Meena Keshwar Kamal", true),
                Arguments.of("Werburga", true),
                Arguments.of("Roman Protasevič", true),
                Arguments.of("Aldelmo di Malmesbury", true),
                Arguments.of("Aelfric il grammatico", true),
                Arguments.of("Bernart Arnaut d'Armagnac", true),
                Arguments.of("Elfleda di Whitby", true),
                Arguments.of("Gaetano Anzalone", true),
                Arguments.of("Colin Campbell (generale)", true),
                Arguments.of("Louis Winslow Austin", true),
                Arguments.of("San Nicanore", true),
                Arguments.of("Regno di Napoli (1908-1745)", false),
                Arguments.of("Regno di Napoli (1806-1815)", false),
                Arguments.of("Rossi", false),
                Arguments.of("Bartolomeo Giuseppe Amico di Castell'Alfero", true),
                Arguments.of("Lucio Anneo Seneca", true),
                Arguments.of("Bodhidharma", true),
                Arguments.of("Aloisio Gonzaga", true),
                Arguments.of("Alex Bagnoli", true),
                Arguments.of("Ashur-uballit I", true)
        );
    }


    //--wikiTitle
    //--numero parametri
    protected static Stream<Arguments> BIOGRAFIE() {
        return Stream.of(
                Arguments.of(VUOTA, 0),
                Arguments.of("Jacques de Molay", 15),
                Arguments.of("Roberto il Forte", 17),
                Arguments.of("Agnese di Borgogna", 17),
                Arguments.of("Matteo Renzi", 14),
                Arguments.of("Hunter King", 10),
                Arguments.of("Laura Mancinelli", 17),
                Arguments.of("Johann Georg Kastner", 14),
                Arguments.of("Meirchion Gul", 15),
                Arguments.of("Vincenzo Vacirca", 15),
                Arguments.of("Ashur-uballit I", 15),
                Arguments.of("Albia Dominica", 15),
                Arguments.of("Angelo Inganni", 12),
                Arguments.of("Andrey Guryev", 17),
                Arguments.of("Ingen Ryūki", 16),
                Arguments.of("Giorgio Merula", 17),
                Arguments.of("Rob Paulsen", 13),
                Arguments.of("Aleksandr Isaevič Solženicyn", 22),
                Arguments.of("Aloisio Gonzaga", 19),
                Arguments.of("Alex Bagnoli", 18),
                Arguments.of("Harry Fielder", 15),
                Arguments.of("Yehudai Gaon", 16),
                Arguments.of("Kaku Takagawa", 13),
                Arguments.of("Filippo Tornielli", 12),
                Arguments.of("Mario Tosi (fotografo)", 13),
                Arguments.of("Giuseppe Trombone de Mier", 12),
                Arguments.of("Herlindis di Maaseik", 17),
                Arguments.of("Rinaldo II di Bar", 14),
                Arguments.of("Harald II di Norvegia", 17)
        );
    }

    //--titolo
    //--pagina esistente
    //--biografia esistente
    protected static Stream<Arguments> PAGINE_E_CATEGORIE() {
        return Stream.of(
                Arguments.of(null, false, false),
                Arguments.of(VUOTA, false, false),
                Arguments.of("Roman Protasevič", true, true),
                Arguments.of("Louis Winslow Austin", true, true),
                Arguments.of("Categoria:Nati nel 1435", true, false),
                Arguments.of("2741616|27416167", false, false),
                Arguments.of("Categoria:Nati nel 2387", false, false),
                Arguments.of("Categoria:BioBot", true, false),
                Arguments.of("Categoria:Supercalifragilistichespiralidoso", false, false),
                Arguments.of("Supercalifragilistichespiralidoso", true, false),
                Arguments.of("Regno di Napoli (1908-1745)", false, false),
                Arguments.of("Rossi", true, false)
        );
    }

    //--categoria
    //--esiste come anonymous
    //--esiste come user, admin
    //--esiste come bot
    protected static Stream<Arguments> CATEGORIE() {
        return Stream.of(
                Arguments.of(null, false, false, false),
                Arguments.of(VUOTA, false, false, false),
                Arguments.of("Inesistente", false, false, false),
                Arguments.of("Nati nel 1435", true, true, true),
                Arguments.of("2741616|27416167", false, false, false),
                Arguments.of("Ambasciatori britannici in Brasile", true, true, true),
                Arguments.of("Nati nel 1935", false, true, true)
        );
    }


    //--nome singolarePlurale
    //--esiste
    protected static Stream<Arguments> ATTIVITA_TRUE() {
        return Stream.of(
                Arguments.of(VUOTA, false),
                Arguments.of("politico", true),
                Arguments.of("politici", true),
                Arguments.of("errata", false),
                Arguments.of("fantasmi", false),
                Arguments.of("attrice", true),
                Arguments.of("attore", true),
                Arguments.of("attori", true),
                Arguments.of("nessuna", false),
                Arguments.of("direttore di scena", false),
                Arguments.of("accademici", false),
                Arguments.of("vescovo ariano", true)
        );
    }

    //--nome singolare
    //--esiste
    public static Stream<Arguments> NAZIONALITA_SINGOLARE() {
        return Stream.of(
                Arguments.of(VUOTA, false),
                Arguments.of("turco", true),
                Arguments.of("errata", false),
                Arguments.of("tedesca", true),
                Arguments.of("direttore di scena", false),
                Arguments.of("brasiliano", true),
                Arguments.of("vescovo ariano", false),
                Arguments.of("errata", false),
                Arguments.of("britannici", false),
                Arguments.of("tedesco", true),
                Arguments.of("tedeschi", false)
        );
    }


    //--nome nazionalità plurale (solo minuscola)
    //--esiste
    public static Stream<Arguments> NAZIONALITA_PLURALE() {
        return Stream.of(
                Arguments.of(VUOTA, false),
                Arguments.of("turco", false),
                Arguments.of("afghani", true),
                Arguments.of("Afghani", false),
                Arguments.of("andorrani", true),
                Arguments.of("tedesca", false),
                Arguments.of("arabi", true),
                Arguments.of("tedesco", false)
        );
    }

    //--nome singolarePlurale
    //--esiste
    public static Stream<Arguments> NAZIONALITA_TRUE() {
        return Stream.of(
                Arguments.of(VUOTA, false),
                Arguments.of("turco", true),
                Arguments.of("errata", false),
                Arguments.of("tedesca", true),
                Arguments.of("direttore di scena", false),
                Arguments.of("brasiliano", true),
                Arguments.of("vescovo ariano", false),
                Arguments.of("errata", false),
                Arguments.of("britannici", true),
                Arguments.of("tedesco", true),
                Arguments.of("tedeschi", true)
        );
    }

    //--nome singolare
    //--esiste
    public static Stream<Arguments> ATTIVITA_SINGOLARE() {
        return Stream.of(
                Arguments.of(VUOTA, false),
                Arguments.of("politico", true),
                Arguments.of("politici", false),
                Arguments.of("errata", false),
                Arguments.of("attrice", true),
                Arguments.of("attrici", false),
                Arguments.of("direttore di scena", false),
                Arguments.of("vescovo ariano", true)

        );
    }


    //--cognome
    //--flag diacritico
    protected static Stream<Arguments> DIACRITICI() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(VUOTA, false),
                Arguments.of("Díaz", true),
                Arguments.of("Diaz", false),
                Arguments.of("Fernández", true),
                Arguments.of("Fernandez", false),
                Arguments.of("García", true),
                Arguments.of("Garcia", false),
                Arguments.of("González", true),
                Arguments.of("Gonzalez", false),
                Arguments.of("Gómez", true),
                Arguments.of("Gomez", false),
                Arguments.of("Hernández", true),
                Arguments.of("Hernandez", false),
                Arguments.of("Itō", true),
                Arguments.of("Ito", false),
                Arguments.of("López", true),
                Arguments.of("Lopez", false),
                Arguments.of("Martínez", true),
                Arguments.of("Martinez", false),
                Arguments.of("Müller", true),
                Arguments.of("Muller", false),
                Arguments.of("Rodríguez", true),
                Arguments.of("Rodriguez", false),
                Arguments.of("Sánchez", true),
                Arguments.of("Sanchez", false)
        );
    }

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    protected void setUpAll() {
        MockitoAnnotations.openMocks(this);
        slf4jLogger = LoggerFactory.getLogger("wiki23.admin");

        initMocks();
        //        fixRiferimentiIncrociati();
    }

    /**
     * Inizializzazione dei service <br>
     * Devono essere tutti 'mockati' prima di iniettare i riferimenti incrociati <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void initMocks() {
        super.initMocks();

        assertNotNull(wikiBotService);
        assertNotNull(elaboraService);
        assertNotNull(didascaliaService);
        assertNotNull(bioService);
        assertNotNull(bioBackend);
        assertNotNull(botLogin);
        assertNotNull(queryService);
        assertNotNull(downloadService);
        assertNotNull(wikiUtility);
        assertNotNull(giornoBackend);
        assertNotNull(meseBackend);
        assertNotNull(annoBackend);
        assertNotNull(cognomeBackend);
        assertNotNull(giornoWikiBackend);
        assertNotNull(annoWikiBackend);

        clazzName = clazz != null ? clazz.getSimpleName() : NULL;
    }

    //    /**
    //     * Regola tutti riferimenti incrociati <br>
    //     * Deve essere fatto dopo aver costruito tutte le referenze 'mockate' <br>
    //     * Nelle sottoclassi devono essere regolati i riferimenti dei service specifici <br>
    //     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
    //     */
    //    protected void fixRiferimentiIncrociati() {
    //        super.fixRiferimentiIncrociati();
    //
    //        elaboraService.wikiBotService = wikiBotService;
    //        wikiUtility.queryService = queryService;
    //        wikiUtility.regexService = regexService;
    //        attivitaBackend.logger = logService;
    //        nazionalitaBackend.logger = logService;
    //        bioBackend.giornoBackend = giornoBackend;
    //        bioBackend.meseBackend = meseBackend;
    //        cognomeBackend.bioBackend = bioBackend;
    //    }
    //

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
        bio = null;
        listaPageIds = null;
        listWrapBio = null;
        listBio = null;
        listWrapDidascalia = null;
        listWrapLista = null;
        listMiniWrap = null;
        mappaWrap = null;
        mappaLista = null;
        typeUser = null;
        pageId = 0L;
        wrapLista = null;
    }

    @Test
    @Order(0)
    @DisplayName("0 - Check iniziale dei parametri necessari per il test")
    void checkIniziale() {
        System.out.println("0 - Check iniziale dei parametri necessari per il test");

        if (clazz == null) {
            message = String.format("Manca il flag '%s' nel metodo setUpAll() di questa classe [%s] di test", "clazz", this.getClass().getSimpleName());
            logService.error(new WrapLog().message(message).type(AETypeLog.test));
            assertTrue(false);
            return;
        }

        if (textService.isEmpty(clazzName)) {
            message = String.format("Manca il flag '%s' nel metodo setUpAll() di questa classe [%s] di test", "clazzName", this.getClass().getSimpleName());
            logService.error(new WrapLog().message(message).type(AETypeLog.test));
            assertTrue(false);
            return;
        }

        if (textService.isEmpty(collectionName)) {
            message = String.format("Manca il flag '%s' nel metodo setUpAll() di questa classe [%s] di test", "collectionName", this.getClass().getSimpleName());
            logService.error(new WrapLog().message(message).type(AETypeLog.test));
            assertTrue(false);
            return;
        }
    }

    @Test
    @Order(1)
    @DisplayName("1 - Costruttore base con/senza parametri")
    void costruttoreBase() {
        if (this.ammessoCostruttoreVuoto) {
            System.out.println(String.format("1 - Costruttore base new %s(xxx) con ALMENO un parametro", clazzName));
            System.out.println(VUOTA);

            System.out.println(String.format("La classe [%s] non prevede un costruttore SENZA parametri", clazzName));
            System.out.println(String.format("Non è possibile creare un'istanza di [%s] SENZA parametri", clazzName));
            System.out.println(String.format("new %s() NON funziona (dà errore)", clazzName));
            System.out.println("È obbligatorio usare il costruttore con un parametro per la creazione.");
            System.out.println("Ci potrebbero essere anche altri costruttori oltre a quello base con un parametro.");
        }
        else {
            System.out.println(String.format("1 - Costruttore base new %s() SENZA parametri", clazzName));
            System.out.println(VUOTA);

            System.out.println(String.format("La classe [%s] prevede un costruttore SENZA parametri", clazzName));
            System.out.println(String.format("new %s() funziona", clazzName));
            System.out.println(String.format("new %s(xxx) NON funziona", clazzName));
            System.out.println(String.format("Costruttore base senza parametri per un'istanza di %s", clazzName));
            System.out.println("Questa classe NON accetta parametri nel costruttore");
        }
    }


    @Test
    @Order(2)
    @DisplayName("2 - appContext.getBean con/senza parametri")
    void getBean() {
        Object istanzaGenerica = null;

        if (this.ammessoCostruttoreVuoto) {
            System.out.println(String.format("2 - appContext.getBean(%s.class, xxx) con ALMENO un parametro", clazzName));
            System.out.println(VUOTA);
            System.out.println("Errore previsto (nel test). Tipo warning.");

            System.out.println(String.format("La classe [%s] non prevede un costruttore SENZA parametri", clazzName));
            System.out.println(String.format("Non è possibile creare un'istanza di [%s] SENZA parametri", clazzName));
            System.out.println(String.format("appContext.getBean(%s.class) NON funziona (dà errore)", clazzName));
            System.out.println("È obbligatorio usare il costruttore con un parametro per la creazione.");
            System.out.println("Ci potrebbero essere anche altri costruttori oltre a quello base con un parametro.");
            System.out.println(VUOTA);

            try {
                istanzaGenerica = appContext.getBean(clazz);
            } catch (Exception unErrore) {
                logService.warn(new WrapLog().exception(unErrore));
                return;
            }
            assertNull(istanzaGenerica);
        }
        else {
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
                logService.error(new WrapLog().exception(unErrore));
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
    }


    // 5 - senzaParametroNelCostruttore
    protected void fixSenzaParametroNelCostruttore() {
        System.out.println("5 - senzaParametroNelCostruttore");
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
            logService.info(new WrapLog().message(message).type(AETypeLog.test));

            if (istanzaCostruitaSenzaParametri) {
                message = String.format("Sono riuscito a costruire un'istanza della classe [%s]", clazzName);
                logService.info(new WrapLog().message(message).type(AETypeLog.test));

                message = String.format("Come previsto appContext.getBean(%s.class) funziona", clazzName);
                logService.info(new WrapLog().message(message).type(AETypeLog.test));

                message = String.format("Il flag '%s' nel metodo setUpAll() di questa classe [%s] è corretto", "ammessoCostruttoreVuoto", this.getClass().getSimpleName());
                logService.info(new WrapLog().message(message).type(AETypeLog.test));

                message = String.format("Nella classe [%s] o in una sua superclasse esiste un costruttore senza parametri", clazzName);
                logService.info(new WrapLog().message(message).type(AETypeLog.test));

                message = String.format("Costruita come previsto un'istanza valida della classe [%s]", clazzName);
                logService.warn(new WrapLog().message(message).type(AETypeLog.test));
                assertTrue(true);
            }
            else {
                message = String.format("Non sono riuscito a costruire un'istanza della classe [%s]", clazzName);
                logService.info(new WrapLog().message(message).type(AETypeLog.test));

                message = String.format("Contrariamente al previsto appContext.getBean(%s.class) NON funziona", clazzName);
                logService.info(new WrapLog().message(message).type(AETypeLog.test));

                message = String.format("Potrebbe essere sbagliato il flag '%s' nel metodo setUpAll() di questa classe [%s]", "ammessoCostruttoreVuoto", this.getClass().getSimpleName());
                logService.info(new WrapLog().message(message).type(AETypeLog.test));

                message = String.format("Potrebbe mancare il costruttore senza parametri nella classe [%s] o nelle sue superclassi", clazzName);
                logService.info(new WrapLog().message(message).type(AETypeLog.test));

                message = String.format("Nella classe [%s] manca il costruttore senza parametri", clazzName);
                logService.warn(new WrapLog().message(message).type(AETypeLog.test));
                assertTrue(false);
            }
        }
        else {
            message = String.format("La classe [%s] prevede un parametro (obbligatorio) nel costruttore", clazzName);
            logService.info(new WrapLog().message(message).type(AETypeLog.test));

            if (istanzaCostruitaSenzaParametri) {
                message = String.format("Sono riuscito a costruire un'istanza della classe [%s] anche SENZA un parametro nel costruttore", clazzName);
                logService.info(new WrapLog().message(message).type(AETypeLog.test));

                message = String.format("Contrariamente al previsto appContext.getBean(%s.class) funziona", clazzName);
                logService.info(new WrapLog().message(message).type(AETypeLog.test));

                message = String.format("Potrebbe essere sbagliato il flag '%s' nel metodo setUpAll() di questa classe [%s]", "ammessoCostruttoreVuoto", this.getClass().getSimpleName());
                logService.info(new WrapLog().message(message).type(AETypeLog.test));

                message = String.format("Potrebbe esserci un costruttore senza parametri nella classe [%s] o nelle sue superclassi", clazzName);
                logService.info(new WrapLog().message(message).type(AETypeLog.test));

                message = String.format("La classe [%s] ha un costruttore NON previsto", clazzName);
                logService.warn(new WrapLog().message(message).type(AETypeLog.test));
                assertTrue(false);
            }
            else {
                message = String.format("Non sono riuscito a costruire un'istanza della classe [%s] SENZA un parametro nel costruttore", clazzName);
                logService.info(new WrapLog().message(message).type(AETypeLog.test));

                message = String.format("Come previsto appContext.getBean(%s.class) NON funziona", clazzName);
                logService.info(new WrapLog().message(message).type(AETypeLog.test));

                message = String.format("Il flag '%s' nel metodo setUpAll() di questa classe [%s] di test è corretto", "ammessoCostruttoreVuoto", this.getClass().getSimpleName());
                logService.info(new WrapLog().message(message).type(AETypeLog.test));

                message = String.format("Nella classe [%s] non esiste un costruttore senza parametri", clazzName);
                logService.info(new WrapLog().message(message).type(AETypeLog.test));

                message = String.format("La classe [%s] ha solo costruttori coi parametri (come previsto)", clazzName);
                logService.warn(new WrapLog().message(message).type(AETypeLog.test));
                assertTrue(true);
            }
        }
    }


    protected void fixCheckParametroNelCostruttore(String parametro, String valore, String check, String funzione) {
        System.out.println("6 - checkParametroNelCostruttore");
        System.out.println(String.format("Costruisce un'istanza con un parametro farlocco (tipo '%s')", valore));
        System.out.println(VUOTA);
        Object istanza = null;
        boolean isCostruttoreValido = false;

        try {
            istanza = appContext.getBean(clazz, valore);
        } catch (Exception unErrore) {
            logService.error(new WrapLog().exception(new AlgosException(unErrore)));
            assertTrue(false);
        }
        assertNotNull(istanza);

        if (istanza instanceof AlgosBuilderPattern istanzaCheck) {
            isCostruttoreValido = istanzaCheck.isCostruttoreValido();
        }
        else {
            message = String.format("La classe [%s] NON implementa l'interfaccia %s", clazzName, tagCheck);
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
            message = String.format("La classe [%s] NON controlla la validità del parametro usato nel costruttore", clazzName);
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
            message = String.format("Istanza costruita con appContext.getBean(%s.class, %s)", clazzName, valore);
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
            message = String.format("L'istanza esiste ma non ci sono altre informazioni sulla fruibilità della stessa");
            logService.warn(new WrapLog().message(message).type(AETypeLog.test));
            message = String.format("Considera la possibilità di implementare l'interfaccia %s per la classe [%s]", tagCheck, clazzName);
            logService.warn(new WrapLog().message(message).type(AETypeLog.test));
            return;
        }

        message = String.format("La classe [%s] implementa l'interfaccia %s", clazzName, tagCheck);
        logService.info(new WrapLog().message(message).type(AETypeLog.test));
        message = String.format("Controlla la validità del parametro usato nel costruttore");
        logService.info(new WrapLog().message(message).type(AETypeLog.test));
        message = String.format("Istanza costruita con appContext.getBean(%s.class, %s)", clazzName, valore);
        logService.info(new WrapLog().message(message).type(AETypeLog.test));
        message = String.format("Controllo nel metodo %s.%s, invocato da  @PostConstruct", clazzName, check);
        logService.info(new WrapLog().message(message).type(AETypeLog.test));
        message = String.format("Funzione%s%s.%s(%s)", FORWARD, backendClazzName, funzione, valore);
        logService.info(new WrapLog().message(message).type(AETypeLog.test));
        System.out.println(VUOTA);

        if (isCostruttoreValido) {
            message = String.format("Istanza valida col valore accettabile [%s] del parametro '%s' nel costruttore", valore, parametro);
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
        }
        else {
            message = String.format("Istanza NON valida perché il valore [%s] per il parametro chiave della collection '%s' non è previsto", valore, collectionName);
            logService.warn(new WrapLog().message(message).type(AETypeLog.test));
        }
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }

    protected void fixBeanStandard(String nomeParametroCostruttore, String valore, String metodiEseguibili, String metodoDaRegolare, String metodiBuilderPattern) {
        System.out.println(String.format("7 - Istanza della classe [%s] costruita col solo parametro e SENZA altre regolazioni", clazzName));
        System.out.println(VUOTA);
        Object istanza = null;
        boolean isCostruttoreValido;
        boolean isBuildPatternValido;

        if (clazz == null) {
            message = String.format("Manca il flag '%s' nel metodo setUpAll() della classe '%s'", "clazz", this.getClass().getSimpleName());
            logService.error(new WrapLog().message(message).type(AETypeLog.test));
            assertTrue(false);
            return;
        }
        if (textService.isEmpty(collectionName)) {
            message = String.format("Manca il flag '%s' nel metodo setUpAll() della classe '%s'", "collectionName", this.getClass().getSimpleName());
            logService.error(new WrapLog().message(message).type(AETypeLog.test));
            assertTrue(false);
            return;
        }

        try {
            istanza = appContext.getBean(clazz, valore);
        } catch (Exception unErrore) {
            logService.error(new WrapLog().exception(new AlgosException(unErrore)));
        }
        assertNotNull(istanza);

        if (istanza instanceof AlgosBuilderPattern istanzaCheck) {
            isCostruttoreValido = istanzaCheck.isCostruttoreValido();
        }
        else {
            message = String.format("La classe [%s] NON implementa l'interfaccia %s", clazzName, tagCheck);
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
            message = String.format("La classe [%s] NON controlla la validità del parametro usato nel costruttore", clazzName);
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
            message = String.format("Istanza costruita con appContext.getBean(%s.class, %s)", clazzName, valore);
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
            message = String.format("L'istanza esiste ma non ci sono altre informazioni sulla fruibilità della stessa");
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
            return;
        }

        if (istanza instanceof AlgosBuilderPattern istanzaBuildPattern) {
            isBuildPatternValido = istanzaBuildPattern.isPatternCompleto();
        }
        else {
            message = String.format("La classe [%s] NON implementa l'interfaccia %s", clazzName, tagCheck);
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
            message = String.format("La classe [%s] NON controlla se esistono ulteriori regolazioni del BuilderPattern", clazzName);
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
            message = String.format("Istanza costruita con appContext.getBean(%s.class, %s)", clazzName, valore);
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
            message = String.format("L'istanza esiste ma non ci sono altre informazioni sulla fruibilità della stessa");
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
            return;
        }

        message = String.format("Costruisce un'istanza col parametro '%s'", valore);
        logService.warn(new WrapLog().message(message).type(AETypeLog.test));
        if (isCostruttoreValido) {
            message = String.format("La classe [%s] implementa l'interfaccia %s", clazzName, tagCheck);
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
            message = String.format("Istanza con costruttore valido - esiste il valore del parametro chiave [%s] nella collection '%s'", valore, collectionName);
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
        }
        else {
            message = String.format("La classe [%s] implementa l'interfaccia %s", clazzName, tagCheck);
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
            message = String.format("Istanza NON valida - non esiste il valore del parametro chiave [%s] nella collection '%s'", valore, collectionName);
            logService.warn(new WrapLog().message(message).type(AETypeLog.test));
            System.out.println(VUOTA);
            System.out.println(VUOTA);
            return;
        }

        message = String.format("La classe [%s] implementa l'interfaccia %s", clazzName, tagCheck);
        logService.info(new WrapLog().message(message).type(AETypeLog.test));
        if (isBuildPatternValido) {
            message = String.format("L'istanza è valida/eseguibile da subito, senza ulteriori regolazioni del BuilderPattern");
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
            message = String.format("BuilderPattern%s%s", FORWARD, metodiBuilderPattern);
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
            message = String.format("Istanza [%s.%s] pronta per %s", collectionName, textService.primaMaiuscola(valore), metodiEseguibili);
            logService.warn(new WrapLog().message(message).type(AETypeLog.test));
            System.out.println(VUOTA);
            //            this.debug("xxx",valore,"(nessuno)",true,true);
        }
        else {
            message = "L'istanza NON è utilizzabile, perché occorrono ulteriori regolazioni del BuilderPattern";
            logService.info(new WrapLog().message(message).type(AETypeLog.test));
            message = String.format("BuilderPattern%s%s", FORWARD, metodiBuilderPattern);
            logService.info(new WrapLog().message(message).type(AETypeLog.test));

            message = String.format("Prova ad usare i metodi%s%s", FORWARD, metodiDaRegolare);
            logService.warn(new WrapLog().message(message).type(AETypeLog.test));
            System.out.println(VUOTA);
        }
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
        System.out.println(String.format("8 - Istanza valida della classe [%s] costruita col parametro SENZA altre regolazioni", clazzName));
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
        System.out.println(String.format("Funzione%s%s.%s(%s)", FORWARD, backendClazzName, funzione, sorgente));
        message = String.format("Il valore '%s' non è accettabile per un'istanza valida di classe [%s]", valore, clazzName);
        logService.warn(new WrapLog().message(message));
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
        logService.warn(new WrapLog().message(message));
    }


    // 9 - Metodi builderPattern per validare l'istanza
    protected void fixBuilderPatternIniziale() {
        System.out.println("9 - Metodi builderPattern per validare l'istanza");
        System.out.println(VUOTA);

        if (istanzaValidaSubitoDopoCostruttore) {
            message = String.format("Una istanza di classe [%s] è immediatamente eseguibile dopo un costruttore (valido)", clazzName);
            logService.warn(new WrapLog().message(message).type(AETypeLog.test));
        }
        else {
            message = String.format("Anche con un costruttore valido, una istanza di classe [%s] NON è immediatamente eseguibile", clazzName);
            logService.warn(new WrapLog().message(message).type(AETypeLog.test));
        }

        System.out.println(String.format("Istanza di classe [%s]", clazzName));
        System.out.println(String.format("Istanza valida subito dopo il costruttore: %s", istanzaValidaSubitoDopoCostruttore));
        System.out.println(String.format("Metodi BuilderPattern disponibili: %s", metodiBuilderPattern));
        System.out.println(String.format("Metodo di default: %s", textService.isValid(metodoDefault) ? metodoDefault : NULL));
        System.out.println(String.format("Metodi da regolare: %s", textService.isValid(metodiDaRegolare) ? metodiDaRegolare : NULL));
        System.out.println(String.format("Metodi eseguibili: %s", textService.isValid(metodiEseguibili) ? metodiEseguibili : NULL));

        if (istanzaValidaSubitoDopoCostruttore) {
            message = String.format("Si possono invocare i metodi eseguibili", clazz.getSimpleName());
            logService.warn(new WrapLog().message(message).type(AETypeLog.test));
        }
        else {
            message = String.format("Occorre invocare uno dei metodi builderPattern da regolare");
            logService.warn(new WrapLog().message(message).type(AETypeLog.test));
        }

        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }


    protected void fixBuilderPattern(Object istanza, String keyValue, String nomeMetodo) {
        //        debug((Object) istanza, keyValue, nomeMetodo);
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

    protected void fixMappaWrapKey(final String sorgente, final LinkedHashMap<String, List<WrapLista>> mappaWrap) {
        System.out.println(VUOTA);
        System.out.println("40 - Key della mappaWrap");

        if (mappaWrap != null && mappaWrap.size() > 0) {
            message = String.format("La mappaWrap della lista %s ha %d chiavi (paragrafi) per %d didascalie", sorgente, mappaWrap.size(), wikiUtility.getSizeAllWrap(mappaWrap));
            System.out.println(message);
            printMappaWrapKeyOrder(mappaWrap);
        }
        else {
            message = "La mappaWrap è nulla";
            System.out.println(message);
        }
    }

    protected void fixMappaWrapDidascalie(final String sorgente, final LinkedHashMap<String, List<WrapLista>> mappaWrap) {
        fixMappaWrapDidascalie(sorgente, mappaWrap, "50 - Mappa STANDARD wrapLista (paragrafi e righe)");
    }

    protected void fixMappaWrapDidascalie(final String sorgente, final LinkedHashMap<String, List<WrapLista>> mappaWrap, String incipit) {
        System.out.println(VUOTA);
        System.out.println(incipit);

        if (mappaWrap != null && mappaWrap.size() > 0) {
            message = String.format("La mappaWrap della lista %s ha %d didascalie", sorgente, wikiUtility.getSizeAllWrap(mappaWrap));
            System.out.println(message);
            printMappaDidascalie(mappaWrap);
        }
        else {
            message = "La mappaWrap è nulla";
            System.out.println(message);
        }
    }


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

        System.out.println("Risultato");
        System.out.println(String.format("Status: %s", result.isValido() ? "true" : "false"));
        System.out.println(String.format("Modificata: %s", result.isModificata() ? "true" : "false"));
        System.out.println(String.format("Query: %s", result.getQueryType()));
        System.out.println(String.format("Title: %s", result.getWikiTitle()));
        System.out.println(String.format("PageId: %s", result.getPageid()));
        System.out.println(String.format("Namespace: %s", result.getNameSpace()));
        System.out.println(String.format("Type: %s", result.getTypePage()));
        System.out.println(String.format("User: %s", result.getUserType()));
        System.out.println(String.format("Limit: %d", result.getLimit()));
        System.out.println(String.format("Summary: %s", result.getSummary()));
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
        printWrapBio(result.getWrap());
    }


    protected void printWrapBio(WrapBio wrap) {
        if (wrap != null) {
            System.out.println(VUOTA);
            System.out.println(String.format("Wrap valido: %s", wrap.isValida() ? "true" : "false"));
            System.out.println(String.format("Wrap title: %s", wrap.getTitle()));
            System.out.println(String.format("Wrap pageid: %s", wrap.getPageid()));
            System.out.println(String.format("Wrap type: %s", wrap.getType()));
            System.out.println(String.format("Wrap timeStamp: %s", wrap.getTimeStamp()));
            System.out.println(String.format("Wrap templBio: %s", textService.isValid(wrap.getTemplBio()) ? "valido" : "non esiste"));
            if (textService.isValid(wrap.getTemplBio())) {
                System.out.println(String.format("templBio: %s", getMax(wrap.getTemplBio())));
            }
        }
    }

    protected void printMiniWrap(List<WrapTime> listaMiniWrap) {
        if (listaMiniWrap != null) {
            System.out.println(VUOTA);
            System.out.println(String.format("Pageid (wikiTitle) and last timestamp"));
            System.out.println(VUOTA);
            for (WrapTime wrap : listaMiniWrap) {
                System.out.println(String.format("%s (%s)%s%s", wrap.getPageid(), wrap.getWikiTitle(), FORWARD, wrap.getLastModifica()));
            }
        }
    }

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

    protected void printBio(List<Bio> listaBio, String titolo) {
        int cont = 0;
        String message = textService.isValid(titolo) ? String.format(" di %s", titolo) : VUOTA;
        if (listaBio != null) {
            System.out.println(String.format("Nella lista ci sono %d biografie%s", listaBio.size(), message));
            System.out.println(VUOTA);
            for (Bio bio : listaBio) {
                cont++;
                System.out.print(cont);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.println(bio != null ? bio.wikiTitle : VUOTA);
            }
        }
    }

    protected void printBio(List<Bio> listaBio) {
        printBio(listaBio, VUOTA);
    }


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


    protected void printWrapDidascalia(List<WrapDidascalia> lista) {
        int cont = 0;
        String value;

        if (lista != null) {
            for (WrapDidascalia wrap : lista) {
                cont++;
                value = wrap.getWikiTitle();

                System.out.print(TAB);
                System.out.print(TAB);
                System.out.print(cont);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);

                System.out.print(textService.setQuadre(value));
                System.out.print(SPAZIO);

                System.out.println(VUOTA);
            }
        }
    }


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


    protected void printMappaSub(LinkedHashMap<String, List<WrapDidascalia>> mappaSub) {
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

                System.out.print(textService.setQuadre(key));
                System.out.print(SPAZIO);

                System.out.println(VUOTA);

                printWrapDidascalia(lista);
            }
        }
    }


    protected void printWrapLista(List<WrapLista> listWrapLista) {
        int max = 15;
        if (listWrapLista != null) {
            message = String.format("Faccio vedere una lista delle prime %d didascalie", max);
            System.out.println(message);
            message = "Paragrafo, paragrafoLink, ordinamento, sottoParagrafo, didascaliaBreve";
            System.out.println(message);
            System.out.println(VUOTA);
            printSub(listWrapLista.subList(0, Math.min(max, listWrapLista.size())));
            System.out.println();
        }
    }

    protected void printSub(List<WrapLista> listWrapLista) {
        if (listWrapLista == null) {
            return;
        }

        for (WrapLista wrap : listWrapLista) {
            System.out.print(wrap.titoloParagrafo);
            System.out.print(SEP);
            if (textService.isValid(wrap.titoloParagrafoLink)) {
                System.out.print(wrap.titoloParagrafoLink);
            }
            else {
                System.out.print("null");
            }
            System.out.print(SEP);
            if (textService.isValid(wrap.ordinamento)) {
                System.out.print(wrap.ordinamento);
            }
            else {
                System.out.print("null");
            }
            System.out.print(SEP);
            if (textService.isValid(wrap.titoloSottoParagrafo)) {
                System.out.print(wrap.titoloSottoParagrafo);
            }
            else {
                System.out.print("null");
            }
            System.out.print(SEP);
            System.out.println(wrap.didascaliaBreve);
        }
        System.out.println(VUOTA);
    }

    protected void printMappaWrap(LinkedHashMap<String, List<WrapLista>> mappaWrap) {
        List<WrapLista> lista;

        if (mappaWrap != null) {
            message = String.format("Faccio vedere una mappa delle didascalie");
            System.out.println(VUOTA);
            for (String paragrafo : mappaWrap.keySet()) {
                System.out.print("==");
                System.out.print(paragrafo);
                System.out.print("==");
                System.out.print(CAPO);
                lista = mappaWrap.get(paragrafo);
                printSub(lista);
            }
        }
    }

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

    protected void printMappaDidascalie(LinkedHashMap<String, List<WrapLista>> mappaWrap) {
        List<WrapLista> lista;
        String titoloParagrafo = VUOTA;
        WrapLista wrapZero;

        if (mappaWrap != null) {
            message = String.format("Faccio vedere una mappa delle didascalie con i relativi paragrafi");
            System.out.println(message);
            System.out.println(VUOTA);
            for (String paragrafo : mappaWrap.keySet()) {
                lista = mappaWrap.get(paragrafo);
                wrapZero = lista.get(0) != null ? lista.get(0) : null;
                if (wrapZero != null) {
                    titoloParagrafo = textService.isValid(wrapZero.titoloParagrafoLink) ? wrapZero.titoloParagrafoLink : wrapZero.titoloParagrafo;
                }
                System.out.print("==");
                System.out.print(titoloParagrafo);
                System.out.print("==");
                System.out.print(CAPO);

                for (WrapLista wrap : lista) {
                    System.out.println(wrap.didascalia);
                }
                System.out.println(VUOTA);
            }
        }
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

    protected void printBioTmpl(Bio bio) {
        String tmplBio = bio.tmplBio != null ? bio.tmplBio : VUOTA;
        printBio(bio);

        if (textService.isValid(tmplBio)) {
            tmplBio = textService.sostituisce(tmplBio, CAPO, SPAZIO);
            System.out.println(String.format("tmpl: %s", tmplBio));
        }
    }

    protected void printMappaBio(Map<String, String> mappa) {
        if (mappa != null) {
            for (String key : mappa.keySet()) {
                System.out.print(key);
                System.out.print(FORWARD);
                System.out.println(mappa.get(key));
            }
        }
    }

    protected void printMappaWrapKeyOrder(LinkedHashMap<String, List<WrapLista>> mappaWrap) {
        List<WrapLista> lista;
        int pos = 0;

        if (mappaWrap != null) {
            System.out.println(VUOTA);
            message = String.format("Faccio vedere le %d chiavi ordinate di una mappa", mappaWrap.keySet().size());
            System.out.println(message);
            for (String key : mappaWrap.keySet()) {
                pos++;
                System.out.print(pos);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.println(key);
            }
        }
    }


    protected void printWrap(WrapLista wrap, TextService textService) {
        if (wrap == null) {
            return;
        }

        System.out.println(String.format("Titolo pagina: %s", textService.isValid(wrap.titoloPagina) ? wrap.titoloPagina : NULL));
        System.out.println(String.format("Titolo paragrafo: %s", textService.isValid(wrap.titoloParagrafo) ? wrap.titoloParagrafo : NULL));
        System.out.println(String.format("Paragrafo link: %s", textService.isValid(wrap.titoloParagrafoLink) ? wrap.titoloParagrafoLink : NULL));
        System.out.println(String.format("Sottoparagrafo: %s", textService.isValid(wrap.titoloSottoParagrafo) ? wrap.titoloSottoParagrafo : NULL));
        System.out.println(String.format("Ordinamento: %s", textService.isValid(wrap.ordinamento) ? wrap.ordinamento : NULL));
        System.out.println(String.format("Lista: %s", textService.isValid(wrap.lista) ? wrap.lista : NULL));
        System.out.println(String.format("giornoNato: %s", textService.isValid(wrap.giornoNato) ? wrap.giornoNato : NULL));
        System.out.println(String.format("giornoMorto: %s", textService.isValid(wrap.giornoMorto) ? wrap.giornoMorto : NULL));
        System.out.println(String.format("annoNato: %s", textService.isValid(wrap.annoNato) ? wrap.annoNato : NULL));
        System.out.println(String.format("annoMorto: %s", textService.isValid(wrap.annoMorto) ? wrap.annoMorto : NULL));
        System.out.println(String.format("Didascalia: %s", textService.isValid(wrap.didascalia) ? wrap.didascalia : NULL));
        System.out.println(VUOTA);
    }


}