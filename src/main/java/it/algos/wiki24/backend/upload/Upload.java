package it.algos.wiki24.backend.upload;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
import it.algos.wiki24.backend.liste.*;
import it.algos.wiki24.backend.packages.bio.biomongo.*;
import it.algos.wiki24.backend.packages.tabelle.attplurale.*;
import it.algos.wiki24.backend.packages.tabelle.attsingolare.*;
import it.algos.wiki24.backend.packages.tabelle.nazplurale.*;
import it.algos.wiki24.backend.packages.tabelle.nazsingolare.*;
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import jakarta.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Scope;

import javax.inject.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 10-Jan-2024
 * Time: 09:05
 */
@SpringComponent
@Primary()
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Upload implements AlgosBuilderPattern {

    @Inject
    TextService textService;

    @Inject
    ApplicationContext appContext;

    @Inject
    AnnotationService annotationService;

    @Inject
    LogService logger;

    @Inject
    WikiUtilityService wikiUtilityService;

    @Inject
    GiornoModulo giornoModulo;

    @Inject
    AnnoModulo annoModulo;

    @Inject
    protected AttSingolareModulo attSingolareModulo;

    @Inject
    protected AttPluraleModulo attPluraleModulo;

    @Inject
    protected NazSingolareModulo nazSingolareModulo;

    @Inject
    protected NazPluraleModulo nazPluraleModulo;

    @Inject
    QueryService queryService;

    @Inject
    BioMongoModulo bioMongoModulo;

    protected String titoloPagina;

    protected CrudModulo moduloCorrente;

    protected boolean costruttoreValido = false;

    protected boolean patternCompleto = false;

    protected String nomeLista;

    protected TypeLista type;

    protected String collectionName;

    protected String message;

    protected String headerText;

    protected String incipitText;

    protected String bodyText;

    protected String bottomText;

    protected String uploadText;

    protected int numBio;

    protected boolean uploadTest;

    protected TypeSummary typeSummary;

    protected boolean usaSottopaginaOltreMax;

    private ListaPagina istanzaLista;

    protected boolean isSottoPagina;

    protected boolean isSottoSottoPagina;

    protected String keySottoPagina;

    protected String keySottoSottoPagina;

    protected List<String> listaSottoPagine = new ArrayList<>();

    protected List<String> listaSottoSottoPagine = new ArrayList<>();

    /**
     * Costruttore base con 1 parametro (obbligatorio) <br>
     * Not annotated with @Autowired annotation, classe astratta <br>
     * La superclasse usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public Upload(String nomeLista) {
        this.nomeLista = nomeLista;
    }// end of constructor not @Autowired and used


    @PostConstruct
    protected void postConstruct() {
        this.fixPreferenze();
        this.patternCompleto = type != null && type != TypeLista.nessunaLista;
        this.checkValiditaCostruttore();
    }

    /**
     * Preferenze usate da questa classe <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.type = TypeLista.nessunaLista;
        this.typeSummary = TypeSummary.nessuno;

        this.uploadTest = false;
        this.headerText = VUOTA;
        this.incipitText = VUOTA;
        this.bodyText = VUOTA;
        this.bottomText = VUOTA;
        this.uploadText = VUOTA;
    }

    /**
     * Pattern Builder <br>
     */
    public Upload type(TypeLista type) {
        this.type = type;

        if (type == null) {
            return this;
        }

        this.moduloCorrente = switch (type) {
            case giornoNascita, giornoMorte -> giornoModulo;
            case annoNascita, annoMorte -> annoModulo;
            case attivitaSingolare -> attSingolareModulo;
            case attivitaPlurale -> attPluraleModulo;
            case nazionalitaSingolare -> nazSingolareModulo;
            case nazionalitaPlurale -> nazPluraleModulo;
            default -> null;
        };

        this.titoloPagina = switch (type) {
            case giornoNascita -> wikiUtilityService.wikiTitleNatiGiorno(nomeLista);
            case giornoMorte -> wikiUtilityService.wikiTitleMortiGiorno(nomeLista);
            case annoNascita -> wikiUtilityService.wikiTitleNatiAnno(nomeLista);
            case annoMorte -> wikiUtilityService.wikiTitleMortiAnno(nomeLista);
            case attivitaSingolare -> wikiUtilityService.wikiTitleAttivita(nomeLista);
            case attivitaPlurale -> wikiUtilityService.wikiTitleAttivita(nomeLista);
            case nazionalitaSingolare -> wikiUtilityService.wikiTitleNazionalita(nomeLista);
            case nazionalitaPlurale -> wikiUtilityService.wikiTitleNazionalita(nomeLista);
            default -> null;
        };

        this.usaSottopaginaOltreMax = switch (type) {
            case giornoNascita, giornoMorte -> WPref.usaSottopagineGiorni.is();
            case annoNascita, annoMorte -> WPref.usaSottopagineAnni.is();
            default -> false;
        };

        this.typeSummary = switch (type) {
            case giornoNascita, giornoMorte -> TypeSummary.giorniBio;
            case annoNascita, annoMorte -> TypeSummary.anniBio;
            case attivitaSingolare, attivitaPlurale -> TypeSummary.attivitàBio;
            case nazionalitaSingolare, nazionalitaPlurale -> TypeSummary.nazionalitàBio;
            default -> TypeSummary.nessuno;
        };

        istanzaLista = appContext.getBean(ListaPagina.class, nomeLista, type);
        //        istanzaLista = appContext.getBean(Lista.class, nomeLista).type(type);
        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Upload sottoPagina(String keySottoPagina) {
        this.isSottoPagina = true;
        this.keySottoPagina = keySottoPagina;
        if (textService.isValid(titoloPagina)) {
            titoloPagina = titoloPagina + SLASH + textService.primaMaiuscola(keySottoPagina);
        }
        else {
            logger.error(new WrapLog().message("Nel Pattern Builder di questa classe, occorre chiamare type() PRIMA di sottopagina()"));
            return null;
        }

        return this;
    }


    /**
     * Pattern Builder <br>
     */
    public Upload sottoSottoPagina(String keySottoSottoPagina) {
        this.isSottoSottoPagina = true;
        this.keySottoSottoPagina = keySottoSottoPagina;
        if (textService.isValid(titoloPagina)) {
            titoloPagina = titoloPagina + SLASH + textService.primaMaiuscola(keySottoSottoPagina);
        }
        else {
            logger.error(new WrapLog().message("Nel Pattern Builder di questa classe, occorre chiamare type() PRIMA di sottopagina()"));
            return null;
        }

        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Upload test() {
        return test(true);
    }

    /**
     * Pattern Builder <br>
     */
    public Upload test(boolean uploadTest) {
        this.uploadTest = uploadTest;
        this.typeSummary = TypeSummary.test;
        return this;
    }

    protected void checkValiditaCostruttore() {
        costruttoreValido = textService.isValid(nomeLista);
    }

    protected boolean checkValiditaPattern() {
        boolean valoreValidoNomeLista;

        if (costruttoreValido && patternCompleto) {
            return true;
        }

        if (!costruttoreValido) {
            message = String.format("Non è valido il costruttore di %s", this.getClass().getSimpleName());
            logger.error(new WrapLog().message(message));
            return false;
        }

        if (type == null || type == TypeLista.nessunaLista) {
            message = String.format("Manca il typeLista di [%s]", nomeLista);
            logger.error(new WrapLog().message(message));
            return false;
        }

        patternCompleto = moduloCorrente != null;
        valoreValidoNomeLista = switch (type) {
            case giornoNascita, giornoMorte, annoNascita, annoMorte -> patternCompleto && moduloCorrente.existByKey(nomeLista);
            case attivitaSingolare, nazionalitaSingolare -> patternCompleto;
            case attivitaPlurale, nazionalitaPlurale -> textService.isValid(titoloPagina);
            default -> false;
        };

        if (!valoreValidoNomeLista) {
            message = String.format("Non esiste un valore 'nomeLista' valido per il type [%s%s]", type.getCategoria(), nomeLista);
            logger.error(new WrapLog().message(message));
            return false;
        }
        patternCompleto = patternCompleto && valoreValidoNomeLista;

        if (!patternCompleto) {
            message = String.format("Pattern non completo di %s", this.getClass().getSimpleName());
            logger.error(new WrapLog().message(message));
            return false;
        }

        return patternCompleto;
    }

    protected boolean checkBio() {

        if (patternCompleto && numBio() < 1) {
            patternCompleto = false;
        }

        return patternCompleto;
    }

    public WResult uploadOnly() {
        int numeroMinimoDiVociPerPoterStampare;
        if (!checkValiditaPattern()) {
            return WResult.errato();
        }

        if (!checkBio()) {
            message = String.format("Non ci sono biografie per la lista %s di %s", type.getTag(), titoloPagina);
            logger.info(new WrapLog().message(message));

            return WResult.valido(message).typeResult(TypeResult.noBio);
        }

        numeroMinimoDiVociPerPoterStampare = switch (type) {
            case giornoNascita, giornoMorte, annoNascita, annoMorte -> 0;
            case attivitaSingolare -> MAX_INT_VALUE;
            case attivitaPlurale -> WPref.sogliaPaginaAttivita.getInt();
            case nazionalitaSingolare -> MAX_INT_VALUE;
            case nazionalitaPlurale -> WPref.sogliaPaginaNazionalita.getInt();
            default -> MAX_INT_VALUE;
        };

        if (numBio < numeroMinimoDiVociPerPoterStampare) {
            message = String.format("Nella lista [%s] ci sono solo [%s] biografie che sono meno delle [%s] richieste.", titoloPagina, numBio, numeroMinimoDiVociPerPoterStampare);
            logger.info(new WrapLog().message(message));
            return WResult.valido(message).typeResult(TypeResult.noBio);
        }

        if (textService.isEmpty(uploadText)) {
            uploadText = getUploadText();
        }

        if (textService.isEmpty(uploadText)) {
            return WResult.errato();
        }

        return registra();
    }

    public WResult uploadAll() {
        WResult result = uploadOnly();
        List<String> listaSottopagine;

        if (result.isValido()) {
            listaSottopagine = listaSottoPagine();
            if (listaSottopagine != null && listaSottopagine.size() > 0) {
                for (String keySottopagina : listaSottopagine) {
                    result = appContext.getBean(Upload.class, nomeLista).test(uploadTest).type(type).sottoPagina(keySottopagina).uploadOnly();
                }
            }
        }

        return result;
    }

    protected WResult registra() {
        String wikiTitle;

        if (textService.isValid(titoloPagina)) {
            wikiTitle = titoloPagina;
        }
        else {
            return WResult.errato("Manca il wikiTitle");
        }

        String newTextSignificativo = WPref.scriveComunque.is() ? VUOTA : bodyText + bottomText;

        if (textService.isEmpty(titoloPagina)) {
            logger.error(new WrapLog().message("Manca il titolo della pagina"));
        }
        if (textService.isEmpty(uploadText)) {
            logger.error(new WrapLog().message("Manca l' uploadText della pagina"));
        }

        if (uploadTest) {
            wikiTitle = "Utente:Biobot/" + titoloPagina;
        }

        return queryService.writeCheck(wikiTitle, uploadText, newTextSignificativo, typeSummary.get());
    }


    /**
     * Lista delle sottoPagine <br>
     * Controlla che il valore esista, altrimenti lo recupera da Lista <br>
     *
     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
     */
    public List<String> listaSottoPagine() {
        if (!checkValiditaPattern()) {
            return null;
        }

        if (!checkBio()) {
            return new ArrayList<>();
        }

        if (listaSottoPagine == null || listaSottoPagine.isEmpty()) {
            listaSottoPagine = istanzaLista.getKeySottoPagine();
        }

        return listaSottoPagine;
    }

    /**
     * Mappa delle sottoPagine <br>
     * Recupera il valore dalla Lista <br>
     *
     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
     */
    public LinkedHashMap<String, String> mappaSottoPagine() {
        //        return istanzaLista != null ? istanzaLista.mappaSottoPagine() : null;
        return null;
    }

    /**
     * Lista delle sottoSottoPagine <br>
     * Controlla che il valore esista, altrimenti lo recupera da Lista <br>
     *
     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
     */
    public List<String> listaSottoSottoPagine() {
        if (!checkValiditaPattern()) {
            return null;
        }

        if (!checkBio()) {
            return new ArrayList<>();
        }

        if (listaSottoSottoPagine == null || listaSottoSottoPagine.isEmpty()) {
            listaSottoSottoPagine = istanzaLista.getKeySottoPagine();
        }

        return listaSottoSottoPagine;
    }

    public WResult uploadSottopagina(String keySottopagina) {
        WResult risultato = WResult.errato();
        String nomeListaSottopagina = nomeLista + SLASH + keySottopagina;
        risultato = appContext.getBean(Upload.class, nomeLista).type(type).test(uploadTest).sottoPagina(keySottopagina).uploadOnly();

        return risultato;
    }

    public String creaHeader() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(avviso());
        buffer.append(CAPO);
        buffer.append(include());
        buffer.append(CAPO);

        this.headerText = buffer.toString();
        return headerText;
    }

    public String creaIncipit() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(incipit());

        this.incipitText = buffer.toString();
        return incipitText;
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
        StringBuffer buffer = new StringBuffer();
        boolean usaIncipit = false;
        String tagAttNazDiretta = VUOTA;
        String tagAttNazInversa = VUOTA;
        String tagDidascalie = "[[Progetto:Biografie/Didascalie|progetto biografie]]";
        String tagTmplBio = "[[template:Bio|template Bio]]";
        String tagDiscussione = VUOTA;
        String tagDiscussioneInversa = VUOTA;
        String tagModulo = VUOTA;
        String tagModuloInverso = VUOTA;
        String tagLista = VUOTA;
        int numSottopagine = 0;
        int sogliaSottoPagina = WPref.sogliaSottoPagina.getInt();

        switch (type) {
            case attivitaPlurale: {
                usaIncipit = true;
                tagAttNazDiretta = "attività";
                tagAttNazInversa = "nazionalità";
                numSottopagine = WPref.sogliaPaginaAttivita.getInt();
                tagDiscussione = "Discussioni progetto:Biografie/Attività|";
                tagDiscussioneInversa = "Discussioni progetto:Biografie/Nazionalità|";
                tagModulo = "Modulo:Bio/Plurale attività|";
                tagModuloInverso = "Modulo:Bio/Plurale nazionalità|";
                break;
            }
            case nazionalitaPlurale: {
                usaIncipit = true;
                tagAttNazDiretta = "nazionalità";
                tagAttNazInversa = "attività";
                numSottopagine = WPref.sogliaPaginaNazionalita.getInt();
                tagDiscussione = "Discussioni progetto:Biografie/Nazionalità|";
                tagDiscussioneInversa = "Discussioni progetto:Biografie/Attività|";
                tagModulo = "Modulo:Bio/Plurale nazionalità|";
                tagModuloInverso = "Modulo:Bio/Plurale attività|";
                break;
            }
            default: {}
        }
        tagLista = textService.setDoppieQuadre(tagDiscussione + "lista");

        buffer.append("Questa");
        buffer.append(REF);

        if (isSottoPagina) {
            buffer.append("Questa sottopagina");
            buffer.append(SPAZIO);
            buffer.append(QUADRA_INI);
            buffer.append(textService.setBold(textService.primaMaiuscola(nomeLista) + SLASH + textService.primaMaiuscola(keySottoPagina)));
            buffer.append(QUADRA_END);
            buffer.append(SPAZIO);
            buffer.append("è stata creata perché ci sono");
            buffer.append(SPAZIO);
            buffer.append(textService.setBold(numBio + VUOTA));
            buffer.append(SPAZIO);
            buffer.append("voci biografiche nel paragrafo");
            buffer.append(SPAZIO + textService.setBold(textService.primaMaiuscola(keySottoPagina)) + SPAZIO);
            buffer.append("della");
            buffer.append(SPAZIO);
            buffer.append(tagAttNazDiretta);
            buffer.append(SPAZIO + textService.setBold(textService.primaMaiuscola(nomeLista)) + SPAZIO);
            buffer.append(REF_END);
        }
        else {
            buffer.append("Questa pagina di una singola");
            buffer.append(SPAZIO);
            buffer.append(textService.setBold(tagAttNazDiretta));
            buffer.append(SPAZIO);
            buffer.append("è stata creata perché le relative voci biografiche superano le");
            buffer.append(SPAZIO);
            buffer.append(textService.setBold(numSottopagine + VUOTA));
            buffer.append(SPAZIO);
            buffer.append("unità.");
            buffer.append(REF_END);
        }

        buffer.append(SPAZIO);
        buffer.append("è una lista");
        buffer.append(REF);
        buffer.append("Le didascalie delle voci sono quelle previste nel");
        buffer.append(SPAZIO);
        buffer.append(tagDidascalie);
        buffer.append(REF_END);
        buffer.append(REF);
        buffer.append("Le voci, all'interno di ogni paragrafo, sono in ordine alfabetico per");
        buffer.append(SPAZIO + textService.setBold("forzaOrdinamento") + SPAZIO);
        buffer.append("oppure per");
        buffer.append(SPAZIO + textService.setBold("cognome") + SPAZIO);
        buffer.append("oppure per");
        buffer.append(SPAZIO + textService.setBold("titolo") + SPAZIO);
        buffer.append("della pagina su wikipedia.");
        buffer.append(REF_END);
        buffer.append(SPAZIO);
        buffer.append("di persone");
        buffer.append(REF);
        buffer.append("Ogni persona è presente in una sola");
        buffer.append(SPAZIO + tagLista + VIRGOLA_SPAZIO);
        buffer.append("in base a quanto riportato nel parametro");
        buffer.append(SPAZIO + textService.setBold(tagAttNazDiretta) + SPAZIO);
        buffer.append("utilizzato dal");
        buffer.append(SPAZIO);
        buffer.append(textService.setBold(tagTmplBio));
        buffer.append(REF_END);
        buffer.append(SPAZIO);
        buffer.append("presenti");
        buffer.append(REF);
        buffer.append("La lista non è esaustiva e contiene");
        buffer.append(SPAZIO + textService.setBold("solo") + SPAZIO);
        buffer.append("le persone che sono citate nell'enciclopedia e per le quali è stato implementato correttamente il");
        buffer.append(SPAZIO);
        buffer.append(textService.setBold(tagTmplBio));
        buffer.append(REF_END);
        buffer.append(SPAZIO);
        buffer.append("nell'enciclopedia che hanno come");
        buffer.append(SPAZIO);
        buffer.append(tagAttNazDiretta);
        buffer.append(REF);
        buffer.append("Le");
        buffer.append(SPAZIO + textService.setBold(tagAttNazDiretta) + SPAZIO);
        buffer.append("sono quelle");
        buffer.append(SPAZIO);
        buffer.append(SPAZIO + textService.setBold(textService.setDoppieQuadre(tagDiscussione + "convenzionalmente")) + SPAZIO);
        buffer.append("previste dalla comunità e inserite nel'");
        buffer.append(SPAZIO + textService.setBold(textService.setDoppieQuadre(tagModulo + "elenco")) + SPAZIO);
        buffer.append("utilizzato dal");
        buffer.append(SPAZIO);
        buffer.append(tagTmplBio);
        buffer.append(REF_END);
        buffer.append(SPAZIO);
        buffer.append("quella di");
        buffer.append(SPAZIO + textService.setBold(textService.primaMinuscola(nomeLista)));
        if (isSottoPagina) {
            buffer.append(SPAZIO);
            buffer.append("e sono");
            buffer.append(SPAZIO);
            buffer.append(textService.setBold(textService.primaMinuscola(keySottoPagina)));
            buffer.append(PUNTO);
        }
        else {
            buffer.append(PUNTO);
            buffer.append(SPAZIO);
            buffer.append("Le persone sono suddivise");
            buffer.append(REF);
            buffer.append("La lista è suddivisa in paragrafi per ogni");
            buffer.append(SPAZIO + textService.setBold(tagAttNazInversa) + SPAZIO);
            buffer.append("individuata.");
            buffer.append(SPAZIO);
            buffer.append("Se il numero di voci biografiche nel paragrafo supera le");
            buffer.append(SPAZIO + textService.setBold(sogliaSottoPagina + VUOTA) + SPAZIO);
            buffer.append("unità, viene creata una");
            buffer.append(SPAZIO + textService.setBold("sottopagina") + SPAZIO);
            buffer.append(PUNTO);
            buffer.append(REF_END);
            buffer.append(SPAZIO);
            buffer.append("per");
            buffer.append(SPAZIO);
            buffer.append(tagAttNazInversa);
            buffer.append(PUNTO);
        }
        buffer.append(REF);
        buffer.append("Le");
        buffer.append(SPAZIO + textService.setBold(tagAttNazInversa) + SPAZIO);
        buffer.append("sono quelle");
        buffer.append(SPAZIO + textService.setBold(textService.setDoppieQuadre(tagDiscussioneInversa + "convenzionalmente")) + SPAZIO);
        buffer.append("previste dalla comunità e inserite nel'");
        buffer.append(SPAZIO + textService.setBold(textService.setDoppieQuadre(tagModuloInverso + "elenco")) + SPAZIO);
        buffer.append("utilizzato dal");
        buffer.append(SPAZIO);
        buffer.append(tagTmplBio);
        buffer.append(REF_END);
        buffer.append(CAPO);

        if (usaIncipit) {
            this.incipitText = buffer.toString();
        }
        else {
            this.incipitText = VUOTA;
        }

        return incipitText;
    }


    protected String fixToc() {
        if (isSottoPagina) {
            return switch (type) {
                case giornoNascita, giornoMorte -> TypeToc.nessuno.get();
                case annoNascita, annoMorte -> TypeToc.nessuno.get();
                case attivitaSingolare, attivitaPlurale -> TypeToc.noToc.get();
                case nazionalitaSingolare, nazionalitaPlurale -> TypeToc.noToc.get();
                default -> TypeToc.nessuno.get();
            };
        }
        else {
            return TypeToc.nessuno.get();
        }
    }

    protected String fixUnConnected() {
        return UNCONNECTED;
    }

    protected String fixUnEditableSection() {
        return NOEDITSECTION;
    }


    protected String torna() {
        String giornoAnnoLink = isSottoPagina ? textService.levaCodaDaUltimo(titoloPagina, SLASH) : nomeLista;
        String giornoAnnoTxt = String.format("{{Torna a|%s}}", giornoAnnoLink);

        String attivitaLink = textService.levaCodaDaUltimo(titoloPagina, SLASH);
        String attivitaTxt = String.format("{{Torna a|%s}}", attivitaLink);

        String nazionalitaLink = textService.levaCodaDaUltimo(titoloPagina, SLASH);
        String nazionalitaTxt = String.format("{{Torna a|%s}}", nazionalitaLink);

        return switch (type) {
            case giornoNascita, giornoMorte, annoNascita, annoMorte -> giornoAnnoTxt;
            case attivitaSingolare, attivitaPlurale -> attivitaTxt;
            case nazionalitaSingolare, nazionalitaPlurale -> nazionalitaTxt;
            default -> VUOTA;
        };
    }

    protected String tmpListaBio() {
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMM yyy")); ;
        String progetto = "biografie";
        String txtVoci;

        txtVoci = textService.format(numBio());

        return String.format("{{ListaBio|bio=%s|data=%s|progetto=%s}}", txtVoci, data, progetto);
    }


    protected String creaBody() {
        StringBuffer buffer = new StringBuffer();
        String titoloLista;

        if (isSottoPagina) {
            bodyText = istanzaLista.getBodySottoPagina(keySottoPagina);
        }
        else {
            if (istanzaLista != null && istanzaLista.getTypeLista() == type) {
                bodyText = istanzaLista.getBodyText();
            }
            else {
                message = String.format("I type di Lista [%s] e Upload [%s], NON coincidono", istanzaLista.getTypeLista().getTag(), type.getTag());
                logger.error(new WrapLog().exception(new AlgosException(message)));
                return VUOTA;
            }
        }

        titoloLista = switch (type) {
            case giornoNascita -> wikiUtilityService.wikiTitleNatiGiorno(nomeLista);
            case giornoMorte -> wikiUtilityService.wikiTitleMortiGiorno(nomeLista);
            case annoNascita -> wikiUtilityService.wikiTitleNatiAnno(nomeLista);
            case annoMorte -> wikiUtilityService.wikiTitleMortiAnno(nomeLista);
            default -> VUOTA;
        };

        if (isSottoPagina) {
            buffer.append(bodyText);
        }
        else {
            if (textService.isValid(titoloLista)) {
                buffer.append(DOPPIE_GRAFFE_INI);
                buffer.append(type.getPersone());
                buffer.append(CAPO);
                buffer.append("|titolo=");
                buffer.append(titoloLista);
                buffer.append(CAPO);
                buffer.append("|voci=");
                buffer.append(numBio());
                buffer.append(CAPO);
                buffer.append("|testo=<nowiki></nowiki>");
                buffer.append(CAPO);
                buffer.append(bodyText);
                buffer.append(DOPPIE_GRAFFE_END);
            }
            else {
                buffer.append(bodyText);
            }
        }

        this.bodyText = buffer.toString();
        return this.bodyText;
    }


    public String creaBottom() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(CAPO);

        boolean usaInclude = switch (type) {
            case giornoNascita, giornoMorte, annoNascita, annoMorte -> true;
            case attivitaSingolare, attivitaPlurale, nazionalitaSingolare, nazionalitaPlurale -> false;
            default -> false;
        };

        buffer.append(note(incipitText));
        buffer.append(correlate());
        if (usaInclude) {
            buffer.append(includeIni());
        }
        buffer.append(interProgetto());
        buffer.append(portale());
        buffer.append(categorie());
        if (usaInclude) {
            buffer.append(includeEnd());
        }

        this.bottomText = buffer.toString();
        return bottomText = textService.isValid(bottomText) ? bottomText.trim() : bottomText;
    }

    protected String note(String textDaEsaminare) {
        String tag = "</ref>";

        if (textDaEsaminare.contains(tag)) {
            return note();
        }
        else {
            return VUOTA;
        }
    }

    protected String note() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(CAPO);
        buffer.append(wikiUtilityService.setParagrafo("Note"));
        buffer.append("<references/>");

        return buffer.toString();
    }


    protected String correlate() {
        StringBuffer buffer = new StringBuffer();
        String tagCorrelato = VUOTA;

        boolean usaCorrelate = switch (type) {
            case giornoNascita, giornoMorte, annoNascita, annoMorte -> false;
            case attivitaSingolare, attivitaPlurale, nazionalitaSingolare, nazionalitaPlurale -> true;
            default -> false;
        };

        if (!usaCorrelate) {
            return VUOTA;
        }

        tagCorrelato = switch (type) {
            case attivitaSingolare, attivitaPlurale -> ATT;
            case nazionalitaSingolare, nazionalitaPlurale -> NAZ;
            default -> VUOTA;
        };

        buffer.append(CAPO);
        buffer.append(wikiUtilityService.setParagrafo("Voci correlate"));
        buffer.append(ASTERISCO);
        buffer.append(textService.setDoppieQuadre(PATH_BIOGRAFIE + tagCorrelato));
        buffer.append(CAPO);

        return buffer.toString();
    }

    protected String interProgetto() {
        if (isSottoPagina) {
            return VUOTA;
        }
        else {
            return switch (type) {
                case giornoNascita, giornoMorte -> VUOTA;
                case annoNascita, annoMorte -> interProgettoAnni();
                default -> VUOTA;
            };
        }
    }

    protected String portale() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(CAPO);
        buffer.append("{{Portale|biografie}}");
        buffer.append(CAPO);

        return buffer.toString();
    }


    protected String categorie() {
        return switch (type) {
            case giornoNascita, giornoMorte -> categorieGiorni();
            case annoNascita, annoMorte -> categorieAnni();
            case attivitaSingolare, attivitaPlurale -> categorieAttivita();
            case nazionalitaSingolare, nazionalitaPlurale -> categorieNazionalita();
            default -> VUOTA;
        };
    }

    protected String categorieGiorni() {
        StringBuffer buffer = new StringBuffer();
        int posCat;
        String nomeGiorno;
        String nomeCat;

        nomeGiorno = nomeLista;
        nomeCat = titoloPagina;
        posCat = ((GiornoEntity) giornoModulo.findByKey(nomeGiorno)).getOrdine();

        buffer.append(CAPO);
        buffer.append("*");
        if (uploadTest) {
            buffer.append(NO_WIKI_INI);
        }
        buffer.append("[[Categoria:");
        buffer.append(type.getCategoria());
        buffer.append("|");
        buffer.append(SPAZIO);
        buffer.append(posCat);
        buffer.append("]]");
        buffer.append(CAPO);
        buffer.append("*");
        if (uploadTest) {
            buffer.append(NO_WIKI_INI);
        }
        buffer.append("[[Categoria:");
        buffer.append(nomeCat);
        buffer.append("|");
        buffer.append(SPAZIO);
        buffer.append("]]");
        if (uploadTest) {
            buffer.append(NO_WIKI_END);
        }

        return buffer.toString();
    }

    protected String categorieAnni() {
        StringBuffer buffer = new StringBuffer();
        String nomeCat;
        int ordineCategoriaSottopagina;
        String keyParagrafo = textService.levaPrimaAncheTag(nomeLista, SLASH);
        String nomeAnno = isSottoPagina ? textService.levaCodaDaUltimo(nomeLista, SLASH) : nomeLista;
        AnnoEntity anno = (AnnoEntity) annoModulo.findByKey(nomeAnno);
        String secolo = anno.getSecolo().nome; ;
        int posCat = anno.getOrdine() * MOLTIPLICATORE_ORDINE_CATEGORIA_ANNI; ;

        if (isSottoPagina) {
            nomeCat = textService.levaCodaDaUltimo(titoloPagina, SLASH);
            ordineCategoriaSottopagina = Mese.getOrder(keySottoPagina);
            if (ordineCategoriaSottopagina == 0 && keyParagrafo.equals(TAG_LISTA_NO_GIORNO)) {
                ordineCategoriaSottopagina = 13;
            }
            posCat += ordineCategoriaSottopagina;
        }
        else {
            nomeCat = titoloPagina;
        }

        buffer.append(CAPO);
        buffer.append(ASTERISCO);
        if (uploadTest) {
            buffer.append(NO_WIKI_INI);
        }
        buffer.append("[[Categoria:");
        buffer.append(type.getCategoria());
        buffer.append(secolo);
        buffer.append("|");
        buffer.append(SPAZIO);
        buffer.append(posCat);
        buffer.append("]]");
        if (uploadTest) {
            buffer.append(NO_WIKI_END);
        }
        buffer.append(CAPO);
        buffer.append(ASTERISCO);
        if (uploadTest) {
            buffer.append(NO_WIKI_INI);
        }
        buffer.append("[[Categoria:");
        buffer.append(nomeCat);
        buffer.append("|");
        buffer.append(SPAZIO);
        buffer.append("]]");
        if (uploadTest) {
            buffer.append(NO_WIKI_END);
        }

        return buffer.toString();
    }

    protected String categorieAttivita() {
        StringBuffer buffer = new StringBuffer();
        String categoria;

        categoria = String.format("Categoria:Bio attività%s%s", PIPE, textService.primaMaiuscola(nomeLista));
        categoria = isSottoPagina ? categoria + SLASH + keySottoPagina : categoria;

        if (uploadTest) {
            buffer.append(NO_WIKI_INI);
        }
        buffer.append(ASTERISCO);
        buffer.append(textService.setDoppieQuadre(categoria));
        if (uploadTest) {
            buffer.append(NO_WIKI_END);
        }

        return buffer.toString();
    }

    protected String categorieNazionalita() {
        StringBuffer buffer = new StringBuffer();
        String categoria;

        categoria = String.format("Categoria:Bio nazionalità%s%s", PIPE, textService.primaMaiuscola(nomeLista));
        categoria = isSottoPagina ? categoria + SLASH + keySottoPagina : categoria;

        if (uploadTest) {
            buffer.append(NO_WIKI_INI);
        }
        buffer.append(ASTERISCO);
        buffer.append(textService.setDoppieQuadre(categoria));
        if (uploadTest) {
            buffer.append(NO_WIKI_END);
        }

        return buffer.toString();
    }

    protected String interProgettoAnni() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(CAPO);
        buffer.append("{{subst:#if:{{subst:#invoke:string|match|{{subst:#invoke:interprogetto|interprogetto}}|template vuoto|nomatch=}}||== Altri progetti ==\n" +
                "{{interprogetto}}}}");

        return buffer.toString();
    }


    protected String includeIni() {
        return "<noinclude>";
    }

    protected String includeEnd() {
        return "</noinclude>";
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

    /**
     * Testo header <br>
     *
     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
     */
    public String getHeaderText() {
        if (!checkValiditaPattern()) {
            return STRING_ERROR;
        }

        this.numBio();

        if (!checkBio()) {
            return VUOTA;
        }

        headerText = creaHeader();
        return headerText;
    }


    /**
     * Testo incipit <br>
     *
     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
     */
    public String getIncipitText() {
        if (!checkValiditaPattern()) {
            return STRING_ERROR;
        }

        this.numBio();

        if (!checkBio()) {
            return VUOTA;
        }

        incipitText = creaIncipit();
        return incipitText;
    }

    /**
     * Testo body <br>
     *
     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
     */
    public String getBodyText() {
        if (!checkValiditaPattern()) {
            return STRING_ERROR;
        }

        this.numBio();

        if (!checkBio()) {
            return VUOTA;
        }

        bodyText = creaBody();
        return bodyText;
    }

    /**
     * Testo bottom <br>
     *
     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
     */
    public String getBottomText() {
        if (!checkValiditaPattern()) {
            return STRING_ERROR;
        }

        if (!checkBio()) {
            return VUOTA;
        }

        creaIncipit();
        bottomText = creaBottom();
        return bottomText;
    }

    /**
     * Testo uploadText (all) <br>
     *
     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
     */
    public String getUploadText() {
        StringBuffer buffer = new StringBuffer();

        if (!checkValiditaPattern()) {
            return STRING_ERROR;
        }

        if (!checkBio()) {
            return VUOTA;
        }

        buffer.append(creaHeader());
        buffer.append(creaIncipit());
        buffer.append(creaBody());
        buffer.append(creaBottom());

        uploadText = buffer.toString().trim();
        return uploadText;
    }

    /**
     * Numero delle biografie (Bio) che hanno una valore valido (letto dalla lista) <br>
     * Controlla di essere o meno in una sottopagina <br>
     * Rinvia al metodo della lista <br>
     * Numero delle biografie (Bio) che hanno una valore valido per l'intera pagina (letto dalla lista) <br>
     * Numero delle biografie (Bio) che hanno una valore valido per il paragrafo (sottopagina) specifico (letto dalla lista) <br>
     * Prima esegue una query diretta al database (più veloce)
     * Se non trova nulla controlla la mappaCompleta (creandola se manca) per vedere se esiste il paragrafo/sottopagina
     *
     * @return -1 se il pattern della classe non è valido o se nella mappa non esiste il paragrafo indicato come keySottopagina
     * zero se i dati sono validi ma non ci sono biografie <br>
     */
    public int numBio() {
        if (isSottoPagina) {
            //            numBio = istanzaLista.n(keySottoPagina);
            //            numBio= istanzaLista.getMappaSottoPagine().get(keySottoPagina).size();
            numBio = istanzaLista.getNumBioSottoPagina(keySottoPagina);
        }
        else {
            if (numBio == 0) {
                //                numBio = istanzaLista.numBioSotto();
                numBio = istanzaLista.getNumBio();
            }
        }

        return numBio;
    }

    /**
     * Numero delle biografie (Bio) che hanno una valore valido (letto dalla lista) <br>
     * Controlla di essere o meno in una sottopagina <br>
     * Rinvia al metodo della lista <br>
     * Numero delle biografie (Bio) che hanno una valore valido per l'intera pagina (letto dalla lista) <br>
     * Numero delle biografie (Bio) che hanno una valore valido per il paragrafo (sottopagina) specifico (letto dalla lista) <br>
     * Prima esegue una query diretta al database (più veloce)
     * Se non trova nulla controlla la mappaCompleta (creandola se manca) per vedere se esiste il paragrafo/sottopagina
     *
     * @return -1 se il pattern della classe non è valido o se nella mappa non esiste il paragrafo indicato come keySottopagina
     * zero se i dati sono validi ma non ci sono biografie <br>
     */
    public int numBio(String keySottopagina) {
        if (numBio == 0) {
            numBio = istanzaLista.getNumBioSottoPagina(keySottopagina);
        }

        return numBio;
    }

    public LinkedHashMap<String, String> mappaSottoSottoPagine() {
//        return istanzaLista != null ? istanzaLista.mappaSottoSottoPagine() : null;
        return  null;
    }

    public ListaPagina getIstanzaLista() {
        return istanzaLista;
    }

}
