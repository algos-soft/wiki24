package it.algos.wiki24.backend.statistiche;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.cognome.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import it.algos.wiki24.backend.packages.nome.*;
import it.algos.wiki24.backend.packages.wiki.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.wiki.query.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

import javax.annotation.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Fri, 01-Jul-2022
 * Time: 10:34
 */
public abstract class Statistiche {

    public static final String MANCA = "Nel [[template:Bio|template Bio]] della voce biografica '''manca''' completamente il parametro ";

    public static final String VUOTO = "Nel [[template:Bio|template Bio]] della voce biografica manca il '''valore''' del parametro ";

    public static final String VALIDO = "Nel [[template:Bio|template Bio]] della voce biografica deve esserci un valore '''valido''' ed univoco per il parametro ";

    public static final String DEVE = "Il [[template:Bio|template Bio]] della voce biografica deve avere un valore valido al parametro ";

    public static final String VALIDO_CORRISPONDENTE = VALIDO + "corrispondente";

    public static final String NOTA_VALIDO = "valido";

    public static final String VALORI_CERTI = "Vengono prese in considerazione '''solo''' le voci biografiche che hanno valori '''validi e certi''' per";

    protected WPref lastStatistica;

    protected WPref durataStatistica;

    protected AETypeSummary typeSummary;

    protected String tagNato;

    protected String tagMorto;

    protected String mancaNato = MANCA;

    protected String mancaMorto = MANCA;

    protected String vuotoNato = VUOTO;

    protected String vuotoMorto = VUOTO;

    protected String validoNato = VALIDO;

    protected String validoMorto = VALIDO;

    /**
     * Istanza di una interfaccia <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public WikiUtility wikiUtility;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public DateService dateService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public LogService logger;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public MathService mathService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public BioBackend bioBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AttPluraleBackend attPluraleBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public NazPluraleBackend nazPluraleBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public GiornoWikiBackend giornoWikiBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AnnoWikiBackend annoWikiBackend;

    @Autowired
    public NomeBackend nomeBackend;

    @Autowired
    public CognomeBackend cognomeBackend;

    protected WikiBackend currentWikiBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public MongoService mongoService;

    @Autowired
    public LogService logService;

    protected int totNati = 0;

    protected int totMorti = 0;

    protected AETypeToc typeToc;

    protected List lista;

    protected LinkedHashMap<String, MappaStatistiche> mappa;

    protected boolean uploadTest = false;

    protected long inizio;

    protected AETypeTime typeTime;

    protected String infoTime;

    public String wikiTitleUpload;

    public String wikiTitleTest;

    protected Map<String, Integer> mappaValidi;

    @PostConstruct
    protected void postConstruct() {
        this.fixPreferenze();
    }


    /**
     * Preferenze usate da questa classe <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.typeSummary = AETypeSummary.bioBot;
        this.typeToc = AETypeToc.forceToc;
        this.inizio = System.currentTimeMillis();

        this.fixNomeParametri();
    }

    protected void fixNomeParametri() {
        this.mancaNato = textService.isValid(tagNato) ? mancaNato + tagNato : mancaNato;
        this.mancaMorto = textService.isValid(tagMorto) ? mancaMorto + tagMorto : mancaMorto;

        this.vuotoNato = textService.isValid(tagNato) ? vuotoNato + tagNato : vuotoNato;
        this.vuotoMorto = textService.isValid(tagMorto) ? vuotoMorto + tagMorto : vuotoMorto;

        this.validoNato = textService.isValid(tagNato) ? validoNato + tagNato : validoNato;
        this.validoMorto = textService.isValid(tagMorto) ? validoMorto + tagMorto : validoMorto;
    }


    /**
     * Pattern Builder <br>
     */
    public Statistiche test() {
        this.uploadTest = true;
        this.wikiTitleUpload = UPLOAD_TITLE_DEBUG + this.getClass().getSimpleName();
        return this;
    }

    public WResult esegue() {
        WResult result = WResult.build();

        this.elabora();
        logService.debug(new WrapLog().message(String.format("Elaborazione%s%s", FORWARD, result.delta())));

        this.creaLista();
        logService.debug(new WrapLog().message(String.format("Creazione lista%s%s", FORWARD, result.delta())));

        this.creaMappa();
        logService.debug(new WrapLog().message(String.format("Creazione mappa%s%s", FORWARD, result.delta())));

        return esegueUpload();
    }

    /**
     * Elabora i dati
     * Prima esegue una (eventuale) elaborazione <br>
     */
    protected void elabora() {
        if (currentWikiBackend != null) {
            currentWikiBackend.elabora();
        }
    }

    /**
     * Recupera la lista
     */
    protected void creaLista() {
    }


    /**
     * Costruisce la mappa <br>
     */
    protected void creaMappa() {
        mappa = new LinkedHashMap<>();
    }

    protected WResult esegueUpload() {
        return textService.isValid(wikiTitleUpload) ? esegueUpload(wikiTitleUpload) : null;
    }

