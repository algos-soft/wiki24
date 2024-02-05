package it.algos.wiki24.backend.upload;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.enumeration.*;
import it.algos.base24.backend.exception.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.base24.backend.packages.crono.mese.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import it.algos.base24.ui.view.*;
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

    protected String bodyText;

    protected String bottomText;

    protected String uploadText;

    protected int numBio;

    protected boolean uploadTest;

    protected TypeSummary typeSummary;

    protected boolean usaSottopaginaOltreMax;

    private Lista istanzaLista;

    protected boolean isSottopagina;

    protected String keySottopagina;

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
            case attivitaSingolare -> VUOTA;
            case attivitaPlurale -> wikiUtilityService.wikiTitleAttivita(nomeLista);
            case nazionalitaSingolare -> VUOTA;
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
            default -> TypeSummary.nessuno;
        };

        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Upload sottopagina(String keySottopagina) {
        this.isSottopagina = true;
        this.keySottopagina = keySottopagina;
        if (textService.isValid(titoloPagina)) {
            titoloPagina = titoloPagina + SLASH + textService.primaMaiuscola(keySottopagina);
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
            //            message = String.format("Non ci sono biografie per la lista %s di %s", type.getTag(), titoloPagina);
            //            logger.info(new WrapLog().message(message));
            patternCompleto = false;
        }

        return patternCompleto;
    }

    public WResult uploadOnly() {
        if (!checkValiditaPattern()) {
            return WResult.errato();
        }

        if (!checkBio()) {
            message = String.format("Non ci sono biografie per la lista %s di %s", type.getTag(), titoloPagina);
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
            listaSottopagine = listaSottopagine();
            if (listaSottopagine != null && listaSottopagine.size() > 0) {
                for (String keySottopagina : listaSottopagine) {
                    result = appContext.getBean(Upload.class, nomeLista).type(type).sottopagina(keySottopagina).uploadOnly();
                }
            }
        }

        return result;
    }

    protected WResult registra() {
        String wikiTitle = titoloPagina;
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

    //    /**
    //     * Pattern Builder <br>
    //     */
    //    public String creaUploadText() {
    //        StringBuffer buffer = new StringBuffer();
    //        String message;
    //        this.numBio();
    //        if (!checkValiditaPattern()) {
    //            return this;
    //        }
    //        if (textService.isValid(uploadText)) {
    //            return this;
    //        }

    //        if (type == null || type == TypeLista.nessunaLista) {
    //            System.out.println(VUOTA);
    //            message = String.format("Tentativo di usare il metodo '%s' per l'istanza [%s.%s]", "esegue", collectionName, nomeLista);
    //            logger.info(new WrapLog().message(message));
    //            message = String.format("Manca il '%s' per l'istanza [%s.%s] e il metodo '%s' NON può funzionare.", "typeLista", collectionName, nomeLista, "esegue");
    //            logger.warn(new WrapLog().message(message));
    //            return null;
    //        }
    //
    //        buffer.append(creaHeader());
    //        buffer.append(creaBody());
    //        buffer.append(creaBottom());
    //
    //        uploadText = buffer.toString().trim();
    //        return uploadText;
    //    }

    public List<String> listaSottopagine() {
        if (!checkValiditaPattern()) {
            return null;
        }

        if (!checkBio()) {
            return null;
        }

        return appContext.getBean(Lista.class, nomeLista).type(type).listaSottopagine();

        //        if (textService.isEmpty(bodyText)) {
        ////            this.esegue();
        //        }
        //        return istanzaLista != null ? istanzaLista.listaSottopagine() : null;
    }

    public WResult uploadSottopagina(String keySottopagina) {
        WResult risultato = WResult.errato();
        String nomeListaSottopagina = nomeLista + SLASH + keySottopagina;
        risultato = appContext.getBean(Upload.class, nomeLista).type(type).test(uploadTest).sottopagina(keySottopagina).uploadOnly();

        return risultato;
    }

    public String creaHeader() {
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


    protected String fixToc() {
        return TypeToc.nessuno.get();
    }

    protected String fixUnConnected() {
        return UNCONNECTED;
    }

    protected String fixUnEditableSection() {
        return NOEDITSECTION;
    }


    protected String torna() {
        String linkRitorno = isSottopagina ? textService.levaCodaDaUltimo(titoloPagina, SLASH) : nomeLista;

        return switch (type) {
            case giornoNascita, giornoMorte, annoNascita, annoMorte -> String.format("{{Torna a|%s}}", linkRitorno);
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
        //        String keyParagrafo;
        //        String listaOriginaria;

        if (isSottopagina) {
            bodyText = appContext.getBean(Lista.class, nomeLista).type(type).getTestoSottopagina(keySottopagina);

            //            listaOriginaria = textService.levaCodaDaUltimo(nomeLista, SLASH);
            //            keyParagrafo = textService.levaPrimaAncheTag(nomeLista, SLASH);
            //            if (istanzaLista == null) {
            //                istanzaLista = appContext.getBean(Lista.class, listaOriginaria).type(type);
            //            }
            //            if (istanzaLista != null && istanzaLista.getType() == type) {
            //                bodyText = istanzaLista.getTestoSottopagina(keyParagrafo);
            //            }
        }
        else {
            istanzaLista = appContext.getBean(Lista.class, nomeLista).type(type);
            if (istanzaLista != null && istanzaLista.getType() == type) {
                bodyText = istanzaLista.bodyText();
            }
            else {
                message = String.format("I type di Lista [%s] e Upload [%s], NON coincidono", istanzaLista.getType().getTag(), type.getTag());
                logger.error(new WrapLog().exception(new AlgosException(message)));
                return VUOTA;
            }
        }

        if (!isSottopagina) {
            buffer.append(DOPPIE_GRAFFE_INI);
        }
        buffer.append(getListaIni());

        buffer.append(bodyText);

        if (!isSottopagina) {
            buffer.append(DOPPIE_GRAFFE_END);
        }

        this.bodyText = buffer.toString();
        return this.bodyText;
    }

    protected String getListaIni() {
        StringBuffer buffer = new StringBuffer();

        if (isSottopagina) {
            return VUOTA;
        }

        buffer.append(type.getPersone());
        buffer.append(CAPO);
        buffer.append("|titolo=");
        buffer.append(switch (type) {
            case giornoNascita -> wikiUtilityService.wikiTitleNatiGiorno(nomeLista);
            case giornoMorte -> wikiUtilityService.wikiTitleMortiGiorno(nomeLista);
            case annoNascita -> wikiUtilityService.wikiTitleNatiAnno(nomeLista);
            case annoMorte -> wikiUtilityService.wikiTitleMortiAnno(nomeLista);
            default -> VUOTA;
        });
        buffer.append(CAPO);
        buffer.append("|voci=");
        buffer.append(numBio());
        buffer.append(CAPO);
        buffer.append("|testo=<nowiki></nowiki>");
        buffer.append(CAPO);

        return buffer.toString();
    }


    public String creaBottom() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(note("textDaEsaminare"));
        buffer.append(correlate());

        buffer.append(includeIni());
        buffer.append(interProgetto());
        buffer.append(portale());
        buffer.append(categorie());
        buffer.append(includeEnd());

        this.bottomText = buffer.toString();
        return bottomText;
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
        buffer.append(wikiUtilityService.setParagrafo("Note"));
        buffer.append("<references/>");

        return buffer.toString();
    }


    protected String correlate() {
        return VUOTA;
    }

    protected String interProgetto() {
        if (isSottopagina) {
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

        return buffer.toString();
    }


    protected String categorie() {
        return switch (type) {
            case giornoNascita, giornoMorte -> categorieGiorni();
            case annoNascita, annoMorte -> categorieAnni();
            default -> VUOTA;
        };
    }

    protected String categorieGiorni() {
        StringBuffer buffer = new StringBuffer();
        int posCat;
        String nomeGiorno;
        String nomeCat;

        //        if (isSottopagina) {
        //            nomeGiorno = textService.levaCodaDaPrimo(nomeLista, SLASH);
        //            nomeCat = textService.levaCodaDaUltimo(wikiTitleUpload, SLASH);
        //        }
        //        else {
        //            nomeGiorno = nomeLista;
        //            nomeCat = titoloPagina;
        //        }

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
        if (uploadTest) {
            buffer.append(NO_WIKI_END);
        }

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
        String nomeAnno = isSottopagina ? textService.levaCodaDaUltimo(nomeLista, SLASH) : nomeLista;
        AnnoEntity anno = (AnnoEntity) annoModulo.findByKey(nomeAnno);
        String secolo = anno.getSecolo().nome; ;
        int posCat = anno.getOrdine() * MOLTIPLICATORE_ORDINE_CATEGORIA_ANNI; ;

        if (isSottopagina) {
            nomeCat = textService.levaCodaDaUltimo(titoloPagina, SLASH);
            ordineCategoriaSottopagina = Mese.getOrder(keySottopagina);
            if (ordineCategoriaSottopagina == 0 && keyParagrafo.equals(TAG_LISTA_NO_GIORNO)) {
                ordineCategoriaSottopagina = 13;
            }
            posCat += ordineCategoriaSottopagina;
        }
        else {
            nomeCat = titoloPagina;
        }

        buffer.append(CAPO);
        buffer.append("*");
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
        if (numBio == 0) {
            if (isSottopagina) {
                numBio = appContext.getBean(Lista.class, nomeLista).type(type).numBio(keySottopagina);
            }
            else {
                numBio = appContext.getBean(Lista.class, nomeLista).type(type).numBio();
            }
        }

        return numBio;
    }

    //    /**
    //     * Numero delle biografie (Bio) che hanno una valore valido per il paragrafo (sottopagina) specifico <br>
    //     * Controlla di essere in una sottopagina <br>
    //     * Rinvia al metodo della lista <br>
    //     * Prima esegue una query diretta al database (più veloce)
    //     * Se non trova nulla controlla la mappaCompleta (creandola se manca) per vedere se esiste il paragrafo/sottopagina
    //     *
    //     * @return -1 se il pattern della classe non è valido o se nella mappa non esiste il paragrafo indicato come keySottopagina, zero se i dati sono validi ma non ci sono biografie <br>
    //     */
    //    public int numBio(String keyParagrafo) {
    //        String listaOriginaria;
    //
    //        if (!isSottopagina) {
    //            return INT_ERROR;
    //        }
    //
    //        if (numBio == 0) {
    //            listaOriginaria = textService.levaCodaDaUltimo(nomeLista, SLASH);
    //            numBio = appContext.getBean(Lista.class, listaOriginaria).type(type).numBio(keyParagrafo);
    //        }
    //        return numBio;
    //    }

    //    /**
    //     * Testo header <br>
    //     *
    //     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
    //     */
    //    public String getHeaderText(String keyParagrafo) {
    //        if (!checkValiditaPattern()) {
    //            return STRING_ERROR;
    //        }
    //
    //        this.numBio(keyParagrafo);
    //
    //        if (!checkBio()) {
    //            return VUOTA;
    //        }
    //
    //        if (textService.isEmpty(headerText)) {
    //            this.esegue();
    //        }
    //
    //        return headerText;
    //    }

    //    /**
    //     * Testo body <br>
    //     *
    //     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
    //     */
    //    public String getBodyText(String keyParagrafo) {
    //        if (!checkValiditaPattern()) {
    //            return STRING_ERROR;
    //        }
    //
    //        this.numBio(keyParagrafo);
    //
    //        if (!checkBio()) {
    //            return VUOTA;
    //        }
    //
    //        if (textService.isEmpty(bodyText)) {
    //            this.esegue();
    //        }
    //
    //        return bodyText;
    //    }

}
