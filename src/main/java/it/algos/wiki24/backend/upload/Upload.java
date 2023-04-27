package it.algos.wiki24.backend.upload;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.giorno.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import it.algos.wiki24.wiki.query.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

/**
 * Project wiki23
 * Created by Algos
 * User: gac
 * Date: Wed, 08-Jun-2022
 * Time: 06:54
 * <p>
 * Classe specializzata per caricare (upload) le liste biografiche sul server wiki. <br>
 * <p>
 * Liste cronologiche (in namespace principale) di nati e morti nel giorno o nell'anno <br>
 * Liste di nomi e cognomi (in namespace principale). <br>
 * Liste di attività e nazionalità (in Progetto:Biografie). <br>
 * <p>
 * Necessita del login come bot <br>
 * Sovrascritta nelle sottoclassi concrete <br>
 */
public abstract class Upload {

    protected static final String INFO_PAGINA_ATTIVITA = "Questa pagina di una singola '''attività''' è stata creata perché le relative voci biografiche superano le '''" + WPref.sogliaAttNazWiki.getInt() + "''' unità.";

    protected static final String INFO_PAGINA_NAZIONALITA = "Questa pagina di una singola '''nazionalità''' è stata creata perché le relative voci biografiche superano le '''" + WPref.sogliaAttNazWiki.getInt() + "''' unità.";

    protected static final String INFO_PAGINA_COGNOMI = "Questa pagina di un singolo '''cognome''' è stata creata perché le relative voci biografiche superano le '''" + WPref.sogliaCognomiWiki.getInt() + "''' unità.";

    protected static final String INFO_PARAGRAFI_ATTIVITA = "La lista è suddivisa in paragrafi per ogni '''attività''' individuata. Se il numero di voci biografiche nel" +
            " paragrafo supera le '''" + WPref.sogliaSottoPagina.getInt() + "''' unità, viene creata una '''sottopagina'''.";

    protected static final String INFO_PARAGRAFI_NAZIONALITA = "La lista è suddivisa in paragrafi per ogni '''nazionalità''' individuata. Se il numero di voci biografiche nel" +
            " paragrafo supera le '''" + WPref.sogliaSottoPagina.getInt() + "''' unità, viene creata una '''sottopagina'''.";

    protected static final String TITOLO_LINK_PARAGRAFO_ATTIVITA = UPLOAD_TITLE_PROJECT + ATT;

    protected static final String TITOLO_LINK_PARAGRAFO_NAZIONALITA = UPLOAD_TITLE_PROJECT + NAZ;

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
    public TextService textService;

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
    public BioBackend bioBackend;

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
    public AttPluraleBackend attPluraleBackend;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public GiornoBackend giornoBackend;

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
    public AnnoBackend annoBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public AnnoWikiBackend annoWikiBackend;

    @Autowired
    public WikiApiService wikiApiService;

    @Autowired
    public ArrayService arrayService;

    @Autowired
    public AttSingolareBackend attSingolareBackend;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public QueryService queryService;

    protected AETypeLista typeCrono;

    protected Lista lista;

    protected String nomeSottoPagina;

    /**
     * Mappa delle didascalie che hanno una valore valido per la pagina specifica <br>
     * La mappa è composta da una chiave (ordinata) che corrisponde al titolo del paragrafo <br>
     * Ogni valore della mappa è costituito da una lista di didascalie per ogni paragrafo <br>
     * La visualizzazione dei paragrafi può anche essere esclusa, ma questi sono comunque presenti <br>
     * La mappa viene creata nel @PostConstruct dell'istanza <br>
     */
    protected LinkedHashMap<String, List<WrapLista>> mappaWrap;

    protected String titoloLinkParagrafo;

    protected String titoloLinkVediAnche;

    protected String nomeLista;

    protected String attNazUpper;

    protected String attNaz;

    protected String attNazRevert;

    protected String attNazRevertUpper;

    //    protected String nomeAttivitaNazionalitaPlurale;

    protected String subAttivitaNazionalita;

    protected boolean uploadTest = false;

    protected String summary;

    protected WPref lastUpload;

    protected WPref durataUpload;

    protected WPref nextUpload;

    protected boolean usaParagrafi;

    protected String wikiTitleUpload;

    protected String wikiTitleModulo;


    protected AETypeToc typeToc;

    protected AETypeTime unitaMisuraUpload;

    protected WResult result;



    protected WResult esegueUpload(String wikiTitle, LinkedHashMap<String, List<WrapLista>> mappa) {
        return null;
    }


