package it.algos.wiki24.backend.upload;

import static it.algos.base24.backend.boot.BaseCost.*;
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
import it.algos.wiki24.backend.service.*;
import it.algos.wiki24.backend.wrapper.*;
import jakarta.annotation.*;
import org.springframework.context.*;

import javax.inject.*;
import java.time.*;
import java.time.format.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Wed, 10-Jan-2024
 * Time: 09:05
 */
public abstract class Upload implements AlgosBuilderPattern {

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
    QueryService queryService;

    protected String titoloPagina;

    protected CrudModulo moduloCorrente;

    protected boolean costruttoreValido = false;

    protected boolean patternCompleto = false;

    protected String nomeLista;

    protected TypeLista typeLista;

    protected String collectionName;

    protected String message;

    protected String headerText;

    protected String bodyText;

    protected String bottomText;

    protected String uploadText;

    protected int numBio;

    protected Class clazzLista;

    protected boolean uploadTest;

    protected TypeSummary typeSummary;

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
        this.patternCompleto = typeLista != TypeLista.nessunaLista && clazzLista != null;
        this.checkValiditaCostruttore();
    }

    /**
     * Preferenze usate da questa classe <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Puo essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void fixPreferenze() {
        this.typeLista = TypeLista.nessunaLista;
        this.typeSummary = TypeSummary.nessuno;

        //        this.usaDimensioneParagrafi = true;
        //        this.usaIncludeSottoMax = true;
        //        this.usaSottopaginaOltreMax = true;
        this.uploadTest = false;
        this.headerText = VUOTA;
        this.bodyText = VUOTA;
        this.bottomText = VUOTA;
        this.uploadText = VUOTA;
    }

    /**
     * Pattern Builder <br>
     */
    public Upload test() {
        this.uploadTest = true;
        this.typeSummary = TypeSummary.test;
        return this;
    }

    public WResult upload() {
        String message;

        if (typeLista == null || typeLista == TypeLista.nessunaLista) {
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

        return registra();
    }

    protected WResult registra() {
        String wikiTitle = titoloPagina;

        if (textService.isEmpty(titoloPagina)) {
            logger.error(new WrapLog().message("Manca il titolo della pagina"));
        }
        if (textService.isEmpty(uploadText)) {
            logger.error(new WrapLog().message("Manca l' uploadText della pagina"));
        }

        if (uploadTest) {
            wikiTitle = "Utente:Biobot/" + titoloPagina;
        }

        return queryService.write(wikiTitle, uploadText,typeSummary.get());
    }


    /**
     * Pattern Builder <br>
     */
    public Upload esegue() {
        StringBuffer buffer = new StringBuffer();
        String message;
        if (!checkValiditaPattern()) {
            return this;
        }
        if (textService.isValid(uploadText)) {
            return this;
        }

        if (typeLista == null || typeLista == TypeLista.nessunaLista) {
            System.out.println(VUOTA);
            message = String.format("Tentativo di usare il metodo '%s' per l'istanza [%s.%s]", "esegue", collectionName, nomeLista);
            logger.info(new WrapLog().message(message));
            message = String.format("Manca il '%s' per l'istanza [%s.%s] e il metodo '%s' NON può funzionare.", "typeLista", collectionName, nomeLista, "esegue");
            logger.warn(new WrapLog().message(message));
            return null;
        }

        buffer.append(creaHeader());
        buffer.append(creaBody());
        buffer.append(creaBottom());
        this.uploadText = buffer.toString().trim();

        return this;
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
        return switch (typeLista) {
            case giornoNascita, giornoMorte, annoNascita, annoMorte -> String.format("{{Torna a|%s}}", nomeLista);
            default -> VUOTA;
        };
    }

    protected String tmpListaBio() {
        if (numBio == 0) {
            numBio = ((Lista) appContext.getBean(clazzLista, nomeLista)).numBio();
        }
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMM yyy")); ;
        String progetto = "biografie";
        String txtVoci = textService.format(numBio);

        return String.format("{{ListaBio|bio=%s|data=%s|progetto=%s}}", txtVoci, data, progetto);
    }


    protected String creaBody() {
        StringBuffer buffer = new StringBuffer();
        Lista istanza = ((Lista) appContext.getBean(clazzLista, nomeLista));
        if (istanza.getType() == typeLista) {
            bodyText = istanza.bodyText();
        }
        else {
            message = String.format("I type di Lista [%s] e Upload [%s], NON coincidono", istanza.getType().getTag(), typeLista.getTag());
            logger.error(new WrapLog().exception(new AlgosException(message)));
            return VUOTA;
        }

        //        if (isSottopagina) {
        //            buffer.append(creaBody());
        //        }
        //        else {
        buffer.append(getListaIni());
        buffer.append(bodyText);
        buffer.append(DOPPIE_GRAFFE_END);
        //        }

        this.bodyText = buffer.toString();
        return this.bodyText;
    }

    protected String getListaIni() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("{{");
        buffer.append(typeLista.getPersone());
        buffer.append(CAPO);
        buffer.append("|titolo=");
        buffer.append(switch (typeLista) {
            case giornoNascita -> wikiUtilityService.wikiTitleNatiGiorno(nomeLista);
            case giornoMorte -> wikiUtilityService.wikiTitleMortiGiorno(nomeLista);
            case annoNascita -> wikiUtilityService.wikiTitleNatiAnno(nomeLista);
            case annoMorte -> wikiUtilityService.wikiTitleMortiAnno(nomeLista);
            default -> VUOTA;
        });
        buffer.append(CAPO);
        buffer.append("|voci=");
        buffer.append(numBio);
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
        buffer.append(interprogetto());
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

    protected String interprogetto() {
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

    protected String includeIni() {
        return "<noinclude>";
    }

    protected String includeEnd() {
        return "</noinclude>";
    }

    protected void checkValiditaCostruttore() {
        if (moduloCorrente != null) {
            this.costruttoreValido = moduloCorrente.existByKey(textService.primaMaiuscola(nomeLista)) || moduloCorrente.existByKey(textService.primaMinuscola(nomeLista));
        }
        else {
            message = String.format("Manca il backend in fixPreferenze() di %s", this.getClass().getSimpleName());
            logger.error(new WrapLog().message(message));
            this.costruttoreValido = false;
        }

        if (this.typeLista == TypeLista.nessunaLista) {
            message = String.format("Manca il type della lista in fixPreferenze() di %s", this.getClass().getSimpleName());
            logger.error(new WrapLog().message(message));
            this.costruttoreValido = false;
        }

        this.collectionName = costruttoreValido ? annotationService.getCollectionName(BioMongoEntity.class) : VUOTA;
    }

    protected boolean checkValiditaPattern() {
        if (costruttoreValido && patternCompleto) {
            return true;
        }
        else {
            if (!costruttoreValido) {
                message = String.format("Non è valido il costruttore di %s", this.getClass().getSimpleName());
                logger.error(new WrapLog().message(message));
            }
            if (!patternCompleto) {
                message = String.format("Pattern non completo di %s", this.getClass().getSimpleName());
                logger.error(new WrapLog().message(message));
                if (clazzLista == null) {
                    message = String.format("Manca la classe della lista in fixPreferenze() di %s", this.getClass().getSimpleName());
                    logger.info(new WrapLog().message(message));
                }
            }

            return false;
        }
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

    public String getHeaderText() {
        if (textService.isEmpty(headerText)) {
            this.esegue();
        }

        return headerText;
    }

    public String getBodyText() {
        if (textService.isEmpty(bodyText)) {
            this.esegue();
        }

        return bodyText;
    }

    public String getBottomText() {
        if (textService.isEmpty(bottomText)) {
            this.esegue();
        }

        return bottomText;
    }

    public String getUploadText() {
        if (textService.isEmpty(uploadText)) {
            this.esegue();
        }

        return uploadText;
    }

}
