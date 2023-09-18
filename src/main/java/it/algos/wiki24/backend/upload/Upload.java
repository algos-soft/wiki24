package it.algos.wiki24.backend.upload;

import static it.algos.vaad24.backend.boot.VaadCost.*;
import it.algos.vaad24.backend.enumeration.*;
import it.algos.vaad24.backend.exception.*;
import it.algos.vaad24.backend.logic.*;
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
public abstract class Upload implements AlgosBuilderPattern {

    protected static final String INFO_PAGINA_ATTIVITA = "Questa pagina di una singola '''attività''' è stata creata perché le relative voci biografiche superano le '''" + WPref.sogliaAttNazWiki.getInt() + "''' unità.";

    protected static final String INFO_PAGINA_NAZIONALITA = "Questa pagina di una singola '''nazionalità''' è stata creata perché le relative voci biografiche superano le '''" + WPref.sogliaAttNazWiki.getInt() + "''' unità.";

    protected static final String INFO_PAGINA_COGNOMI = "Questa pagina di un singolo '''cognome''' è stata creata perché le relative voci biografiche superano le '''" + WPref.sogliaWikiCognomi.getInt() + "''' unità.";

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

    @Autowired
    public AnnotationService annotationService;

    public AETypeLista typeLista;

    public Lista lista;

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

    public String nomeLista;

    protected String attNazUpper;

    protected String attNaz;

    protected String attNazRevert;

    protected String attNazRevertUpper;

    protected String subAttivitaNazionalita;

    public boolean uploadTest;

    public String summary;

    protected WPref lastUpload;

    protected WPref durataUpload;

    protected WPref nextUpload;

    protected boolean usaParagrafi;

    public boolean usaNumeriTitoloParagrafi;

    public String wikiTitleUpload;

    public String wikiTitleModulo;

    public AETypeLink typeLinkCrono;

    public AETypeToc typeToc;

    public boolean usaIcona;

    protected AETypeTime unitaMisuraUpload;

    protected WResult result;

    public AETypeLink typeLinkParagrafi;

    public String headerText;

    public String bodyText;

    public String uploadText;

    protected boolean costruttoreValido = false;

    protected boolean patternCompleto = false;

    protected boolean isUploading = false;

    protected String collectionName;

    protected int sogliaSottopagina;

    protected int sogliaDiv;

    protected boolean usaDiv;

    protected int sogliaIncludeAll;

    protected String keyParagrafoSottopagina;

    protected boolean usaSottoPagina;

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
    public Upload(String nomeLista) {
        this.nomeLista = nomeLista;
    }// end of constructor

    @PostConstruct
    protected void postConstruct() {
        this.nomeLista = textService.primaMaiuscola(nomeLista);
        this.typeLista = AETypeLista.nessunaLista;
        this.typeToc = AETypeToc.forceToc;
        this.typeLinkParagrafi = AETypeLink.nessunLink;
        this.typeLinkCrono = (AETypeLink) WPref.linkCrono.getEnumCurrentObj();
        this.usaNumeriTitoloParagrafi = true;
        this.usaIcona = WPref.usaSimboliCrono.is();
        this.isSottopagina = false;
        this.uploadTest = false;

        this.fixPreferenze();
        this.fixPreferenzeBackend();
        this.checkValiditaCostruttore();
    }

    protected void fixPreferenze() {
        this.summary = "[[Utente:Biobot|bioBot]]";

        this.usaSottoPagina = false;
        this.sogliaIncludeAll = WPref.sogliaIncludeAll.getInt();
        this.sogliaSottopagina = WPref.sogliaSottoPagina.getInt();
        this.sogliaDiv = WPref.sogliaDiv.getInt();
        this.usaDiv = WPref.usaDivAttNaz.is();
    }

    protected void fixPreferenzeBackend() {
        if (wikiBackend != null) {
            this.lastUpload = wikiBackend.lastUpload;
            this.durataUpload = wikiBackend.durataUpload;
            this.nextUpload = wikiBackend.nextUpload;
        }
    }