    protected String avviso() {
        return "<!-- NON MODIFICATE DIRETTAMENTE QUESTA PAGINA - GRAZIE -->";
    }


    protected String includeIni() {
        return "<noinclude>";
    }

    protected String fixToc() {
        return typeToc.get();
    }

    protected String fixUnconnected() {
        return UNCONNECTED;
    }

    protected String torna(String wikiTitle) {
        wikiTitle = textService.levaCodaDaUltimo(wikiTitle, SLASH);
        return textService.isValid(wikiTitle) ? String.format("{{Torna a|%s}}", wikiTitle) : VUOTA;
    }

    protected String tmpListaBio(int numVoci) {
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMM yyy")); ;
        String progetto = "biografie";
        String txtVoci = textService.format(numVoci);

        return String.format("{{ListaBio|bio=%s|data=%s|progetto=%s}}", txtVoci, data, progetto);
    }

    protected String tmpListaStat() {
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMM yyy")); ;
        return String.format("{{StatBio|data=%s}}", data);
    }

    protected String includeEnd() {
        return "</noinclude>";
    }

    protected String incipit() {
        return VUOTA;
    }


    protected String note() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(CAPO);
        buffer.append(wikiUtility.setParagrafo("Note"));
        buffer.append("<references/>");

        return buffer.toString();
    }

    protected String correlate() {
        return VUOTA;
    }

    protected String portale() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(CAPO);
        buffer.append("{{Portale|biografie}}");

        return buffer.toString();
    }


    protected String categorie() {
        return VUOTA;
    }


    protected WResult registra(String wikiTitle, String newText) {
        String newTextSignificativo = VUOTA;
        String tag = "progetto=biografie";

        if (newText.contains(tag)) {
            newTextSignificativo = newText.substring(newText.indexOf(tag));
        }

        if (!WPref.scriveComunque.is() && textService.isValid(newTextSignificativo)) {
            return appContext.getBean(QueryWrite.class).urlRequestCheck(wikiTitle, newText, newTextSignificativo, summary);
        }
        else {
            return appContext.getBean(QueryWrite.class).urlRequest(wikiTitle, newText, summary);
        }
    }

    public void uploadSottoPagine(String wikiTitle, String parente, String sottoPagina, int ordineSottoPagina, List<WrapLista> lista) {
    }

    public void uploadSottoPagine(String wikiTitle, String parente, String sottoPagina, List<WrapLista> lista) {
    }

    @Deprecated
    public void fixUploadMinuti(final long inizio) {
        long fine = System.currentTimeMillis();
        String message;
        Long delta = fine - inizio;

        if (lastUpload != null) {
            lastUpload.setValue(LocalDateTime.now());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("lastUpload è nullo")));
            return;
        }

        if (durataUpload != null) {
            delta = delta / 1000 / 60;
            durataUpload.setValue(delta.intValue());
        }
        else {
            logger.warn(new WrapLog().exception(new AlgosException("durataUpload è nullo")));
            return;
        }
    }

    /**
     * Esegue la scrittura della pagina <br>
     */
    public WResult uploadModuloOld(String wikiTitle, String newText) {
        if (textService.isValid(newText)) {
            WResult resultQuery = appContext.getBean(QueryWrite.class).urlRequest(wikiTitle, newText, summary);
            //            String title = textService.isValid(result.getWikiTitle()) ? result.getWikiTitle() + SOMMA_SPAZIO + resultQuery.getWikiTitle() : resultQuery.getWikiTitle();
            //            result.setWikiTitle(title);
            result.setPageid(resultQuery.getPageid());
            result.setResponse(resultQuery.getResponse());
            result.valido(resultQuery.isValido());
            result.eseguito(true);
            result.setModificata(result.isModificata() ? true : resultQuery.isModificata() ? true : false);
            result.typeResult(AETypeResult.uploadValido);
            result.typeLog(AETypeLog.upload);
            result.method("Upload");
            result.setValidMessage("Upload di 1/+ moduli dopo averli ordinati alfabeticamente");
            result.setIntValue(0);
            result.setLista(null);

            return result;
        }
        else {
            return WResult.errato();
        }
    }


    /**
     * Esegue la scrittura della pagina <br>
     */
    public WResult uploadModuloNew(String wikiTitle, String newModuloText) {
        String oldTextAll = wikiApiService.legge(wikiTitle);
        String oldModuloText = wikiApiService.leggeTestoModulo(wikiTitle);
        String newTextAll = textService.sostituisce(oldTextAll, oldModuloText, newModuloText);

        return uploadModuloOld(wikiTitle, newTextAll);
    }


}
