package it.algos.wiki23.backend.statistiche;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.wiki23.backend.enumeration.*;
import it.algos.wiki23.backend.packages.anno.*;
import it.algos.wiki23.backend.packages.attivita.*;
import it.algos.wiki23.backend.packages.bio.*;
import it.algos.wiki23.backend.packages.giorno.*;
import it.algos.wiki23.backend.packages.nazionalita.*;
import it.algos.wiki23.backend.service.*;
import it.algos.wiki23.backend.wrapper.*;
import it.algos.wiki23.wiki.query.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

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

    public static final String VALIDO = "Nel [[template:Bio|template Bio]] della voce biografica deve esserci un valore '''valido''' per il parametro";

    public static final String VALIDO_CORRISPONDENTE = VALIDO + SPAZIO + "corrispondente";

    public static final String NOTA_VALIDO = "valido";

    public static final String VALORI_CERTI = "Vengono prese in considerazione '''solo''' le voci biografiche che hanno valori '''validi e certi''' per";

    protected WPref lastStatistica;

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
    public AttivitaBackend attivitaBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public NazionalitaBackend nazionalitaBackend;

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

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public MongoService mongoService;

    protected int totNati = 0;

    protected int totMorti = 0;

    protected AETypeToc typeToc;

    protected List lista;

    protected LinkedHashMap<String, MappaStatistiche> mappa;

    protected boolean uploadTest = false;


    protected String infoTime;

    protected void esegue() {
        this.fixPreferenze();
        this.elabora();
        this.creaLista();
        this.creaMappa();

    }

    /**
     * Preferenze usate da questa 'view' <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.typeToc = AETypeToc.forceToc;
    }

    /**
     * Elabora i dati
     */
    protected void elabora() {
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

    protected WResult upload(String wikiTitle) {
        WResult result;
        StringBuffer buffer = new StringBuffer();
        long inizio = System.currentTimeMillis();

        buffer.append(avviso());
        buffer.append(CAPO);
        buffer.append(includeIni());
        buffer.append(fixToc());
        buffer.append(tmpStatBio());
        buffer.append(includeEnd());
        buffer.append(incipit());
        buffer.append(bodyAnte());
        buffer.append(body());
        buffer.append(bodyPost());
        buffer.append(note());
        buffer.append(correlate());
        buffer.append(categorie());
        result = registra(wikiTitle, buffer.toString());

        fixInfo(inizio);
        return result;
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

        for (String key : mappa.keySet()) {
            mappaSingola = mappa.get(key);
            riga = riga(mappaSingola);
            if (textService.isValid(riga)) {
                buffer.append(riga);
            }
        }

        return buffer.toString();
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
        return appContext.getBean(QueryWrite.class).urlRequest(wikiTitle, newText);
    }


    public void fixInfo(long inizio) {
        long fine;
        long delta;
        String message;

        fine = System.currentTimeMillis();
        delta = fine - inizio;
        delta = delta / 1000 / 60;
        message = String.format("Elaborazione statistiche %s eseguita in %s minuti", infoTime, delta);

        if (lastStatistica != null) {
            lastStatistica.setValue(LocalDateTime.now());
            logger.info(new WrapLog().message(message).type(AETypeLog.upload).usaDb());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("lastStatistica è nullo")));
        }
    }

}
