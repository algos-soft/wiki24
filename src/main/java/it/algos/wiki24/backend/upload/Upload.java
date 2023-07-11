package it.algos.wiki24.backend.upload;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.packages.crono.anno.*;
import it.algos.vaad24.backend.packages.crono.giorno.*;
import it.algos.vaad24.backend.service.*;
import it.algos.vaad24.backend.wrapper.*;
import it.algos.vaad24.ui.dialog.*;
import static it.algos.wiki24.backend.boot.Wiki24Cost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.anno.*;
import it.algos.wiki24.backend.packages.attplurale.*;
import it.algos.wiki24.backend.packages.attsingolare.*;
import it.algos.wiki24.backend.packages.bio.*;
import it.algos.wiki24.backend.packages.giorno.*;
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

    @Autowired
    public LogService logService;

    protected AETypeLista typeLista;

    protected Lista lista;

    protected String nomeSottoPagina;

    protected boolean isSottopagina;

    public WikiBackend wikiBackend;

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

    protected String subAttivitaNazionalita;

    protected boolean uploadTest = false;

    protected String summary;

    protected WPref lastUpload;

    protected WPref durataUpload;

    protected WPref nextUpload;

    protected boolean usaParagrafi;

    protected boolean usaNumeriTitoloParagrafi;

    public String wikiTitleUpload;

    public String wikiTitleModulo;


    protected AETypeToc typeToc;

    protected AETypeTime unitaMisuraUpload;

    protected WResult result;

    protected AETypeLink typeLink;


    /**
     * Costruttore base senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     */
    public Upload() {
    }// end of constructor

    /**
     * Costruttore base con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * Uso: appContext.getBean(UploadAttivita.class).upload(nomeAttivitaPlurale) <br>
     * Non rimanda al costruttore della superclasse. Regola qui solo alcune property. <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public Upload(String nome) {
        this.nomeLista = nome;
    }// end of constructor

    @PostConstruct
    protected void postConstruct() {
        this.nomeLista = textService.primaMaiuscola(nomeLista);
        this.typeLista = AETypeLista.nessunaLista;
        this.typeToc = AETypeToc.forceToc;
        this.usaNumeriTitoloParagrafi = true;
        this.isSottopagina = false;

        this.fixPreferenze();
        this.fixPreferenzeBackend();
    }

    protected void fixPreferenze() {
        this.summary = "[[Utente:Biobot|bioBot]]";
    }

    protected void fixPreferenzeBackend() {
        if (wikiBackend != null) {
            this.lastUpload = wikiBackend.lastUpload;
            this.durataUpload = wikiBackend.durataUpload;
            this.nextUpload = wikiBackend.nextUpload;
        }
    }


    protected void fixMappaWrap() {
    }

    public WResult esegue() {
        StringBuffer buffer = new StringBuffer();
        this.fixMappaWrap();

        buffer.append(creaHader());
        buffer.append(creaBody());
        buffer.append(creaBottom(buffer.toString().trim()));

        return registra(buffer.toString().trim());
    }

    protected String creaHader() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(avviso());
        buffer.append(include());
        buffer.append(incipit());
        buffer.append(CAPO);

        return buffer.toString();
    }

    protected String avviso() {
        return "<!-- NON MODIFICATE DIRETTAMENTE QUESTA PAGINA - GRAZIE -->";
    }

    protected String include() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(includeIni());
        buffer.append(fixToc());
        buffer.append(fixUnconnected());
        buffer.append(torna());
        buffer.append(tmpListaBio());
        buffer.append(includeEnd());

        return buffer.toString();
    }

    protected String incipit() {
        return VUOTA;
    }

    protected String includeIni() {
        return "<noinclude>";
    }

    protected String fixToc() {
        if (isSottopagina) {
            return AETypeToc.noToc.get();
        }
        else {
            return typeToc.get();
        }
    }

    protected String fixUnconnected() {
        return UNCONNECTED;
    }


    protected String torna() {
        String localWikiTitle = wikiTitleUpload;
        String text = VUOTA;

        if (isSottopagina) {
            localWikiTitle = textService.levaCodaDaUltimo(localWikiTitle, SLASH);
            text = textService.isValid(localWikiTitle) ? String.format("{{Torna a|%s}}", localWikiTitle) : VUOTA;
        }

        return text;
    }

    protected String tmpListaBio() {
        int numVoci = mappaWrap != null ? wikiUtility.getSizeAllWrap(mappaWrap) : 0;
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMM yyy")); ;
        String progetto = "biografie";
        String txtVoci = textService.format(numVoci);

        return String.format("{{ListaBio|bio=%s|data=%s|progetto=%s}}", txtVoci, data, progetto);
    }

    protected String includeEnd() {
        return "</noinclude>";
    }


    protected String creaBody() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(testoBody());
        buffer.append(CAPO);
        return buffer.toString();
    }

    public String testoBody() {
        if (mappaWrap != null) {
            return testoBody(mappaWrap);
        }
        else {
            return VUOTA;
        }
    }

    public String testoBody(Map<String, List<WrapLista>> mappa) {
        return VUOTA;
    }

    protected String creaBottom(String textDaEsaminare) {
        StringBuffer buffer = new StringBuffer();

        buffer.append(note(textDaEsaminare));
        buffer.append(correlate());

        if (uploadTest) {
            buffer.append(portale());
        }
        else {
            buffer.append(includeIni());
            buffer.append(portale());
            buffer.append(categorie());
            buffer.append(CAPO);
            buffer.append(includeEnd());
        }

        return buffer.toString();
    }


    protected String note(String textDaEsaminare) {
        StringBuffer buffer = new StringBuffer();
        String tag = "</ref>";

        if (textDaEsaminare.contains(tag)) {
            return note();
        }
        else {
            return buffer.toString();
        }
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

    protected WResult esegueUpload(String wikiTitle, LinkedHashMap<String, List<WrapLista>> mappa) {
        return null;
    }


    protected WResult registra(String newText) {
        WResult result = WResult.crea();
        String newTextSignificativo = VUOTA;
        String tag = "progetto=biografie";

        //        if (wikiUtility.getSizeAllWrap(mappaWrap) < 1) {
        //            return WResult.errato("Non ci sono biografie per la lista " + wikiTitleUpload);
        //        }

        if (textService.isEmpty(wikiTitleUpload)) {
            return WResult.errato("Manca il wikiTitleUpload ");
        }

        if (newText.contains(tag)) {
            newTextSignificativo = newText.substring(newText.indexOf(tag));
        }

        if (uploadTest) {
            result = appContext.getBean(QueryWrite.class).urlRequest(wikiTitleUpload, newText, summary);
            //            if (result.isValido() && !result.isModificata()) {
            //                result.typeResult(AETypeResult.uploadValido);
            //            }
            return result;
        }

        if (!WPref.scriveComunque.is() && textService.isValid(newTextSignificativo)) {
            return appContext.getBean(QueryWrite.class).urlRequestCheck(wikiTitleUpload, newText, newTextSignificativo, summary);
        }
        else {
            return appContext.getBean(QueryWrite.class).urlRequest(wikiTitleUpload, newText, summary);
        }
    }

    protected WResult registra(String wikiTitle, String newText) {
        String newTextSignificativo = VUOTA;
        String tag = "progetto=biografie";

        if (uploadTest) {
            return appContext.getBean(QueryWrite.class).urlRequest(wikiTitle, newText, summary);
        }

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

    public void fixUploadMinuti(final long inizio) {
        long fine = System.currentTimeMillis();
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

    /**
     * Esegue la scrittura di tutte le pagine previste <br>
     */
    public WResult uploadAll() {
        return null;
    }

}
