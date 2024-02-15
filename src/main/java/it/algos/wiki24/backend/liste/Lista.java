package it.algos.wiki24.backend.liste;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.base24.backend.boot.BaseCost.*;
import it.algos.base24.backend.logic.*;
import it.algos.base24.backend.packages.crono.anno.*;
import it.algos.base24.backend.packages.crono.giorno.*;
import it.algos.base24.backend.packages.crono.secolo.*;
import it.algos.base24.backend.service.*;
import it.algos.base24.backend.wrapper.*;
import static it.algos.wiki24.backend.boot.WikiCost.*;
import it.algos.wiki24.backend.enumeration.*;
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
import java.util.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Fri, 05-Jan-2024
 * Time: 07:40
 */
@SpringComponent
@Primary()
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Lista implements AlgosBuilderPattern {

    @Inject
    ApplicationContext appContext;

    @Inject
    TextService textService;

    @Inject
    LogService logger;

    @Inject
    BioMongoModulo bioMongoModulo;

    @Inject
    DidascaliaService didascaliaService;

    @Inject
    AnnotationService annotationService;

    @Inject
    WikiUtilityService wikiUtilityService;

    @Inject
    protected GiornoModulo giornoModulo;

    @Inject
    protected AnnoModulo annoModulo;

    @Inject
    ArrayService arrayService;

    @Inject
    MathService mathService;

    @Inject
    SecoloModulo secoloModulo;

    @Inject
    protected AttSingolareModulo attSingolareModulo;

    @Inject
    protected AttPluraleModulo attPluraleModulo;

    @Inject
    protected NazSingolareModulo nazSingolareModulo;

    @Inject
    protected NazPluraleModulo nazPluraleModulo;

    protected TypeLista type;


    /**
     * Lista ordinata (per cognome) delle biografie (Bio) che hanno una valore valido per la pagina specifica <br>
     * La lista è ordinata per cognome <br>
     */
    protected List<BioMongoEntity> listaBio;


    protected List<WrapDidascalia> listaWrapDidascalie;

    protected LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappaCompleta;


    protected String bodyText;

    protected List<String> listaSottoPagine = new ArrayList<>();

    protected List<String> listaSottoSottoPagine = new ArrayList<>();

    protected LinkedHashMap<String, String> mappaSottoPagine;

    protected LinkedHashMap<String, String> mappaSottoSottoPagine;

    protected boolean costruttoreValido = false;

    protected boolean patternCompleto = false;

    protected String nomeLista;

    protected String titoloPagina;

    protected CrudModulo moduloCorrente;


    protected String collectionName;

    protected String message;

    boolean usaDimensioneParagrafi;

    boolean usaIncludeSottoMax;

    boolean usaSottopagine;

    boolean isSottopagina;

    private int sogliaDiv;

    private int sogliaParagrafi;

    private int sogliaIncludeAll;

    private int sogliaSottoPagina;

    private int sogliaVociTotaliPaginaPerSottopagine;

    /**
     * Costruttore base con 1 parametro (obbligatorio) <br>
     * Not annotated with @Autowired annotation, classe astratta <br>
     * La classe usa poi il metodo @PostConstruct inizia() per proseguire dopo l'init del costruttore <br>
     */
    public Lista(String nomeLista) {
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

        this.usaDimensioneParagrafi = true;
        this.sogliaDiv = WPref.sogliaDiv.getInt();
        this.sogliaParagrafi = WPref.sogliaParagrafi.getInt();
        this.sogliaIncludeAll = WPref.sogliaIncludeAll.getInt();
        this.sogliaSottoPagina = WPref.sogliaSottoPagina.getInt();

    }

    /**
     * Pattern Builder <br>
     */
    public Lista type(TypeLista type) {
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

        this.usaSottopagine = switch (type) {
            case giornoNascita, giornoMorte -> WPref.usaSottopagineGiorni.is();
            case annoNascita, annoMorte -> WPref.usaSottopagineAnni.is();
            case attivitaSingolare, attivitaPlurale -> WPref.usaSottopagineAttivita.is();
            case nazionalitaSingolare, nazionalitaPlurale -> WPref.usaSottopagineNazionalita.is();
            default -> false;
        };

        this.sogliaVociTotaliPaginaPerSottopagine = switch (type) {
            case giornoNascita, giornoMorte -> WPref.sogliaPaginaGiorniAnni.getInt();
            case annoNascita, annoMorte -> WPref.sogliaPaginaGiorniAnni.getInt();
            case attivitaSingolare, attivitaPlurale -> WPref.sogliaSottoPagina.getInt();
            case nazionalitaSingolare, nazionalitaPlurale -> WPref.sogliaSottoPagina.getInt();
            default -> 0;
        };

        this.usaIncludeSottoMax = switch (type) {
            case giornoNascita, giornoMorte -> true;
            case annoNascita, annoMorte -> true;
            case attivitaSingolare, attivitaPlurale -> false;
            case nazionalitaSingolare, nazionalitaPlurale -> false;
            default -> false;
        };

        return this;
    }

    /**
     * Pattern Builder <br>
     */
    public Lista nonUsaDimensioneParagrafi() {
        this.usaDimensioneParagrafi = false;
        return this;
    }

    //    /**
    //     * Pattern Builder <br>
    //     */
    //    public Lista nonUsaIncludeNeiParagrafi() {
    //        this.usaIncludeSottoMax = false;
    //        return this;
    //    }

    protected void checkValiditaCostruttore() {
        costruttoreValido = textService.isValid(nomeLista);
    }

    /**
     * Pattern valido se: <br>
     * <p>
     * costruttoreValido = true <br>
     * patternCompleto = true <br>
     * type != null e type == TypeLista.nessunaLista <br>
     * nomeLista != null e corrispondente a un valore valido di giorno/anno/attività/nazionalità <br>
     */
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
     * Numero delle biografie (Bio) che hanno una valore valido per la pagina specifica <br>
     * Rimanda direttamente al metodo dedicato del service BioMongoModulo, SENZA nessuna elaborazione in questa classe <br>
     *
     * @return -1 se il pattern della classe non è valido, zero se i dati sono validi ma non ci sono biografie <br>
     */
    public int numBio() {
        if (bioMongoModulo == null || textService.isEmpty(nomeLista)) {
            return INT_ERROR;
        }

        return switch (type) {
            case giornoNascita -> bioMongoModulo.countAllByGiornoNato(nomeLista);
            case giornoMorte -> bioMongoModulo.countAllByGiornoMorto(nomeLista);
            case annoNascita -> bioMongoModulo.countAllByAnnoNato(nomeLista);
            case annoMorte -> bioMongoModulo.countAllByAnnoMorto(nomeLista);
            case attivitaSingolare -> bioMongoModulo.countAllByAttivitaSingolare(nomeLista);
            case attivitaPlurale -> bioMongoModulo.countAllByAttivitaPlurale(nomeLista);
            case nazionalitaSingolare -> bioMongoModulo.countAllByNazionalitaSingolare(nomeLista);
            case nazionalitaPlurale -> bioMongoModulo.countAllByNazionalitaPlurale(nomeLista);
            default -> 0;
        };
    }


    /**
     * Lista ordinata delle biografie (Bio) che hanno una valore valido per la pagina specifica <br>
     * Rimanda direttamente al metodo dedicato del service BioMongoModulo, SENZA nessuna elaborazione in questa classe <br>
     *
     * @return null se il pattern della classe non è valido, lista con zero elementi se i dati sono validi ma non ci sono biografie <br>
     */
    public List<BioMongoEntity> listaBio() {
        if (bioMongoModulo == null || textService.isEmpty(nomeLista)) {
            return null;
        }

        return switch (type) {
            case giornoNascita -> bioMongoModulo.findAllByGiornoNato(nomeLista);
            case giornoMorte -> bioMongoModulo.findAllByGiornoMorto(nomeLista);
            case annoNascita -> bioMongoModulo.findAllByAnnoNato(nomeLista);
            case annoMorte -> bioMongoModulo.findAllByAnnoMorto(nomeLista);
            case attivitaSingolare -> bioMongoModulo.findAllByAttivitaSingolare(nomeLista);
            case attivitaPlurale -> bioMongoModulo.findAllByAttivitaPlurale(nomeLista);
            case nazionalitaSingolare -> bioMongoModulo.findAllByNazionalitaSingolare(nomeLista);
            case nazionalitaPlurale -> bioMongoModulo.findAllByNazionalitaPlurale(nomeLista);
            default -> null;
        };
    }


    /**
     * Lista ordinata di tutti i wrapLista che hanno una valore valido per la pagina specifica <br>
     * Costruisce un wrap per ogni elemento della listaBio recuperata da BioMongoModulo <br>
     *
     * @return null se il pattern della classe non è valido, lista con zero elementi se i dati sono validi ma non ci sono biografie <br>
     */
    public List<WrapDidascalia> listaWrapDidascalie() {
        WrapDidascalia wrap;

        listaWrapDidascalie = new ArrayList<>();
        if (listaBio == null || listaBio.size() == 0) {
            listaBio = listaBio();
        }

        if (listaBio != null && listaBio.size() > 0) {
            for (BioMongoEntity bio : listaBio) {
                wrap = appContext.getBean(WrapDidascalia.class).type(type).get(bio);
                if (wrap != null) {
                    listaWrapDidascalie.add(wrap);
                }
            }
        }

        listaBio = null;
        return listaWrapDidascalie;
    }


    /**
     * Lista ordinata di tutte le didascalie che hanno una valore valido per la pagina specifica <br>
     * Estrae la didascalia da ogni elemento della listaWrapDidascalie di questa classe <br>
     * Lista esemplificativa fine a se stessa che NON viene utilizzata da bodyText che usa direttamente listaWrapDidascalie <br>
     *
     * @return null se il pattern della classe non è valido, lista con zero elementi se i dati sono validi ma non ci sono biografie <br>
     */
    public List<String> listaTestoDidascalie() {
        List<String> listaTestoDidascalie;

        //        if (!checkValiditaPattern()) {
        //            return null;
        //        }

        listaTestoDidascalie = new ArrayList<>();
        if (listaWrapDidascalie == null || listaWrapDidascalie.size() == 0) {
            listaWrapDidascalie = listaWrapDidascalie();
        }

        if (listaWrapDidascalie != null && listaWrapDidascalie.size() > 0) {
            for (WrapDidascalia wrap : listaWrapDidascalie) {
                listaTestoDidascalie.add(wrap.getDidascalia());
            }
        }

        listaWrapDidascalie = null;
        return listaTestoDidascalie;
    }


    public LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappaDidascalie() {
        LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaPrima;
        LinkedHashMap<String, List<String>> mappaSeconda;
        List<String> listaTerza;
        String keyUno;
        String keyDue;
        String keyTre;

        if (!checkValiditaPattern()) {
            return null;
        }

        mappaCompleta = new LinkedHashMap<>();
        mappaSottoPagine = new LinkedHashMap<>();
        mappaSottoSottoPagine = new LinkedHashMap<>();
        if (listaWrapDidascalie == null || listaWrapDidascalie.size() == 0) {
            listaWrapDidascalie = listaWrapDidascalie();
        }
        if (listaWrapDidascalie != null) {
            for (WrapDidascalia wrap : listaWrapDidascalie) {
                //--primo livello - paragrafi
                keyUno = wrap.getPrimoLivello();
                if (!mappaCompleta.containsKey(keyUno)) {
                    mappaPrima = new LinkedHashMap<>();
                    mappaCompleta.put(keyUno, mappaPrima);
                }

                //--secondo livello - decade/decina/lettera alfabetica iniziale
                keyDue = wrap.getSecondoLivello();
                mappaPrima = mappaCompleta.get(keyUno);
                if (!mappaPrima.containsKey(keyDue)) {
                    mappaSeconda = new LinkedHashMap<>();
                    mappaPrima.put(keyDue, mappaSeconda);
                }

                //--terzo livello - giorno/anno/binomio alfabetico iniziale
                keyTre = wrap.getTerzoLivello();
                mappaSeconda = mappaPrima.get(keyDue);
                listaTerza = new ArrayList<>();
                if (!mappaSeconda.containsKey(keyTre)) {
                    mappaSeconda.put(keyTre, listaTerza);
                }

                //--didascalia
                listaTerza = mappaSeconda.get(keyTre);
                listaTerza.add(wrap.getDidascalia());
            }
        }

        listaWrapDidascalie = null;
        mappaCompleta = ordinamento(mappaCompleta);
        return fixAltreInCoda(mappaCompleta);
    }

    public LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> ordinamento(final LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappaIn) {
        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappaOut = new LinkedHashMap<>();
        LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaParagrafo;

        mappaOut = switch (type) {
            case giornoNascita, giornoMorte: {
                yield mappaIn;
            }
            case annoNascita, annoMorte: {
                yield mappaIn;
            }
            case attivitaSingolare, attivitaPlurale, nazionalitaSingolare, nazionalitaPlurale: {
                for (String key : mappaIn.keySet()) {
                    mappaParagrafo = mappaIn.get(key);
                    mappaParagrafo = arrayService.sort(mappaParagrafo);
                    mappaOut.put(key, mappaParagrafo);
                }
                yield arrayService.sort(mappaOut);
            }
            default:
                yield mappaIn;
        };

        return mappaOut;
    }

    /**
     * Sposta in coda alla mappa il paragrafo 'Altre...' (eventuale) <br>
     */
    public LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> fixAltreInCoda(final LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappaIn) {
        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>> mappaOut = new LinkedHashMap<>();
        LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaAltre = new LinkedHashMap<>();
        String tag;

        if (mappaIn == null) {
            return null;
        }

        tag = switch (type) {
            case giornoNascita, giornoMorte -> TypeInesistente.giorno.getTag();
            case annoNascita, annoMorte -> TypeInesistente.anno.getTag();
            case attivitaSingolare, attivitaPlurale -> TypeInesistente.nazionalita.getTag();
            case nazionalitaSingolare, nazionalitaPlurale -> TypeInesistente.attivita.getTag();
            default -> VUOTA;
        };

        for (String key : mappaIn.keySet()) {
            if (key.equals(tag)) {
                mappaAltre = mappaIn.get(key);
            }
            else {
                mappaOut.put(key, mappaIn.get(key));
            }
        }

        if (mappaAltre != null && mappaAltre.size() > 0) {
            mappaOut.put(tag, mappaAltre);
        }

        return mappaOut;
    }

    public List<String> keyMappa() {
        if (!checkValiditaPattern()) {
            return null;
        }

        if (mappaCompleta == null || mappaCompleta.size() == 0) {
            mappaCompleta = mappaDidascalie();
        }

        return mappaCompleta != null ? mappaCompleta.keySet().stream().toList() : null;
    }

    /**
     * Testo body della pagina suddiviso (eventualmente) in paragrafi <br>
     * Elabora il testo body della pagina principale <br>
     * Elabora lista e mappa delle eventuali sottoPagine <br>
     *
     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
     */
    public String bodyText() {
        StringBuffer buffer = new StringBuffer();
        int numVociLista; //voci totali
        int numChiaviMappa; //paragrafi effettivi
        boolean usaParagrafi;
        int numVociParagrafo;
        String sottoPagina;
        String vedi;

        if (!checkValiditaPattern()) {
            return STRING_ERROR;
        }

        if (mappaCompleta == null || mappaCompleta.size() == 0) {
            mappaCompleta = mappaDidascalie();
        }

        if (mappaCompleta != null && mappaCompleta.size() > 0) {
            numVociLista = wikiUtilityService.getSizeMappaMappa(mappaCompleta);
            numChiaviMappa = mappaCompleta.size();
            usaParagrafi = numVociLista > sogliaSottoPagina && numChiaviMappa >= sogliaParagrafi;

            if (usaParagrafi) {
                for (String keyParagrafo : mappaCompleta.keySet()) {
                    numVociParagrafo = wikiUtilityService.getSizeMappa(mappaCompleta.get(keyParagrafo));

                    //titolo con/senza dimensione
                    if (usaDimensioneParagrafi) {
                        //titolo con/senza includeOnly
                        if (usaIncludeSottoMax && numVociLista < sogliaIncludeAll) {
                            //per i titoli dei paragrafi che vengono 'inclusi', meglio non mettere le dimensioni
                            buffer.append(wikiUtilityService.setParagrafoIncludeOnly(keyParagrafo, numVociParagrafo));
                        }
                        else {
                            buffer.append(wikiUtilityService.setParagrafo(keyParagrafo, numVociParagrafo));
                        }
                    }
                    else {
                        buffer.append(wikiUtilityService.setParagrafo(keyParagrafo));
                    }

                    //corpo con/senza sottopagine
                    if (usaSottopagine && numVociLista > sogliaVociTotaliPaginaPerSottopagine && numVociParagrafo > sogliaSottoPagina) {
                        sottoPagina = String.format("%s%s%s", textService.primaMaiuscola(titoloPagina), SLASH, keyParagrafo);

                        vedi = String.format("{{Vedi anche|%s}}", sottoPagina);
                        buffer.append(vedi + CAPO);

                        if (listaSottoPagine != null) {
                            listaSottoPagine.add(keyParagrafo);
                        }
                        if (mappaSottoPagine != null) {
                            mappaSottoPagine.put(keyParagrafo, bodySottopagina(keyParagrafo));
                        }
                    }
                    else {
                        buffer.append(bodyParagrafo(keyParagrafo, true));
                    }
                }
            }
            else {
                //corpo unico senza paragrafi e senza sottopagine
                buffer.append(bodyAll());
            }
        }

        bodyText = buffer.toString().trim();
        return bodyText;
    }


    /**
     * Testo della pagina <br>
     */
    public String bodyAll() {
        StringBuffer buffer = new StringBuffer();
        int numVociTotali = wikiUtilityService.getSizeMappaMappa(mappaCompleta);
        boolean usaDiv = numVociTotali > sogliaDiv;

        if (usaDiv) {
            buffer.append(DIV_INI_CAPO);
        }
        for (String keyParagrafo : mappaCompleta.keySet()) {
            buffer.append(bodyParagrafo(keyParagrafo, false));
        }
        if (usaDiv) {
            buffer.append(DIV_END_CAPO);
        }

        return buffer.toString();
    }

    /**
     * Testo del paragrafo <br>
     */
    public String bodyParagrafo(String keyParagrafo, boolean usaDivisori) {
        LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaParagrafo = mappaCompleta.get(keyParagrafo);
        StringBuffer buffer = new StringBuffer();
        int numVociParagrafo = wikiUtilityService.getSizeMappa(mappaParagrafo);
        boolean usaDiv = usaDivisori && numVociParagrafo > sogliaDiv;

        if (numVociParagrafo > 0) {
            if (usaDiv) {
                buffer.append(DIV_INI_CAPO);
            }
            for (String secondoLivello : mappaParagrafo.keySet()) {
                for (String terzoLivello : mappaParagrafo.get(secondoLivello).keySet()) {
                    for (String didascalia : mappaParagrafo.get(secondoLivello).get(terzoLivello)) {
                        buffer.append(ASTERISCO);
                        buffer.append(didascalia);
                        buffer.append(CAPO);
                    }
                }
            }
            if (usaDiv) {
                buffer.append(DIV_END_CAPO);
            }
        }

        return buffer.toString();
    }

    public TypeLista getType() {
        return type;
    }

    /**
     * Lista delle sottoPagine <br>
     * Elabora il testo body della pagina principale, se non già elaborato <br>
     *
     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
     */
    public List<String> listaSottoPagine() {
        if (!checkValiditaPattern()) {
            return null;
        }

        if (textService.isEmpty(bodyText)) {
            bodyText();
        }

        return listaSottoPagine;
    }

    /**
     * Mappa delle sottoPagine <br>
     * Elabora il testo body della pagina principale, se non già elaborato <br>
     *
     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
     */
    public LinkedHashMap<String, String> mappaSottoPagine() {
        if (!checkValiditaPattern()) {
            return null;
        }

        if (textService.isEmpty(bodyText)) {
            bodyText();
        }

        return mappaSottoPagine;
    }

    /**
     * Lista delle sottoSottoPagine <br>
     * Elabora il testo body della pagina principale, se non già elaborato <br>
     *
     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
     */
    public List<String> listaSottoSottoPagine() {
        if (!checkValiditaPattern()) {
            return null;
        }

        listaSottoPagine();
        return listaSottoSottoPagine;
    }


    /**
     * Mappa delle sottoSottoPagine <br>
     * Elabora il testo body della pagina principale, se non già elaborato <br>
     *
     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
     */
    public LinkedHashMap<String, String> mappaSottoSottoPagine() {
        if (!checkValiditaPattern()) {
            return null;
        }

        listaSottoPagine();
        return mappaSottoSottoPagine;
    }

    /**
     * Testo della sottopagina <br>
     *
     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
     */
    public String bodySottopagina(String keySottopagina) {
        StringBuffer buffer = new StringBuffer();
        int numVociSottoPagina; //voci della sottoPagina
        boolean usaParagrafiPreferenze;
        boolean usaParagrafiDinamico;
        boolean usaSottoSottoPaginaPreferenze = false;
        boolean usaSottoSottoPaginaDinamico = false;
        boolean usaDiv;
        int numChiaviMappaSottoPagina; //paragrafi della sottoPagina
        LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaSottoPagina;
        int numVociParagrafo = 0;
        String sottoPagina;
        String vedi;

        if (!checkValiditaPattern()) {
            return STRING_ERROR;
        }
        if (mappaCompleta == null || mappaCompleta.size() == 0) {
            mappaCompleta = mappaDidascalie();
        }
        if (!mappaCompleta.containsKey(textService.primaMaiuscola(keySottopagina))) {
            return STRING_ERROR;
        }

        usaParagrafiPreferenze = switch (type) {
            case giornoNascita, giornoMorte -> WPref.usaParagrafiGiorniSotto.is();
            case annoNascita, annoMorte -> WPref.usaParagrafiAnniSotto.is();
            case attivitaSingolare, attivitaPlurale, nazionalitaSingolare, nazionalitaPlurale -> WPref.usaParagrafiAttNazSotto.is();
            default -> false;
        };

        usaSottoSottoPaginaPreferenze = switch (type) {
            case giornoNascita, giornoMorte -> false;
            case annoNascita, annoMorte -> false;
            case attivitaSingolare, attivitaPlurale -> true;
            case nazionalitaSingolare, nazionalitaPlurale -> true;
            default -> false;
        };

        mappaSottoPagina = mappaCompleta.get(textService.primaMaiuscola(keySottopagina));
        numVociSottoPagina = wikiUtilityService.getSizeMappa(mappaSottoPagina);
        numChiaviMappaSottoPagina = mappaSottoPagina.size();
        usaParagrafiDinamico = numVociSottoPagina > sogliaSottoPagina && numChiaviMappaSottoPagina >= sogliaParagrafi;
        if (usaParagrafiPreferenze && usaParagrafiDinamico) {
            for (String keyParagrafo : mappaSottoPagina.keySet()) {
                numVociParagrafo = wikiUtilityService.getSizeMappaMappaLista(mappaSottoPagina.get(keyParagrafo));
                usaSottoSottoPaginaDinamico = numVociParagrafo > sogliaSottoPagina;
                usaDiv = numVociParagrafo > sogliaDiv;

                buffer.append(wikiUtilityService.setParagrafo(keyParagrafo, numVociParagrafo));
                //corpo con/senza sottoSottoPagine
                if (usaSottoSottoPaginaPreferenze && usaSottoSottoPaginaDinamico) {
                    sottoPagina = String.format("%s%s%s%s%s", textService.primaMaiuscola(titoloPagina), SLASH, textService.primaMaiuscola(keySottopagina), SLASH, keyParagrafo);
                    vedi = String.format("{{Vedi anche|%s}}", sottoPagina);
                    buffer.append(vedi + CAPO);

                    if (listaSottoSottoPagine != null) {
                        listaSottoSottoPagine.add(keySottopagina + SLASH + keyParagrafo);
                    }
                    if (mappaSottoSottoPagine != null) {
                        mappaSottoSottoPagine.put(keySottopagina + SLASH + keyParagrafo, bodySottoSottoPagina(keySottopagina, keyParagrafo));
                    }
                }
                else {
                    if (usaDiv) {
                        buffer.append(DIV_INI_CAPO);
                    }
                    for (String terzoLivello : mappaSottoPagina.get(keyParagrafo).keySet()) {
                        for (String didascalia : mappaSottoPagina.get(keyParagrafo).get(terzoLivello)) {
                            buffer.append(ASTERISCO);
                            buffer.append(didascalia);
                            buffer.append(CAPO);
                        }
                    }
                    if (usaDiv) {
                        buffer.append(DIV_END_CAPO);
                    }
                }
            }

            //                for (String terzoLivello : mappaSottoPagina.get(keyParagrafo).keySet()) {
            //                    buffer.append(wikiUtilityService.setParagrafo(keyParagrafo, numVociParagrafo));
            //                    if (usaDiv) {
            //                        buffer.append(DIV_INI_CAPO);
            //                    }
            //                    for (String didascalia : mappaSottoPagina.get(keyParagrafo).get(terzoLivello)) {
            //                        buffer.append(ASTERISCO);
            //                        buffer.append(didascalia);
            //                        buffer.append(CAPO);
            //                    }
            //                    if (usaDiv) {
            //                        buffer.append(DIV_END_CAPO);
            //                    }
            //                }
        }
        else {
            usaDiv = numVociSottoPagina > sogliaDiv;
            if (usaDiv) {
                buffer.append(DIV_INI_CAPO);
            }
            buffer.append(bodyParagrafo(textService.primaMaiuscola(keySottopagina), false));
            if (usaDiv) {
                buffer.append(DIV_END_CAPO);
            }
        }

        return buffer.toString().trim();
    }

    /**
     * Testo della sottoSottoPagina <br>
     *
     * @return STRING_ERROR se il pattern della classe non è valido, VUOTA se i dati sono validi ma non ci sono biografie <br>
     */
    public String bodySottoSottoPagina(String keySottoPagina, String keyParagrafo) {
        StringBuffer buffer = new StringBuffer();
        int numVociSottoPagina; //voci della sottoPagina
        boolean usaParagrafiPreferenze;
        boolean usaParagrafiDinamico;
        boolean usaSottoSottoPaginaPreferenze = false;
        boolean usaSottoSottoPaginaDinamico = false;
        boolean usaDiv;
        int numChiaviMappaSottoPagina; //paragrafi della sottoPagina
        LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaSottoPagina;
        LinkedHashMap<String, List<String>> mappaSottoSottoPagina;
        int numVociParagrafo = 0;
        String sottoPagina;
        String vedi;

        if (!checkValiditaPattern()) {
            return STRING_ERROR;
        }
        if (mappaCompleta == null || mappaCompleta.size() == 0) {
            mappaCompleta = mappaDidascalie();
        }
        if (!mappaCompleta.containsKey(textService.primaMaiuscola(keySottoPagina))) {
            return STRING_ERROR;
        }

        mappaSottoPagina = mappaCompleta.get(textService.primaMaiuscola(keySottoPagina));

        mappaSottoSottoPagina = mappaSottoPagina.get(keyParagrafo);

        //        mappaSottoPagina = mappaCompleta.get(textService.primaMaiuscola(keySottoPagina));
        //        numVociSottoPagina = wikiUtilityService.getSizeMappa(mappaSottoPagina);
        //        numChiaviMappaSottoPagina = mappaSottoPagina.size();
        //        usaParagrafiDinamico = numVociSottoPagina > sogliaSottoPagina && numChiaviMappaSottoPagina >= sogliaParagrafi;

        buffer.append(DIV_INI_CAPO);

        //            numVociParagrafo = wikiUtilityService.getSizeMappaMappaLista(mappaSottoPagina.get(keyParagrafo));
        //            usaSottoSottoPaginaDinamico = numVociParagrafo > sogliaSottoPagina;
        //            usaDiv = numVociParagrafo > sogliaDiv;

        //            buffer.append(wikiUtilityService.setParagrafo(keyParagrafo, numVociParagrafo));
        //corpo con/senza sottoSottoPagine
        //            if (usaDiv) {
        //            }
        for (String key : mappaSottoSottoPagina.keySet()) {
            for (String didascalia : mappaSottoSottoPagina.get(key)) {
                buffer.append(ASTERISCO);
                buffer.append(didascalia);
                buffer.append(CAPO);
            }
            //            if (usaDiv) {
            //            }
        }
        buffer.append(DIV_END_CAPO);

        return buffer.toString().trim();
    }

    public LinkedHashMap<String, LinkedHashMap<String, List<String>>> getMappaSottopagina(String keySottopagina) {
        if (mappaCompleta == null || mappaCompleta.size() == 0) {
            mappaCompleta = mappaDidascalie();
        }

        if (mappaCompleta != null && mappaCompleta.size() > 0) {
            return mappaCompleta.get(textService.primaMaiuscola(keySottopagina));
        }

        return null;
    }

    /**
     * Numero delle biografie (Bio) che hanno una valore valido per il paragrafo (sottopagina) specifico <br>
     * Prima esegue una query diretta al database (più veloce)
     * Se non trova nulla controlla la mappaCompleta (creandola se manca) per vedere se esiste il paragrafo/sottopagina
     *
     * @return -1 se il pattern della classe non è valido o se nella mappa non esiste il paragrafo indicato come keySottopagina, zero se i dati sono validi ma non ci sono biografie <br>
     */
    public int numBio(final String keySottopagina) {
        int numBioSottopagina = INT_ERROR;
        String lowerLista = textService.primaMinuscola(nomeLista);
        String lowerSotto = textService.primaMinuscola(keySottopagina);

        if (!checkValiditaPattern()) {
            return numBioSottopagina;
        }

        numBioSottopagina = switch (type) {
            case giornoNascita -> bioMongoModulo.countByGiornoNatoAndSecolo(lowerLista, keySottopagina);
            case giornoMorte -> bioMongoModulo.countByGiornoMortoAndSecolo(lowerLista, keySottopagina);
            case annoNascita -> bioMongoModulo.countByAnnoNatoAndMese(lowerLista, lowerSotto);
            case annoMorte -> bioMongoModulo.countByAnnoMortoAndMese(lowerLista, lowerSotto);
            case attivitaSingolare -> bioMongoModulo.countByAttivitaAndNazionalita(lowerLista, lowerSotto);
            case attivitaPlurale -> bioMongoModulo.countByAttivitaAndNazionalita(lowerLista, lowerSotto);
            case nazionalitaSingolare -> bioMongoModulo.countByNazionalitaAndAttivita(lowerLista, lowerSotto);
            case nazionalitaPlurale -> bioMongoModulo.countByNazionalitaAndAttivita(lowerLista, lowerSotto);
            default -> INT_ERROR;
        };

        if (numBioSottopagina > 0) {
            return numBioSottopagina;
        }

        return numBioSottopagina;
    }

    public LinkedHashMap<String, LinkedHashMap<String, List<String>>> mappaSottopagina(String keySottopagina) {
        if (textService.isEmpty(bodyText)) {
            bodyText();
        }

        return mappaCompleta.get(keySottopagina);
    }

}