    protected void checkValiditaCostruttore() {
        if (wikiBackend != null) {
            this.costruttoreValido = wikiBackend.isExistByKey(textService.primaMaiuscola(nomeLista)) || wikiBackend.isExistByKey(textService.primaMinuscola(nomeLista));
        }
        else {
            String message = String.format("Manca il backend in fixPreferenze() di %s", this.getClass().getSimpleName());
            logService.error(new WrapLog().message(message));
            this.costruttoreValido = false;
        }

        this.collectionName = annotationService.getCollectionName(wikiBackend.entityClazz);
    }


    /**
     * Pattern Builder <br>
     */
    public Upload typeLista(AETypeLista typeLista) {
        this.typeLista = typeLista;
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Upload test() {
        this.uploadTest = true;
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Upload test(boolean uploadTest) {
        this.uploadTest = uploadTest;
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Upload typeLinkParagrafi(AETypeLink typeLinkParagrafi) {
        this.typeLinkParagrafi = typeLinkParagrafi;
        this.usaNumeriTitoloParagrafi = typeLinkParagrafi != AETypeLink.nessunLink;
        return this;
    }


    /**
     * Pattern Builder <br>
     */
    public Upload typeLinkCrono(AETypeLink typeLinkCrono) {
        this.typeLinkCrono = typeLinkCrono;
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Upload noToc() {
        this.typeToc = AETypeToc.noToc;
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Upload forceToc() {
        this.typeToc = AETypeToc.forceToc;
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Upload siNumVoci() {
        this.usaNumeriTitoloParagrafi = true;
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Upload noNumVoci() {
        this.usaNumeriTitoloParagrafi = false;
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Upload sottoPagina(LinkedHashMap<String, List<WrapLista>> mappa) {
        this.wikiTitleUpload = nomeLista;
        this.mappaWrap = mappa;
        this.costruttoreValido = true;
        this.isSottopagina = true;
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Upload sottoPagina(List<WrapLista> lista) {
        mappaWrap = wikiUtility.creaMappaSottopagina(lista, true);
        this.costruttoreValido = true;
        this.isSottopagina = true;
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Upload sottoPagina(String keyParagrafo) {
        this.wikiTitleUpload = nomeLista;
        this.keyParagrafoSottopagina = keyParagrafo;
        mappaWrap = appContext.getBean(ListaNomi.class, nomeLista).mappaWrap();
        List<WrapLista> lista = mappaWrap.get(keyParagrafo);
        mappaWrap = wikiUtility.creaMappaSottopagina(lista, true);
        this.costruttoreValido = true;
        this.isSottopagina = true;
        return this;
    }


    @Override
    public boolean isCostruttoreValido() {
        return this.costruttoreValido;
    }

    @Override
    public boolean isPatternCompleto() {
        return this.patternCompleto;
    }

    @Override
    public String getNome() {
        return this.nomeLista;
    }

    public LinkedHashMap<String, List<WrapLista>> mappaWrap() {
        String message;

        if (typeLista == null || typeLista == AETypeLista.nessunaLista) {
            System.out.println(VUOTA);
            message = String.format("Tentativo di usare il metodo '%s' per l'istanza [%s.%s]", "mappaWrap", collectionName, nomeLista);
            logger.info(new WrapLog().message(message));
            message = String.format("Manca il '%s' per l'istanza [%s.%s] e il metodo '%s' NON può funzionare.", "typeLista", collectionName, nomeLista, "mappaWrap");
            logger.warn(new WrapLog().message(message));
            return null;
        }

        if (mappaWrap == null || mappaWrap.size() > 0) {
            this.fixMappaWrap();
        }
        return mappaWrap;
    }

    public boolean mappaWrapTest() {
        LinkedHashMap<String, List<WrapLista>> mappaWrap = mappaWrap();

        if (mappaWrap != null && mappaWrap.size() > 0) {
            this.patternCompleto = true;
        }
        else {
            this.patternCompleto = false;
        }

        return patternCompleto;
    }

    /**
     * Pattern Builder <br>
     */
    public Upload esegue() {
        StringBuffer buffer = new StringBuffer();
        String message;
        if (textService.isValid(uploadText)) {
            return this;
        }

        if (typeLista == null || typeLista == AETypeLista.nessunaLista) {
            System.out.println(VUOTA);
            message = String.format("Tentativo di usare il metodo '%s' per l'istanza [%s.%s]", "esegue", collectionName, nomeLista);
            logger.info(new WrapLog().message(message));
            message = String.format("Manca il '%s' per l'istanza [%s.%s] e il metodo '%s' NON può funzionare.", "typeLista", collectionName, nomeLista, "esegue");
            logger.warn(new WrapLog().message(message));
            return null;
        }

        this.fixMappaWrap();

        if (mappaWrap != null && mappaWrap.size() > 0) {
            buffer.append(creaHader());
            buffer.append(creaBodyLayer());
            buffer.append(creaBottom(buffer.toString().trim()));

            this.uploadText = buffer.toString().trim();
        }

        return this;
    }

    public WResult upload() {
        String message;
        isUploading = true;

        if (typeLista == null || typeLista == AETypeLista.nessunaLista) {
            System.out.println(VUOTA);
            message = String.format("Tentativo di usare il metodo '%s' per l'istanza [%s.%s]", "upload", collectionName, nomeLista);
            logger.info(new WrapLog().message(message));
            message = String.format("Manca il '%s' per l'istanza [%s.%s] e il metodo '%s' NON può funzionare.", "typeLista", collectionName, nomeLista, "upload");
            logger.error(new WrapLog().message(message));
            return null;
        }
        if (!costruttoreValido) {
            System.out.println(VUOTA);
            message = String.format("Upload non effettuato perché il valore [%s.%s] della collection '%s' non esiste", collectionName, nomeLista, collectionName);
            logger.error(new WrapLog().message(message));
            return null;
        }
        if (!patternCompleto) {
            System.out.println(VUOTA);
            message = String.format("AlgosBuilderPattern per l'istanza [%s.%s] non completo. Non funziona il metodo '%s'", collectionName, nomeLista, "upload");
            logger.error(new WrapLog().message(message));
            return null;
        }

        if (textService.isEmpty(uploadText)) {
            this.esegue();
        }
        if (textService.isEmpty(uploadText)) {
            return WResult.errato();
        }

        return registra(uploadText);
    }


    public boolean fixMappaWrap() {
        return false;
    }

    /**
     * Solo per test <br>
     */
    public String testoHeader() {
        this.uploadTest = true;
        if (textService.isEmpty(uploadText)) {
            this.esegue();
        }
        return headerText;
    }

    /**
     * Solo per test <br>
     */
    public String testoBody() {
        this.uploadTest = true;
        if (textService.isEmpty(uploadText)) {
            this.esegue();
        }
        return bodyText;
    }

    public String creaHader() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(avviso());
        buffer.append(include());
        buffer.append(incipit());
        buffer.append(CAPO);

        this.headerText = buffer.toString();
        return headerText;
    }

    protected String avviso() {
        return "<!-- NON MODIFICATE DIRETTAMENTE QUESTA PAGINA - GRAZIE -->";
    }

    protected String include() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(includeIni());
        buffer.append(fixToc());
        buffer.append(fixUnConnected());
        buffer.append(fixUnEditableSection());
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

    protected String fixUnConnected() {
        return UNCONNECTED;
    }

    protected String fixUnEditableSection() {
        return NOEDITSECTION;
    }


    protected String torna() {
        String localWikiTitle = wikiTitleUpload;
        String text = VUOTA;

        if (isSottopagina) {
            localWikiTitle = textService.levaCodaDaUltimo(localWikiTitle, SLASH);
            text = textService.isValid(localWikiTitle) ? String.format("{{Torna a|%s}}", localWikiTitle) : VUOTA;
        }
        else {
            text = switch (typeLista) {
                case giornoNascita, giornoMorte, annoNascita, annoMorte -> String.format("{{Torna a|%s}}", nomeLista);
                default -> text;
            };
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


    protected String creaBodyLayer() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(creaBody());
        buffer.append(CAPO);

        this.bodyText = buffer.toString();
        return bodyText;
    }


    public String creaBody() {
        StringBuffer buffer = new StringBuffer();
        List<WrapLista> lista = null;
        int numVociTotaliPagina = wikiUtility.getSizeAllWrap(mappaWrap);
        int numVociParagrafo;
        int numVociPerTitolo;
        String titoloParagrafoLink;
        String titoloParagrafoDefinitivo;
        String vedi;
        String sottoPagina;
        String sottoNomeLista;
        boolean usaDivLocal;

        if (isSottopagina && mappaWrap.size() == 1 || numVociTotaliPagina < WPref.sogliaVociPerParagrafi.getInt()) {
            for (String key : mappaWrap.keySet()) {
                lista = mappaWrap.get(key);
            }
            if (lista != null) {
                buffer.append("{{Div col}}" + CAPO);
                for (WrapLista wrap : lista) {
                    buffer.append(ASTERISCO);
                    buffer.append(wrap.didascalia);
                    buffer.append(CAPO);
                }
                buffer.append("{{Div col end}}" + CAPO);
            }
            return buffer.toString().trim();
        }

        for (String keyParagrafo : mappaWrap.keySet()) {
            lista = mappaWrap.get(keyParagrafo);
            numVociParagrafo = lista.size();
            numVociPerTitolo = usaNumeriTitoloParagrafi ? numVociParagrafo : 0;
            titoloParagrafoLink = lista.get(0).titoloParagrafoLink;
            if (isSottopagina) {
                titoloParagrafoDefinitivo = wikiUtility.setParagrafo(keyParagrafo);
            }
            else {
                if (numVociTotaliPagina < sogliaIncludeAll) {
                    titoloParagrafoDefinitivo = switch (typeLista) {
                        case giornoNascita -> wikiUtility.fixTitoloLinkIncludeOnly(keyParagrafo, titoloParagrafoLink, numVociPerTitolo);
                        case giornoMorte -> wikiUtility.fixTitoloLinkIncludeOnly(keyParagrafo, titoloParagrafoLink, numVociPerTitolo);
                        case annoNascita -> wikiUtility.fixTitoloLinkIncludeOnly(keyParagrafo, titoloParagrafoLink, numVociPerTitolo);
                        case annoMorte -> wikiUtility.fixTitoloLinkIncludeOnly(keyParagrafo, titoloParagrafoLink, numVociPerTitolo);
                        default -> wikiUtility.fixTitoloLink(keyParagrafo, titoloParagrafoLink, numVociPerTitolo);
                    };
                }
                else {
                    titoloParagrafoDefinitivo = wikiUtility.fixTitoloLink(keyParagrafo, titoloParagrafoLink, numVociPerTitolo);
                }
            }
            buffer.append(titoloParagrafoDefinitivo);

            if (usaSottopagina(numVociTotaliPagina, numVociParagrafo)) {
                sottoPagina = wikiTitleUpload + SLASH + textService.primaMaiuscola(keyParagrafo);
                sottoNomeLista = nomeLista + SLASH + textService.primaMaiuscola(keyParagrafo);
                vedi = String.format("{{Vedi anche|%s}}", sottoPagina);
                buffer.append(vedi + CAPO);
                if (isUploading) {
                    this.vediSottoPagina(sottoNomeLista, lista);
                }
            }
            else {
                usaDivLocal = usaDiv ? lista.size() > sogliaDiv : false;
                buffer.append(usaDivLocal ? "{{Div col}}" + CAPO : VUOTA);
                for (WrapLista wrap : lista) {
                    buffer.append(ASTERISCO);
                    buffer.append(wrap.didascalia);
                    buffer.append(CAPO);
                }
                buffer.append(usaDivLocal ? "{{Div col end}}" + CAPO : VUOTA);
            }
        }

        return buffer.toString().trim();
    }


    protected boolean usaSottopagina(int numVociTotaliPagina, int numVociParagrafo) {
        boolean usaSottopagina = false;

        if (isSottopagina) {
            return false;
        }
        if (numVociParagrafo < sogliaSottopagina) {
            return false;
        }
        if (!usaSottoPagina) {
            return false;
        }
        if (typeLista == AETypeLista.annoNascita || typeLista == AETypeLista.annoMorte || typeLista == AETypeLista.giornoNascita || typeLista == AETypeLista.giornoMorte) {
            if (numVociTotaliPagina > WPref.sogliaPaginaGiorniAnni.getInt()) {
                return true;
            }
            else {
                return false;
            }
        }

        return usaSottopagina;
    }

    protected WResult vediSottoPagina(String sottoPagina, List<WrapLista> lista) {
        return WResult.errato();
    }

    protected String creaBottom(String textDaEsaminare) {
        StringBuffer buffer = new StringBuffer();

        buffer.append(note(textDaEsaminare));
        buffer.append(correlate());

        buffer.append(includeIni());
        buffer.append(portale());
        buffer.append(categorie());
        buffer.append(includeEnd());

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
        WResult result;
        String newTextSignificativo = VUOTA;
        String tag = "progetto=biografie";
        String sottoPagina;
        String fixTitleTest;

        if (textService.isEmpty(wikiTitleUpload)) {
            return WResult.errato("Manca il wikiTitleUpload ");
        }

        if (newText.contains(tag)) {
            newTextSignificativo = newText.substring(newText.indexOf(tag));
        }

        if (uploadTest) {
            if (isSottopagina) {
                sottoPagina = nomeLista;
                //                if (sottoPagina.contains(SPAZIO)) {
                //                    sottoPagina = sottoPagina.substring(sottoPagina.lastIndexOf(SPAZIO), sottoPagina.length());
                //                }
                if (!sottoPagina.contains(UPLOAD_TITLE_DEBUG)) {
                    this.wikiTitleUpload = UPLOAD_TITLE_DEBUG + sottoPagina;
                }
                if (textService.isValid(keyParagrafoSottopagina)) {
                    this.wikiTitleUpload = UPLOAD_TITLE_DEBUG + nomeLista + SLASH + keyParagrafoSottopagina;
                }
            }
            else {
                fixTitleTest = typeLista.getTag();
                fixTitleTest = textService.isValid(fixTitleTest) ? textService.primaMaiuscola(fixTitleTest) + SLASH : VUOTA;
                this.wikiTitleUpload = UPLOAD_TITLE_DEBUG + fixTitleTest + textService.primaMaiuscola(nomeLista);
            }

            result = appContext.getBean(QueryWrite.class).urlRequest(wikiTitleUpload, newText, summary);
        }
        else {
            if (!WPref.scriveComunque.is() && textService.isValid(newTextSignificativo)) {
                result = appContext.getBean(QueryWrite.class).urlRequestCheck(wikiTitleUpload, newText, newTextSignificativo, summary);
            }
            else {
                result = appContext.getBean(QueryWrite.class).urlRequest(wikiTitleUpload, newText, summary);
            }
        }

        result.setTagCode(nomeLista);
        if (result.isValido()) {
            switch (result.getTypeResult()) {
                case queryWriteCreata -> result.typeResult(AETypeResult.uploadNuova);
                case queryWriteModificata -> result.typeResult(AETypeResult.uploadModificata);
                case queryWriteEsistente -> result.typeResult(AETypeResult.uploadUguale);
                case uploadSostanzialmenteUguale -> result.typeResult(AETypeResult.uploadSostanzialmenteUguale);
                default -> {}
            } ;
        }
        else {
            result.typeResult(AETypeResult.uploadErrato);
        }

        return result;
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

    public LinkedHashMap<String, List<WrapLista>> getMappaWrap() {
        return mappaWrap;
    }

}