    protected WResult esegueUpload(String wikiTitle) {
        WResult result = WResult.build();

        StringBuffer buffer = new StringBuffer();
        logService.debug(new WrapLog().message(String.format("InizioUpload %s%s", FORWARD, result.delta())));

        buffer.append(avviso());
        buffer.append(CAPO);
        logService.debug(new WrapLog().message(String.format("avviso %s%s", FORWARD, result.delta())));
        buffer.append(includeIni());
        logService.debug(new WrapLog().message(String.format("includeIni %s%s", FORWARD, result.delta())));
        buffer.append(fixToc());
        logService.debug(new WrapLog().message(String.format("fixToc %s%s", FORWARD, result.delta())));
        buffer.append(tmpStatBio());
        logService.debug(new WrapLog().message(String.format("tmpStatBio %s%s", FORWARD, result.delta())));
        buffer.append(includeEnd());
        logService.debug(new WrapLog().message(String.format("includeEnd %s%s", FORWARD, result.delta())));
        buffer.append(incipit());
        logService.debug(new WrapLog().message(String.format("incipit %s%s", FORWARD, result.delta())));
        buffer.append(bodyAnte());
        logService.debug(new WrapLog().message(String.format("Ante%s%s", FORWARD, result.delta())));
        buffer.append(body());
        logService.debug(new WrapLog().message(String.format("Post%s%s", FORWARD, result.delta())));
        buffer.append(bodyPost());
        buffer.append(note());
        buffer.append(correlate());
        buffer.append(categorie());
        logService.debug(new WrapLog().message(String.format("Buffer%s%s", FORWARD, result.delta())));

        result = registra(wikiTitle, buffer.toString());

        return fixInfo(result);
    }

    protected String avviso() {
        return "<!-- NON MODIFICATE DIRETTAMENTE QUESTA PAGINA - GRAZIE -->";
    }

    protected String fixToc() {
        return typeToc.get();
    }

    protected String includeIni() {
        return "<noinclude>";
    }

    protected String tmpStatBio() {
        StringBuffer buffer = new StringBuffer();
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMM yyy")); ;

        buffer.append(String.format("{{StatBio|data=%s}}", data));
        return buffer.toString();
    }

    protected String includeEnd() {
        return "</noinclude>";
    }

    protected String incipit() {
        return CAPO;
    }

    /**
     * Eventuale prima tabella <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected String bodyAnte() {
        return VUOTA;
    }

    /**
     * Tabella normale <br>
     */
    protected String body() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(inizioTabella());
        buffer.append(colonne());
        buffer.append(corpo());
        buffer.append(fineTabella());

        return buffer.toString();
    }


    /**
     * Eventuale terza tabella <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected String bodyPost() {
        return VUOTA;
    }

    protected String colonne() {
        return VUOTA;
    }

    protected String corpo() {
        StringBuffer buffer = new StringBuffer();
        String riga;
        MappaStatistiche mappaSingola;
        int numRiga = 1;

        for (String key : mappa.keySet()) {
            mappaSingola = mappa.get(key);
            riga = riga(numRiga++, mappaSingola);
            if (textService.isValid(riga)) {
                buffer.append(riga);
            }
        }

        return buffer.toString();
    }

    protected String riga(int numRiga, MappaStatistiche mappa) {
        return VUOTA;
    }

    protected String riga(MappaStatistiche mappa) {
        return VUOTA;
    }


    protected String note() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(wikiUtility.setParagrafo("Note"));
        buffer.append("<references/>");
        buffer.append(CAPO);

        return buffer.toString();
    }

    protected String correlate() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(CAPO);
        buffer.append("{{CorrelateBio}}");
        buffer.append(CAPO);

        return buffer.toString();
    }

    protected String categorie() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(CAPO);
        buffer.append("[[Categoria:Progetto Biografie|{{PAGENAME}}]]");
        buffer.append(CAPO);

        return buffer.toString();
    }


    protected String inizioTabella() {
        String testo = VUOTA;

        testo += CAPO;
        testo += "{|class=\"wikitable sortable\" style=\"background-color:#EFEFEF; text-align: right;\"";
        testo += CAPO;

        return testo;
    }

    protected String fineTabella() {
        String testo = VUOTA;

        testo += "|}";
        testo += CAPO;

        return testo;
    }


    protected WResult registra(String wikiTitle, String newText) {
        return appContext.getBean(QueryWrite.class).urlRequest(wikiTitle, newText, typeSummary.get());
    }


    public WResult fixInfo(WResult result) {
        String message = VUOTA;

        if (typeTime != null) {
            message = typeTime.message(inizio, String.format("Elaborazione statistiche e upload %s eseguito", infoTime));
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("manca typeTime")));
        }

        if (lastStatistica != null) {
            lastStatistica.setValue(LocalDateTime.now());
            result.validMessage(message);
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("lastStatistica è nullo")));
        }

        if (durataStatistica != null) {
            durataStatistica.setValue(typeTime.durata(inizio));
        }

        if (uploadTest) {
            logger.info(new WrapLog().message(result.getValidMessage()).type(AETypeLog.statistiche));
        }
        else {
            logger.info(new WrapLog().message(result.getValidMessage()).type(AETypeLog.statistiche).usaDb());
        }

        return result;
    }

}
